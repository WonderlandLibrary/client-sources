// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.util.Iterator;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.Twelve;

public class Mods extends Cmd
{
    public Mods() {
        super("mods", "Lists all mods.", "mods");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        String mods = "";
        for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
            mods = String.valueOf(mods) + mod.getModName() + ", ";
        }
        this.addMessage(String.valueOf(mods.substring(0, mods.length() - 2)) + ".");
    }
}
