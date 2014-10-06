package com.altran.iot.infrastructure;

import org.springframework.stereotype.Repository;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Repository
public class InfrastructureRepositoryInMemory implements InfrastructureRepository {
    @Override
    public List<String> listAllRadioGatewayIds() {
        //TODO return new ArrayList<>();
        throw new NotImplementedException();
    }

    @Override
    public List<String> listAllRadioSensorIds() {
        //TODO return new ArrayList<>();
        throw new NotImplementedException();
    }
}
