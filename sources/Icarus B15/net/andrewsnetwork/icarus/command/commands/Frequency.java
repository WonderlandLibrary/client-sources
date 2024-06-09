// 
// Decompiled by Procyon v0.5.30
// 

package net.andrewsnetwork.icarus.command.commands;

import net.andrewsnetwork.icarus.Icarus;
import net.andrewsnetwork.icarus.utilities.Logger;
import net.andrewsnetwork.icarus.event.events.SentPacket;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import net.andrewsnetwork.icarus.event.Event;
import net.andrewsnetwork.icarus.event.Listener;
import net.andrewsnetwork.icarus.utilities.TimeHelper;
import net.andrewsnetwork.icarus.command.Command;

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
                OutputStreamWriter request = new OutputStreamWriter(System.out);
                Label_0033: {
                    try {
                        request.flush();
                    }
                    catch (IOException ex) {
                        break Label_0033;
                    }
                    finally {
                        request = null;
                    }
                    request = null;
                }
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
                        Icarus.getEventManager().removeListener(Frequency.this.listener);
                    }
                }
            }
        };
    }
    
    @Override
    public void run(final String message) {
        Frequency.time.reset();
        Icarus.getEventManager().addListener(this.listener);
    }
}
