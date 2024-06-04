package com.polarware.command.impl;

import com.polarware.Client;
import com.polarware.command.Command;
import com.polarware.util.chat.ChatUtil;
import com.polarware.util.localization.Localization;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

/**
 * @author Auth
 * @since 3/02/2022
 */
public final class Help extends Command {

    public Help() {
        super("command.help.description", "help", "?");
    }

    @Override
    public void execute(final String[] args) {
        Client.INSTANCE.getCommandManager()
                .forEach(command -> ChatUtil.display(StringUtils.capitalize(command.getExpressions()[0]) + " " + Arrays.toString(command.getExpressions()) + " \2478» \2477" + Localization.get(command.getDescription())));
    }
}