package com.enviro.assessment.grade001.tebogomkhize.investments.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.assessment.grade001.tebogomkhize.investments.domain.Product;


public interface ProductRepository extends JpaRepository<Product, String> {
    List<Product> findAllByInvestorInvestorID(String investorID);

    Product findByProductID(String productID);
}


