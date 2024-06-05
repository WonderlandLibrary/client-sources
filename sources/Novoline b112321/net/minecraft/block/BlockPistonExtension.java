package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.BlockPistonExtension$1;
import net.minecraft.block.BlockPistonExtension$EnumPistonType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPistonExtension extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyEnum TYPE = PropertyEnum.create("type", BlockPistonExtension$EnumPistonType.class);
   public static final PropertyBool SHORT = PropertyBool.create("short");

   public BlockPistonExtension() {
      super(Material.piston);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(TYPE, BlockPistonExtension$EnumPistonType.DEFAULT).withProperty(SHORT, Boolean.FALSE));
      this.setStepSound(soundTypePiston);
      this.setHardness(0.5F);
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if(var4.abilities.isCreative()) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         BlockPos var6 = var2.offset(var5.getOpposite());
         Block var7 = var1.getBlockState(var6).getBlock();
         if(var7 == Blocks.piston || var7 == Blocks.sticky_piston) {
            var1.setBlockToAir(var6);
         }
      }

      super.onBlockHarvested(var1, var2, var3, var4);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      EnumFacing var4 = ((EnumFacing)var3.getValue(FACING)).getOpposite();
      var2 = var2.offset(var4);
      IBlockState var5 = var1.getBlockState(var2);
      if((var5.getBlock() == Blocks.piston || var5.getBlock() == Blocks.sticky_piston) && ((Boolean)var5.getValue(BlockPistonBase.EXTENDED)).booleanValue()) {
         var5.getBlock().dropBlockAsItem(var1, var2, var5, 0);
         var1.setBlockToAir(var2);
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return false;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return false;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.applyHeadBounds(var3);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.applyCoreBounds(var3);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   private void applyCoreBounds(IBlockState var1) {
      float var2 = 0.25F;
      float var3 = 0.375F;
      float var4 = 0.625F;
      float var5 = 0.25F;
      float var6 = 0.75F;
      switch(BlockPistonExtension$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)var1.getValue(FACING)).ordinal()]) {
      case 1:
         this.setBlockBounds(0.375F, 0.25F, 0.375F, 0.625F, 1.0F, 0.625F);
         break;
      case 2:
         this.setBlockBounds(0.375F, 0.0F, 0.375F, 0.625F, 0.75F, 0.625F);
         break;
      case 3:
         this.setBlockBounds(0.25F, 0.375F, 0.25F, 0.75F, 0.625F, 1.0F);
         break;
      case 4:
         this.setBlockBounds(0.25F, 0.375F, 0.0F, 0.75F, 0.625F, 0.75F);
         break;
      case 5:
         this.setBlockBounds(0.375F, 0.25F, 0.25F, 0.625F, 0.75F, 1.0F);
         break;
      case 6:
         this.setBlockBounds(0.0F, 0.375F, 0.25F, 0.75F, 0.625F, 0.75F);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.applyHeadBounds(var1.getBlockState(var2));
   }

   public void applyHeadBounds(IBlockState var1) {
      float var2 = 0.25F;
      EnumFacing var3 = (EnumFacing)var1.getValue(FACING);
      switch(BlockPistonExtension$1.$SwitchMap$net$minecraft$util$EnumFacing[var3.ordinal()]) {
      case 1:
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);
         break;
      case 2:
         this.setBlockBounds(0.0F, 0.75F, 0.0F, 1.0F, 1.0F, 1.0F);
         break;
      case 3:
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 0.25F);
         break;
      case 4:
         this.setBlockBounds(0.0F, 0.0F, 0.75F, 1.0F, 1.0F, 1.0F);
         break;
      case 5:
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 0.25F, 1.0F, 1.0F);
         break;
      case 6:
         this.setBlockBounds(0.75F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
      BlockPos var6 = var2.offset(var5.getOpposite());
      IBlockState var7 = var1.getBlockState(var6);
      if(var7.getBlock() != Blocks.piston && var7.getBlock() != Blocks.sticky_piston) {
         var1.setBlockToAir(var2);
      } else {
         var7.getBlock().onNeighborBlockChange(var1, var6, var7, var4);
      }

   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return true;
   }

   public static EnumFacing getFacing(int var0) {
      int var1 = var0 & 7;
      return var1 > 5?null:EnumFacing.getFront(var1);
   }

   public Item getItem(World var1, BlockPos var2) {
      return var1.getBlockState(var2).getValue(TYPE) == BlockPistonExtension$EnumPistonType.STICKY?Item.getItemFromBlock(Blocks.sticky_piston):Item.getItemFromBlock(Blocks.piston);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, getFacing(var1)).withProperty(TYPE, (var1 & 8) > 0?BlockPistonExtension$EnumPistonType.STICKY:BlockPistonExtension$EnumPistonType.DEFAULT);
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getIndex();
      if(var1.getValue(TYPE) == BlockPistonExtension$EnumPistonType.STICKY) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, TYPE, SHORT});
   }
}
