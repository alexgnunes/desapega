package br.com.futurodev.desapega.exception;

import java.math.BigDecimal;

public class InsufficientBalanceException extends Throwable {
    public InsufficientBalanceException(BigDecimal balancePerson) {
        super("Saldo insuficiente. Saldo atual: " + balancePerson);
    }
}
