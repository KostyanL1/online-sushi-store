package com.legenkiy.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.URL;

@Entity
@Table(name = "promotions")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class Promotion {


    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "title")
    @NotBlank(message = "Поле для заголовку акції порожнє!")
    @Size(min = 3, max = 25, message = "Довжина заголовку не може бути коротша ніж 3 та довша ніж 25!")
    private String title;
    @Column(name = "description")
    @NotBlank(message = "Поле для опису акції порожнє!")
    @Size(min = 15, max = 300, message = "Довжина опису акції не може бути коротша ніж 15 та довша ніж 300!")
    private String description;
    @Column(name = "image_url")
    @URL(message = "Невірний формат для URL!")
    private String imageUrl;


}
