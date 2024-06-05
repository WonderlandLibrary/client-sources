package net.minecraft.block;

import java.util.Iterator;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripWire extends Block {
   public static final PropertyBool field_176294_M = PropertyBool.create("attached");
   public static final PropertyBool field_176289_Q = PropertyBool.create("south");
   public static final PropertyBool field_176293_a = PropertyBool.create("powered");
   public static final PropertyBool field_176292_R = PropertyBool.create("west");
   public static final PropertyBool field_176291_P = PropertyBool.create("east");
   public static final PropertyBool field_176290_b = PropertyBool.create("suspended");
   public static final PropertyBool field_176295_N = PropertyBool.create("disarmed");
   public static final PropertyBool field_176296_O = PropertyBool.create("north");
   private static final String __OBFID = "CL_00000328";

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if ((Boolean)var1.getValue(field_176293_a)) {
         var2 |= 1;
      }

      if ((Boolean)var1.getValue(field_176290_b)) {
         var2 |= 2;
      }

      if ((Boolean)var1.getValue(field_176294_M)) {
         var2 |= 4;
      }

      if ((Boolean)var1.getValue(field_176295_N)) {
         var2 |= 8;
      }

      return var2;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (!var1.isRemote && !(Boolean)var3.getValue(field_176293_a)) {
         this.func_176288_d(var1, var2);
      }

   }

   private void func_176288_d(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      boolean var4 = (Boolean)var3.getValue(field_176293_a);
      boolean var5 = false;
      List var6 = var1.getEntitiesWithinAABBExcludingEntity((Entity)null, new AxisAlignedBB((double)var2.getX() + this.minX, (double)var2.getY() + this.minY, (double)var2.getZ() + this.minZ, (double)var2.getX() + this.maxX, (double)var2.getY() + this.maxY, (double)var2.getZ() + this.maxZ));
      if (!var6.isEmpty()) {
         Iterator var7 = var6.iterator();

         while(var7.hasNext()) {
            Entity var8 = (Entity)var7.next();
            if (!var8.doesEntityNotTriggerPressurePlate()) {
               var5 = true;
               break;
            }
         }
      }

      if (var5 != var4) {
         var3 = var3.withProperty(field_176293_a, var5);
         var1.setBlockState(var2, var3, 3);
         this.func_176286_e(var1, var2, var3);
      }

      if (var5) {
         var1.scheduleUpdate(var2, this, this.tickRate(var1));
      }

   }

   public boolean isFullCube() {
      return false;
   }

   public void onBlockHarvested(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4) {
      if (!var1.isRemote && var4.getCurrentEquippedItem() != null && var4.getCurrentEquippedItem().getItem() == Items.shears) {
         var1.setBlockState(var2, var3.withProperty(field_176295_N, true), 4);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176293_a, field_176290_b, field_176294_M, field_176295_N, field_176296_O, field_176291_P, field_176292_R, field_176289_Q});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176293_a, (var1 & 1) > 0).withProperty(field_176290_b, (var1 & 2) > 0).withProperty(field_176294_M, (var1 & 4) > 0).withProperty(field_176295_N, (var1 & 8) > 0);
   }

   private void func_176286_e(World var1, BlockPos var2, IBlockState var3) {
      EnumFacing[] var4 = new EnumFacing[]{EnumFacing.SOUTH, EnumFacing.WEST};
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         EnumFacing var7 = var4[var6];

         for(int var8 = 1; var8 < 42; ++var8) {
            BlockPos var9 = var2.offset(var7, var8);
            IBlockState var10 = var1.getBlockState(var9);
            if (var10.getBlock() == Blocks.tripwire_hook) {
               if (var10.getValue(BlockTripWireHook.field_176264_a) == var7.getOpposite()) {
                  Blocks.tripwire_hook.func_176260_a(var1, var9, var10, false, true, var8, var3);
               }
               break;
            }

            if (var10.getBlock() != Blocks.tripwire) {
               break;
            }
         }
      }

   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(field_176296_O, func_176287_c(var2, var3, var1, EnumFacing.NORTH)).withProperty(field_176291_P, func_176287_c(var2, var3, var1, EnumFacing.EAST)).withProperty(field_176289_Q, func_176287_c(var2, var3, var1, EnumFacing.SOUTH)).withProperty(field_176292_R, func_176287_c(var2, var3, var1, EnumFacing.WEST));
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      this.func_176286_e(var1, var2, var3.withProperty(field_176293_a, true));
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.string;
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      boolean var5 = (Boolean)var3.getValue(field_176290_b);
      boolean var6 = !World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
      if (var5 != var6) {
         this.dropBlockAsItem(var1, var2, var3, 0);
         var1.setBlockToAir(var2);
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      boolean var4 = (Boolean)var3.getValue(field_176294_M);
      boolean var5 = (Boolean)var3.getValue(field_176290_b);
      if (!var5) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.09375F, 1.0F);
      } else if (!var4) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0625F, 0.0F, 1.0F, 0.15625F, 1.0F);
      }

   }

   public static boolean func_176287_c(IBlockAccess var0, BlockPos var1, IBlockState var2, EnumFacing var3) {
      BlockPos var4 = var1.offset(var3);
      IBlockState var5 = var0.getBlockState(var4);
      Block var6 = var5.getBlock();
      if (var6 == Blocks.tripwire_hook) {
         EnumFacing var9 = var3.getOpposite();
         return var5.getValue(BlockTripWireHook.field_176264_a) == var9;
      } else if (var6 == Blocks.tripwire) {
         boolean var7 = (Boolean)var2.getValue(field_176290_b);
         boolean var8 = (Boolean)var5.getValue(field_176290_b);
         return var7 == var8;
      } else {
         return false;
      }
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public void randomTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      var3 = var3.withProperty(field_176290_b, !World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()));
      var1.setBlockState(var2, var3, 3);
      this.func_176286_e(var1, var2, var3);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.string;
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (!var1.isRemote && (Boolean)var1.getBlockState(var2).getValue(field_176293_a)) {
         this.func_176288_d(var1, var2);
      }

   }

   public BlockTripWire() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176293_a, false).withProperty(field_176290_b, false).withProperty(field_176294_M, false).withProperty(field_176295_N, false).withProperty(field_176296_O, false).withProperty(field_176291_P, false).withProperty(field_176289_Q, false).withProperty(field_176292_R, false));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.15625F, 1.0F);
      this.setTickRandomly(true);
   }
}
