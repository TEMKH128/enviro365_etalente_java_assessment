package com.enviro.assessment.grade001.tebogomkhize.investments.repositories;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import com.enviro.assessment.grade001.tebogomkhize.investments.domain.Investor;


@Repository
public interface InvestorRepository extends JpaRepository<Investor, String> {
}


