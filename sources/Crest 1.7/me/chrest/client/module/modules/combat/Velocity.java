// 
// Decompiled by Procyon v0.5.30
// 

package me.chrest.client.module.modules.combat;

import me.chrest.event.EventTarget;
import net.minecraft.network.play.server.S27PacketExplosion;
import me.chrest.utils.ClientUtils;
import net.minecraft.network.play.server.S12PacketEntityVelocity;
import me.chrest.event.events.PacketReceiveEvent;
import me.chrest.client.option.Option;
import me.chrest.client.module.Module;

@Mod
public class Velocity extends Module
{
    @Option.Op(min = 0.0, max = 200.0, increment = 5.0, name = "Percent")
    private double percent;
    
    public Velocity() {
        this.percent = 0.0;
    }
    
    @EventTarget
    private void onPacketReceive(final PacketReceiveEvent event) {
        if (event.getPacket() instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)event.getPacket();
            if (ClientUtils.world().getEntityByID(packet.func_149412_c()) == ClientUtils.player()) {
                if (this.percent > 0.0) {
                    final S12PacketEntityVelocity tmp52_51 = packet;
                    tmp52_51.field_149415_b *= (int)(this.percent / 100.0);
                    final S12PacketEntityVelocity tmp71_70 = packet;
                    tmp71_70.field_149416_c *= (int)(this.percent / 100.0);
                    final S12PacketEntityVelocity tmp90_89 = packet;
                    tmp90_89.field_149414_d *= (int)(this.percent / 100.0);
                }
                else {
                    event.setCancelled(true);
                }
            }
        }
        else if (event.getPacket() instanceof S27PacketExplosion) {
            final S27PacketExplosion packet2;
            final S27PacketExplosion s27PacketExplosion = packet2 = (S27PacketExplosion)event.getPacket();
            s27PacketExplosion.field_149152_f *= (float)(this.percent / 100.0);
            final S27PacketExplosion s27PacketExplosion2 = packet2;
            s27PacketExplosion2.field_149153_g *= (float)(this.percent / 100.0);
            final S27PacketExplosion s27PacketExplosion3 = packet2;
            s27PacketExplosion3.field_149159_h *= (float)(this.percent / 100.0);
        }
    }
}
