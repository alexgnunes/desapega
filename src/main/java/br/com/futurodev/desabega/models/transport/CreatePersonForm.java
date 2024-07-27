package br.com.futurodev.desabega.models.transport;

import jakarta.validation.constraints.NotBlank;

public record CreatePersonForm(@NotBlank String name,
                               @NotBlank String email,
                               @NotBlank String password,
                               @NotBlank String phone) {

}
