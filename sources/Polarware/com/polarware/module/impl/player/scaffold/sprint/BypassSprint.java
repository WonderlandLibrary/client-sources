package com.polarware.module.impl.player.scaffold.sprint;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.MathHelper;

public class BypassSprint extends Mode<ScaffoldModule> {

    public BypassSprint(String name, ScaffoldModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (MoveUtil.isMoving() && mc.thePlayer.isSprinting() && mc.thePlayer.onGround) {
            final double speed = MoveUtil.WALK_SPEED;
            final float yaw = (float) MoveUtil.direction();
            final double posX = MathHelper.sin(yaw) * speed + mc.thePlayer.posX;
            final double posZ = -MathHelper.cos(yaw) * speed + mc.thePlayer.posZ;
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(posX, event.getPosY(), posZ, false));
        }
//        mc.thePlayer.setSprinting(false);
    };
}
