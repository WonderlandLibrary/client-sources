package koks.gui.customhud.valuehudsystem;

import koks.gui.customhud.Component;

/**
 * @author avox | lmao | kroko
 * @created on 06.09.2020 : 03:34
 */
public class ValueHUD {

    private String valueName;
    private String selectedComboMode;
    private String[] comboArray;
    private Type type;
    private boolean onlyInt;

    private boolean toggled;
    private Component component;

    private float current, min, max;

    public ValueHUD(String valueName, String selectedComboMode, String[] comboArray, Component component) {
        this.valueName = valueName;
        this.selectedComboMode = selectedComboMode;
        this.comboArray = comboArray;
        this.component = component;
        this.type = Type.COMBO;
    }

    public ValueHUD(String valueName, float current, float min, float max, boolean onlyInt, Component component) {
        this.valueName = valueName;
        this.current = current;
        this.min = min;
        this.max = max;
        this.onlyInt = onlyInt;
        this.component = component;
        this.type = Type.SLIDER;
    }

    public ValueHUD(String valueName, boolean toggled, Component component) {
        this.valueName = valueName;
        this.toggled = toggled;
        this.type = Type.CHECKBOX;
        this.component = component;
    }

    public enum Type {
        COMBO,
        CHECKBOX,
        SLIDER;
    }

    public boolean isOnlyInt() {
        return onlyInt;
    }

    public void setOnlyInt(boolean onlyInt) {
        this.onlyInt = onlyInt;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    public String getValueName() {
        return valueName;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public String getSelectedComboMode() {
        return selectedComboMode;
    }

    public void setSelectedComboMode(String selectedComboMode) {
        this.selectedComboMode = selectedComboMode;
    }

    public String[] getComboArray() {
        return comboArray;
    }

    public void setComboArray(String[] comboArray) {
        this.comboArray = comboArray;
    }

    public boolean isToggled() {
        return toggled;
    }

    public float getCurrent() {
        return current;
    }

    public void setCurrent(float current) {
        this.current = current;
    }

    public float getMin() {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public float getMax() {
        return max;
    }

    public void setMax(float max) {
        this.max = max;
    }

    public void setToggled(boolean toggled) {
        this.toggled = toggled;
    }
}
