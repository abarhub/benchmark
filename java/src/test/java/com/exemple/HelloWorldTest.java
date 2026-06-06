package com.exemple;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;
import org.opentest4j.MultipleFailuresError;

public class HelloWorldTest {

    @Test
    public void testMulti1() {
        int lenx = 3;
        double[][] tab1 = new double[lenx][lenx];
        double[][] tab2 = new double[lenx][lenx];

        HelloWorld.initialisation(tab1, tab2);

        var res = HelloWorld.multi(tab1, tab2, lenx);

        assertNotNull(res);
        verifieTableau(lenx, res);
    }

    
    @Test
    public void testMultiThread1() {
        int lenx = 3;
        double[][] tab1 = new double[lenx][lenx];
        double[][] tab2 = new double[lenx][lenx];

        HelloWorld.initialisation(tab1, tab2);

        var res = HelloWorld.multiThread(tab1, tab2, lenx);

        assertNotNull(res);
        verifieTableau(lenx, res);
    }


    private void verifieTableau(int lenx, double[][] res) throws MultipleFailuresError {
        assertEquals(lenx, res.length);
        assertAll(
                () -> assertEquals(3.3, res[0][0], 0.01),
                () -> assertEquals(3.42, res[0][1], 0.01),
                () -> assertEquals(3.3, res[0][2], 0.01),
                () -> assertEquals(3.21, res[1][0], 0.01),
                () -> assertEquals(3.3, res[1][1], 0.01),
                () -> assertEquals(3.21, res[1][2], 0.01),
                () -> assertEquals(3.3, res[2][0], 0.01),
                () -> assertEquals(3.42, res[2][1], 0.01),
                () -> assertEquals(3.3, res[2][2], 0.01));
    }

    
}
