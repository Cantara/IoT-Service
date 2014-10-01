package com.altran.iot.search;

import com.altran.iot.observation.Observation;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.store.Directory;
import org.apache.lucene.util.Version;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.List;

public class LuceneIndexer {
    private static final Logger logger = LoggerFactory.getLogger(LuceneIndexer.class);
    private final ObjectMapper mapper = new ObjectMapper();


    public static final String FIELD_RADIOGATEWAY = "radiogateway";
    public static final String FIELD_RADIOSENSOR = "radiosensor";
    public static final String FIELD_TIMESTAMP = "timestamp";
    public static final String FIELD_MEASUREMENTS = "measurements";


    private final Directory index;
    private static final Analyzer ANALYZER = new StandardAnalyzer(Version.LUCENE_31);

    public LuceneIndexer(Directory index) {
        this.index = index;
    }

    public void removeFromIndex(String timestamp) {
        try {
            IndexWriter w = getWriter();
            w.deleteDocuments(new Term(FIELD_TIMESTAMP, timestamp));
            w.optimize();
            w.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void update(Observation observation) {
        try {
            IndexWriter w = getWriter();
            w.updateDocument(new Term(FIELD_TIMESTAMP, observation.getTimestampCreated()), createLuceneDocument(observation));
            w.optimize();
            w.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void addToIndex(Observation observation) {
        try {
            IndexWriter writer = getWriter();
            addToIndex(writer, observation);
            writer.optimize();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getLocalizedMessage(), e);
        }
    }

    public void addToIndex(List<Observation> observations) throws IOException {
        IndexWriter indexWriter = new IndexWriter(index, ANALYZER, IndexWriter.MaxFieldLength.UNLIMITED);
        for (Observation observation : observations) {
            Document doc = createLuceneDocument(observation);
            indexWriter.addDocument(doc);
        }
        indexWriter.optimize();
        indexWriter.close();
    }

    /**
     * Use with caution. Close writer after use.
     *
     * @return IndexWriter
     * @throws org.apache.lucene.index.CorruptIndexException     if the index is corrupt
     * @throws org.apache.lucene.store.LockObtainFailedException if another writer
     *                                                           has this index open (<code>write.lock</code> could not
     *                                                           be obtained)
     * @throws IOException                                       if the directory cannot be
     *                                                           read/written to or if there is any other low-level
     *                                                           IO error
     */
    public IndexWriter getWriter() throws IOException {
        return new IndexWriter(index, ANALYZER, IndexWriter.MaxFieldLength.UNLIMITED);
    }

    public void addToIndex(IndexWriter writer, Observation user) {
        try {
            Document doc = createLuceneDocument(user);
            writer.addDocument(doc);
        } catch (IOException e) {
            logger.error("addToIndex failed for {}. Index was probably not updated.", user.toString(), e);
        }
    }

    private Document createLuceneDocument(Observation observation) {
        Document doc = new Document();
        doc.add(new Field(FIELD_RADIOGATEWAY, observation.getRadioGatewayId(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(FIELD_RADIOSENSOR, observation.getRadioSensorId(), Field.Store.YES, Field.Index.ANALYZED));
        doc.add(new Field(FIELD_TIMESTAMP, observation.getTimestampCreated(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        try {
            doc.add(new Field(FIELD_MEASUREMENTS, mapper.writeValueAsString(observation), Field.Store.YES, Field.Index.ANALYZED));
        } catch (JsonProcessingException jpe) {
            logger.error("Unable to parse and map the measurements.", jpe);

        }
        return doc;
    }

}
