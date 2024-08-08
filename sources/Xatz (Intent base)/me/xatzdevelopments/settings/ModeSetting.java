package me.xatzdevelopments.settings;

import java.util.Arrays;
import java.util.List;

public class ModeSetting extends Setting {
	
	public int index;
	public List<String> modes;
	
	public ModeSetting(String name, String defaultMode, String... modes) {
		this.name = name;
		this.modes = Arrays.asList(modes);
		index = this.modes.indexOf(defaultMode);
	}
	
	public String getMode() {
		return modes.get(index);
	}
	
	public List retriveModes() {
		return modes;
	}
	

    public int getIndex() {
        return this.index;
    }

	public boolean is(String mode) {
		return index == modes.indexOf(mode);
	}
	
	public void cycle() {
		if(index < modes.size() - 1) {
			index++;
		}else {
			index = 0;
		}
	}
	
	 public void set(final int mode) {
	        this.index = mode;
	    }
	
	public void set(final String mode) {
        index = modes.indexOf(mode);
    }
	
	public void cycle(final boolean forwards) {
        if (forwards) {
            if (index < modes.size() - 1) {
                this.index++;
            }
            else {
                index = 0;
            }
        }
        if (!forwards) {
            if (index > 0) {
                index--;
            }
            else {
                index = modes.size() - 1;
            }
        }
    }


    public String getSelected() {
        return getSelected();
    }
	
}
