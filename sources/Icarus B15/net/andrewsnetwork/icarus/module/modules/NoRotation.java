// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.minecraft.network.play.server.S08PacketPlayerPosLook;
import net.andrewsnetwork.icarus.event.events.ReceivePacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.module.Module;

public class NoRotation extends Module
{
    public NoRotation() {
        super("NoRotation", -9868951, Category.COMBAT);
        this.setTag("No Rotation");
    }
    
    @Override
    public void onEvent(final Event e) {
        Label_0040: {
            if (e instanceof EatMyAssYouFuckingDecompiler) {
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                try {
                    request.flush();
                }
                catch (IOException ex) {
                    break Label_0040;
                }
                finally {
                    request = null;
                }
                request = null;
            }
        }
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
