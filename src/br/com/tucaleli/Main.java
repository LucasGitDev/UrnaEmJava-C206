package br.com.tucaleli;

import br.com.tucaleli.controllers.FileController;
import br.com.tucaleli.exceptions.*;
import br.com.tucaleli.models.Candidate;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;

public class Main {

    static Scanner scanner = new Scanner(System.in);
    static List<Candidate> candidates = new ArrayList<>();

    static HashSet<String> voters = new HashSet<>();

    public static void main(String[] args) {
        init();

        int option;
        do {
            showMenu();
            option = scanner.nextInt();
            scanner.nextLine();
            clearScreen();
            try {
                switch (option) {
                    case 0 -> System.out.println("Bye!");
                    case 1 -> vote();
                    case 2 -> {
                        if (!loginAdmin()) {
                            System.out.println("Invalid password!");
                        }
                    }
                    default -> throw new InvalidOptionOnMenuException();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        } while (option != 0);
    }

    public static void vote() throws SaveVoteException, AlreadyVotedException {
        // Questionar CPF do usuário
        System.out.println("Type your CPF: ");
        String cpf = scanner.nextLine();

        // Verificar se o CPF é válido (11 dígitos)
        if (cpf.length() != 11) {
            throw new InvalidCPFException();
        }

        // Verificar se o usuário já votou
        if (voters.contains(cpf)) {
            throw new AlreadyVotedException();
        }

        // Apresentar a lista de candidatos
        System.out.println("Candidates:");
        for (Candidate candidate : candidates) {
            System.out.println(candidate.getNumber() + " - " + candidate.getName());
        }

        // Receber a opção do usuário
        System.out.println("Type the number of the candidate you want to vote: ");
        int option = scanner.nextInt();
        scanner.nextLine();

        // Verificar se a opção é válida
        if (!isCandidateValid(option)) {
            throw new InvalidCandidateException();
        }

        // Confirmar o voto
        System.out.println("Are you sure you want to vote for " + getCandidateName(option) + "? (y/n)");
        String confirm = scanner.nextLine();

        if (!confirm.equals("y")) {
            System.out.println("Vote canceled!");
            return;
        }

        // Escrever o cpf do usuario no arquivo referente ao candidato
        if (!saveVote(cpf, option)) {
            throw new SaveVoteException();
        }

        // Apresentar mensagem de sucesso
        System.out.println("Vote successfully registered!");
    }

    public static boolean saveVote(String cpf, int candidateNumber) {
        voters.add(cpf);
        String fileName = "candidate-" + candidateNumber + ".txt";
        try {
            FileController.appendLine("src/br/com/tucaleli/assets/" + fileName, cpf);
            return true;
        } catch (Exception e) {
            System.out.println(e.getMessage());
            return false;
        }
    }

    public static String getCandidateName(int number) {
        for (Candidate candidate : candidates) {
            if (candidate.getNumber() == number) {
                return candidate.getName();
            }
        }
        return "";
    }

    public static boolean isCandidateValid(int option) {
        for (Candidate candidate : candidates) {
            if (candidate.getNumber() == option) {
                return true;
            }
        }
        return false;
    }

    public static boolean loginAdmin() {
        System.out.println("Enter the admin password:");
        String password = scanner.nextLine();

        if (password.equals("admin")) {
            showSummary();
            return true;
        }
        return false;
    }

    public static void showSummary() {
        // Ler os arquivos de cada candidato
        System.out.println("Summary:");
        for (Candidate candidate : candidates) {
            String fileName = "candidate-" + candidate.getNumber() + ".txt";
            try {
                List<String> lines = FileController.readAllLines("src/br/com/tucaleli/assets/" + fileName);
                System.out.println(candidate.getName() + ": " + lines.size() + " votes");
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }

    public static void showMenu() {
        System.out.println("\n");
        separator();
        System.out.println("1 - Votar");
        System.out.println("2 - Resumo da votação [ADMIN]");
        System.out.println("0 - Sair");
        separator();
    }

    public static void clearScreen() {
        System.out.print("\n\n\n\n\n\n\n\n\n\n\n\n\n\n\n");
    }

    public static void separator() {
        System.out.println("====================================");
    }

    public static void init() {
        initCandidates();
        preloadVoters();
    }

    public static void initCandidates() {
        candidates.add(new Candidate("Paulinho Anao", 77));
        candidates.add(new Candidate("Rogerao", 51));
        candidates.add(new Candidate("Darth Verde", 43));
    }

    public static void preloadVoters() {
        // Ler todos os arquivos de candidatos
        for (Candidate candidate : candidates) {
            // Ler o arquivo do candidato
            try {
                List<String> lines = FileController.readAllLines("src/br/com/tucaleli/assets/candidate-" + candidate.getNumber() + ".txt");
                // Adicionar os CPFs lidos no HashSet
                voters.addAll(lines);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
    }
}