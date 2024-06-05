package net.minecraft.block;

import com.google.common.base.Predicate;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStairs$EnumHalf;
import net.minecraft.block.BlockStairs$EnumShape;
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
import net.minecraft.util.EnumFacing$Plane;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.Explosion;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockStairs extends Block {
   public static final PropertyDirection FACING = PropertyDirection.create("facing", (Predicate)EnumFacing$Plane.HORIZONTAL);
   public static final PropertyEnum HALF = PropertyEnum.create("half", BlockStairs$EnumHalf.class);
   public static final PropertyEnum SHAPE = PropertyEnum.create("shape", BlockStairs$EnumShape.class);
   private static final int[][] field_150150_a = new int[][]{{4, 5}, {5, 7}, {6, 7}, {4, 6}, {0, 1}, {1, 3}, {2, 3}, {0, 2}};
   private final Block modelBlock;
   private final IBlockState modelState;
   private boolean hasRaytraced;
   private int rayTracePass;

   protected BlockStairs(IBlockState var1) {
      super(var1.getBlock().blockMaterial);
      this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HALF, BlockStairs$EnumHalf.BOTTOM).withProperty(SHAPE, BlockStairs$EnumShape.STRAIGHT));
      this.modelBlock = var1.getBlock();
      this.modelState = var1;
      this.setHardness(this.modelBlock.blockHardness);
      this.setResistance(this.modelBlock.blockResistance / 3.0F);
      this.setStepSound(this.modelBlock.stepSound);
      this.setLightOpacity(255);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      if(this.hasRaytraced) {
         this.setBlockBounds(0.5F * (float)(this.rayTracePass % 2), 0.5F * (float)(this.rayTracePass / 4 % 2), 0.5F * (float)(this.rayTracePass / 2 % 2), 0.5F + 0.5F * (float)(this.rayTracePass % 2), 0.5F + 0.5F * (float)(this.rayTracePass / 4 % 2), 0.5F + 0.5F * (float)(this.rayTracePass / 2 % 2));
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
      }

   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public void setBaseCollisionBounds(IBlockAccess var1, BlockPos var2) {
      if(var1.getBlockState(var2).getValue(HALF) == BlockStairs$EnumHalf.TOP) {
         this.setBlockBounds(0.0F, 0.5F, 0.0F, 1.0F, 1.0F, 1.0F);
      } else {
         this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 0.5F, 1.0F);
      }

   }

   public static boolean isBlockStairs(Block var0) {
      return var0 instanceof BlockStairs;
   }

   public static boolean isSameStair(IBlockAccess var0, BlockPos var1, IBlockState var2) {
      IBlockState var3 = var0.getBlockState(var1);
      Block var4 = var3.getBlock();
      return isBlockStairs(var4) && var3.getValue(HALF) == var2.getValue(HALF) && var3.getValue(FACING) == var2.getValue(FACING);
   }

   public int func_176307_f(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs$EnumHalf var5 = (BlockStairs$EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs$EnumHalf.TOP;
      if(var4 == EnumFacing.EAST) {
         IBlockState var7 = var1.getBlockState(var2.east());
         Block var8 = var7.getBlock();
         if(isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            EnumFacing var9 = (EnumFacing)var7.getValue(FACING);
            if(var9 == EnumFacing.NORTH && !isSameStair(var1, var2.south(), var3)) {
               return 1;
            }

            if(var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.north(), var3)) {
               return 2;
            }
         }
      } else if(var4 == EnumFacing.WEST) {
         IBlockState var10 = var1.getBlockState(var2.west());
         Block var13 = var10.getBlock();
         if(isBlockStairs(var13) && var5 == var10.getValue(HALF)) {
            EnumFacing var16 = (EnumFacing)var10.getValue(FACING);
            if(var16 == EnumFacing.NORTH && !isSameStair(var1, var2.south(), var3)) {
               return 2;
            }

            if(var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.north(), var3)) {
               return 1;
            }
         }
      } else if(var4 == EnumFacing.SOUTH) {
         IBlockState var11 = var1.getBlockState(var2.south());
         Block var14 = var11.getBlock();
         if(isBlockStairs(var14) && var5 == var11.getValue(HALF)) {
            EnumFacing var17 = (EnumFacing)var11.getValue(FACING);
            if(var17 == EnumFacing.WEST && !isSameStair(var1, var2.east(), var3)) {
               return 2;
            }

            if(var17 == EnumFacing.EAST && !isSameStair(var1, var2.west(), var3)) {
               return 1;
            }
         }
      } else if(var4 == EnumFacing.NORTH) {
         IBlockState var12 = var1.getBlockState(var2.north());
         Block var15 = var12.getBlock();
         if(isBlockStairs(var15) && var5 == var12.getValue(HALF)) {
            EnumFacing var18 = (EnumFacing)var12.getValue(FACING);
            if(var18 == EnumFacing.WEST && !isSameStair(var1, var2.east(), var3)) {
               return 1;
            }

            if(var18 == EnumFacing.EAST && !isSameStair(var1, var2.west(), var3)) {
               return 2;
            }
         }
      }

      return 0;
   }

   public int func_176305_g(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs$EnumHalf var5 = (BlockStairs$EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs$EnumHalf.TOP;
      if(var4 == EnumFacing.EAST) {
         IBlockState var7 = var1.getBlockState(var2.west());
         Block var8 = var7.getBlock();
         if(isBlockStairs(var8) && var5 == var7.getValue(HALF)) {
            EnumFacing var9 = (EnumFacing)var7.getValue(FACING);
            if(var9 == EnumFacing.NORTH && !isSameStair(var1, var2.north(), var3)) {
               return 1;
            }

            if(var9 == EnumFacing.SOUTH && !isSameStair(var1, var2.south(), var3)) {
               return 2;
            }
         }
      } else if(var4 == EnumFacing.WEST) {
         IBlockState var10 = var1.getBlockState(var2.east());
         Block var13 = var10.getBlock();
         if(isBlockStairs(var13) && var5 == var10.getValue(HALF)) {
            EnumFacing var16 = (EnumFacing)var10.getValue(FACING);
            if(var16 == EnumFacing.NORTH && !isSameStair(var1, var2.north(), var3)) {
               return 2;
            }

            if(var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.south(), var3)) {
               return 1;
            }
         }
      } else if(var4 == EnumFacing.SOUTH) {
         IBlockState var11 = var1.getBlockState(var2.north());
         Block var14 = var11.getBlock();
         if(isBlockStairs(var14) && var5 == var11.getValue(HALF)) {
            EnumFacing var17 = (EnumFacing)var11.getValue(FACING);
            if(var17 == EnumFacing.WEST && !isSameStair(var1, var2.west(), var3)) {
               return 2;
            }

            if(var17 == EnumFacing.EAST && !isSameStair(var1, var2.east(), var3)) {
               return 1;
            }
         }
      } else if(var4 == EnumFacing.NORTH) {
         IBlockState var12 = var1.getBlockState(var2.south());
         Block var15 = var12.getBlock();
         if(isBlockStairs(var15) && var5 == var12.getValue(HALF)) {
            EnumFacing var18 = (EnumFacing)var12.getValue(FACING);
            if(var18 == EnumFacing.WEST && !isSameStair(var1, var2.west(), var3)) {
               return 1;
            }

            if(var18 == EnumFacing.EAST && !isSameStair(var1, var2.east(), var3)) {
               return 2;
            }
         }
      }

      return 0;
   }

   public boolean func_176306_h(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs$EnumHalf var5 = (BlockStairs$EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs$EnumHalf.TOP;
      float var7 = 0.5F;
      float var8 = 1.0F;
      var7 = 0.0F;
      var8 = 0.5F;
      float var9 = 0.0F;
      float var10 = 1.0F;
      float var11 = 0.0F;
      float var12 = 0.5F;
      boolean var13 = true;
      if(var4 == EnumFacing.EAST) {
         var9 = 0.5F;
         var12 = 1.0F;
         IBlockState var14 = var1.getBlockState(var2.east());
         Block var15 = var14.getBlock();
         if(isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            EnumFacing var16 = (EnumFacing)var14.getValue(FACING);
            if(var16 == EnumFacing.NORTH && !isSameStair(var1, var2.south(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if(var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.north(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if(var4 == EnumFacing.WEST) {
         var10 = 0.5F;
         var12 = 1.0F;
         IBlockState var19 = var1.getBlockState(var2.west());
         Block var22 = var19.getBlock();
         if(isBlockStairs(var22) && var5 == var19.getValue(HALF)) {
            EnumFacing var25 = (EnumFacing)var19.getValue(FACING);
            if(var25 == EnumFacing.NORTH && !isSameStair(var1, var2.south(), var3)) {
               var12 = 0.5F;
               var13 = false;
            } else if(var25 == EnumFacing.SOUTH && !isSameStair(var1, var2.north(), var3)) {
               var11 = 0.5F;
               var13 = false;
            }
         }
      } else if(var4 == EnumFacing.SOUTH) {
         var11 = 0.5F;
         var12 = 1.0F;
         IBlockState var20 = var1.getBlockState(var2.south());
         Block var23 = var20.getBlock();
         if(isBlockStairs(var23) && var5 == var20.getValue(HALF)) {
            EnumFacing var26 = (EnumFacing)var20.getValue(FACING);
            if(var26 == EnumFacing.WEST && !isSameStair(var1, var2.east(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if(var26 == EnumFacing.EAST && !isSameStair(var1, var2.west(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      } else if(var4 == EnumFacing.NORTH) {
         IBlockState var21 = var1.getBlockState(var2.north());
         Block var24 = var21.getBlock();
         if(isBlockStairs(var24) && var5 == var21.getValue(HALF)) {
            EnumFacing var27 = (EnumFacing)var21.getValue(FACING);
            if(var27 == EnumFacing.WEST && !isSameStair(var1, var2.east(), var3)) {
               var10 = 0.5F;
               var13 = false;
            } else if(var27 == EnumFacing.EAST && !isSameStair(var1, var2.west(), var3)) {
               var9 = 0.5F;
               var13 = false;
            }
         }
      }

      this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      return var13;
   }

   public boolean func_176304_i(IBlockAccess var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      EnumFacing var4 = (EnumFacing)var3.getValue(FACING);
      BlockStairs$EnumHalf var5 = (BlockStairs$EnumHalf)var3.getValue(HALF);
      boolean var6 = var5 == BlockStairs$EnumHalf.TOP;
      float var7 = 0.5F;
      float var8 = 1.0F;
      var7 = 0.0F;
      var8 = 0.5F;
      float var9 = 0.0F;
      float var10 = 0.5F;
      float var11 = 0.5F;
      float var12 = 1.0F;
      boolean var13 = false;
      if(var4 == EnumFacing.EAST) {
         IBlockState var14 = var1.getBlockState(var2.west());
         Block var15 = var14.getBlock();
         if(isBlockStairs(var15) && var5 == var14.getValue(HALF)) {
            EnumFacing var16 = (EnumFacing)var14.getValue(FACING);
            if(var16 == EnumFacing.NORTH && !isSameStair(var1, var2.north(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if(var16 == EnumFacing.SOUTH && !isSameStair(var1, var2.south(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if(var4 == EnumFacing.WEST) {
         IBlockState var19 = var1.getBlockState(var2.east());
         Block var22 = var19.getBlock();
         if(isBlockStairs(var22) && var5 == var19.getValue(HALF)) {
            var9 = 0.5F;
            var10 = 1.0F;
            EnumFacing var25 = (EnumFacing)var19.getValue(FACING);
            if(var25 == EnumFacing.NORTH && !isSameStair(var1, var2.north(), var3)) {
               var11 = 0.0F;
               var12 = 0.5F;
               var13 = true;
            } else if(var25 == EnumFacing.SOUTH && !isSameStair(var1, var2.south(), var3)) {
               var11 = 0.5F;
               var12 = 1.0F;
               var13 = true;
            }
         }
      } else if(var4 == EnumFacing.SOUTH) {
         IBlockState var20 = var1.getBlockState(var2.north());
         Block var23 = var20.getBlock();
         if(isBlockStairs(var23) && var5 == var20.getValue(HALF)) {
            var11 = 0.0F;
            var12 = 0.5F;
            EnumFacing var26 = (EnumFacing)var20.getValue(FACING);
            if(var26 == EnumFacing.WEST && !isSameStair(var1, var2.west(), var3)) {
               var13 = true;
            } else if(var26 == EnumFacing.EAST && !isSameStair(var1, var2.east(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      } else if(var4 == EnumFacing.NORTH) {
         IBlockState var21 = var1.getBlockState(var2.south());
         Block var24 = var21.getBlock();
         if(isBlockStairs(var24) && var5 == var21.getValue(HALF)) {
            EnumFacing var27 = (EnumFacing)var21.getValue(FACING);
            if(var27 == EnumFacing.WEST && !isSameStair(var1, var2.west(), var3)) {
               var13 = true;
            } else if(var27 == EnumFacing.EAST && !isSameStair(var1, var2.east(), var3)) {
               var9 = 0.5F;
               var10 = 1.0F;
               var13 = true;
            }
         }
      }

      this.setBlockBounds(var9, var7, var11, var10, var8, var12);
      return var13;
   }

   public void addCollisionBoxesToList(World var1, BlockPos var2, IBlockState var3, AxisAlignedBB var4, List var5, Entity var6) {
      this.setBaseCollisionBounds(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      boolean var7 = this.func_176306_h(var1, var2);
      super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      if(this.func_176304_i(var1, var2)) {
         super.addCollisionBoxesToList(var1, var2, var3, var4, var5, var6);
      }

      this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.modelBlock.randomDisplayTick(var1, var2, var3, var4);
   }

   public void onBlockClicked(World var1, BlockPos var2, EntityPlayer var3) {
      this.modelBlock.onBlockClicked(var1, var2, var3);
   }

   public void onBlockDestroyedByPlayer(World var1, BlockPos var2, IBlockState var3) {
      this.modelBlock.onBlockDestroyedByPlayer(var1, var2, var3);
   }

   public int getMixedBrightnessForBlock(IBlockAccess var1, BlockPos var2) {
      return this.modelBlock.getMixedBrightnessForBlock(var1, var2);
   }

   public float getExplosionResistance(Entity var1) {
      return this.modelBlock.getExplosionResistance(var1);
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return this.modelBlock.getBlockLayer();
   }

   public int tickRate(World var1) {
      return this.modelBlock.tickRate(var1);
   }

   public AxisAlignedBB getSelectedBoundingBox(World var1, BlockPos var2) {
      return this.modelBlock.getSelectedBoundingBox(var1, var2);
   }

   public Vec3 modifyAcceleration(World var1, BlockPos var2, Entity var3, Vec3 var4) {
      return this.modelBlock.modifyAcceleration(var1, var2, var3, var4);
   }

   public boolean isCollidable() {
      return this.modelBlock.isCollidable();
   }

   public boolean canCollideCheck(IBlockState var1, boolean var2) {
      return this.modelBlock.canCollideCheck(var1, var2);
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return this.modelBlock.canPlaceBlockAt(var1, var2);
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      this.onNeighborBlockChange(var1, var2, this.modelState, Blocks.air);
      this.modelBlock.onBlockAdded(var1, var2, this.modelState);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      this.modelBlock.breakBlock(var1, var2, this.modelState);
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, Entity var3) {
      this.modelBlock.onEntityCollidedWithBlock(var1, var2, var3);
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      this.modelBlock.updateTick(var1, var2, var3, var4);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      return this.modelBlock.onBlockActivated(var1, var2, this.modelState, var4, EnumFacing.DOWN, 0.0F, 0.0F, 0.0F);
   }

   public void onBlockDestroyedByExplosion(World var1, BlockPos var2, Explosion var3) {
      this.modelBlock.onBlockDestroyedByExplosion(var1, var2, var3);
   }

   public MapColor getMapColor(IBlockState var1) {
      return this.modelBlock.getMapColor(this.modelState);
   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      IBlockState var9 = super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8);
      var9 = var9.withProperty(FACING, var8.getHorizontalFacing()).withProperty(SHAPE, BlockStairs$EnumShape.STRAIGHT);
      return var3 == EnumFacing.DOWN || var3 != EnumFacing.UP && (double)var5 > 0.5D?var9.withProperty(HALF, BlockStairs$EnumHalf.TOP):var9.withProperty(HALF, BlockStairs$EnumHalf.BOTTOM);
   }

   public MovingObjectPosition collisionRayTrace(World var1, BlockPos var2, Vec3 var3, Vec3 var4) {
      MovingObjectPosition[] var5 = new MovingObjectPosition[8];
      IBlockState var6 = var1.getBlockState(var2);
      int var7 = ((EnumFacing)var6.getValue(FACING)).getHorizontalIndex();
      boolean var8 = var6.getValue(HALF) == BlockStairs$EnumHalf.TOP;
      int[] var9 = field_150150_a[var7 + 4];
      this.hasRaytraced = true;

      for(int var10 = 0; var10 < 8; ++var10) {
         this.rayTracePass = var10;
         if(Arrays.binarySearch(var9, var10) < 0) {
            var5[var10] = super.collisionRayTrace(var1, var2, var3, var4);
         }
      }

      for(int var13 : var9) {
         var5[var13] = null;
      }

      MovingObjectPosition var20 = null;
      double var21 = 0.0D;

      for(MovingObjectPosition var16 : var5) {
         double var17 = var16.hitVec.squareDistanceTo(var4);
         if(var17 > var21) {
            var20 = var16;
            var21 = var17;
         }
      }

      return var20;
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(HALF, (var1 & 4) > 0?BlockStairs$EnumHalf.TOP:BlockStairs$EnumHalf.BOTTOM);
      var2 = var2.withProperty(FACING, EnumFacing.getFront(5 - (var1 & 3)));
      return var2;
   }

   public int getMetaFromState(IBlockState var1) {
      int var2 = 0;
      if(var1.getValue(HALF) == BlockStairs$EnumHalf.TOP) {
         var2 |= 4;
      }

      var2 = var2 | 5 - ((EnumFacing)var1.getValue(FACING)).getIndex();
      return var2;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if(this.func_176306_h(var2, var3)) {
         switch(this.func_176305_g(var2, var3)) {
         case 0:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.STRAIGHT);
            break;
         case 1:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.INNER_RIGHT);
            break;
         case 2:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.INNER_LEFT);
         }
      } else {
         switch(this.func_176307_f(var2, var3)) {
         case 0:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.STRAIGHT);
            break;
         case 1:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.OUTER_RIGHT);
            break;
         case 2:
            var1 = var1.withProperty(SHAPE, BlockStairs$EnumShape.OUTER_LEFT);
         }
      }

      return var1;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{FACING, HALF, SHAPE});
   }
}
