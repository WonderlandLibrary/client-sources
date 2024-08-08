package net.futureclient.client.modules.render.freecam;

import net.futureclient.client.events.Event;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener4 extends n<we>
{
    public final Freecam k;
    
    public Listener4(final Freecam k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook && Freecam.getMinecraft().player.isEntityAlive() && Freecam.getMinecraft6().world.isBlockLoaded(new BlockPos(Freecam.getMinecraft5().player.posX, Freecam.getMinecraft8().player.posY, Freecam.getMinecraft11().player.posZ), false) && !(Freecam.getMinecraft9().currentScreen instanceof GuiDownloadTerrain)) {
            eventPacket.M(true);
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
