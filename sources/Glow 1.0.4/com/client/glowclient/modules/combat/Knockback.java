package com.client.glowclient.modules.combat;

import com.client.glowclient.events.*;
import net.minecraft.network.play.server.*;
import net.minecraftforge.fml.common.eventhandler.*;
import com.client.glowclient.modules.*;
import com.client.glowclient.utils.*;

public class Knockback extends ModuleContainer
{
    public static final NumberValue vertical;
    public static final NumberValue horizontal;
    
    @Override
    public String M() {
        return new StringBuilder().insert(0, String.format("H:%.0f,", Knockback.horizontal.k())).append(String.format("V:%.0f", Knockback.vertical.k())).toString();
    }
    
    @SubscribeEvent
    public void M(final EventServerPacket eventServerPacket) {
        try {
            final double k = Knockback.horizontal.k();
            final double i = Knockback.vertical.k();
            final double j = Knockback.horizontal.k();
            Label_0109: {
                if (eventServerPacket.getPacket() instanceof SPacketExplosion) {
                    if (k == 0.0 && i == 0.0 && j == 0.0) {
                        final EventServerPacket eventServerPacket2 = eventServerPacket;
                        eventServerPacket.setCanceled(true);
                        break Label_0109;
                    }
                    final SPacketExplosion sPacketExplosion = (SPacketExplosion)eventServerPacket.getPacket();
                    sPacketExplosion.motionX *= (float)k;
                    sPacketExplosion.motionY *= (float)i;
                    sPacketExplosion.motionZ *= (float)j;
                }
                final EventServerPacket eventServerPacket2 = eventServerPacket;
            }
            EventServerPacket eventServerPacket2;
            if (eventServerPacket2.getPacket() instanceof SPacketEntityVelocity && ((SPacketEntityVelocity)eventServerPacket.getPacket()).getEntityID() == Wrapper.mc.player.getEntityId()) {
                if (k == 0.0 && i == 0.0 && j == 0.0) {
                    eventServerPacket.setCanceled(true);
                    return;
                }
                final SPacketEntityVelocity sPacketEntityVelocity = (SPacketEntityVelocity)eventServerPacket.getPacket();
                sPacketEntityVelocity.motionX *= (int)k;
                sPacketEntityVelocity.motionY *= (int)i;
                sPacketEntityVelocity.motionZ *= (int)j;
            }
        }
        catch (Exception ex) {}
    }
    
    public Knockback() {
        super(Category.COMBAT, "Knockback", false, -1, "Changes knockback movement");
    }
    
    static {
        horizontal = ValueFactory.M("Knockback", "Horizontal", "Multiplier for horizontal movement", 0.0, 0.1, -5.0, 5.0);
        vertical = ValueFactory.M("Knockback", "Vertical", "Multiplier for Vertical movement", 0.0, 0.1, -5.0, 5.0);
    }
}
