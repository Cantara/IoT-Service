package com.altran.iot.gateway;

import com.altran.iot.QueryOperations;
import com.altran.iot.WriteOperations;
import com.altran.iot.observation.Observation;
import com.altran.iot.observation.ObservationsService;
import com.altran.iot.observation.ObservedMethod;
import com.altran.iot.search.LuceneIndexer;
import com.altran.iot.search.LuceneSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.util.LRUMap;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.util.*;

/**
 * @author <a href="mailto:bard.lind@gmail.com">Bard Lind</a>
 */
@Component
@Path("/observedsensor")
public class ObservedSensorResouce {
    private static final Logger log = LoggerFactory.getLogger(ObservedSensorResouce.class);

    private final QueryOperations queryOperations;
    private final WriteOperations writeOperations;
    private final ObjectMapper mapper;
    private final LuceneIndexer index;
    private final LRUMap timestamps = new LRUMap(200, 200);


    /**
    @Autowired
    public ObservedMethodsResouce(QueryOperations queryOperations, WriteOperations writeOperations, ObjectMapper mapper) {
        this.queryOperations = queryOperations;
        this.writeOperations = writeOperations;
        this.mapper = mapper;
    }
     **/
    @Autowired
    public ObservedSensorResouce(ObservationsService observationsService, ObjectMapper mapper, LuceneIndexer index) {
        this.queryOperations = observationsService;
        this.writeOperations = observationsService;
        this.mapper = mapper;
        this.index = index;
    }




    //http://localhost:80/iot/observe/observedsensor
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerObservationForSensor(@Context UriInfo ui, String prefix) {


        final long observedMethods;

        try {
            if (prefix != null) {
                log.trace("registerObservationForSensor body={}", prefix);
                //observedMethods = writeOperations.addObservations(prefix, new ArrayList<ObservedMethod>());
                List<Observation> observations = new LinkedList<>();
                Observation observation = Observation.fromD7data(prefix);
                String uniquenessKey = observation.getTimestampCreated() + observation.getRadioGatewayId() + observation.getRadioSensorId();
                if (timestamps.get(uniquenessKey) == null) {
                    log.trace("Registered new timestamp");
                    timestamps.put(uniquenessKey, new String("timestamp"));
                    observations.add(Observation.fromD7data(prefix));
                    index.addToIndex(observations);
                    log.info("registerObservationForSensor - added {}", observation);

                } else {
                    log.info("registerObservationForSensor - dropped - Received duplicate data. {}", observation);
                }
                // observedMethods = -1;
            } else {
                throw new UnsupportedOperationException("You must supply some body content.");
            }
        } catch (Exception e){
            throw new UnsupportedOperationException("You must supply Dash7 gw body content.");
        }

        return Response.ok("ok").build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response registerObservationForSensorGet(@QueryParam("query") String query) {
        // final long observedMethods;

        if (query != null) {
            log.trace("registerObservationForSensor body={}", query);
            // observedMethods = writeOperations.addObservations(prefix, new ArrayList<ObservedMethod>());
            LuceneSearch luceneSearch = new LuceneSearch(index.getDirectory());
            List<Observation> observations = luceneSearch.search(query);
            String result = buildJsonResult(observations);
            log.info("Search query={} returned {} observations", query, observations.size());
            return Response.ok(result).build();
        } else {
            throw new UnsupportedOperationException("You must supply some body content.");
        }

    }


    private String buildJsonResult(List<Observation> observations) {
        StringBuffer resultJSON = new StringBuffer();
        resultJSON.append("{  \n" + "   \"observations\":[");
        for (int i = 0; i < observations.size(); i++) {

            resultJSON.append(observations.get(i).toJsonString());
            resultJSON.append(",");
        }
        return resultJSON.substring(0, resultJSON.length() - 1) + "]}";
    }

}
