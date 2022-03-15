package com.develogical.camera;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard memoryCard;
    private Boolean amITheCameraOn = false;

    public Camera(final Sensor sensor) {
        this.sensor = sensor;
        this.memoryCard = null;
    }

    public Camera(final Sensor sensor, final MemoryCard memoryCard) {
        this.sensor = sensor;
        this.memoryCard = memoryCard;
    }

    public void pressShutter() {
        if (this.memoryCard != null && this.amITheCameraOn) {
            this.memoryCard.write(this.sensor.readData(), null);
        }
    }

    public void powerOn() {
        this.amITheCameraOn = true;
        this.sensor.powerUp();
    }

    public void powerOff() {
        this.amITheCameraOn = false;
       this.sensor.powerDown();
    }
}

