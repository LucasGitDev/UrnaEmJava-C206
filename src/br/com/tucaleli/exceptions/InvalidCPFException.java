package br.com.tucaleli.exceptions;

public class InvalidCPFException extends RuntimeException {
    public InvalidCPFException() {
        super("The CPF is invalid. CPF must have 11 digits.");
    }
}
