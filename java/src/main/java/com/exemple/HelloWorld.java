package com.exemple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class HelloWorld {

    public static void helloworld() {
        System.out.println("Hello World !");
    }

    public static double[][] initialisation(int len, boolean decalage) {
        double[][] tab = new double[len][len];
        for (int i = 0; i < tab.length; i++) {

            for (int j = 0; j < tab[i].length; j++) {

                int pos = i + j;
                if (decalage) {
                    pos++;
                }

                if (pos % 2 == 0) {
                    tab[i][j] = 1.1;
                } else {
                    tab[i][j] = 1.0;
                }

            }
        }
        return tab;
    }

    public static double[][] multi(double[][] tab1, double[][] tab2, int lenx) {
        double[][] res = new double[lenx][lenx];
        for (int i = 0; i < lenx; i++) {

            for (int j = 0; j < lenx; j++) {

                double m = 0.0;
                for (int k = 0; k < lenx; k++) {
                    m += tab1[i][k] * tab2[k][j];
                }
                res[i][j] = m;

            }

        }

        return res;
    }

    public static void multi(List<String> args) {

        int lenx = 3;
        var debug = false;

        if (args != null && args.size() > 0) {
            var iter = args.iterator();
            while (iter.hasNext()) {
                var s = iter.next();
                if (Objects.equals(s, "--debug")) {
                    debug = true;
                    iter.remove();
                }
            }
        }

        if (args != null && args.size() > 0) {
            var s = args.getFirst();
            var n = Integer.parseInt(s);
            if (n > 0) {
                lenx = n;
            }
        }
        if (debug) {
            System.out.println("len=" + lenx);
        }

        double[][] tab1 = initialisation(lenx, false);
        double[][] tab2 = initialisation(lenx, true);

        var res = multi(tab1, tab2, lenx);

        if (debug) {
            System.out.printf("%o", res);
        }
    }

    public static double[][] multiThread(double[][] tab1, double[][] tab2, int lenx, boolean virtualThread) {
        final int len0 = lenx;
        List<Future<Double>> listeResultat = new ArrayList<>();

        double[][] res = new double[lenx][lenx];

        ExecutorService executor;
        if (virtualThread) {
            executor = Executors.newVirtualThreadPerTaskExecutor();
        } else {
            executor = Executors.newCachedThreadPool();
        }

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

        return res;
    }

    public static void multiThread(List<String> args) {
        int lenx = 3;
        var debug = false;
        var thread = false;

        if (args != null && args.size() > 0) {
            var iter = args.iterator();
            while (iter.hasNext()) {
                var s = iter.next();
                if (Objects.equals(s, "--debug")) {
                    debug = true;
                    iter.remove();
                } else if (Objects.equals(s, "--thread")) {
                    thread = true;
                    iter.remove();
                }
            }
        }

        if (args != null && args.size() > 0) {
            var s = args.getFirst();
            var n = Integer.parseInt(s);
            if (n > 0) {
                lenx = n;
            }
        }
        if (debug) {
            System.out.println("len=" + lenx);
        }

        double[][] tab1 = initialisation(lenx, false);
        double[][] tab2 = initialisation(lenx, true);

        var res = multiThread(tab1, tab2, lenx, !thread);

        if (debug) {
            System.out.printf("%s", Arrays.deepToString(res));
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
