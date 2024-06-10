// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.utilities.Logger;

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
        Client.getFriendManager().addFriend(name, alias);
        Logger.writeChat("Friend " + name + " added with the alias of " + alias + ".");
        if (Client.getFileManager().getFileByName("friendsconfiguration") != null) {
            Client.getFileManager().getFileByName("friendsconfiguration").saveFile();
        }
    }
}
