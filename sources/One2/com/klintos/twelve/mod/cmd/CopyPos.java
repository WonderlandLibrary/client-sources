// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class CopyPos extends Cmd
{
    public CopyPos() {
        super("copypos", "Copies your XYZ position to your clipboard.", "copypos");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        final String string = String.valueOf((int)(this.mc.thePlayer.posX + 0.5)) + " " + (int)this.mc.thePlayer.posY + " " + (int)(this.mc.thePlayer.posZ + 0.5);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(string), null);
        this.addMessage("Your current XYZ position has been coppied to your clipboard.");
    }
}
