package com.legenkiy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "description")
    private String description;
    @Column(name = "price")
    private Double price;
    @ManyToOne
    @JoinColumn(name = "type_id")
    private ProductType productType;
    @Column(name = "image_url")
    private String imageUrl;
    @Column(name = "is_available")
    private Boolean isAvailable;

}