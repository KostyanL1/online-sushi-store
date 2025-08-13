package com.legenkiy.service.impl;


import com.legenkiy.dto.ProductTypeDto;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.model.Product;
import com.legenkiy.model.ProductType;
import com.legenkiy.repository.ProductTypeRepository;
import com.legenkiy.service.api.ProductTypeService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;


@ExtendWith(MockitoExtension.class)
public class ProductTypeServiceImplTest {

    @Mock
    private ProductTypeRepository productTypeRepository;

    @InjectMocks
    private ProductTypeServiceImpl productTypeService;


    @Test
    void shouldReturnAllTypes_findAll(){
        productTypeService.findAll();
        Mockito.verify(productTypeRepository).findAll();
    }
    @Test
    void shouldSaveType_save_whenTypeIsCorrect(){

        ProductTypeDto productTypeDto = new ProductTypeDto(
                "test dto"
        );
        productTypeService.save(productTypeDto);
        ArgumentCaptor<ProductType> argumentCaptor = ArgumentCaptor.forClass(ProductType.class);
        Mockito.verify(productTypeRepository).save(argumentCaptor.capture());
        ProductType savedProductType = argumentCaptor.getValue();
        Assertions.assertEquals(productTypeDto.getDescription(), savedProductType.getDescription());

    }
    @Test
    void shouldThrow_save_whenTypeNotCorrect(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> productTypeService.save(null));
    }

    @Test
    void shouldDeleteType_deleteById_whenTypeIdIsCorrect(){

        Mockito.when(productTypeRepository.existsById(1)).thenReturn(true);
        productTypeService.deleteById(1);
        Mockito.verify(productTypeRepository).deleteById(1);

    }

    @Test
    void shouldThrow_deleteById_whenIdNotCorrect(){
        Mockito.when(productTypeRepository.existsById(1)).thenReturn(false);
        Assertions.assertThrows(ObjectNotFoundException.class, () -> productTypeService.deleteById(1));
    }





}
