package com.develogical.camera;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;

import static org.mockito.Mockito.*;

public class CameraTest {

    private Sensor sensor;
    private Camera underTest;

    @Before
    public void init() {
        this.sensor = mock(Sensor.class);
        this.underTest = new Camera(sensor);
    }

    @Test
    public void switchingTheCameraOnPowersUpTheSensor() {
        this.underTest.powerOn();
        verify(this.sensor).powerUp();
    }

    @Test
    public void switchingTheCameraOffPowersDownTheSensor() {
        this.underTest.powerOff();
        verify(this.sensor).powerDown();
    }
}
