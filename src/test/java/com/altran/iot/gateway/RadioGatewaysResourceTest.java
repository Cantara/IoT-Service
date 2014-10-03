package com.altran.iot.gateway;

import com.altran.iot.infrastructure.ObservationSetup;
import com.altran.iot.observation.Observation;
import com.altran.iot.search.LuceneIndexer;
import com.altran.iot.search.LuceneSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Before;
import org.junit.Test;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import static org.junit.Assert.*;

public class RadioGatewaysResourceTest {

    LuceneIndexer luceneIndexer = null;
    Directory index = null;
    RadioGatewaysResource resource = null;
    LuceneSearch luceneSearch = null;
    @Before
    public void setUp() throws Exception {
        index = new RAMDirectory();
        luceneIndexer = new LuceneIndexer(index);
        insertData();
        ObjectMapper mapper = new ObjectMapper();
        luceneSearch = new LuceneSearch(index);
        resource = new RadioGatewaysResource(mapper,luceneIndexer);

    }



    @Test
    public void testListGateways() throws Exception {
        ObservationSetup observationSetup = luceneSearch.getInfrastructure();
        Set<String> expectedRadioGateways = new TreeSet<>();
        expectedRadioGateways.add("92.68.1.142");
        expectedRadioGateways.add("182.168.1.142");
        expectedRadioGateways.add("192.168.1.142");
        assertEquals(observationSetup.getRadioGatewayIds().keySet(), expectedRadioGateways);
        Set<String> expectedRadioSensors = new TreeSet<>();
        expectedRadioSensors.add("001BC50C71000019");
        expectedRadioSensors.add("001BC50C71030017");
        expectedRadioSensors.add("001BC50C7100001b");
        expectedRadioSensors.add("001BC50C71100017");
        expectedRadioSensors.add("001BCA0C7100001a");
        assertEquals(observationSetup.getRadioSensorIds().keySet(), expectedRadioSensors);

    }

    private void insertData() throws IOException {

        String inputData = "{\"ts\":1412231999149,\"data\":{\"001BC50C71100017\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}192.168.1.142";
        String inputData2 = "{\"ts\":1412231999149,\"data\":{\"001BC50C7100001b\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}92.68.1.142";
        String inputData3 = "{\"ts\":1412231999149,\"data\":{\"001BC50C71030017\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C7100001E\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}182.168.1.142";
        String inputData4 = "{\"ts\":1412231999149,\"data\":{\"001BCA0C7100001a\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC57C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}192.168.1.142";
        List<Observation> observations = Observation.fromD7Data(inputData);
        luceneIndexer.addToIndex(observations);
        List<Observation> observations2 = Observation.fromD7Data(inputData2);
        luceneIndexer.addToIndex(observations2);
        List<Observation> observations3 = Observation.fromD7Data(inputData3);
        luceneIndexer.addToIndex(observations3);
        List<Observation> observations4 = Observation.fromD7Data(inputData4);
        luceneIndexer.addToIndex(observations4);
    }
}