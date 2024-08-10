package cc.slack.features.commands.impl;

import cc.slack.start.Slack;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.utils.other.PrintUtil;
import cc.slack.features.modules.api.Module;

@CMDInfo(
        name = "Toggle",
        alias = "t",
        description = "Toggles a module."
)
public class ToggleCMD extends CMD {

    @Override
    public void onCommand(String[] args, String command) {
        String moduleName = "";
        try {
            if (args.length != 1) {
                PrintUtil.message("§cUsage: .t [module]");
                return;
            }

            moduleName = toTitleCase(args[0]); // Convert the module name to Title Case
            Module module = Slack.getInstance().getModuleManager().getModuleByName(moduleName);
            if (module == null) {
                PrintUtil.message("§cCould not find module named: " + moduleName);
                return;
            }

            boolean wasActive = module.isToggle();
            module.toggle();
            if (wasActive) {
                PrintUtil.message("§b" + moduleName + " §fis now " + "§cdisabled§f.");
            } else {
                PrintUtil.message("§b" + moduleName + " §fis now " + "§aenabled§f.");
            }
        } catch (Exception e) {
            PrintUtil.message("§cCould not find module named: §b" + moduleName);
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