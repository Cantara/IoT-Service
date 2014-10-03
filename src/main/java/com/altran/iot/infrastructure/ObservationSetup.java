package com.altran.iot.infrastructure;

import java.util.LinkedList;
import java.util.List;


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

    private List<String> radiogatewayids = new LinkedList<>();
    private List<String> radiosensorids = new LinkedList<>();

    public void addRadioGatewayID(String radiogatewayid) {
        radiogatewayids.add(radiogatewayid);
    }

    public void addRadioSensorID(String radiosensorid) {
        radiosensorids.add(radiosensorid);
    }

    public List<String> getRadiogatewayids() {
        return radiogatewayids;
    }

    public void setRadiogatewayids(List<String> radiogatewayids) {
        this.radiogatewayids = radiogatewayids;
    }

    public List<String> getRadiosensorids() {
        return radiosensorids;
    }

    public void setRadiosensorids(List<String> radiosensorids) {
        this.radiosensorids = radiosensorids;
    }


}
