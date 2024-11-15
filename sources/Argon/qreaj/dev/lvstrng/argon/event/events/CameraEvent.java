package dev.lvstrng.argon.event.events;

import dev.lvstrng.argon.event.CancellableEvent;
import dev.lvstrng.argon.event.EventListener;
import dev.lvstrng.argon.event.listeners.CameraListener;

import java.util.ArrayList;

public class CameraEvent extends CancellableEvent {
    public double x;
    public double y;
    public double z;

    public CameraEvent(final double x, final double y, final double z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    @Override
    public void callListeners(final ArrayList<EventListener> listeners) {
        listeners.stream().filter(listener -> listener instanceof CameraListener).map(listener -> (CameraListener) listener).forEach(listener -> listener.onCamera(this));
    }

    @Override
    public Class<?> getClazz() {
        return CameraListener.class;
    }

    public double getX() {
        return this.x;
    }

    public void setX(final double x) {
        this.x = x;
    }

    public double getY() {
        return this.y;
    }

    public void setY(final double y) {
        this.y = y;
    }

    public double getZ() {
        return this.z;
    }

    public void setZ(final double z) {
        this.z = z;
    }
}
