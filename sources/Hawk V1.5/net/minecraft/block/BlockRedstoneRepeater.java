package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneRepeater extends BlockRedstoneDiode {
   public static final PropertyInteger field_176410_b = PropertyInteger.create("delay", 1, 4);
   private static final String __OBFID = "CL_00000301";
   public static final PropertyBool field_176411_a = PropertyBool.create("locked");

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if (!var4.capabilities.allowEdit) {
         return false;
      } else {
         var1.setBlockState(var2, var3.cycleProperty(field_176410_b), 3);
         return true;
      }
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{AGE, field_176410_b, field_176411_a});
   }

   public boolean func_176405_b(IBlockAccess var1, BlockPos var2, IBlockState var3) {
      return this.func_176407_c(var1, var2, var3) > 0;
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((EnumFacing)var1.getValue(AGE)).getHorizontalIndex();
      var3 |= (Integer)var1.getValue(field_176410_b) - 1 << 2;
      return var3;
   }

   protected IBlockState func_180674_e(IBlockState var1) {
      Integer var2 = (Integer)var1.getValue(field_176410_b);
      Boolean var3 = (Boolean)var1.getValue(field_176411_a);
      EnumFacing var4 = (EnumFacing)var1.getValue(AGE);
      return Blocks.powered_repeater.getDefaultState().withProperty(AGE, var4).withProperty(field_176410_b, var2).withProperty(field_176411_a, var3);
   }

   protected IBlockState func_180675_k(IBlockState var1) {
      Integer var2 = (Integer)var1.getValue(field_176410_b);
      Boolean var3 = (Boolean)var1.getValue(field_176411_a);
      EnumFacing var4 = (EnumFacing)var1.getValue(AGE);
      return Blocks.unpowered_repeater.getDefaultState().withProperty(AGE, var4).withProperty(field_176410_b, var2).withProperty(field_176411_a, var3);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(AGE, EnumFacing.getHorizontal(var1)).withProperty(field_176411_a, false).withProperty(field_176410_b, 1 + (var1 >> 2));
   }

   protected int func_176403_d(IBlockState var1) {
      return (Integer)var1.getValue(field_176410_b) * 2;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.repeater;
   }

   protected boolean func_149908_a(Block var1) {
      return isRedstoneRepeaterBlockID(var1);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      this.func_176400_h(var1, var2, var3);
   }

   protected BlockRedstoneRepeater(boolean var1) {
      super(var1);
      this.setDefaultState(this.blockState.getBaseState().withProperty(AGE, EnumFacing.NORTH).withProperty(field_176410_b, 1).withProperty(field_176411_a, false));
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.isRepeaterPowered) {
         EnumFacing var5 = (EnumFacing)var3.getValue(AGE);
         double var6 = (double)((float)var2.getX() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var8 = (double)((float)var2.getY() + 0.4F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var10 = (double)((float)var2.getZ() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         float var12 = -5.0F;
         if (var4.nextBoolean()) {
            var12 = (float)((Integer)var3.getValue(field_176410_b) * 2 - 1);
         }

         var12 /= 16.0F;
         double var13 = (double)(var12 * (float)var5.getFrontOffsetX());
         double var15 = (double)(var12 * (float)var5.getFrontOffsetZ());
         var1.spawnParticle(EnumParticleTypes.REDSTONE, var6 + var13, var8, var10 + var15, 0.0D, 0.0D, 0.0D);
      }

   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.repeater;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(field_176411_a, this.func_176405_b(var2, var3, var1));
   }
}
