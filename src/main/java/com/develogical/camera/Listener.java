package com.develogical.camera;

public class Listener implements WriteCompleteListener{

    final Camera camera;

    public Listener(final Camera camera) {
        this.camera = camera;
    }

    @Override
    public void writeComplete() {
        camera.shutDownIfRequested();
    }
}
