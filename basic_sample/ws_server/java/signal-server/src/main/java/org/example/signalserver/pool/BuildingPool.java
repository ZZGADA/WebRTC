package org.example.signalserver.pool;

import lombok.Data;
import org.example.signalserver.pool.layer.BuildingLayer;
import org.springframework.boot.autoconfigure.info.ProjectInfoProperties;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 教学楼池：记录所有教学楼和的id和BuildingLayer
 * 通过教学楼 找到 教学楼下面所有教室
 *
 * key：为教学楼id
 * value：为BuildingLayer
 */
@Data
public class BuildingPool {
    public Map<Integer, BuildingLayer> pools;

    public BuildingPool(ConcurrentHashMap<Integer, BuildingLayer> buildingPoolsInstance) {
        this.pools = buildingPoolsInstance;
    }
}
