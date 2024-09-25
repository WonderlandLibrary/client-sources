/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.combat;

import net.minecraft.network.play.client.C03PacketPlayer;
import skizzle.events.Event;
import skizzle.events.listeners.EventAttack;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventRenderGUI;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.settings.ModeSetting;
import skizzle.util.Timer;

public class Criticals
extends Module {
    public double lastDistance;
    public ModeSetting mode = new ModeSetting(Qprot0.0("\u8778\u71c4\ubc2d\ua7e1"), Qprot0.0("\u877b\u71c4\ubc0e\ua7f6\ub6e5\u02fa\u8c21\ueb14"), Qprot0.0("\u877b\u71c4\ubc0e\ua7f6\ub6e5\u02fa\u8c21\ueb14"), Qprot0.0("\u8774\u71ea\ubc0a"));
    public boolean crits;
    public int timeOut;
    public double distance;
    public Timer timer = new Timer();

    public static {
        throw throwable;
    }

    @Override
    public void onEnable() {
        Nigga.distance = 0.0;
    }

    public Criticals() {
        super(Qprot0.0("\u8776\u71d9\ubc20\ua7f0\ub6e3\u02ec\u8c2e\ueb1c\u5711"), 0, Module.Category.COMBAT);
        Criticals Nigga;
        Nigga.addSettings(Nigga.mode);
    }

    @Override
    public void onEvent(Event Nigga) {
        Criticals Nigga2;
        boolean cfr_ignored_0 = Nigga instanceof EventRenderGUI;
        if (Nigga instanceof EventUpdate) {
            if (!Nigga2.timer.hasTimeElapsed((long)-1451916685 ^ 0xFFFFFFFFA975819BL, false)) {
                Nigga2.distance += (double)Nigga2.mc.thePlayer.fallDistance - Nigga2.lastDistance > 0.0 ? (double)Nigga2.mc.thePlayer.fallDistance - Nigga2.lastDistance : 0.0;
                Nigga2.lastDistance = Nigga2.mc.thePlayer.fallDistance;
            } else {
                Nigga2.distance = 0.0;
            }
        }
        if (Nigga instanceof EventAttack) {
            if (Nigga2.mode.getMode().equals(Qprot0.0("\u877b\u71c4\ubc0e\ue27f\uf312\u02fa\u8c21\ueb14")) && Nigga2.mc.thePlayer.onGround) {
                if (Nigga2.timer.hasTimeElapsed((long)1107636656 ^ 0x42053258L, false)) {
                    Nigga2.mc.getNetHandler().addToSendQueueWithoutEvent(new C03PacketPlayer.C04PacketPlayerPosition(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY + 0.0, Nigga2.mc.thePlayer.posZ, false));
                }
                Nigga2.timer.reset();
            } else if (Nigga2.mode.getMode().equals(Qprot0.0("\u8774\u71ea\ubc0a")) && Nigga2.mode.getMode().equals(Qprot0.0("\u8774\u71ea\ubc0a"))) {
                Nigga2.mc.getNetHandler().addToSendQueueWithoutEvent(new C03PacketPlayer.C04PacketPlayerPosition(Nigga2.mc.thePlayer.posX, Nigga2.mc.thePlayer.posY + 0.0, Nigga2.mc.thePlayer.posZ, false));
            }
        }
        if (Nigga instanceof EventPacket) {
            EventPacket Nigga3 = (EventPacket)Nigga;
            if (Nigga2.mode.getMode().equals(Qprot0.0("\u877b\u71c4\ubc0e\ue27f\uf312\u02fa\u8c21\ueb14")) && !Nigga2.timer.hasTimeElapsed((long)1498796105 ^ 0x5955D3A1L, false) && Nigga3.getPacket() instanceof C03PacketPlayer) {
                C03PacketPlayer Nigga4 = (C03PacketPlayer)Nigga3.getPacket();
                if (Nigga2.distance > 1.5) {
                    Nigga2.distance = 0.0;
                    Nigga2.timer.lastMS = System.currentTimeMillis() - ((long)-1885472134 ^ 0xFFFFFFFF8F9DF992L);
                    Nigga4.onGround = true;
                } else {
                    Nigga4.onGround = false;
                }
            }
        }
        Nigga2.setSuffix(Nigga2.mode.getMode());
    }
}

