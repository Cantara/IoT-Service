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

        List<Observation> observations = Observation.fromD7Data(inputData);
        String r = observations.toString();


    }


    @Test
    public void testCreateObservationFromJson(){
        String jsonData = "{\n" +
                "   \"observation\":{  \n" +
                "      \"RadioGatewayId\":\"192.168.1.142\",\n" +
                "      \"RadioGatewayName\":\"null\",\n" +
                "      \"RadioGatewayDescription\":\"null\",\n" +
                "      \"RadioSensorId\":\"001BC50C7100001E\",\n" +
                "      \"RadioSensorName\":\"null\",\n" +
                "      \"RadioSensorDescription\":\"null\",\n" +
                "      \"TimestampCreated\":\"1.4122600381924E12\",\n" +
                "      \"TimestampReceived\":\"1.4122600381924E12\",\n" +
                "      \"Measurements\":  \n" +
                "         {\"uid\":\"001BC50C7100001E\",\"sn\":\"80\",\"ts\":\"1.4122600381885E12\",\"pre\":\"1023\",\"rt\":\"0\",\"lb\":\"45\",\"lig\":\"3987\"}                 }\n" +
                "}";
        Observation o = Observation.fromLucene("","",jsonData);

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(jsonData);
        Map measurements = JsonPath.read(document, "$.observation.Measurements");
        String timestampReceived = JsonPath.read(document, "$.observation.TimestampReceived");
        String timestampCreated = JsonPath.read(document, "$.observation.TimestampCreated");

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
        List<Observation> observations = Observation.fromD7Data(inputData);

        System.out.printf("Observation:{}" + observations);
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
    public void testSensorData5() {
        String inputData = "{\"ts\":1412266933014.9,\"data\":{\"001BC50C71000017\":{\"ts\":1412264622871.8,\"sn\":0,\"lb\":70,\"lig\":3937,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C7100001F\":{\"ts\":1412264781480.6,\"sn\":0,\"lb\":44,\"lig\":3882,\"rt\":0,\"uid\":\"001BC50C7100001F\"},\"001BC50C71000019\":{\"ts\":1412266933010.8,\"sn\":219,\"lb\":74,\"rt\":0,\"x\":-31,\"btn2\":1412264580802.2,\"btn1\":1412264580964.4,\"uid\":\"001BC50C71000019\",\"tmp\":24,\"z\":40,\"pre\":1023,\"lig\":3743,\"y\":-29,\"hum\":35}},\"now\":1412266933275.4}192.168.1.168";
        List<Observation> res = Observation.fromD7Data(inputData);
    }

    @Test
    public void testSensorData6() {
        String inputData = "{\"ts\":1412266891761.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412264622871.8,\"sn\":0,\"lb\":70,\"lig\":3937,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C710\n" +
                "0001F\":{\"ts\":1412264781480.6,\"sn\":0,\"lb\":44,\"lig\":3882,\"rt\":0,\"uid\":\"001BC50C7100001F\"},\"001BC50C71000019\":{\"ts\":1412266891757.6,\"sn\":231,\"lb\"\n" +
                ":74,\"rt\":0,\"x\":-31,\"btn2\":1412264580802.2,\"btn1\":1412264580964.4,\"uid\":\"001BC50C71000019\",\"tmp\":24,\"z\":40,\"pre\":1023,\"lig\":3772,\"y\":-29,\"hum\":\n" +
                "35}},\"now\":1412266891949.1}";
        List<Observation> res = Observation.fromD7Data(inputData);
    }

    @Test
    public void testMappingFromLucene() {
        String inputData = "{\n" +
                "   \"observation\":{  \n" +
                "      \"RadioGatewayId\":\"192.168.1.142\",\n" +
                "      \"RadioGatewayName\":\"null\",\n" +
                "      \"RadioGatewayDescription\":\"null\",\n" +
                "      \"RadioSensorId\":\"001BC50C7100001E\",\n" +
                "      \"RadioSensorName\":\"null\",\n" +
                "      \"RadioSensorDescription\":\"null\",\n" +
                "      \"TimestampCreated\":\"1.4122600283253E12\",\n" +
                "      \"TimestampReceived\":\"1.4122600283253E12\",\n" +
                "      \"Measurements\":  \n" +
                "         {\"uid\":\"001BC50C7100001E\",\"sn\":\"78\",\"ts\":\"1.4122600283213E12\",\"pre\":\"1023\",\"rt\":\"0\",\"lb\":\"45\",\"lig\":\"3987\"}                 }\n" +
                "}";

        Object document = Configuration.defaultConfiguration().jsonProvider().parse(inputData);

        System.out.println(JsonPath.read(document, "$.observation.TimestampReceived"));
    }

}


