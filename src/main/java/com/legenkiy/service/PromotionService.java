package com.legenkiy.service;

import com.legenkiy.dto.PromotionDto;
import com.legenkiy.exceptions.ImageNotFoundException;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.mappers.PromotionMapper;
import com.legenkiy.model.Promotion;
import com.legenkiy.repository.PromotionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.aop.target.LazyInitTargetSource;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionService {

    private final CloudService cloudService;
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;

    public List<Promotion> findAll(){
        return promotionRepository.findAll();
    }


    public Promotion findById(int id){
        return promotionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Promotion not found!"));
    }

    public void save(PromotionDto promotionDto){
        if (promotionDto == null)
            throw new IllegalArgumentException("Promotion must not be null!");
        if (promotionDto.getImage() == null || promotionDto.getImage().isEmpty())
            throw new ImageNotFoundException("Image must be present!");
        String url = cloudService.upload(promotionDto.getImage(), promotionDto.getTitle());
        Promotion promotion = promotionMapper.toEntity(promotionDto, url);
        promotionRepository.save(promotion);
    }
    public void update(int id, PromotionDto promotionDto){
        if (promotionDto == null) throw new IllegalArgumentException("Promotion must not be null!");
        Promotion promotion = findById(id);
        if (promotionDto.getImage() != null && !promotionDto.getImage().isEmpty()){
            String url = cloudService.upload(promotionDto.getImage(), promotionDto.getTitle());
            promotion.setTitle(promotionDto.getTitle());
            promotion.setDescription(promotionDto.getDescription());
            promotion.setImageUrl(url);
        }
        else {
            promotion.setTitle(promotionDto.getTitle());
            promotion.setDescription(promotionDto.getDescription());
        }
        promotionRepository.save(promotion);
    }
    public void deleteById(int id){
        if (promotionRepository.existsById(id)){
            promotionRepository.deleteById(id);
        }else throw new ObjectNotFoundException("Promotion not found!");

    }



}
