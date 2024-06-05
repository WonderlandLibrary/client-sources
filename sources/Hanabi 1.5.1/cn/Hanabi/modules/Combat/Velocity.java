package cn.Hanabi.modules.Combat;

import cn.Hanabi.modules.*;
import cn.Hanabi.events.*;
import net.minecraft.network.play.server.*;
import cn.Hanabi.*;
import com.darkmagician6.eventapi.*;

public class Velocity extends Mod
{
    
    
    public Velocity() {
        super("Velocity", Category.COMBAT);
    }
    
    @EventTarget
    private void onPacket(final EventPacket eventPacket) {
        if ((eventPacket.getPacket() instanceof S12PacketEntityVelocity || eventPacket.getPacket() instanceof S27PacketExplosion) && Hanabi.flag > 0) {
            eventPacket.setCancelled(true);
        }
    }
}
