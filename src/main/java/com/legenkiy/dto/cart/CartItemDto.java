package com.legenkiy.dto.cart;

import com.legenkiy.dto.ProductDto;
import com.legenkiy.model.Product;
import com.legenkiy.model.cart.Cart;
import jakarta.validation.constraints.Min;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class CartItemDto {
    private CartDto cartDto;
    private ProductDto productDto;
    @Min(value = 1)
    private int quantity;
}
