// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands.impl;

import xyz.niggfaclient.utils.other.Printer;
import xyz.niggfaclient.Client;
import xyz.niggfaclient.commands.Command;

public class Rename extends Command
{
    public Rename() {
        super("Rename", "", "", new String[] { "r" });
    }
    
    @Override
    public void onCommand(final String[] args, final String command) {
        if (args.length > 0) {
            Client.getInstance().watermarkName = String.join(" ", (CharSequence[])args);
            Printer.addMessage("Renamed client to " + String.join(" ", (CharSequence[])args));
        }
    }
}
