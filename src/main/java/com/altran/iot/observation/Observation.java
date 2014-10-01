package com.altran.iot.observation;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class Observation {

    private String radioGatewayId;
    private String radioGatewayName;
    private String radioGatewayDescription;
    private String radioSensorId;
    private String radioSensorName;
    private String radioSensorDescription;
    private String timestampCreated;
    private String timestampReceived;
    private Map<String,String> measurements;

    private static final Logger logger = LoggerFactory.getLogger(Observation.class);


    public String getRadioGatewayId() {
        return radioGatewayId;
    }

    public void setRadioGatewayId(String radioGatewayId) {
        this.radioGatewayId = radioGatewayId;
    }

    public String getRadioGatewayName() {
        return radioGatewayName;
    }

    public void setRadioGatewayName(String radioGatewayName) {
        this.radioGatewayName = radioGatewayName;
    }

    public String getRadioGatewayDescription() {
        return radioGatewayDescription;
    }

    public void setRadioGatewayDescription(String radioGatewayDescription) {
        this.radioGatewayDescription = radioGatewayDescription;
    }

    public String getRadioSensorId() {
        return radioSensorId;
    }

    public void setRadioSensorId(String radioSensorId) {
        this.radioSensorId = radioSensorId;
    }

    public String getRadioSensorName() {
        return radioSensorName;
    }

    public void setRadioSensorName(String radioSensorName) {
        this.radioSensorName = radioSensorName;
    }

    public String getRadioSensorDescription() {
        return radioSensorDescription;
    }

    public void setRadioSensorDescription(String radioSensorDescription) {
        this.radioSensorDescription = radioSensorDescription;
    }

    public String getTimestampCreated() {
        return timestampCreated;
    }

    public void setTimestampCreated(String timestampCreated) {
        this.timestampCreated = timestampCreated;
    }

    public String getTimestampReceived() {
        return timestampReceived;
    }

    public void setTimestampReceived(String timestampReceived) {
        this.timestampReceived = timestampReceived;
    }

    public Map<String, String> getMeasurements() {
        return measurements;
    }

    public void setMeasurements(Map<String, String> measurements) {
        this.measurements = measurements;
    }

    private Observation(){

    }

    public static Observation fromD7dataTemplate(String s) {
        Observation o = new Observation();
        o.radioGatewayId="001BC50C7100001E";
        o.radioGatewayName="001BC50C7100001E";
        o.radioGatewayDescription="001BC50C7100001E";
        o.radioSensorId="001BC50C7100001E";
        o.radioSensorDescription="001BC50C7100001E";
        o.radioSensorName="001BC50C7100001E";
        o.timestampCreated="1412099476264.7";
        o.timestampReceived="1412099476264.7";
        Map<String,String> measurementsReveived = new HashMap<>();
        measurementsReveived.put("SensorId1","value1");
        measurementsReveived.put("SensorId2","value2");
        measurementsReveived.put("SensorId3","value3");
        measurementsReveived.put("SensorId4","value4");
        measurementsReveived.put("SensorId5","value5");
        measurementsReveived.put("SensorId6","value6");
        o.measurements=measurementsReveived;
        return o;

    }
    public static Observation fromD7data(String inputData)  {
        Observation o = new Observation();
        try {

            logger.trace("Entry");
            int startIdx = inputData.indexOf("{");
            int endIdx = inputData.lastIndexOf(",");
            String json = inputData.substring(startIdx, endIdx) + "}";
            o.setRadioGatewayId(inputData.substring(inputData.lastIndexOf("}")+1));
            JSONParser jsonParser = new JSONParser();
            logger.trace("Entry - jsonParser:{}",jsonParser);
            JSONObject jsonObject = (JSONObject) jsonParser.parse(json);
            logger.trace("Entry - jsonObject:{}",jsonObject);


            JSONObject structure = (JSONObject) jsonObject.get("data");
            logger.trace("Entry - structure:{}",structure);
            String sensorID = (String) structure.keySet().toArray()[0];
            logger.trace("Entry - sensorID:{}",sensorID);

            JSONObject readings = (JSONObject) structure.values().toArray()[0];
            logger.trace("Entry - readings:{}",readings);

            o.timestampReceived = Double.toString((Double) jsonObject.get("ts"));
            logger.trace("Entry - timestampReceived:{}",o.timestampReceived);
            o.timestampCreated = Double.toString((Double) readings.get("ts"));
            logger.trace("Entry - timestampCreated:{}",o.timestampCreated);
            o.radioSensorId = (String) readings.get("uid");
            logger.trace("Entry - radioSensorId:{}",o.radioSensorId);

            Map<String, String> measurementsReceived = new HashMap<>();
            measurementsReceived.put("sn", Long.toString((Long) readings.get("sn")));
            logger.trace("Entry - sn:{}",Long.toString((Long) readings.get("sn")));
            measurementsReceived.put("rt", Long.toString((Long) readings.get("rt")));
            logger.trace("Entry - rt:{}",Long.toString((Long) readings.get("rt")));
            measurementsReceived.put("lb", Long.toString((Long) readings.get("lb")));
            logger.trace("Entry - lb:{}",Long.toString((Long) readings.get("lb")));
            measurementsReceived.put("lig", Long.toString((Long) readings.get("lig")));
            logger.trace("Entry - lig:{}",Long.toString((Long) readings.get("lig")));


            o.setMeasurements(measurementsReceived);
        } catch (ParseException e){
            logger.error("Attempting to parse Dash7 datapackage failed. Data: {}",inputData,e);
            return Observation.fromD7dataTemplate("");
        }
        return o;
    }

    public static Observation fromLucene(String radioGatewayId,String radioSensorId,String jsondata)  {
        Observation o = new Observation();
        o.setRadioGatewayId(radioGatewayId);
        o.setRadioSensorId(radioSensorId);
        return o;
    }

        @Override
    public String toString() {
        return "Observation{" +
                "radioGatewayId='" + radioGatewayId + '\'' +
                ", radioGatewayName='" + radioGatewayName + '\'' +
                ", radioGatewayDescription='" + radioGatewayDescription + '\'' +
                ", radioSensorId='" + radioSensorId + '\'' +
                ", radioSensorName='" + radioSensorName + '\'' +
                ", radioSensorDescription='" + radioSensorDescription + '\'' +
                ", timestampCreated='" + timestampCreated + '\'' +
                ", timestampReceived='" + timestampReceived + '\'' +
                ", measurements=" + measurements +
                '}';
    }
}


