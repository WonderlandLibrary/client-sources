package me.mees.remix.modules.combat.fastbow;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import net.minecraft.item.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;
import me.satisfactory.base.events.*;
import net.minecraft.network.play.server.*;

public class Faintful extends Mode<Fastbow>
{
    int counter;
    
    public Faintful(final Fastbow parent) {
        super(parent, "Faintful");
        this.counter = 0;
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate eventt) {
        if (this.mc.thePlayer.onGround && this.mc.thePlayer.getCurrentEquippedItem() != null && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.pressed) {
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(this.mc.thePlayer.inventory.getCurrentItem()));
            for (int index = 0; index < 16; ++index) {
                if (!this.mc.thePlayer.isDead) {
                    this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer.C06PacketPlayerPosLook(this.mc.thePlayer.posX, this.mc.thePlayer.posY - 0.09, this.mc.thePlayer.posZ, this.mc.thePlayer.rotationYaw, this.mc.thePlayer.rotationPitch, true));
                }
            }
            this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
            this.mc.thePlayer.stopUsingItem();
        }
    }
    
    @Subscriber
    public void eventPacketRecieve(final EventPacketReceive event) {
        if (event.getPacket() instanceof S18PacketEntityTeleport) {
            final S18PacketEntityTeleport packet = (S18PacketEntityTeleport)event.getPacket();
            if (this.mc.thePlayer != null) {
                packet.setYaw((byte)this.mc.thePlayer.rotationYaw);
            }
            packet.setPitch((byte)this.mc.thePlayer.rotationPitch);
        }
    }
}
