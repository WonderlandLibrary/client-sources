/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.eventbus.Subscribe
 */
package cc.diablo.module.impl.misc;

import cc.diablo.event.impl.PacketEvent;
import cc.diablo.module.Category;
import cc.diablo.module.Module;
import com.google.common.eventbus.Subscribe;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;
import net.minecraft.item.ItemTool;
import net.minecraft.network.play.client.C02PacketUseEntity;
import net.minecraft.util.BlockPos;

public class AutoTool
extends Module {
    public AutoTool() {
        super("AutoTool", "Automatically select the best tool", 0, Category.Misc);
    }

    @Subscribe
    public void onPacket(PacketEvent event) {
        BlockPos pos;
        if (event.getDir() == PacketEvent.DirectionType.Outgoing && event.getPacket() instanceof C02PacketUseEntity) {
            C02PacketUseEntity packetUseEntity = (C02PacketUseEntity)event.getPacket();
            EntityLivingBase ent = (EntityLivingBase)packetUseEntity.getEntityFromWorld(Minecraft.theWorld);
            if (packetUseEntity.getAction() == C02PacketUseEntity.Action.ATTACK) {
                AutoTool.mc.thePlayer.inventory.currentItem = AutoTool.getBestSword(ent);
                AutoTool.mc.playerController.updateController();
            }
        }
        if (AutoTool.mc.gameSettings.keyBindAttack.pressed && AutoTool.mc.objectMouseOver != null && (pos = AutoTool.mc.objectMouseOver.getBlockPos()) != null) {
            AutoTool.updateTool(pos);
        }
    }

    public static void updateTool(BlockPos pos) {
        Minecraft mc = Minecraft.getMinecraft();
        Block block = Minecraft.theWorld.getBlockState(pos).getBlock();
        float strength = 1.0f;
        int bestItemIndex = -1;
        for (int i = 0; i < 9; ++i) {
            ItemStack itemStack = mc.thePlayer.inventory.mainInventory[i];
            if (itemStack == null || !(itemStack.getStrVsBlock(block) > strength)) continue;
            strength = itemStack.getStrVsBlock(block);
            bestItemIndex = i;
        }
        if (bestItemIndex != -1) {
            mc.thePlayer.inventory.currentItem = bestItemIndex;
        }
    }

    public static int getBestSword(Entity target) {
        int originalSlot = Minecraft.getMinecraft().thePlayer.inventory.currentItem;
        int weaponSlot = -1;
        float weaponDamage = 1.0f;
        for (int slot = 0; slot < 9; slot = (int)((byte)(slot + 1))) {
            float damage;
            Minecraft.getMinecraft().thePlayer.inventory.currentItem = slot;
            ItemStack itemStack = Minecraft.getMinecraft().thePlayer.getHeldItem();
            if (itemStack == null || !(itemStack.getItem() instanceof ItemSword) || !((damage = AutoTool.getItemDamage(itemStack)) > weaponDamage)) continue;
            weaponDamage = damage;
            weaponSlot = slot;
        }
        if (weaponSlot != -1) {
            return weaponSlot;
        }
        return originalSlot;
    }

    public static float getItemDamage(ItemStack itemStack) {
        if (itemStack.getItem() instanceof ItemSword) {
            double damage = 4.0 + (double)((ItemSword)itemStack.getItem()).getDamageVsEntity();
            return (float)(damage += (double)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25);
        }
        if (itemStack.getItem() instanceof ItemTool) {
            double damage = 1.0 + (double)((ItemTool)itemStack.getItem()).getToolMaterial().getDamageVsEntity();
            return (float)(damage += (double)((float)EnchantmentHelper.getEnchantmentLevel(Enchantment.sharpness.effectId, itemStack) * 1.25f));
        }
        return 1.0f;
    }
}

