package me.mees.remix.modules.player.regen;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;
import pw.stamina.causam.scan.method.model.*;

public class Guardian extends Mode<Regen>
{
    public Guardian(final Regen parent) {
        super(parent, "Guardian");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (this.mc.thePlayer.getHealth() < 10.0f && !this.mc.thePlayer.isDead && this.mc.thePlayer.getFoodStats().getFoodLevel() > 17 && this.mc.thePlayer.onGround) {
            this.mc.thePlayer.onGround = false;
            for (int i = 0; i < ((Regen)this.parent).findSettingByName("RegenPackets").doubleValue(); ++i) {
                this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 1.0E-22, this.mc.thePlayer.posZ, this.mc.thePlayer.onGround));
            }
        }
    }
}
