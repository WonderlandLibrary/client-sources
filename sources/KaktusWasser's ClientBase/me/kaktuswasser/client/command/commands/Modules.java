// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.util.Iterator;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.utilities.Logger;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

public class Modules extends Command
{
    public Modules() {
        super("modules", "none");
    }
    
    @Override
    public void run(final String message) {
        final StringBuilder list = new StringBuilder("Modules (" + Client.getModuleManager().getModules().size() + "): ");
        for (final Module module : Client.getModuleManager().getModules()) {
            list.append(module.isEnabled() ? "§a" : "§f").append(module.getName()).append("§f, ");
        }
        Logger.writeChat(list.toString().substring(0, list.toString().length() - 2));
    }
}
