package com.polarware.module.impl.combat;

import com.polarware.Client;
import com.polarware.event.annotations.EventLink;

import com.polarware.event.bus.Listener;
import com.polarware.event.impl.network.*;

import com.polarware.module.*;
import com.polarware.module.api.*;

import com.polarware.module.impl.player.ScaffoldModule;
import com.polarware.value.impl.BooleanValue;
import com.polarware.value.impl.BoundsNumberValue;
import com.polarware.value.impl.NumberValue;
import net.minecraft.client.multiplayer.WorldClient;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

@ModuleInfo(name = "Tick Base", description = "Skips ticks to give you an advantage in PvP.", category = Category.COMBAT)
public final class TickBaseModule extends Module {
    public NumberValue range = new NumberValue("Range", this, 3, 1, 6, 0.1);
    public final BoundsNumberValue elapsted =
            new BoundsNumberValue("Game ticks", this, 3, 3, 0.1, 10, 0.1);
    public final BooleanValue shouldTimer = new BooleanValue("Should Timer", this, false);
    public final BoundsNumberValue starttimer =
            new BoundsNumberValue("Start Timer", this, 1, 2, 0.0, 10, 0.05,() -> !shouldTimer.getValue());
    public final BoundsNumberValue endtimer =
            new BoundsNumberValue("End Timer", this, 1, 2, 0.0, 10, 0.05,() -> !shouldTimer.getValue());
    public long balanceCounter  = 0L;
    private long lastTime;
    private WorldClient lastWorld = null;

    @EventLink()
    public final Listener<PacketSendEvent> onPacketSend = event -> {
        final Packet <?> packet = event.getPacket();

        if (
                Client.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled()
        ) {
            return;
        }

        if (packet instanceof C03PacketPlayer) {
            if (this.lastWorld != null && this.lastWorld != mc.theWorld) {
                this.balanceCounter = 0L;
            }

            if (this.balanceCounter > 0L) {
                --this.balanceCounter;
            }

            final long diff = System.currentTimeMillis() - this.lastTime;
            this.balanceCounter += (diff - 50L) * -3L;
            this.lastTime = System.currentTimeMillis();

            this.lastWorld = mc.theWorld;
        }
    };

    @EventLink()
    public final Listener<PacketReceiveEvent> onPacketReceiveEvent = event -> {
        final Packet <?> packet = event.getPacket();

        if (
                Client.INSTANCE.getModuleManager().get(ScaffoldModule.class).isEnabled()
        ) {
            return;
        }

        if (packet instanceof S08PacketPlayerPosLook) {
            this.balanceCounter -= 100L;
        }
    };
}
