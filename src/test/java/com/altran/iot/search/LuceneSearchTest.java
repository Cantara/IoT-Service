package com.altran.iot.search;

import com.altran.iot.observation.Observation;
import junit.framework.TestCase;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LuceneSearchTest extends TestCase {

    @Test
    public void testsearch() throws IOException {
        Directory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> users = new ArrayList<Observation>() {{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Ola"));

        }};
        luceneIndexer.addToIndex(users);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Kari");
        assertEquals(1, result.size());
        result = luceneSearch.search("Ola");
        assertEquals(2, result.size());
        result = luceneSearch.search("ola@example.com");
        assertEquals(1, result.size());
        result = luceneSearch.search("Pølser");
        assertEquals(0, result.size());

    }


    @Test
    public void testremoveuser() throws IOException {
        RAMDirectory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> users = new ArrayList<Observation>() {{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Medel"));

        }};
        luceneIndexer.addToIndex(users);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        luceneIndexer.removeFromIndex("ola@example.com");
        result = luceneSearch.search("Ola");
//        assertEquals(0, result.size());
    }


    @Test
    public void testmodifyuser() throws IOException {
        RAMDirectory index = new RAMDirectory();
        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> users = new ArrayList<Observation>() {{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Medel"));
        }};
        luceneIndexer.addToIndex(users);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        luceneIndexer.update(createObservation("ola@example.com", "Ola"));
        result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        result = luceneSearch.search("Ola");
        assertEquals(1, result.size());

    }

    @Test
    public void testwildcardsearch() throws IOException {
        Directory index = new RAMDirectory();
        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> users = new ArrayList<Observation>() {{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Medel"));

        }};
        luceneIndexer.addToIndex(users);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        result = luceneSearch.search("Ola");
        assertEquals(1, result.size());
        result = luceneSearch.search("ola@");
        assertEquals(1, result.size());
    }

    @Test
    public void testweights() throws IOException {
        Directory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> observations = new ArrayList<Observation>() {{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Ola"));

        }};
        luceneIndexer.addToIndex(observations);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Ola");
        assertEquals(2, result.size());
    }


    @Test
    public void testSensorData4() throws IOException {
        Directory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        String inputData = "{\"ts\":1412231999149,\"data\":{\"001BC50C71000017\":{\"ts\":1412230901167.9,\"sn\":8,\"lb" +
                "\":91,\"lig\":2676,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C71000019\":{\"uid\":\"001" +
                "BC50C71000019\",\"ts\":1412231999144.8,\"tmp\":25,\"sn\":190,\"lb\":90,\"lig\":2999,\"rt\":0" +
                ",\"hum\":39}},\"now\":1412236051093}192.168.1.142";
        List<Observation> observations = Observation.fromD7Data(inputData);
        luceneIndexer.addToIndex(observations);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("192");
        assertTrue(result.size() >= 1);

    }

    @Test
    public void testSensorData5() throws IOException {
        Directory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        String inputData = "{\"ts\":1412266891761.6,\"data\":{\"001BC50C71000017\":{\"ts\":1412264622871.8,\"sn\":0,\"lb\":70,\"lig\":3937,\"rt\":0,\"uid\":\"001BC50C71000017\"},\"001BC50C710\n" +
                "0001F\":{\"ts\":1412264781480.6,\"sn\":0,\"lb\":44,\"lig\":3882,\"rt\":0,\"uid\":\"001BC50C7100001F\"},\"001BC50C71000019\":{\"ts\":1412266891757.6,\"sn\":231,\"lb\"\n" +
                ":74,\"rt\":0,\"x\":-31,\"btn2\":1412264580802.2,\"btn1\":1412264580964.4,\"uid\":\"001BC50C71000019\",\"tmp\":24,\"z\":40,\"pre\":1023,\"lig\":3772,\"y\":-29,\"hum\":\n" +
                "35}},\"now\":1412266891949.1}";
        List<Observation> observations = Observation.fromD7Data(inputData);
        luceneIndexer.addToIndex(observations);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("001BC50C71000017");
        assertTrue(result.size() >= 1);

    }



    private static Observation createObservation(String radioSensorId, String radioGatewayId) {
        Observation observation = Observation.fromD7dataTemplate("");
        observation.setRadioSensorId(radioSensorId);
        observation.setRadioGatewayId(radioGatewayId);
        return observation;
    }

}
