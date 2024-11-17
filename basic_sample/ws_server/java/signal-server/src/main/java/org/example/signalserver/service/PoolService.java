package org.example.signalserver.service;

import org.example.signalserver.pool.layer.Layer;


public interface PoolService {
    /**
     * 根据id 获取当前层的layer
     * @param id
     * @return
     */
    Layer getLayerById(Integer id);
}
