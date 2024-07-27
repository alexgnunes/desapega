package br.com.futurodev.desabega.repositories;

import br.com.futurodev.desabega.models.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;

public interface WalletRepository extends JpaRepository<Wallet, Long> {
}
