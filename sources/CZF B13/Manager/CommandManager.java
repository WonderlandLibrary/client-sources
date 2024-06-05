package gq.vapu.czfclient.Manager;

import gq.vapu.czfclient.API.EventBus;
import gq.vapu.czfclient.Command.Command;
import gq.vapu.czfclient.Command.Commands.*;
import gq.vapu.czfclient.IRC.IRCCommand;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class CommandManager implements Manager {
    private static List<Command> commands;

    public static Optional<Command> getCommandByName(String name) {
        return CommandManager.commands.stream().filter(c2 -> {
            boolean isAlias = false;
            String[] arrstring = c2.getAlias();
            int n = arrstring.length;
            int n2 = 0;
            while (n2 < n) {
                String str = arrstring[n2];
                if (str.equalsIgnoreCase(name)) {
                    isAlias = true;
                    break;
                }
                ++n2;
            }
            return c2.getName().equalsIgnoreCase(name) || isAlias;
        }).findFirst();
    }

    @Override
    public void init() {
        commands = new ArrayList<Command>();
        commands.add(new Help());
        commands.add(new Toggle());
        commands.add(new Bind());
        commands.add(new VClip());
        commands.add(new Echo());
        commands.add(new ModList());
        //this.commands.add(new CommandIRC());
        commands.add(new Config());
        commands.add(new Xraycmd());
        commands.add(new IRCCommand());
        commands.add(new Send());
        commands.add(new Title());
        commands.add(new Name());
        EventBus.getInstance().register(this);
    }

    public List<Command> getCommands() {
        return commands;
    }

    public void add(Command command) {
        commands.add(command);
    }
}
