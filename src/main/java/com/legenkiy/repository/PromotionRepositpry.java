package com.legenkiy.repository;

import com.legenkiy.model.Promotion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface PromotionRepositpry extends JpaRepository<Promotion, Integer> {


}
