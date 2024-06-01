package com.polarware.module.impl.movement.speed;

import com.polarware.module.impl.movement.SpeedModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.motion.StrafeEvent;
import com.polarware.event.impl.other.TeleportEvent;
import com.polarware.event.impl.network.PacketSendEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.MoveUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C0BPacketEntityAction;
import net.minecraft.potion.Potion;

/**
 * @author Alan
 * @since 18/11/2021
 */

public class BlocksMCSpeed extends Mode<SpeedModule> {

    private boolean reset;
    private double speed;

    public BlocksMCSpeed(String name, SpeedModule parent) {
        super(name, parent);
    }


    @EventLink()
    public final Listener<StrafeEvent> onStrafe = event -> {

        final double base = MoveUtil.getAllowedHorizontalDistance();
        final boolean potionActive = mc.thePlayer.isPotionActive(Potion.moveSpeed);

        if (MoveUtil.isMoving()) {
            switch (mc.thePlayer.offGroundTicks) {
                case 0:
                    mc.thePlayer.motionY = MoveUtil.jumpBoostMotion(0.42f);
                    speed = base * (potionActive ? 2.15 : 2.15);
                    break;

                case 1:
                    speed -= 0.8 * (speed - base);
                    break;

                default:
                    speed -= speed / MoveUtil.BUNNY_FRICTION;
                    break;
            }

            reset = false;
        } else if (!reset) {
            speed = 0;

            reset = true;
            speed = MoveUtil.getAllowedHorizontalDistance();
        }

        if (mc.thePlayer.isCollidedHorizontally) {
            speed = MoveUtil.getAllowedHorizontalDistance();
        }

        event.setSpeed(Math.max(speed, base), Math.random() / 2000);
    };

    @EventLink()
    public final Listener<TeleportEvent> onTeleport = event -> {
        speed = 0;
    };

    @Override
    public void onDisable() {
        speed = 0;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (!MoveUtil.isMoving()) {
            event.setPosX(event.getPosX() + (Math.random() - 0.5) / 100);
            event.setPosZ(event.getPosZ() + (Math.random() - 0.5) / 100);
        }

        PacketUtil.sendNoEvent(new C0BPacketEntityAction(mc.thePlayer, C0BPacketEntityAction.Action.START_SPRINTING));
    };


    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        if (event.getPacket() instanceof C0BPacketEntityAction) {
            event.setCancelled(true);
        }
    };
}