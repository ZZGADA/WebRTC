package org.example.signalserver.pool.layer;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

/**
 * 学校层： 一个学校记录下属所有的教学楼的id
 */
@Data
@AllArgsConstructor
public class SchoolLayer extends Layer {
    private Set<Integer> buildingIdSet;
}
