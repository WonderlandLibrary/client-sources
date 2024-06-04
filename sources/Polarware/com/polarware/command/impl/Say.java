package com.polarware.command.impl;

import com.polarware.command.Command;
import com.polarware.util.chat.ChatUtil;

/**
 * @author Auth
 * @since 3/02/2022
 */
public final class Say extends Command {

    public Say() {
        super("command.say.description", "say", "chat");
    }

    @Override
    public void execute(final String[] args) {
        if (args.length <= 1) {
            error(String.format(".%s <message>", args[0]));
        } else {
            ChatUtil.send(String.join(" ", args).substring(3).trim());
            ChatUtil.display("command.say.sent");
        }
    }
}
