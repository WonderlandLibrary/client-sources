package net.minecraft.inventory;

import java.util.Random;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
    private static final Random RANDOM = new Random();

    public static void dropInventoryItems(World worldIn, BlockPos pos, IInventory inventory) {
        InventoryHelper.dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
    }

    public static void dropInventoryItems(World worldIn, Entity entityAt, IInventory inventory) {
        InventoryHelper.dropInventoryItems(worldIn, entityAt.getPosX(), entityAt.getPosY(), entityAt.getPosZ(), inventory);
    }

    private static void dropInventoryItems(World worldIn, double x, double y, double z, IInventory inventory) {
        for (int i = 0; i < inventory.getSizeInventory(); ++i) {
            InventoryHelper.spawnItemStack(worldIn, x, y, z, inventory.getStackInSlot(i));
        }
    }

    public static void dropItems(World p_219961_0_, BlockPos p_219961_1_, NonNullList<ItemStack> p_219961_2_) {
        p_219961_2_.forEach(p_219962_2_ -> InventoryHelper.spawnItemStack(p_219961_0_, p_219961_1_.getX(), p_219961_1_.getY(), p_219961_1_.getZ(), p_219962_2_));
    }

    public static int getItemIndex(Item item) {
        for (int i = 0; i < 45; ++i) {
            if (Minecraft.getInstance().player.inventory.getStackInSlot(i).getItem() != item) continue;
            return i;
        }
        return -1;
    }

    public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
        double d0 = EntityType.ITEM.getWidth();
        double d1 = 1.0 - d0;
        double d2 = d0 / 2.0;
        double d3 = Math.floor(x) + RANDOM.nextDouble() * d1 + d2;
        double d4 = Math.floor(y) + RANDOM.nextDouble() * d1;
        double d5 = Math.floor(z) + RANDOM.nextDouble() * d1 + d2;
        while (!stack.isEmpty()) {
            ItemEntity itementity = new ItemEntity(worldIn, d3, d4, d5, stack.split(RANDOM.nextInt(21) + 10));
            float f = 0.05f;
            itementity.setMotion(RANDOM.nextGaussian() * (double)0.05f, RANDOM.nextGaussian() * (double)0.05f + (double)0.2f, RANDOM.nextGaussian() * (double)0.05f);
            worldIn.addEntity(itementity);
        }
    }
}