package com.legenkiy.service;


import com.legenkiy.dao.ProductDao;
import com.legenkiy.exceprions.ImageNotFoundException;
import com.legenkiy.exceprions.ProductNotFoundException;
import com.legenkiy.mappers.ProductMapper;
import com.legenkiy.model.Product;
import com.legenkiy.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.security.PrivilegedActionException;
import java.util.List;


@RequiredArgsConstructor
@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final CloudService cloudService;
    private final ProductMapper productMapper;

    public Product findById(int id) {
        return productRepository.findById(id).orElseThrow(
                () -> new ProductNotFoundException("Product with id:" + id + " not found!"));
    }

    public List<Product> findAll(){
        return productRepository.findAll();
    }

    public void validateExistsById(int id){
        if (!productRepository.existsById(id))
            throw new ProductNotFoundException("Product with id:" + id + " doesn`t exist!");
    }


    public void save(ProductDao productDao) {
        if (productDao == null) throw new IllegalArgumentException("Product must not be null!");
        if (productDao.getImage() == null || productDao.getImage().isEmpty()) throw new ImageNotFoundException("Image must be present!");
        String url = cloudService.upload(productDao.getImage(), productDao.getName());
         Product product = productMapper.toEntity(productDao, url);
        productRepository.save(product);
    }

    public void deleteById(int id) throws ProductNotFoundException {
        validateExistsById(id);
        productRepository.deleteById(id);
    }

    public void update(int id, ProductDao updatedProductDao) throws ProductNotFoundException {
        if (updatedProductDao == null) {
            throw new IllegalArgumentException("Product must not be null!");
        }
        Product existingProduct = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException("Product with id " + id + " not found"));
        existingProduct.setName(updatedProductDao.getName());
        existingProduct.setDescription(updatedProductDao.getDescription());
        existingProduct.setPrice(updatedProductDao.getPrice());
        existingProduct.setType(updatedProductDao.getType());
        existingProduct.setIsAvailable(updatedProductDao.isAvailable());
        MultipartFile newPhoto = updatedProductDao.getImage();
        if (newPhoto != null && !newPhoto.isEmpty()) {
            String newImageUrl = cloudService.upload(newPhoto, updatedProductDao.getName());
            existingProduct.setImageUrl(newImageUrl);
        }
        productRepository.save(existingProduct);
    }

}
