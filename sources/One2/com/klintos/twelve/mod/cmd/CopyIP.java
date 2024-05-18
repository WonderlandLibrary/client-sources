// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod.cmd;

import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class CopyIP extends Cmd
{
    public CopyIP() {
        super("copyip", "Copies your server ip to your clipboard.", "copyip");
    }
    
    @Override
    public void runCmd(final String msg, final String[] args) {
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(new StringSelection(this.mc.getCurrentServerData().serverIP), null);
        this.addMessage("You're current server IP has been copied to your clipboard.");
    }
}
