// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import com.klintos.twelve.Twelve;

public class Ghost extends Cmd
{
    public Ghost() {
        super("ghost", "Ghosts the client.", "ghost");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        Twelve.getInstance().ghostClient();
    }
}
