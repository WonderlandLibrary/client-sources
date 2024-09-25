/*
 * Decompiled with CFR 0.150.
 */
package skizzle.events.listeners;

import net.minecraft.client.Minecraft;
import net.minecraft.network.Packet;
import skizzle.events.Event;

public class EventVelocity
extends Event<EventVelocity> {
    public Packet packet;
    public int entityID;
    public double motionY;
    public boolean processPacket;
    public double motionX;
    public double motionZ;
    public Minecraft mc = Minecraft.getMinecraft();

    public static {
        throw throwable;
    }

    public Packet getPacket() {
        EventVelocity Nigga;
        return Nigga.packet;
    }

    public void setMotionX(float Nigga) {
        Nigga.motionX = Nigga;
    }

    public double getMotionZ() {
        EventVelocity Nigga;
        return Nigga.motionZ;
    }

    public double getMotionY() {
        EventVelocity Nigga;
        return Nigga.motionY;
    }

    public void setMotionY(float Nigga) {
        Nigga.motionY = Nigga;
    }

    public void setMotionZ(float Nigga) {
        Nigga.motionZ = Nigga;
    }

    public EventVelocity(int Nigga, double Nigga2, double Nigga3, double Nigga4, Packet Nigga5) {
        EventVelocity Nigga6;
        Nigga6.entityID = Nigga;
        Nigga6.motionX = Nigga2;
        Nigga6.motionY = Nigga3;
        Nigga6.motionZ = Nigga4;
        Nigga6.packet = Nigga5;
    }

    public double getMotionX() {
        EventVelocity Nigga;
        return Nigga.motionX;
    }
}

