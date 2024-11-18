package org.example.signalserver.controller;

import lombok.extern.slf4j.Slf4j;
import org.example.signalserver.entity.vo.HealthVO;
import org.example.signalserver.utils.WebResp;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
@RequestMapping("signalServer")
@CrossOrigin
public class HealthController {

    /**
     * check health 健康检测
     * @return HealthVO
     */
    @GetMapping("/checkHealth")
    public WebResp<HealthVO>  healthController() {
        log.info("Health check");
        HealthVO healthVO = new HealthVO(200);
        return WebResp.Success(healthVO,"");
    }
}
