package com.altran.iot.search;

import com.altran.iot.infrastructure.ObservationSetup;
import com.altran.iot.observation.Observation;
import junit.framework.TestCase;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.RAMDirectory;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

public class InfrastructureSearchTest extends TestCase {


    @Test
    public void testGetInfrastructure() throws IOException {
        Directory index = new RAMDirectory();

        LuceneIndexer luceneIndexer = new LuceneIndexer(index);
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
        LuceneSearch luceneSearch = new LuceneSearch(index);
        List<Observation> result = luceneSearch.search("192");
        assertTrue(result.size() >= 1);
        ObservationSetup os = luceneSearch.getInfrastructure();
        System.out.println(os);
        assertTrue(os.getRadiogatewayids().size() > 1);
        assertTrue(os.getRadiosensorids().size() > 1);

    }

}
