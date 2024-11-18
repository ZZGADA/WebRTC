package org.example.signalserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.pool.BuildingPool;
import org.example.signalserver.pool.layer.BuildingLayer;
import org.example.signalserver.pool.layer.Layer;
import org.example.signalserver.pool.layer.SchoolLayer;
import org.example.signalserver.service.BuildingPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;

@Service
@Slf4j
public class BuildingPoolServiceImpl implements BuildingPoolService {
    @Autowired
    private BuildingPool buildingPool;


    @Override
    public Layer getLayerById(Integer id) {
        return buildingPool.getPools().get(id);
    }


    @Override
    public boolean ifHasBuilding(int buildingId) {
        return buildingPool.getPools().containsKey(buildingId);
    }

    @Override
    public void initBuildingLayer(int buildingId) {
        if(!ifHasBuilding(buildingId)) {
            // 如果没有
            synchronized (this){
                // 双重确认
                if(!ifHasBuilding(buildingId)) {
                    BuildingLayer buildingLayer = new BuildingLayer(new HashSet<>());
                    buildingPool.pools.put(buildingId, buildingLayer);
                }
            }
        }
    }

    @Override
    public void bindClassRoomId(int buildingId, int classRoomId) {
        if(!ifHasBuilding(buildingId)) {
            initBuildingLayer(buildingId);
        }

        BuildingLayer buildingLayer =(BuildingLayer)getLayerById(buildingId);
        buildingLayer.getClassRoomIdSet().add(classRoomId);
    }

}
