package org.justinbaur.bankteller.service;

import java.time.LocalDateTime;

public class Time {
    private static Time time = null;

    private Time(){

    }

    public static Time getInstance() {
        System.out.println("Getting time instance");
        if (time == null){
            time = new Time();
            System.out.println("Creating time instance");
        }
        return time;
    }

    public String printTime(){
        return LocalDateTime.now().toString();
    }

}
