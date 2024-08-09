/*
 * Decompiled with CFR 0.153-SNAPSHOT (d6f6758-dirty).
 */
package net.minecraft.inventory;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
    private static final Random RANDOM = new Random();

    public static void dropInventoryItems(World world, BlockPos blockPos, IInventory iInventory) {
        InventoryHelper.dropInventoryItems(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), iInventory);
    }

    public static void dropInventoryItems(World world, Entity entity2, IInventory iInventory) {
        InventoryHelper.dropInventoryItems(world, entity2.getPosX(), entity2.getPosY(), entity2.getPosZ(), iInventory);
    }

    private static void dropInventoryItems(World world, double d, double d2, double d3, IInventory iInventory) {
        for (int i = 0; i < iInventory.getSizeInventory(); ++i) {
            InventoryHelper.spawnItemStack(world, d, d2, d3, iInventory.getStackInSlot(i));
        }
    }

    public static void dropItems(World world, BlockPos blockPos, NonNullList<ItemStack> nonNullList) {
        nonNullList.forEach(arg_0 -> InventoryHelper.lambda$dropItems$0(world, blockPos, arg_0));
    }

    public static void spawnItemStack(World world, double d, double d2, double d3, ItemStack itemStack) {
        double d4 = EntityType.ITEM.getWidth();
        double d5 = 1.0 - d4;
        double d6 = d4 / 2.0;
        double d7 = Math.floor(d) + RANDOM.nextDouble() * d5 + d6;
        double d8 = Math.floor(d2) + RANDOM.nextDouble() * d5;
        double d9 = Math.floor(d3) + RANDOM.nextDouble() * d5 + d6;
        while (!itemStack.isEmpty()) {
            ItemEntity itemEntity = new ItemEntity(world, d7, d8, d9, itemStack.split(RANDOM.nextInt(21) + 10));
            float f = 0.05f;
            itemEntity.setMotion(RANDOM.nextGaussian() * (double)0.05f, RANDOM.nextGaussian() * (double)0.05f + (double)0.2f, RANDOM.nextGaussian() * (double)0.05f);
            world.addEntity(itemEntity);
        }
    }

    private static void lambda$dropItems$0(World world, BlockPos blockPos, ItemStack itemStack) {
        InventoryHelper.spawnItemStack(world, blockPos.getX(), blockPos.getY(), blockPos.getZ(), itemStack);
    }
}

