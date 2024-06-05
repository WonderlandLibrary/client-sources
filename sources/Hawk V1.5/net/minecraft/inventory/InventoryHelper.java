package net.minecraft.inventory;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

public class InventoryHelper {
   private static final String __OBFID = "CL_00002262";
   private static final Random field_180177_a = new Random();

   public static void dropInventoryItems(World var0, BlockPos var1, IInventory var2) {
      func_180174_a(var0, (double)var1.getX(), (double)var1.getY(), (double)var1.getZ(), var2);
   }

   public static void func_180176_a(World var0, Entity var1, IInventory var2) {
      func_180174_a(var0, var1.posX, var1.posY, var1.posZ, var2);
   }

   private static void func_180174_a(World var0, double var1, double var3, double var5, IInventory var7) {
      for(int var8 = 0; var8 < var7.getSizeInventory(); ++var8) {
         ItemStack var9 = var7.getStackInSlot(var8);
         if (var9 != null) {
            func_180173_a(var0, var1, var3, var5, var9);
         }
      }

   }

   private static void func_180173_a(World var0, double var1, double var3, double var5, ItemStack var7) {
      float var8 = field_180177_a.nextFloat() * 0.8F + 0.1F;
      float var9 = field_180177_a.nextFloat() * 0.8F + 0.1F;
      float var10 = field_180177_a.nextFloat() * 0.8F + 0.1F;

      while(var7.stackSize > 0) {
         int var11 = field_180177_a.nextInt(21) + 10;
         if (var11 > var7.stackSize) {
            var11 = var7.stackSize;
         }

         var7.stackSize -= var11;
         EntityItem var12 = new EntityItem(var0, var1 + (double)var8, var3 + (double)var9, var5 + (double)var10, new ItemStack(var7.getItem(), var11, var7.getMetadata()));
         if (var7.hasTagCompound()) {
            var12.getEntityItem().setTagCompound((NBTTagCompound)var7.getTagCompound().copy());
         }

         float var13 = 0.05F;
         var12.motionX = field_180177_a.nextGaussian() * (double)var13;
         var12.motionY = field_180177_a.nextGaussian() * (double)var13 + 0.20000000298023224D;
         var12.motionZ = field_180177_a.nextGaussian() * (double)var13;
         var0.spawnEntityInWorld(var12);
      }

   }
}
