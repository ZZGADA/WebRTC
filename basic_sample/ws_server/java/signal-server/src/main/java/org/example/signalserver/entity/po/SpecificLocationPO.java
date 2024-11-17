package org.example.signalserver.entity.po;

import lombok.Data;

/**
 * SpecificLocationPO 含有校区->教学楼->教室三层信息（三个id）
 */
@Data
public class SpecificLocationPO {
    private Integer schoolId;
    private Integer buildingId;
    private Integer classRoomId;
}
