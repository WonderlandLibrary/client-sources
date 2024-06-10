// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C03PacketPlayer;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;

public class NoFall extends Module
{
    public NoFall() {
        super("NoFall", -256, Category.PLAYER);
        this.setTag("No Fall");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C03PacketPlayer) {
                final C03PacketPlayer player = (C03PacketPlayer)event.getPacket();
                if (NoFall.mc.thePlayer.fallDistance >= 3.0f) {
                    player.field_149474_g = true;
                    NoFall.mc.thePlayer.fallDistance = 0.0f;
                }
            }
        }
    }
}
