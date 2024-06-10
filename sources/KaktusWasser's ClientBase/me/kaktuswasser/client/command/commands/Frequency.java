// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal

package me.kaktuswasser.client.command.commands;

import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

import me.kaktuswasser.client.Client;
import me.kaktuswasser.client.command.Command;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.Listener;
import me.kaktuswasser.client.event.events.SentPacket;
import me.kaktuswasser.client.utilities.Logger;
import me.kaktuswasser.client.utilities.TimeHelper;

public class Frequency extends Command
{
    public static final TimeHelper time;
    private Listener listener;
    
    static {
        time = new TimeHelper();
    }
    
    public Frequency() {
        super("frequency", "none");
        this.listener = new Listener() {
            private int ticks;
            
            @Override
            public void onEvent(final Event e) {
                if (e instanceof SentPacket) {
                    ++this.ticks;
                    final SentPacket event = (SentPacket)e;
                    if (this.ticks > 1) {
                        Logger.writeChat("§aCancelling ONE packet. " + Logger.getPrefix("Frequency"));
                        event.setCancelled(true);
                        this.ticks = 0;
                    }
                    if (Frequency.time.hasReached(2000L)) {
                        Logger.writeChat("Frequency removed.");
                        Client.getEventManager().removeListener(Frequency.this.listener);
                    }
                }
            }
        };
    }
    
    @Override
    public void run(final String message) {
        Frequency.time.reset();
        Client.getEventManager().addListener(this.listener);
    }
}
