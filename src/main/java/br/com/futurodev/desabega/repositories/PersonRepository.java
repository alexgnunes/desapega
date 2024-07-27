package br.com.futurodev.desabega.repositories;

import br.com.futurodev.desabega.models.Person;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PersonRepository extends JpaRepository<Person, Long> {
}
