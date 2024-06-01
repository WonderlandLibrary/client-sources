package com.polarware.module.impl.player.nofall;

import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.player.NoFallModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 3/02/2022
 */
public class WatchdogNoFall extends Mode<NoFallModule> {
    boolean active;

    public WatchdogNoFall(String name, NoFallModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (FallDistanceComponent.distance > 3.5 && !(PlayerUtil.blockRelativeToPlayer(0, MoveUtil.predictedMotion(mc.thePlayer.motionY), 0) instanceof BlockAir) && mc.thePlayer.ticksSinceTeleport > 50) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 50 - Math.random(), mc.thePlayer.posZ, false));

            FallDistanceComponent.distance = 0;
        }
    };
}