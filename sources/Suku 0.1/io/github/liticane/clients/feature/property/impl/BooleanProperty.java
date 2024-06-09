package io.github.liticane.clients.feature.property.impl;

import io.github.liticane.clients.feature.property.Property;
import io.github.liticane.clients.feature.module.Module;
import lombok.Getter;
import lombok.Setter;

import java.util.function.Supplier;

@Setter
@Getter
public class BooleanProperty extends Property {
    private boolean toggled;

    public BooleanProperty(String name, Module parent, boolean toggled) {
        this.name = name;
        this.toggled = toggled;
        parent.getProperties().add(this);
    }

    public BooleanProperty(String name, Module parent, boolean toggled, Supplier<Boolean> visible) {
        this.name = name;
        this.toggled = toggled;
        this.visible = visible;
        parent.getProperties().add(this);
    }

}