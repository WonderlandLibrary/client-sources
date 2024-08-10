package cc.slack.features.commands.impl;

import cc.slack.start.Slack;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.utils.other.PrintUtil;
import cc.slack.features.modules.api.Module;
import org.lwjgl.input.Keyboard;

@CMDInfo(
        name = "Bind",
        alias = "b",
        description = "Binds a module to a key."
)
public class bindCMD extends CMD {

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length == 0) {
            PrintUtil.message("§cUsage: .bind [module] [key], .bind list, or .bind clear");
            return;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                bindMessage();
            } else if (args[0].equalsIgnoreCase("clear")) {
                clearAllBinds();
            } else {
                PrintUtil.message("§cUsage: .bind [module] [key], .bind list, or .bind clear");
            }
        }

        if (args.length == 2) {
            String module_name = toTitleCase(args[0].replace('_', ' '));
            Module module;
            try {
                module = Slack.getInstance().getModuleManager().getModuleByName(module_name);
            } catch (Exception e) {
                PrintUtil.message("§cCould not find module named: " + module_name);
                return;
            }

            try {
                String key = args[1].toUpperCase(); // Convert the key to uppercase
                module.setKey(Keyboard.getKeyIndex(key));
                PrintUtil.message("§f Bound §c" + module.getName() + "§f to §c" + key + "§f.");
            } catch (Exception e) {
                PrintUtil.message("§f Bound §c" + module.getName() + "§f to §c" + "NONE" + "§f.");
                module.setKey(0);
            }
        }

        if (args.length > 2) {
            PrintUtil.message("§cUsage: .bind [module] [key], .bind list, or .bind clear");
            PrintUtil.message("§cPlease replace spaces with underscores in the module name.");
        }
    }

    private void clearAllBinds() {
        for (Module module : Slack.getInstance().getModuleManager().getModules()) {
            module.setKey(0);
        }
        PrintUtil.message("§f All module key binds have been cleared.");
    }

    private void bindMessage() {
        PrintUtil.message("§fDisplaying Module binds:");
        for (Module m : Slack.getInstance().getModuleManager().getModules()) {
            if (m.getKey() != 0) {
                PrintUtil.msgNoPrefix("§c> " + m.getName() + " §7- §c" + Keyboard.getKeyName(m.getKey()));
            }
        }
    }

    private String toTitleCase(String input) {
        StringBuilder titleCase = new StringBuilder();
        boolean nextTitleCase = true;

        for (char c : input.toCharArray()) {
            if (Character.isSpaceChar(c)) {
                nextTitleCase = true;
            } else if (nextTitleCase) {
                c = Character.toTitleCase(c);
                nextTitleCase = false;
            } else {
                c = Character.toLowerCase(c);
            }

            titleCase.append(c);
        }

        return titleCase.toString();
    }
}
