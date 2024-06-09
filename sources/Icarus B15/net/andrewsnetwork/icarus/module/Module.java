// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module;

import net.minecraft.util.StringUtils;
import net.andrewsnetwork.icarus.notification.NotificationType;
import net.andrewsnetwork.icarus.Icarus;
import java.awt.Font;
import net.minecraft.client.Minecraft;
import net.andrewsnetwork.icarus.utilities.UnicodeFontRenderer;
import net.andrewsnetwork.icarus.event.Listener;

public abstract class Module implements Listener
{
    private final UnicodeFontRenderer arrayText;
    public static Minecraft mc;
    private Category category;
    private String name;
    private String tag;
    private int color;
    private int key;
    private int transition;
    private boolean enabled;
    private boolean visible;
    
    static {
        Module.mc = Minecraft.getMinecraft();
    }
    
    public Module(final String name, final int color, final Category category) {
        this.arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 20));
        this.name = name;
        this.color = color;
        this.category = category;
        this.tag = name;
        this.setEnabled(false);
        this.setVisible(true);
    }
    
    public Module(final String name, final int color, final int key, final Category category) {
        this.arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 20));
        this.name = name;
        this.color = color;
        this.key = key;
        this.category = category;
        this.tag = name;
        this.setEnabled(false);
        this.setVisible(true);
    }
    
    public Module(final String name, final Category category) {
        this.arrayText = new UnicodeFontRenderer(new Font("Verdana", 0, 20));
        this.name = name;
        this.category = category;
        this.color = -1;
        this.tag = name;
        this.setEnabled(true);
        this.setVisible(false);
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getTag() {
        return this.tag;
    }
    
    public int getColor() {
        return this.color;
    }
    
    public int getKey() {
        return this.key;
    }
    
    public boolean isEnabled() {
        return !Icarus.getEventManager().isCancelled() && this.enabled;
    }
    
    public boolean isVisible() {
        return this.visible;
    }
    
    public void setName(final String name) {
        this.name = name;
    }
    
    public void setTag(final String tag) {
        this.tag = tag;
    }
    
    public void setColor(final int color) {
        this.color = color;
    }
    
    public void setKey(final int key) {
        this.key = key;
        if (Icarus.getFileManager().getFileByName("modulesconfiguration") != null) {
            Icarus.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (Icarus.getFileManager().getFileByName("modulesconfiguration") != null) {
            Icarus.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
        if (enabled) {
            this.onEnabled();
        }
        else {
            this.onDisabled();
        }
    }
    
    public void setVisible(final boolean visible) {
        this.visible = visible;
    }
    
    public void onEnabled() {
        if (!Icarus.getEventManager().isCancelled()) {
            if (Icarus.getModuleManager().getModuleByName("modulesnotifications") != null && Icarus.getModuleManager().getModuleByName("modulesnotifications").isEnabled()) {
                Icarus.getNotificationManager().addNotification("Module " + this.getName() + " toggled §aon§f.", NotificationType.INFO);
            }
            this.transition = this.arrayText.getStringWidth(StringUtils.stripControlCodes(this.getTag())) / 2;
            Icarus.getEventManager().addListener(this);
        }
    }
    
    public void onDisabled() {
        if (!Icarus.getEventManager().isCancelled()) {
            if (Icarus.getModuleManager().getModuleByName("modulesnotifications") != null && Icarus.getModuleManager().getModuleByName("modulesnotifications").isEnabled()) {
                Icarus.getNotificationManager().addNotification("Module " + this.getName() + " toggled §coff§f.", NotificationType.INFO);
            }
            Icarus.getEventManager().removeListener(this);
        }
    }
    
    public void toggle() {
        this.enabled = !this.enabled;
        if (Icarus.getFileManager().getFileByName("modulesconfiguration") != null) {
            Icarus.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
        if (this.enabled) {
            this.onEnabled();
        }
        else {
            this.onDisabled();
        }
    }
    
    public Category getCategory() {
        return this.category;
    }
    
    public void setCategory(final Category category) {
        this.category = category;
    }
    
    public int getTransition() {
        return this.transition;
    }
    
    public void setTransition(final int transition) {
        this.transition = transition;
    }
    
    public enum Category
    {
        EXPLOITS("EXPLOITS", 0, "Exploits"), 
        COMBAT("COMBAT", 1, "Combat"), 
        WORLD("WORLD", 2, "World"), 
        RENDER("RENDER", 3, "Render"), 
        MOVEMENT("MOVEMENT", 4, "Movement"), 
        MISC("MISC", 5, "Misc"), 
        PLAYER("PLAYER", 6, "Player");
        
        private String name;
        
        private Category(final String s, final int n, final String name) {
            this.name = name;
        }
        
        public String getName() {
            return this.name;
        }
    }
}
