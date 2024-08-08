package net.futureclient.client.modules.combat.criticals;

import net.futureclient.client.events.Event;
import net.minecraft.client.network.NetHandlerPlayClient;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.futureclient.client.nE;
import net.futureclient.client.Re;
import net.minecraft.network.play.client.CPacketUseEntity;
import net.futureclient.client.modules.combat.Criticals;
import net.futureclient.client.Ag;
import net.futureclient.client.n;

public class Listener2 extends n<Ag>
{
    public final Criticals k;
    
    public Listener2(final Criticals k) {
        this.k = k;
        super();
    }
    
    @Override
    public void M(final Ag ag) {
        if (ag.M() instanceof CPacketUseEntity) {
            final CPacketUseEntity cPacketUseEntity = (CPacketUseEntity)ag.M();
            if (!Criticals.M(this.k) || cPacketUseEntity.getAction() != CPacketUseEntity.Action.ATTACK) {
                return;
            }
            switch (Re.k[this.k.mode.M().ordinal()]) {
                case 1:
                    Criticals.getMinecraft6().player.jump();
                case 2: {
                    Criticals.getMinecraft2().player.jump();
                    final EntityPlayerSP player = Criticals.getMinecraft().player;
                    player.motionY /= 0.0;
                }
                case 3: {
                    final double[] array;
                    final int length = (array = new double[] { 1.273197475E-314, 0.0, 1.9522361275E-314, 0.0 }).length;
                    int i = 0;
                    int n = 0;
                    while (i < length) {
                        final double n2 = array[n];
                        final NetHandlerPlayClient connection = Criticals.getMinecraft1().player.connection;
                        final double posX = Criticals.getMinecraft4().player.posX;
                        final double n3 = Criticals.getMinecraft5().player.posY + n2;
                        final double posZ = Criticals.getMinecraft3().player.posZ;
                        ++n;
                        connection.sendPacket((Packet)new CPacketPlayer.Position(posX, n3, posZ, false));
                        i = n;
                    }
                    break;
                }
            }
        }
    }
    
    public void M(final Event event) {
        this.M((Ag)event);
    }
}
