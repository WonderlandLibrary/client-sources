package net.futureclient.client;

import net.futureclient.client.events.Event;
import net.minecraft.client.gui.GuiDownloadTerrain;
import net.minecraft.util.math.BlockPos;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;

public class FD extends n<we>
{
    public final ne k;
    
    public FD(final ne k) {
        this.k = k;
        super();
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventPacket.M();
            if (ne.getMinecraft12().player.isEntityAlive() && ne.getMinecraft7().world.isBlockLoaded(new BlockPos(ne.getMinecraft5().player.posX, ne.getMinecraft14().player.posY, ne.getMinecraft2().player.posZ), false) && !(ne.getMinecraft15().currentScreen instanceof GuiDownloadTerrain)) {
                final ne.ze ze;
                if (!((ne.FE)ne.e(this.k).M()).equals(ne.FE.k) && (ze = ne.M(this.k).remove(sPacketPlayerPosLook.getTeleportId())) != null && ze.M() == sPacketPlayerPosLook.getX() && ze.b() == sPacketPlayerPosLook.getY() && ze.e() == sPacketPlayerPosLook.getZ()) {
                    eventPacket.M(true);
                    return;
                }
                ne.M(this.k, sPacketPlayerPosLook.getTeleportId());
            }
        }
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
}
