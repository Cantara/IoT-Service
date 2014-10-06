package com.altran.iot.infrastructure;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public interface InfrastructureRepository {

    public List<String> listAllRadioGatewayIds();
    public List<String> listAllRadioSensorIds();
}
