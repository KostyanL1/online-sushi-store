package com.legenkiy.dto.cart;

import com.legenkiy.dto.PersonDto;
import com.legenkiy.model.Person;
import com.legenkiy.model.cart.CartItem;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartDto {

    private PersonDto person;

    private LocalDateTime localDateTime;

    private List<CartItemDto> cartItemsDto = new ArrayList<>();

}
