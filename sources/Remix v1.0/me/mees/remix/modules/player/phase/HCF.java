package me.mees.remix.modules.player.phase;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.player.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.client.*;
import net.minecraft.network.*;

public class HCF extends Mode<Phase>
{
    int counter;
    
    public HCF(final Phase parent) {
        super(parent, "HCF");
        this.counter = 0;
    }
    
    @Subscriber
    public void eventBBSet(final EventBBSet event) {
        if (event.getBoundingBox() == null || this.mc.thePlayer == null) {
            return;
        }
        if (event.getBoundingBox().maxY > this.mc.thePlayer.boundingBox.minY && this.mc.thePlayer.isSneaking()) {
            event.setBoundingBox(null);
        }
    }
    
    @Subscriber
    public void eventMove(final EventMove tick) {
        if (((Phase)this.parent).isInsideBlock() && this.mc.thePlayer.isSneaking()) {
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY, this.mc.thePlayer.posZ, false));
            final float yaw = this.mc.thePlayer.rotationYaw;
            this.mc.thePlayer.boundingBox.offsetAndUpdate(0.7 * Math.cos(Math.toRadians(yaw + 450.0f)), 0.0, 0.7 * Math.sin(Math.toRadians(yaw + 450.0f)));
        }
        if (((Phase)this.parent).isInsideBlock()) {
            return;
        }
        final double multiplier = 0.2;
        final double mx = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
        final double mz = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
    }
}
