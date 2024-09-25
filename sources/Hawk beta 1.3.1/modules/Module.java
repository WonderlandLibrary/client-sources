package eze.modules;

import net.minecraft.client.*;
import eze.settings.*;
import java.util.*;
import eze.events.*;

public class Module
{
    public String name;
    public boolean toggled;
    public KeybindSetting keyCode;
    public Category category;
    public Minecraft mc;
    public boolean expanded;
    public int index;
    public List<Setting> settings;
    
    public Module(final String name, final int key, final Category c) {
        this.keyCode = new KeybindSetting(0);
        this.mc = Minecraft.getMinecraft();
        this.settings = new ArrayList<Setting>();
        this.name = name;
        this.keyCode.code = key;
        this.category = c;
        this.addSettings(this.keyCode);
    }
    
    public void addSettings(final Setting... settings) {
        this.settings.addAll(Arrays.asList(settings));
    }
    
    public boolean isEnabled() {
        return this.toggled;
    }
    
    public int getKey() {
        return this.keyCode.code;
    }
    
    public void onEvent(final Event e) {
    }
    
    public void toggle() {
        this.toggled = !this.toggled;
        if (this.toggled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void onEnable() {
    }
    
    public void onDisable() {
    }
    
    public enum Category
    {
        COMBAT("COMBAT", 0, "Combat"), 
        MOVEMENT("MOVEMENT", 1, "Movement"), 
        PLAYER("PLAYER", 2, "Player"), 
        RENDER("RENDER", 3, "Render");
        
        public String name;
        public int moduleIndex;
        
        private Category(final String name2, final int ordinal, final String name) {
            this.name = name;
        }
    }
}
