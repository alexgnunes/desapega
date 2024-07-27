package br.com.futurodev.desabega.models.transport;

import br.com.futurodev.desabega.models.Product;

import java.math.BigDecimal;

public record ProductDto(Long id,
                         String name,
                         String description,
                         BigDecimal price,
                         PersonDto owner,
                         boolean sold) {

    public ProductDto(Product product) {
        this(product.getProductId(), product.getProductName(), product.getDescription(), product.getPrice(), new PersonDto(product.getOwner()), product.isSold());
    }
}
