// 
// Decompiled by Procyon v0.6.0
// 

package gg.childtrafficking.smokex.command;

import gg.childtrafficking.smokex.utils.player.ChatUtils;
import net.minecraft.client.Minecraft;

public class Command
{
    private final CommandInfo annotation;
    private final String name;
    private final String description;
    private final String usage;
    private final String[] aliases;
    protected Minecraft mc;
    
    public Command() {
        this.annotation = this.getClass().getAnnotation(CommandInfo.class);
        this.name = this.annotation.name();
        this.description = this.annotation.description();
        this.usage = this.annotation.usage();
        this.aliases = this.annotation.aliases();
        this.mc = Minecraft.getMinecraft();
    }
    
    public void execute(final String[] args) {
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getDescription() {
        return this.description;
    }
    
    public String getUsage() {
        return this.usage;
    }
    
    public void printUsage() {
        ChatUtils.addChatMessage("§cUsage: " + this.usage);
    }
    
    public String[] getAliases() {
        return this.aliases;
    }
}
