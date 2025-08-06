package com.legenkiy.mappers;


import com.legenkiy.dto.ProductDto;

import com.legenkiy.model.Product;
import com.legenkiy.model.ProductType;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


// should_NameOfMethod_when

public class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();


    @Test
    public void shouldMap_toEntity_whenProductDaoIsCorrect(){
        ProductDto productDto = new ProductDto(
                "Rolls", "Better", 255.0, new ProductType(1, "Rolls"),
                null, true
        );

        String url = "test.url";
        Product product = this.productMapper.toEntity(productDto, url);
        Assertions.assertEquals(product.getName(), productDto.getName());
        Assertions.assertEquals(product.getDescription(), productDto.getDescription());
        Assertions.assertEquals(product.getProductType(), productDto.getProductType());
        Assertions.assertEquals(product.getPrice(), productDto.getPrice());
    }


}
