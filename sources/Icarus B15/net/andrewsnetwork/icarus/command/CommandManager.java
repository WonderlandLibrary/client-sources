// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import net.andrewsnetwork.icarus.command.commands.Add;
import net.andrewsnetwork.icarus.command.commands.Bind;
import net.andrewsnetwork.icarus.command.commands.CrashPEX;
import net.andrewsnetwork.icarus.command.commands.Damage;
import net.andrewsnetwork.icarus.command.commands.Del;
import net.andrewsnetwork.icarus.command.commands.Forward;
import net.andrewsnetwork.icarus.command.commands.Frequency;
import net.andrewsnetwork.icarus.command.commands.Give;
import net.andrewsnetwork.icarus.command.commands.Help;
import net.andrewsnetwork.icarus.command.commands.ItemUtilities;
import net.andrewsnetwork.icarus.command.commands.MVI;
import net.andrewsnetwork.icarus.command.commands.Modules;
import net.andrewsnetwork.icarus.command.commands.OldNames;
import net.andrewsnetwork.icarus.command.commands.Toggle;
import net.andrewsnetwork.icarus.command.commands.Vclip;
import net.andrewsnetwork.icarus.utilities.Logger;

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
