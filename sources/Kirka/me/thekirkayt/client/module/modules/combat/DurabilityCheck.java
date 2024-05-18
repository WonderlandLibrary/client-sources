/*
 * Decompiled with CFR 0.143.
 */
package me.thekirkayt.client.module.modules.combat;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import me.thekirkayt.client.module.Module;
import me.thekirkayt.utils.ClientUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

@Module.Mod(displayName="DurabilityCheck")
public class DurabilityCheck
extends Module {
    private List<DurabilityItem> durabilityItems = new ArrayList<DurabilityItem>();

    @Override
    public void enable() {
        if (ClientUtils.player() != null) {
            this.loadDurabilityItems();
            int count = 0;
            double prevDamage = 0.0;
            int slot = -1;
            boolean crit = false;
            int clicks = 0;
            int packets = 0;
            for (DurabilityItem item : this.durabilityItems) {
                double armorDamage = item.damage - prevDamage;
                if ((armorDamage /= 4.0) < 1.0) {
                    armorDamage = 1.0;
                }
                count += (int)armorDamage;
                ClientUtils.sendMessage("xd: " + item.damage + ", " + armorDamage);
                if (item.slot != -1) {
                    ++clicks;
                }
                if (item.crit) {
                    packets += 4;
                }
                prevDamage = item.damage;
            }
            ClientUtils.sendMessage("Clicks: " + clicks + ", Packets: " + packets + ", Dura/hit: " + count);
        }
    }

    private void loadDurabilityItems() {
        this.durabilityItems.clear();
        boolean pickedLowDamageItem = false;
        for (int i = 0; i < 35; ++i) {
            if (!(this.getDamageFromSlot(i, false) >= 5.0) && pickedLowDamageItem) continue;
            if (this.getDamageFromSlot(i, false) < 5.0) {
                pickedLowDamageItem = true;
                continue;
            }
            this.durabilityItems.add(new DurabilityItem(this.getDamageFromSlot(i, false), i, false));
            this.durabilityItems.add(new DurabilityItem(this.getDamageFromSlot(i, true), i, true));
        }
        this.durabilityItems.sort(new Comparator<DurabilityItem>(){

            @Override
            public int compare(DurabilityItem item1, DurabilityItem item2) {
                if (item1.damage < item2.damage) {
                    return -1;
                }
                if (item1.damage > item2.damage) {
                    return 1;
                }
                return 1;
            }
        });
    }

    private double getDamageFromSlot(int slot, boolean crit) {
        double damage = 1.0;
        Minecraft.getMinecraft();
        ItemStack weapon = Minecraft.thePlayer.inventory.mainInventory[slot];
        if (weapon == null || weapon == null) {
            return damage;
        }
        if (weapon.getItem() instanceof ItemSword) {
            ItemSword sword = (ItemSword)weapon.getItem();
            int sharpLevel = EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, weapon);
            double baseDamage = sword.repairMaterial.getDamageVsEntity() >= 0.0f ? 5.0 + (double)sword.repairMaterial.getDamageVsEntity() : (double)sword.repairMaterial.getDamageVsEntity();
            damage = baseDamage;
            damage += (double)sharpLevel * 1.25;
        }
        if (crit) {
            damage *= 1.5;
        }
        return damage;
    }

    private class DurabilityItem {
        public double damage;
        public int slot;
        public boolean crit;

        public DurabilityItem(double damage, int slot, boolean crit) {
            this.damage = damage;
            this.slot = slot;
            this.crit = crit;
        }
    }

}

