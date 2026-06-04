package com.exemple;

public class HelloWorld {

    private static void helloworld(){
        System.out.println("Hello World !");
    }

    private static void multi(String[] args){

        int lenx=3;

        if(args!=null&&args.length>0){
            var s=args[0];
            var n=Integer.parseInt(s);
            if(n>0){
                lenx=n;
            }
        }
        System.out.println("len="+lenx);

        double[][] tab1=new double[lenx][lenx];
        double[][] tab2=new double[lenx][lenx];
        double[][] res=new double[lenx][lenx];

        for(int i=0;i<lenx;i++){

            for(int j=0;j<lenx;j++){

                double m=0.0;
                for(int k=0;k<lenx;k++){
                    m+=tab1[i][k]*tab2[k][j];
                }
                res[i][j]=m;

            }

        }

    }

    public static void main(String[] args) {
        
        if(false){
            helloworld();
        } else if(true) {
            multi(args);
        }
    }

}
