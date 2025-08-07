package com.legenkiy.dto;

import com.legenkiy.model.Product;
import com.legenkiy.model.cart.Cart;
import jakarta.validation.constraints.Min;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private Cart cart;
    private Product product;
    @Min(value = 1)
    private int quantity;
}
