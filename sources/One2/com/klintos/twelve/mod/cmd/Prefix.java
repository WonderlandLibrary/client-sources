// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.klintos.twelve.Twelve;

public class Prefix extends Cmd
{
    public Prefix() {
        super("prefix", "Changes the command prefix.", "prefix <Character>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            final String prefix = msg.split(" ")[1];
            Twelve.getInstance().getCmdHandler().setPrefix(prefix);
            this.addMessage("Command prefix now changed to §c" + prefix + "§f.");
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
