package br.com.tucaleli.exceptions;

public class InvalidCandidateException extends RuntimeException {
    public InvalidCandidateException() {
        super("The candidate number is invalid. Candidate number must be included in the reference list.");
    }
}
