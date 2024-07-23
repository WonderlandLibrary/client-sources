package io.github.liticane.monoxide.command.impl;

import io.github.liticane.monoxide.command.data.CommandInfo;
import net.minecraft.network.play.client.C01PacketChatMessage;
import io.github.liticane.monoxide.command.Command;

@CommandInfo(name = "say", description = "say stuff")
public class SayCommand extends Command {
    @Override
    public boolean execute(String[] args) {
        if (args.length == 1) {
            String msg = args[0];
            getPlayer().sendQueue.addToSendQueue(new C01PacketChatMessage(msg));
            return true;
        } else if (args.length == 0) {
            sendHelp(this, "[Message]");
        } else {
            return false;
        }

        return true;
    }
}