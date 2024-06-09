package me.travis.wurstplus.command.commands;

import me.travis.wurstplus.command.Command;
import me.travis.wurstplus.command.syntax.ChunkBuilder;
import me.travis.wurstplus.command.syntax.SyntaxChunk;
import me.travis.wurstplus.command.syntax.parsers.ModuleParser;
import me.travis.wurstplus.module.Module;
import me.travis.wurstplus.module.ModuleManager;

public class RenameModuleCommand extends Command {

    public RenameModuleCommand() {
        super("renamemodule", new ChunkBuilder().append("module", true, new ModuleParser()).append("name").build());
    }

    @Override
    public void call(String[] args) {
        if (args.length == 0) {
            sendChatMessage("Please specify a module!");
            return;
        }

        Module module = ModuleManager.getModuleByName(args[0]);
        if (module == null) {
            sendChatMessage("Unknown module '" + args[0] + "'!");
            return;
        }

        String name = args.length == 1 ? module.getOriginalName() : args[1];

        if (!(name.matches("[a-zA-Z]+"))) {
            sendChatMessage("Name must be alphabetic!");
            return;
        }

        sendChatMessage("&b" + module.getName() + "&r renamed to &b" + name);
        module.setName(name);
    }

}
