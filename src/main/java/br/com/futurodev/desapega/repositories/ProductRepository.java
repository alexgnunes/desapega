package br.com.futurodev.desapega.repositories;

import br.com.futurodev.desapega.models.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface ProductRepository extends JpaRepository<Product, Long> {

    Page<Product> findAllByOwnerPersonIdAndDeletedFalse(Long personId, Pageable pageable);
    Page<Product> findByOwnerPersonIdNotAndDeletedFalseAndSoldFalse(Long personId, Pageable pageable);
    Optional<Product> findByProductIdAndDeletedFalseAndOwnerPersonId(Long productId,Long personId);
    Optional<Product> findByProductIdAndDeletedFalseAndSoldFalseAndOwnerPersonIdNot(Long productId,Long personId);
}
