package com.altran.iot;

import org.junit.Test;

/**
 * Created by totto on 9/30/14.
 */
public class SynapticsParsingTest {

    @Test
    public void testSimplePostData(){
        String inputData = "18:07:07.295 {\"ts\":1412101119002.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412101118998.6,\"sn\":24,\"lb\":77,\"lig\":428,\"rt\":0,\"uid\":\"001BC50C71000017\"}},\"now\":1412101247524.6}192.168.1.142";
        String jsonResult="{  \n" +
                "      \"RadioGatewayId\":\"192.168.1.121\",\n" +
                "      \"RadioGatewayName\":\"192.168.1.121\",\n" +
                "      \"RadioGatewayDescription\":\"192.168.1.121\",\n" +
                "      \"RadioSensorId\":\"001BC50C7100001E\",\n" +
                "      \"RadioSensorName\":\"001BC50C7100001E\",\n" +
                "      \"RadioSensorDescription\":\"001BC50C7100001E\",\n" +
                "      \"TimestampCreated\":\"1412099476264.7\",\n" +
                "      \"TimestampReceived\":\"1412099476264.7\",\n" +
                "      \"Measurements\":  \n" +
                "         { \"SensorId1\":\"value1\",\n" +
                "           \"SensorId2\":\"value2\",\n" +
                "           \"SensorId3\":\"value3\",\n" +
                "           \"SensorId4\":\"value4\",\n" +
                "           \"SensorId5\":\"value5\",\n" +
                "           \"SensorId6\":\"value6\"\n" +
                "       \n" +
                "          }\n" +
                "}";
    }

}
