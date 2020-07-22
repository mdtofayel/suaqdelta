package com.happyride.eservice.service;

import com.happyride.eservice.entity.model.PostReport;
import com.happyride.eservice.repository.ReportRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReportService {
    @Autowired
    private ReportRepository reportRepository;

    public PostReport saveReport(PostReport postReport){
        return reportRepository.save(postReport);
    }
}
