package dev.star.commands.impl;

import dev.star.Client;
import dev.star.commands.Command;
import dev.star.config.LocalConfig;
import dev.star.gui.notifications.NotificationManager;
import dev.star.gui.notifications.NotificationType;
import dev.star.utils.misc.Multithreading;

public class SaveCommand extends Command {

    public SaveCommand() {
        super("Save", "Save a config", "name");
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
                    String saveData = Client.INSTANCE.getConfigManager().serialize();

                    if (Client.INSTANCE.getConfigManager().saveConfig(localConfig.getName(), saveData)) {
                        NotificationManager.post(NotificationType.SUCCESS, "Success", "Config update successfully!");
                    } else {
                        NotificationManager.post(NotificationType.WARNING, "Error", "The config did not update successfully!");
                    }
                });
            }
        }
    }
}
