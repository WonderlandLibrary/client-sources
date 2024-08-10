package cc.slack.features.commands.impl;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.utils.other.PrintUtil;
import net.minecraft.client.gui.GuiScreen;

import cc.slack.utils.client.IMinecraft;

@CMDInfo(
        name = "ign",
        alias = "IGN",
        description = "Returns your current ign."
)
public class ignCMD extends CMD {

    @Override
    public void onCommand(String[] args, String command) {
        PrintUtil.message("§f Your IGN is: §e" + IMinecraft.mc.thePlayer.getNameClear() + " §f and and it was copied to your clipboard");
        GuiScreen.setClipboardString(IMinecraft.mc.thePlayer.getNameClear());
    }

}
