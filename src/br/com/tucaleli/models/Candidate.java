package br.com.tucaleli.models;

public class Candidate {
    private String name;
    private int number;

    public Candidate(String name, int number) {
        this.name = name;
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public int getNumber() {
        return number;
    }
}
