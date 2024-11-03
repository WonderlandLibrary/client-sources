package dev.star.module.impl.combat;

import dev.star.event.impl.player.MotionEvent;
import dev.star.module.Category;
import dev.star.module.Module;
import dev.star.module.settings.impl.NumberSetting;
import net.minecraft.entity.player.EntityPlayer;

public final class Teams extends Module {


    public Teams() {
        super("Teams", Category.COMBAT, "Does Not Target Teammates");
    }

    public boolean canAttack(EntityPlayer entity) {
        if (!this.isEnabled()) return true;

        if(mc.thePlayer.getTeam() != null && entity.getTeam() != null) {
            Character targetColor = entity.getDisplayName().getFormattedText().charAt(1);
            Character playerColor = mc.thePlayer.getDisplayName().getFormattedText().charAt(1);
            if(playerColor.equals(targetColor)) {
                return false;
            }
        } else {
            return false;
        }

        return true;
    }
}
