package com.zenika.sensit;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.nitorcreations.junit.runners.NestedRunner;
import com.zenika.sensit.resources.Device;
import com.zenika.sensit.resources.Sensor;

@RunWith(NestedRunner.class)
public class SensitTest {

    private Sensit sensit = new Sensit();

    private final String deviceId = "";
    private final String temperatureSensorId = "";
    private final String authToken = "";

    @Before
    public void beforeEach() {
        sensit.setToken(authToken);
    }

    public class WhenNotAuthenticated {
        @Test
        public void allMethodsShouldThrowNotAuthenticatedException() {
            sensit = new Sensit();
            assertThatExceptionOfType(NotAuthenticatedException.class).isThrownBy(() -> sensit.getDevice(""));
            assertThatExceptionOfType(NotAuthenticatedException.class).isThrownBy(() -> sensit.getAllDevices());
            assertThatExceptionOfType(NotAuthenticatedException.class).isThrownBy(() -> sensit.getSensor("", ""));
        }
    }

    public class GetDeviceMethod {

        public class WhenIdIsIncorrect {
            @Test
            public void shouldReturnNull() {
                Device d = sensit.getDevice("incorrectId").getData();
                assertThat(d).isNull();
            }
        }

        public class InTheNominalCase {
            Device outputDevice;

            @Before
            public void beforeEach() {
                outputDevice = sensit.getDevice(deviceId).getData();
            }

            @Test
            public void shouldNotReturnNull() {
                assertThat(outputDevice).isNotNull();
            }

            @Test
            public void resultShouldContainSensors() {
                assertThat(outputDevice.getSensors()).isNotNull();
                assertThat(outputDevice.getSensors()).isNotEmpty();
            }

            @Test
            public void resultShouldHaveTheCorrectId() {
                assertThat(outputDevice.getId()).isEqualTo(deviceId);
            }

            @Test
            public void resultShouldHaveMandatoryAttributesFilled() {
                assertThat(outputDevice.getDeviceModel()).isNotNull();
                assertThat(outputDevice.getActivationDate()).isNotNull();
                assertThat(outputDevice.getLastCommDate()).isNotNull();
                assertThat(outputDevice.getBattery()).isNotNull();
                assertThat(outputDevice.getLastConfigDate()).isNotNull();
                assertThat(outputDevice.getSerialNumber()).isNotNull();
                assertThat(outputDevice.getMode()).isNotNull();
            }
        }

    }

    public class GetAllDevicesMethod {

        List<Device> outputDevices;

        @Before
        public void beforeEach() {
            outputDevices = sensit.getAllDevices().getData();
        }

        @Test
        public void shouldNotReturnNull() {
            assertThat(outputDevices).isNotNull();
        }

        @Test
        public void resultShouldContainAtLeastOneDevice() {
            assertThat(outputDevices).isNotEmpty();
        }
    }

    public class GetSensorMethod {

        public class WhenIdIsIncorrect {
            @Test
            public void shouldReturnNull() {
                Sensor s = sensit.getSensor(deviceId, "incorrectId").getData();
                assertThat(s).isNull();
            }
        }

        public class InTheNominalCase {

            private Sensor s;

            @Before
            public void beforeEach() {
                s = sensit.getSensor(deviceId, temperatureSensorId).getData();
            }

            @Test
            public void shouldReturnSensorWithCorrectId() {
                assertThat(s.getId()).isEqualTo(temperatureSensorId);
            }

            @Test
            public void shouldNotReturnNull() {
                assertThat(s).isNotNull();
            }

            @Test
            public void shouldReturnDataHistory() {
                assertThat(s.getHistory()).isNotNull();
                assertThat(s.getHistory()).isNotEmpty();
            }

            @Test
            public void shouldReturnDataWithDate() {
                assertThat(s.getHistory().get(0).getDate()).isNotNull();
            }

        }

    }

}
