package com.altran.iot;


import com.altran.iot.observation.ObservedMethod;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public interface QueryOperations {
    List<ObservedMethod> findObservationsByName(String prefix, String name);
}
