package com.legenkiy.mappers;


import com.legenkiy.dao.ProductDao;

import com.legenkiy.model.Product;
import com.legenkiy.model.Type;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;


// should_NameOfMethod_when

public class ProductMapperTest {

    private final ProductMapper productMapper = new ProductMapper();


    @Test
    public void shouldMap_toEntity_whenProductDaoIsCorrect(){
        ProductDao productDao = new ProductDao(
                "Rolls", "Better", 255.0, new Type(1, "Rolls"),
                null, true
        );

        String url = "test.url";
        Product product = this.productMapper.toEntity(productDao, url);
        Assertions.assertEquals(product.getName(), productDao.getName());
        Assertions.assertEquals(product.getDescription(), productDao.getDescription());
        Assertions.assertEquals(product.getType(), productDao.getType());
        Assertions.assertEquals(product.getPrice(), productDao.getPrice());
    }


}
