package io.github.liticane.clients.feature.property.impl;

import io.github.liticane.clients.feature.property.Property;
import io.github.liticane.clients.feature.module.Module;

import java.util.function.Supplier;

public class NumberProperty extends Property {
    public double val, min, max, inc;
    public float[] flt = new float[1];

    public NumberProperty(String name, Module parent, double val, double min, double max, double inc) {
        this.name = name;
        this.val = val;
        this.min = min;
        this.max = max;
        this.inc = inc;
        parent.getProperties().add(this);
    }

    public NumberProperty(String name, Module parent, double val, double min, double max, double inc, Supplier<Boolean> visible) {
        this.name = name;
        this.val = val;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.visible = visible;
        parent.getProperties().add(this);
    }

    public void setValue(double value) {
        double prec = 1 / inc;
        this.val = Math.round(Math.max(min, Math.min(max, value)) * prec) / prec;
    }

    public double getValue() {
        return val;
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public double getInc() {
        return inc;
    }

    public float[] getFlt() {
        return flt;
    }

    public void setVal(double val) {
        this.val = val;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public void setInc(double inc) {
        this.inc = inc;
    }

    public void setFlt(float[] flt) {
        this.flt = flt;
    }
}