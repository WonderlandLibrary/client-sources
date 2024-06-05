package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStairs extends Block {
   public static final PropertyEnum SHAPE;
   private int field_150153_O;
   public static final PropertyEnum HALF;
   public static final PropertyDirection FACING;
   private final Block modelBlock;
   private final IBlockState modelState;
   private boolean field_150152_N;
   private static final String __OBFID = "CL_00000314";
   private static final int[][] field_150150_a;

   public void onBlockDestroyedByExplosion(World var1, BlockPos var2, Explosion var3) {
      this.modelBlock.onBlockDestroyedByExplosion(var1, var2, var3);
   }

   public static boolean isSameStair(IBlockAccess var0, BlockPos var1, IBlockState var2) {
      IBlockState var3 = var0.getBlockState(var1);
      Block var4 = var3.getBlock();
      return isBlockStairs(var4) && var3.getValue(HALF) == var2.getValue(HALF) && var3.getValue(FACING) == var2.getValue(FACING);
   }

   public MapColor getMapColor(IBlockState var1) {
      return this.modelBlock.getMapColor(this.modelState);
   }

   public Vec3 modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return this.modelBlock.modifyAcceleration(var1, var2, var3, var4);
   }

   public int getMixedBrightnessForBlock(IBlockAccess var1, BlockPos var2) {
      return this.modelBlock.getMixedBrightnessForBlock(var1, var2);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return this.modelBlock.canPlaceBlockAt(var1, var2);
   }

   public float getExplosionResistance(Entity var1) {
      return this.modelBlock.getExplosionResistance(var1);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.modelBlock.updateTick(var1, var2, var3, var4);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, HALF, SHAPE});
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.onNeighborBlockChange(var1, var2, this.modelState, Blocks.air);
      this.modelBlock.onBlockAdded(var1, var2, this.modelState);
   }

   public boolean isCollidable() {
      return this.modelBlock.isCollidable();
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      if (this.field_150152_N) {
         this.setBlockBounds(0.5F * (float)(this.field_150153_O % 2), 0.5F * (float)(this.field_150153_O / 4 % 2), 0.5F * (float)(this.field_150153_O / 2 % 2), 0.5F + 0.5F * (float)(this.field_150153_O % 2), 0.5F + 0.5F * (float)(this.field_150153_O / 4 % 2), 0.5F + 0.5F * (float)(this.field_150153_O / 2 % 2));
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8);
      var9 = var9.withProperty(FACING, var8.func_174811_aO()).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
      return var3 == EnumFacing.DOWN || var3 != EnumFacing.UP && !((double)var5 <= 0.5D) ? var9.withProperty(HALF, BlockStairs.EnumHalf.TOP) : var9.withProperty(HALF, BlockStairs.EnumHalf.BOTTOM);
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
      this.modelBlock.onBlockDestroyedByPlayer(var1, var2, var3);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      this.modelBlock.breakBlock(var1, var2, this.modelState);
   }

   protected BlockStairs(IBlockState var1) {
      super(var1.getBlock().blockMaterial);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, BlockStairs.EnumHalf.BOTTOM).withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT));
      this.modelBlock = var1.getBlock();
      this.modelState = var1;
      this.setHardness(this.modelBlock.blockHardness);
      this.setResistance(this.modelBlock.blockResistance / 3.0F);
      this.setStepSound(this.modelBlock.stepSound);
      this.setLightOpacity(255);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int tickRate(World var1) {
      return this.modelBlock.tickRate(var1);
   }

   public void setBaseCollisionBounds(IBlockAccess var1, BlockPos var2) {
      if (var1.getBlockState(var2).getValue(HALF) == BlockStairs.EnumHalf.TOP) {
         this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      MovingObjectPosition[] var5 = new MovingObjectPosition[8];
      IBlockState var6 = var1.getBlockState(var2);
      int var7 = ((EnumFacing)var6.getValue(FACING)).getHorizontalIndex();
      boolean var8 = var6.getValue(HALF) == BlockStairs.EnumHalf.TOP;
      int[] var9 = field_150150_a[var7 + (var8 ? 4 : 0)];
      this.field_150152_N = true;

      for(int var10 = 0; var10 < 8; ++var10) {
         this.field_150153_O = var10;
         if (Arrays.binarySearch(var9, var10) < 0) {
            var5[var10] = super.collisionRayTrace(var1, var2, var3, var4);
         }
      }

      int[] var21 = var9;
      int var11 = var9.length;

      for(int var12 = 0; var12 < var11; ++var12) {
         int var13 = var21[var12];
         var5[var13] = null;
      }

      MovingObjectPosition var22 = null;
      double var23 = 0.0D;
      MovingObjectPosition[] var15 = var5;
      int var16 = var5.length;

      for(int var17 = 0; var17 < var16; ++var17) {
         MovingObjectPosition var18 = var15[var17];
         if (var18 != null) {
            double var19 = var18.hitVec.squareDistanceTo(var4);
            if (var19 > var23) {
               var22 = var18;
               var23 = var19;
            }
         }
      }

      return var22;
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      return this.modelBlock.getSelectedBoundingBox(var1, var2);
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBaseCollisionBounds(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      boolean var7 = this.func_176306_h(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      if (var7 && this.func_176304_i(var1, var2)) {
         super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      }

      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public boolean func_176306_h(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs.EnumHalf var5 = (BlockStairs.EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs.EnumHalf.TOP;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if (var6) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 1.0F;
      float var11 = 0.0F;
      float var12 = 0.5F;
      boolean var13 = true;
      IBlockState var14;
      Block var15;
      EnumFacing var16;
      if (var4 == EnumFacing.EAST) {
         var9 = 0.5F;
         var12 = 1.0F;
         var14 = var1.getBlockState(var2.offsetEast());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if (var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.WEST) {
         var10 = 0.5F;
         var12 = 1.0F;
         var14 = var1.getBlockState(var2.offsetWest());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if (var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.SOUTH) {
         var11 = 0.5F;
         var12 = 1.0F;
         var14 = var1.getBlockState(var2.offsetSouth());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.WEST && !isSameStair(var1, var2.offsetEast(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if (var16 == EnumFacing.EAST && !isSameStair(var1, var2.offsetWest(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      } else if (var4 == EnumFacing.NORTH) {
         var14 = var1.getBlockState(var2.offsetNorth());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.WEST && !isSameStair(var1, var2.offsetEast(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if (var16 == EnumFacing.EAST && !isSameStair(var1, var2.offsetWest(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      }

      this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      return var13;
   }

   static {
      FACING = PropertyDirection.create("facing", (Predicate)EnumFacing.Plane.HORIZONTAL);
      HALF = PropertyEnum.create("half", BlockStairs.EnumHalf.class);
      SHAPE = PropertyEnum.create("shape", BlockStairs.EnumShape.class);
      field_150150_a = new int[][]{{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
   }

   public boolean func_176304_i(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs.EnumHalf var5 = (BlockStairs.EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs.EnumHalf.TOP;
      float var7 = 0.5F;
      float var8 = 1.0F;
      if (var6) {
         var7 = 0.0F;
         var8 = 0.5F;
      }

      float var9 = 0.0F;
      float var10 = 0.5F;
      float var11 = 0.5F;
      float var12 = 1.0F;
      boolean var13 = false;
      IBlockState var14;
      Block var15;
      EnumFacing var16;
      if (var4 == EnumFacing.EAST) {
         var14 = var1.getBlockState(var2.offsetWest());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if (var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.WEST) {
         var14 = var1.getBlockState(var2.offsetEast());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var9 = 0.5F;
            var10 = 1.0F;
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if (var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.SOUTH) {
         var14 = var1.getBlockState(var2.offsetNorth());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var11 = 0.0F;
            var12 = 0.5F;
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.WEST && !isSameStair(var1, var2.offsetWest(), var3)) {
               var13 = true;
            } else if (var16 == EnumFacing.EAST && !isSameStair(var1, var2.offsetEast(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      } else if (var4 == EnumFacing.NORTH) {
         var14 = var1.getBlockState(var2.offsetSouth());
         var15 = var14.getBlock();
         if (isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            var16 = (EnumFacing)var14.getValue(FACING);
            if (var16 == EnumFacing.WEST && !isSameStair(var1, var2.offsetWest(), var3)) {
               var13 = true;
            } else if (var16 == EnumFacing.EAST && !isSameStair(var1, var2.offsetEast(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      }

      if (var13) {
         this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      }

      return var13;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (this.func_176306_h(var2, var3)) {
         switch(this.func_176305_g(var2, var3)) {
         case 0:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
            break;
         case 1:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.INNER_RIGHT);
            break;
         case 2:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.INNER_LEFT);
         }
      } else {
         switch(this.func_176307_f(var2, var3)) {
         case 0:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.STRAIGHT);
            break;
         case 1:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_RIGHT);
            break;
         case 2:
            var1 = var1.withProperty(SHAPE, BlockStairs.EnumShape.OUTER_LEFT);
         }
      }

      return var1;
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if (var1.getValue(HALF) == BlockStairs.EnumHalf.TOP) {
         var2 |= 4;
      }

      var2 |= 5 - ((EnumFacing)var1.getValue(FACING)).getIndex();
      return var2;
   }

   public boolean canCollideCheck(IBlockState var1, boolean var2) {
      return this.modelBlock.canCollideCheck(var1, var2);
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, Entity var3) {
      this.modelBlock.onEntityCollidedWithBlock(var1, var2, var3);
   }

   public int func_176307_f(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs.EnumHalf var5 = (BlockStairs.EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs.EnumHalf.TOP;
      IBlockState var7;
      Block var8;
      EnumFacing var9;
      if (var4 == EnumFacing.EAST) {
         var7 = var1.getBlockState(var2.offsetEast());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      } else if (var4 == EnumFacing.WEST) {
         var7 = var1.getBlockState(var2.offsetWest());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.SOUTH) {
         var7 = var1.getBlockState(var2.offsetSouth());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.WEST && !isSameStair(var1, var2.offsetEast(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var9 == EnumFacing.EAST && !isSameStair(var1, var2.offsetWest(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.NORTH) {
         var7 = var1.getBlockState(var2.offsetNorth());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.WEST && !isSameStair(var1, var2.offsetEast(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.EAST && !isSameStair(var1, var2.offsetWest(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return this.modelBlock.onBlockActivated(var1, var2, this.modelState, var4, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.modelBlock.getBlockLayer();
   }

   public void onBlockClicked(World var1, BlockPos var2, EntityPlayer var3) {
      this.modelBlock.onBlockClicked(var1, var2, var3);
   }

   public static boolean isBlockStairs(Block var0) {
      return var0 instanceof BlockStairs;
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(HALF, (var1 & 4) > 0 ? BlockStairs.EnumHalf.TOP : BlockStairs.EnumHalf.BOTTOM);
      var2 = var2.withProperty(FACING, EnumFacing.getFront(5 - (var1 & 3)));
      return var2;
   }

   public int func_176305_g(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs.EnumHalf var5 = (BlockStairs.EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs.EnumHalf.TOP;
      IBlockState var7;
      Block var8;
      EnumFacing var9;
      if (var4 == EnumFacing.EAST) {
         var7 = var1.getBlockState(var2.offsetWest());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      } else if (var4 == EnumFacing.WEST) {
         var7 = var1.getBlockState(var2.offsetEast());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.NORTH && !isSameStair(var1, var2.offsetNorth(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.offsetSouth(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.SOUTH) {
         var7 = var1.getBlockState(var2.offsetNorth());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.WEST && !isSameStair(var1, var2.offsetWest(), var3)) {
               return var6 ? 2 : 1;
            }

            if (var9 == EnumFacing.EAST && !isSameStair(var1, var2.offsetEast(), var3)) {
               return var6 ? 1 : 2;
            }
         }
      } else if (var4 == EnumFacing.NORTH) {
         var7 = var1.getBlockState(var2.offsetSouth());
         var8 = var7.getBlock();
         if (isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            var9 = (EnumFacing)var7.getValue(FACING);
            if (var9 == EnumFacing.WEST && !isSameStair(var1, var2.offsetWest(), var3)) {
               return var6 ? 1 : 2;
            }

            if (var9 == EnumFacing.EAST && !isSameStair(var1, var2.offsetEast(), var3)) {
               return var6 ? 2 : 1;
            }
         }
      }

      return 0;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.modelBlock.randomDisplayTick(var1, var2, var3, var4);
   }

   public boolean isFullCube() {
      return false;
   }

   public static enum EnumShape implements IStringSerializable {
      OUTER_LEFT("OUTER_LEFT", 3, "outer_left");

      private final String field_176699_f;
      STRAIGHT("STRAIGHT", 0, "straight"),
      INNER_LEFT("INNER_LEFT", 1, "inner_left");

      private static final BlockStairs.EnumShape[] $VALUES = new BlockStairs.EnumShape[]{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT};
      private static final String __OBFID = "CL_00002061";
      INNER_RIGHT("INNER_RIGHT", 2, "inner_right");

      private static final BlockStairs.EnumShape[] ENUM$VALUES = new BlockStairs.EnumShape[]{STRAIGHT, INNER_LEFT, INNER_RIGHT, OUTER_LEFT, OUTER_RIGHT};
      OUTER_RIGHT("OUTER_RIGHT", 4, "outer_right");

      public String toString() {
         return this.field_176699_f;
      }

      private EnumShape(String var3, int var4, String var5) {
         this.field_176699_f = var5;
      }

      public String getName() {
         return this.field_176699_f;
      }
   }

   public static enum EnumHalf implements IStringSerializable {
      private static final String __OBFID = "CL_00002062";
      private static final BlockStairs.EnumHalf[] $VALUES = new BlockStairs.EnumHalf[]{TOP, BOTTOM};
      TOP("TOP", 0, "top"),
      BOTTOM("BOTTOM", 1, "bottom");

      private static final BlockStairs.EnumHalf[] ENUM$VALUES = new BlockStairs.EnumHalf[]{TOP, BOTTOM};
      private final String field_176709_c;

      public String getName() {
         return this.field_176709_c;
      }

      private EnumHalf(String var3, int var4, String var5) {
         this.field_176709_c = var5;
      }

      public String toString() {
         return this.field_176709_c;
      }
   }
}
