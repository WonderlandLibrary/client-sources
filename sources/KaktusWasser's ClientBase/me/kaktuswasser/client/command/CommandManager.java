// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import me.kaktuswasser.client.command.commands.Add;
import me.kaktuswasser.client.command.commands.Bind;
import me.kaktuswasser.client.command.commands.CrashPEX;
import me.kaktuswasser.client.command.commands.Damage;
import me.kaktuswasser.client.command.commands.Del;
import me.kaktuswasser.client.command.commands.Forward;
import me.kaktuswasser.client.command.commands.Frequency;
import me.kaktuswasser.client.command.commands.Give;
import me.kaktuswasser.client.command.commands.Help;
import me.kaktuswasser.client.command.commands.ItemUtilities;
import me.kaktuswasser.client.command.commands.MVI;
import me.kaktuswasser.client.command.commands.Modules;
import me.kaktuswasser.client.command.commands.OldNames;
import me.kaktuswasser.client.command.commands.Toggle;
import me.kaktuswasser.client.command.commands.Vclip;
import me.kaktuswasser.client.utilities.Logger;

public class CommandManager
{
    public static String prefix;
    private List<Command> commands;
    
    static {
        CommandManager.prefix = ".";
    }
    
    public Command getCommandUsingName(final String name) {
        if (this.commands == null) {
            return null;
        }
        for (final Command command : this.commands) {
            if (command.getCommand().equalsIgnoreCase(name)) {
                return command;
            }
        }
        return null;
    }
    
    public void setupCommands() {
        this.commands = new ArrayList<Command>();
        this.commands.add(new Toggle());
        this.commands.add(new Add());
        this.commands.add(new Del());
        this.commands.add(new Bind());
        this.commands.add(new Damage());
        this.commands.add(new Modules());
        this.commands.add(new Give());
        this.commands.add(new MVI());
        this.commands.add(new ItemUtilities());
        this.commands.add(new Frequency());
        this.commands.add(new Forward());
        this.commands.add(new Vclip());
        this.commands.add(new OldNames());
        this.commands.add(new Help());
        this.commands.add(new CrashPEX());
    }
    
    public void organizeCommands() {
        Collections.sort(this.commands, new Comparator<Command>() {
            @Override
            public int compare(final Command mod1, final Command mod2) {
                return mod1.getCommand().compareTo(mod2.getCommand());
            }
        });
        Logger.writeConsole("Succesfully loaded " + this.commands.size() + " commands.");
    }
    
    public List<Command> getCommands() {
        return this.commands;
    }
}
