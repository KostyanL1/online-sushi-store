package com.legenkiy.service;


import com.legenkiy.dto.PromotionDto;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.mappers.PromotionMapper;
import com.legenkiy.model.Promotion;
import com.legenkiy.repository.PromotionRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class PromotionServiceTest {


    @Mock
    private PromotionRepository promotionRepository;

    @Mock
    private CloudService cloudService;

    @Mock
    private PromotionMapper promotionMapper;

    @InjectMocks
    private PromotionService promotionService;



    private MultipartFile createMultipartFile() {
        return new MockMultipartFile("file", "file.png", "image/png", new byte[100]);
    }

    @Test
    void shouldSavePromotion_save_whenDtoIsValid() {
        MultipartFile multipartFile = createMultipartFile();
        PromotionDto promotionDto = new PromotionDto(
                "Promo title", "Promo description", multipartFile
        );
        String uploadedUrl = "https://cloudinary.com/sushi.jpg";
        Mockito.when(cloudService.upload(Mockito.any(), Mockito.anyString()))
                .thenReturn(uploadedUrl);
        Mockito.when(promotionMapper.toEntity(Mockito.any(), Mockito.eq(uploadedUrl)))
                .thenAnswer(invocation -> {
                    PromotionDto dto = invocation.getArgument(0);
                    Promotion promotion = new Promotion();
                    promotion.setTitle(dto.getTitle());
                    promotion.setDescription(dto.getDescription());
                    promotion.setImageUrl(uploadedUrl);
                    return promotion;
                });
        promotionService.save(promotionDto);
        ArgumentCaptor<Promotion> captor = ArgumentCaptor.forClass(Promotion.class);
        Mockito.verify(promotionRepository).save(captor.capture());
        Promotion saved = captor.getValue();

        Assertions.assertEquals(promotionDto.getTitle(), saved.getTitle());
        Assertions.assertEquals(promotionDto.getDescription(), saved.getDescription());
        Assertions.assertEquals(uploadedUrl, saved.getImageUrl());
    }

    @Test
    void shouldThrow_save_whenDtoIsNull() {
        Assertions.assertThrows(IllegalArgumentException.class, () -> promotionService.save(null));
    }

    @Test
    void shouldUpdate_update_whenPromotionExistsAndNewImageProvided() {
        int id = 1;
        Promotion oldPromotion = new Promotion(
                id, "Old title", "Old description", "old-image-url.jpg"
        );
        PromotionDto promotionDto = new PromotionDto(
                "New title", "New description", createMultipartFile()
        );
        String newImageUrl = "https://example.com/new-image.jpg";
        Mockito.when(promotionRepository.findById(id)).thenReturn(Optional.of(oldPromotion));
        Mockito.when(cloudService.upload(Mockito.any(), Mockito.anyString()))
                .thenReturn(newImageUrl);
        promotionService.update(id, promotionDto);
        Mockito.verify(promotionRepository).save(Mockito.argThat(
                savedPromotion ->
                        savedPromotion.getTitle().equals("New title") &&
                                savedPromotion.getDescription().equals("New description") &&
                                savedPromotion.getImageUrl().equals(newImageUrl)
        ));
    }

    @Test
    void shouldUpdate_update_whenPromotionExistsAndNoNewImageProvided() {
        int id = 1;
        Promotion oldPromotion = new Promotion(
                id, "Old title", "Old description", "old-image-url.jpg"
        );
        MultipartFile emptyFile = new MockMultipartFile("file", new byte[0]);
        PromotionDto promotionDto = new PromotionDto(
                "Updated title", "Updated description", emptyFile
        );
        Mockito.when(promotionRepository.findById(id)).thenReturn(Optional.of(oldPromotion));

        promotionService.update(id, promotionDto);
        Mockito.verify(promotionRepository).save(Mockito.argThat(
                savedPromotion ->
                        savedPromotion.getTitle().equals("Updated title") &&
                                savedPromotion.getDescription().equals("Updated description") &&
                                savedPromotion.getImageUrl().equals("old-image-url.jpg")
        ));
    }

    @Test
    void shouldThrow_update_whenMissingPromotion(){
        PromotionDto promotionDto =  new PromotionDto(
                "title", " description", createMultipartFile()
        );
        Mockito.when(promotionRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> promotionService.update(0, promotionDto));
    }

    @Test
    void shouldThrow_update_whenPromotionIsNull(){
        Assertions.assertThrows(IllegalArgumentException.class, () -> promotionService.update(0, null));
    }
    @Test
    void shouldReturnPromotion_findById_whenIdIsCorrect(){
        Promotion promotion = new Promotion(
                1, "title", "description", "image-url.jpg"
        );
        Mockito.when(promotionRepository.findById(1)).thenReturn(Optional.of(promotion));
        Promotion result = promotionService.findById(1);
        Assertions.assertEquals(promotion, result);
        Mockito.verify(promotionRepository).findById(1);
    }
    @Test
    void shouldThrow_findById_whenIdNotCorrect(){
        Mockito.when(promotionRepository.findById(0)).thenReturn(Optional.empty());
        Assertions.assertThrows(ObjectNotFoundException.class, () -> promotionService.findById(0));
    }
    @Test
    void shouldDelete_deleteById_whenIdIsCorrect(){
        Mockito.when(promotionRepository.existsById(1)).thenReturn(true);
        promotionService.deleteById(1);
        Mockito.verify(promotionRepository).deleteById(1);
    }
    @Test
    void shouldThrow_deleteById_whenIdNotCorrect(){
        Mockito.when(promotionRepository.existsById(0)).thenReturn(false);
        Assertions.assertThrows(ObjectNotFoundException.class, () ->  promotionService.deleteById(0));
    }

    @Test
    void shouldReturnAllPromotions_findAAll(){
        promotionService.findAll();
        Mockito.verify(promotionRepository).findAll();
    }









}


