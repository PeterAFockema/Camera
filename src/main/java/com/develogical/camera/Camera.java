package com.develogical.camera;

import com.sun.org.apache.xpath.internal.operations.Bool;

public class Camera {

    private final Sensor sensor;
    private final MemoryCard memoryCard;
    private Boolean amITheCameraOn = false;
    private Boolean writingData = false;
    private Boolean offRequested = false;
    private final WriteCompleteListener writeCompleteListener = new Listener(this);

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
            this.writingData = true;
            this.memoryCard.write(this.sensor.readData(), this.writeCompleteListener);
        }
    }

    public void powerOn() {
        this.amITheCameraOn = true;
        this.sensor.powerUp();
    }

    public void powerOff() {
        if (!this.writingData) {
            this.amITheCameraOn = false;
            this.sensor.powerDown();
        } else {
            this.offRequested = true;
        }
    }

    public void shutDownIfRequested() {
        this.writingData = false;
//        if (this.offRequested) {
            this.powerOff();
//        }
    }
}

