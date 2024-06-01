package best.actinium.property.impl;

import best.actinium.module.Module;
import best.actinium.property.Property;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BooleanProperty extends Property {

    private boolean enabled;

    public BooleanProperty(String name, Module parent, boolean enabled) {
        super(name, parent);
        this.enabled = enabled;
    }

    public void toggle() {
        setEnabled(!isEnabled());
    }

}