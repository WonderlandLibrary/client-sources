package net.futureclient.client.modules.miscellaneous.songstealer;

import net.futureclient.client.AB;
import net.futureclient.client.events.EventMotion;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.miscellaneous.SongStealer;
import net.futureclient.client.KF;
import net.futureclient.client.n;

public class Listener1 extends n<KF>
{
    public final SongStealer k;
    
    public Listener1(final SongStealer k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventMotion)event);
    }
    
    public void M(final EventMotion eventMotion) {
        switch (AB.k[eventMotion.M().ordinal()]) {
            case 1:
                if (!SongStealer.getMinecraft1().isSingleplayer()) {
                    SongStealer.M(this.k);
                    return;
                }
                if (!SongStealer.getMinecraft2().isGamePaused() && SongStealer.getMinecraft3().player != null) {
                    SongStealer.M(this.k);
                    break;
                }
                break;
        }
    }
}
