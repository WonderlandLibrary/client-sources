package me.teus.eclipse.modules.value.impl;

import lombok.Getter;
import lombok.Setter;
import me.teus.eclipse.modules.value.Value;
@Getter
@Setter
public class NumberValue extends Value {
    public double value, min, max, inc;
    public NumberValue(String name, double value, double min, double max, double inc) {
        super(name, false);
        this.name = name;
        this.value = value;
        this.min = min;
        this.max = max;
        this.inc = inc;
    }

    public void setValue(double value){
        double prec = 1.0d / this.inc;
        this.value = Math.round(Math.max(this.min, Math.min(this.max, value)) * prec) / prec;
    }
}
