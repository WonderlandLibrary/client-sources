/*
 * Decompiled with CFR 0_122.
 */
package winter.utils.value.types;

import winter.Client;
import winter.module.modules.Nametags;
import winter.utils.file.FileManager;
import winter.utils.value.Value;

public class NumberValue
extends Value {
    private String name;
    private double value;
    private double max;
    private double min;
    private double increment;

    public NumberValue(String name, double value, double min, double max, double increment) {
        super(name);
        this.name = name;
        this.value = value;
        this.max = max;
        this.min = min;
        this.increment = increment;
    }

    @Override
    public String getName() {
        return this.name;
    }

    public double getValue() {
        return Nametags.roundToPlace(this.value, 2);
    }

    public double getIncrement() {
        return this.increment;
    }

    public double getMin() {
        return this.min;
    }

    public double getMax() {
        return this.max;
    }

    public void increase() {
        if (Nametags.roundToPlace(this.value, 2) < this.max) {
            this.value += this.increment;
        }
        if (Client.fileManager != null) {
            Client.fileManager.saveFiles();
        }
    }

    public void decrease() {
        if (Nametags.roundToPlace(this.value, 2) > this.min) {
            this.value -= this.increment;
        }
    }

    public void setVal(double newv) {
        newv = NumberValue.clamp_number(newv, this.min, this.max);
        this.value = newv = (double)Math.round(newv * (1.0 / this.increment)) / (1.0 / this.increment);
    }

    public static double clamp_number(double value, double min, double max) {
        value = Math.max(min, value);
        value = Math.min(max, value);
        return value;
    }
}

