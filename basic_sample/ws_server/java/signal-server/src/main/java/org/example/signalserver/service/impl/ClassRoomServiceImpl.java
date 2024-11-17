package org.example.signalserver.service.impl;

import org.example.signalserver.pool.ClassRoomPool;
import org.example.signalserver.pool.layer.BuildingLayer;
import org.example.signalserver.pool.layer.ClassRoomLayer;
import org.example.signalserver.pool.layer.Layer;
import org.example.signalserver.service.ClassRoomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.util.HashMap;
import java.util.HashSet;
import java.util.concurrent.ConcurrentHashMap;

@Service
public class ClassRoomServiceImpl implements ClassRoomService {
    @Autowired
    private ClassRoomPool classRoomPool;


    @Override
    public Layer getLayerById(Integer id) {
        return this.classRoomPool.getPools().getOrDefault(id,null);
    }

    @Override
    public boolean ifHasClassRoom(int classRoomId){
        return classRoomPool.getPools().containsKey(classRoomId);
    }

    @Override
    public void initClassRoomLayer(int classRoomId){
        if(!ifHasClassRoom(classRoomId)) {
            // 如果没有
            synchronized (this){
                // 双重确认
                if(!ifHasClassRoom(classRoomId)) {
                    ClassRoomLayer classRoomLayer = new ClassRoomLayer(new ConcurrentHashMap<>());
                    classRoomPool.getPools().put(classRoomId,classRoomLayer);
                }
            }
        }
    }

    /**
     * 关键一步 将session信息插入到ClassRoom layer中的SessionMap中
     */
    @Override
    public void bindWebSocketSession(int classRoomId,String sessionId, WebSocketSession session) {
        if(!ifHasClassRoom(classRoomId)) {
            initClassRoomLayer(classRoomId);
        }

        ClassRoomLayer classRoomLayer =(ClassRoomLayer)getLayerById(classRoomId);
        classRoomLayer.getSessionMap().put(sessionId,session);
    }
}
