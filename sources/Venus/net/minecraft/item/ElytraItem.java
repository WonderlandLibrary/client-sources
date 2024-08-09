/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.item;

import net.minecraft.block.DispenserBlock;
import net.minecraft.enchantment.IArmorVanishable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;

public class ElytraItem
extends Item
implements IArmorVanishable {
    public ElytraItem(Item.Properties properties) {
        super(properties);
        DispenserBlock.registerDispenseBehavior(this, ArmorItem.DISPENSER_BEHAVIOR);
    }

    public static boolean isUsable(ItemStack itemStack) {
        return itemStack.getDamage() < itemStack.getMaxDamage() - 1;
    }

    @Override
    public boolean getIsRepairable(ItemStack itemStack, ItemStack itemStack2) {
        return itemStack2.getItem() == Items.PHANTOM_MEMBRANE;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, PlayerEntity playerEntity, Hand hand) {
        ItemStack itemStack = playerEntity.getHeldItem(hand);
        EquipmentSlotType equipmentSlotType = MobEntity.getSlotForItemStack(itemStack);
        ItemStack itemStack2 = playerEntity.getItemStackFromSlot(equipmentSlotType);
        if (itemStack2.isEmpty()) {
            playerEntity.setItemStackToSlot(equipmentSlotType, itemStack.copy());
            itemStack.setCount(0);
            return ActionResult.func_233538_a_(itemStack, world.isRemote());
        }
        return ActionResult.resultFail(itemStack);
    }
}

