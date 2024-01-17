package com.example.tools.repository;

import java.util.List;

public interface ChangeInterfaceIdRepository {
    List<String> getAllTablesWithInterfaceId();

    int updateTables(String table, String oldInterfaceId, String newInterfaceId);
}
