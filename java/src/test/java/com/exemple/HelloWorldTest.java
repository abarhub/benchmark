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
        double[][] tab1 = HelloWorld.initialisation(lenx,false);
        double[][] tab2 = HelloWorld.initialisation(lenx,true);

        var res = HelloWorld.multi(tab1, tab2, lenx);

        assertNotNull(res);
        verifieTableau(lenx, res);
    }

    
    @Test
    public void testMultiThread1() {
        int lenx = 3;
        double[][] tab1 = HelloWorld.initialisation(lenx,false);
        double[][] tab2 = HelloWorld.initialisation(lenx,true);

        var res = HelloWorld.multiThread(tab1, tab2, lenx);

        assertNotNull(res);
        verifieTableau(lenx, res);
    }


    private void verifieTableau(int lenx, double[][] res) throws MultipleFailuresError {
        assertEquals(lenx, res.length);
        assertAll(
                () -> assertEquals(3.3, res[0][0], 0.001),
                () -> assertEquals(3.42, res[0][1], 0.001),
                () -> assertEquals(3.3, res[0][2], 0.001),
                () -> assertEquals(3.21, res[1][0], 0.001),
                () -> assertEquals(3.3, res[1][1], 0.001),
                () -> assertEquals(3.21, res[1][2], 0.001),
                () -> assertEquals(3.3, res[2][0], 0.001),
                () -> assertEquals(3.42, res[2][1], 0.001),
                () -> assertEquals(3.3, res[2][2], 0.001));
    }

    
}
