package br.com.tucaleli.exceptions;

public class InvalidPassword extends Exception {
    public InvalidPassword() {
        super("Invalid password!");
    }
}
