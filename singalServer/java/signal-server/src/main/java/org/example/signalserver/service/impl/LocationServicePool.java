package org.example.signalserver.service.impl;

import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.pool.LocationPool;
import org.example.signalserver.service.LocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LocationServicePool implements LocationService {
    @Autowired
    private LocationPool locationPool;


    @Override
    public SpecificLocationPO getSpecificLocationBySessionId(String sessionId) {
        return locationPool.pools.getOrDefault(sessionId,null);
    }


    @Override
    public void saveSpecificLocation(String sessionId,SpecificLocationPO specificLocation) {
        this.locationPool.pools.put(sessionId,specificLocation);
    }
}
