package com.cybertek.repository;

import com.cybertek.enums.Status;
import com.cybertek.entity.*;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ProductRepository extends JpaRepository<Product,Long> {

   List<Product> findAllByStatus(Status status);

   Optional<Product> findByName(String name);

   @Query(value = "SELECT * FROM products p JOIN product_sub_category_rel ps  " +
           "on p.id=product_sub_category_rel.product_id " +
           "WHERE ps.sub_category_id=?1" ,nativeQuery = true)
   List<Product> findAllBySubCategoryId(Integer subCategoryId);

   List<Product> findByUom(Uom uom);

   List<Product> findByCurrency(Currency foundedCurrency);
}
