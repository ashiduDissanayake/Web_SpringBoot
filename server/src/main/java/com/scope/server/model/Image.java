package com.scope.server.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private String imageUrl;
    private String imageId;

    // Many images can belong to one product
    @ManyToOne
    private Product product;  // Reference to the Product entity (foreign key)

    public Image(String name, String imageUrl, String imageId, Product product) {
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageId = imageId;
        this.product = product;
    }
}
