package org.example.signalserver.pool;


import org.example.signalserver.pool.layer.ClassRoomLayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 教室池：记录所有的教室id和ClassRoomLayer
 * 通过教室ID找到最终的ClassRoomLayer
 *
 * ClassRoomLayer: 存了教室ID和当前教室下的所有WebSocketSession
 * key：教室id
 * value：ClassRoomLayer
 */
public class ClassRoomPool {
    Map<Integer, ClassRoomLayer> pools;

    public ClassRoomPool(ConcurrentHashMap<Integer, ClassRoomLayer> classroomPoolInstance) {
        this.pools = classroomPoolInstance;
    }
}
