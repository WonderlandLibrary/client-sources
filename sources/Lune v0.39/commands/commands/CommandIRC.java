package me.superskidder.lune.commands.commands;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.superskidder.lune.Lune;
import me.superskidder.lune.modules.cloud.IRC;
import me.superskidder.lune.commands.Command;
import me.superskidder.lune.utils.player.PlayerUtil;
import net.minecraft.client.Minecraft;


public class CommandIRC extends Command {

    public CommandIRC() {
        super("irc");
    }

    @Override
    public void run(String[] args) {
        if (args.length < 1) {
            PlayerUtil.sendMessage(".irc <text>");
        } else {
            String t = "";
            for (String s : args) {
                t = t + s + " ";
            }

            IRC.sendMessage("MSG@" + Lune.username + ChatFormatting.GRAY + "(" + Minecraft.getMinecraft().thePlayer.getName() + ")" + "@" + ChatFormatting.BLUE + Lune.CLIENT_NAME + ChatFormatting.WHITE + "@" + t);
        }
    }
}
