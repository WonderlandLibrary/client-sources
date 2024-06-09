package dev.vertic.module.impl.other;

import dev.vertic.module.Module;
import dev.vertic.module.api.Category;
import dev.vertic.setting.impl.BooleanSetting;
import net.minecraft.entity.player.EntityPlayer;

public class Teams extends Module {

    private final BooleanSetting scoreboard = new BooleanSetting("ScoreBoard", false);
    private final BooleanSetting color = new BooleanSetting("Color", true);

    public Teams() {
        super("Teams", "Excludes teammates from certain modules.", Category.OTHER);
    }

    public boolean isTeam(EntityPlayer player) {
        if (mc.thePlayer == null) {
            return false;
        }

        if (scoreboard.isEnabled() && mc.thePlayer.getTeam() != null && player.getTeam() != null &&
                mc.thePlayer.getTeam().isSameTeam(player.getTeam())) {
            return true;
        }

        String displayName = mc.thePlayer.getDisplayName().getUnformattedText();

        if (color.isEnabled() && displayName != null && player.getDisplayName() != null) {
            String targetName = player.getDisplayName().getFormattedText().replace("§r", "");
            String clientName = displayName.replace("§r", "");
            return targetName.startsWith("§" + clientName.charAt(1));
        }

        return false;

    }

}
