package dev.star.commands.impl;

import dev.star.Client;
import dev.star.commands.Command;
import dev.star.config.LocalConfig;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.misc.FileUtils;
import dev.star.utils.misc.Multithreading;

public class LoadCommand extends Command {

    public LoadCommand() {
        super("Load", "Loads a config", "name");
    }


    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            usage();
        } else {
            String Name = args[0];
            LocalConfig localConfig = new LocalConfig(Name);
            if (localConfig.name != null) {

                Multithreading.runAsync(() -> {
                    String loadData = FileUtils.readFile(localConfig.getFile());

                    if (Client.INSTANCE.getConfigManager().loadConfig(loadData, false)) {
                        NotificationManager.post(NotificationType.SUCCESS, "Success", "Config loaded successfully!");
                    } else {
                        NotificationManager.post(NotificationType.WARNING, "Error", "The online config did not load successfully!");
                    }
                });
            }
        }
    }



}
