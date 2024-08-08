package net.futureclient.client.modules.render.freecam;

import net.futureclient.loader.mixin.common.network.packet.serverbound.wrapper.ICPacketPlayer;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.network.play.client.CPacketEntityAction;
import net.futureclient.client.events.Event;
import net.futureclient.client.modules.render.Freecam;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener3 extends n<Ag>
{
    public final Freecam k;
    
    public Listener3(final Freecam k) {
        this.k = k;
        super();
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
    
    @Override
    public void M(final Ag ag) {
        final CPacketEntityAction cPacketEntityAction;
        if (ag.M() instanceof CPacketEntityAction && ((cPacketEntityAction = (CPacketEntityAction)ag.M()).getAction().equals((Object)CPacketEntityAction.Action.START_SNEAKING) || cPacketEntityAction.getAction().equals((Object)CPacketEntityAction.Action.STOP_SNEAKING) || cPacketEntityAction.getAction().equals((Object)CPacketEntityAction.Action.START_SPRINTING) || cPacketEntityAction.getAction().equals((Object)CPacketEntityAction.Action.STOP_SPRINTING))) {
            ag.M(true);
        }
        if (ag.M() instanceof CPacketPlayer || ag.M() instanceof CPacketPlayer.Position || ag.M() instanceof CPacketPlayer.PositionRotation || ag.M() instanceof CPacketPlayer.Rotation) {
            ((ICPacketPlayer)ag.M()).setX(Freecam.M(this.k).posX);
            ((ICPacketPlayer)ag.M()).setY(Freecam.M(this.k).posY);
            ((ICPacketPlayer)ag.M()).setZ(Freecam.M(this.k).posZ);
            ((ICPacketPlayer)ag.M()).setYaw(Freecam.M(this.k).rotationYaw);
            ((ICPacketPlayer)ag.M()).setPitch(Freecam.M(this.k).rotationPitch);
            ((ICPacketPlayer)ag.M()).setMoving(false);
            ((ICPacketPlayer)ag.M()).setRotating(false);
        }
    }
}
