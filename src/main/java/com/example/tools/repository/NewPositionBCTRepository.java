package com.example.tools.repository;

import java.util.List;

public interface NewPositionBCTRepository {
    List<String> checkIfExistOrNot(String bct_number);
    int insert(String bct_number, String position_case, String position_batch);
}
