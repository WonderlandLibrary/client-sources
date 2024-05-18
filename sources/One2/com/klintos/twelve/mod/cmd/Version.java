// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

public class Version extends Cmd
{
    public Version() {
        super("version", "Gets the servers version and brand.", "version");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        if (this.mc.getCurrentServerData().gameVersion != null) {
            this.addMessage("Server is currently running §c" + this.mc.getCurrentServerData().gameVersion + "§f.");
        }
        this.addMessage(this.mc.getCurrentServerData().serverIP);
    }
}
