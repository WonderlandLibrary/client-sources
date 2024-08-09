package dev.darkmoon.client.command.impl;

import com.mojang.realmsclient.gui.ChatFormatting;
import dev.darkmoon.client.command.Command;
import dev.darkmoon.client.command.CommandAbstract;

@Command(name = "way", description = "Показывает расстояние от координаты")
public class WayCommand extends CommandAbstract {

    public static int x, z;
    public static boolean gps = false;

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length > 1) {
            if (args[1].equals("off")) {
                x = 0;
                z = 0;
                gps = false;
            } else {
                gps = true;
                x = Integer.parseInt(args[1]);
                z = Integer.parseInt(args[2]);
            }
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(ChatFormatting.GRAY + "Ошибка в использовании" + ChatFormatting.WHITE + ":");
        sendMessage(ChatFormatting.WHITE + ".way " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "x, z" + ChatFormatting.GRAY + ">");
        sendMessage(ChatFormatting.WHITE + ".way " + ChatFormatting.GRAY + "<"
                + ChatFormatting.RED + "off" + ChatFormatting.GRAY + ">");
    }
}
