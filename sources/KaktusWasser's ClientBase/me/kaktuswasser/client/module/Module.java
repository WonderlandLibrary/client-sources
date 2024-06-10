// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.module;

import net.minecraft.util.StringUtils;

import java.awt.Font;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.event.Listener;
import me.kaktuswasser.client.notification.NotificationType;
import me.kaktuswasser.client.utilities.UnicodeFontRenderer;
import net.minecraft.client.Minecraft;

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
        return !Client.getEventManager().isCancelled() && this.enabled;
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
        if (Client.getFileManager().getFileByName("modulesconfiguration") != null) {
            Client.getFileManager().getFileByName("modulesconfiguration").saveFile();
        }
    }
    
    public void setEnabled(final boolean enabled) {
        this.enabled = enabled;
        if (Client.getFileManager().getFileByName("modulesconfiguration") != null) {
            Client.getFileManager().getFileByName("modulesconfiguration").saveFile();
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
        if (!Client.getEventManager().isCancelled()) {
            if (Client.getModuleManager().getModuleByName("modulesnotifications") != null && Client.getModuleManager().getModuleByName("modulesnotifications").isEnabled()) {
                Client.getNotificationManager().addNotification("Module " + this.getName() + " toggled §aon§f.", NotificationType.INFO);
            }
            this.transition = this.arrayText.getStringWidth(StringUtils.stripControlCodes(this.getTag())) / 2;
            Client.getEventManager().addListener(this);
        }
    }
    
    public void onDisabled() {
        if (!Client.getEventManager().isCancelled()) {
            if (Client.getModuleManager().getModuleByName("modulesnotifications") != null && Client.getModuleManager().getModuleByName("modulesnotifications").isEnabled()) {
                Client.getNotificationManager().addNotification("Module " + this.getName() + " toggled §coff§f.", NotificationType.INFO);
            }
            Client.getEventManager().removeListener(this);
        }
    }
    
    public void toggle() {
        this.enabled = !this.enabled;
        if (Client.getFileManager().getFileByName("modulesconfiguration") != null) {
            Client.getFileManager().getFileByName("modulesconfiguration").saveFile();
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
