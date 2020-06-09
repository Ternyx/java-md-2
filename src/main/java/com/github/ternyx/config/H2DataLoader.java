package com.github.ternyx.config;

import com.github.ternyx.services.impl.HospitalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

/**
 * H2DataLoader
 */
@Component
public class H2DataLoader implements ApplicationRunner {
    
    @Autowired
    HospitalService hospitalService;


    @Override
    public void run(ApplicationArguments args) throws Exception {
        try {
            hospitalService.inputData();
            System.out.println("Databse data filled");
        } catch (Exception e) {
            System.out.println(e.getMessage());
            System.out.println("Database data was not prefilled successfully");
        }
    }
}
