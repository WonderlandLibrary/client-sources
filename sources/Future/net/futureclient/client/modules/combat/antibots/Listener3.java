package net.futureclient.client.modules.combat.antibots;

import net.futureclient.client.events.Event;
import net.futureclient.client.events.EventWorld;
import net.futureclient.client.modules.combat.AntiBots;
import net.futureclient.client.fF;
import net.futureclient.client.n;

public class Listener3 extends n<fF>
{
    public final AntiBots k;
    
    public Listener3(final AntiBots k) {
        this.k = k;
        super();
    }
    
    public void M(final EventWorld eventWorld) {
        this.k.K.clear();
        AntiBots.e(this.k, !AntiBots.getMinecraft8().isSingleplayer() && AntiBots.getMinecraft9().getCurrentServerData() != null && AntiBots.getMinecraft17().getCurrentServerData().serverIP.toLowerCase().contains("hypixel"));
        AntiBots.M(this.k, !AntiBots.getMinecraft10().isSingleplayer() && AntiBots.getMinecraft14().getCurrentServerData() != null && AntiBots.getMinecraft4().getCurrentServerData().serverIP.toLowerCase().contains("mineplex"));
    }
    
    public void M(final Event event) {
        this.M((EventWorld)event);
    }
}
