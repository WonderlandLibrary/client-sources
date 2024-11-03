package dev.star.commands.impl;

import dev.star.Client;
import dev.star.commands.Command;
import dev.star.utils.misc.Multithreading;

public class CreateCommand extends Command {


    public CreateCommand() {
        super("Create", "Creates a config", "name");
    }


    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            usage();
        } else {
            String Name = args[0];


            Multithreading.runAsync(() -> {
                Client.INSTANCE.getConfigManager().saveConfig(Name);
                Client.INSTANCE.getConfigManager().collectConfigs();
            });
        }
    }



}
