// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.Logger;

public class Toggle extends Command
{
    public Toggle() {
        super("t", "<mod name>");
    }
    
    @Override
    public void run(final String message) {
        final String[] arguments = message.split(" ");
        final Module mod = Client.getModuleManager().getModuleByName(arguments[1]);
        if (mod == null) {
            Logger.writeChat("Module \"" + arguments[1] + "\" is not valid!");
        }
        else {
            mod.toggle();
            Logger.writeChat("Module \"" + mod.getName() + "\" toggled " + (mod.isEnabled() ? "§2on" : "§4off") + "§f.");
        }
    }
}
