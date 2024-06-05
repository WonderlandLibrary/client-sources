/*
 * Decompiled with CFR 0.150.
 */
package digital.rbq.command;

import java.util.ArrayList;
import java.util.List;
import digital.rbq.command.AbstractCommand;
import digital.rbq.command.CommandHandler;
import digital.rbq.command.impl.BindCommand;
import digital.rbq.command.impl.ClientNameCommand;
import digital.rbq.command.impl.ConfigCommand;
import digital.rbq.command.impl.HelpCommand;
import digital.rbq.command.impl.TeleportCommand;
import digital.rbq.command.impl.ToggleCommand;
import digital.rbq.command.impl.VClipCommand;
import digital.rbq.core.Autumn;

public final class CommandManager {
    public static final String PREFIX = ".";
    private final List<AbstractCommand> commands = new ArrayList<AbstractCommand>();

    public CommandManager() {
        Autumn.EVENT_BUS_REGISTRY.eventBus.subscribe(new CommandHandler(this));
        this.commands.add(new HelpCommand());
        this.commands.add(new BindCommand());
        this.commands.add(new VClipCommand());
        this.commands.add(new TeleportCommand());
        this.commands.add(new ToggleCommand());
        this.commands.add(new ConfigCommand());
        this.commands.add(new ClientNameCommand());
    }

    public List<AbstractCommand> getCommands() {
        return this.commands;
    }

    public final boolean execute(String args) {
        String noPrefix = args.substring(1);
        String[] split = noPrefix.split(" ");
        if (split.length > 0) {
            List<AbstractCommand> commands = this.commands;
            int commandsSize = commands.size();
            for (int i = 0; i < commandsSize; ++i) {
                AbstractCommand command = commands.get(i);
                for (String alias : command.getAliases()) {
                    if (!split[0].equalsIgnoreCase(alias)) continue;
                    command.execute(split);
                    return true;
                }
            }
        }
        return false;
    }
}

