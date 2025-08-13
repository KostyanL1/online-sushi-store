package com.legenkiy.service.api;

import com.legenkiy.dto.ProductTypeDto;
import com.legenkiy.model.ProductType;

import java.util.List;

public interface ProductTypeService {

    List<ProductType> findAll();
    void save(ProductTypeDto productTypeDto);
    void deleteById(int id);


}
