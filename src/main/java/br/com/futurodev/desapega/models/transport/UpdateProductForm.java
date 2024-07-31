package br.com.futurodev.desapega.models.transport;

import java.math.BigDecimal;

public record UpdateProductForm (String name,
                                 String description,
                                 BigDecimal price){
}
