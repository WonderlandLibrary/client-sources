package me.valk.overlay.tabGui;

import me.valk.Vital;
import me.valk.overlay.tabGui.theme.TabTheme;

public class TabPartRenderer<T>
{
    private Class type;
    
    public TabPartRenderer(final Class type) {
        this.type = type;
    }
    
    public Class getType() {
        return this.type;
    }
    
    public TabTheme getTheme() {
        return Vital.getVital().getTabGui().getTabTheme();
    }
    
    public void render(final T object) {
    }
}
