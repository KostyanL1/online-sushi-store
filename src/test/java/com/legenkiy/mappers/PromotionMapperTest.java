package com.legenkiy.mappers;


import com.legenkiy.dto.PromotionDto;
import com.legenkiy.model.Promotion;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.mock.web.MockMultipartFile;

public class PromotionMapperTest {

    private PromotionMapper promotionMapper;

    @BeforeEach
    void setUp(){
        this.promotionMapper = new PromotionMapper();
    }


    @Test
    void shouldMap_toEntity_whenPromotionIsCorrect(){
        PromotionDto promotionDto = new PromotionDto(
                "title",  "description", new MockMultipartFile(
                "file", "file.png", "image/png", new byte[100]
        )
        );
        String url = "test.url";
        Promotion promotion = promotionMapper.toEntity(promotionDto, url);
        Assertions.assertEquals(promotion.getTitle(), promotionDto.getTitle());
        Assertions.assertEquals(promotion.getDescription(), promotionDto.getDescription());
        Assertions.assertEquals(promotion.getImageUrl(), url);
    }
    @Test
    void shouldThrow_toEntity_whenPromotionIsNull(){
        Assertions.assertThrows(NullPointerException.class, () -> promotionMapper.toEntity(null, null));

    }






}
