package me.tojatta.api.utilities.value.interfaces;

public interface Toggleable
{
    boolean isEnabled();
    
    void setEnabled(final boolean p0);
    
    void onEnable();
    
    void onDisable();
    
    void onToggle();
    
    void toggle();
}
