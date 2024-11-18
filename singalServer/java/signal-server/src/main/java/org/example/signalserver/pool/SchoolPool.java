package org.example.signalserver.pool;

import lombok.Data;
import org.example.signalserver.pool.layer.SchoolLayer;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


/**
 * 学校池：记录所有学校的id和一个SchoolLayer
 * 通过学校id找到学校下面的教学楼信息
 *
 * key：为学校id
 * value：SchoolLayer
 */
@Data
public class SchoolPool {
    public Map<Integer, SchoolLayer> pools;

    public SchoolPool(ConcurrentHashMap<Integer, SchoolLayer> schoolPoolsInstance) {
        this.pools = schoolPoolsInstance;
    }
}
