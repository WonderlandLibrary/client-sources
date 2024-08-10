package cc.slack.features.commands.impl;

import cc.slack.start.Slack;
import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.utils.other.PrintUtil;
import net.minecraft.util.ChatFormatting;

@CMDInfo(
        name = "Help",
        alias = "h",
        description = "Displays all of Slack's commands."
)
public class HelpCMD extends CMD {

    @Override
    public void onCommand(String[] args, String command) {
        if (args.length > 0) {
            PrintUtil.message("§cInvalid use of arguments, expected none.");
            return;
        }

        PrintUtil.message("Command list: ");
        Slack.getInstance().getCmdManager().getCommands().forEach(cmd -> PrintUtil.message(cmd.getName() + " - " + ChatFormatting.GRAY + cmd.getDescription()));
    }

}
