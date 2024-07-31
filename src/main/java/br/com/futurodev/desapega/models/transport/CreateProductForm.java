package br.com.futurodev.desapega.models.transport;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record CreateProductForm(@NotBlank String name,
                                @NotBlank String description,
                                @NotNull BigDecimal price) {

}
