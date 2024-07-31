package br.com.futurodev.desapega.exception;

public class PersonNotFoundException extends Exception {

    public PersonNotFoundException(String email) {
        super("Usuário não cadastrado");
    }
}
