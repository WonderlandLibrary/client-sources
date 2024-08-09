package dev.excellent.client.command.impl;

import dev.excellent.Excellent;
import dev.excellent.client.command.Command;
import dev.excellent.impl.util.chat.ChatUtil;
import net.minecraft.util.text.TextFormatting;
import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;

public final class HelpCommand extends Command {

    public HelpCommand() {
        super("", "help", "?");
    }

    @Override
    public void execute(final String[] args) {
        Excellent.getInst().getCommandManager().forEach(command -> ChatUtil.addText(TextFormatting.RED + StringUtils.capitalize(command.getExpressions()[0]) + " " + Arrays.toString(command.getExpressions()) /*+ " \2478» \2477" + command.getDescription()*/));
    }
}