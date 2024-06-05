package me.mees.remix.modules.combat.antiknockback;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.server.*;
import pw.stamina.causam.scan.method.model.*;

public class Normal extends Mode<AntiKnockback>
{
    public Normal(final AntiKnockback parent) {
        super(parent, "Normal");
    }
    
    @Subscriber
    public void readPacket(final EventPacketReceive eventPacketReceive) {
        if (eventPacketReceive.packet instanceof S12PacketEntityVelocity) {
            final S12PacketEntityVelocity packet = (S12PacketEntityVelocity)eventPacketReceive.packet;
            if (this.mc.theWorld.getEntityByID(packet.func_149412_c()) == this.mc.thePlayer) {
                eventPacketReceive.setCancelled(true);
            }
        }
        if (eventPacketReceive.packet instanceof S27PacketExplosion) {
            eventPacketReceive.setCancelled(true);
        }
    }
}
