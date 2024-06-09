package dev.vertic.setting.impl;

import dev.vertic.setting.Setting;
import lombok.Getter;

import java.util.Arrays;
import java.util.List;
import java.util.function.BooleanSupplier;

@Getter
public class ModeSetting extends Setting {

    private int index;
    private List<String> listOfModes;

    public ModeSetting(final String name, final String defaultMode, final String...otherModes) {
        super(name);
        this.listOfModes = Arrays.asList(otherModes);
        this.setMode(defaultMode);
    }
    public ModeSetting(final String name, final BooleanSupplier visibility, final String defaultMode, final String...otherModes) {
        super(name, visibility);
        this.listOfModes = Arrays.asList(otherModes);
        this.setMode(defaultMode);
    }

    public String getMode() {
        if (index < 0 || index >= listOfModes.size()) {
            index = 0;
        }
        return listOfModes.get(index);
    }

    public void setMode(final String mode) {
        this.index = listOfModes.indexOf(mode);
    }

    public boolean is(final String mode) {
        if (index < 0 || index >= listOfModes.size()) {
            index = 0;
        }
        return listOfModes.get(index).equals(mode);
    }

    public void increment() {
        if (index < listOfModes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
    }
    public void decrement() {
        if (index > 0) {
            index--;
        } else {
            index = listOfModes.size() - 1;
        }
    }

}
