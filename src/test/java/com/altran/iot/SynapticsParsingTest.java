package com.altran.iot;

import com.altran.iot.observation.Observation;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.Map;

/**
 * Created by totto on 9/30/14.
 */
public class SynapticsParsingTest {

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testSimplePostData() throws Exception {
        String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String jsonResult="{\"radioGatewayId\":null,\"radioGatewayName\":null,\"radioGatewayDescription\":null,\"radioSensorId\":null,\"radioSensorName\":\"001BC50C71000017\",\"radioSensorDescription\":null,\"timestampCreated\":\"1.4121011189986E12\",\"timestampReceived\":\"1.4121011190026E12\",\"measurements\":{\"sn\":\"24\",\"rt\":\"0\",\"lb\":\"77\",\"lig\":\"428\"}}";

        Observation observation = Observation.fromD7data(inputData);
        String r = observation.toString();
        String j=mapper.writeValueAsString(observation);
        System.out.println("Observation: " + r);
        System.out.println("Observation.json: " + j);
        assert(jsonResult.equalsIgnoreCase(j));


    }

    @Test
    public void testSplitAndParse() throws Exception {
        String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        int startIdx =inputData.indexOf("{");
        int endIdx =inputData.lastIndexOf(",");
        String json = inputData.substring(startIdx,endIdx)+"}";
        System.out.println(json);
        JSONParser jsonParser = new JSONParser();
        JSONObject jsonObject = (JSONObject) jsonParser.parse(json);

        Double ts = (Double) jsonObject.get("ts");
        JSONObject structure = (JSONObject) jsonObject.get("data");
        String sensorID=(String)structure.keySet().toArray()[0];
        JSONObject readings = (JSONObject)structure.values().toArray()[0];

        Long sn = (Long)readings.get("sn");
        String uid = (String)readings.get("uid");
        Long rt = (Long)readings.get("rt");
        Long lb = (Long)readings.get("lb");
        Long lig = (Long)readings.get("lig");
        Double ts2 = (Double)readings.get("ts");


        System.out.println("Found timestamp: " + structure.get("lb"));



    }
}


