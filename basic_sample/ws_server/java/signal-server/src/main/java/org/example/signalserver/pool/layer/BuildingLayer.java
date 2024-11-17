package org.example.signalserver.pool.layer;

import lombok.AllArgsConstructor;
import lombok.Data;


import java.util.Set;

@Data
@AllArgsConstructor
public class BuildingLayer extends Layer {
    private Set<Integer> classRoomIdSet;
}
