package me.satisfactory.base.setting;

import me.satisfactory.base.module.*;
import java.util.*;
import me.satisfactory.base.hero.settings.*;

public class Setting
{
    private String name;
    private Module parent;
    private String sval;
    private Mode mode;
    private ArrayList<String> options;
    private boolean bval;
    private double dval;
    private double min;
    private double max;
    private boolean onlyint;
    private double upValue;
    
    public Setting(final String name, final Module parent, final String sval, final ArrayList<String> options) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.sval = sval;
        this.options = options;
        this.mode = Mode.COMBO;
    }
    
    public Setting(final String name, final Module parent, final boolean bval) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.bval = bval;
        this.mode = Mode.CHECK;
    }
    
    public Setting(final String name, final Module parent, final double dval, final double min, final double max, final boolean isInt, final double upValue) {
        this.onlyint = false;
        this.name = name;
        this.parent = parent;
        this.dval = dval;
        this.min = min;
        this.max = max;
        this.onlyint = isInt;
        this.upValue = upValue;
        this.mode = Mode.SLIDER;
    }
    
    public String getName() {
        return this.name;
    }
    
    public Module getParentMod() {
        return this.parent;
    }
    
    public ArrayList<String> getOptions() {
        return this.options;
    }
    
    public int currentIndex() {
        return this.options.indexOf(this.sval);
    }
    
    public String getValString() {
        return this.sval.substring(0, 1).toUpperCase() + this.sval.substring(1, this.sval.length());
    }
    
    public void setValString(final String in) {
        this.sval = in;
        SettingsManager.save();
    }
    
    public String getValStringForSaving() {
        return this.sval;
    }
    
    public boolean booleanValue() {
        return this.bval;
    }
    
    public void setValBoolean(final boolean in) {
        this.bval = in;
        SettingsManager.save();
    }
    
    public double doubleValue() {
        if (this.onlyint) {
            this.dval = (int)this.dval;
        }
        return this.dval;
    }
    
    public void setValDouble(final double in) {
        this.dval = in;
        SettingsManager.save();
    }
    
    public double getUpValue() {
        return this.upValue;
    }
    
    public void setUpValue(final double up) {
        this.upValue = up;
        SettingsManager.save();
    }
    
    public double getMin() {
        return this.min;
    }
    
    public double getMax() {
        return this.max;
    }
    
    public boolean isCombo() {
        return this.mode.equals(Mode.COMBO);
    }
    
    public boolean isCheck() {
        return this.mode.equals(Mode.CHECK);
    }
    
    public boolean isSlider() {
        return this.mode.equals(Mode.SLIDER);
    }
    
    public boolean onlyInt() {
        return this.onlyint;
    }
    
    private enum Mode
    {
        COMBO, 
        CHECK, 
        SLIDER;
    }
}
