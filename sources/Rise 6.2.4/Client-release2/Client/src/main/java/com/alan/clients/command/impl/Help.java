package com.alan.clients.command.impl;

import com.alan.clients.Client;
import com.alan.clients.command.Command;
import com.alan.clients.util.chat.ChatUtil;
import com.alan.clients.util.localization.Localization;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public final class Help extends Command {

    public Help() {
        super("command.help.description", "help", "?");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getCommandManager().getCommandList()
                .forEach(command -> ChatUtil.display(StringUtils.capitalize(command.getExpressions()[0]) + " " + Arrays.toString(command.getExpressions()) + " \2478» \2477" + Localization.get(command.getDescription())));
    }
}