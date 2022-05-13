package com.example.tools.service;

public interface NewPositionBCTService {
    boolean checkIfExistOrNot(String bct_number);
    int insert(String bct_number, String position_case, String position_batch);
}
