package org.example.signalserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.pool.BuildingPool;
import org.example.signalserver.pool.ClassRoomPool;
import org.example.signalserver.pool.LocationPool;
import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.service.WebSocketService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.Map;

@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    /**
     * 保存连接 session 的地方
     */
    @Autowired
    public LocationPool locationPool;

    @Autowired
    public SchoolPoolServiceImpl schoolPoolServiceImpl;

    @Autowired
    public BuildingPool buildingPool;;

    @Autowired
    public ClassRoomPool classRoomPool;



    /**
     * 添加 session
     */
    public void add(WebSocketSession socketSession) {

        
    }

    /**
     * 删除 session,会返回删除的 session
     */
    public WebSocketSession remove(String sessionId) {
        // 删除 session
//        return wsPool.poolSessionIdToLocation.remove(sessionId);
        return null;
    }

    /**
     * 删除并同步关闭连接
     */
    public void removeAndClose(String key) {
        WebSocketSession session = this.remove(key);
        if (session != null) {
            try {
                // 关闭连接
                session.close();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
    }


    /**
     * 获得 session
     */
    public WebSocketSession get(String sessionId) {
        SpecificLocationPO specificLocationPO = locationPool.poolSessionIdToLocation.get(sessionId);
        schoolPoolServiceImpl.getSchoolLayerById()



        return null;
    }


    /**
     * 获取当前教室连接的所有WebSocket客户端
     * 根据校区->教室->学生做区分
     */
    @Override
    public Map<String, WebSocketSession> getSchoolClassRoomAllSession() {

        return null;
    }

    @Override
    public void add(String key, WebSocketSession session) {

    }
}
