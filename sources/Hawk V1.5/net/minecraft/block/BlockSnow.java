package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockSnow extends Block {
   private static final String __OBFID = "CL_00000309";
   public static final PropertyInteger LAYERS_PROP = PropertyInteger.create("layers", 1, 8);

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(LAYERS_PROP, (var1 & 7) + 1);
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(LAYERS_PROP) - 1;
   }

   protected BlockSnow() {
      super(Material.snow);
      this.setDefaultState(this.blockState.getBaseState().withProperty(LAYERS_PROP, 1));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabDecorations);
      this.setBlockBoundsForItemRender();
   }

   public boolean isFullCube() {
      return false;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{LAYERS_PROP});
   }

   public void harvestBlock(World var1, EntityPlayer var2, BlockPos var3, IBlockState var4, TileEntity var5) {
      spawnAsEntity(var1, var3, new ItemStack(Items.snowball, (Integer)var4.getValue(LAYERS_PROP) + 1, 0));
      var1.setBlockToAir(var3);
      var2.triggerAchievement(StatList.mineBlockStatArray[Block.getIdFromBlock(this)]);
   }

   private boolean checkAndDropBlock(World var1, BlockPos var2, IBlockState var3) {
      if (!this.canPlaceBlockAt(var1, var2)) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      } else {
         return true;
      }
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return (Integer)var1.getBlockState(var2).getValue(LAYERS_PROP) < 5;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.snowball;
   }

   public void setBlockBoundsForItemRender() {
      this.getBoundsForLayers(0);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2.offsetDown());
      Block var4 = var3.getBlock();
      return var4 != Blocks.ice && var4 != Blocks.packed_ice ? (var4.getMaterial() == Material.leaves ? true : (var4 == this && (Integer)var3.getValue(LAYERS_PROP) == 7 ? true : var4.isOpaqueCube() && var4.blockMaterial.blocksMovement())) : false;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.checkAndDropBlock(var1, var2, var3);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.getLightFor(EnumSkyBlock.BLOCK, var2) > 11) {
         this.dropBlockAsItem(var1, var2, var1.getBlockState(var2), 0);
         var1.setBlockToAir(var2);
      }

   }

   public boolean isReplaceable(World var1, BlockPos var2) {
      return (Integer)var1.getBlockState(var2).getValue(LAYERS_PROP) == 1;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      int var4 = (Integer)var3.getValue(LAYERS_PROP) - 1;
      float var5 = 0.125F;
      return new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)((float)var2.getY() + (float)var4 * var5), (double)var2.getZ() + this.maxZ);
   }

   protected void getBoundsForLayers(int var1) {
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, (float)var1 / 8.0F, 1.0F);
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.UP ? true : super.shouldSideBeRendered(var1, var2, var3);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      this.getBoundsForLayers((Integer)var3.getValue(LAYERS_PROP));
   }
}
