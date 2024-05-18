// 
// Decompiled by Procyon v0.5.30
// 

package com.klintos.twelve.mod;

import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C08PacketPlayerBlockPlacement;
import net.minecraft.network.Packet;
import net.minecraft.network.play.client.C09PacketHeldItemChange;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemArmor;
import com.darkmagician6.eventapi.EventTarget;
import com.klintos.twelve.mod.events.EventPreUpdate;
import com.klintos.twelve.utils.TimerUtil;

public class AutoArmor extends Mod
{
    private TimerUtil timer;
    
    public AutoArmor() {
        super("AutoArmor", 0, ModCategory.COMBAT);
        this.timer = new TimerUtil();
    }
    
    @EventTarget
    public void onPreUpdate(final EventPreUpdate event) {
        final boolean needHelmet = AutoArmor.mc.thePlayer.inventory.armorInventory[3] == null;
        final boolean needChest = AutoArmor.mc.thePlayer.inventory.armorInventory[2] == null;
        final boolean needLeggings = AutoArmor.mc.thePlayer.inventory.armorInventory[1] == null;
        final boolean needBoots = AutoArmor.mc.thePlayer.inventory.armorInventory[0] == null;
        if (AutoArmor.mc.inGameHasFocus) {
            if (needHelmet && this.timer.delay(10.0)) {
                this.addBestHelmet();
            }
            if (needChest && this.timer.delay(10.0)) {
                this.addBestChest();
            }
            if (needLeggings && this.timer.delay(10.0)) {
                this.addBestLeggings();
            }
            if (needBoots && this.timer.delay(10.0)) {
                this.addBestBoots();
            }
        }
    }
    
    public void addBestHelmet() {
        int bestDamage = -1;
        int bestSlot = -1;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem().getUnlocalizedName().startsWith("item.helmet")) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    if (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack) > bestDamage) {
                        bestDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
                        bestSlot = i;
                        bestStack = stack;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            if (bestSlot >= 9) {
                Minecraft.getMinecraft().playerController.windowClick(0, bestSlot, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
            }
            else {
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(bestSlot - 36));
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(bestStack));
            }
        }
    }
    
    public void addBestChest() {
        int bestDamage = -1;
        int bestSlot = -1;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem().getUnlocalizedName().startsWith("item.chestplate")) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    if (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack) > bestDamage) {
                        bestDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
                        bestSlot = i;
                        bestStack = stack;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            if (bestSlot >= 9) {
                Minecraft.getMinecraft().playerController.windowClick(0, bestSlot, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
            }
            else {
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(bestSlot - 36));
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(bestStack));
            }
        }
    }
    
    public void addBestLeggings() {
        int bestDamage = -1;
        int bestSlot = -1;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem().getUnlocalizedName().startsWith("item.leggings")) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    if (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack) > bestDamage) {
                        bestDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
                        bestSlot = i;
                        bestStack = stack;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            if (bestSlot >= 9) {
                Minecraft.getMinecraft().playerController.windowClick(0, bestSlot, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
            }
            else {
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(bestSlot - 36));
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(bestStack));
            }
        }
    }
    
    public void addBestBoots() {
        int bestDamage = -1;
        int bestSlot = -1;
        ItemStack bestStack = null;
        for (int i = 9; i < 45; ++i) {
            if (AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                final ItemStack stack = AutoArmor.mc.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (stack.getItem().getUnlocalizedName().startsWith("item.boots")) {
                    final ItemArmor armor = (ItemArmor)stack.getItem();
                    if (armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack) > bestDamage) {
                        bestDamage = armor.damageReduceAmount + EnchantmentHelper.getEnchantmentLevel(Enchantment.field_180310_c.effectId, stack);
                        bestSlot = i;
                        bestStack = stack;
                    }
                }
            }
        }
        if (bestSlot != -1) {
            if (bestSlot >= 9) {
                Minecraft.getMinecraft().playerController.windowClick(0, bestSlot, 0, 1, (EntityPlayer)Minecraft.getMinecraft().thePlayer);
            }
            else {
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C09PacketHeldItemChange(bestSlot - 36));
                AutoArmor.mc.thePlayer.sendQueue.addToSendQueue((Packet)new C08PacketPlayerBlockPlacement(bestStack));
            }
        }
    }
}
