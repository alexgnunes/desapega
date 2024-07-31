package br.com.futurodev.desapega.repositories;

import br.com.futurodev.desapega.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String username);
}
