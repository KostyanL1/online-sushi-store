package com.legenkiy.service.impl;

import com.legenkiy.dto.PromotionDto;
import com.legenkiy.exceptions.ImageNotFoundException;
import com.legenkiy.exceptions.ObjectNotFoundException;
import com.legenkiy.mappers.PromotionMapper;
import com.legenkiy.model.Promotion;
import com.legenkiy.repository.PromotionRepository;
import com.legenkiy.service.api.PromotionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PromotionServiceImpl implements PromotionService {

    private final CloudServiceImpl cloudServiceImpl;
    private final PromotionRepository promotionRepository;
    private final PromotionMapper promotionMapper;


    @Override
    public List<Promotion> findAll(){
        return promotionRepository.findAll();
    }

    @Override
    public Promotion findById(int id){
        return promotionRepository.findById(id).orElseThrow(() -> new ObjectNotFoundException("Promotion not found!"));
    }
    @Override
    public void save(PromotionDto promotionDto){
        if (promotionDto == null)
            throw new IllegalArgumentException("Promotion must not be null!");
        if (promotionDto.getImage() == null || promotionDto.getImage().isEmpty())
            throw new ImageNotFoundException("Image must be present!");
        String url = cloudServiceImpl.upload(promotionDto.getImage(), promotionDto.getTitle());
        Promotion promotion = promotionMapper.toEntity(promotionDto, url);
        promotionRepository.save(promotion);
    }

    @Override
    public void update(int id, PromotionDto promotionDto){
        if (promotionDto == null) throw new IllegalArgumentException("Promotion must not be null!");
        Promotion promotion = findById(id);
        if (promotionDto.getImage() != null && !promotionDto.getImage().isEmpty()){
            String url = cloudServiceImpl.upload(promotionDto.getImage(), promotionDto.getTitle());
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
    @Override
    public void deleteById(int id){
        if (promotionRepository.existsById(id)){
            promotionRepository.deleteById(id);
        }else throw new ObjectNotFoundException("Promotion not found!");

    }



}
