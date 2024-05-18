package me.Emir.Karaguc.command;

import net.minecraft.network.play.server.S02PacketChat;

public abstract class Command {

    public String command;

    public Command(String command) {
        this.command = command;
    }

    public String getCommand() {
        return command;
    }

    public abstract void runCommand(String msg, String[] ARGS);

    public abstract String getDescription();

    public abstract String getSyntax();

    public boolean onReceiveChat(S02PacketChat packetChat) {
        return true;
    }
}
