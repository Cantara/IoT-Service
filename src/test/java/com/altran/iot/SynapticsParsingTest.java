package com.altran.iot;

import com.altran.iot.observation.Observation;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by totto on 9/30/14.
 */
public class SynapticsParsingTest {

    private final ObjectMapper mapper = new ObjectMapper();


    @Test
    public void testSimplePostData() throws Exception {
        //String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String inputData = "{\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String jsonResult="{\"radioGatewayId\":null,\"radioGatewayName\":null,\"radioGatewayDescription\":null,\"radioSensorId\":null,\"radioSensorName\":\"001BC50C71000017\",\"radioSensorDescription\":null,\"timestampCreated\":\"1.4121011189986E12\",\"timestampReceived\":\"1.4121011190026E12\",\"measurements\":{\"sn\":\"24\",\"rt\":\"0\",\"lb\":\"77\",\"lig\":\"428\"}}";

        Observation observation = Observation.fromD7data(inputData);
        String r = observation.toString();


    }


    @Test
    public void testCreateObservationFromJson(){
        String jsonData="{  \n" +
                "   \"radioGatewayId\":null,\n" +
                "   \"radioGatewayName\":null,\n" +
                "   \"radioGatewayDescription\":null,\n" +
                "   \"radioSensorId\":null,\n" +
                "   \"radioSensorName\":\"001BC50C71000017\",\n" +
                "   \"radioSensorDescription\":null,\n" +
                "   \"timestampCreated\":\"1.4121011189986E12\",\n" +
                "   \"timestampReceived\":\"1.4121011190026E12\",\n" +
                "   \"measurements\":{  \n" +
                "      \"sn\":\"24\",\n" +
                "      \"rt\":\"0\",\n" +
                "      \"lb\":\"77\",\n" +
                "      \"lig\":\"428\"\n" +
                "   }\n" +
                "}\n" +
                "\n";
        Observation o = Observation.fromLucene("","",jsonData);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
        Map measurements = JsonPath.read(document, "$.measurements");
        String timestampReceived = JsonPath.read(document, "$.timestampReceived");
        String timestampCreated = JsonPath.read(document, "$.timestampCreated");

    }

    @Test
    public void testSplitAndParse() throws Exception {
        //String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String inputData = "{\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
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

    @Test
    public void testSensorData2() {
        String inputData = "{\"ts\":1412231188318.1,\"data\":{\"001BC50C71000017\":{\"ts\":1412230901167.9,\"sn\":8,\"\n" +
                "lb\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"0\n" +
                "01BC50C71000019\",\"ts\":1412231188314,\"tmp\":25,\"sn\":52,\"lb\":89,\"lig\":2749,\"rt\":0,\n" +
                "\"hum\":39}},\"now\":1412231188865.8}192.168.1.142";


        Object document = Configuration.defaultConfiguration().jsonProvider().parse(inputData);

        System.out.println((Double) JsonPath.read(document, "$.ts"));
        System.out.println((Map) JsonPath.read(document, "$.data"));
        Map map = (Map) JsonPath.read(document, "$.data");
        Map map2 = (Map) map.get("001BC50C71000017");
        //System.out.println("lig:{}",Double.toString(map2.get("lig")));
        Observation observation = Observation.fromD7data(inputData);

        System.out.printf("Observation:{}" + observation.toJsonString());
        assert (true);
    }

    @Test
    public void testSensorData3() {
        String inputData = "{\"ts\":1412231999149,\"data\":{\"001BC50C71000017\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412232202945.3}192.168.1.142";
        Object document = Configuration.defaultConfiguration().jsonProvider().parse(inputData);

        System.out.println((Map) JsonPath.read(document, "$.data"));

        Map observations = (Map) JsonPath.read(document, "$.data");
        for (Object key : observations.keySet()) {
            System.out.println("\n\nRadioSensor = " + key);
            // System.out.println("Sensorvalues = " + observations.get(key));
            Map sensorvalues = (Map) observations.get(key);
            for (Object sensortype : sensorvalues.keySet()) {
                System.out.print("Sensortype =" + sensortype);
                System.out.println("  Sensorreading =" + sensorvalues.get(sensortype));
            }
        }
        List<Observation> res = Observation.fromD7Data(inputData);

    }

    @Test
    public void testSensorData4() {
        String inputData = "{\"ts\":1412231999149,\"data\":{\"001BC50C71000017\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}192.168.1.142";
        List<Observation> res = Observation.fromD7Data(inputData);

    }

    @Test
    public void testMappingFromLucene() {
        String inputData = "{observation={RadioGatewayId=192.168.1.142, RadioGatewayName=null, RadioGatewayDescription=null, RadioSensorId=001BC50C71000017, RadioSensorName=null, RadioSensorDescription=null, TimestampCreated=1412231999149, TimestampReceived=1412231999149, Measurements={uid=001BC50C71000017, sn=8, ts=1.4122309011679E12, rt=0, lb=91, lig=2676}}}}";

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(inputData);

        System.out.println(JsonPath.read(document, "$..TimestampCreated[0]"));
    }

}


