package org.example.signalserver.pool.layer;

import lombok.Data;

import java.util.Set;

/**
 * 学校层： 一个学校记录下属所有的教学楼的id
 */
@Data
public class SchoolLayer {
    private Integer schoolId;
    Set<Integer> buildingIdSet;
}
