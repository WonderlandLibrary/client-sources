// 
// Decompiled by Procyon v0.5.30
// 

package net.minecraft.client.triton.management.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.triton.impl.modules.render.Hud;
import net.minecraft.client.triton.management.event.EventManager;
import net.minecraft.client.triton.management.option.Option;
import net.minecraft.client.triton.management.option.OptionManager;
import net.minecraft.client.triton.utils.ClientUtils;
import net.minecraft.client.triton.utils.RenderUtils;
import net.minecraft.client.triton.utils.RenderingUtils;

public class Module
{
    private String id;
    private String displayName;
    private boolean enabled;
    private Category category;
    public int keybind;
    private String suffix;
    private boolean shown;
    
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
        	String weird = String.format("%s" + ((this.suffix.length() > 0) ? "§7 %s" : ""), this.displayName, this.suffix);
            ClientUtils.clientFont().drawStringWithShadow(weird, x, y, Hud.currentColor);
            return true;
        }
        return false;
    }
    public static double colorhue;
    private int getColor() {
        return 0xffe6e6e6;
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
        Combat("Combat", 0), 
        Render("Render", 1), 
        Movement("Movement", 2), 
        Player("Player", 3),  
    	Misc("Misc", 4);
        
        private Category(final String s, final int n) {
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
