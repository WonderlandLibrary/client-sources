package me.enrythebest.reborn.cracked.mods.base;

import me.enrythebest.reborn.cracked.*;
import net.minecraft.src.*;

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
    
    public ModBase(final String var1, final String var2, final boolean var3, final String var4) {
        this.command = "";
        this.modKey = "0";
        this.modColor = -1;
        this.wrapper = new MorbidWrapper();
        this.modName = var1;
        this.modKey = var2;
        this.shouldShow = var3;
        this.command = var4;
    }
    
    public ModBase(final String var1, final String var2, final boolean var3) {
        this.command = "";
        this.modKey = "0";
        this.modColor = -1;
        this.wrapper = new MorbidWrapper();
        this.modName = var1;
        this.modKey = var2;
        this.shouldShow = var3;
    }
    
    public void onToggle() {
        this.enabled = !this.enabled;
        if (this.enabled) {
            MorbidWrapper.addChat(String.valueOf(this.modName) + " §eEngaged");
            this.onEnable();
        }
        else {
            MorbidWrapper.addChat(String.valueOf(this.modName) + " §4Disengaged");
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
    
    public void postRenderChest(final double var1, final double var3, final double var5) {
    }
    
    public void onRenderHand() {
    }
    
    public boolean onVelocityPacket(final Packet28EntityVelocity var1) {
        return false;
    }
    
    public void onCommand(final String var1) {
    }
    
    public String getName() {
        return this.modName;
    }
    
    public String getKey() {
        return this.modKey;
    }
    
    public void setKey(final String var1) {
        this.modKey = var1.toUpperCase();
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public void setColor(final int var1) {
        this.modColor = var1;
    }
    
    public int getColor() {
        return this.modColor;
    }
    
    public void setEnabled(final boolean var1) {
        this.enabled = var1;
        if (this.isEnabled()) {
            this.onEnable();
        }
        else {
            this.onDisable();
        }
    }
    
    public void setDescription(final String var1) {
        this.description = var1;
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
    
    public static void setCommandExists(final boolean var0) {
        ModBase.commandExists = var0;
    }
    
    public boolean isEnabled() {
        return this.enabled;
    }
}
