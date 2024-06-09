package lunadevs.luna.utils.Values;

import java.util.ArrayList;

public class RestrictedValue {
    private double value, min, max;
    private String name;
    private static ArrayList<RestrictedValue> vals = new ArrayList<RestrictedValue>();

    public static ArrayList<RestrictedValue> getVals() {
        return vals;
    }

    public RestrictedValue(String name, double value, double min, double max) {
        this.setName(name);
        this.setValue(value);
        this.setMin(min);
        this.setMax(max);
        this.getVals().add(this);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }
}