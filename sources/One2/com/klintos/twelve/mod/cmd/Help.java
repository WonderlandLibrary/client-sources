// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.util.Iterator;
import com.klintos.twelve.Twelve;

public class Help extends Cmd
{
    public Help() {
        super("help", "Shows all commands.", "help");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        for (final Cmd cmd : Twelve.getInstance().getCmdHandler().getCmds()) {
            if (cmd != this) {
                this.addMessage(String.valueOf(Twelve.getInstance().getCmdHandler().getPrefix()) + cmd.getCmd() + " - " + cmd.getDescription());
            }
        }
        this.addMessage("Add 'help' afer any command for more information on it.");
    }
}
