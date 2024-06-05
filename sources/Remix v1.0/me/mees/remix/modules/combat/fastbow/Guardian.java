package me.mees.remix.modules.combat.fastbow;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import net.minecraft.item.*;
import net.minecraft.client.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import net.minecraft.entity.player.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.server.*;

public class Guardian extends Mode<Fastbow>
{
    public double delay;
    
    public Guardian(final Fastbow parent) {
        super(parent, "Guardian");
        this.delay = 1.0;
    }
    
    @Subscriber
    public void onUpdate(final EventMotion event) {
        if (this.mc.thePlayer.onGround && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow) {
            final Minecraft mc = this.mc;
            if (Minecraft.getMinecraft().gameSettings.keyBindUseItem.pressed) {
                this.mc.thePlayer.sendQueue.addToSendQueue(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
                if (this.mc.thePlayer.ticksExisted % this.delay == 0.0) {
                    for (int i = 0; i < 20; ++i) {
                        this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY + 1.0E-9, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                        if (this.mc.thePlayer.ticksExisted % 3 == 0) {
                            this.mc.thePlayer.sendQueue.addToSendQueue(new C03PacketPlayer.C04PacketPlayerPosition(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 2.0, this.mc.thePlayer.posZ, false));
                        }
                    }
                }
                this.mc.thePlayer.sendQueue.addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                this.mc.playerController.onStoppedUsingItem(this.mc.thePlayer);
            }
        }
    }
    
    @Subscriber
    public void eventPacketRecieve(final EventPacketReceive event) {
        if (event.getPacket() instanceof S18PacketEntityTeleport) {
            final S18PacketEntityTeleport packet = (S18PacketEntityTeleport)event.getPacket();
            final boolean canFb = this.mc.thePlayer.onGround && this.mc.thePlayer.isUsingItem() && this.mc.thePlayer.inventory.getCurrentItem() != null && !this.mc.thePlayer.isEating() && !this.mc.thePlayer.isBlocking();
            if (this.mc.thePlayer != null && canFb) {
                packet.setYaw((byte)this.mc.thePlayer.rotationYaw);
            }
            packet.setPitch((byte)this.mc.thePlayer.rotationPitch);
        }
    }
}
