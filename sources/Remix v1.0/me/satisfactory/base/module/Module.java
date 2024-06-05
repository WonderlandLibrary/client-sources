package me.satisfactory.base.module;

import net.minecraft.client.*;
import java.util.*;
import me.satisfactory.base.*;
import me.satisfactory.base.setting.*;
import me.satisfactory.base.hero.settings.*;

public class Module
{
    protected static Minecraft mc;
    private String name;
    private int keybind;
    private int animation;
    private Category category;
    private boolean state;
    private boolean enabled;
    private List<Mode> modes;
    private Mode mode;
    
    public Module(final String name, final int keybind, final Category category) {
        this.name = name;
        this.keybind = keybind;
        this.category = category;
        this.modes = new ArrayList<Mode>();
        Base.INSTANCE.getValueManager().register(this);
    }
    
    public void addSetting(final Setting setting) {
        Base.INSTANCE.getSettingManager().rSetting(setting);
    }
    
    public Setting findSettingByName(final String name) {
        return Base.INSTANCE.getSettingManager().getSettingByName(name);
    }
    
    public int getAnimation() {
        return this.animation;
    }
    
    public void setAnimation(final int Animation) {
        this.animation = Animation;
    }
    
    public String getName() {
        return this.name;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public int getKeybind() {
        return this.keybind;
    }
    
    public void setKeybind(final int keybind) {
        this.keybind = keybind;
        ModuleManager.save();
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void toggle() {
        this.state = !this.state;
        this.onToggle();
        if (this.state) {
            if (this.mode != null) {
                Base.INSTANCE.getEventManager().register(this.mode);
                this.mode.onEnable();
            }
            this.onEnable();
            this.enabled = true;
        }
        else {
            if (this.mode != null) {
                Base.INSTANCE.getEventManager().unregister(this.mode);
                this.mode.onDisable();
            }
            this.onDisable();
            this.enabled = false;
        }
        ModuleManager.save();
    }
    
    public void addMode(final Mode mode) {
        this.modes.add(mode);
        if (this.mode == null) {
            this.mode = mode;
        }
    }
    
    public Mode getMode() {
        return this.mode;
    }
    
    public void setMode(final Mode themode) {
        if (this.mode != null) {
            Base.INSTANCE.getEventManager().unregister(this.mode);
            this.mode.onDisable();
        }
        if (this.enabled) {
            Base.INSTANCE.getEventManager().register(themode);
            themode.onEnable();
        }
        this.mode = themode;
        SettingsManager.save();
    }
    
    public List<Mode> getModes() {
        return this.modes;
    }
    
    public void onToggle() {
    }
    
    public void onEnable() {
        Base.INSTANCE.getEventManager().register(this);
    }
    
    public void onDisable() {
        Base.INSTANCE.getEventManager().unregister(this);
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (enabled) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    protected Setting getSettingByModule(final Module mod, final String setting) {
        return Base.INSTANCE.getSettingManager().getSettingByModule(mod, setting);
    }
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
}
