// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.module.modules;

import net.andrewsnetwork.icarus.event.events.ReachDistance;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.events.EatMyAssYouFuckingDecompiler;
import net.andrewsnetwork.icarus.event.Event;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.andrewsnetwork.icarus.module.Module;

public class Reach extends Module
{
    private float reachDistance;
    
    public Reach() {
        super("Reach", -6527457, Category.EXPLOITS);
    }
    
    @Override
    public void onEnabled() {
        super.onEnabled();
        if (Reach.mc.thePlayer != null) {
            this.reachDistance = 32767.0f;
            Reach.mc.getNetHandler().addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(Reach.mc.thePlayer.posX, Double.NaN, Reach.mc.thePlayer.posZ, true));
        }
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
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C03PacketPlayer) {
                event.setCancelled(true);
            }
        }
        else if (e instanceof ReachDistance) {
            ((ReachDistance)e).setReach(this.reachDistance);
        }
    }
}
