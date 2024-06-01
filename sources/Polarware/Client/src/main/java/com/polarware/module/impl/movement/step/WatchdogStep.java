package com.polarware.module.impl.movement.step;

import com.polarware.module.impl.movement.StepModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreMotionEvent;
import com.polarware.event.impl.other.StepEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * @author Auth
 * @since 22/3/2022
 */

public class WatchdogStep extends Mode<StepModule> {

    private final NumberValue height = new NumberValue("Height", this, 1, 1, 1.5, 0.1);
    private final NumberValue timer = new NumberValue("Timer", this, 0.5, 0.1, 1, 0.1);

    public WatchdogStep(String name, StepModule parent) {
        super(name, parent);
    }

    @EventLink()
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {

        if (mc.thePlayer.onGround && !PlayerUtil.inLiquid()) {
            mc.thePlayer.stepHeight = this.height.getValue().floatValue();
        } else {
            mc.thePlayer.stepHeight = 0.6F;
        }
    };

    @EventLink()
    public final Listener<StepEvent> onStep = event -> {
        if (!mc.thePlayer.onGround || PlayerUtil.inLiquid()) {
            return;
        }

        final double height = event.getHeight();

        if (height <= 0.6F) {
            return;
        }

        final double[] values;

        if (height > 1.015) {
            values = new double[]{0.42F, 0.7532F, 1.0F, 0.98F};
        } else if (height > 0.875) {
            values = new double[]{0.42F, 0.7532F, 1.0F};
        } else {
            values = new double[]{0.39F, 0.6938F};
        }

        mc.timer.timerSpeed = this.timer.getValue().floatValue();

        for (final double d : values) {
            PacketUtil.send(new C03PacketPlayer.C04PacketPlayerPosition(mc.thePlayer.posX, mc.thePlayer.posY + d, mc.thePlayer.posZ, false));
        }
    };

    @Override
    public void onDisable() {
        mc.thePlayer.stepHeight = 0.6F;
    }
}