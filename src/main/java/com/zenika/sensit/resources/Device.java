package com.zenika.sensit.resources;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.zenika.sensit.resources.Sensor.SensorType;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * Device resource of the sensit api.
 * 
 * @see http://api.sensit.io/v1
 */
@Getter
@Setter
@NoArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class Device {
    public List<Sensor> sensors = new ArrayList<Sensor>();
    public String id;
    public String deviceModel;
    public String activationDate;
    public String configStatus;
    public String lastCommDate;
    public Integer battery;
    public String lastConfigDate;
    public String serialNumber;
    public Integer mode;

    /**
     * Return the motion sensor of this device.
     */
    public Sensor getMotionSensor() {
        
        
        Sensor motionSensor = this.sensors.stream()
                .filter(s -> s.getSensorType().equals(SensorType.MOTION.getLabel()))
                .findFirst()
                .orElse(null);
        
        return motionSensor;
    }

    public Sensor getTemperatureSensor() {
        Sensor temperatureSensor = this.sensors.stream()
                .filter(s -> s.getSensorType().equals(SensorType.TEMPERATURE.getLabel()))
                .findFirst()
                .orElse(null);
        
        return temperatureSensor;
    }
}