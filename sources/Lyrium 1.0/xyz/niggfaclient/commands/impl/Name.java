// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import java.awt.datatransfer.Clipboard;
import xyz.niggfaclient.utils.other.Printer;
import java.awt.datatransfer.ClipboardOwner;
import java.awt.datatransfer.Transferable;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import net.minecraft.client.Minecraft;
import xyz.niggfaclient.commands.Command;

public class Name extends Command
{
    public Name() {
        super("Name", "", "", new String[] { "n" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length == 1) {
            return;
        }
        final StringSelection stringSelection = new StringSelection(Minecraft.getMinecraft().thePlayer.getName());
        final Clipboard clipboard = Toolkit.getDefaultToolkit().getSystemClipboard();
        clipboard.setContents(stringSelection, null);
        Printer.addMessage("Copied your username to the clipboard!");
    }
}
