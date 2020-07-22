package com.happyride.eservice.repository;

import com.happyride.eservice.entity.model.PostReport;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ReportRepository extends JpaRepository<PostReport, Long> {
}
