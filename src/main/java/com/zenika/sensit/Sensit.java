package com.zenika.sensit;

import java.util.List;

import javax.ws.rs.core.GenericType;

import com.zenika.sensit.resources.Device;
import com.zenika.sensit.resources.SensitResponse;
import com.zenika.sensit.resources.Sensor;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class Sensit {

    private SensitApiClient rest = new SensitApiClient();
    
    public Sensit(String token) {
        this.setToken(token);
    }
    
    /** Set the authorization token to use to authenticate to the sensit api. */
    public Sensit setToken(String token) {
        this.rest.addDefaultHeader("Authorization", "Bearer "+token);
        return this;
    }

    /**
     * Retrieve the device with the given id.
     * @param id
     * @return
     */
    public SensitResponse<Device> getDevice(String id) {
        SensitResponse<Device> response = rest.get("/devices/"+id, new GenericType<SensitResponse<Device>>() {});
        return response;
    }
    
    /**
     * Retrieve all devices
     */
    public SensitResponse<List<Device>> getAllDevices() {
        SensitResponse<List<Device>> response = rest.get("/devices", new GenericType<SensitResponse<List<Device>>>() {});
        return response;
    }
    
    /**
     * Retrieve a sensor by device id and sensor id.
     * 
     * @param deviceId ID of the device the sensor belongs to.
     * @param sensorId ID of the sensor
     */
    public SensitResponse<Sensor> getSensor(String deviceId, String sensorId) {
        return rest.get("/devices/"+deviceId+"/sensors/"+sensorId, new GenericType<SensitResponse<Sensor>>() {});
    }

}
