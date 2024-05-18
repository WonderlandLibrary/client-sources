// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import java.util.Iterator;
import com.klintos.twelve.Twelve;
import com.darkmagician6.eventapi.EventManager;
import net.minecraft.client.Minecraft;
import com.klintos.twelve.mod.value.Value;
import java.util.ArrayList;

public class Mod
{
    private String modName;
    private int modKeybind;
    private ModCategory modCategory;
    private boolean modEnabled;
    private ArrayList<Value> values;
    protected static Minecraft mc;
    
    static {
        Mod.mc = Minecraft.getMinecraft();
    }
    
    public Mod(final String modName, final int modKeybind, final ModCategory modCategory) {
        this.values = new ArrayList<Value>();
        this.modName = modName;
        this.modKeybind = modKeybind;
        this.modCategory = modCategory;
    }
    
    public void onToggle() {
        this.modEnabled = !this.modEnabled;
        if (this.getEnabled()) {
            EventManager.register((Object)this);
            Twelve.getInstance().getModHandler().registeredMods.add(this);
            this.onEnable();
        }
        else {
            EventManager.unregister((Object)this);
            Twelve.getInstance().getModHandler().registeredMods.remove(this);
            this.onDisable();
        }
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public boolean getEnabled() {
        return this.modEnabled;
    }
    
    public void setModState(final boolean state) {
        this.modEnabled = state;
    }
    
    public String getModName() {
        return this.modName;
    }
    
    public void setModName(final String name) {
        this.modName = name;
    }
    
    public int getModKeybind() {
        return this.modKeybind;
    }
    
    public void setModKeybind(final int bind) {
        this.modKeybind = bind;
    }
    
    public ModCategory getModCategory() {
        return this.modCategory;
    }
    
    public void setModCategory(final ModCategory modCategory) {
        this.modCategory = modCategory;
    }
    
    public void addValue(final Value value) {
        for (final Value v : this.values) {
            if (v.getName().equals(value.getName())) {
                return;
            }
        }
        this.values.add(value);
    }
    
    public ArrayList<Value> getValues() {
        return this.values;
    }
}
