package org.example.signalserver.pool.layer;

import lombok.Data;
import org.example.signalserver.entity.po.ClassRoomPO;


import java.util.Set;


@Data
public class BuildingLayer {
    private Integer buildingId;
    Set<Integer> classRoomIdSet;
}
