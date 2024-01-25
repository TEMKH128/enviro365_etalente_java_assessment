package com.enviro.assessment.grade001.tebogomkhize.investments.repositories;

import java.util.List;
import java.time.LocalDate;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.assessment.grade001.tebogomkhize.investments.domain.Transaction;


public interface TransactionRepository extends JpaRepository<Transaction, String> {
    List<Transaction> findByProduct_ProductIDAndProduct_Investor_InvestorIDAndDateBetween(
        String productID, String investorID, LocalDate startDate, LocalDate endDate);
}