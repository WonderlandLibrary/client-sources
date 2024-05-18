// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import net.minecraft.util.IChatComponent;
import net.minecraft.util.ChatComponentText;
import net.minecraft.client.Minecraft;

public abstract class Cmd
{
    private String cmd;
    private String description;
    private String syntax;
    protected Minecraft mc;
    
    public Cmd(final String cmd, final String description, final String syntax) {
        this.mc = Minecraft.getMinecraft();
        this.cmd = cmd;
        this.description = description;
        this.syntax = syntax;
    }
    
    public abstract void runCmd(final String p0, final String[] p1);
    
    public void runHelp() {
        this.addMessage("Usage: " + this.getSyntax());
    }
    
    public void addMessage(final String s) {
        Minecraft.getMinecraft().ingameGUI.getChatGUI().printChatMessage((IChatComponent)new ChatComponentText("§8[§fONE§c2§8]§f: " + s));
    }
    
    public String getCmd() {
        return this.cmd;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getSyntax() {
        return this.syntax;
    }
}
