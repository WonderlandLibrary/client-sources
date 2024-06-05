package me.mees.remix.modules.combat.fastbow;

import me.satisfactory.base.module.*;
import me.mees.remix.modules.combat.*;
import me.satisfactory.base.events.*;
import net.minecraft.item.*;
import net.minecraft.entity.player.*;
import net.minecraft.world.*;
import net.minecraft.network.*;
import net.minecraft.network.play.client.*;
import net.minecraft.util.*;
import pw.stamina.causam.scan.method.model.*;

public class Fast extends Mode<Fastbow>
{
    public Fast(final Fastbow parent) {
        super(parent, "Fast");
    }
    
    @Subscriber
    public void onUpdate(final EventPlayerUpdate tick) {
        if (this.mc.thePlayer.inventory.getCurrentItem() == null) {
            return;
        }
        if (!this.mc.thePlayer.onGround) {}
        if (this.mc.thePlayer.inventory.getCurrentItem().getItem() instanceof ItemBow && this.mc.gameSettings.keyBindUseItem.pressed) {
            this.mc.playerController.sendUseItem(this.mc.thePlayer, this.mc.theWorld, this.mc.thePlayer.inventory.getCurrentItem());
            this.mc.thePlayer.inventory.getCurrentItem().getItem().onItemRightClick(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer);
            for (int i = 0; i < 20; ++i) {
                this.mc.thePlayer.sendQueue.sendPacketNoEvent(new C03PacketPlayer(false));
            }
            this.mc.getNetHandler().addToSendQueue(new C07PacketPlayerDigging(C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, new BlockPos(0, 0, 0), EnumFacing.DOWN));
            this.mc.thePlayer.inventory.getCurrentItem().getItem().onPlayerStoppedUsing(this.mc.thePlayer.inventory.getCurrentItem(), this.mc.theWorld, this.mc.thePlayer, 100000);
        }
    }
}
