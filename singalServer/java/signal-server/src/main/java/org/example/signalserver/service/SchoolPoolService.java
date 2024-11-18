package org.example.signalserver.service;

public interface SchoolPoolService extends PoolService{
    boolean ifHasSchool(int schoolId);
    void initSchoolLayer(int schoolId);
    void bindBuildingId(int schoolId, int buildingId);
}
