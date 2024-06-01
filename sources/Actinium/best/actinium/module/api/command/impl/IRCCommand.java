package best.actinium.module.api.command.impl;

import best.actinium.Actinium;
import best.actinium.module.Module;
import best.actinium.module.api.command.Command;
import best.actinium.util.io.BackendUtil;
import best.actinium.util.render.ChatUtil;
import net.minecraft.util.Formatting;
import org.lwjglx.input.Keyboard;

import java.io.IOException;
import java.util.Arrays;

public final class IRCCommand extends Command {
    public IRCCommand() {
        super("irc");
    }

    @Override
    public void execute(String[] args) {
        String message = mc.thePlayer.getName() + " > "  /*BackendUtil.getUserName()*/  + String.join(" ", Arrays.copyOfRange(args, 1, args.length));
        BackendUtil.sendMessage(BackendUtil.WEBHOOK_IRC_URL, message);
    }
}