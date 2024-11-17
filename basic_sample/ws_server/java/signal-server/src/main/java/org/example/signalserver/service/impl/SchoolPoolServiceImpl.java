package org.example.signalserver.service.impl;

import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.pool.SchoolPool;
import org.example.signalserver.pool.layer.SchoolLayer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;



@Service
public class SchoolPoolServiceImpl {
    @Autowired
    private SchoolPool schoolPool;


    SchoolPoolServiceImpl(){}


    /**
     * 根据学校id找schoolLayer
     */
    public SchoolLayer get(WebSocketSession session, SpecificLocationPO locationPO){

        return null;
    }


    /**
     * 将
     * @param session
     * @param locationPO
     * @return
     */
    public boolean putSchoolIntoPool(WebSocketSession session, SpecificLocationPO locationPO){
        schoolPool.pools.put()

        return true;
    }
}
