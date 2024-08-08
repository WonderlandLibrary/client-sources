// 
// Decompiled by Procyon v0.5.36
// 

package me.perry.mcdonalds.features.modules.player;

import net.minecraft.item.Item;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import net.minecraft.util.ResourceLocation;
import net.minecraft.potion.PotionUtils;
import me.perry.mcdonalds.util.InventoryUtil;
import net.minecraft.init.Items;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.item.ItemBow;
import me.perry.mcdonalds.features.setting.Setting;
import me.perry.mcdonalds.features.modules.Module;

public class Quiver extends Module
{
    private final Setting<Integer> tickDelay;
    
    public Quiver() {
        super("Quiver", "Rotates and shoots yourself with good potion effects", Category.PLAYER, true, false, false);
        this.tickDelay = (Setting<Integer>)this.register(new Setting("Hold time", (T)3, (T)0, (T)8));
    }
    
    @Override
    public void onUpdate() {
        if (Quiver.mc.player != null) {
            if (Quiver.mc.player.inventory.getCurrentItem().getItem() instanceof ItemBow && Quiver.mc.player.isHandActive() && Quiver.mc.player.getItemInUseMaxCount() >= this.tickDelay.getValue()) {
                Quiver.mc.player.connection.sendPacket((Packet)new CPacketPlayer.Rotation(Quiver.mc.player.cameraYaw, -90.0f, Quiver.mc.player.onGround));
                Quiver.mc.playerController.onStoppedUsingItem((EntityPlayer)Quiver.mc.player);
            }
            final List<Integer> arrowSlots = InventoryUtil.getItemInventory(Items.TIPPED_ARROW);
            if (arrowSlots.get(0) == -1) {
                return;
            }
            int speedSlot = -1;
            int strengthSlot = -1;
            for (final Integer slot : arrowSlots) {
                if (PotionUtils.getPotionFromItem(Quiver.mc.player.inventory.getStackInSlot((int)slot)).getRegistryName().getPath().contains("swiftness")) {
                    speedSlot = slot;
                }
                else {
                    if (!Objects.requireNonNull(PotionUtils.getPotionFromItem(Quiver.mc.player.inventory.getStackInSlot((int)slot)).getRegistryName()).getPath().contains("strength")) {
                        continue;
                    }
                    strengthSlot = slot;
                }
            }
        }
    }
    
    @Override
    public void onEnable() {
    }
    
    private int findBow() {
        return InventoryUtil.getItemHotbar((Item)Items.BOW);
    }
}
