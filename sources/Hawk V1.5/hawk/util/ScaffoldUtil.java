package hawk.util;

import java.util.Arrays;
import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;

public class ScaffoldUtil {
   private static List<Block> blockBlacklist;
   private static Minecraft mc = Minecraft.getMinecraft();

   public static boolean contains(Block var0) {
      return blockBlacklist.contains(var0);
   }

   public static boolean invCheck() {
      for(int var0 = 36; var0 < 45; ++var0) {
         if (mc.thePlayer.inventoryContainer.getSlot(var0).getHasStack() && isValid(mc.thePlayer.inventoryContainer.getSlot(var0).getStack())) {
            return false;
         }
      }

      return true;
   }

   public static Vec3d getVec3d(BlockPos var0, EnumFacing var1) {
      double var2 = (double)var0.getX() + 0.5026836562D;
      double var4 = (double)var0.getY() + 0.5026836562D;
      double var6 = (double)var0.getZ() + 0.5026836562D;
      var2 += (double)var1.getFrontOffsetX() / 2.0D;
      var6 += (double)var1.getFrontOffsetZ() / 2.0D;
      var4 += (double)var1.getFrontOffsetY() / 2.0D;
      if (var1 != EnumFacing.UP && var1 != EnumFacing.DOWN) {
         var4 += randomNumber(0.30526836562D, -0.30526836562D);
      } else {
         var2 += randomNumber(0.30526836562D, -0.30526836562D);
         var6 += randomNumber(0.30526836562D, -0.30526836562D);
      }

      if (var1 == EnumFacing.WEST || var1 == EnumFacing.EAST) {
         var6 += randomNumber(0.30526836562D, -0.30526836562D);
      }

      if (var1 == EnumFacing.SOUTH || var1 == EnumFacing.NORTH) {
         var2 += randomNumber(0.30526836562D, -0.30526836562D);
      }

      return new Vec3d(var2, var4, var6);
   }

   public static BlockInfo getBlockData(BlockPos var0) {
      if (!blockBlacklist.contains(mc.theWorld.getBlockState(var0.add(0, -1, 0)).getBlock())) {
         return new BlockInfo(var0.add(0, -1, 0), EnumFacing.UP);
      } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var0.add(-1, 0, 0)).getBlock())) {
         return new BlockInfo(var0.add(-1, 0, 0), EnumFacing.EAST);
      } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var0.add(1, 0, 0)).getBlock())) {
         return new BlockInfo(var0.add(1, 0, 0), EnumFacing.WEST);
      } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var0.add(0, 0, -1)).getBlock())) {
         return new BlockInfo(var0.add(0, 0, -1), EnumFacing.SOUTH);
      } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var0.add(0, 0, 1)).getBlock())) {
         return new BlockInfo(var0.add(0, 0, 1), EnumFacing.NORTH);
      } else {
         BlockPos var1 = var0.add(-1, 0, 0);
         if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(-1, 0, 0)).getBlock())) {
            return new BlockInfo(var1.add(-1, 0, 0), EnumFacing.EAST);
         } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(1, 0, 0)).getBlock())) {
            return new BlockInfo(var1.add(1, 0, 0), EnumFacing.WEST);
         } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(0, 0, -1)).getBlock())) {
            return new BlockInfo(var1.add(0, 0, -1), EnumFacing.SOUTH);
         } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var1.add(0, 0, 1)).getBlock())) {
            return new BlockInfo(var1.add(0, 0, 1), EnumFacing.NORTH);
         } else {
            BlockPos var2 = var0.add(1, 0, 0);
            if (!blockBlacklist.contains(mc.theWorld.getBlockState(var2.add(-1, 0, 0)).getBlock())) {
               return new BlockInfo(var2.add(-1, 0, 0), EnumFacing.EAST);
            } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var2.add(1, 0, 0)).getBlock())) {
               return new BlockInfo(var2.add(1, 0, 0), EnumFacing.WEST);
            } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var2.add(0, 0, -1)).getBlock())) {
               return new BlockInfo(var2.add(0, 0, -1), EnumFacing.SOUTH);
            } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var2.add(0, 0, 1)).getBlock())) {
               return new BlockInfo(var2.add(0, 0, 1), EnumFacing.NORTH);
            } else {
               BlockPos var3 = var0.add(0, 0, -1);
               if (!blockBlacklist.contains(mc.theWorld.getBlockState(var3.add(-1, 0, 0)).getBlock())) {
                  return new BlockInfo(var3.add(-1, 0, 0), EnumFacing.EAST);
               } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var3.add(1, 0, 0)).getBlock())) {
                  return new BlockInfo(var3.add(1, 0, 0), EnumFacing.WEST);
               } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var3.add(0, 0, -1)).getBlock())) {
                  return new BlockInfo(var3.add(0, 0, -1), EnumFacing.SOUTH);
               } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var3.add(0, 0, 1)).getBlock())) {
                  return new BlockInfo(var3.add(0, 0, 1), EnumFacing.NORTH);
               } else {
                  BlockPos var4 = var0.add(0, 0, 1);
                  if (!blockBlacklist.contains(mc.theWorld.getBlockState(var4.add(-1, 0, 0)).getBlock())) {
                     return new BlockInfo(var4.add(-1, 0, 0), EnumFacing.EAST);
                  } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var4.add(1, 0, 0)).getBlock())) {
                     return new BlockInfo(var4.add(1, 0, 0), EnumFacing.WEST);
                  } else if (!blockBlacklist.contains(mc.theWorld.getBlockState(var4.add(0, 0, -1)).getBlock())) {
                     return new BlockInfo(var4.add(0, 0, -1), EnumFacing.SOUTH);
                  } else {
                     return !blockBlacklist.contains(mc.theWorld.getBlockState(var4.add(0, 0, 1)).getBlock()) ? new BlockInfo(var4.add(0, 0, 1), EnumFacing.NORTH) : null;
                  }
               }
            }
         }
      }
   }

   public static boolean isValid(ItemStack var0) {
      if (isEmpty(var0)) {
         return false;
      } else if (var0.getUnlocalizedName().equalsIgnoreCase("tile.chest")) {
         return false;
      } else if (!(var0.getItem() instanceof ItemBlock)) {
         return false;
      } else {
         return !blockBlacklist.contains(((ItemBlock)var0.getItem()).getBlock());
      }
   }

   public static double randomNumber(double var0, double var2) {
      return Math.random() * (var0 - var2) + var2;
   }

   public static void swap(int var0, int var1) {
      mc.playerController.windowClick(mc.thePlayer.inventoryContainer.windowId, var0, var1, 2, mc.thePlayer);
   }

   public static int getBlockSlot() {
      for(int var0 = 36; var0 < 45; ++var0) {
         ItemStack var1 = mc.thePlayer.inventoryContainer.getSlot(var0).getStack();
         if (var1 != null && var1.getItem() instanceof ItemBlock && !contains(((ItemBlock)var1.getItem()).getBlock())) {
            return var0 - 36;
         }
      }

      return -1;
   }

   static {
      blockBlacklist = Arrays.asList(Blocks.air, Blocks.water, Blocks.tnt, Blocks.chest, Blocks.flowing_water, Blocks.lava, Blocks.flowing_lava, Blocks.enchanting_table, Blocks.carpet, Blocks.chest, Blocks.torch, Blocks.anvil, Blocks.trapped_chest, Blocks.noteblock, Blocks.jukebox, Blocks.iron_ore, Blocks.lapis_ore, Blocks.wooden_pressure_plate, Blocks.stone_pressure_plate, Blocks.light_weighted_pressure_plate, Blocks.heavy_weighted_pressure_plate, Blocks.stone_button, Blocks.wooden_button, Blocks.lever, Blocks.enchanting_table);
   }

   public static boolean isEmpty(ItemStack var0) {
      return var0 == null;
   }
}
