package br.com.estudo.domain.exceptions;

public class ErrosSistema extends RuntimeException {

    public ErrosSistema(String message) {
        super(message);
    }

    public ErrosSistema(String message, Throwable cause) {
        super(message, cause);
    }
}
