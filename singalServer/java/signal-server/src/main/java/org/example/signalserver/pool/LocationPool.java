package org.example.signalserver.pool;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.po.SpecificLocationPO;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
public class LocationPool {
    /**
     * 保存连接 sessionId 和 教室-教学楼-教室三级关系的地方
     * key: session id
     * value: SpecificLocationPO
     */
    public Map<String, SpecificLocationPO> pools;

    public LocationPool(ConcurrentHashMap<String, SpecificLocationPO> poolInstance) {
        pools = poolInstance;
    }
}
