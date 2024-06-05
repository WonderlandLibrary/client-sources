package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneTorch extends BlockTorch {
   private final boolean field_150113_a;
   private static Map field_150112_b = Maps.newHashMap();
   private static final String __OBFID = "CL_00000298";

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return this.field_150113_a && var3.getValue(FACING_PROP) != var4 ? 15 : 0;
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return var4 == EnumFacing.DOWN ? this.isProvidingWeakPower(var1, var2, var3, var4) : 0;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (this.field_150113_a) {
         EnumFacing[] var4 = EnumFacing.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EnumFacing var7 = var4[var6];
            var1.notifyNeighborsOfStateChange(var2.offset(var7), this);
         }
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (this.field_150113_a) {
         double var5 = (double)((float)var2.getX() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var7 = (double)((float)var2.getY() + 0.7F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         double var9 = (double)((float)var2.getZ() + 0.5F) + (double)(var4.nextFloat() - 0.5F) * 0.2D;
         EnumFacing var11 = (EnumFacing)var3.getValue(FACING_PROP);
         if (var11.getAxis().isHorizontal()) {
            EnumFacing var12 = var11.getOpposite();
            double var13 = 0.27000001072883606D;
            var5 += 0.27000001072883606D * (double)var12.getFrontOffsetX();
            var7 += 0.2199999988079071D;
            var9 += 0.27000001072883606D * (double)var12.getFrontOffsetZ();
         }

         var1.spawnParticle(EnumParticleTypes.REDSTONE, var5, var7, var9, 0.0D, 0.0D, 0.0D);
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!this.func_176592_e(var1, var2, var3) && this.field_150113_a == this.func_176597_g(var1, var2, var3)) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public boolean isAssociatedBlock(Block var1) {
      return var1 == Blocks.unlit_redstone_torch || var1 == Blocks.redstone_torch;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.redstone_torch);
   }

   public boolean canProvidePower() {
      return true;
   }

   private boolean func_176597_g(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing var4 = ((EnumFacing)var3.getValue(FACING_PROP)).getOpposite();
      return var1.func_175709_b(var2.offset(var4), var4);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      boolean var5 = this.func_176597_g(var1, var2, var3);
      List var6 = (List)field_150112_b.get(var1);

      while(var6 != null && !var6.isEmpty() && var1.getTotalWorldTime() - ((BlockRedstoneTorch.Toggle)var6.get(0)).field_150844_d > 60L) {
         var6.remove(0);
      }

      if (this.field_150113_a) {
         if (var5) {
            var1.setBlockState(var2, Blocks.unlit_redstone_torch.getDefaultState().withProperty(FACING_PROP, var3.getValue(FACING_PROP)), 3);
            if (this.func_176598_a(var1, var2, true)) {
               var1.playSoundEffect((double)((float)var2.getX() + 0.5F), (double)((float)var2.getY() + 0.5F), (double)((float)var2.getZ() + 0.5F), "random.fizz", 0.5F, 2.6F + (var1.rand.nextFloat() - var1.rand.nextFloat()) * 0.8F);

               for(int var7 = 0; var7 < 5; ++var7) {
                  double var8 = (double)var2.getX() + var4.nextDouble() * 0.6D + 0.2D;
                  double var10 = (double)var2.getY() + var4.nextDouble() * 0.6D + 0.2D;
                  double var12 = (double)var2.getZ() + var4.nextDouble() * 0.6D + 0.2D;
                  var1.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, var8, var10, var12, 0.0D, 0.0D, 0.0D);
               }

               var1.scheduleUpdate(var2, var1.getBlockState(var2).getBlock(), 160);
            }
         }
      } else if (!var5 && !this.func_176598_a(var1, var2, false)) {
         var1.setBlockState(var2, Blocks.redstone_torch.getDefaultState().withProperty(FACING_PROP, var3.getValue(FACING_PROP)), 3);
      }

   }

   protected BlockRedstoneTorch(boolean var1) {
      this.field_150113_a = var1;
      this.setTickRandomly(true);
      this.setCreativeTab((CreativeTabs)null);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.redstone_torch);
   }

   public int tickRate(World var1) {
      return 2;
   }

   private boolean func_176598_a(World var1, BlockPos var2, boolean var3) {
      if (!field_150112_b.containsKey(var1)) {
         field_150112_b.put(var1, Lists.newArrayList());
      }

      List var4 = (List)field_150112_b.get(var1);
      if (var3) {
         var4.add(new BlockRedstoneTorch.Toggle(var2, var1.getTotalWorldTime()));
      }

      int var5 = 0;

      for(int var6 = 0; var6 < var4.size(); ++var6) {
         BlockRedstoneTorch.Toggle var7 = (BlockRedstoneTorch.Toggle)var4.get(var6);
         if (var7.field_180111_a.equals(var2)) {
            ++var5;
            if (var5 >= 8) {
               return true;
            }
         }
      }

      return false;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      if (this.field_150113_a) {
         EnumFacing[] var4 = EnumFacing.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EnumFacing var7 = var4[var6];
            var1.notifyNeighborsOfStateChange(var2.offset(var7), this);
         }
      }

   }

   static class Toggle {
      private static final String __OBFID = "CL_00000299";
      BlockPos field_180111_a;
      long field_150844_d;

      public Toggle(BlockPos var1, long var2) {
         this.field_180111_a = var1;
         this.field_150844_d = var2;
      }
   }
}
