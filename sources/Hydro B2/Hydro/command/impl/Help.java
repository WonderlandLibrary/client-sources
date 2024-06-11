package Hydro.command.impl;

import java.util.List;

import Hydro.command.CommandExecutor;
import Hydro.command.CommandManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.ChatComponentText;

public class Help implements CommandExecutor {

    @Override
    public void execute(EntityPlayerSP sender, List<String> args) {
        CommandManager.getCommands().forEach(command -> Minecraft.getMinecraft().thePlayer.addChatMessage(new ChatComponentText((command.getName() + " - " + command.getDesc()))));
    }
}
