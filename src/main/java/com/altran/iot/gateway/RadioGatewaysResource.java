package com.altran.iot.gateway;

import com.altran.iot.QueryOperations;
import com.altran.iot.WriteOperations;
import com.altran.iot.infrastructure.ObservationSetup;
import com.altran.iot.observation.Observation;
import com.altran.iot.search.LuceneIndexer;
import com.altran.iot.search.LuceneSearch;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.index.TermEnum;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author <a href="bard.lind@gmail.com">Bard Lind</a>
 */
@Component
@Path("/radiogateways")
public class RadioGatewaysResource {
    private static final Logger log = LoggerFactory.getLogger(RadioGatewaysResource.class);

    private final ObjectMapper mapper;
    private final LuceneIndexer index;
    private LuceneSearch luceneSearch;

    @Autowired
    public RadioGatewaysResource(ObjectMapper mapper, LuceneIndexer index) {
        this.mapper = mapper;
        this.index = index;
        this.luceneSearch = new LuceneSearch(index.getDirectory());
    }


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/")
    public Response listGateways() {
        log.trace("List All Gateways");
        ObservationSetup observationSetup = luceneSearch.getInfrastructure();
        String result = null; //buildJsonResult(observationSetup);
        try {
            if ( observationSetup != null) {
                result = mapper.writeValueAsString(observationSetup);
            }
        } catch (JsonProcessingException e) {
            log.warn("Error parsing ObservationSetup to Json. observationSetup: {}, error {}", observationSetup.toString(), e.getMessage());
        }
        log.trace("List All Gateways {}", result);
        return Response.ok(result).build();
    }

}
