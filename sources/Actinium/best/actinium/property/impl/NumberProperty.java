package best.actinium.property.impl;

import best.actinium.module.Module;
import best.actinium.property.Property;
import imgui.type.ImFloat;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NumberProperty extends Property {

    public Double min, value, max, increment;
    public ImFloat imFloat;

    public NumberProperty(String name, Module parent, double min, double value, double max, double increment) {
        super(name, parent);
        this.min = min;
        this.value = value;
        this.max = max;
        this.increment = increment;
        this.imFloat = new ImFloat((float) value);
    }

    public void setValue(double value) {
        double precision = 1 / increment;
        this.value = Math.round(Math.max(min, Math.min(max, value)) * precision) / precision;
    }

}