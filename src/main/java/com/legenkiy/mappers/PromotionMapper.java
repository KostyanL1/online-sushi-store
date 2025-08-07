package com.legenkiy.mappers;

import com.legenkiy.dto.PromotionDto;
import com.legenkiy.model.Promotion;
import org.springframework.stereotype.Component;

@Component
public class PromotionMapper {

    public Promotion toEntity(PromotionDto promotionDto, String url){
        Promotion promotion = new Promotion();
        promotion.setTitle(promotionDto.getTitle());
        promotion.setDescription(promotionDto.getDescription());
        promotion.setImageUrl(url);
        return promotion;
    }
}
