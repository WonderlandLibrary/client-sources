package gq.vapu.czfclient.Module.Modules.Combat;



import java.awt.Color;

import gq.vapu.czfclient.API.EventHandler;
import gq.vapu.czfclient.API.EventUpdate;
import gq.vapu.czfclient.Module.Module;
import gq.vapu.czfclient.Module.ModuleType;
import gq.vapu.czfclient.Util.TimerUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

public class AutoSword extends Module {
    private ItemStack bestSword;
    private ItemStack prevBestSword;
    private boolean shouldSwitch = false;
    public TimerUtil timer = new TimerUtil();

    public AutoSword() {
        super("AutoSword", new String[]{"aw", "autosword"}, ModuleType.Combat);
    }

    @EventHandler
    private void onUpdate(EventUpdate event) {
        if (!this.timer.hasReached(100L) || this.mc.currentScreen != null && !(this.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        int best = -1;
        float swordDamage = 0.0f;
        for (int i = 9; i < 45; ++i) {
            ItemStack is;
            float swordD;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !((is = mc.thePlayer.inventoryContainer.getSlot(i).getStack()).getItem() instanceof ItemSword) || (swordD = this.getSharpnessLevel(is)) <= swordDamage) continue;
            swordDamage = swordD;
            best = i;
        }
        ItemStack current = mc.thePlayer.inventoryContainer.getSlot(36).getStack();
        if (!(best == -1 || current != null && current.getItem() instanceof ItemSword && swordDamage <= this.getSharpnessLevel(current))) {
            Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, best, 0, 2,mc.thePlayer);
            this.timer.reset();
        }
    }

    public boolean isBestWeapon(ItemStack stack) {
        float damage = this.getDamage(stack);
        for (int i = 9; i < 36; ++i) {
            ItemStack is;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || this.getDamage(is = mc.thePlayer.inventoryContainer.getSlot(i).getStack()) <= damage || !(is.getItem() instanceof ItemSword)) continue;
            return false;
        }
        if (stack.getItem() instanceof ItemSword) {
            return true;
        }
        return false;
    }

    private float getDamage(ItemStack stack) {
        float damage = 0.0f;
        Item item = stack.getItem();
        if (item instanceof ItemSword) {
            ItemSword sword = (ItemSword)item;
            damage += sword.getDamageVsEntity();
        }
        return damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f + (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
    }

    public void getBestWeapon(int slot) {
        for (int i = 9; i < 36; ++i) {
            ItemStack is;
            if (!mc.thePlayer.inventoryContainer.getSlot(i).getHasStack() || !this.isBestWeapon(is = mc.thePlayer.inventoryContainer.getSlot(i).getStack()) || this.getDamage(is) <= 0.0f || !(is.getItem() instanceof ItemSword)) continue;
            this.swap(i, slot - 36);
            break;
        }
    }

    protected void swap(int slot, int hotbarNum) {
        Minecraft.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, slot, hotbarNum, 2, mc.thePlayer);
    }

    private float getSharpnessLevel(ItemStack stack) {
        float damage = ((ItemSword)stack.getItem()).getDamageVsEntity();
        damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, stack) * 1.25f;
        return damage += (float)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireAspect.effectId, stack) * 0.01f;
    }
}

