package com.alan.clients.module.impl.movement.longjump;

import com.alan.clients.component.impl.player.PacketlessDamageComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.component.impl.render.PercentageComponent;
import com.alan.clients.event.EventBusPriorities;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.packet.PacketReceiveEvent;
import com.alan.clients.module.impl.movement.LongJump;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S12PacketEntityVelocity;

public class WatchdogLongJump extends Mode<LongJump> {

    public WatchdogLongJump(String name, LongJump parent) {
        super(name, parent);
    }
    @Override
    public void onEnable() {
        PacketlessDamageComponent.setActive(1f);
    }

    @EventLink(EventBusPriorities.LOW)
    public final Listener<PacketReceiveEvent> onPacketReceive = event -> {
        if (event.isCancelled()) return;

        final Packet<?> p = event.getPacket();

        if (p instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity wrapper = (S12PacketEntityVelocity) p;

            if (wrapper.getEntityID() == mc.thePlayer.getEntityId()) {
                event.setCancelled();
                mc.thePlayer.motionY = wrapper.getMotionY() / 8000.0D;

                mc.thePlayer.ticksSinceVelocity = 0;
            }
        }
    };

    @EventLink
    public final Listener<PreMotionEvent> onPreMotion = event -> {
        PercentageComponent.setActive((float) (PacketlessDamageComponent.isActive() ? PacketlessDamageComponent.getJumps() : 6) / 6);

        if (PacketlessDamageComponent.isActive()) {
            MoveUtil.stop();
        } else {
            if (mc.thePlayer.onGround) {
                MoveUtil.strafe(MoveUtil.getAllowedHorizontalDistance() - Math.random() / 100);
                mc.thePlayer.jump();
            }

            event.setOnGround(false);

            if (mc.thePlayer.offGroundTicks == 1) {
                mc.timer.timerSpeed = 0.2f;
                event.setOnGround(true);
            }

            if (mc.thePlayer.offGroundTicks <= 5 && mc.thePlayer.offGroundTicks >= 1) {
                PingSpoofComponent.spoof(99999, true, true, false, false, true);
            }

            if (mc.thePlayer.ticksSinceVelocity <= 20 && mc.thePlayer.ticksSinceVelocity > 1) {
                mc.thePlayer.motionY += 0.0239;

                MoveUtil.moveFlying(0.0039);
            }
        }
    };
}