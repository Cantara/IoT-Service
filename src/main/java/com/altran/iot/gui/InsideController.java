package com.altran.iot.gui;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;


@Controller
public class InsideController {

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
    private String namepatient1 = "Lise";
    private String namepatient2 = "Petter";
    private String namepatient3 = "Ole";
    private String namepatient4 = "Knut";
    private String namepatient5 = "Reidun";

    Random randomGenerator = new Random();


    @RequestMapping("/dement")
    public ModelAndView test() {


        Map model = new HashMap<String, String>();
        model.put(HEADING, "Pasientovervåkning - demente");
        model.put(PATIENT_1, namepatient1 + " Inside - (A: 50, B: 30, C:45)  - " + patient1);
        if (randomGenerator.nextInt(100) > 40) {
            model.put(PATIENT_2, namepatient2 + " Inside - (A: 50, B: 40, C:45)  - " + patient2);
        } else {
            model.put(PATIENT_2, namepatient2 + " Inside - (A: 55, B: 45, C:45)  - " + patient2);
        }
        if (randomGenerator.nextInt(100) > 30) {
            model.put(PATIENT_3, namepatient3 + " Inside - (A: 50, B: 36, C:65)  - " + patient3);
            model.put(PATIENT_4, namepatient4 + " <font color=\"red\">Outside - (A: 50, B: 30, C:13)  - " + patient4 + "</font>");
        } else {
            model.put(PATIENT_3, namepatient3 + " <font color=\"red\">Outside - (A: 50, B: 40, C:?)  - " + patient3 + "</font>");
            model.put(PATIENT_4, namepatient4 + " Inside - (A: 50, B: 36, C:65)  - " + patient4);
        }
        model.put(PATIENT_5, namepatient5 + " <font color=\"yellow\">Offline - (A: ?, B: ?, C:?)  - " + patient5 + "</font> ");

        model.put(CORNER_1, "A: 50, B: 30, C:45");
        model.put(CORNER_2, "A: 50, B: 80, C:45");
        model.put(CORNER_3, "A: 40, B: 30, C:15");
        model.put(CORNER_4, "A: 40, B: 80, C:15");


        String message = "Pasientovervåkning - demente";
        return new ModelAndView("dement", "model", model);
    }
}
