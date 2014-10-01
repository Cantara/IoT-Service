package com.altran.iot;

import com.altran.iot.observation.Observation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;

/**
 * Created by totto on 9/30/14.
 */
public class SynapticsParsingTest {

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testSimplePostData() throws Exception {
        String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String jsonResult="{\"radioGatewayId\":\"001BC50C7100001E\",\"radioGatewayName\":\"001BC50C7100001E\",\"radioGatewayDescription\":\"001BC50C7100001E\",\"radioSensorId\":\"001BC50C7100001E\",\"radioSensorName\":\"001BC50C7100001E\",\"radioSensorDescription\":\"001BC50C7100001E\",\"timestampCreated\":\"1412099476264.7\",\"timestampReceived\":\"1412099476264.7\",\"measurements\":{\"SensorId5\":\"value5\",\"SensorId6\":\"value6\",\"SensorId2\":\"value2\",\"SensorId1\":\"value1\",\"SensorId4\":\"value4\",\"SensorId3\":\"value3\"}}";

        Observation observation = Observation.fromD7data(inputData);
        String r = observation.toString();
        String j=mapper.writeValueAsString(observation);
        System.out.println("Observation: "+r);
        System.out.println("Observation.json: "+j);
        assert(jsonResult.equalsIgnoreCase(j));


    }

}
