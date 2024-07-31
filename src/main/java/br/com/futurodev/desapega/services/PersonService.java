package br.com.futurodev.desapega.services;

import br.com.futurodev.desapega.exception.PersonNotFoundException;
import br.com.futurodev.desapega.exception.UserAlreadyRegisteredException;
import br.com.futurodev.desapega.models.Person;
import br.com.futurodev.desapega.models.Wallet;
import br.com.futurodev.desapega.models.transport.CreatePersonForm;
import br.com.futurodev.desapega.models.transport.PersonDto;
import br.com.futurodev.desapega.repositories.PersonRepository;
import jakarta.transaction.Transactional;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class PersonService implements UserDetailsService {

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

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return this.personRepository.findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException(username));
    }

    public Person getSinglePerson(String email) throws PersonNotFoundException {
        return this.personRepository.findByEmail(email)
                .orElseThrow(() -> new PersonNotFoundException(email));
    }
}
