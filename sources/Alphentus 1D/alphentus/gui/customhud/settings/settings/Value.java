package alphentus.gui.customhud.settings.settings;

/**
 * @author avox | lmao
 * @since on 13.08.2020.
 */
public class Value {

    private ValueTab valueTab;
    private String valueIdentifier;

    private String valueName;
    private String currentMode;
    private String[] modes;

    private boolean toggled;
    private boolean onlyInt;
    private boolean visible = true;
    private boolean extended;
    private boolean dragging;

    private float minValue;
    private float maxValue;
    private float currentValue;

    /*
    CheckBox
     */
    public Value(String valueName, boolean toggled, ValueTab valueTab) {
        this.valueName = valueName;
        this.toggled = toggled;
        this.valueTab = valueTab;
        this.valueIdentifier = "CheckBox";
    }

    /*
    ComboBox
     */
    public Value(String valueName, String[] modes, String currentMode, ValueTab valueTab) {
        this.valueName = valueName;
        this.modes = modes;
        this.currentMode = currentMode;
        this.valueTab = valueTab;
        this.valueIdentifier = "ComboBox";
    }

    /*
    Slider
     */
    public Value(String valueName, float minValue, float maxValue, float currentValue, boolean onlyInt, ValueTab valueTab) {
        this.valueName = valueName;
        this.minValue = minValue;
        this.maxValue = maxValue;
        this.currentValue = currentValue;
        this.onlyInt = onlyInt;
        this.valueTab = valueTab;
        this.valueIdentifier = "Slider";
    }

    public ValueTab getValueTab() {
        return valueTab;
    }

    public String getValueIdentifier() {
        return valueIdentifier;
    }

    public String getValueName() {
        return valueName;
    }

    public void setCurrentMode(String mode) {
        currentMode = mode;
    }

    public String getCurrentMode() {
        return currentMode;
    }

    public String[] getModes() {
        return modes;
    }

    public void setState(boolean state) {
        toggled = state;
    }

    public boolean isState() {
        return toggled;
    }

    public boolean isOnlyInt() {
        return onlyInt;
    }

    public void setVisible(boolean isVisible) {
        visible = isVisible;
    }

    public boolean isVisible() {
        return visible;
    }

    public void setExtended(boolean isExtended) {
        extended = isExtended;
    }

    public boolean isExtended() {
        return extended;
    }

    public void toggleExtended() {
        extended = !extended;
    }

    public void setDragging(boolean isDragging) {
        dragging = isDragging;
    }

    public boolean isDragging() {
        return dragging;
    }

    public float getMinValue() {
        return minValue;
    }

    public float getMaxValue() {
        return maxValue;
    }

    public void setCurrentValue(float value) {
        currentValue = value;
    }

    public float getCurrentValue() {
        return currentValue;
    }

}