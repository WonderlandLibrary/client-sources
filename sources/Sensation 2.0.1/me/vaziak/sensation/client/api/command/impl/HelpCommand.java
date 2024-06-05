package me.vaziak.sensation.client.api.command.impl;

import me.vaziak.sensation.Sensation;
import me.vaziak.sensation.client.api.Module;
import me.vaziak.sensation.client.api.command.Command;
import me.vaziak.sensation.utils.client.ChatUtils;
import net.minecraft.util.EnumChatFormatting;
import org.lwjgl.input.Keyboard;

import java.util.List;

public class HelpCommand extends Command {
    public HelpCommand() {
        super("help", "Gives you a list of commands", "", new String[]{});
    }

    @Override
    public boolean onCommand(String[] args) {
        ChatUtils.log("Sensation v" + Sensation.instance.version);
        ChatUtils.log("Commands:");

        for (Command command : Sensation.instance.commandManager.getCommandList()) {
            ChatUtils.log(" ." + command.getName() + " " + command.getUsage() + " - " + command.getDescription());
        }

        return true;
    }
}
