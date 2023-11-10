package com.example.tools.repository;

import com.example.tools.model.BctIdMapping;

import java.util.List;

public interface MdpfRepository {

    int insert1800SttnId(String sttnId);

    List<BctIdMapping> getBctIdMapping();
}
