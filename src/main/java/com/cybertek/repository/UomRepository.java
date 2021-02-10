package com.cybertek.repository;

import com.cybertek.entity.Uom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UomRepository extends JpaRepository<Uom,Integer> {


    Optional<Uom> findByName(String name);
}
