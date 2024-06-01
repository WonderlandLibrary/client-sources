package best.actinium.property.impl;

import best.actinium.module.Module;
import best.actinium.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class InfoStringProperty extends Property {
    private boolean line;

    public InfoStringProperty(String name, Module parent,boolean difline) {
        super(name, parent);
        line = difline;
    }
}
