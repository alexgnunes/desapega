package br.com.futurodev.desabega.models.transport;

import jakarta.validation.constraints.NotBlank;

import java.math.BigDecimal;

public record UpdateProductForm (String name,
                                 String description,
                                 BigDecimal price){
}
