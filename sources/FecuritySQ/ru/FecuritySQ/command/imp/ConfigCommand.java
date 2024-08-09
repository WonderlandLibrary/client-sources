package ru.FecuritySQ.command.imp;


import net.minecraft.client.Minecraft;
import net.minecraft.util.Util;
import net.minecraft.util.text.TextFormatting;
import ru.FecuritySQ.FecuritySQ;
import ru.FecuritySQ.command.Command;
import ru.FecuritySQ.command.CommandAbstract;
import ru.FecuritySQ.config.ConfigManager;

import java.awt.*;
import java.io.File;
import java.net.URI;
import java.util.Objects;

@Command(name = "cfg", description = "Сохраняет конфиги чита")
public class ConfigCommand extends CommandAbstract {

    @Override
    public void execute(String[] args) throws Exception {
        if (args.length >= 2) {
            try {
                if (args[1].equals("save")) {
                    FecuritySQ.get().getConfigManager().saveConfig(args[2]);
                    sendMessage("Конфигурация " + TextFormatting.GRAY + args[2] + TextFormatting.RESET + " сохранена.");
                }
                if (args[1].equals("load")) {
                    if (FecuritySQ.get().getConfigManager().loadConfig(args[2])) {
                        sendMessage("Конфигурация " + TextFormatting.GRAY + args[2] + TextFormatting.RESET + " загружена.");
                    } else {
                        sendMessage("Конфигурация " + TextFormatting.GRAY + args[2] + TextFormatting.RESET + " не была загружена. (скорее всего, её просто нету)");
                    }
                }
                if (args[1].equals("delete")) {
                    FecuritySQ.get().getConfigManager().deleteConfig(args[2]);
                    sendMessage("Конфигурация " + TextFormatting.GRAY + args[2] + TextFormatting.RESET + " удалена.");
                }
                if (args[1].equals("list")) {
                    File file = new File(Minecraft.getInstance().gameDir ,"\\FecuritySQ\\configs");
                    if (ConfigManager.getLoadedConfigs().isEmpty()) {
                        sendMessage("Конфигурации не найдены.");
                    }
                    System.out.println(ConfigManager.getLoadedConfigs().size());
                    for (File s : Objects.requireNonNull(file.listFiles())) {
                        sendMessage(s.getName().replaceAll(".json", ""));
                    }
                }
                if (args[1].equals("dir")) {
                    File file = new File(Minecraft.getInstance().gameDir ,"\\FecuritySQ\\configs");
                    Util.getOSType().openFile(file);
                }
            } catch (Exception exception) {
                exception.printStackTrace();
            }
        } else {
            error();
        }
    }

    @Override
    public void error() {
        sendMessage(TextFormatting.GRAY + "Ошибка в использовании" + TextFormatting.WHITE + ":");
        sendMessage(TextFormatting.WHITE + "." + "cfg load " + TextFormatting.GRAY + "<name>");
        sendMessage(TextFormatting.WHITE + "." + "cfg save " + TextFormatting.GRAY + "<name>");
        sendMessage(TextFormatting.WHITE + "." + "cfg delete " + TextFormatting.GRAY + "<name>");
        sendMessage(TextFormatting.WHITE + "." + "cfg list" + TextFormatting.GRAY
                + " - показать список конфигов");
        sendMessage(TextFormatting.WHITE + "." + "cfg dir" + TextFormatting.GRAY
                + " - открыть папку с конфигами");
    }
}
