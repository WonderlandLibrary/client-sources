package me.mees.remix.modules.player.regen;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import pw.stamina.causam.scan.method.model.*;

public class Packet extends Mode<Regen>
{
    int counter;
    
    public Packet(final Regen parent) {
        super(parent, "Packet");
        this.counter = 0;
    }
    
    @Override
    public void onEnable() {
        super.onEnable();
    }
    
    @Override
    public void onDisable() {
        super.onDisable();
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (!this.mc.thePlayer.capabilities.isCreativeMode && this.mc.thePlayer.getFoodStats().getFoodLevel() > 17 && this.mc.thePlayer.getHealth() < this.mc.thePlayer.getMaxHealth() && this.mc.thePlayer.getHealth() != 0.0f) {
            for (int i = 0; i < ((Regen)this.parent).findSettingByName("RegenPackets").doubleValue(); ++i) {
                this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer());
            }
        }
    }
}
