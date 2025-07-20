package com.legenkiy.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "type")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Type {

    @Id
    @Column(name = "id")
    private  int id;

    @Column(name = "type")
    @Size(min = 1, max = 20 , message = "Довжина типу продукту не може бути коротша ніж 1 та довша ніж 20!")
    private String type;


}
