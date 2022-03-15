package com.develogical.camera;

public class Camera {

    private final Sensor sensor;

    public Camera(final Sensor sensor) {
        this.sensor = sensor;
    }

    public void pressShutter() {
        // not implemented
    }

    public void powerOn() {
        this.sensor.powerUp();
    }

    public void powerOff() {
       this.sensor.powerDown();
    }
}

