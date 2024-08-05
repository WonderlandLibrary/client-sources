package studio.dreamys.setting;

import studio.dreamys.module.Module;
import studio.dreamys.near;

import java.util.ArrayList;

@SuppressWarnings("unused")
public class Setting {
    private final String name;
    private final Module parent;
    private final String mode;

    private String sval;
    private ArrayList<String> options;

    private boolean bval;

    private double dval;
    private double min;
    private double max;
    private boolean onlyint;

    public Setting(String name, Module parent, String sval, ArrayList<String> options) {
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        mode = "Combo";
    }

    public Setting(String name, Module parent, boolean bval) {
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        mode = "Check";
    }

    public Setting(String name, Module parent, double dval, double min, double max, boolean onlyint) {
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = onlyint;
        mode = "Slider";
    }

    public String getName() {
        return name;
    }

    public Module getParentMod() {
        return parent;
    }

    public String getValString() {
        return sval;
    }

    public void setValString(String in) {
        sval = in;
        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }

    public ArrayList<String> getOptions() {
        return options;
    }

    public boolean getValBoolean() {
        return bval;
    }

    public void setValBoolean(boolean in) {
        bval = in;
        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }

    public double getValDouble() {
        if (onlyint) {
            dval = (int) dval;
        }
        return dval;
    }

    public void setValDouble(double in) {
        dval = in;
        if (near.saveLoad != null) {
            near.saveLoad.save();
        }
    }

    public double getMin() {
        return min;
    }

    public double getMax() {
        return max;
    }

    public boolean isCombo() {
        return mode.equalsIgnoreCase("Combo");
    }

    public boolean isCheck() {
        return mode.equalsIgnoreCase("Check");
    }

    public boolean isSlider() {
        return mode.equalsIgnoreCase("Slider");
    }

}
