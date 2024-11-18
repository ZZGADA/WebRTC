package org.example.signalserver.config;

import org.example.signalserver.pool.BuildingPool;
import org.example.signalserver.pool.ClassRoomPool;
import org.example.signalserver.pool.SchoolPool;
import org.example.signalserver.pool.LocationPool;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.ConcurrentHashMap;

@Configuration
public class WsPoolConfig {

    /**
     * 注册一个单例bean wsPool
     */
    @Bean
    public LocationPool wsPool() {
        return new LocationPool(new ConcurrentHashMap<>());
    }

    @Bean
    public SchoolPool schoolPool() {
        return new SchoolPool(new ConcurrentHashMap<>());
    }

    @Bean
    public BuildingPool buildingPool(){
        return new BuildingPool(new ConcurrentHashMap<>());
    }

    @Bean
    public ClassRoomPool classRoomPool() {
        return new ClassRoomPool(new ConcurrentHashMap<>());
    }

}
