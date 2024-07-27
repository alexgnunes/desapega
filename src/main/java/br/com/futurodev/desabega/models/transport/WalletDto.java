package br.com.futurodev.desabega.models.transport;

import java.math.BigDecimal;

public record WalletDto(Long id,
                        BigDecimal balance) {
}
