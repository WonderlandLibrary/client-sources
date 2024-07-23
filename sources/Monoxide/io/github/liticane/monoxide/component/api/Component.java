package io.github.liticane.monoxide.component.api;

import io.github.liticane.monoxide.listener.handling.EventHandling;
import io.github.liticane.monoxide.util.interfaces.Methods;

public class Component implements Methods {

    private final String name;
    private boolean running = false;

    public Component() {
        ComponentInfo componentInfo = this.getClass().getAnnotation(ComponentInfo.class);
        if(componentInfo == null)
            throw new RuntimeException();
        this.name = componentInfo.name();
    }

    public void launch() {
        EventHandling.getInstance().registerListener(this);
        running = true;
    }

    public void end() {
        EventHandling.getInstance().unregisterListener(this);
        running = true;
    }

}
