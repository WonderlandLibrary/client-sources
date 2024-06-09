// 
// Decompiled by Procyon v0.5.36
// 

package xyz.niggfaclient.commands;

import java.util.Iterator;
import xyz.niggfaclient.utils.other.Printer;
import java.util.Arrays;
import xyz.niggfaclient.events.impl.ChatEvent;
import xyz.niggfaclient.commands.impl.Teleport;
import xyz.niggfaclient.commands.impl.Rename;
import xyz.niggfaclient.commands.impl.Friend;
import xyz.niggfaclient.commands.impl.HClip;
import xyz.niggfaclient.commands.impl.VClip;
import xyz.niggfaclient.commands.impl.Name;
import xyz.niggfaclient.commands.impl.Help;
import xyz.niggfaclient.commands.impl.Hide;
import xyz.niggfaclient.commands.impl.ConfigCMD;
import xyz.niggfaclient.commands.impl.Bind;
import java.util.ArrayList;
import java.util.List;

public class CommandManager
{
    public List<Command> commands;
    public String prefix;
    public static CommandManager instance;
    
    public CommandManager() {
        this.commands = new ArrayList<Command>();
        this.prefix = ".";
        this.setup();
    }
    
    public void setup() {
        this.commands.add(new Bind());
        this.commands.add(new ConfigCMD());
        this.commands.add(new Hide());
        this.commands.add(new Help());
        this.commands.add(new Name());
        this.commands.add(new VClip());
        this.commands.add(new HClip());
        this.commands.add(new Friend());
        this.commands.add(new Rename());
        this.commands.add(new Teleport());
    }
    
    public void handleChat(final ChatEvent e) {
        String message = e.getMessage();
        if (message.startsWith(this.prefix)) {
            e.setCancelled();
            message = message.substring(this.prefix.length());
            if (message.split(" ").length > 0) {
                final String commandName = message.split(" ")[0];
                for (final Command c : this.commands) {
                    if (!c.aliases.contains(commandName) && c.name.equalsIgnoreCase(commandName)) {
                        c.onCommand(Arrays.copyOfRange(message.split(" "), 1, message.split(" ").length), message);
                        return;
                    }
                }
                Printer.addMessage("" + message + " is not a command, Try .help");
            }
        }
    }
    
    static {
        CommandManager.instance = new CommandManager();
    }
}
