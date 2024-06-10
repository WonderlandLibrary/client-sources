// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.ReachDistance;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;

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
