package org.example.signalserver.service;

public interface BuildingPoolService extends PoolService {
    boolean ifHasBuilding(int buildingId);
    void initBuildingLayer(int buildingId);
    void bindClassRoomId(int buildingId, int classRoomId);
}
