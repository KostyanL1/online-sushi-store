package com.legenkiy.dto;

import com.legenkiy.model.enums.Role;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@AllArgsConstructor
@NoArgsConstructor
@Data
public class PersonDto {

    @NotBlank(message = "Name cannot be blank")
    @Size(min = 2, max = 50, message = "Name must be between 2 and 50 characters")
    private String name;

    @NotBlank(message = "Phone number cannot be blank")
    @Pattern(
            regexp = "^380[0-9]{9}$",
            message = "Phone number must be in the format 380XXXXXXXXX"
    )
    private String cellNumber;

    @NotNull(message = "Role must be provided")
    private Role role;

}
