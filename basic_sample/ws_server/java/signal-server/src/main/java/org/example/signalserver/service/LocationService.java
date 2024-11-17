package org.example.signalserver.service;

import org.example.signalserver.entity.po.SpecificLocationPO;

public interface LocationService {
    SpecificLocationPO getSpecificLocationBySessionId(String sessionId);
    void saveSpecificLocation(String sessionId,SpecificLocationPO specificLocation);
}
