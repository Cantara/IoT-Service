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
        List<Observation> users = new ArrayList<Observation>(){{
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
        List<Observation> users = new ArrayList<Observation>(){{
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
        assertEquals(0, result.size());
    }


    @Test
    public void testmodifyuser() throws IOException {
        RAMDirectory index = new RAMDirectory();
        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
        List<Observation> users = new ArrayList<Observation>(){{
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
        List<Observation> users = new ArrayList<Observation>(){{
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
        List<Observation> observations = new ArrayList<Observation>(){{
            add(createObservation("kari.norman@example.com", "Kari"));
            add(createObservation("ola@example.com", "Ola"));
            add(createObservation("medel.svenson@example.com", "Ola"));

        }};
        luceneIndexer.addToIndex(observations);
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("Ola");
        assertEquals(2, result.size());
    }


    private static Observation createObservation(String radioSensorId, String radioGatewayId) {
        Observation observation =  Observation.fromD7dataTemplate("");
        observation.setRadioSensorId(radioSensorId);
        observation.setRadioGatewayId(radioGatewayId);
        return observation;
    }

}
