package com.altran.iot;


import com.altran.iot.observation.ObservedMethod;

import java.util.List;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
public interface WriteOperations {
    long addObservations(String prefix,List<ObservedMethod> observedMethods);
}
