package org.example.signalserver.service.impl;

import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.pool.LocationPool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;


/**
 * WebSocketPoolServiceImpl 处理socket的连接
 * 在连接的逻辑上分为学校、教学楼、教室三层
 * 对外暴露 add get remove 操作
 */
@Service
public class WebSocketPoolServiceImpl {
    @Autowired
    public LocationPool locationPool;

    @Autowired
    public SchoolPoolServiceImpl schoolPoolServiceImpl;

    /**
     * session 对象 和 locationPO
     * session记录websocket的连接信息
     * locationPO 记录客户端的逻辑信息
     *
     */
    public void add(WebSocketSession session, SpecificLocationPO locationPO) {
        this.locationPool.pools.put(session.getId(), locationPO);
        schoolPoolServiceImpl.putSchoolIntoPool(session, locationPO);
    }

}
