package me.zeroeightsix.kami.command.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.zeroeightsix.kami.command.Command;
import me.zeroeightsix.kami.command.syntax.ChunkBuilder;
import me.zeroeightsix.kami.command.syntax.parsers.ModuleParser;
import me.zeroeightsix.kami.module.Module;
import me.zeroeightsix.kami.module.ModuleManager;

/**
 * Created by FINZ0 on 12 December 2019
 */
public class DrawnCommand extends Command {

    public DrawnCommand() {
        super("drawn", new ChunkBuilder()
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

        if (m.isDrawn()) {
            m.setDrawn(false);
        } else if (!m.isDrawn()) {
            m.setDrawn(true);
        }

        Command.sendChatMessage(m.getName() + (m.isDrawn() ? ChatFormatting.GREEN + " drawn = true" : ChatFormatting.RED + " drawn = false"));

    }
}
