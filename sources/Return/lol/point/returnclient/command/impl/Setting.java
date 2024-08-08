package lol.point.returnclient.command.impl;

import lol.point.Return;
import lol.point.returnclient.command.Command;
import lol.point.returnclient.command.CommandInfo;
import lol.point.returnclient.module.Module;
import lol.point.returnclient.settings.impl.BooleanSetting;
import lol.point.returnclient.settings.impl.NumberSetting;
import lol.point.returnclient.settings.impl.StringSetting;
import lol.point.returnclient.util.minecraft.ChatUtil;

import java.util.ArrayList;
import java.util.List;

@CommandInfo(
        name = "Setting",
        usage = ".setting <Module> <Setting> <Value>",
        description = "helps you manage modules",
        aliases = {"setting", "settings", "value", "change"}
)
public class Setting extends Command {

    @Override
    public void execute(String... args) {
        String commandString = String.join(" ", args);
        List<String> parsedArgs = parseArguments(commandString);

        if (parsedArgs.size() != 3) {
            ChatUtil.addChatMessage("§9Usage: §c\"§b" + getUsage() + "§c\"");
            return;
        }

        String moduleName = parsedArgs.get(0);
        String settingName = parsedArgs.get(1);
        String value = parsedArgs.get(2);

        Module module = Return.INSTANCE.moduleManager.getModuleByName(moduleName);
        if (module == null) {
            ChatUtil.addChatMessage("§cModule §e" + moduleName + "§c was not found!");
            return;
        }

        lol.point.returnclient.settings.Setting setting = Return.INSTANCE.settingManager.getSettingByName(module, settingName);
        if (setting == null) {
            ChatUtil.addChatMessage("§cSetting §e" + settingName + "§c was not found!");
            return;
        }

        try {
            switch (setting) {
                case BooleanSetting set -> {
                    if (!value.equalsIgnoreCase("true") && !value.equalsIgnoreCase("false")) {
                        ChatUtil.addChatMessage("§eValue must be either §atrue §eor §cfalse§e!");
                        return;
                    }
                    set.value = value.equalsIgnoreCase("true");
                    ChatUtil.addChatMessage("§aSet §b" + module.name + "'s §e" + set.name + "§a to §e" + (set.value ? "§atrue" : "§cfalse") + "§a.");
                }
                case NumberSetting set -> {
                    set.value = Double.parseDouble(value);
                    ChatUtil.addChatMessage("§aSet §b" + module.name + "'s §e" + set.name + "§a to §e" + value + "§a.");
                }
                case StringSetting set -> {
                    if (set.modes.stream().noneMatch(s -> s.equals(value))) {
                        ChatUtil.addChatMessage("§cMode §e" + value + "§c was not found!");
                        return;
                    }
                    set.value = value;
                    ChatUtil.addChatMessage("§aSet §b" + module.name + "'s §e" + set.name + "§a to §e" + value + "§a.");
                }
                default -> {
                }
            }
        } catch (NumberFormatException e) {
            ChatUtil.addChatMessage("§cThe number you typed was malformed!");
        }
    }

    private List<String> parseArguments(String commandString) {
        List<String> arguments = new ArrayList<>();
        boolean inQuotes = false;
        StringBuilder currentArg = new StringBuilder();

        for (char c : commandString.toCharArray()) {
            if (c == '\"') {
                inQuotes = !inQuotes;
            } else if (c == ' ' && !inQuotes) {
                if (!currentArg.isEmpty()) {
                    arguments.add(currentArg.toString());
                    currentArg.setLength(0);
                }
            } else {
                currentArg.append(c);
            }
        }

        if (!currentArg.isEmpty()) {
            arguments.add(currentArg.toString());
        }

        return arguments;
    }
}