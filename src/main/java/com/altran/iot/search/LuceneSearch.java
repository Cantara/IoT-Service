package com.altran.iot.search;

import com.altran.iot.infrastructure.ObservationSetup;
import com.altran.iot.observation.Observation;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.MultiFieldQueryParser;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class LuceneSearch {
    private static final Logger logger = LoggerFactory.getLogger(LuceneSearch.class);
    private static final Analyzer ANALYZER = new StandardAnalyzer(Version.LUCENE_31);
    private static final int MAX_HITS = 200;
    private static final int MAX_INFRASTRUCTURE_HITS = 2000;

    private final Directory index;

    public Directory getDirectory() {
        return index;
    }

    public LuceneSearch(Directory index) {
        this.index = index;
    }

    public ObservationSetup getInfrastructure() {
        String queryString = "lb";

        String wildCardQuery = buildWildCardQuery(queryString);
        String[] fields = {
                LuceneIndexer.FIELD_TIMESTAMP,
                LuceneIndexer.FIELD_RADIOGATEWAY,
                LuceneIndexer.FIELD_RADIOSENSOR,
                LuceneIndexer.FIELD_MEASUREMENTS
        };
        HashMap<String, Float> boosts = new HashMap<>();
        boosts.put(LuceneIndexer.FIELD_RADIOGATEWAY, 1.5f);
        boosts.put(LuceneIndexer.FIELD_RADIOSENSOR, 3f);
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(Version.LUCENE_30, fields, ANALYZER, boosts);
        multiFieldQueryParser.setAllowLeadingWildcard(true);
        Query q;
        try {
            q = multiFieldQueryParser.parse(wildCardQuery);
            logger.info("getInfrastructure - search q={}", q);
        } catch (ParseException e) {
            logger.error("getInfrastructure - Could not parse wildCardQuery={}. Returning empty search result.", wildCardQuery, e);
            return new ObservationSetup();
        }

        ObservationSetup result = new ObservationSetup();
        IndexSearcher searcher = null;
        try {
            searcher = new IndexSearcher(index, true);
            result.setNumOfMeasurements(searcher.getIndexReader().numDocs());
            TopDocs topDocs = searcher.search(q, MAX_INFRASTRUCTURE_HITS);

            for (ScoreDoc hit : topDocs.scoreDocs) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);
                logger.trace("getInfrastructure - added observation to result: " + d);
                result.addRadioGatewayID(d.get(LuceneIndexer.FIELD_RADIOGATEWAY));
                result.addRadioSensorID(d.get(LuceneIndexer.FIELD_RADIOSENSOR));
            }
        } catch (IOException e) {
            logger.error("getInfrastructure - Error when searching.", e);
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    logger.info("getInfrastructure - searcher.close() failed. Ignore. {}", e.getMessage());
                }
            }
        }
        return result;
    }

    public List<Observation> search(String queryString) {
        String wildCardQuery = buildWildCardQuery(queryString);
        String[] fields = {
                LuceneIndexer.FIELD_TIMESTAMP,
                LuceneIndexer.FIELD_RADIOGATEWAY,
                LuceneIndexer.FIELD_RADIOSENSOR,
                LuceneIndexer.FIELD_MEASUREMENTS
        };
        HashMap<String, Float> boosts = new HashMap<>();
        boosts.put(LuceneIndexer.FIELD_RADIOGATEWAY, 1.5f);
        boosts.put(LuceneIndexer.FIELD_RADIOSENSOR, 3f);
        MultiFieldQueryParser multiFieldQueryParser = new MultiFieldQueryParser(Version.LUCENE_30, fields, ANALYZER, boosts);
        multiFieldQueryParser.setAllowLeadingWildcard(true);
        Query q;
        try {
            q = multiFieldQueryParser.parse(wildCardQuery);
            logger.info("search q={}", q);
        } catch (ParseException e) {
            logger.error("Could not parse wildCardQuery={}. Returning empty search result.", wildCardQuery, e);
            return new ArrayList<>();
        }

        List<Observation> result = new ArrayList<>();
        IndexSearcher searcher = null;
        try {
            searcher = new IndexSearcher(index, true);
            SortField sortField = new SortField(LuceneIndexer.FIELD_TIMESTAMP, SortField.STRING, true);  // Descending sort og timestamp
            Sort sort = new Sort(sortField);
            TopFieldDocs topDocs = searcher.search(q, MAX_HITS, sort);

            for (ScoreDoc hit : topDocs.scoreDocs) {
                int docId = hit.doc;
                Document d = searcher.doc(docId);
                logger.trace("added observation to result: " + d);
                Observation observation =  Observation.fromLucene(d.get(LuceneIndexer.FIELD_RADIOGATEWAY),d.get(LuceneIndexer.FIELD_RADIOSENSOR),d.get(LuceneIndexer.FIELD_MEASUREMENTS));
                //System.out.println(user.getUsername() + " : " + hit.score);
                result.add(observation);
            }
        } catch (IOException e) {
            logger.error("Error when searching.", e);
        } finally {
            if (searcher != null) {
                try {
                    searcher.close();
                } catch (IOException e) {
                    logger.info("searcher.close() failed. Ignore. {}", e.getMessage());
                }
            }
        }

        return result;
    }

    private String buildWildCardQuery(String queryString) {
        String[] queryitems = queryString.replace('_', ' ').split(" ");
        StringBuilder strb = new StringBuilder();
        for (String queryitem : queryitems) {
            strb.append(queryitem).append("^2 ");
            strb.append(queryitem).append("* ");
        }
        String wildCardQuery = strb.toString();
        logger.debug("Original query={}, wildcard query= {}", queryString, wildCardQuery);
        return wildCardQuery;
    }
}
