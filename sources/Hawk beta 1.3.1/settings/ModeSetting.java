package eze.settings;

import java.util.*;

public class ModeSetting extends Setting
{
    public int index;
    public List<String> modes;
    
    public ModeSetting(final String name, final String defaultMode, final String... modes) {
        this.name = name;
        this.modes = Arrays.asList(modes);
        this.index = this.modes.indexOf(defaultMode);
    }
    
    public String getMode() {
        return this.modes.get(this.index);
    }
    
    public boolean is(final String mode) {
        return this.index == this.modes.indexOf(mode);
    }
    
    public void cycle() {
        if (this.index < this.modes.size() - 1) {
            ++this.index;
        }
        else {
            this.index = 0;
        }
    }
}
