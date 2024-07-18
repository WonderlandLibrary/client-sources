package com.alan.clients.module.impl.player.nofall;

import com.alan.clients.component.impl.player.FallDistanceComponent;
import com.alan.clients.component.impl.player.PingSpoofComponent;
import com.alan.clients.event.Listener;
import com.alan.clients.event.annotations.EventLink;
import com.alan.clients.event.impl.motion.PreMotionEvent;
import com.alan.clients.event.impl.render.Render2DEvent;
import com.alan.clients.module.impl.player.NoFall;
import com.alan.clients.module.impl.player.Scaffold;
import com.alan.clients.util.packet.PacketUtil;
import com.alan.clients.util.player.PlayerUtil;
import com.alan.clients.value.Mode;
import com.alan.clients.value.impl.BooleanValue;
import net.minecraft.network.play.client.C03PacketPlayer;

public final class WatchdogNoFall extends Mode<NoFall> {

    private int blinkTicks = 0;

    private boolean start;

    public WatchdogNoFall(String name, NoFall parent) {
        super(name, parent);
    }

    public final BooleanValue LessFall = new BooleanValue("Packet", this, true);

    @EventLink
    public final Listener<PreMotionEvent> onPreMotionEvent = event -> {
        if (!PlayerUtil.isBlockUnder() || getModule(Scaffold.class).isEnabled()) {
            return;
        }

        if (this.mc.thePlayer.offGroundTicks == 1 && mc.thePlayer.motionY < 0 && PlayerUtil.isBlockUnder() && !PlayerUtil.isBlockUnder(3)) {
            start = true;
        }

        if (start) {
            PingSpoofComponent.spoof(99999, true, false, false, false, true);
            event.setOnGround(true);
            blinkTicks++;
        }

        if (start && mc.thePlayer.onGround) {
            PingSpoofComponent.dispatch();
            start = false;
            blinkTicks = 0;
        }

        if(!(blinkTicks > 0) && (FallDistanceComponent.distance > 3) && LessFall.getValue()){
            PacketUtil.send(new C03PacketPlayer(true));

            mc.timer.timerSpeed = 0.5f;
            FallDistanceComponent.distance = 0;
        }
    };

    @EventLink
    public final Listener<Render2DEvent> event = event -> {
        if (blinkTicks > 0) {
            mc.fontRendererObj.drawCentered("Blinking: " + blinkTicks, (double) mc.scaledResolution.getScaledWidth() / 2, (double) mc.scaledResolution.getScaledHeight() / 2 + 20, -1);
        }
    };
}
