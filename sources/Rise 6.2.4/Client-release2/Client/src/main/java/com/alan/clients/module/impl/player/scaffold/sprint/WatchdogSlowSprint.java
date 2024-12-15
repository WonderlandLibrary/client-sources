package com.alan.clients.module.impl.player.scaffold.sprint;


import com.alan.clients.event.Listener;
import com.alan.clients.event.Priorities;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.StrafeEvent;
import com.alan.clients.event.impl.packet.PacketSendEvent;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.player.MoveUtil;
import com.alan.clients.value.Mode;
import net.minecraft.potion.Potion;

public class WatchdogSlowSprint extends Mode<Scaffold> {

    private int ticks;

    public WatchdogSlowSprint(String name, Scaffold parent) {
        super(name, parent);
    }

    @EventLink(value = Priorities.VERY_LOW)
    public final Listener<StrafeEvent> onPreMotion = event -> {
        ticks++;

        mc.gameSettings.keyBindSprint.setPressed(true);
        mc.thePlayer.setSprinting(true);


        double limit = mc.thePlayer.isPotionActive(Potion.moveSpeed) ? 0.118 : 0.083;

        if (mc.thePlayer.onGround)
            MoveUtil.strafe(limit - (mc.thePlayer.ticksSinceTeleport <= 40 ? 0.1 : 0) - (Math.random() * 0.0001));

        if (MoveUtil.speed() >= limit && !mc.gameSettings.keyBindJump.isKeyDown()) {
            MoveUtil.moveFlying((MoveUtil.speed() - limit) * -1);
        }
    };


    @EventLink
    public final Listener<PacketSendEvent> eventListener = event -> {
    };

    @Override
    public void onDisable() {
        mc.thePlayer.capabilities.isFlying = false;
        this.mc.timer.timerSpeed = 1;
    }

}