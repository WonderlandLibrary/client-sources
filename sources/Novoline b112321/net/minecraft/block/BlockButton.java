package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockButton$1;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockButton extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing");
   public static final PropertyBool POWERED = PropertyBool.create("powered");
   private final boolean wooden;

   protected BlockButton(boolean var1) {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(POWERED, Boolean.FALSE));
      this.setTickRandomly(true);
      this.setCreativeTab(CreativeTabs.tabRedstone);
      this.wooden = var1;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public int tickRate(World var1) {
      return this.wooden?30:20;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public boolean canPlaceBlockOnSide(World var1, BlockPos var2, EnumFacing var3) {
      return func_181088_a(var1, var2, var3.getOpposite());
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      for(EnumFacing var6 : EnumFacing.values()) {
         if(func_181088_a(var1, var2, var6)) {
            return true;
         }
      }

      return false;
   }

   protected static boolean func_181088_a(World var0, BlockPos var1, EnumFacing var2) {
      BlockPos var3 = var1.offset(var2);
      return var2 == EnumFacing.DOWN?World.doesBlockHaveSolidTopSurface(var0, var3):var0.getBlockState(var3).getBlock().isNormalCube();
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return func_181088_a(var1, var2, var3.getOpposite())?this.getDefaultState().withProperty(FACING, var3).withProperty(POWERED, Boolean.FALSE):this.getDefaultState().withProperty(FACING, EnumFacing.DOWN).withProperty(POWERED, Boolean.FALSE);
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if(this.checkForDrop(var1, var2, var3) && !func_181088_a(var1, var2, ((EnumFacing)var3.getValue(FACING)).getOpposite())) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   private boolean checkForDrop(World var1, BlockPos var2, IBlockState var3) {
      if(this.canPlaceBlockAt(var1, var2)) {
         return true;
      } else {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
         return false;
      }
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      this.updateBlockBounds(var1.getBlockState(var2));
   }

   private void updateBlockBounds(IBlockState var1) {
      EnumFacing var2 = (EnumFacing)var1.getValue(FACING);
      boolean var3 = ((Boolean)var1.getValue(POWERED)).booleanValue();
      float var4 = (float)1 / 16.0F;
      switch(BlockButton$1.$SwitchMap$net$minecraft$util$EnumFacing[var2.ordinal()]) {
      case 1:
         this.setBlockBounds(0.0F, 0.375F, 0.3125F, var4, 0.625F, 0.6875F);
         break;
      case 2:
         this.setBlockBounds(1.0F - var4, 0.375F, 0.3125F, 1.0F, 0.625F, 0.6875F);
         break;
      case 3:
         this.setBlockBounds(0.3125F, 0.375F, 0.0F, 0.6875F, 0.625F, var4);
         break;
      case 4:
         this.setBlockBounds(0.3125F, 0.375F, 1.0F - var4, 0.6875F, 0.625F, 1.0F);
         break;
      case 5:
         this.setBlockBounds(0.3125F, 0.0F, 0.375F, 0.6875F, 0.0F + var4, 0.625F);
         break;
      case 6:
         this.setBlockBounds(0.3125F, 1.0F - var4, 0.375F, 0.6875F, 1.0F, 0.625F);
      }

   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!((Boolean)var3.getValue(POWERED)).booleanValue()) {
         var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.TRUE), 3);
         var1.markBlockRangeForRenderUpdate(var2, var2);
         var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
         this.notifyNeighbors(var1, var2, (EnumFacing)var3.getValue(FACING));
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

      return true;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if(((Boolean)var3.getValue(POWERED)).booleanValue()) {
         this.notifyNeighbors(var1, var2, (EnumFacing)var3.getValue(FACING));
      }

      super.breakBlock(var1, var2, var3);
   }

   public int getWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return ((Boolean)var3.getValue(POWERED)).booleanValue()?15:0;
   }

   public int getStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !((Boolean)var3.getValue(POWERED)).booleanValue()?0:(var3.getValue(FACING) == var4?15:0);
   }

   public boolean canProvidePower() {
      return true;
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(!var1.isRemote && ((Boolean)var3.getValue(POWERED)).booleanValue()) {
         if(this.wooden) {
            this.checkForArrows(var1, var2, var3);
         } else {
            var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.FALSE));
            this.notifyNeighbors(var1, var2, (EnumFacing)var3.getValue(FACING));
            var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
            var1.markBlockRangeForRenderUpdate(var2, var2);
         }
      }

   }

   public void setBlockBoundsForItemRender() {
      float var1 = 0.1875F;
      float var2 = 0.125F;
      float var3 = 0.125F;
      this.setBlockBounds(0.3125F, 0.375F, 0.375F, 0.6875F, 0.625F, 0.625F);
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if(!var1.isRemote && this.wooden && !((Boolean)var3.getValue(POWERED)).booleanValue()) {
         this.checkForArrows(var1, var2, var3);
      }

   }

   private void checkForArrows(World var1, BlockPos var2, IBlockState var3) {
      this.updateBlockBounds(var3);
      List var4 = var1.getEntitiesWithinAABB(EntityArrow.class, new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)var2.getY() + this.maxY, (double)var2.getZ() + this.maxZ));
      boolean var5 = !var4.isEmpty();
      boolean var6 = ((Boolean)var3.getValue(POWERED)).booleanValue();
      var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.TRUE));
      this.notifyNeighbors(var1, var2, (EnumFacing)var3.getValue(FACING));
      var1.markBlockRangeForRenderUpdate(var2, var2);
      var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.6F);
      var1.setBlockState(var2, var3.withProperty(POWERED, Boolean.FALSE));
      this.notifyNeighbors(var1, var2, (EnumFacing)var3.getValue(FACING));
      var1.markBlockRangeForRenderUpdate(var2, var2);
      var1.playSoundEffect((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "random.click", 0.3F, 0.5F);
      var1.scheduleUpdate(var2, this, this.tickRate(var1));
   }

   private void notifyNeighbors(World var1, BlockPos var2, EnumFacing var3) {
      var1.notifyNeighborsOfStateChange(var2, this);
      var1.notifyNeighborsOfStateChange(var2.offset(var3.getOpposite()), this);
   }

   public IBlockState getStateFromMeta(int var1) {
      EnumFacing var2;
      switch(var1 & 7) {
      case 0:
         var2 = EnumFacing.DOWN;
         break;
      case 1:
         var2 = EnumFacing.EAST;
         break;
      case 2:
         var2 = EnumFacing.WEST;
         break;
      case 3:
         var2 = EnumFacing.SOUTH;
         break;
      case 4:
         var2 = EnumFacing.NORTH;
         break;
      case 5:
      default:
         var2 = EnumFacing.UP;
      }

      return this.getDefaultState().withProperty(FACING, var2).withProperty(POWERED, Boolean.valueOf((var1 & 8) > 0));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2;
      switch(BlockButton$1.$SwitchMap$net$minecraft$util$EnumFacing[((EnumFacing)var1.getValue(FACING)).ordinal()]) {
      case 1:
         var2 = 1;
         break;
      case 2:
         var2 = 2;
         break;
      case 3:
         var2 = 3;
         break;
      case 4:
         var2 = 4;
         break;
      case 5:
      default:
         var2 = 5;
         break;
      case 6:
         var2 = 0;
      }

      if(((Boolean)var1.getValue(POWERED)).booleanValue()) {
         var2 |= 8;
      }

      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, POWERED});
   }
}
