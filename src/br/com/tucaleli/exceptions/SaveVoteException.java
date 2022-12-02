package br.com.tucaleli.exceptions;

public class SaveVoteException extends Exception {
    public SaveVoteException() {
        super("Error saving vote!");
    }
}
