package arsenic.command.impl;

import arsenic.command.Command;
import arsenic.command.CommandInfo;
import arsenic.config.ConfigManager;
import arsenic.main.Nexus;
import arsenic.utils.minecraft.PlayerUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@CommandInfo(name = "config", args = {"save/load/list", "config name"}, aliases = {"c"}, help = "helps you manipulate configs", minArgs = 1)
public class ConfigCommand extends Command {
    //don't scroll down if you wish to have living braincells
    ArrayList<String> args = new ArrayList<>();

    @Override
    public void execute(String[] args) {
        ConfigManager configManager = Nexus.getNexus().getConfigManager();
        if (args[0].equalsIgnoreCase("load")) {
            try {
                configManager.saveConfig(); // save the current config before we load another one
                configManager.loadConfig(args[1]);
                PlayerUtils.addWaterMarkedMessageToChat("loaded " + args[1]);
            } catch (NullPointerException e) {
                PlayerUtils.addWaterMarkedMessageToChat(args[1] + " does not exist");
            }
        } else if (args[0].equalsIgnoreCase("list")) {
            PlayerUtils.addWaterMarkedMessageToChat("Configs that are available: ");
            PlayerUtils.addWaterMarkedMessageToChat(configManager.getConfigList());
        } else if (args[0].equalsIgnoreCase("save") || args[0].equalsIgnoreCase("create")) {
            try {
                configManager.createConfig(args[1]);
                PlayerUtils.addWaterMarkedMessageToChat("created/saved " + args[1]);
            } catch (ArrayIndexOutOfBoundsException r) {
                PlayerUtils.addWaterMarkedMessageToChat("could not create/save a config with the name " + args[1]);
            }
        } else {
            PlayerUtils.addWaterMarkedMessageToChat(args[0] + " is not a valid argument");
        }
    }

    @Override
    protected List<String> getAutoComplete(String str, int arg, List<String> list) {
        return arg == 0 ?
                getClosestArg(str) :
                Collections.singletonList(""); //idk bro dont ask me i never worked with such things
    }

    private List<String> getClosestArg(String name) { //idk wtf i did but atleast it works
        if (args.isEmpty()) { //most scuffed thing you'll ever see BUT IT WORKS AAAAAA
            args.add("list");
            args.add("save");
            args.add("load");
        }
        return args.stream().filter(c -> c.toLowerCase().startsWith(name.toLowerCase()) && c.length() > name.length()).map(String::toLowerCase).collect(Collectors.toList());
    }
}