package dev.star.commands.impl;

import dev.star.commands.Command;
import dev.star.config.LocalConfig;
import dev.star.utils.misc.IOUtils;

public class DeleteCommand extends Command {
    public DeleteCommand() {
        super("Delete", "Delets a config", "name");
    }


    @Override
    public void execute(String[] args) {
        if (args.length != 1) {
            usage();
        } else {
            String Name = args[0];
            LocalConfig localConfig = new LocalConfig(Name);
            if (localConfig.name != null) {

                IOUtils.deleteFile(localConfig.getFile());

            }
        }
    }
}
