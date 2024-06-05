package me.satisfactory.base.command;

import net.minecraft.client.*;

public class Command
{
    protected static Minecraft mc;
    private String name;
    private String usage;
    private String prefix;
    
    public Command(final String name, final String usage) {
        this.name = name;
        this.usage = usage;
    }
    
    public String getName() {
        return this.name;
    }
    
    public String getUsage() {
        return this.usage;
    }
    
    public String getPrefix() {
        return this.prefix;
    }
    
    public void setPrefix(final String prefix) {
        this.prefix = prefix;
    }
    
    public void execute(final String[] args) {
    }
    
    static {
        Command.mc = Minecraft.getMinecraft();
    }
}
