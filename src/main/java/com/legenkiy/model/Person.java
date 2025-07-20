package com.legenkiy.model;

import com.legenkiy.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "person")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class Person {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "name")
    private String name;
    @Column(name = "cell_number")
    @Pattern(
            regexp = "^380[0-9]{9}$",
            message = "Phone number must be in the format 380XXXXXXXXX"
    )
    private String cellNumber;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Column(name = "is_verified")
    private boolean isVerified;

    {
        this.role = Role.USER;
    }


}
