package best.actinium.module.api.command.impl;

import best.actinium.Actinium;
import best.actinium.module.api.command.Command;
import best.actinium.module.api.config.ConfigManager;
import best.actinium.util.render.ChatUtil;

import java.io.File;
import java.util.Objects;

public final class ConfigCommand extends Command {

    private final File configFolder = new File(mc.mcDataDir, "/" + Actinium.NAME.toLowerCase()+ "/Configs");

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

        if ("list".equalsIgnoreCase(action)) {
            listConfigs();
        } else {
            String config = args.length == 3 ? args[2] : null;

            if ("save".equalsIgnoreCase(action))
                ConfigManager.saveConfig(config);

            if ("load".equalsIgnoreCase(action))
                ConfigManager.loadConfig(config);
        }
    }

    private void listConfigs() {
        ChatUtil.display("Listing of all the configs:");

        for (File file : Objects.requireNonNull(configFolder.listFiles())) {
            ChatUtil.display(file.getName().replace(".json",""));
        }
    }
}
