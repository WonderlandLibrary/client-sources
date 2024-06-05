package net.minecraft.block;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Iterator;
import java.util.Random;
import java.util.Set;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockRedstoneWire extends Block {
   private final Set field_150179_b = Sets.newHashSet();
   public static final PropertyEnum NORTH = PropertyEnum.create("north", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum SOUTH = PropertyEnum.create("south", BlockRedstoneWire.EnumAttachPosition.class);
   private static final String __OBFID = "CL_00000295";
   public static final PropertyEnum WEST = PropertyEnum.create("west", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyEnum EAST = PropertyEnum.create("east", BlockRedstoneWire.EnumAttachPosition.class);
   public static final PropertyInteger POWER = PropertyInteger.create("power", 0, 15);
   private boolean canProvidePower = true;

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(POWER);
   }

   public int isProvidingStrongPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      return !this.canProvidePower ? 0 : this.isProvidingWeakPower(var1, var2, var3, var4);
   }

   private int func_176342_a(World var1, BlockPos var2, int var3) {
      if (var1.getBlockState(var2).getBlock() != this) {
         return var3;
      } else {
         int var4 = (Integer)var1.getBlockState(var2).getValue(POWER);
         return var4 > var3 ? var4 : var3;
      }
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected static boolean func_176343_a(IBlockState var0, EnumFacing var1) {
      Block var2 = var0.getBlock();
      if (var2 == Blocks.redstone_wire) {
         return true;
      } else if (Blocks.unpowered_repeater.func_149907_e(var2)) {
         EnumFacing var3 = (EnumFacing)var0.getValue(BlockRedstoneRepeater.AGE);
         return var3 == var1 || var3.getOpposite() == var1;
      } else {
         return var2.canProvidePower() && var1 != null;
      }
   }

   protected static boolean func_176340_e(IBlockAccess var0, BlockPos var1) {
      return func_176346_d(var0.getBlockState(var1));
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      int var5 = (Integer)var3.getValue(POWER);
      if (var5 != 0) {
         double var6 = (double)var2.getX() + 0.5D + ((double)var4.nextFloat() - 0.5D) * 0.2D;
         double var8 = (double)((float)var2.getY() + 0.0625F);
         double var10 = (double)var2.getZ() + 0.5D + ((double)var4.nextFloat() - 0.5D) * 0.2D;
         float var12 = (float)var5 / 15.0F;
         float var13 = var12 * 0.6F + 0.4F;
         float var14 = Math.max(0.0F, var12 * var12 * 0.7F - 0.5F);
         float var15 = Math.max(0.0F, var12 * var12 * 0.6F - 0.7F);
         var1.spawnParticle(EnumParticleTypes.REDSTONE, var6, var8, var10, (double)var13, (double)var14, (double)var15);
      }

   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{NORTH, EAST, SOUTH, WEST, POWER});
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) || var1.getBlockState(var2.offsetDown()).getBlock() == Blocks.glowstone;
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      if (!var1.isRemote) {
         EnumFacing[] var4 = EnumFacing.values();
         int var5 = var4.length;

         EnumFacing var7;
         for(int var6 = 0; var6 < var5; ++var6) {
            var7 = var4[var6];
            var1.notifyNeighborsOfStateChange(var2.offset(var7), this);
         }

         this.updateSurroundingRedstone(var1, var2, var3);
         Iterator var9 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var9.hasNext()) {
            var7 = (EnumFacing)var9.next();
            this.func_176344_d(var1, var2.offset(var7));
         }

         var9 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var9.hasNext()) {
            var7 = (EnumFacing)var9.next();
            BlockPos var8 = var2.offset(var7);
            if (var1.getBlockState(var8).getBlock().isNormalCube()) {
               this.func_176344_d(var1, var8.offsetUp());
            } else {
               this.func_176344_d(var1, var8.offsetDown());
            }
         }
      }

   }

   private IBlockState func_176345_a(World var1, BlockPos var2, BlockPos var3, IBlockState var4) {
      IBlockState var5 = var4;
      int var6 = (Integer)var4.getValue(POWER);
      byte var7 = 0;
      int var8 = this.func_176342_a(var1, var3, var7);
      this.canProvidePower = false;
      int var9 = var1.func_175687_A(var2);
      this.canProvidePower = true;
      if (var9 > 0 && var9 > var8 - 1) {
         var8 = var9;
      }

      int var10 = 0;
      Iterator var11 = EnumFacing.Plane.HORIZONTAL.iterator();

      while(true) {
         while(var11.hasNext()) {
            EnumFacing var12 = (EnumFacing)var11.next();
            BlockPos var13 = var2.offset(var12);
            boolean var14 = var13.getX() != var3.getX() || var13.getZ() != var3.getZ();
            if (var14) {
               var10 = this.func_176342_a(var1, var13, var10);
            }

            if (var1.getBlockState(var13).getBlock().isNormalCube() && !var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube()) {
               if (var14 && var2.getY() >= var3.getY()) {
                  var10 = this.func_176342_a(var1, var13.offsetUp(), var10);
               }
            } else if (!var1.getBlockState(var13).getBlock().isNormalCube() && var14 && var2.getY() <= var3.getY()) {
               var10 = this.func_176342_a(var1, var13.offsetDown(), var10);
            }
         }

         if (var10 > var8) {
            var8 = var10 - 1;
         } else if (var8 > 0) {
            --var8;
         } else {
            var8 = 0;
         }

         if (var9 > var8 - 1) {
            var8 = var9;
         }

         if (var6 != var8) {
            var4 = var4.withProperty(POWER, var8);
            if (var1.getBlockState(var2) == var5) {
               var1.setBlockState(var2, var4, 2);
            }

            this.field_150179_b.add(var2);
            EnumFacing[] var16 = EnumFacing.values();
            int var17 = var16.length;

            for(int var18 = 0; var18 < var17; ++var18) {
               EnumFacing var15 = var16[var18];
               this.field_150179_b.add(var2.offset(var15));
            }
         }

         return var4;
      }
   }

   public Item getItem(World var1, BlockPos var2) {
      return Items.redstone;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      var1 = var1.withProperty(WEST, this.getAttachPosition(var2, var3, EnumFacing.WEST));
      var1 = var1.withProperty(EAST, this.getAttachPosition(var2, var3, EnumFacing.EAST));
      var1 = var1.withProperty(NORTH, this.getAttachPosition(var2, var3, EnumFacing.NORTH));
      var1 = var1.withProperty(SOUTH, this.getAttachPosition(var2, var3, EnumFacing.SOUTH));
      return var1;
   }

   private BlockRedstoneWire.EnumAttachPosition getAttachPosition(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3);
      Block var5 = var1.getBlockState(var2.offset(var3)).getBlock();
      if (func_176343_a(var1.getBlockState(var4), var3) || !var5.isSolidFullCube() && func_176346_d(var1.getBlockState(var4.offsetDown()))) {
         return BlockRedstoneWire.EnumAttachPosition.SIDE;
      } else {
         Block var6 = var1.getBlockState(var2.offsetUp()).getBlock();
         return !var6.isSolidFullCube() && var5.isSolidFullCube() && func_176346_d(var1.getBlockState(var4.offsetUp())) ? BlockRedstoneWire.EnumAttachPosition.UP : BlockRedstoneWire.EnumAttachPosition.NONE;
      }
   }

   private IBlockState updateSurroundingRedstone(World var1, BlockPos var2, IBlockState var3) {
      var3 = this.func_176345_a(var1, var2, var2, var3);
      ArrayList var4 = Lists.newArrayList(this.field_150179_b);
      this.field_150179_b.clear();
      Iterator var5 = var4.iterator();

      while(var5.hasNext()) {
         BlockPos var6 = (BlockPos)var5.next();
         var1.notifyNeighborsOfStateChange(var6, this);
      }

      return var3;
   }

   private void func_176344_d(World var1, BlockPos var2) {
      if (var1.getBlockState(var2).getBlock() == this) {
         var1.notifyNeighborsOfStateChange(var2, this);
         EnumFacing[] var3 = EnumFacing.values();
         int var4 = var3.length;

         for(int var5 = 0; var5 < var4; ++var5) {
            EnumFacing var6 = var3[var5];
            var1.notifyNeighborsOfStateChange(var2.offset(var6), this);
         }
      }

   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public int isProvidingWeakPower(IBlockAccess var1, BlockPos var2, IBlockState var3, EnumFacing var4) {
      if (!this.canProvidePower) {
         return 0;
      } else {
         int var5 = (Integer)var3.getValue(POWER);
         if (var5 == 0) {
            return 0;
         } else if (var4 == EnumFacing.UP) {
            return var5;
         } else {
            EnumSet var6 = EnumSet.noneOf(EnumFacing.class);
            Iterator var7 = EnumFacing.Plane.HORIZONTAL.iterator();

            while(var7.hasNext()) {
               EnumFacing var8 = (EnumFacing)var7.next();
               if (this.func_176339_d(var1, var2, var8)) {
                  var6.add(var8);
               }
            }

            if (var4.getAxis().isHorizontal() && var6.isEmpty()) {
               return var5;
            } else if (var6.contains(var4) && !var6.contains(var4.rotateYCCW()) && !var6.contains(var4.rotateY())) {
               return var5;
            } else {
               return 0;
            }
         }
      }
   }

   public int colorMultiplier(IBlockAccess var1, BlockPos var2, int var3) {
      IBlockState var4 = var1.getBlockState(var2);
      return var4.getBlock() != this ? super.colorMultiplier(var1, var2, var3) : this.func_176337_b((Integer)var4.getValue(POWER));
   }

   private boolean func_176339_d(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      BlockPos var4 = var2.offset(var3);
      IBlockState var5 = var1.getBlockState(var4);
      Block var6 = var5.getBlock();
      boolean var7 = var6.isNormalCube();
      boolean var8 = var1.getBlockState(var2.offsetUp()).getBlock().isNormalCube();
      return !var8 && var7 && func_176340_e(var1, var4.offsetUp()) ? true : (func_176343_a(var5, var3) ? true : (var6 == Blocks.powered_repeater && var5.getValue(BlockRedstoneDiode.AGE) == var3 ? true : !var7 && func_176340_e(var1, var4.offsetDown())));
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         if (this.canPlaceBlockAt(var1, var2)) {
            this.updateSurroundingRedstone(var1, var2, var3);
         } else {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         }
      }

   }

   public boolean isFullCube() {
      return false;
   }

   protected static boolean func_176346_d(IBlockState var0) {
      return func_176343_a(var0, (EnumFacing)null);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         this.updateSurroundingRedstone(var1, var2, var3);
         Iterator var4 = EnumFacing.Plane.VERTICAL.iterator();

         EnumFacing var5;
         while(var4.hasNext()) {
            var5 = (EnumFacing)var4.next();
            var1.notifyNeighborsOfStateChange(var2.offset(var5), this);
         }

         var4 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var4.hasNext()) {
            var5 = (EnumFacing)var4.next();
            this.func_176344_d(var1, var2.offset(var5));
         }

         var4 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var4.hasNext()) {
            var5 = (EnumFacing)var4.next();
            BlockPos var6 = var2.offset(var5);
            if (var1.getBlockState(var6).getBlock().isNormalCube()) {
               this.func_176344_d(var1, var6.offsetUp());
            } else {
               this.func_176344_d(var1, var6.offsetDown());
            }
         }
      }

   }

   private int func_176337_b(int var1) {
      float var2 = (float)var1 / 15.0F;
      float var3 = var2 * 0.6F + 0.4F;
      if (var1 == 0) {
         var3 = 0.3F;
      }

      float var4 = var2 * var2 * 0.7F - 0.5F;
      float var5 = var2 * var2 * 0.6F - 0.7F;
      if (var4 < 0.0F) {
         var4 = 0.0F;
      }

      if (var5 < 0.0F) {
         var5 = 0.0F;
      }

      int var6 = MathHelper.clamp_int((int)(var3 * 255.0F), 0, 255);
      int var7 = MathHelper.clamp_int((int)(var4 * 255.0F), 0, 255);
      int var8 = MathHelper.clamp_int((int)(var5 * 255.0F), 0, 255);
      return -16777216 | var6 << 16 | var7 << 8 | var8;
   }

   public BlockRedstoneWire() {
      super(Material.circuits);
      this.setDefaultState(this.blockState.getBaseState().withProperty(NORTH, BlockRedstoneWire.EnumAttachPosition.NONE).withProperty(EAST, BlockRedstoneWire.EnumAttachPosition.NONE).withProperty(SOUTH, BlockRedstoneWire.EnumAttachPosition.NONE).withProperty(WEST, BlockRedstoneWire.EnumAttachPosition.NONE).withProperty(POWER, 0));
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.0625F, 1.0F);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(POWER, var1);
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Items.redstone;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean canProvidePower() {
      return this.canProvidePower;
   }

   static enum EnumAttachPosition implements IStringSerializable {
      NONE("NONE", 2, "none"),
      SIDE("SIDE", 1, "side");

      private final String field_176820_d;
      UP("UP", 0, "up");

      private static final BlockRedstoneWire.EnumAttachPosition[] $VALUES = new BlockRedstoneWire.EnumAttachPosition[]{UP, SIDE, NONE};
      private static final BlockRedstoneWire.EnumAttachPosition[] ENUM$VALUES = new BlockRedstoneWire.EnumAttachPosition[]{UP, SIDE, NONE};
      private static final String __OBFID = "CL_00002070";

      public String getName() {
         return this.field_176820_d;
      }

      private EnumAttachPosition(String var3, int var4, String var5) {
         this.field_176820_d = var5;
      }

      public String toString() {
         return this.getName();
      }
   }
}
