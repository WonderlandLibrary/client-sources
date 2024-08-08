package net.futureclient.client.modules.movement.nofall;

import net.futureclient.client.events.Event;
import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketPlayer;
import net.futureclient.client.Ic;
import net.futureclient.client.lB;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.modules.movement.NoFall;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener1 extends n<Ag>
{
    public final NoFall k;
    
    public Listener1(final NoFall k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        if (ag.M() instanceof CPacketPlayer) {
            final CPacketPlayer cPacketPlayer = (CPacketPlayer)ag.M();
            switch (lB.k[((Ic.zc)NoFall.M(this.k).M()).ordinal()]) {
                case 1:
                    if (NoFall.getMinecraft1().player.fallDistance > 3.0f) {
                        ((ICPacketPlayer)cPacketPlayer).setOnGround(true);
                        return;
                    }
                    break;
                case 2:
                    if (NoFall.getMinecraft4().player.fallDistance > 3.0f) {
                        ((ICPacketPlayer)cPacketPlayer).setY(NoFall.getMinecraft8().player.posY + 1.3262473694E-314);
                        return;
                    }
                    break;
                case 3:
                    if (NoFall.getMinecraft20().player.fallDistance > 3.0f) {
                        NoFall.getMinecraft5().player.onGround = true;
                        NoFall.getMinecraft11().player.capabilities.isFlying = true;
                        NoFall.getMinecraft19().player.capabilities.allowFlying = true;
                        ((ICPacketPlayer)cPacketPlayer).setOnGround(false);
                        NoFall.getMinecraft13().player.velocityChanged = true;
                        NoFall.getMinecraft3().player.capabilities.isFlying = false;
                        NoFall.getMinecraft7().player.jump();
                        break;
                    }
                    break;
            }
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
