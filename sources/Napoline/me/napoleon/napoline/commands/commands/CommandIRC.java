package me.napoleon.napoline.commands.commands;

import com.mojang.realmsclient.gui.ChatFormatting;

import me.napoleon.napoline.Napoline;
import me.napoleon.napoline.commands.Command;
import me.napoleon.napoline.junk.cloud.IRC;
import me.napoleon.napoline.utils.player.PlayerUtil;
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

            IRC.sendMessage("MSG@" + Napoline.username + ChatFormatting.GRAY + "(" + Minecraft.getMinecraft().thePlayer.getName() + ")" + "@" + ChatFormatting.BLUE + Napoline.CLIENT_NAME + ChatFormatting.WHITE + "@" + t);
        }
    }
}
