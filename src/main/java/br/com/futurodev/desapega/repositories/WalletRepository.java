package br.com.futurodev.desapega.repositories;

import br.com.futurodev.desapega.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
