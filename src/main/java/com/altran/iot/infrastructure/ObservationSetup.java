package com.altran.iot.infrastructure;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;


/**
 * Simple representation of the observed infrastructure
 */


public class ObservationSetup {
    @Override
    public String toString() {
        return "ObservationSetup{" +
                "radiogatewayids=" + radiogatewayids +
                ", radiosensorids=" + radiosensorids +
                '}';
    }

    private Map<String, String> radiogatewayids = new HashMap();
    private Map<String, String> radiosensorids = new HashMap<>();

    public int getNumOfMeasurements() {
        return numOfMeasurements;
    }

    public void setNumOfMeasurements(int numOfMeasurements) {
        this.numOfMeasurements = numOfMeasurements;
    }

    private int numOfMeasurements;


    public void addRadioGatewayID(String radiogatewayid) {
        if (radiogatewayids.get(radiogatewayid) == null) {
            radiogatewayids.put(radiogatewayid, radiogatewayid);
        }
    }

    public void addRadioSensorID(String radiosensorid) {
        if (radiosensorids.get(radiosensorid) == null) {
            radiosensorids.put(radiosensorid, radiosensorid);
        }
    }

    public Map<String, String> getRadiogatewayids() {
        return radiogatewayids;
    }

    public void setRadiogatewayids(Map<String, String> radiogatewayids) {
        this.radiogatewayids = radiogatewayids;
    }

    public Map<String, String> getRadiosensorids() {
        return radiosensorids;
    }

    public void setRadiosensorids(Map<String, String> radiosensorids) {
        this.radiosensorids = radiosensorids;
    }


}
