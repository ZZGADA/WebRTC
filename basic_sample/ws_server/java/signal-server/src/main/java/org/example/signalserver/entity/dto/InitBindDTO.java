package org.example.signalserver.entity.dto;

import lombok.Data;

@Data
public class InitBindDTO {
    private  String type;
    private BindDTO data;
}
