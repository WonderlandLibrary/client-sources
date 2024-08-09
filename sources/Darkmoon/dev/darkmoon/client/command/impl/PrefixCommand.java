package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.command.CommandManager;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;

@Command(name = "prefix", description = "Изменяет префикс для команд")
public class PrefixCommand extends CommandAbstract {
    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
        sendMessage(ChatFormatting.WHITE + CommandManager.getPrefix() + "prefix " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "symbol" + ChatFormatting.GRAY + ">");
    }

    @Override
    public void execute(String[] args) throws Exception {
        CommandManager.setPrefix(args[1]);
        sendMessage(ChatFormatting.GRAY + "Префикс успешно изменен на " + ChatFormatting.RED + args[1]);
    }
}
