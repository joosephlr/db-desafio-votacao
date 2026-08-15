package br.com.votacao.api.exception;

public class CpfInvalidoException extends RuntimeException {

    public CpfInvalidoException(String message) {
        super(message);
    }

    public CpfInvalidoException(String message, Throwable cause) {
        super(message, cause);
    }
}