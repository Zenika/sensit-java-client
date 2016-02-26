package com.zenika.sensit.resources;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Sensor resource of the sensit api.
 * 
 * @see http://api.sensit.io/v1
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown=true)
public class Sensor {
    
    public List<Measure> history = new ArrayList<Measure>();
    public String id;
    public String sensorType;
    public Config config;

    @Getter
    @Setter
    @NoArgsConstructor
    public static class Config {
        public String threshold;
        public String period;
    }
    
    @AllArgsConstructor
    public enum SensorType {
        MOTION("motion"), TEMPERATURE("temperature"), BUTTON("button");
        
        @Getter
        private String label;
    }
}
