package io.github.liticane.clients.feature.property.impl;

import io.github.liticane.clients.feature.module.Module;
import io.github.liticane.clients.feature.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class StringProperty extends Property {

    public final List<String> modes;
    private int index;

    private String mode;

    public StringProperty(String name, Module parent, String mode, String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.mode = mode;
        this.index = this.modes.indexOf(mode);

        parent.getProperties().add(this);
    }

    public boolean is(String mode) {
        return this.mode.equalsIgnoreCase(mode);
    }

}