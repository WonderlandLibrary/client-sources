/*
 * Decompiled with CFR 0.152.
 */
package cc.diablo.setting.impl;

import cc.diablo.setting.Setting;

public class NumberSetting
extends Setting {
    public double val;
    public double min;
    public double max;
    public double inc;
    public double def;
    public float value;

    public NumberSetting(String name, double val, double min, double max, double inc) {
        this.name = name;
        this.def = val;
        this.val = val;
        this.min = min;
        this.max = max;
        this.inc = inc;
        this.value = (float)val;
    }

    public double getVal() {
        return this.val;
    }

    public float getValue() {
        return this.value;
    }

    public double getDefault() {
        return this.def;
    }

    public void increment(boolean pos) {
        this.setValue(this.getVal() + (double)(pos ? 1 : -1) * this.inc);
    }

    public void setValue(double val) {
        double pres = 1.0 / this.inc;
        this.val = (double)Math.round(Math.max(this.getMin(), Math.min(this.getMax(), val)) * pres) / pres;
        this.value = (float)this.val;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public double getInc() {
        return this.inc;
    }

    public double getDef() {
        return this.def;
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

    public void setDef(double def) {
        this.def = def;
    }
}

