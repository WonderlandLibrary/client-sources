package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockCrops;
import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockDirt$DirtType;
import net.minecraft.block.BlockFarmland$1;
import net.minecraft.block.BlockStem;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.BlockPos$MutableBlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockFarmland extends Block {
   public static final iV P = iV.a("moisture", 0, 7);

   protected BlockFarmland() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(P, Integer.valueOf(0)));
      this.setTickRandomly(true);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.9375F, 1.0F);
      this.setLightOpacity(255);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return new AxisAlignedBB((double)var2.getX(), (double)var2.getY(), (double)var2.getZ(), (double)(var2.getX() + 1), (double)(var2.getY() + 1), (double)(var2.getZ() + 1));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = ((Integer)var3.getValue(P)).intValue();
      if(!this.hasWater(var1, var2) && !var1.canLightningStrike(var2.up())) {
         var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(var5 - 1)), 2);
      } else if(var5 < 7) {
         var1.setBlockState(var2, var3.withProperty(P, Integer.valueOf(7)), 2);
      }

   }

   public void onFallenUpon(World var1, BlockPos var2, Entity var3, float var4) {
      if(var3 instanceof EntityLivingBase) {
         if(!var1.isRemote && var1.rand.nextFloat() < var4 - 0.5F) {
            if(!(var3 instanceof EntityPlayer) && !var1.getGameRules().getBoolean("mobGriefing")) {
               return;
            }

            var1.setBlockState(var2, Blocks.dirt.getDefaultState());
         }

         super.onFallenUpon(var1, var2, var3, var4);
      }

   }

   private boolean hasCrops(World var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2.up()).getBlock();
      return var3 instanceof BlockCrops || var3 instanceof BlockStem;
   }

   private boolean hasWater(World var1, BlockPos var2) {
      for(BlockPos$MutableBlockPos var4 : BlockPos.getAllInBoxMutable(var2.a(-4, 0, -4), var2.a(4, 1, 4))) {
         if(var1.getBlockState(var4).getBlock().getMaterial() == Material.water) {
            return true;
         }
      }

      return false;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      super.onNeighborBlockChange(var1, var2, var3, var4);
      if(var1.getBlockState(var2.up()).getBlock().getMaterial().isSolid()) {
         var1.setBlockState(var2, Blocks.dirt.getDefaultState());
      }

   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      switch(BlockFarmland$1.$SwitchMap$net$minecraft$util$EnumFacing[var3.ordinal()]) {
      case 1:
         return true;
      case 2:
      case 3:
      case 4:
      case 5:
         Block var4 = var1.getBlockState(var2).getBlock();
         return !var4.isOpaqueCube() && var4 != Blocks.farmland;
      default:
         return super.shouldSideBeRendered(var1, var2, var3);
      }
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Blocks.dirt.getItemDropped(Blocks.dirt.getDefaultState().withProperty(BlockDirt.VARIANT, BlockDirt$DirtType.DIRT), var2, var3);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.dirt);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(P, Integer.valueOf(var1 & 7));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((Integer)var1.getValue(P)).intValue();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{P});
   }
}
