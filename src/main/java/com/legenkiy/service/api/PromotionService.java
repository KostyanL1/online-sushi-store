package com.legenkiy.service.api;

import com.legenkiy.dto.PromotionDto;
import com.legenkiy.model.Promotion;

import java.util.List;

public interface PromotionService {

    List<Promotion> findAll();
    Promotion findById(int id);
    void save(PromotionDto promotionDto);
    void update(int id, PromotionDto promotionDto);
    void deleteById(int id);

}
