package best.actinium.property.impl;

import best.actinium.module.Module;
import best.actinium.property.Property;
import imgui.type.ImString;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InputProperty extends Property {

    private String input;
    private ImString imString;

    public InputProperty(String name, Module parent) {
        super(name, parent);
        this.input = "";
        this.imString = new ImString(this.input, 512);
    }

    public InputProperty(String name, Module parent, String value) {
        super(name, parent);
        this.input = value;
        this.imString = new ImString(this.input, 512);
    }

}
