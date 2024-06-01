package com.polarware.module.impl.movement.step;

import com.polarware.module.impl.movement.StepModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.StepEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.value.Mode;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Alan
 * @since 22/3/2022
 */

public class VulcanStep extends Mode<StepModule> {

    public VulcanStep(String name, StepModule parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.ticksSinceJump > 11) {
            mc.thePlayer.stepHeight = 1;
        } else {
            mc.thePlayer.stepHeight = 0.6F;
        }
    };

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {

        if (event.getHeight() > 0.6) {
            mc.timer.timerSpeed = 0.5f;
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + 0.5F, mc.thePlayer.posZ, true));
        }
    };
}