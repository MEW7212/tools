package com.example.tools.service.impl;

import com.example.tools.repository.NewPositionBCTRepository;
import com.example.tools.service.NewPositionBCTService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Log4j2
@Service
public class NewPositionBCTServiceI implements NewPositionBCTService {
    @Autowired
    NewPositionBCTRepository newPositionBCTRepository;

    @Override
    public boolean checkIfExistOrNot(String bct_number) {
        try {
            List<String> bct_numbers = newPositionBCTRepository.checkIfExistOrNot(bct_number);
            if (!bct_numbers.isEmpty()) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public int insert(String bct_number, String position_case, String position_batch) {
        try {
            return newPositionBCTRepository.insert(bct_number, position_case, position_batch);
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
    }
}
