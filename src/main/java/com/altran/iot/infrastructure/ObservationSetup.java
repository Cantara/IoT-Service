package com.altran.iot.infrastructure;

import java.util.HashMap;
import java.util.Map;


/**
 * Simple representation of the observed infrastructure
 */


public class ObservationSetup {
    private Map<String, String> radioGatewayIds = new HashMap();
    private Map<String, String> radioSensorIds = new HashMap<>();

    public int getNumOfMeasurements() {
        return numOfMeasurements;
    }

    public void setNumOfMeasurements(int numOfMeasurements) {
        this.numOfMeasurements = numOfMeasurements;
    }

    private int numOfMeasurements;


    public void addRadioGatewayID(String radiogatewayid) {
        String count = radioGatewayIds.get(radiogatewayid);
        if (count == null) {
            radioGatewayIds.put(radiogatewayid, "1");
        } else {
            radioGatewayIds.put(radiogatewayid, Integer.toString(Integer.parseInt(count) + 1));
        }
    }

    public void addRadioSensorID(String radiosensorid) {
        String count = radioSensorIds.get(radiosensorid);
        if (count == null) {
            radioSensorIds.put(radiosensorid, "1");
        } else {
            radioSensorIds.put(radiosensorid, Integer.toString(Integer.parseInt(count) + 1));
        }
    }

    public Map<String, String> getRadioGatewayIds() {
        return radioGatewayIds;
    }

    public void setRadioGatewayIds(Map<String, String> radioGatewayIds) {
        this.radioGatewayIds = radioGatewayIds;
    }

    public Map<String, String> getRadioSensorIds() {
        return radioSensorIds;
    }

    public void setRadioSensorIds(Map<String, String> radioSensorIds) {
        this.radioSensorIds = radioSensorIds;
    }

    @Override
    public String toString() {
        return "ObservationSetup{" +
                "radiogatewayids=" + radioGatewayIds +
                ", radiosensorids=" + radioSensorIds +
                '}';
    }


}
