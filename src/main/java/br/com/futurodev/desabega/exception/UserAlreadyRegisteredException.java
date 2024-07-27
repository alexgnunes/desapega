package br.com.futurodev.desabega.exception;

public class UserAlreadyRegisteredException extends Exception {

    public UserAlreadyRegisteredException(String email) {
        super("Usuario já possui um cadastro");
    }
}
