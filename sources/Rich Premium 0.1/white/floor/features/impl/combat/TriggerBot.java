package white.floor.features.impl.combat;

import clickgui.setting.Setting;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import white.floor.Main;
import white.floor.event.EventTarget;
import white.floor.event.event.EventUpdate;
import white.floor.features.Category;
import white.floor.features.Feature;

public class TriggerBot extends Feature {

    EntityLivingBase target;

    public TriggerBot() {
        super("TriggerBot", "auto ydar.",0, Category.COMBAT);
    }

    @EventTarget
    public void pank(EventUpdate drain) {
        target = (EntityLivingBase) mc.objectMouseOver.entityHit;

        if (mc.player.getCooledAttackStrength(0) == 1 && mc.player.getDistanceToEntity(target) <= 3 && target instanceof EntityPlayer) {
            mc.playerController.attackEntity(mc.player, target);
            mc.player.swingArm(EnumHand.MAIN_HAND);
        }
    }
}
