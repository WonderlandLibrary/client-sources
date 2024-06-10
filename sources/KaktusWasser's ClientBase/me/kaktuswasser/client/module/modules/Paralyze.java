// 
// Decompiled by KaktusWasser
// https://www.youtube.com/KaktusWasserReal
// 

package me.kaktuswasser.client.module.modules;

import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C03PacketPlayer;
import me.kaktuswasser.client.event.Event;
import me.kaktuswasser.client.event.events.PreMotion;
import me.kaktuswasser.client.module.Module;
import me.kaktuswasser.client.values.ConstrainedValue;
import me.kaktuswasser.client.values.Value;
import net.minecraft.entity.Entity;

public class Paralyze extends Module
{
    public final Value<Integer> packets;
    
    public Paralyze() {
        super("Paralyze", -16711936, Category.PLAYER);
        this.packets = (Value<Integer>)new ConstrainedValue("paralyze_Packets", "packets", 200, 4, 500, this);
    }
    
    @Override
    public void onEvent(final Event e) {
        if (e instanceof PreMotion && !Paralyze.mc.theWorld.getEntitiesWithinAABBExcludingEntity(Paralyze.mc.thePlayer, Paralyze.mc.thePlayer.boundingBox).isEmpty()) {
            for (int i = 0; i < this.packets.getValue(); ++i) {
                Paralyze.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Paralyze.mc.thePlayer.posX, Paralyze.mc.thePlayer.posY + 1.0E-9, Paralyze.mc.thePlayer.posZ, false));
                Paralyze.mc.getNetHandler().getNetworkManager().sendPacket(new C03PacketPlayer.C04PacketPlayerPosition(Paralyze.mc.thePlayer.posX, Paralyze.mc.thePlayer.posY, Paralyze.mc.thePlayer.posZ, false));
            }
        }
    }
}
