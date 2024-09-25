package eze.settings;

public class BooleanSetting extends Setting
{
    public boolean enabled;
    
    public BooleanSetting(final String name, final boolean enabled) {
        this.name = name;
        this.enabled = enabled;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
    }
    
    public void toggle() {
        this.enabled = !this.enabled;
    }
}
