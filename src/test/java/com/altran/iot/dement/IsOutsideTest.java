package com.altran.iot.dement;

import com.altran.iot.gui.InsideController;
import org.junit.Test;

import java.util.StringTokenizer;


public class IsOutsideTest {

    @Test
    public void shouldBeOutsideTest() {
        String position = "A: 50, B: 30, C:45";
        assert (InsideController.isInside(position) == true);
        position = "A: 50, B: 30, C:95";
        assert (InsideController.isInside(position) == true);
        position = "A: 50, B: 70, C:45";
        assert (InsideController.isInside(position) == true);
    }

    @Test
    public void shouldBeInsideTest() {
        String position = "A: 60, B: 70, C:65";
        assert (InsideController.isInside(position) == true);
        position = "A: 90, B: 80, C:195";
        assert (InsideController.isInside(position) == true);
        position = "A: 80, B: 780, C:145";
        assert (InsideController.isInside(position) == true);
    }
}
