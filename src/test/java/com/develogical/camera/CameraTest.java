package com.develogical.camera;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import static org.mockito.Mockito.*;

public class CameraTest {

    public static final byte[] RETURN_VALUE = new byte[]{};
    private Sensor sensor;
    private Camera underTest;
    private MemoryCard memoryCard;
    private Camera underTestWithCard;

    @Before
    public void init() {
        this.sensor = mock(Sensor.class);
        this.memoryCard = mock(MemoryCard.class);
        this.underTest = new Camera(sensor);
        this.underTestWithCard = new Camera(this.sensor, this.memoryCard);
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

    @Test
    public void pressingTheShutterWithPowerOnCopiesDataFromTheSensorToTheMemory() {
        when(this.sensor.readData()).thenReturn(RETURN_VALUE);

        this.underTestWithCard.powerOn();
        this.underTestWithCard.pressShutter();
        verify(this.memoryCard).write(Mockito.eq(RETURN_VALUE), Mockito.any());
    }

   // @Test
    //public void pressingTheShutterWithPowerNotOnCopiesDataFromTheSensorToTheMemory() {
    //    this.underTest.pressShutter();
    //    byte[] returnValue = new byte[]{};
    //   when(sensor.readData()).thenReturn(returnValue);
   //     verifyNoMoreInteractions(this.memoryCard);
    //}
}
