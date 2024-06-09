package io.github.liticane.clients.feature.command.impl;

import io.github.liticane.clients.Client;
import io.github.liticane.clients.config.ConfigManager;
import io.github.liticane.clients.feature.command.Command;
import io.github.liticane.clients.util.misc.ChatUtil;
import org.lwjglx.input.Keyboard;
import tv.twitch.chat.Chat;

import java.io.File;
import java.util.Objects;

public final class ConfigCommand extends Command {

    private final File configFolder = new File(mc.mcDataDir, "/" + Client.NAME + "/Configs");

    public ConfigCommand() {
        super("config", "cfg", "c");
    }

    @Override
    public void execute(String[] args) {
        if (args.length < 2 || args.length > 3) {
            ChatUtil.display("Invalid arguments! Usage: .config [save|load|list] [name]");
            return;
        }

        String action = args[1];
        String config = args[2];

        if ("list".equalsIgnoreCase(action))
            listConfigs();

        if ("save".equalsIgnoreCase(action))
            ConfigManager.saveConfig(config);

        if ("load".equalsIgnoreCase(action))
            ConfigManager.loadConfig(config);
    }

    private void listConfigs() {
        ChatUtil.display("Listing of all the configs:");

        for (File file : Objects.requireNonNull(configFolder.listFiles())) {
            ChatUtil.display(file.getName());
        }
    }
}
