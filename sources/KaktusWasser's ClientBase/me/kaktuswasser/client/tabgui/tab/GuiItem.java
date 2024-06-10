// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.tabgui.tab;

import me.kaktuswasser.client.module.Module;

public class GuiItem
{
    private final Module module;
    private String name;
    
    public GuiItem(final Module mod) {
        this.module = mod;
        this.name = mod.getName();
    }
    
    public Module getModule() {
        return this.module;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
}
