package br.com.futurodev.desapega.models.transport;

import br.com.futurodev.desapega.models.Person;

public record PersonDto(Long id,
                        String name,
                        String email,
                        String phone,
                        WalletDto wallet) {

    public PersonDto(Person person) {
        this(person.getPersonId(), person.getName(), person.getEmail(), person.getPhone(), new WalletDto(person.getWallet().getWalletId(), person.getWallet().getBalance()));
    }

}
