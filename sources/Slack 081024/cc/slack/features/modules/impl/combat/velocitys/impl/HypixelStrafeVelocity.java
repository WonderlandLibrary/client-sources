package cc.slack.features.modules.impl.combat.velocitys.impl;

import cc.slack.events.impl.player.UpdateEvent;
import cc.slack.features.modules.impl.combat.velocitys.IVelocity;
import cc.slack.utils.player.MovementUtil;

public class HypixelStrafeVelocity implements IVelocity {

    @Override
    public void onUpdate(UpdateEvent event) {
        if (mc.thePlayer.hurtTime == 9) {
            MovementUtil.strafe(MovementUtil.getSpeed() * 0.9f);
        }
    }

    @Override
    public String toString() {
        return "Hypixel Damage Strafe";
    }
}
