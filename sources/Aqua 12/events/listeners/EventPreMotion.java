// 
// Decompiled by Procyon v0.5.36
// 

package events.listeners;

import intent.AquaDev.aqua.utils.RotationUtil;
import net.minecraft.client.Minecraft;
import events.Event;

public class EventPreMotion extends Event
{
    public static EventPreMotion getInstance;
    private static float yaw;
    private static float pitch;
    
    public EventPreMotion(final float yaw, final float pitch) {
        EventPreMotion.yaw = yaw;
        EventPreMotion.pitch = pitch;
    }
    
    public static float getYaw() {
        return EventPreMotion.yaw;
    }
    
    public void setYaw(final float yaw) {
        EventPreMotion.yaw = yaw;
        Minecraft.getMinecraft().thePlayer.renderYawOffset = RotationUtil.calculateCorrectYawOffset(yaw);
        Minecraft.getMinecraft().thePlayer.rotationYawHead = yaw;
    }
    
    public static float getPitch() {
        return EventPreMotion.pitch;
    }
    
    public void setPitch(final float pitch) {
        EventPreMotion.pitch = pitch;
        Minecraft.getMinecraft().thePlayer.prevRotationPitchHead = pitch;
        Minecraft.getMinecraft().thePlayer.rotationPitchHead = pitch;
    }
}
