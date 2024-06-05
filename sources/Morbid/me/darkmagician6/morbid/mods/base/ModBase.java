package me.darkmagician6.morbid.mods.base;

import me.darkmagician6.morbid.*;

public abstract class ModBase
{
    private String modName;
    private String description;
    public String command;
    private String modKey;
    private int modColor;
    private boolean enabled;
    private boolean shouldShow;
    private static boolean commandExists;
    private final MorbidWrapper wrapper;
    
    public ModBase(final String n, final String k, final boolean b, final String s) {
        this.command = "";
        this.modKey = "0";
        this.modColor = -1;
        this.wrapper = new MorbidWrapper();
        this.modName = n;
        this.modKey = k;
        this.shouldShow = b;
        this.command = s;
    }
    
    public ModBase(final String n, final String k, final boolean b) {
        this.command = "";
        this.modKey = "0";
        this.modColor = -1;
        this.wrapper = new MorbidWrapper();
        this.modName = n;
        this.modKey = k;
        this.shouldShow = b;
    }
    
    public void onToggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
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
    
    public void preUpdate() {
    }
    
    public void postUpdate() {
    }
    
    public void preMotionUpdate() {
    }
    
    public void postMotionUpdate() {
    }
    
    public void postRenderChest(final double x, final double y, final double z) {
    }
    
    public void onRenderHand() {
    }
    
    public boolean onVelocityPacket(final ey packet) {
        return false;
    }
    
    public void onCommand(final String s) {
    }
    
    public String getName() {
        return this.modName;
    }
    
    public String getKey() {
        return this.modKey;
    }
    
    public void setKey(final String key) {
        this.modKey = key.toUpperCase();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setColor(final int col) {
        this.modColor = col;
    }
    
    public int getColor() {
        return this.modColor;
    }
    
    public void setEnabled(final boolean b) {
        this.enabled = b;
        if (this.isEnabled()) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void setDescription(final String desc) {
        this.description = desc;
    }
    
    public boolean shouldShow() {
        return this.shouldShow;
    }
    
    public MorbidWrapper getWrapper() {
        return this.wrapper;
    }
    
    public static boolean commandExists() {
        return ModBase.commandExists;
    }
    
    public static void setCommandExists(final boolean b) {
        ModBase.commandExists = b;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
