package io.github.liticane.monoxide.listener.event.minecraft.player.movement;

import io.github.liticane.monoxide.listener.event.Event;

public class UpdateMotionEvent extends Event {
    final Type type;
    boolean isCurrentView;

    public enum Type {
        PRE, MID, POST
    }

    public UpdateMotionEvent(Type type, boolean isCurrentView) {
        this.type = type;
        this.isCurrentView = isCurrentView;
    }

    public Type getType() {
        return type;
    }

    public boolean isCurrentView() {
        return isCurrentView;
    }
}