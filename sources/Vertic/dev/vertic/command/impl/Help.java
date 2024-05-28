package dev.vertic.command.impl;

import dev.vertic.Client;
import dev.vertic.command.Command;
import net.minecraft.util.ChatComponentText;

import java.util.ArrayList;
import java.util.Arrays;

import static net.minecraft.util.EnumChatFormatting.*;

public class Help extends Command {

    public Help() {
        super("Help", "help", "h", "?");
    }

    @Override
    public void call(String[] args) {
        addChatMessage(AQUA + Client.name + GOLD + " » " + WHITE + "Commands help: ");
        for (Command c : Client.instance.getCommandManager().commands) {
            addChatMessage(c.getName() + " : ." + Arrays.toString(c.getCalls()));
        }
    }

    @Override
    protected void addChatMessage(final Object message) {
        if (mc.thePlayer != null) {
            mc.thePlayer.addChatMessage(new ChatComponentText("" + message));
        }
    }

}
