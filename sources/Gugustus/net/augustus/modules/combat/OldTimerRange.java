package net.augustus.modules.combat;

import java.awt.Color;
import java.util.ArrayList;
import net.augustus.events.EventReadPacket;
import net.augustus.events.EventRender3D;
import net.augustus.events.EventSendPacket;
import net.augustus.modules.Categorys;
import net.augustus.modules.Module;
import net.augustus.utils.TimeHelper;
import net.lenni0451.eventapi.reflection.EventTarget;
import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;

public class OldTimerRange
extends Module {
    private final TimeHelper timeHelper = new TimeHelper();
    private final TimeHelper timeHelper2 = new TimeHelper();
    private final ArrayList<Integer> diffs = new ArrayList();
    public long balanceCounter = 0L;
    private long lastTime;
    private WorldClient lastWorld = null;

    public OldTimerRange() {
        super("TimerRange", new Color(23, 233, 123), Categorys.COMBAT);
    }

    @EventTarget
    public void onEventRender3D(EventRender3D eventRender3D) {
    }

    @EventTarget
    public void onEventSendPacket(EventSendPacket eventSendPacket) {
        Packet<?> packet = eventSendPacket.getPacket();
        if (packet instanceof C03PacketPlayer) {
            if (this.lastWorld != null && this.lastWorld != TimerRange.mc.theWorld) {
                this.balanceCounter = 0L;
                this.diffs.clear();
            }
            if (this.balanceCounter > 0L) {
                --this.balanceCounter;
            }
            long diff = System.currentTimeMillis() - this.lastTime;
            this.diffs.add((int)diff);
            this.balanceCounter += (diff - 50L) * -3L;
            this.lastTime = System.currentTimeMillis();
            if (this.balanceCounter > 150L) {
                // empty if block
            }
            this.lastWorld = TimerRange.mc.theWorld;
        }
    }

    @EventTarget
    public void onEventReadPacket(EventReadPacket eventReadPacket) {
        Packet<?> packet = eventReadPacket.getPacket();
        if (packet instanceof S08PacketPlayerPosLook) {
            this.balanceCounter += -100L;
        }
    }
}

