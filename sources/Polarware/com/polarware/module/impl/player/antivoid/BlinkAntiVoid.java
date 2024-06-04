package com.polarware.module.impl.player.antivoid;

import com.polarware.component.impl.player.BlinkComponent;
import com.polarware.component.impl.player.FallDistanceComponent;
import com.polarware.module.impl.movement.LongJumpModule;
import com.polarware.module.impl.other.TestModule;
import com.polarware.module.impl.player.AntiVoidModule;
import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.event.bus.Listener;
import com.polarware.event.annotations.EventLink;
import com.polarware.event.impl.motion.PreUpdateEvent;
import com.polarware.util.packet.PacketUtil;
import com.polarware.util.player.PlayerUtil;
import com.polarware.value.Mode;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.network.play.client.C00PacketKeepAlive;
import net.minecraft.network.play.client.C01PacketChatMessage;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C0FPacketConfirmTransaction;
import net.minecraft.util.Vec3;

public class BlinkAntiVoid extends Mode<AntiVoidModule> {

    private final NumberValue distance = new NumberValue("Distance", this, 5, 0, 10, 1);
    private final BooleanValue toggleScaffold = new BooleanValue("Toggle Scaffold", this, false);
    private Vec3 position, motion;
    private boolean wasVoid, setBack;
    private int overVoidTicks;
    private ScaffoldModule scaffoldModule;
    private LongJumpModule longJump;
    private TestModule test;

    public BlinkAntiVoid(String name, AntiVoidModule parent) {
        super(name, parent);
    }

    @Override
    public void onDisable() {
        BlinkComponent.blinking = false;
    }

    @EventLink()
    public final Listener<PreUpdateEvent> onPreUpdate = event -> {

        if (mc.thePlayer.ticksExisted <= 50) return;

        if (scaffoldModule == null) {
            scaffoldModule = getModule(ScaffoldModule.class);
        }

        if (longJump == null) {
            longJump = getModule(LongJumpModule.class);
        }

        if (test == null) {
            test = getModule(TestModule.class);
        }

        if (scaffoldModule.isEnabled() || longJump.isEnabled() || test.isEnabled()) {
            return;
        }

        boolean overVoid = !mc.thePlayer.onGround && !PlayerUtil.isBlockUnder(30, true);

        if (overVoid) {
            overVoidTicks++;
        } else if (mc.thePlayer.onGround) {
            overVoidTicks = 0;
        }

        if (overVoid && position != null && motion != null && overVoidTicks < 30 + distance.getValue().doubleValue() * 20) {
            if (!setBack) {
                wasVoid = true;

                BlinkComponent.blinking = true;
                BlinkComponent.setExempt(C0FPacketConfirmTransaction.class, C00PacketKeepAlive.class, C01PacketChatMessage.class);

                if (FallDistanceComponent.distance > distance.getValue().doubleValue() || setBack) {
                    PacketUtil.sendNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(position.xCoord, position.yCoord - 0.1 - Math.random(), position.zCoord, false));
                    if (this.toggleScaffold.getValue()) {
                        getModule(ScaffoldModule.class).setEnabled(true);
                    }

                    BlinkComponent.packets.clear();

                    FallDistanceComponent.distance = 0;

                    setBack = true;
                }
            } else {
                BlinkComponent.blinking = false;
            }
        } else {

            setBack = false;

            if (wasVoid) {
                BlinkComponent.blinking = false;
                wasVoid = false;
            }

            motion = new Vec3(mc.thePlayer.motionX, mc.thePlayer.motionY, mc.thePlayer.motionZ);
            position = new Vec3(mc.thePlayer.posX, mc.thePlayer.posY, mc.thePlayer.posZ);
        }
    };
}