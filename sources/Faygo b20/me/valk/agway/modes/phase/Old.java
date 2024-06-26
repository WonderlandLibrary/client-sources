package me.valk.agway.modes.phase;

import me.valk.agway.modules.world.Phase;
import me.valk.event.EventListener;
import me.valk.event.EventType;
import me.valk.event.events.entity.EventEntityCollision;
import me.valk.event.events.player.EventMotion;
import me.valk.event.events.player.EventPushOut;
import me.valk.module.ModMode;
import net.minecraft.network.play.client.C03PacketPlayer;

/**
 * Created by TheeRealOG on 8/14/2016.
 */
public class Old extends ModMode<Phase> {


    public Old(Phase parent) {
        super(parent, "Old");
    }

    @EventListener
    public void onCollide(EventEntityCollision event) {
        if (this.p.isCollidedHorizontally && event.getEntity() == this.p && event.getBoundingBox() != null && event.getLocation().getY() >= this.p.getEntityBoundingBox().minY) {
            event.setBoundingBox(null);
        }
    }

    @EventListener
    public void onPushOut(EventPushOut event) {
        event.setCancelled(true);
    }

    @EventListener
    public void onMotion(EventMotion event) {
        if (event.getType() == EventType.POST && this.p.isCollidedHorizontally && this.p.onGround) {
            double multiplier = 0.3;
            double mx = Math.cos(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            double mz = Math.sin(Math.toRadians(this.mc.thePlayer.rotationYaw + 90.0f));
            double x = this.mc.thePlayer.movementInput.moveForward * multiplier * mx + this.mc.thePlayer.movementInput.moveStrafe * multiplier * mz;
            double z = this.mc.thePlayer.movementInput.moveForward * multiplier * mz - this.mc.thePlayer.movementInput.moveStrafe * multiplier * mx;
            this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX + x, this.p.posY, this.p.posZ + z, false));
            for (int i = 0; i < 10; ++i) {
                this.p.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.p.posX, Double.MAX_VALUE, this.p.posZ, false));
            }
            this.p.setPosition(this.p.posX + x, this.p.posY, this.p.posZ + z);
        }
    }
}
