package com.altran.iot.gateway;

import com.altran.iot.QueryOperations;
import com.altran.iot.WriteOperations;
import com.altran.iot.observation.Observation;
import com.altran.iot.observation.ObservationsService;
import com.altran.iot.observation.ObservedMethod;
import com.altran.iot.search.LuceneIndexer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriInfo;
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
                List<Observation> l = new LinkedList<>();
                l.add(Observation.fromD7data(prefix));
                index.addToIndex(l);
                observedMethods = -1;
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
    public Response registerObservationForSensorGet(@QueryParam("body") String prefix) {
        final long observedMethods;

        if (prefix != null ) {
            log.trace("registerObservationForSensor body={}", prefix);
            observedMethods = writeOperations.addObservations(prefix, new ArrayList<ObservedMethod>());
        } else {
            throw new UnsupportedOperationException("You must supply some body content.");
        }

        return Response.ok("ok").build();
    }


}
