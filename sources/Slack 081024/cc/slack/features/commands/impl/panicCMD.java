package cc.slack.features.commands.impl;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.features.modules.api.Module;
import cc.slack.start.Slack;
import cc.slack.utils.other.PrintUtil;

import java.util.List;

@CMDInfo(
        name = "Panic",
        alias = "panic",
        description = "Disables all active modules."
)
public class panicCMD extends CMD {

    @Override
    public void onCommand(String[] args, String cmd) {
        try {
            List<Module> modules = Slack.getInstance().getModuleManager().getModules();

            for (Module module : modules) {
                if (module.isToggle()) {
                    module.toggle();
                }
            }

            PrintUtil.message("§fAll active modules have been §cdisabled§f.");
        } catch (Exception e) {
            PrintUtil.message("§fAn §cerror §foccurred while disabling modules.");
        }
    }
}
