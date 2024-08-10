package cc.slack.features.modules.impl.movement.spiders.impl;

import cc.slack.events.State;
import cc.slack.events.impl.network.PacketEvent;
import cc.slack.events.impl.player.MotionEvent;
import cc.slack.features.modules.impl.movement.spiders.ISpider;
import cc.slack.utils.player.MovementUtil;
import cc.slack.utils.player.PlayerUtil;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class VulcanSpider implements ISpider {

    @Override
    public void onMotion(MotionEvent event) {
        if (event.getState() == State.PRE) {
            if (mc.thePlayer.isCollidedHorizontally) {
                if (mc.thePlayer.ticksExisted % 2 == 0) {
                    event.setGround(true);
                    mc.thePlayer.motionY = PlayerUtil.getJumpHeight();
                }

                final double yaw = MovementUtil.getDirection();
                event.setX(event.getX() - -MathHelper.sin((float) yaw) * 0.1f);
                event.setZ(event.getZ() - MathHelper.cos((float) yaw) * 0.1f);
            }
        }
    }

    @Override
    public String toString() {
        return "Vulcan";
    }
}
