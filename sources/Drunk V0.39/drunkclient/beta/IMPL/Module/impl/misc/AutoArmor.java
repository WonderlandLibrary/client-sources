/*
 * Decompiled with CFR 0.152.
 */
package drunkclient.beta.IMPL.Module.impl.misc;

import drunkclient.beta.API.EventHandler;
import drunkclient.beta.API.events.world.EventPreUpdate;
import drunkclient.beta.IMPL.Module.Module;
import drunkclient.beta.IMPL.Module.Type;
import drunkclient.beta.IMPL.set.Numbers;
import drunkclient.beta.IMPL.set.Option;
import drunkclient.beta.UTILS.world.Timer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiChat;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.network.play.client.C16PacketClientStatus;

public class AutoArmor
extends Module {
    Timer timer = new Timer();
    public Numbers<Double> delay = new Numbers<Double>("Delay", "Delay", 350.0, 1.0, 1000.0, 50.0);
    public Option<Boolean> inv = new Option<Boolean>("Inventory Only", "Inventory Only", false);

    public AutoArmor() {
        super("Auto Armor", new String[0], Type.MISC, "Automatically assumes armor");
        this.addValues(this.inv, this.delay);
    }

    @EventHandler
    public void onUpdateArmor(EventPreUpdate e) {
        int delay = (int)((Double)this.delay.getValue() + 0.0);
        if (((Boolean)this.inv.getValue()).booleanValue() && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            return;
        }
        if (AutoArmor.mc.currentScreen != null && !(AutoArmor.mc.currentScreen instanceof GuiInventory)) {
            if (!(AutoArmor.mc.currentScreen instanceof GuiChat)) return;
        }
        if (!this.timer.check(delay)) return;
        this.getBestArmor();
    }

    private void getBestArmor() {
        int type = 1;
        while (type < 5) {
            block5: {
                block4: {
                    if (!Minecraft.thePlayer.inventoryContainer.getSlot(4 + type).getHasStack()) break block4;
                    ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(4 + type).getStack();
                    if (AutoArmor.isBestArmor(is, type)) break block5;
                    C16PacketClientStatus p = new C16PacketClientStatus(C16PacketClientStatus.EnumState.OPEN_INVENTORY_ACHIEVEMENT);
                    Minecraft.thePlayer.sendQueue.addToSendQueue(p);
                    this.drop(4 + type);
                }
                for (int i = 9; i < 45; ++i) {
                    if (!Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) continue;
                    ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                    if (!AutoArmor.isBestArmor(is, type) || !(AutoArmor.getProtection(is) > 0.0f)) continue;
                    this.shiftClick(i);
                    this.timer.reset();
                    if (((Double)this.delay.getValue()).longValue() <= 0L) continue;
                    return;
                }
            }
            ++type;
        }
    }

    public static boolean isBestArmor(ItemStack stack, int type) {
        float prot = AutoArmor.getProtection(stack);
        String strType = "";
        if (type == 1) {
            strType = "helmet";
        } else if (type == 2) {
            strType = "chestplate";
        } else if (type == 3) {
            strType = "leggings";
        } else if (type == 4) {
            strType = "boots";
        }
        if (!stack.getUnlocalizedName().contains(strType)) {
            return false;
        }
        int i = 5;
        while (i < 45) {
            Minecraft.getMinecraft();
            if (Minecraft.thePlayer.inventoryContainer.getSlot(i).getHasStack()) {
                Minecraft.getMinecraft();
                ItemStack is = Minecraft.thePlayer.inventoryContainer.getSlot(i).getStack();
                if (AutoArmor.getProtection(is) > prot && is.getUnlocalizedName().contains(strType)) {
                    return false;
                }
            }
            ++i;
        }
        return true;
    }

    public void shiftClick(int slot) {
        AutoArmor.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 0, 1, Minecraft.thePlayer);
    }

    public void drop(int slot) {
        AutoArmor.mc.playerController.windowClick(Minecraft.thePlayer.inventoryContainer.windowId, slot, 1, 4, Minecraft.thePlayer);
    }

    public static float getProtection(ItemStack stack) {
        float prot = 0.0f;
        if (!(stack.getItem() instanceof ItemArmor)) return prot;
        ItemArmor armor = (ItemArmor)stack.getItem();
        prot = (float)((double)prot + ((double)armor.damageReduceAmount + (double)((100 - armor.damageReduceAmount) * EnchantmentHelper.getEnchantmentLevel(Enchantment.protection.effectId, stack)) * 0.0075));
        prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.blastProtection.effectId, stack) / 100.0);
        prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.fireProtection.effectId, stack) / 100.0);
        prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.thorns.effectId, stack) / 100.0);
        prot = (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.unbreaking.effectId, stack) / 50.0);
        return (float)((double)prot + (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.baneOfArthropods.effectId, stack) / 100.0);
    }
}

