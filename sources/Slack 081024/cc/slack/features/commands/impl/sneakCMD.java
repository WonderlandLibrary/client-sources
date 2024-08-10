package cc.slack.features.commands.impl;

import cc.slack.features.commands.api.CMD;
import cc.slack.features.commands.api.CMDInfo;
import cc.slack.utils.other.PrintUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;

@CMDInfo(
        name = "Sneak",
        alias = "sneak",
        description = "Toggles sneaking."
)
public class sneakCMD extends CMD {

    @Override
    public void onCommand(String[] args, String cmd) {
        try {
            KeyBinding sneakKey = Minecraft.getMinecraft().gameSettings.keyBindSneak;

            if (sneakKey.isKeyDown()) {
                KeyBinding.setKeyBindState(sneakKey.getKeyCode(), false);
                PrintUtil.message("§fYou have §cstopped sneaking§f.");
            } else {
                KeyBinding.setKeyBindState(sneakKey.getKeyCode(), true);
                PrintUtil.message("§fYou are §anow sneaking§f.");
            }
        } catch (Exception e) {
            PrintUtil.message("§cAn error occurred while toggling sneak.");
        }
    }
}
