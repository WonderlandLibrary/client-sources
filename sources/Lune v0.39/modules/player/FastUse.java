package me.superskidder.lune.modules.player;

import me.superskidder.lune.events.EventPreUpdate;
import me.superskidder.lune.modules.Mod;
import me.superskidder.lune.modules.ModCategory;
import me.superskidder.lune.events.EventPacketSend;
import me.superskidder.lune.manager.event.EventTarget;
import me.superskidder.lune.values.type.Mode;
import me.superskidder.lune.values.type.Num;
import net.minecraft.item.ItemAppleGold;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemPotion;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C07PacketPlayerDigging;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class FastUse extends Mod {
    Mode mod = new Mode<>("Mode", mods.values(), mods.Hypixel);
    Num<Number> speed = new Num<>("Speed", 10, 1, 10);

    enum mods {
        Hypixel,
        Vanilla
    }

    public FastUse() {
        super("FastUse", ModCategory.Player, "Use items faster(Hypixel only 1.12.2)");
        addValues(mod,speed);
    }


    @EventTarget
    public void onUpdate(EventPreUpdate e) {

        if (mod.getValue() == mods.Hypixel) {
            if (mc.thePlayer.getItemInUseDuration() >= speed.getValue().floatValue()
                    && (this.mc.thePlayer.getCurrentEquippedItem() != null
                    && this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemPotion
                    || this.mc.thePlayer.getCurrentEquippedItem().getItem() instanceof ItemFood)) {
                mc.thePlayer.sendQueue.getNetworkManager()
                        .sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
                mc.thePlayer.sendQueue.getNetworkManager()
                        .sendPacket(new C08PacketPlayerBlockPlacement(mc.thePlayer.itemInUse));
                for (int i = 0; i < 30; i++) {
                    mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C03PacketPlayer(mc.thePlayer.onGround));
                }
                mc.thePlayer.sendQueue.getNetworkManager().sendPacket(new C07PacketPlayerDigging(
                        C07PacketPlayerDigging.Action.RELEASE_USE_ITEM, BlockPos.ORIGIN, EnumFacing.DOWN));
                mc.thePlayer.sendQueue.getNetworkManager()
                        .sendPacket(new C09PacketHeldItemChange(mc.thePlayer.inventory.currentItem));
            }
        }
    }

    @EventTarget
    public void onPacket(EventPacketSend e) {
        if (mod.getValue() == mods.Vanilla) {
            if (this.mc.thePlayer.isUsingItem() && mc.thePlayer.getHeldItem().getItem() instanceof ItemAppleGold) {
                for (int s = 0; s < 4; s++) {
                    mc.getNetHandler().addToSendQueueNoEvent(new C03PacketPlayer(false));
                }
            }
        }
    }
}
