package alphentus.settings;

import alphentus.mod.Mod;

/**
 * @author avox | lmao
 * @since on 30/07/2020.
 */
public class Setting {

    private String name;

    private String[] combos;
    private String selectedCombo;

    private boolean state;

    private float min;
    private float max;
    private float current;
    private boolean onlyInt;

    private String settingIdentifier;

    private Mod mod;

    private boolean visible;

    private boolean extended;

    public Setting (String name, boolean state, Mod mod) {
        this.name = name;
        this.state = state;
        this.mod = mod;
        this.visible = true;
        this.settingIdentifier = "CheckBox";
    }

    public Setting (String name, String[] combos, String selectedCombo, Mod mod) {
        this.name = name;
        this.combos = combos;
        this.selectedCombo = selectedCombo;
        this.mod = mod;
        this.visible = true;
        this.settingIdentifier = "ComboBox";
    }

    public Setting (String name, float min, float max, float current, boolean onlyInt, Mod mod) {
        this.name = name;
        this.min = min;
        this.max = max;
        this.current = current;
        this.onlyInt = onlyInt;
        this.mod = mod;
        this.visible = true;
        this.settingIdentifier = "Slider";
    }

    public Setting (boolean onlyInt) {
        this.onlyInt = onlyInt;
    }

    public String getName () {
        return name;
    }

    public void setName (String name) {
        this.name = name;
    }

    public String[] getCombos () {
        return combos;
    }

    public void setCombos (String[] combos) {
        this.combos = combos;
    }

    public String getSelectedCombo () {
        return selectedCombo;
    }

    public void setSelectedCombo (String selectedCombo) {
        this.selectedCombo = selectedCombo;
    }

    public boolean isState () {
        return state;
    }

    public void setState (boolean state) {
        this.state = state;
    }

    public float getMin () {
        return min;
    }

    public void setVisible (boolean visible) {
        this.visible = visible;
    }

    public boolean isVisible () {
        return visible;
    }

    public void setMin (float min) {
        this.min = min;
    }

    public float getMax () {
        return max;
    }

    public void setMax (float max) {
        this.max = max;
    }

    public boolean isOnlyInt () {
        return onlyInt;
    }

    public float getCurrent () {
        if (isOnlyInt())
            return (int) current;
        return current;
    }

    public void setCurrent (float current) {
        this.current = current;
    }

    public String getSettingIdentifier () {
        return settingIdentifier;
    }

    public Mod getMod () {
        return mod;
    }

    public void setSettingIdentifier (String settingIdentifier) {
        this.settingIdentifier = settingIdentifier;
    }

    public boolean isExtended() {
        return extended;
    }

    public void setExtended(boolean extended) {
        this.extended = extended;
    }
}
