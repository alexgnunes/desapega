package br.com.futurodev.desabega.repositories;

import br.com.futurodev.desabega.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByOwnerPersonIdAndDeletedFalse(Long personId, Pageable pageable);
    Page<Product> findByOwnerPersonIdNotAndDeletedFalseAndSoldFalse(Long personId, Pageable pageable);
    Optional<Product> findByProductIdAndDeletedFalseAndOwnerPersonId(Long productId,Long personId);
}
