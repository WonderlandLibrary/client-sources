package me.travis.wurstplus.command.commands;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.syntax.ChunkBuilder;
import me.travis.wurstplus.command.syntax.parsers.ModuleParser;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;

public class ToggleCommand extends Command {

    public ToggleCommand() {
        super("toggle", new ChunkBuilder()
                .append("module", true, new ModuleParser())
                .build());
    }

    @Override
    public void call(String[] args) {
        if (args.length == 0) {
            Command.sendChatMessage("Please specify a module!");
            return;
        }
        Module m = ModuleManager.getModuleByName(args[0]);
        if (m == null) {
            Command.sendChatMessage("Unknown module '" + args[0] + "'");
            return;
        }
        m.toggle();
        Command.sendChatMessage(m.getName() + (m.isEnabled() ? " &aenabled" : " &cdisabled"));
    }
}
