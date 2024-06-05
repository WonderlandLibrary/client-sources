package net.minecraft.block;

import com.google.common.collect.Lists;
import java.util.Iterator;
import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public abstract class BlockRailBase extends Block {
   private static final String __OBFID = "CL_00000195";
   protected final boolean isPowered;

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown());
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (!var1.isRemote) {
         var3 = this.func_176564_a(var1, var2, var3, true);
         if (this.isPowered) {
            this.onNeighborBlockChange(var1, var2, var3, this);
         }
      }

   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      super.breakBlock(var1, var2, var3);
      if (((BlockRailBase.EnumRailDirection)var3.getValue(this.func_176560_l())).func_177018_c()) {
         var1.notifyNeighborsOfStateChange(var2.offsetUp(), this);
      }

      if (this.isPowered) {
         var1.notifyNeighborsOfStateChange(var2, this);
         var1.notifyNeighborsOfStateChange(var2.offsetDown(), this);
      }

   }

   protected BlockRailBase(boolean var1) {
      super(Material.circuits);
      this.isPowered = var1;
      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      this.setCreativeTab(CreativeTabs.tabTransport);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      BlockRailBase.EnumRailDirection var4 = var3.getBlock() == this ? (BlockRailBase.EnumRailDirection)var3.getValue(this.func_176560_l()) : null;
      if (var4 != null && var4.func_177018_c()) {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.625F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.125F, 1.0F);
      }

   }

   protected IBlockState func_176564_a(World var1, BlockPos var2, IBlockState var3, boolean var4) {
      return var1.isRemote ? var3 : (new BlockRailBase.Rail(this, var1, var2, var3)).func_180364_a(var1.isBlockPowered(var2), var4).func_180362_b();
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!var1.isRemote) {
         BlockRailBase.EnumRailDirection var5 = (BlockRailBase.EnumRailDirection)var3.getValue(this.func_176560_l());
         boolean var6 = false;
         if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown())) {
            var6 = true;
         }

         if (var5 == BlockRailBase.EnumRailDirection.ASCENDING_EAST && !World.doesBlockHaveSolidTopSurface(var1, var2.offsetEast())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.ASCENDING_WEST && !World.doesBlockHaveSolidTopSurface(var1, var2.offsetWest())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.ASCENDING_NORTH && !World.doesBlockHaveSolidTopSurface(var1, var2.offsetNorth())) {
            var6 = true;
         } else if (var5 == BlockRailBase.EnumRailDirection.ASCENDING_SOUTH && !World.doesBlockHaveSolidTopSurface(var1, var2.offsetSouth())) {
            var6 = true;
         }

         if (var6) {
            this.dropBlockAsItem(var1, var2, var3, 0);
            var1.setBlockToAir(var2);
         } else {
            this.func_176561_b(var1, var2, var3, var4);
         }
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public abstract IProperty func_176560_l();

   public static boolean func_176562_d(World var0, BlockPos var1) {
      return func_176563_d(var0.getBlockState(var1));
   }

   public int getMobilityFlag() {
      return 0;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   protected void func_176561_b(World var1, BlockPos var2, IBlockState var3, Block var4) {
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      this.setBlockBoundsBasedOnState(var1, var2);
      return super.collisionRayTrace(var1, var2, var3, var4);
   }

   public boolean isFullCube() {
      return false;
   }

   public static boolean func_176563_d(IBlockState var0) {
      Block var1 = var0.getBlock();
      return var1 == Blocks.rail || var1 == Blocks.golden_rail || var1 == Blocks.detector_rail || var1 == Blocks.activator_rail;
   }

   public static enum EnumRailDirection implements IStringSerializable {
      EAST_WEST("EAST_WEST", 1, 1, "east_west"),
      ASCENDING_WEST("ASCENDING_WEST", 3, 3, "ascending_west"),
      ASCENDING_EAST("ASCENDING_EAST", 2, 2, "ascending_east"),
      ASCENDING_SOUTH("ASCENDING_SOUTH", 5, 5, "ascending_south"),
      NORTH_SOUTH("NORTH_SOUTH", 0, 0, "north_south"),
      NORTH_WEST("NORTH_WEST", 8, 8, "north_west");

      private final int field_177027_l;
      private static final BlockRailBase.EnumRailDirection[] ENUM$VALUES = new BlockRailBase.EnumRailDirection[]{NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST};
      private static final BlockRailBase.EnumRailDirection[] $VALUES = new BlockRailBase.EnumRailDirection[]{NORTH_SOUTH, EAST_WEST, ASCENDING_EAST, ASCENDING_WEST, ASCENDING_NORTH, ASCENDING_SOUTH, SOUTH_EAST, SOUTH_WEST, NORTH_WEST, NORTH_EAST};
      SOUTH_WEST("SOUTH_WEST", 7, 7, "south_west"),
      NORTH_EAST("NORTH_EAST", 9, 9, "north_east"),
      ASCENDING_NORTH("ASCENDING_NORTH", 4, 4, "ascending_north"),
      SOUTH_EAST("SOUTH_EAST", 6, 6, "south_east");

      private static final String __OBFID = "CL_00002137";
      private final String field_177028_m;
      private static final BlockRailBase.EnumRailDirection[] field_177030_k = new BlockRailBase.EnumRailDirection[values().length];

      private EnumRailDirection(String var3, int var4, int var5, String var6) {
         this.field_177027_l = var5;
         this.field_177028_m = var6;
      }

      static {
         BlockRailBase.EnumRailDirection[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockRailBase.EnumRailDirection var3 = var0[var2];
            field_177030_k[var3.func_177015_a()] = var3;
         }

      }

      public String toString() {
         return this.field_177028_m;
      }

      public int func_177015_a() {
         return this.field_177027_l;
      }

      public boolean func_177018_c() {
         return this == ASCENDING_NORTH || this == ASCENDING_EAST || this == ASCENDING_SOUTH || this == ASCENDING_WEST;
      }

      public String getName() {
         return this.field_177028_m;
      }

      public static BlockRailBase.EnumRailDirection func_177016_a(int var0) {
         if (var0 < 0 || var0 >= field_177030_k.length) {
            var0 = 0;
         }

         return field_177030_k[var0];
      }
   }

   public class Rail {
      private final List field_150657_g;
      private final boolean field_150656_f;
      private static final String __OBFID = "CL_00000196";
      private final BlockPos field_180367_c;
      private final World field_150660_b;
      private final BlockRailBase field_180365_d;
      private IBlockState field_180366_e;
      final BlockRailBase this$0;

      private boolean func_180359_a(BlockPos var1) {
         return BlockRailBase.func_176562_d(this.field_150660_b, var1) || BlockRailBase.func_176562_d(this.field_150660_b, var1.offsetUp()) || BlockRailBase.func_176562_d(this.field_150660_b, var1.offsetDown());
      }

      private boolean func_180363_c(BlockPos var1) {
         for(int var2 = 0; var2 < this.field_150657_g.size(); ++var2) {
            BlockPos var3 = (BlockPos)this.field_150657_g.get(var2);
            if (var3.getX() == var1.getX() && var3.getZ() == var1.getZ()) {
               return true;
            }
         }

         return false;
      }

      private boolean func_150649_b(BlockRailBase.Rail var1) {
         return this.func_150653_a(var1) || this.field_150657_g.size() != 2;
      }

      private void func_150645_c(BlockRailBase.Rail var1) {
         this.field_150657_g.add(var1.field_180367_c);
         BlockPos var2 = this.field_180367_c.offsetNorth();
         BlockPos var3 = this.field_180367_c.offsetSouth();
         BlockPos var4 = this.field_180367_c.offsetWest();
         BlockPos var5 = this.field_180367_c.offsetEast();
         boolean var6 = this.func_180363_c(var2);
         boolean var7 = this.func_180363_c(var3);
         boolean var8 = this.func_180363_c(var4);
         boolean var9 = this.func_180363_c(var5);
         BlockRailBase.EnumRailDirection var10 = null;
         if (var6 || var7) {
            var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         if (var8 || var9) {
            var10 = BlockRailBase.EnumRailDirection.EAST_WEST;
         }

         if (!this.field_150656_f) {
            if (var7 && var9 && !var6 && !var8) {
               var10 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (var7 && var8 && !var6 && !var9) {
               var10 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (var6 && var8 && !var7 && !var9) {
               var10 = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (var6 && var9 && !var7 && !var8) {
               var10 = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
         }

         if (var10 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var2.offsetUp())) {
               var10 = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.func_176562_d(this.field_150660_b, var3.offsetUp())) {
               var10 = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
         }

         if (var10 == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var5.offsetUp())) {
               var10 = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.func_176562_d(this.field_150660_b, var4.offsetUp())) {
               var10 = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
         }

         if (var10 == null) {
            var10 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), var10);
         this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);
      }

      private boolean func_180361_d(BlockPos var1) {
         BlockRailBase.Rail var2 = this.func_180697_b(var1);
         if (var2 == null) {
            return false;
         } else {
            var2.func_150651_b();
            return var2.func_150649_b(this);
         }
      }

      public BlockRailBase.Rail func_180364_a(boolean var1, boolean var2) {
         BlockPos var3 = this.field_180367_c.offsetNorth();
         BlockPos var4 = this.field_180367_c.offsetSouth();
         BlockPos var5 = this.field_180367_c.offsetWest();
         BlockPos var6 = this.field_180367_c.offsetEast();
         boolean var7 = this.func_180361_d(var3);
         boolean var8 = this.func_180361_d(var4);
         boolean var9 = this.func_180361_d(var5);
         boolean var10 = this.func_180361_d(var6);
         BlockRailBase.EnumRailDirection var11 = null;
         if ((var7 || var8) && !var9 && !var10) {
            var11 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         if ((var9 || var10) && !var7 && !var8) {
            var11 = BlockRailBase.EnumRailDirection.EAST_WEST;
         }

         if (!this.field_150656_f) {
            if (var8 && var10 && !var7 && !var9) {
               var11 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
            }

            if (var8 && var9 && !var7 && !var10) {
               var11 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
            }

            if (var7 && var9 && !var8 && !var10) {
               var11 = BlockRailBase.EnumRailDirection.NORTH_WEST;
            }

            if (var7 && var10 && !var8 && !var9) {
               var11 = BlockRailBase.EnumRailDirection.NORTH_EAST;
            }
         }

         if (var11 == null) {
            if (var7 || var8) {
               var11 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
            }

            if (var9 || var10) {
               var11 = BlockRailBase.EnumRailDirection.EAST_WEST;
            }

            if (!this.field_150656_f) {
               if (var1) {
                  if (var8 && var10) {
                     var11 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                  }

                  if (var9 && var8) {
                     var11 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                  }

                  if (var10 && var7) {
                     var11 = BlockRailBase.EnumRailDirection.NORTH_EAST;
                  }

                  if (var7 && var9) {
                     var11 = BlockRailBase.EnumRailDirection.NORTH_WEST;
                  }
               } else {
                  if (var7 && var9) {
                     var11 = BlockRailBase.EnumRailDirection.NORTH_WEST;
                  }

                  if (var10 && var7) {
                     var11 = BlockRailBase.EnumRailDirection.NORTH_EAST;
                  }

                  if (var9 && var8) {
                     var11 = BlockRailBase.EnumRailDirection.SOUTH_WEST;
                  }

                  if (var8 && var10) {
                     var11 = BlockRailBase.EnumRailDirection.SOUTH_EAST;
                  }
               }
            }
         }

         if (var11 == BlockRailBase.EnumRailDirection.NORTH_SOUTH) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var3.offsetUp())) {
               var11 = BlockRailBase.EnumRailDirection.ASCENDING_NORTH;
            }

            if (BlockRailBase.func_176562_d(this.field_150660_b, var4.offsetUp())) {
               var11 = BlockRailBase.EnumRailDirection.ASCENDING_SOUTH;
            }
         }

         if (var11 == BlockRailBase.EnumRailDirection.EAST_WEST) {
            if (BlockRailBase.func_176562_d(this.field_150660_b, var6.offsetUp())) {
               var11 = BlockRailBase.EnumRailDirection.ASCENDING_EAST;
            }

            if (BlockRailBase.func_176562_d(this.field_150660_b, var5.offsetUp())) {
               var11 = BlockRailBase.EnumRailDirection.ASCENDING_WEST;
            }
         }

         if (var11 == null) {
            var11 = BlockRailBase.EnumRailDirection.NORTH_SOUTH;
         }

         this.func_180360_a(var11);
         this.field_180366_e = this.field_180366_e.withProperty(this.field_180365_d.func_176560_l(), var11);
         if (var2 || this.field_150660_b.getBlockState(this.field_180367_c) != this.field_180366_e) {
            this.field_150660_b.setBlockState(this.field_180367_c, this.field_180366_e, 3);

            for(int var12 = 0; var12 < this.field_150657_g.size(); ++var12) {
               BlockRailBase.Rail var13 = this.func_180697_b((BlockPos)this.field_150657_g.get(var12));
               if (var13 != null) {
                  var13.func_150651_b();
                  if (var13.func_150649_b(this)) {
                     var13.func_150645_c(this);
                  }
               }
            }
         }

         return this;
      }

      private boolean func_150653_a(BlockRailBase.Rail var1) {
         return this.func_180363_c(var1.field_180367_c);
      }

      private void func_180360_a(BlockRailBase.EnumRailDirection var1) {
         this.field_150657_g.clear();
         switch(var1) {
         case NORTH_SOUTH:
            this.field_150657_g.add(this.field_180367_c.offsetNorth());
            this.field_150657_g.add(this.field_180367_c.offsetSouth());
            break;
         case EAST_WEST:
            this.field_150657_g.add(this.field_180367_c.offsetWest());
            this.field_150657_g.add(this.field_180367_c.offsetEast());
            break;
         case ASCENDING_EAST:
            this.field_150657_g.add(this.field_180367_c.offsetWest());
            this.field_150657_g.add(this.field_180367_c.offsetEast().offsetUp());
            break;
         case ASCENDING_WEST:
            this.field_150657_g.add(this.field_180367_c.offsetWest().offsetUp());
            this.field_150657_g.add(this.field_180367_c.offsetEast());
            break;
         case ASCENDING_NORTH:
            this.field_150657_g.add(this.field_180367_c.offsetNorth().offsetUp());
            this.field_150657_g.add(this.field_180367_c.offsetSouth());
            break;
         case ASCENDING_SOUTH:
            this.field_150657_g.add(this.field_180367_c.offsetNorth());
            this.field_150657_g.add(this.field_180367_c.offsetSouth().offsetUp());
            break;
         case SOUTH_EAST:
            this.field_150657_g.add(this.field_180367_c.offsetEast());
            this.field_150657_g.add(this.field_180367_c.offsetSouth());
            break;
         case SOUTH_WEST:
            this.field_150657_g.add(this.field_180367_c.offsetWest());
            this.field_150657_g.add(this.field_180367_c.offsetSouth());
            break;
         case NORTH_WEST:
            this.field_150657_g.add(this.field_180367_c.offsetWest());
            this.field_150657_g.add(this.field_180367_c.offsetNorth());
            break;
         case NORTH_EAST:
            this.field_150657_g.add(this.field_180367_c.offsetEast());
            this.field_150657_g.add(this.field_180367_c.offsetNorth());
         }

      }

      private void func_150651_b() {
         for(int var1 = 0; var1 < this.field_150657_g.size(); ++var1) {
            BlockRailBase.Rail var2 = this.func_180697_b((BlockPos)this.field_150657_g.get(var1));
            if (var2 != null && var2.func_150653_a(this)) {
               this.field_150657_g.set(var1, var2.field_180367_c);
            } else {
               this.field_150657_g.remove(var1--);
            }
         }

      }

      public IBlockState func_180362_b() {
         return this.field_180366_e;
      }

      private BlockRailBase.Rail func_180697_b(BlockPos var1) {
         IBlockState var2 = this.field_150660_b.getBlockState(var1);
         if (BlockRailBase.func_176563_d(var2)) {
            return this.this$0.new Rail(this.this$0, this.field_150660_b, var1, var2);
         } else {
            BlockPos var3 = var1.offsetUp();
            var2 = this.field_150660_b.getBlockState(var3);
            if (BlockRailBase.func_176563_d(var2)) {
               return this.this$0.new Rail(this.this$0, this.field_150660_b, var3, var2);
            } else {
               var3 = var1.offsetDown();
               var2 = this.field_150660_b.getBlockState(var3);
               return BlockRailBase.func_176563_d(var2) ? this.this$0.new Rail(this.this$0, this.field_150660_b, var3, var2) : null;
            }
         }
      }

      public Rail(BlockRailBase var1, World var2, BlockPos var3, IBlockState var4) {
         this.this$0 = var1;
         this.field_150657_g = Lists.newArrayList();
         this.field_150660_b = var2;
         this.field_180367_c = var3;
         this.field_180366_e = var4;
         this.field_180365_d = (BlockRailBase)var4.getBlock();
         BlockRailBase.EnumRailDirection var5 = (BlockRailBase.EnumRailDirection)var4.getValue(var1.func_176560_l());
         this.field_150656_f = this.field_180365_d.isPowered;
         this.func_180360_a(var5);
      }

      protected int countAdjacentRails() {
         int var1 = 0;
         Iterator var2 = EnumFacing.Plane.HORIZONTAL.iterator();

         while(var2.hasNext()) {
            EnumFacing var3 = (EnumFacing)var2.next();
            if (this.func_180359_a(this.field_180367_c.offset(var3))) {
               ++var1;
            }
         }

         return var1;
      }
   }

   static final class SwitchEnumRailDirection {
      static final int[] field_180371_a = new int[BlockRailBase.EnumRailDirection.values().length];
      private static final String __OBFID = "CL_00002138";

      static {
         try {
            field_180371_a[BlockRailBase.EnumRailDirection.NORTH_SOUTH.ordinal()] = 1;
         } catch (NoSuchFieldError var10) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.EAST_WEST.ordinal()] = 2;
         } catch (NoSuchFieldError var9) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.ASCENDING_EAST.ordinal()] = 3;
         } catch (NoSuchFieldError var8) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.ASCENDING_WEST.ordinal()] = 4;
         } catch (NoSuchFieldError var7) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.ASCENDING_NORTH.ordinal()] = 5;
         } catch (NoSuchFieldError var6) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.ASCENDING_SOUTH.ordinal()] = 6;
         } catch (NoSuchFieldError var5) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.SOUTH_EAST.ordinal()] = 7;
         } catch (NoSuchFieldError var4) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.SOUTH_WEST.ordinal()] = 8;
         } catch (NoSuchFieldError var3) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.NORTH_WEST.ordinal()] = 9;
         } catch (NoSuchFieldError var2) {
         }

         try {
            field_180371_a[BlockRailBase.EnumRailDirection.NORTH_EAST.ordinal()] = 10;
         } catch (NoSuchFieldError var1) {
         }

      }
   }
}
