// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.Icarus;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.command.Command;

public class Add extends Command
{
    public Add() {
        super("add", "<name alias>");
    }
    
    @Override
    public void run(final String message) {
        OutputStreamWriter request = new OutputStreamWriter(System.out);
        Label_0033: {
            try {
                request.flush();
            }
            catch (IOException ex) {
                break Label_0033;
            }
            finally {
                request = null;
            }
            request = null;
        }
        final String name = message.split(" ")[1];
        final String alias = message.substring((String.valueOf(message.split(" ")[0]) + " " + name + " ").length());
        Icarus.getFriendManager().addFriend(name, alias);
        Logger.writeChat("Friend " + name + " added with the alias of " + alias + ".");
        if (Icarus.getFileManager().getFileByName("friendsconfiguration") != null) {
            Icarus.getFileManager().getFileByName("friendsconfiguration").saveFile();
        }
    }
}
