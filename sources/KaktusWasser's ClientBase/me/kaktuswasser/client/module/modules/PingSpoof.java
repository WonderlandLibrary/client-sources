// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.play.client.C00PacketKeepAlive;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.EatMyAssYouFuckingDecompiler;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.module.Module;

public class PingSpoof extends Module
{
    public PingSpoof() {
        super("PingSpoof", -9320847, Category.EXPLOITS);
        this.setTag("Ping Spoof");
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof SentPacket) {
            final SentPacket event = (SentPacket)e;
            if (event.getPacket() instanceof C00PacketKeepAlive) {
                final C00PacketKeepAlive packet = (C00PacketKeepAlive)event.getPacket();
                packet.key = Integer.MAX_VALUE;
            }
        }
    }
}
