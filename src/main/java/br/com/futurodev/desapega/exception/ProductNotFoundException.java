package br.com.futurodev.desapega.exception;

public class ProductNotFoundException extends RuntimeException {

    public ProductNotFoundException(Long id) {
        super(String.format("Produto não encontrado com o id: %s", id));
    }
}
