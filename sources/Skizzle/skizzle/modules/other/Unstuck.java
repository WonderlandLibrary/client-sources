/*
 * Decompiled with CFR 0.150.
 */
package skizzle.modules.other;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.minecraft.util.Vec3;
import skizzle.events.Event;
import skizzle.events.listeners.EventPacket;
import skizzle.events.listeners.EventUpdate;
import skizzle.modules.Module;

public class Unstuck
extends Module {
    public int reset = 0;
    public boolean onGroundLag;
    public int packets = 0;
    public Vec3 lagbackPacket;

    public Unstuck() {
        super(Qprot0.0("\uaf77\u71c5\u9425\ua7f0\u8e84\u2afb\u8c24"), 0, Module.Category.OTHER);
        Unstuck Nigga;
    }

    @Override
    public void onEnable() {
        Nigga.packets = 0;
    }

    @Override
    public void onEvent(Event Nigga) {
        Unstuck Nigga2;
        if (Nigga instanceof EventPacket && Nigga2.mc.thePlayer != null && Nigga2.mc.thePlayer.ticksExisted > 10) {
            Packet Nigga3;
            EventPacket Nigga4 = (EventPacket)Nigga;
            if (Nigga4.getPacket() instanceof S08PacketPlayerPosLook) {
                ++Nigga2.packets;
                Nigga3 = (S08PacketPlayerPosLook)Nigga4.getPacket();
                Nigga2.lagbackPacket = new Vec3(Nigga3.x, Nigga3.y, Nigga3.z);
                Nigga2.onGroundLag = Nigga2.mc.thePlayer.onGround;
            }
            if (Nigga4.getPacket() instanceof C03PacketPlayer && Nigga2.packets > 0) {
                Nigga3 = (C03PacketPlayer)Nigga4.getPacket();
                ((C03PacketPlayer)Nigga3).x = Nigga2.lagbackPacket.xCoord;
                ((C03PacketPlayer)Nigga3).y = Nigga2.lagbackPacket.yCoord;
                ((C03PacketPlayer)Nigga3).z = Nigga2.lagbackPacket.zCoord;
                ((C03PacketPlayer)Nigga3).onGround = Nigga2.onGroundLag;
                Nigga2.mc.thePlayer.posX = Nigga2.lagbackPacket.xCoord;
                Nigga2.mc.thePlayer.posY = Nigga2.lagbackPacket.yCoord;
                Nigga2.mc.thePlayer.posZ = Nigga2.lagbackPacket.zCoord;
                Nigga2.mc.thePlayer.setPosition(Nigga2.lagbackPacket.xCoord, Nigga2.lagbackPacket.yCoord, Nigga2.lagbackPacket.zCoord);
                Nigga2.mc.thePlayer.motionX = 0.0;
                Nigga2.mc.thePlayer.motionY = 0.0;
                Nigga2.mc.thePlayer.motionZ = 0.0;
            }
        }
        if (Nigga instanceof EventUpdate) {
            if (Nigga2.packets > 0) {
                ++Nigga2.reset;
            }
            if (Nigga2.reset > 1 + Nigga2.packets) {
                Nigga2.packets = 0;
                Nigga2.reset = 0;
            }
        }
    }

    public static {
        throw throwable;
    }
}

