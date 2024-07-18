package com.alan.clients.command.impl;

import com.alan.clients.command.Command;
import rip.vantage.commons.packet.impl.client.community.C2SPacketTitleIRC;
import rip.vantage.network.core.Network;

public class Title extends Command {
    public Title() {
        super("command.title.description", "title");
    }

    @Override
    public void execute(String[] args) {
        if (args.length != 7) {
            error(".title <message> <fadeInTime> <displayTime> <fadeOutTime> <color> <group/user>");
        } else {
            Network.getInstance().getClient().sendMessage(new C2SPacketTitleIRC(args[1], Integer.parseInt(args[2]), Integer.parseInt(args[3]), Integer.parseInt(args[4]), args[5], args[6]).export());
        }
    }
}
