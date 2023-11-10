package com.example.tools.repository;

public interface ChangeInterfaceIdRepository {
    void updateSetting(String interfaceId, String waterId);
    void updateL3(String interfaceId, String waterId);
    void update105L3(String interfaceId, String waterId);
    void update106L3(String interfaceId, String waterId);
    void update107L3(String interfaceId, String waterId);
    void update108L3(String interfaceId, String waterId);
    void update109L3(String interfaceId, String waterId);
    void update110L3(String interfaceId, String waterId);
    void updateOthers(String interfaceId, String waterId);
    void updateMtrreadFtr(String interfaceId, String waterId);
}
