package net.futureclient.client.modules.render.norotate;

import net.futureclient.loader.mixin.common.network.packet.clientbound.wrapper.ISPacketPlayerPosLook;
import net.minecraft.network.play.server.SPacketPlayerPosLook;
import net.futureclient.client.events.EventPacket;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.NoRotate;
import net.futureclient.client.we;
import net.futureclient.client.n;

public class Listener1 extends n<we>
{
    public final NoRotate k;
    
    public Listener1(final NoRotate k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((EventPacket)event);
    }
    
    public void M(final EventPacket eventPacket) {
        if (eventPacket.M() instanceof SPacketPlayerPosLook) {
            final SPacketPlayerPosLook sPacketPlayerPosLook = (SPacketPlayerPosLook)eventPacket.M();
            if (NoRotate.getMinecraft3().player.rotationYaw != -180.0f && NoRotate.getMinecraft1().player.rotationPitch != 0.0f) {
                ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setYaw(NoRotate.getMinecraft2().player.rotationYaw);
                ((ISPacketPlayerPosLook)sPacketPlayerPosLook).setPitch(NoRotate.getMinecraft().player.rotationPitch);
            }
        }
    }
}
