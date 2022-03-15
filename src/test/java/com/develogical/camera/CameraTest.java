package com.develogical.camera;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
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

    @Test
    public void pressingTheShutterWithPowerOffCopiesNoDataFromTheSensorToTheMemory() {
        this.underTestWithCard.pressShutter();
        verifyNoMoreInteractions(this.memoryCard);

        this.underTestWithCard.powerOff();
        this.underTestWithCard.pressShutter();
        verifyNoMoreInteractions(this.memoryCard);

        this.underTestWithCard.powerOn();
        this.underTestWithCard.powerOff();
        this.underTestWithCard.pressShutter();
        verifyNoMoreInteractions(this.memoryCard);
    }

    @Test
    public void ifTheDataIsCurrentlyBeingWrittenSwitchingTheCameraOffDoesNotPowerDownTheSensor() {
        ArgumentCaptor<WriteCompleteListener> writeCompleteListenerArgumentCaptor
                = ArgumentCaptor.forClass(WriteCompleteListener.class);

        this.underTestWithCard.powerOn();
        this.underTestWithCard.pressShutter();
        this.underTestWithCard.powerOff();

        verify(this.memoryCard).write(any(), writeCompleteListenerArgumentCaptor.capture());
        WriteCompleteListener writeCompleteListener = writeCompleteListenerArgumentCaptor.getValue();
        verify(this.sensor, times(0)).powerDown();

        //Still writing
        writeCompleteListener.writeComplete();

        //Finished writing
        verify(this.sensor, times(1)).powerDown();

    }

    @Test
    public void takingPhotoDoesNotPowerDownCamera() {
        ArgumentCaptor<WriteCompleteListener> writeCompleteListenerArgumentCaptor
                = ArgumentCaptor.forClass(WriteCompleteListener.class);

        this.underTestWithCard.powerOn();
        this.underTestWithCard.pressShutter();

        verify(this.memoryCard).write(any(), writeCompleteListenerArgumentCaptor.capture());
        WriteCompleteListener writeCompleteListener = writeCompleteListenerArgumentCaptor.getValue();

        //Still writing
        writeCompleteListener.writeComplete();

        verify(this.sensor, times(0)).powerDown();

    }

}
