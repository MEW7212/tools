package com.example.tools.service;

public interface MdpfService {

    void insert1800SttnId(String sttnId);

    void setMapFromBctIdMapping();

    String getBctNumber(String meterNum);
}
