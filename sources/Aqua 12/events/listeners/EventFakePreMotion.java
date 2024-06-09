// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.client.Minecraft;
import events.Event;

public class EventFakePreMotion extends Event
{
    public static EventFakePreMotion getInstance;
    private static float yaw;
    private static float pitch;
    
    public EventFakePreMotion(final float yaw, final float pitch) {
        EventFakePreMotion.yaw = yaw;
        EventFakePreMotion.pitch = pitch;
    }
    
    public static float getYaw() {
        return EventFakePreMotion.yaw;
    }
    
    public void setYaw(final float yaw) {
        EventFakePreMotion.yaw = yaw;
        Minecraft.getMinecraft().thePlayer.renderYawOffset = RotationUtil.calculateCorrectYawOffset(yaw);
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
    }
    
    public static float getPitch() {
        return EventFakePreMotion.pitch;
    }
    
    public void setPitch(final float pitch) {
        EventFakePreMotion.pitch = pitch;
        Minecraft.getMinecraft().thePlayer.prevRotationPitchHead = pitch;
        Minecraft.getMinecraft().thePlayer.rotationPitchHead = pitch;
    }
}
