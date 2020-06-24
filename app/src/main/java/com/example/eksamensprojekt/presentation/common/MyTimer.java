package com.example.eksamensprojekt.presentation.common;

public class MyTimer {

    private static long startTimeStamp = 0;

    // statisk metode start
    public static void start() {
        startTimeStamp = System.currentTimeMillis();
    }

    // statisk metode stop
    public static void stop() {
        long stopTimeStamp = System.currentTimeMillis();
        long execTime = stopTimeStamp - startTimeStamp;
        System.out.println("Exec time: " +  execTime + "ms");
    }


    /**
     * Denne metode skriver tiden til konsollen
     * */
    public static void timestamp2Console(){
        long timeStamp = System.currentTimeMillis();
        System.out.println(timeStamp);
        //return System.nanoTime();
    }

    public static long currentTimestamp(){
        long timeStamp = System.currentTimeMillis();
        return timeStamp;
    }

    public static String timeElapsed(long start, long end) {
        long elapsedTime =  end-start;
        return "The function took " + elapsedTime + " milliseconds to execute.";
    }





}