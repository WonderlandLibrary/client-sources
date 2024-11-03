package dev.stephen.nexus.commands;

import dev.stephen.nexus.utils.mc.ChatUtils;
import lombok.Getter;
import net.minecraft.util.Formatting;


@Getter
public abstract class Command {
    protected String name;
    protected String[] commands;

    public Command(String name) {
        this.name = name;
        this.commands = new String[]{""};
    }

    public Command(String name, String[] commands) {
        this.name = name;
        this.commands = commands;
    }

    public static void sendMessage(String message) {
        ChatUtils.addMessageToChat(Formatting.GRAY + message);
    }

    public abstract void execute(String[] commands);
}