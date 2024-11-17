package org.example.signalserver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.dto.InitBindDTO;
import org.example.signalserver.pool.BuildingPool;
import org.example.signalserver.pool.ClassRoomPool;
import org.example.signalserver.pool.LocationPool;
import org.example.signalserver.entity.po.SpecificLocationPO;
import org.example.signalserver.pool.layer.ClassRoomLayer;
import org.example.signalserver.pool.layer.Layer;
import org.example.signalserver.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.WebSocketSession;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

@Service
@Slf4j
public class WebSocketServiceImpl implements WebSocketService {

    /**
     * 保存连接 session 的地方
     */
    @Autowired
    private LocationService locationService;

    @Autowired
    private SchoolPoolService schoolPoolService;

    @Autowired
    private BuildingPoolService buildingPoolService;

    @Autowired
    private ClassRoomService classRoomService;

    /**
     * 外观模式 一个add操作封装了school building classroom三个add
     */
    @Override
    public void add(WebSocketSession session, InitBindDTO initBindDTO) {
        log.info("添加绑定 WebSocketSession 开始");
        int schoolId = initBindDTO.getData().getSchoolId();
        int buildingId = initBindDTO.getData().getBuildingId();
        int classRoomId = initBindDTO.getData().getClassRoomId();

        // 如果学校不存在
        if(!schoolPoolService.ifHasSchool(schoolId)){
            schoolPoolService.initSchoolLayer(schoolId);
        }
        // 学校绑定教学楼信息
        schoolPoolService.bindBuildingId(schoolId,buildingId);


        // 如果教学楼不存在
        if(!buildingPoolService.ifHasBuilding(buildingId)){
            buildingPoolService.initBuildingLayer(buildingId);
        }
        // 教学楼绑定
        buildingPoolService.bindClassRoomId(classRoomId,classRoomId);


        // 如果教室不存在
        if(!classRoomService.ifHasClassRoom(classRoomId)){
            classRoomService.initClassRoomLayer(classRoomId);
        }
        // 教室绑定socket session
        classRoomService.bindWebSocketSession(classRoomId,session.getId(),session);

        // 加载location
        SpecificLocationPO specificLocationPO = new SpecificLocationPO();
        specificLocationPO.setBuildingId(buildingId);
        specificLocationPO.setSchoolId(schoolId);
        specificLocationPO.setClassRoomId(classRoomId);

        locationService.saveSpecificLocation(session.getId(), specificLocationPO);

        log.info("添加绑定 WebSocketSession 成功");
    }

    /**
     * 删除 session,会返回删除的 session
     */
    public WebSocketSession remove(String sessionId) {
        // 删除 session
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



        return null;
    }

    /**
     * 获取全部
     * @param classRoomId 教室id
     * @return sessionId-session 1对1 关联的mao
     */
    @Override
    public Map<String, WebSocketSession> getClassRoomAllSessionByClassId(Integer classRoomId) {
        Layer layer = this.classRoomService.getLayerById(classRoomId);
        if(Objects.isNull(layer)) {
            return null;
        }
        ClassRoomLayer classRoomLayer = (ClassRoomLayer) layer;
        return new HashMap<>(classRoomLayer.getSessionMap());
    }
}
