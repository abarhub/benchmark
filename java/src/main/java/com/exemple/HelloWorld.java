package com.exemple;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloWorld {

    private static void helloworld() {
        System.out.println("Hello World !");
    }

    private static void multi(List<String> args) {

        int lenx = 3;

        if (args != null && args.size() > 0) {
            var s = args.getFirst();
            var n = Integer.parseInt(s);
            if (n > 0) {
                lenx = n;
            }
        }
        System.out.println("len=" + lenx);

        double[][] tab1 = new double[lenx][lenx];
        double[][] tab2 = new double[lenx][lenx];
        double[][] res = new double[lenx][lenx];

        initialisation(tab1, tab2);

        for (int i = 0; i < lenx; i++) {

            for (int j = 0; j < lenx; j++) {

                double m = 0.0;
                for (int k = 0; k < lenx; k++) {
                    m += tab1[i][k] * tab2[k][j];
                }
                res[i][j] = m;

            }

        }

    }

    private static void initialisation(double[][] tab1, double[][] tab2) {

        for (int i = 0; i < tab1.length; i++) {

            for (int j = 0; j < tab1[0].length; j++) {

                int pos = i + j;

                if (pos % 2 == 0) {
                    tab1[i][j] = 1.1;
                } else {
                    tab1[i][j] = 1.0;
                }

                if ((pos + 1) % 2 == 0) {
                    tab2[i][j] = 1.1;
                } else {
                    tab2[i][j] = 1.0;
                }

            }
        }

    }

    private static void multiThread(List<String> args) {
        int lenx = 3;

        if (args != null && args.size() > 0) {
            var s = args.getFirst();
            var n = Integer.parseInt(s);
            if (n > 0) {
                lenx = n;
            }
        }
        System.out.println("len=" + lenx);

        // ExecutorService executor = Executors.newFixedThreadPool(10);
        ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

        double[][] tab1 = new double[lenx][lenx];
        double[][] tab2 = new double[lenx][lenx];
        double[][] res = new double[lenx][lenx];

        initialisation(tab1, tab2);

        final int len0 = lenx;
        List<Future<Double>> listeResultat = new ArrayList<>();

        for (int i = 0; i < lenx; i++) {

            for (int j = 0; j < lenx; j++) {

                final int i0 = i;
                final int j0 = j;

                Callable<Double> callableTask = () -> {
                    double m = 0.0;
                    for (int k = 0; k < len0; k++) {
                        m += tab1[i0][k] * tab2[k][j0];
                    }
                    res[i0][j0] = m;
                    return m;
                };

                var res2 = executor.submit(callableTask);
                listeResultat.add(res2);
            }

        }

        for (var future : listeResultat) {
            Double result = null;
            try {
                result = future.get();
            } catch (InterruptedException | ExecutionException e) {
                e.printStackTrace();
            }
        }

    }

    public static void main(String[] args) {

        int operation = 3;
        List<String> liste = new ArrayList<>();
        if (args != null) {
            for (var s : args) {
                liste.add(s);
            }
        }

        if (liste.size() > 0) {
            switch (liste.getFirst()) {
                case "helloword":
                    operation = 1;
                    liste.removeFirst();
                    break;
                case "multi":
                    operation = 2;
                    liste.removeFirst();
                    break;
                case "multithread":
                    operation = 3;
                    liste.removeFirst();
                    break;
            }
        }

        if (operation == 1) {
            helloworld();
        } else if (operation == 2) {
            multi(liste);
        } else if (operation == 3) {
            multiThread(liste);
        }
    }

}
