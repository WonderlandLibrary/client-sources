package dev.excellent.client.command.impl;

import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;

public final class NameCommand extends Command {

    public NameCommand() {
        super("", "name", "username", "nick", "nickname");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length == 1) {
            final String name = mc.player.getNameClear();
            mc.keyboardListener.setClipboardString(name);
            ChatUtil.addText("Name copied: " + name);
        } else {
            error();
        }
    }
}
