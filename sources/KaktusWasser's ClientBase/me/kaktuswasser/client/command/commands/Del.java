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

public class Del extends Command
{
    public Del() {
        super("del", "<name>");
    }
    
    @Override
    public void run(final String message) {
        final String name = message.split(" ")[1];
        Client.getFriendManager().removeFriend(name);
        Logger.writeChat("Friend " + name + " has been removed.");
        if (Client.getFileManager().getFileByName("friendsconfiguration") != null) {
            Client.getFileManager().getFileByName("friendsconfiguration").saveFile();
        }
    }
}
