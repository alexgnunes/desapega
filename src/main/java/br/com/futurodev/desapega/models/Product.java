package br.com.futurodev.desapega.models;


import br.com.futurodev.desapega.models.transport.CreateProductForm;
import br.com.futurodev.desapega.models.transport.UpdateProductForm;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@Table(name = "product")
public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long productId;

    @Column(nullable = false)
    private String productName;

    @Column(nullable = false)
    private BigDecimal price;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean sold;

    @Column(nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private boolean deleted;

    private LocalDateTime deletedAt;

    @ManyToOne
    @JoinColumn(name = "owner_id")
    private Person owner;

    public Product(CreateProductForm form) {
        this.productName = form.name();
        this.description = form.description();
        this.price = form.price();
    }

    public void updateAvailableAttributes(UpdateProductForm form) {
        this.productName = form.name() != null ? form.name()  : this.productName;
        this.description = form.description() != null ? form.description() : this.description;
        this.price = form.price() != null ? form.price() : this.price;
    }

    public void markAsDeleted() {
        this.deleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    public void markAsSold() {
        this.sold = true;
    }
}
