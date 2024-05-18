// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module;

import java.io.ObjectInputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Retention;
import java.lang.annotation.ElementType;
import java.lang.annotation.Target;
import java.lang.annotation.Annotation;
import java.awt.Color;
import me.chrest.utils.ClientUtils;
import java.util.Iterator;
import me.chrest.client.option.OptionManager;
import java.util.ArrayList;
import me.chrest.client.option.Option;
import java.util.List;
import me.chrest.event.EventManager;
import java.util.Random;

public class Module
{
    private String id;
    private String displayName;
    private boolean enabled;
    private Category category;
    public int keybind;
    private String suffix;
    private boolean shown;
    private int offset;
    private boolean backwards;
    public int color;
    public static double colorhue;
    
    public Module() {
        this.offset = 10;
    }
    
    public void setProperties(final String id, final String displayName, final Category type, final int keybind, final String suffix, final boolean shown) {
        this.id = id;
        this.displayName = displayName;
        this.category = type;
        this.keybind = keybind;
        this.suffix = suffix;
        this.shown = shown;
    }
    
    public void postInitialize() {
    }
    
    public void toggle() {
        if (this.enabled) {
            this.disable();
        }
        else {
            this.enable();
        }
    }
    
    public void enable() {
        final Random randomService = new Random();
        final StringBuilder sb = new StringBuilder();
        sb.append("0x");
        while (sb.length() < 10) {
            sb.append(Integer.toHexString(randomService.nextInt()));
        }
        sb.setLength(8);
        this.color = Integer.decode(sb.toString());
        this.enabled = true;
        EventManager.register(this);
    }
    
    public void disable() {
        this.enabled = false;
        EventManager.unregister(this);
    }
    
    public List<Option> getOptions() {
        final List<Option> optionList = new ArrayList<Option>();
        for (final Option option : OptionManager.getOptionList()) {
            if (option.getModule().equals(this)) {
                optionList.add(option);
            }
        }
        return optionList;
    }
    
    public String getId() {
        return this.id;
    }
    
    public String getDisplayName() {
        return this.displayName;
    }
    
    public boolean drawDisplayName(final float x, final float y) {
        if (this.enabled && this.shown) {
            int index = 0;
            long x2 = 0L;
            ClientUtils.clientFont().drawStringWithShadow(String.format("%s" + ((this.suffix.length() > 0) ? "§7:§f %s" : ""), this.displayName, this.suffix), x2, y, this.color);
            ++index;
            ++x2;
            return true;
        }
        return false;
    }
    
    private int getColor() {
        final double S = 0.9;
        final double B = 0.9;
        final double H = Module.colorhue;
        return Color.HSBtoRGB((float)H, (float)S, (float)B);
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public int getKeybind() {
        return this.keybind;
    }
    
    public void setKeybind(final int keybind) {
        this.keybind = keybind;
    }
    
    public String getSuffix() {
        return this.suffix;
    }
    
    public void setSuffix(final String suffix) {
        this.suffix = suffix;
    }
    
    public boolean isShown() {
        return this.shown;
    }
    
    public void setShown(final boolean shown) {
        this.shown = shown;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
    
    public void setDisplayName(final String displayName) {
        this.displayName = displayName;
    }
    
    public Module getInstance() {
        for (final Module mod : ModuleManager.getModules()) {
            if (mod.getClass().equals(this.getClass())) {
                return mod;
            }
        }
        return null;
    }
    
    public enum Category
    {
        Combat("Combat", 0, "Combat", 0), 
        Render("Render", 1, "Render", 1), 
        Movement("Movement", 2, "Movement", 2), 
        Player("Player", 3, "Player", 3), 
        Minigames("Minigames", 4, "Minigames", 4), 
        Misc("Misc", 5, "Misc", 5);
        
        private Category(final String s2, final int n2, final String s, final int n) {
        }
    }
    
    @Target({ ElementType.TYPE })
    @Retention(RetentionPolicy.RUNTIME)
    public @interface Mod {
        String displayName() default "null";
        
        boolean enabled() default false;
        
        int keybind() default -1;
        
        boolean shown() default true;
        
        String suffix() default "";
    }
}
