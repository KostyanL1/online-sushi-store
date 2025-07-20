package com.legenkiy.repository;

import com.legenkiy.model.Person;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface PersonRepository extends JpaRepository<Person, Integer> {

    Optional<Person> findByCellNumber(String cellNumber);

}
