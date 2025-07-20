package com.legenkiy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "product")
@NoArgsConstructor
@AllArgsConstructor
@Data

public class Product {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private int id;

    @Column(name = "name")
    @NotBlank(message = "Поле для назви продукту порожнє!")
    @Size(min = 1, max = 60, message = "Довжина назви продукту не може бути коротша ніж 1 та довша ніж 60!")
    private String name;

    @Column(name = "description")
    @NotBlank(message = "Поле для опису продукту порожнє!")
    @Size(min = 15, max = 300, message = "Довжина опису продукту не може бути коротша ніж 15 та довша ніж 300!")
    private String description;

    @NotNull(message = "Ціна товару не може бути порожньою!")
    @Min(value = 0, message = "Ціна товару не може бути від'ємною!")
    @Max(value = 5000, message = "Ціна товару не може бути більшою ніж 5000!")
    @Column(name = "price")
    private Double price;

    @ManyToOne
    @JoinColumn(name = "type_id")
    @NotNull(message = "Тип продукту не вказано!")
    private Type typeId;

    @NotBlank(message = "Поле для URL зображення порожнє!")
    @URL(message = "Невірний формат для URL!")
    @Column(name = "image_url")
    private String imageUrl;

    @NotNull(message = "Потрібно вказати, чи доступний товар!")
    @Column(name = "is_available")
    private Boolean isAvailable;

}