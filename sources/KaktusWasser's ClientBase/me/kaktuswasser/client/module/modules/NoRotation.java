// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.ReceivePacket;
import me.kaktuswasser.client.module.Module;

public class NoRotation extends Module
{
    public NoRotation() {
        super("NoRotation", -9868951, Category.COMBAT);
        this.setTag("No Rotation");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof ReceivePacket) {
            final ReceivePacket event = (ReceivePacket)e;
            if (event.getPacket() instanceof S08PacketPlayerPosLook) {
                final S08PacketPlayerPosLook poslook = (S08PacketPlayerPosLook)event.getPacket();
                if (NoRotation.mc.thePlayer != null && NoRotation.mc.thePlayer.rotationYaw != -180.0f && NoRotation.mc.thePlayer.rotationPitch != 0.0f) {
                    poslook.field_148936_d = NoRotation.mc.thePlayer.rotationYaw;
                    poslook.field_148937_e = NoRotation.mc.thePlayer.rotationPitch;
                }
            }
        }
    }
}
