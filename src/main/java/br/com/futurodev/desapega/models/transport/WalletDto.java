package br.com.futurodev.desapega.models.transport;

import java.math.BigDecimal;

public record WalletDto(Long id,
                        BigDecimal balance) {
}
