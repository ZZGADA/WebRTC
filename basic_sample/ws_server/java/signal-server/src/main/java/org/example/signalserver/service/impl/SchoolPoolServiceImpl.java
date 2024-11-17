package org.example.signalserver.service.impl;

import org.example.signalserver.pool.SchoolPool;
import org.example.signalserver.pool.layer.Layer;
import org.example.signalserver.pool.layer.SchoolLayer;
import org.example.signalserver.service.SchoolPoolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;


@Service
public class SchoolPoolServiceImpl implements SchoolPoolService {
    @Autowired
    private SchoolPool schoolPool;

    /**
     * 学校是否存在
     */
    @Override
    public boolean ifHasSchool(int schoolId) {
        return schoolPool.pools.containsKey(schoolId);
    }

    @Override
    public void initSchoolLayer(int schoolId) {
        if(!ifHasSchool(schoolId)) {
            // 如果没有
            synchronized (this){
                // 双重确认
                if(!ifHasSchool(schoolId)) {
                    SchoolLayer schoolLayer = new SchoolLayer(new HashSet<>());
                    schoolPool.pools.put(schoolId, schoolLayer);
                }
            }
        }
    }


    /**
     * 加入教学楼id
     */
    @Override
    public void bindBuildingId(int schoolId, int buildingId) {
        if(!ifHasSchool(schoolId)) {
            initSchoolLayer(schoolId);
        }

        SchoolLayer schoolLayer = (SchoolLayer) getLayerById(schoolId);
        schoolLayer.getBuildingIdSet().add(buildingId);
    }


    @Override
    public Layer getLayerById(Integer id) {
        return this.schoolPool.pools.get(id);
    }
}
