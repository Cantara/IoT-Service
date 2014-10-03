package com.altran.iot.infrastructure;

import com.altran.iot.observation.Observation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Service
public class InfrastructureService {
    private static final Logger log = LoggerFactory.getLogger(InfrastructureService.class);

    @Autowired
    public InfrastructureService() {
    }



    public void updateSensorInfrastructure(Observation observation) {

    }
}
