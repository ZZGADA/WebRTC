package org.example.signalserver.entity.po;

import lombok.Data;

import java.util.Map;

@Data
public class BuildingPO {
    private Integer buildingId;
    Map<Integer,ClassRoomPO> rooms;
}
