/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.combat;

import cc.diablo.event.impl.UpdateEvent;
import cc.diablo.helpers.PacketHelper;
import cc.diablo.helpers.player.KillAuraHelper;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemPotion;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C03PacketPlayer;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;

public class AutoPot
extends Module {
    private static boolean doThrow;
    int slot;

    public AutoPot() {
        super("Auto Pot", "Automatically use potions", 0, Category.Combat);
    }

    @Override
    public void onEnable() {
        this.slot = -1;
        super.onEnable();
    }

    @Subscribe
    public void onUpdate(UpdateEvent e) {
        if (AutoPot.mc.currentScreen instanceof GuiInventory && AutoPot.mc.thePlayer.onGround) {
            ItemPotion potion;
            ItemStack is;
            Item item;
            boolean shouldSplash;
            int potionSlot = this.getPotionFromInv();
            boolean bl = shouldSplash = AutoPot.mc.thePlayer.getHealth() <= 10.0f;
            if (potionSlot != -1 && (item = (is = AutoPot.mc.thePlayer.inventoryContainer.getSlot(potionSlot).getStack()).getItem()) instanceof ItemPotion && (potion = (ItemPotion)item).getEffects(is) != null) {
                for (PotionEffect o : potion.getEffects(is)) {
                    PotionEffect effect = o;
                    if (effect.getPotionID() != Potion.moveSpeed.id || AutoPot.mc.thePlayer.isPotionActive(Potion.moveSpeed)) continue;
                    shouldSplash = true;
                }
            }
            int prevSlot = AutoPot.mc.thePlayer.inventory.currentItem;
            if (potionSlot != -1 && (AutoPot.mc.thePlayer.ticksExisted & 0x14) == 0) {
                AutoPot.mc.playerController.windowClick(AutoPot.mc.thePlayer.openContainer.windowId, potionSlot, 1, 2, AutoPot.mc.thePlayer);
                KillAuraHelper.setRotations(e, AutoPot.mc.thePlayer.rotationYaw, 85.0f);
                PacketHelper.sendPacketNoEvent(new C03PacketPlayer.C05PacketPlayerLook(AutoPot.mc.thePlayer.rotationYaw, 85.0f, AutoPot.mc.thePlayer.onGround));
                PacketHelper.sendPacketNoEvent(new C09PacketHeldItemChange(1));
                PacketHelper.sendPacketNoEvent(new C08PacketPlayerBlockPlacement(AutoPot.mc.thePlayer.inventory.getStackInSlot(1)));
                PacketHelper.sendPacketNoEvent(new C09PacketHeldItemChange(AutoPot.mc.thePlayer.inventory.currentItem));
            }
        }
    }

    private int getPotionFromInv() {
        int pot = -1;
        for (int i = 0; i < 45; ++i) {
            ItemPotion potion;
            ItemStack is;
            Item item;
            if (!AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((item = (is = AutoPot.mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem()) instanceof ItemPotion) || (potion = (ItemPotion)item).getEffects(is) == null) continue;
            for (PotionEffect o : potion.getEffects(is)) {
                PotionEffect effect = o;
                if (effect.getPotionID() != Potion.heal.id && (effect.getPotionID() != Potion.regeneration.id || AutoPot.mc.thePlayer.isPotionActive(Potion.regeneration)) && (effect.getPotionID() != Potion.moveSpeed.id || AutoPot.mc.thePlayer.isPotionActive(Potion.moveSpeed)) || !ItemPotion.isSplash(is.getItemDamage())) continue;
                pot = i;
            }
        }
        return pot;
    }
}

