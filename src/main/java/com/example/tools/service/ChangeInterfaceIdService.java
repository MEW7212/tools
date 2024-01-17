package com.example.tools.service;

import java.util.List;

public interface ChangeInterfaceIdService {
    List<String> getAllTablesWithInterfaceId();

    void updateTables(String table, String oldInterfaceId, String newInterfaceId);
}
