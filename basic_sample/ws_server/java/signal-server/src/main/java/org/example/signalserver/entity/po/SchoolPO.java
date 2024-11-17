package org.example.signalserver.entity.po;

import lombok.Data;

import java.util.Map;

@Data
public class SchoolPO {
    private Integer schoolId;
    Map<Integer,BuildingPO> buildings;
}
