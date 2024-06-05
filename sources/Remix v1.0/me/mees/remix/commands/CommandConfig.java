package me.mees.remix.commands;

import me.satisfactory.base.command.*;
import me.satisfactory.base.utils.*;
import me.satisfactory.base.utils.file.*;

public class CommandConfig extends Command
{
    public CommandConfig() {
        super("Config", "config");
    }
    
    @Override
    public void execute(final String[] args) {
        if (args.length != 1) {
            MiscellaneousUtil.sendInfo("An error has occured! Please try .Config <Config>");
        }
        else {
            if (FileUtils.getConfigFile(args[0]).exists()) {
                MiscellaneousUtil.sendInfo(FileUtils.getConfigFileConf(args[0]).getAbsolutePath());
                MiscellaneousUtil.sendInfo("Successfully loaded the config '" + args[0] + "'");
            }
            MiscellaneousUtil.sendInfo("The config '" + args[0] + "' does not exist!");
        }
    }
}
