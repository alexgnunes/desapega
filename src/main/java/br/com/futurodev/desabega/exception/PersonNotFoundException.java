package br.com.futurodev.desabega.exception;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(String email) {
        super("Usuário não cadastrado");
    }
}
