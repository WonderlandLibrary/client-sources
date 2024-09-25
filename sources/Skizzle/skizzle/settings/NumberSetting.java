/*
 * Decompiled with CFR 0.150.
 */
package skizzle.settings;

import skizzle.files.FileManager;
import skizzle.settings.Setting;

public class NumberSetting
extends Setting {
    public double sliderValue;
    public double increment;
    public double maximum;
    public double value;
    public double minimum;

    public void setMinimum(double Nigga) {
        Nigga.minimum = Nigga;
    }

    public void setValue(double Nigga) {
        NumberSetting Nigga2;
        double Nigga3 = 1.0 / Nigga2.increment;
        Nigga2.value = (double)Math.round(Math.max(Nigga2.minimum, Math.min(Nigga2.maximum, Nigga)) * Nigga3) / Nigga3;
        FileManager Nigga4 = new FileManager();
        Nigga4.updateSettings();
    }

    public void setIncrement(double Nigga) {
        Nigga.increment = Nigga;
    }

    public double getIncrement() {
        NumberSetting Nigga;
        return Nigga.increment;
    }

    public void setMaximum(double Nigga) {
        Nigga.maximum = Nigga;
    }

    public static {
        throw throwable;
    }

    public NumberSetting(String Nigga, double Nigga2, double Nigga3, double Nigga4, double Nigga5) {
        NumberSetting Nigga6;
        Nigga6.name = Nigga;
        Nigga6.value = Nigga2;
        Nigga6.sliderValue = Nigga2;
        Nigga6.minimum = Nigga3;
        Nigga6.maximum = Nigga4;
        Nigga6.increment = Nigga5;
    }

    public double getMinimum() {
        NumberSetting Nigga;
        return Nigga.minimum;
    }

    public void increment(boolean Nigga) {
        NumberSetting Nigga2;
        Nigga2.setValue(Nigga2.getValue() + (double)(Nigga ? 1 : -1) * Nigga2.increment);
    }

    public double getValue() {
        NumberSetting Nigga;
        return Nigga.value;
    }

    public double getMaximum() {
        NumberSetting Nigga;
        return Nigga.maximum;
    }
}

