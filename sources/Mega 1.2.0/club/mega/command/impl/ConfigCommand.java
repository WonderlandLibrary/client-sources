package club.mega.command.impl;

import club.mega.command.Command;
import club.mega.util.ChatUtil;
import club.mega.util.ConfigUtil;

import java.io.File;
import java.util.Objects;

@Command.Info(name = "Config", description = "Save / Load / Create / List / Delete Configs", usage = ".config (save / load / create / list / delete) <name>", aliases = {"config", "c"})
public class ConfigCommand extends Command {

    @Override
    public void execute(final String[] args) {
        switch (args[0].toLowerCase()) {
            case "save":
                ConfigUtil.save(args[1]);
                ChatUtil.sendMessage("Config saved ``" + args[1] + "``");
                break;
            case "load":
                ConfigUtil.load(args[1]);
                ChatUtil.sendMessage("Config loaded ``" + args[1] + "``");
                break;
            case "create":
                if (!new File(MC.mcDataDir, "Async/Configs/ " + args[1] + ".json").exists()) {
                    ConfigUtil.save(args[1]);
                    ChatUtil.sendMessage("Config created ``" + args[1] + "``");
                } else
                    ChatUtil.sendMessage("Config ``" + args[1] + "`` is already existing");
                break;
            case "list":
                final File[] listOfFiles = new File(MC.mcDataDir, "/Mega/Configs").listFiles();

                for (int i = 0; i < Objects.requireNonNull(listOfFiles).length; i++)
                    ChatUtil.sendMessage(listOfFiles[i].getName().replace(".json", ""));
                break;
            case "delete":
                final File file = new File(MC.mcDataDir, "Mega/Configs/ " + args[1] + ".json");
                if (file.exists()) {
                    file.delete();
                    ChatUtil.sendMessage("Config deleted ``" + args[1] + "``");
                } else
                    ChatUtil.sendMessage("Config ``" + args[1] + "`` doesn't exist");

                break;
        }
    }
}
