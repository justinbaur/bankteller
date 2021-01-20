package org.justinbaur.bankteller.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Time {
    private static Time time = null;

    private static final Logger LOG = LoggerFactory.getLogger(Time.class);

    private Time(){

    }

    public static Time getInstance() {
        LOG.info("Getting time instance");
        if (time == null){
            time = new Time();
            LOG.info("Creating time instance");
        }
        return time;
    }

    public String printTime(){
        return LocalDateTime.now().toString();
    }

}
