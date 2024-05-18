// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.util.Iterator;
import com.klintos.twelve.mod.Mod;
import com.klintos.twelve.Twelve;

public class Toggle extends Cmd
{
    public Toggle() {
        super("t", "Toggles a mod.", "t <Mod>");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        try {
            boolean valid = false;
            final String modName = args[1];
            for (final Mod mod : Twelve.getInstance().getModHandler().getMods()) {
                if (mod.getModName().toLowerCase().contains(modName.toLowerCase())) {
                    if (mod.getEnabled()) {
                        this.addMessage(String.valueOf(mod.getModName()) + " §cdisabled.");
                    }
                    else {
                        this.addMessage(String.valueOf(mod.getModName()) + " §aenabled.");
                    }
                    mod.onToggle();
                    valid = true;
                    break;
                }
            }
            if (!valid) {
                this.addMessage("Invalid Mod given.");
            }
        }
        catch (Exception e) {
            this.runHelp();
        }
    }
}
