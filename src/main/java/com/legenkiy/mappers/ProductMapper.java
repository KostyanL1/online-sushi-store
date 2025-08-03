package com.legenkiy.mappers;


import com.legenkiy.dao.ProductDao;
import com.legenkiy.model.Product;
import org.springframework.stereotype.Component;

@Component
public class ProductMapper {

    public Product toEntity(ProductDao productDao, String imageUrl){
            Product product = new Product();
            product.setName(productDao.getName());
            product.setDescription(productDao.getDescription());
            product.setPrice(productDao.getPrice());
            product.setType(productDao.getType());
            product.setImageUrl(imageUrl);
            product.setIsAvailable(productDao.isAvailable());
            return product;

    }

}
