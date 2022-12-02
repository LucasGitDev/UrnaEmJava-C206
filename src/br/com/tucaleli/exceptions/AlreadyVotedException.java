package br.com.tucaleli.exceptions;

public class AlreadyVotedException extends Exception {
    public AlreadyVotedException() {
        super("You have already voted!");
    }
}
