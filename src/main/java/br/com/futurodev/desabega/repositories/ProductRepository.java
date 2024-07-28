package br.com.futurodev.desabega.repositories;

import br.com.futurodev.desabega.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByOwnerPersonIdAndDeletedFalse(Long personId, Pageable pageable);
}
