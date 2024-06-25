package cc.slack.features.commands.impl;

import cc.slack.Slack;
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
   public void onCommand(String[] args, String command) {
      if (args.length > 0) {
         PrintUtil.message("Â§cInvalid use of arguments, expected none.");
      } else {
         PrintUtil.message("Command list: ");
         Slack.getInstance().getCmdManager().getCommands().forEach((cmd) -> {
            PrintUtil.message(cmd.getName() + " - " + ChatFormatting.GRAY + cmd.getDescription());
         });
      }
   }
}
