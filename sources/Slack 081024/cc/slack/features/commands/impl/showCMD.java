package cc.slack.features.commands.impl;

import cc.slack.start.Slack;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.features.modules.api.Module;
import cc.slack.utils.other.PrintUtil;

@CMDInfo(
        name = "Show",
        alias = "show_module",
        description = "Usage: .show module_name"
)
public class showCMD extends CMD {

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length != 1) {
            PrintUtil.message("§cInvalid use of arguments. Format: .show module_name");
            return;
        }

        String module_name = args[0].replace('_', ' ');

        Module module;

        try {
            module = Slack.getInstance().getModuleManager().getModuleByName(module_name);
        } catch (Exception e) {
            PrintUtil.message("§cCould not find module named: " + module_name);
            return;
        }

        module.render = true;

        PrintUtil.message("§f Set §c" + module.getName() + "§f to §c SHOW §f.");
    }
}
