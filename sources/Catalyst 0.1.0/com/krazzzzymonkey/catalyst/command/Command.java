// 
// Decompiled by Procyon v0.5.36
// 

package com.krazzzzymonkey.catalyst.command;

public abstract class Command
{
    private /* synthetic */ String command;
    
    public abstract void runCommand(final String p0, final String[] p1);
    
    public abstract String getSyntax();
    
    public String getCommand() {
        return this.command;
    }
    
    public Command(final String llIIIlllllIIIII) {
        this.command = llIIIlllllIIIII;
    }
    
    public abstract String getDescription();
}
