package com.alan.clients.module.impl.player.scaffold.sprint;

import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.JumpEvent;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.motion.PreUpdateEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import net.minecraft.block.BlockAir;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.util.BlockPos;

public class WatchdogSlow extends Mode<Scaffold> {

    private int ticks;

    public WatchdogSlow(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<JumpEvent> onJump = event -> {
        mc.thePlayer.omniSprint = true;
        mc.thePlayer.setSprinting(true);
    };

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<PreUpdateEvent> onPreMotion = event -> {
        mc.gameSettings.keyBindSprint.setPressed(false);
        mc.thePlayer.setSprinting(false);
        mc.thePlayer.omniSprint = false;

        int value = 1;

        if (mc.thePlayer.onGroundTicks >= 2) {
            mc.thePlayer.jump();
        }

        if (mc.thePlayer.onGroundTicks <= 20) {
            mc.gameSettings.keyBindSneak.setPressed(ticks % value == 0 && PlayerUtil.blockRelativeToPlayer(0, -1, 0) instanceof BlockAir);
//
            mc.thePlayer.safeWalk = (ticks - 1) % value != 0;

            if (mc.thePlayer.onGround) {
                getParent().placeDelay.setValue(1);
                getParent().placeDelay.setSecondValue(1);
             } else {
                getParent().placeDelay.setValue(0);
                getParent().placeDelay.setSecondValue(0);
            }
        }

//        double limit = mc.thePlayer.ticksSinceTeleport <= 20 ? 0.05 : (mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.118 : 0.08);
//
//        if (MoveUtil.speed() >= limit && mc.thePlayer.ticksSinceVelocity > 20 && !mc.gameSettings.keyBindJump.isKeyDown()) {
//            MoveUtil.moveFlying((MoveUtil.speed() - limit) * -1);
//        }
    };

    @EventLink
    private final Listener<PreMotionEvent> preMotionEventListener = event -> {
    };

    @EventLink
    public final Listener<PacketSendEvent> eventListener = event -> {
        if (event.getPacket() instanceof C08PacketPlayerBlockPlacement) {
            C08PacketPlayerBlockPlacement packet = (C08PacketPlayerBlockPlacement) event.getPacket();

            if (!packet.getPosition().equals(new BlockPos(-1, -1, -1))) {
                ticks++;
            }
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1;
    }

}