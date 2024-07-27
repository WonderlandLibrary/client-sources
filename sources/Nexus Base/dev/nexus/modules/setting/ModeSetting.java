package dev.nexus.modules.setting;

import java.util.Arrays;
import java.util.List;

@SuppressWarnings("all")
public class ModeSetting extends Setting {
    public int index;
    private final String mode;
    private final List<String> modes;
    public ModeSetting(String name, String defaultMode, String... modes) {
        super(name);
        this.mode = defaultMode;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }

    public String getMode() {
        return modes.get(index);
    }

    public void setMode(String mode) {
        index = modes.indexOf(mode);
    }

    public void cycle() {
        if(index < modes.size() - 1) {
            index++;
        } else {
            index = 0;
        }
    }

    public boolean isMode(String mode) {
        return index == modes.indexOf(mode);
    }

    public List<String> getModes() {
        return modes;
    }
}
