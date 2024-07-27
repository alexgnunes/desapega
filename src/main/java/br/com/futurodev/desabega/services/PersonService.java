package br.com.futurodev.desabega.services;

import br.com.futurodev.desabega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desabega.models.Person;
import br.com.futurodev.desabega.models.Wallet;
import br.com.futurodev.desabega.models.transport.CreatePersonForm;
import br.com.futurodev.desabega.models.transport.PersonDto;
import br.com.futurodev.desabega.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.security.KeyStore;
import java.util.Optional;

@Service
public class PersonService {

    private final PersonRepository personRepository;
    private final PasswordEncoder passwordEncoder;

    PersonService(PersonRepository personRepository, PasswordEncoder passwordEncoder) {
        this.personRepository = personRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public PersonDto createPerson(CreatePersonForm createPersonForm) throws UserAlreadyRegisteredException {
        if (this.personRepository.existsByEmail(createPersonForm.email())) {
            throw new UserAlreadyRegisteredException(createPersonForm.email());
        }
        String password = this.passwordEncoder.encode(createPersonForm.password());

        Wallet wallet = new Wallet();
        wallet.setBalance(BigDecimal.ZERO);

        Person person = new Person(createPersonForm);
        person.setPassword(password);
        person.setWallet(wallet);
        Person persistencePerson = this.personRepository.save(person);
        return new PersonDto(persistencePerson);
    }
}
