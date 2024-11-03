package net.augustus.utils;

import java.util.ArrayList;
import net.augustus.Augustus;
import net.augustus.utils.interfaces.MM;
import net.minecraft.block.Block;
import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFurnace;
import net.minecraft.block.BlockLiquid;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBanner;
import net.minecraft.item.ItemFlintAndSteel;
import net.minecraft.item.ItemLeaves;
import net.minecraft.item.ItemSlab;
import net.minecraft.item.ItemSnow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class BlockUtil implements MM {
   private final ArrayList<Item> nonValidItems = new ArrayList<>();

   public BlockUtil() {
      this.nonValidItems.add(Item.getItemById(30));
      this.nonValidItems.add(Item.getItemById(58));
      this.nonValidItems.add(Item.getItemById(116));
      this.nonValidItems.add(Item.getItemById(158));
      this.nonValidItems.add(Item.getItemById(23));
      this.nonValidItems.add(Item.getItemById(6));
      this.nonValidItems.add(Item.getItemById(54));
      this.nonValidItems.add(Item.getItemById(146));
      this.nonValidItems.add(Item.getItemById(130));
      this.nonValidItems.add(Item.getItemById(26));
      this.nonValidItems.add(Item.getItemById(50));
      this.nonValidItems.add(Item.getItemById(76));
      this.nonValidItems.add(Item.getItemById(46));
      this.nonValidItems.add(Item.getItemById(37));
      this.nonValidItems.add(Item.getItemById(38));
   }

   public boolean isValidStack(ItemStack itemStack) {
      Item item = itemStack.getItem();
      if (!(item instanceof ItemSlab)
         && !(item instanceof ItemLeaves)
         && !(item instanceof ItemSnow)
         && !(item instanceof ItemBanner)
         && !(item instanceof ItemFlintAndSteel)) {
         for(Item item1 : this.nonValidItems) {
            if (item.equals(item1)) {
               return false;
            }
         }

         return true;
      } else {
         return false;
      }
   }

   public static boolean isValidBock(BlockPos blockPos) {
      Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
      return !(block instanceof BlockLiquid) && !(block instanceof BlockAir) && !(block instanceof BlockChest) && !(block instanceof BlockFurnace);
   }

   public static boolean isAirBlock(BlockPos blockPos) {
      Block block = Minecraft.getMinecraft().theWorld.getBlockState(blockPos).getBlock();
      return block instanceof BlockAir;
   }

   public static boolean isScaffoldToggled() {
      return /*Augustus.getInstance().getModuleManager().scaffoldWalk.isToggled()
         || Augustus.getInstance().getModuleManager().newScaffold.isToggled()
         || */Augustus.getInstance().getModuleManager().blockFly.isToggled();
   }

   public static boolean isPlayerNextToBlock(EntityPlayer player, Block targetBlock) {
      Minecraft mc = Minecraft.getMinecraft();
      BlockPos playerPos = new BlockPos(player.posX, player.posY, player.posZ);

      // Check nearby blocks in a 3x3x3 area around the player
      for (int xOffset = -1; xOffset <= 1; xOffset++) {
         for (int yOffset = -1; yOffset <= 1; yOffset++) {
            for (int zOffset = -1; zOffset <= 1; zOffset++) {
               BlockPos blockPos = playerPos.add(xOffset, yOffset, zOffset);
               Block block = mc.theWorld.getBlockState(blockPos).getBlock();

               if (block.getUnlocalizedName().equals(targetBlock.getUnlocalizedName())) {
                  return true;
               }
            }
         }
      }

      return false;
   }
}
