package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.Tuple;
import net.minecraft.world.World;

public class BlockSponge extends Block {
   private static final String __OBFID = "CL_00000311";
   public static final PropertyBool WET_PROP = PropertyBool.create("wet");

   public int getMetaFromState(IBlockState var1) {
      return (Boolean)var1.getValue(WET_PROP) ? 1 : 0;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      this.setWet(var1, var2, var3);
      super.onNeighborBlockChange(var1, var2, var3, var4);
   }

   protected void setWet(World var1, BlockPos var2, IBlockState var3) {
      if (!(Boolean)var3.getValue(WET_PROP) && this.absorbWater(var1, var2)) {
         var1.setBlockState(var2, var3.withProperty(WET_PROP, true), 2);
         var1.playAuxSFX(2001, var2, Block.getIdFromBlock(Blocks.water));
      }

   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if ((Boolean)var3.getValue(WET_PROP)) {
         EnumFacing var5 = EnumFacing.random(var4);
         if (var5 != EnumFacing.UP && !World.doesBlockHaveSolidTopSurface(var1, var2.offset(var5))) {
            double var6 = (double)var2.getX();
            double var8 = (double)var2.getY();
            double var10 = (double)var2.getZ();
            if (var5 == EnumFacing.DOWN) {
               var8 -= 0.05D;
               var6 += var4.nextDouble();
               var10 += var4.nextDouble();
            } else {
               var8 += var4.nextDouble() * 0.8D;
               if (var5.getAxis() == EnumFacing.Axis.X) {
                  var10 += var4.nextDouble();
                  if (var5 == EnumFacing.EAST) {
                     ++var6;
                  } else {
                     var6 += 0.05D;
                  }
               } else {
                  var6 += var4.nextDouble();
                  if (var5 == EnumFacing.SOUTH) {
                     ++var10;
                  } else {
                     var10 += 0.05D;
                  }
               }
            }

            var1.spawnParticle(EnumParticleTypes.DRIP_WATER, var6, var8, var10, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   private boolean absorbWater(World var1, BlockPos var2) {
      LinkedList var3 = Lists.newLinkedList();
      ArrayList var4 = Lists.newArrayList();
      var3.add(new Tuple(var2, 0));
      int var5 = 0;

      BlockPos var6;
      while(!var3.isEmpty()) {
         Tuple var7 = (Tuple)var3.poll();
         var6 = (BlockPos)var7.getFirst();
         int var8 = (Integer)var7.getSecond();
         EnumFacing[] var9 = EnumFacing.values();
         int var10 = var9.length;

         for(int var11 = 0; var11 < var10; ++var11) {
            EnumFacing var12 = var9[var11];
            BlockPos var13 = var6.offset(var12);
            if (var1.getBlockState(var13).getBlock().getMaterial() == Material.water) {
               var1.setBlockState(var13, Blocks.air.getDefaultState(), 2);
               var4.add(var13);
               ++var5;
               if (var8 < 6) {
                  var3.add(new Tuple(var13, var8 + 1));
               }
            }
         }

         if (var5 > 64) {
            break;
         }
      }

      Iterator var14 = var4.iterator();

      while(var14.hasNext()) {
         var6 = (BlockPos)var14.next();
         var1.notifyNeighborsOfStateChange(var6, Blocks.air);
      }

      return var5 > 0;
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, 0));
      var3.add(new ItemStack(var1, 1, 1));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{WET_PROP});
   }

   protected BlockSponge() {
      super(Material.sponge);
      this.setDefaultState(this.blockState.getBaseState().withProperty(WET_PROP, false));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.setWet(var1, var2, var3);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(WET_PROP, (var1 & 1) == 1);
   }

   public int damageDropped(IBlockState var1) {
      return (Boolean)var1.getValue(WET_PROP) ? 1 : 0;
   }
}
