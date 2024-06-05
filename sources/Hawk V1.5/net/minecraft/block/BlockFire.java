package net.minecraft.block;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Random;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.WorldProviderEnd;

public class BlockFire extends Block {
   public static final PropertyInteger field_176542_R = PropertyInteger.create("upper", 0, 2);
   public static final PropertyBool field_176541_P = PropertyBool.create("south");
   private final Map field_149849_a = Maps.newIdentityHashMap();
   public static final PropertyBool field_176540_b = PropertyBool.create("flip");
   private static final String __OBFID = "CL_00000245";
   public static final PropertyBool field_176546_O = PropertyBool.create("east");
   public static final PropertyBool field_176544_M = PropertyBool.create("alt");
   public static final PropertyBool field_176545_N = PropertyBool.create("north");
   private final Map field_149848_b = Maps.newIdentityHashMap();
   public static final PropertyInteger field_176543_a = PropertyInteger.create("age", 0, 15);
   public static final PropertyBool field_176539_Q = PropertyBool.create("west");

   private void func_176536_a(World var1, BlockPos var2, int var3, Random var4, int var5) {
      int var6 = this.func_176532_c(var1.getBlockState(var2).getBlock());
      if (var4.nextInt(var3) < var6) {
         IBlockState var7 = var1.getBlockState(var2);
         if (var4.nextInt(var5 + 10) < 5 && !var1.func_175727_C(var2)) {
            int var8 = var5 + var4.nextInt(5) / 4;
            if (var8 > 15) {
               var8 = 15;
            }

            var1.setBlockState(var2, this.getDefaultState().withProperty(field_176543_a, var8), 3);
         } else {
            var1.setBlockToAir(var2);
         }

         if (var7.getBlock() == Blocks.tnt) {
            Blocks.tnt.onBlockDestroyedByPlayer(var1, var2, var7.withProperty(BlockTNT.field_176246_a, true));
         }
      }

   }

   protected boolean func_176537_d(World var1, BlockPos var2) {
      return var1.func_175727_C(var2) || var1.func_175727_C(var2.offsetWest()) || var1.func_175727_C(var2.offsetEast()) || var1.func_175727_C(var2.offsetNorth()) || var1.func_175727_C(var2.offsetSouth());
   }

   private boolean func_176533_e(World var1, BlockPos var2) {
      EnumFacing[] var3 = EnumFacing.values();
      int var4 = var3.length;

      for(int var5 = 0; var5 < var4; ++var5) {
         EnumFacing var6 = var3[var5];
         if (this.func_176535_e(var1, var2.offset(var6))) {
            return true;
         }
      }

      return false;
   }

   private int func_176534_d(Block var1) {
      Integer var2 = (Integer)this.field_149849_a.get(var1);
      return var2 == null ? 0 : var2;
   }

   public void func_180686_a(Block var1, int var2, int var3) {
      this.field_149849_a.put(var1, var2);
      this.field_149848_b.put(var1, var3);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(24) == 0) {
         var1.playSound((double)((float)var2.getX() + 0.5F), (double)((float)var2.getY() + 0.5F), (double)((float)var2.getZ() + 0.5F), "fire.fire", 1.0F + var4.nextFloat(), var4.nextFloat() * 0.7F + 0.3F, false);
      }

      int var5;
      double var6;
      double var8;
      double var10;
      if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) && !Blocks.fire.func_176535_e(var1, var2.offsetDown())) {
         if (Blocks.fire.func_176535_e(var1, var2.offsetWest())) {
            for(var5 = 0; var5 < 2; ++var5) {
               var6 = (double)var2.getX() + var4.nextDouble() * 0.10000000149011612D;
               var8 = (double)var2.getY() + var4.nextDouble();
               var10 = (double)var2.getZ() + var4.nextDouble();
               var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.fire.func_176535_e(var1, var2.offsetEast())) {
            for(var5 = 0; var5 < 2; ++var5) {
               var6 = (double)(var2.getX() + 1) - var4.nextDouble() * 0.10000000149011612D;
               var8 = (double)var2.getY() + var4.nextDouble();
               var10 = (double)var2.getZ() + var4.nextDouble();
               var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.fire.func_176535_e(var1, var2.offsetNorth())) {
            for(var5 = 0; var5 < 2; ++var5) {
               var6 = (double)var2.getX() + var4.nextDouble();
               var8 = (double)var2.getY() + var4.nextDouble();
               var10 = (double)var2.getZ() + var4.nextDouble() * 0.10000000149011612D;
               var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.fire.func_176535_e(var1, var2.offsetSouth())) {
            for(var5 = 0; var5 < 2; ++var5) {
               var6 = (double)var2.getX() + var4.nextDouble();
               var8 = (double)var2.getY() + var4.nextDouble();
               var10 = (double)(var2.getZ() + 1) - var4.nextDouble() * 0.10000000149011612D;
               var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
         }

         if (Blocks.fire.func_176535_e(var1, var2.offsetUp())) {
            for(var5 = 0; var5 < 2; ++var5) {
               var6 = (double)var2.getX() + var4.nextDouble();
               var8 = (double)(var2.getY() + 1) - var4.nextDouble() * 0.10000000149011612D;
               var10 = (double)var2.getZ() + var4.nextDouble();
               var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
            }
         }
      } else {
         for(var5 = 0; var5 < 3; ++var5) {
            var6 = (double)var2.getX() + var4.nextDouble();
            var8 = (double)var2.getY() + var4.nextDouble() * 0.5D + 0.5D;
            var10 = (double)var2.getZ() + var4.nextDouble();
            var1.spawnParticle(EnumParticleTypes.SMOKE_LARGE, var6, var8, var10, 0.0D, 0.0D, 0.0D);
         }
      }

   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var1.getGameRules().getGameRuleBooleanValue("doFireTick")) {
         if (!this.canPlaceBlockAt(var1, var2)) {
            var1.setBlockToAir(var2);
         }

         Block var5 = var1.getBlockState(var2.offsetDown()).getBlock();
         boolean var6 = var5 == Blocks.netherrack;
         if (var1.provider instanceof WorldProviderEnd && var5 == Blocks.bedrock) {
            var6 = true;
         }

         if (!var6 && var1.isRaining() && this.func_176537_d(var1, var2)) {
            var1.setBlockToAir(var2);
         } else {
            int var7 = (Integer)var3.getValue(field_176543_a);
            if (var7 < 15) {
               var3 = var3.withProperty(field_176543_a, var7 + var4.nextInt(3) / 2);
               var1.setBlockState(var2, var3, 4);
            }

            var1.scheduleUpdate(var2, this, this.tickRate(var1) + var4.nextInt(10));
            if (!var6) {
               if (!this.func_176533_e(var1, var2)) {
                  if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) || var7 > 3) {
                     var1.setBlockToAir(var2);
                  }

                  return;
               }

               if (!this.func_176535_e(var1, var2.offsetDown()) && var7 == 15 && var4.nextInt(4) == 0) {
                  var1.setBlockToAir(var2);
                  return;
               }
            }

            boolean var8 = var1.func_180502_D(var2);
            byte var9 = 0;
            if (var8) {
               var9 = -50;
            }

            this.func_176536_a(var1, var2.offsetEast(), 300 + var9, var4, var7);
            this.func_176536_a(var1, var2.offsetWest(), 300 + var9, var4, var7);
            this.func_176536_a(var1, var2.offsetDown(), 250 + var9, var4, var7);
            this.func_176536_a(var1, var2.offsetUp(), 250 + var9, var4, var7);
            this.func_176536_a(var1, var2.offsetNorth(), 300 + var9, var4, var7);
            this.func_176536_a(var1, var2.offsetSouth(), 300 + var9, var4, var7);

            for(int var10 = -1; var10 <= 1; ++var10) {
               for(int var11 = -1; var11 <= 1; ++var11) {
                  for(int var12 = -1; var12 <= 4; ++var12) {
                     if (var10 != 0 || var12 != 0 || var11 != 0) {
                        int var13 = 100;
                        if (var12 > 1) {
                           var13 += (var12 - 1) * 100;
                        }

                        BlockPos var14 = var2.add(var10, var12, var11);
                        int var15 = this.func_176538_m(var1, var14);
                        if (var15 > 0) {
                           int var16 = (var15 + 40 + var1.getDifficulty().getDifficultyId() * 7) / (var7 + 30);
                           if (var8) {
                              var16 /= 2;
                           }

                           if (var16 > 0 && var4.nextInt(var13) <= var16 && (!var1.isRaining() || !this.func_176537_d(var1, var14))) {
                              int var17 = var7 + var4.nextInt(5) / 4;
                              if (var17 > 15) {
                                 var17 = 15;
                              }

                              var1.setBlockState(var14, var3.withProperty(field_176543_a, var17), 3);
                           }
                        }
                     }
                  }
               }
            }
         }
      }

   }

   public boolean requiresUpdates() {
      return false;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176543_a, field_176545_N, field_176546_O, field_176541_P, field_176539_Q, field_176542_R, field_176540_b, field_176544_M});
   }

   public boolean canPlaceBlockAt(World var1, BlockPos var2) {
      return World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) || this.func_176533_e(var1, var2);
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176543_a, var1);
   }

   protected BlockFire() {
      super(Material.fire);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176543_a, 0).withProperty(field_176540_b, false).withProperty(field_176544_M, false).withProperty(field_176545_N, false).withProperty(field_176546_O, false).withProperty(field_176541_P, false).withProperty(field_176539_Q, false).withProperty(field_176542_R, 0));
      this.setTickRandomly(true);
   }

   private int func_176538_m(World var1, BlockPos var2) {
      if (!var1.isAirBlock(var2)) {
         return 0;
      } else {
         int var3 = 0;
         EnumFacing[] var4 = EnumFacing.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            EnumFacing var7 = var4[var6];
            var3 = Math.max(this.func_176534_d(var1.getBlockState(var2.offset(var7)).getBlock()), var3);
         }

         return var3;
      }
   }

   public boolean isFullCube() {
      return false;
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      int var4 = var3.getX();
      int var5 = var3.getY();
      int var6 = var3.getZ();
      if (!World.doesBlockHaveSolidTopSurface(var2, var3.offsetDown()) && !Blocks.fire.func_176535_e(var2, var3.offsetDown())) {
         boolean var7 = (var4 + var5 + var6 & 1) == 1;
         boolean var8 = (var4 / 2 + var5 / 2 + var6 / 2 & 1) == 1;
         int var9 = 0;
         if (this.func_176535_e(var2, var3.offsetUp())) {
            var9 = var7 ? 1 : 2;
         }

         return var1.withProperty(field_176545_N, this.func_176535_e(var2, var3.offsetNorth())).withProperty(field_176546_O, this.func_176535_e(var2, var3.offsetEast())).withProperty(field_176541_P, this.func_176535_e(var2, var3.offsetSouth())).withProperty(field_176539_Q, this.func_176535_e(var2, var3.offsetWest())).withProperty(field_176542_R, var9).withProperty(field_176540_b, var8).withProperty(field_176544_M, var7);
      } else {
         return this.getDefaultState();
      }
   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) && !this.func_176533_e(var1, var2)) {
         var1.setBlockToAir(var2);
      }

   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.CUTOUT;
   }

   public MapColor getMapColor(IBlockState var1) {
      return MapColor.tntColor;
   }

   public boolean isCollidable() {
      return false;
   }

   public boolean func_176535_e(IBlockAccess var1, BlockPos var2) {
      return this.func_176534_d(var1.getBlockState(var2).getBlock()) > 0;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public int tickRate(World var1) {
      return 30;
   }

   public boolean isOpaqueCube() {
      return false;
   }

   private int func_176532_c(Block var1) {
      Integer var2 = (Integer)this.field_149848_b.get(var1);
      return var2 == null ? 0 : var2;
   }

   public void onBlockAdded(World var1, BlockPos var2, IBlockState var3) {
      if (var1.provider.getDimensionId() > 0 || !Blocks.portal.func_176548_d(var1, var2)) {
         if (!World.doesBlockHaveSolidTopSurface(var1, var2.offsetDown()) && !this.func_176533_e(var1, var2)) {
            var1.setBlockToAir(var2);
         } else {
            var1.scheduleUpdate(var2, this, this.tickRate(var1) + var1.rand.nextInt(10));
         }
      }

   }

   public static void func_149843_e() {
      Blocks.fire.func_180686_a(Blocks.planks, 5, 20);
      Blocks.fire.func_180686_a(Blocks.double_wooden_slab, 5, 20);
      Blocks.fire.func_180686_a(Blocks.wooden_slab, 5, 20);
      Blocks.fire.func_180686_a(Blocks.oak_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.spruce_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.birch_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.jungle_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.dark_oak_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.acacia_fence_gate, 5, 20);
      Blocks.fire.func_180686_a(Blocks.oak_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.spruce_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.birch_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.jungle_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.dark_oak_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.acacia_fence, 5, 20);
      Blocks.fire.func_180686_a(Blocks.oak_stairs, 5, 20);
      Blocks.fire.func_180686_a(Blocks.birch_stairs, 5, 20);
      Blocks.fire.func_180686_a(Blocks.spruce_stairs, 5, 20);
      Blocks.fire.func_180686_a(Blocks.jungle_stairs, 5, 20);
      Blocks.fire.func_180686_a(Blocks.log, 5, 5);
      Blocks.fire.func_180686_a(Blocks.log2, 5, 5);
      Blocks.fire.func_180686_a(Blocks.leaves, 30, 60);
      Blocks.fire.func_180686_a(Blocks.leaves2, 30, 60);
      Blocks.fire.func_180686_a(Blocks.bookshelf, 30, 20);
      Blocks.fire.func_180686_a(Blocks.tnt, 15, 100);
      Blocks.fire.func_180686_a(Blocks.tallgrass, 60, 100);
      Blocks.fire.func_180686_a(Blocks.double_plant, 60, 100);
      Blocks.fire.func_180686_a(Blocks.yellow_flower, 60, 100);
      Blocks.fire.func_180686_a(Blocks.red_flower, 60, 100);
      Blocks.fire.func_180686_a(Blocks.deadbush, 60, 100);
      Blocks.fire.func_180686_a(Blocks.wool, 30, 60);
      Blocks.fire.func_180686_a(Blocks.vine, 15, 100);
      Blocks.fire.func_180686_a(Blocks.coal_block, 5, 5);
      Blocks.fire.func_180686_a(Blocks.hay_block, 60, 20);
      Blocks.fire.func_180686_a(Blocks.carpet, 60, 20);
   }

   public int getMetaFromState(IBlockState var1) {
      return (Integer)var1.getValue(field_176543_a);
   }
}
