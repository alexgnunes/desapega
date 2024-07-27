package br.com.futurodev.desabega.models;

import br.com.futurodev.desabega.models.transport.CreatePersonForm;
import br.com.futurodev.desabega.models.transport.WalletDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "person")
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long personId;

    @Column(nullable = false)
    private String name;
    @Column(nullable = false, unique = true)
    private String email;
    @Column(nullable = false)
    private String password;
    @Column(nullable = false)
    private String phone;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "walletId")
    private Wallet wallet;

    @OneToMany(mappedBy = "owner")
    private List<Product> products = new ArrayList<>();

    public Person(CreatePersonForm createPersonForm){
        this.name = createPersonForm.name();
        this.email = createPersonForm.email();
        this.password = createPersonForm.password();
        this.phone = createPersonForm.phone();
    }
}
