package net.minecraft.block;

import java.util.Random;
import net.iV;
import net.minecraft.block.Block;
import net.minecraft.block.BlockRedstoneDiode;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.StatCollector;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
   public static final PropertyBool LOCKED = PropertyBool.create("locked");
   public static final iV S = iV.a("delay", 1, 4);

   protected BlockRedstoneRepeater(boolean var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(S, Integer.valueOf(1)).withProperty(LOCKED, Boolean.FALSE));
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal("item.diode.name");
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(LOCKED, Boolean.valueOf(this.isLocked(var2, var3, var1)));
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var4.abilities.isAllowEdit()) {
         return false;
      } else {
         var1.setBlockState(var2, var3.cycleProperty(S), 3);
         return true;
      }
   }

   protected int getDelay(IBlockState var1) {
      return ((Integer)var1.getValue(S)).intValue() * 2;
   }

   protected IBlockState getPoweredState(IBlockState var1) {
      Integer var2 = (Integer)var1.getValue(S);
      Boolean var3 = (Boolean)var1.getValue(LOCKED);
      EnumFacing var4 = (EnumFacing)var1.getValue(FACING);
      return Blocks.powered_repeater.getDefaultState().withProperty(FACING, var4).withProperty(S, var2).withProperty(LOCKED, var3);
   }

   protected IBlockState getUnpoweredState(IBlockState var1) {
      Integer var2 = (Integer)var1.getValue(S);
      Boolean var3 = (Boolean)var1.getValue(LOCKED);
      EnumFacing var4 = (EnumFacing)var1.getValue(FACING);
      return Blocks.unpowered_repeater.getDefaultState().withProperty(FACING, var4).withProperty(S, var2).withProperty(LOCKED, var3);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.repeater;
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.repeater;
   }

   public boolean isLocked(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return this.getPowerOnSides(var1, var2, var3) > 0;
   }

   protected boolean canPowerSide(Block var1) {
      return isRedstoneRepeaterBlockID(var1);
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if(this.isRepeaterPowered) {
         EnumFacing var5 = (EnumFacing)var3.getValue(FACING);
         double var6 = (double)((float)var2.getX() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var8 = (double)((float)var2.getY() + 0.4F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var10 = (double)((float)var2.getZ() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         float var12 = -5.0F;
         if(var4.nextBoolean()) {
            var12 = (float)(((Integer)var3.getValue(S)).intValue() * 2 - 1);
         }

         var12 = var12 / 16.0F;
         double var13 = (double)(var12 * (float)var5.getFrontOffsetX());
         double var15 = (double)(var12 * (float)var5.getFrontOffsetZ());
         var1.spawnParticle(EnumParticleTypes.REDSTONE, var6 + var13, var8, var10 + var15, 0.0D, 0.0D, 0.0D, new int[0]);
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      this.notifyNeighbors(var1, var2, var3);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(var1)).withProperty(LOCKED, Boolean.FALSE).withProperty(S, Integer.valueOf(1 + (var1 >> 2)));
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      var2 = var2 | ((EnumFacing)var1.getValue(FACING)).getHorizontalIndex();
      var2 = var2 | ((Integer)var1.getValue(S)).intValue() - 1 << 2;
      return var2;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, S, LOCKED});
   }
}
