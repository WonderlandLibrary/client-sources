/*
 * Decompiled with CFR 0.145.
 */
package us.amerikan.events;

import com.darkmagician6.eventapi.events.Event;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;

public class EventAim
implements Event {
    private static float yaw;
    private static float pitch;
    private static float oldyaw;
    private static float oldpitch;
    private static float setyaw;
    private static float setpitch;

    public EventAim(float y2, float p2) {
        yaw = y2;
        pitch = p2;
    }

    public static boolean isSet() {
        Minecraft.getMinecraft();
        Minecraft.getMinecraft();
        return yaw != Minecraft.thePlayer.rotationYaw;
    }

    public static void setYaw(float y2) {
        oldyaw = yaw;
        yaw = y2;
    }

    public static void setPitch(float p2) {
        oldpitch = pitch;
        pitch = p2;
    }
}

