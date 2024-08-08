package in.momin5.cookieclient.api.setting.settings;

import com.lukflug.panelstudio.settings.NumberSetting;
import in.momin5.cookieclient.CookieClient;
import in.momin5.cookieclient.api.module.Module;
import in.momin5.cookieclient.api.setting.Setting;

public class SettingNumber extends Setting implements NumberSetting {
    public double value;

    public double minimum;

    public double maximum;

    public double increment;

    public SettingNumber(String name, Module parent, double value, double minimun, double maximum, double increment) {
        super(name,parent);
        this.value = value;
        this.minimum = minimun;
        this.maximum = maximum;
        this.increment = increment;
    }

    public double getValue() {
        return this.value;
    }

    public void setValue(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;

        if(CookieClient.configSave != null) {
            CookieClient.configSave.save();
        }
    }

    public void increment(boolean positive) {
        setValue(getValue() + (positive ? 1 : -1) * increment);
    }

    public double getMinimum() {
        return this.minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return this.maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }

    public double getIncrement() {
        return this.increment;
    }

    public void setIncrement(double increment) {
        this.increment = increment;
    }

    @Override
    public double getMaximumValue() {
        return this.maximum;
    }

    @Override
    public double getMinimumValue() {
        return this.minimum;
    }

    @Override
    public double getNumber() {
        return this.value;
    }

    @Override
    public int getPrecision() {
        return 1;
    }

    @Override
    public void setNumber(double value) {
        double precision = 1.0D / this.increment;
        this.value = Math.round(Math.max(this.minimum, Math.min(this.maximum, value)) * precision) / precision;
    }
}
