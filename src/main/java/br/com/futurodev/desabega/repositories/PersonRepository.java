package br.com.futurodev.desabega.repositories;

import br.com.futurodev.desabega.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<Person, Long> {

    boolean existsByEmail(String email);

    Optional<Person> findByEmail(String username);
}
