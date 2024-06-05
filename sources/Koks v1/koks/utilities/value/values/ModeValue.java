package koks.utilities.value.values;

import koks.modules.Module;
import koks.utilities.value.Value;

/**
 * @author avox | lmao | kroko
 * @created on 03.09.2020 : 18:24
 */
public class ModeValue<T extends String> extends Value {

    private String selectedMode;
    private String[] modes;
    private BooleanValue[] objects;

    public ModeValue(String name, String selectedMode, String[] modes, Module module) {
        setName(name);
        this.selectedMode = selectedMode;
        this.modes = modes;
        setModule(module);
    }

    public ModeValue(String name, BooleanValue[] objects, Module module) {
        setName(name);
        this.objects = objects;
        setModule(module);
    }

    public BooleanValue[] getObjects() {
        return objects;
    }

    public String getSelectedMode() {
        return selectedMode;
    }

    public void setSelectedMode(String selectedMode) {
        this.selectedMode = selectedMode;
    }

    public String[] getModes() {
        return modes;
    }

    public void setModes(String[] modes) {
        this.modes = modes;
    }
}
