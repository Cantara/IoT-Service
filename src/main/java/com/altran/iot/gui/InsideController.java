package com.altran.iot.gui;

import com.altran.iot.QueryOperations;
import com.altran.iot.WriteOperations;
import com.altran.iot.observation.Observation;
import com.altran.iot.observation.ObservationsService;
import com.altran.iot.search.LuceneIndexer;
import com.altran.iot.search.LuceneSearch;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Controller
public class InsideController {

    private static final Logger log = LoggerFactory.getLogger(InsideController.class);

    private final QueryOperations queryOperations;
    private final WriteOperations writeOperations;
    private final ObjectMapper mapper;
    private final LuceneIndexer index;
    private LuceneSearch luceneSearch;


    public static final String HEADING = "message";

    public static final String PATIENT_1 = "patient1";
    public static final String PATIENT_2 = "patient2";
    public static final String PATIENT_3 = "patient3";
    public static final String PATIENT_4 = "patient4";
    public static final String PATIENT_5 = "patient5";

    public static final String CORNER_1 = "corner1";
    public static final String CORNER_2 = "corner2";
    public static final String CORNER_3 = "corner3";
    public static final String CORNER_4 = "corner4";

    private String patient1 = "001BC50C71000019";
    private String patient2 = "001BC50C7100001E";
    private String patient3 = "001BC50C71000017";
    private String patient4 = "001BC50C7100001F";
    private String patient5 = "001BC50C7100001A";
    private String namepatient1 = "Leon";
    private String namepatient2 = "Stig";
    private String namepatient3 = "Kirsten";
    private String namepatient4 = "Jan Helge";
    private String namepatient5 = "Jason";

    Random randomGenerator = new Random();

    /**
     * @Autowired public ObservedMethodsResouce(QueryOperations queryOperations, WriteOperations writeOperations, ObjectMapper mapper) {
     * this.queryOperations = queryOperations;
     * this.writeOperations = writeOperations;
     * this.mapper = mapper;
     * }
     */
    @Autowired
    public InsideController(ObservationsService observationsService, ObjectMapper mapper, LuceneIndexer index) {
        this.queryOperations = observationsService;
        this.writeOperations = observationsService;
        this.mapper = mapper;
        this.index = index;
        this.luceneSearch = new LuceneSearch(index.getDirectory());
    }


    @RequestMapping("/dement")
    public ModelAndView calculatePatients() {

        String valuePatient1 = getTriangulationString(patient1);
        String valuePatient2 = getTriangulationString(patient2);
        String valuePatient3 = getTriangulationString(patient3);
        String valuePatient4 = getTriangulationString(patient4);
        String valuePatient5 = getTriangulationString(patient5);


        Map model = new HashMap<String, String>();
        model.put(HEADING, "Pasientovervåkning - demente");


        if (isLost(valuePatient1)) {
            model.put(PATIENT_1, namepatient1 + " - <font color=\"orange\">Unknown - " + valuePatient1 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient1 + "\">" + patient1 + "</a></font>");
        } else if (isInside(valuePatient1)) {
            model.put(PATIENT_1, namepatient1 + " - Inside - " + valuePatient1 + "  - " + patient1);
        } else {
            model.put(PATIENT_1, namepatient1 + " - <font color=\"red\">Outside - " + valuePatient1 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient1 + "\">" + patient1 + "</a></font>");
        }

        if (isLost(valuePatient2)) {
            model.put(PATIENT_2, namepatient2 + " - <font color=\"orange\">Unknown - " + valuePatient2 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient2 + "\">" + patient2 + "</a></font>");
        } else if (isInside(valuePatient2)) {
            model.put(PATIENT_2, namepatient2 + " - Inside - " + valuePatient2 + "  - " + patient2);
        } else {
            model.put(PATIENT_2, namepatient2 + " - <font color=\"red\">Outside - " + valuePatient2 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient2 + "\">" + patient2 + "</a></font>");
        }

        if (isLost(valuePatient3)) {
            model.put(PATIENT_3, namepatient3 + " - <font color=\"orange\">Unknown - " + valuePatient3 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient3 + "\">" + patient3 + "</a></font>");
        } else if (isInside(valuePatient3)) {
            model.put(PATIENT_3, namepatient3 + " - Inside - " + valuePatient3 + "  - " + patient3);
        } else {
            model.put(PATIENT_3, namepatient3 + " - <font color=\"red\">Outside - " + valuePatient3 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient3 + "\">" + patient3 + "</a></font>");
        }

        if (isLost(valuePatient4)) {
            model.put(PATIENT_4, namepatient4 + " - <font color=\"orange\">Unknown - " + valuePatient4 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient4 + "\">" + patient4 + "</a></font>");
        } else if (isInside(valuePatient4)) {
            model.put(PATIENT_4, namepatient4 + " - Inside - " + valuePatient4 + "  - " + patient4);
        } else {
            model.put(PATIENT_4, namepatient4 + " - <font color=\"red\">Outside - " + valuePatient4 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient4 + "\">" + patient4 + "</a></font>");
        }

        if (isLost(valuePatient5)) {
            model.put(PATIENT_5, namepatient5 + " - <font color=\"orange\">Unknown - " + valuePatient5 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient5 + "\">" + patient5 + "</a></font>");
        } else if (isInside(valuePatient5)) {
            model.put(PATIENT_5, namepatient5 + " - Inside - " + valuePatient5 + "  - " + patient5);
        } else {
            model.put(PATIENT_5, namepatient5 + " - <font color=\"red\">Outside - " + valuePatient5 + "  - " + "<a href=\"http://iot.altrancloud.com/?query=" + patient5 + "\">" + patient5 + "</a></font>");
        }



        model.put(CORNER_1, "A: 50, B: 30, C:45");
        model.put(CORNER_2, "A: 50, B: 80, C:45");
        model.put(CORNER_3, "A: 40, B: 30, C:15");
        model.put(CORNER_4, "A: 40, B: 80, C:15");


        String message = "Pasientovervåkning - demente";
        return new ModelAndView("dement", "model", model);
    }


    /**
     * Find the last link budget for our sensors, if the last sensor reading is old, return value="?"
     * <p/>
     *
     * @param patient
     * @return
     */
    private String getTriangulationString(String patient) {
        String valuePatient;
        try {


            Map triangulations = new HashMap();
            List<Observation> observations = luceneSearch.search(patient);
            for (int i = 0; i < 10; i++) {  // we just check the latest 10 measurements
                Observation s = observations.get(i);
                if (triangulations.get(s.getRadioGatewayId()) == null) {  // Registration does not exist
                    if (isOld(s.getTimestampCreated())) {  // is the registration old
                        triangulations.put(s.getRadioGatewayId(), "?");
                    } else {
                        triangulations.put(s.getRadioGatewayId(), s.getMeasurements().get("lb")); // add registration
                    }
                }
            }
            Map<String, String> treeMap = new TreeMap<String, String>(triangulations);  // Sort on keys
            Iterator entries = treeMap.entrySet().iterator();
            Map.Entry thisEntry = (Map.Entry) entries.next();
            valuePatient = "(A:" + thisEntry.getValue();
            thisEntry = (Map.Entry) entries.next();
            valuePatient = valuePatient + ", B:" + thisEntry.getValue();
            thisEntry = (Map.Entry) entries.next();
            valuePatient = valuePatient + ", C:" + thisEntry.getValue() + ")";


        } catch (Exception e) {
            valuePatient = "(A: ?, B: ?, C: ?)";
        }
        return valuePatient;
    }

    public static boolean isInside(String position) {
        boolean isIn =true;
        try {
            String delims = ",";
            StringTokenizer st = new StringTokenizer(position, delims);
            while (st.hasMoreElements()) {
                String value = st.nextElement().toString();
                value = value.replaceAll("[^\\d.]", "");
                Integer i = Integer.parseInt(value);
                if (i < 55) {
                    isIn = false;

                }
            }
        } catch (Exception e) {
            log.error("Error trying to calculate if patient is lost");
            return true;
        }
        return isIn;
    }

    public static boolean isLost(String position) {
        if (position.indexOf("?") > 0) {
            return true;
        }
        return false;
    }

    public static boolean isOld(String dateString) {
        try {
            SimpleDateFormat dateParser = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if (new Date().getTime() - dateParser.parse(dateString).getTime() <= 2 * 60 * 1000) {
                return false;
            }
        } catch (ParseException pe) {
            log.error("Illegal datestring received, returning isOld=true string:" + dateString);
        }
        return true;
    }
}
