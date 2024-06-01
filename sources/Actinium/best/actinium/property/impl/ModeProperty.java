package best.actinium.property.impl;

import best.actinium.module.Module;
import best.actinium.property.Property;
import lombok.Getter;
import lombok.Setter;

import java.util.Arrays;
import java.util.List;

@Getter
@Setter
public class ModeProperty extends Property {

    private final List<String> modes;
    private String mode;

    public int index;
    public ModeProperty(String name, Module parent, String[] modes, String mode) {
        super(name, parent);
        this.modes = Arrays.asList(modes);
        this.mode = mode;
        this.index = this.modes.indexOf(mode);
    }

    public boolean is(String mode) {
        return this.mode.equalsIgnoreCase(mode);
    }

}