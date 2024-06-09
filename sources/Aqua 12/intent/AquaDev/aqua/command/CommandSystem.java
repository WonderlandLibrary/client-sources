// 
// Decompiled by Procyon v0.5.36
// 

package intent.AquaDev.aqua.command;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Arrays;
import intent.AquaDev.aqua.command.impl.commands.FriendCommand;
import intent.AquaDev.aqua.command.impl.commands.Global;
import intent.AquaDev.aqua.command.impl.commands.Ign;
import intent.AquaDev.aqua.command.impl.commands.SkidIrcCommand;
import intent.AquaDev.aqua.command.impl.commands.config;
import intent.AquaDev.aqua.command.impl.commands.toggle;
import intent.AquaDev.aqua.command.impl.commands.bind;
import java.util.List;

public class CommandSystem
{
    public static List<Command> commands;
    public final String Chat_Prefix = ".";
    
    public CommandSystem() {
        this.addCommand(new bind());
        this.addCommand(new toggle());
        this.addCommand(new config());
        this.addCommand(new SkidIrcCommand());
        this.addCommand(new Ign());
        this.addCommand(new Global());
        this.addCommand(new FriendCommand());
    }
    
    public void addCommand(final Command cmd) {
        CommandSystem.commands.add(cmd);
    }
    
    public boolean execute(String text) {
        final String s = text;
        this.getClass();
        if (!s.startsWith(".")) {
            return false;
        }
        text = text.substring(1);
        final String[] arguments = text.split(" ");
        for (final Command cmd : CommandSystem.commands) {
            if (cmd.getName().equalsIgnoreCase(arguments[0])) {
                final String[] args = Arrays.copyOfRange(arguments, 1, arguments.length);
                cmd.execute(args);
                return true;
            }
        }
        return false;
    }
    
    static {
        CommandSystem.commands = new ArrayList<Command>();
    }
}
