package com.zenika.sensit.resources;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nitorcreations.junit.runners.NestedRunner;
import com.zenika.sensit.Sensit;
import com.zenika.sensit.resources.Sensor.SensorType;

@RunWith(NestedRunner.class)
public class DeviceTest {

    private final String deviceId = "";
    private final String authToken = "";

    private Device device;
    private Sensit sensit;


    @Before
    public void beforeEach() {
        sensit = new Sensit(authToken);
        device = sensit.getDevice(deviceId).getData();
    }

    public class GetMotionSensorMethod {

        @Test
        public void shouldReturnSensorWithCorrectType() {
            Sensor s = device.getMotionSensor();
            assertThat(s.getSensorType()).isEqualTo(SensorType.MOTION.getLabel());
        }

        @Test
        public void shouldNotReturnNull() {
            Sensor s = device.getMotionSensor();
            assertThat(s).isNotNull();
        }

        @Test
        public void shouldReturnSensorWithNoHistory() {
            Sensor s = device.getMotionSensor();
            assertThat(s.getHistory()).isNotNull();
            assertThat(s.getHistory()).isEmpty();
        }

    }

}
