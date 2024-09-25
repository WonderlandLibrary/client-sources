/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.world;

import java.util.ArrayList;
import java.util.Collections;
import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.util.AxisAlignedBB;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;
import skizzle.modules.ModuleManager;
import skizzle.settings.NumberSetting;

public class AntiVoid
extends Module {
    public NumberSetting height;
    public double flyHeight;
    public ArrayList<Packet> packets = new ArrayList();

    public AntiVoid() {
        super(Qprot0.0("\udf3c\u71c5\ue485\ua7ed\uf104\u5aa8\u8c26\ub3ac"), 0, Module.Category.WORLD);
        AntiVoid Nigga;
        Nigga.height = new NumberSetting(Qprot0.0("\udf35\u71ce\ue498\ua7e3\uf13a\u5ab3"), 10.0, 0.0, 100.0, 1.0);
        Nigga.addSettings(Nigga.height);
    }

    public void updateFlyHeight() {
        AntiVoid Nigga;
        double Nigga2 = 1.0;
        AxisAlignedBB Nigga3 = Nigga.mc.thePlayer.getEntityBoundingBox().expand(0.0, 0.0, 0.0);
        Nigga.flyHeight = 0.0;
        while (Nigga.flyHeight < Nigga.mc.thePlayer.posY) {
            AxisAlignedBB Nigga4 = Nigga3.offset(0.0, -Nigga.flyHeight, 0.0);
            if (Minecraft.theWorld.checkBlockCollision(Nigga4)) {
                if (Nigga2 < 0.0) break;
                Nigga.flyHeight -= Nigga2;
                Nigga2 /= 2.0;
            }
            Nigga.flyHeight += Nigga2;
        }
    }

    @Override
    public void onDisable() {
        AntiVoid Nigga;
        Nigga.packets.clear();
        super.onDisable();
    }

    @Override
    public void onEnable() {
        AntiVoid Nigga;
        Nigga.packets.clear();
        super.onEnable();
    }

    @Override
    public void onEvent(Event Nigga) {
        AntiVoid Nigga2;
        if (Nigga2.mc.thePlayer != null && Nigga2.mc.thePlayer.ticksExisted > 20) {
            if (Nigga instanceof EventPacket) {
                Packet Nigga3 = ((EventPacket)Nigga).getPacket();
                if (Nigga3 instanceof C03PacketPlayer && (!(Nigga2.flyHeight > Nigga2.height.getValue()) || !(Nigga2.mc.thePlayer.fallDistance > Float.intBitsToFloat(2.09498419E9f ^ 0x7CDEEBFF)) || ModuleManager.fly.isEnabled())) {
                    Nigga2.packets.add(Nigga3);
                }
            } else if (Nigga instanceof EventUpdate) {
                EventUpdate cfr_ignored_0 = (EventUpdate)Nigga;
                if (Nigga2.packets.size() > 10) {
                    Nigga2.packets.remove(0);
                }
                Nigga2.updateFlyHeight();
                if (Nigga2.flyHeight > Nigga2.height.getValue() && Nigga2.mc.thePlayer.fallDistance > Float.intBitsToFloat(2.13859264E9f ^ 0x7F785579) && !ModuleManager.fly.isEnabled() && !Nigga2.packets.isEmpty()) {
                    Collections.reverse(Nigga2.packets);
                    for (Packet Nigga4 : Nigga2.packets) {
                        Nigga2.mc.getNetHandler().addToSendQueue(Nigga4);
                    }
                    Nigga2.packets.clear();
                }
            }
        }
    }

    public static {
        throw throwable;
    }
}

