package dev.excellent.client.command.impl;

import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;

public final class SayCommand extends Command {

    public SayCommand() {
        super("", "say", "chat");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length > 1) {
            ChatUtil.sendText(String.join(" ", args).substring(3).trim());
            ChatUtil.addText("Сообщение отправлено в чат");
        } else {
            error();
        }
    }
}
