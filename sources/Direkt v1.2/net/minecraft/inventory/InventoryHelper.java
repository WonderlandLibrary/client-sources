package net.minecraft.inventory;

import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
	private static final Random RANDOM = new Random();

	public static void dropInventoryItems(World worldIn, BlockPos pos, IInventory inventory) {
		dropInventoryItems(worldIn, pos.getX(), pos.getY(), pos.getZ(), inventory);
	}

	public static void dropInventoryItems(World worldIn, Entity entityAt, IInventory inventory) {
		dropInventoryItems(worldIn, entityAt.posX, entityAt.posY, entityAt.posZ, inventory);
	}

	private static void dropInventoryItems(World worldIn, double x, double y, double z, IInventory inventory) {
		for (int i = 0; i < inventory.getSizeInventory(); ++i) {
			ItemStack itemstack = inventory.getStackInSlot(i);

			if (itemstack != null) {
				spawnItemStack(worldIn, x, y, z, itemstack);
			}
		}
	}

	public static void spawnItemStack(World worldIn, double x, double y, double z, ItemStack stack) {
		float f = (RANDOM.nextFloat() * 0.8F) + 0.1F;
		float f1 = (RANDOM.nextFloat() * 0.8F) + 0.1F;
		float f2 = (RANDOM.nextFloat() * 0.8F) + 0.1F;

		while (stack.stackSize > 0) {
			int i = RANDOM.nextInt(21) + 10;

			if (i > stack.stackSize) {
				i = stack.stackSize;
			}

			stack.stackSize -= i;
			EntityItem entityitem = new EntityItem(worldIn, x + f, y + f1, z + f2, new ItemStack(stack.getItem(), i, stack.getMetadata()));

			if (stack.hasTagCompound()) {
				entityitem.getEntityItem().setTagCompound(stack.getTagCompound().copy());
			}

			float f3 = 0.05F;
			entityitem.motionX = RANDOM.nextGaussian() * 0.05000000074505806D;
			entityitem.motionY = (RANDOM.nextGaussian() * 0.05000000074505806D) + 0.20000000298023224D;
			entityitem.motionZ = RANDOM.nextGaussian() * 0.05000000074505806D;
			worldIn.spawnEntityInWorld(entityitem);
		}
	}
}
