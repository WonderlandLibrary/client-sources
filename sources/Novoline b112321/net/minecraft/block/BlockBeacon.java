package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityBeacon;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.HttpUtil;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.chunk.Chunk;

public class BlockBeacon extends BlockContainer {
   public BlockBeacon() {
      super(Material.glass, MapColor.diamondColor);
      this.setHardness(3.0F);
      this.setCreativeTab(CreativeTabs.tabMisc);
   }

   public TileEntity createNewTileEntity(World var1, int var2) {
      return new TileEntityBeacon();
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var1.isRemote) {
         TileEntity var9 = var1.getTileEntity(var2);
         if(var9 instanceof TileEntityBeacon) {
            var4.displayGUIChest((TileEntityBeacon)var9);
            var4.triggerAchievement(StatList.field_181730_N);
         }
      }

      return true;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public int getRenderType() {
      return 3;
   }

   public void onBlockPlacedBy(World var1, BlockPos var2, IBlockState var3, EntityLivingBase var4, ItemStack var5) {
      super.onBlockPlacedBy(var1, var2, var3, var4, var5);
      if(var5.hasDisplayName()) {
         TileEntity var6 = var1.getTileEntity(var2);
         if(var6 instanceof TileEntityBeacon) {
            ((TileEntityBeacon)var6).setName(var5.getDisplayName());
         }
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      TileEntity var5 = var1.getTileEntity(var2);
      if(var5 instanceof TileEntityBeacon) {
         ((TileEntityBeacon)var5).updateBeacon();
         var1.addBlockEvent(var2, this, 1, 0);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public static void updateColorAsync(World var0, BlockPos var1) {
      HttpUtil.field_180193_a.submit(BlockBeacon::lambda$updateColorAsync$1);
   }

   private static void lambda$updateColorAsync$1(World var0, BlockPos var1) {
      Chunk var2 = var0.getChunkFromBlockCoords(var1);
      int var3 = var1.getY() - 1;

      while(true) {
         BlockPos var4 = new BlockPos(var1.getX(), var3, var1.getZ());
         if(!var2.canSeeSky(var4)) {
            return;
         }

         IBlockState var5 = var0.getBlockState(var4);
         if(var5.getBlock() == Blocks.beacon) {
            ((WorldServer)var0).addScheduledTask(BlockBeacon::lambda$null$0);
         }

         --var3;
      }
   }

   private static void lambda$null$0(World var0, BlockPos var1) {
      TileEntity var2 = var0.getTileEntity(var1);
      if(var2 instanceof TileEntityBeacon) {
         ((TileEntityBeacon)var2).updateBeacon();
         var0.addBlockEvent(var1, Blocks.beacon, 1, 0);
      }

   }
}
