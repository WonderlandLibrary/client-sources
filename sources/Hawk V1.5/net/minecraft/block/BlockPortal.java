package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.EnumWorldBlockLayer;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockPortal extends BlockBreakable {
   private static final String __OBFID = "CL_00000284";
   public static final PropertyEnum field_176550_a;

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176550_a, (var1 & 3) == 2 ? EnumFacing.Axis.Z : EnumFacing.Axis.X);
   }

   public static int func_176549_a(EnumFacing.Axis var0) {
      return var0 == EnumFacing.Axis.X ? 1 : (var0 == EnumFacing.Axis.Z ? 2 : 0);
   }

   public BlockPortal() {
      super(Material.portal, false);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176550_a, EnumFacing.Axis.X));
      this.setTickRandomly(true);
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176550_a});
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      EnumFacing.Axis var4 = null;
      IBlockState var5 = var1.getBlockState(var2);
      if (var1.getBlockState(var2).getBlock() == this) {
         var4 = (EnumFacing.Axis)var5.getValue(field_176550_a);
         if (var4 == null) {
            return false;
         }

         if (var4 == EnumFacing.Axis.Z && var3 != EnumFacing.EAST && var3 != EnumFacing.WEST) {
            return false;
         }

         if (var4 == EnumFacing.Axis.X && var3 != EnumFacing.SOUTH && var3 != EnumFacing.NORTH) {
            return false;
         }
      }

      boolean var6 = var1.getBlockState(var2.offsetWest()).getBlock() == this && var1.getBlockState(var2.offsetWest(2)).getBlock() != this;
      boolean var7 = var1.getBlockState(var2.offsetEast()).getBlock() == this && var1.getBlockState(var2.offsetEast(2)).getBlock() != this;
      boolean var8 = var1.getBlockState(var2.offsetNorth()).getBlock() == this && var1.getBlockState(var2.offsetNorth(2)).getBlock() != this;
      boolean var9 = var1.getBlockState(var2.offsetSouth()).getBlock() == this && var1.getBlockState(var2.offsetSouth(2)).getBlock() != this;
      boolean var10 = var6 || var7 || var4 == EnumFacing.Axis.X;
      boolean var11 = var8 || var9 || var4 == EnumFacing.Axis.Z;
      return var10 && var3 == EnumFacing.WEST ? true : (var10 && var3 == EnumFacing.EAST ? true : (var11 && var3 == EnumFacing.NORTH ? true : var11 && var3 == EnumFacing.SOUTH));
   }

   public void randomDisplayTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      if (var4.nextInt(100) == 0) {
         var1.playSound((double)var2.getX() + 0.5D, (double)var2.getY() + 0.5D, (double)var2.getZ() + 0.5D, "portal.portal", 0.5F, var4.nextFloat() * 0.4F + 0.8F, false);
      }

      for(int var5 = 0; var5 < 4; ++var5) {
         double var6 = (double)((float)var2.getX() + var4.nextFloat());
         double var8 = (double)((float)var2.getY() + var4.nextFloat());
         double var10 = (double)((float)var2.getZ() + var4.nextFloat());
         double var12 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         double var14 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         double var16 = ((double)var4.nextFloat() - 0.5D) * 0.5D;
         int var18 = var4.nextInt(2) * 2 - 1;
         if (var1.getBlockState(var2.offsetWest()).getBlock() != this && var1.getBlockState(var2.offsetEast()).getBlock() != this) {
            var6 = (double)var2.getX() + 0.5D + 0.25D * (double)var18;
            var12 = (double)(var4.nextFloat() * 2.0F * (float)var18);
         } else {
            var10 = (double)var2.getZ() + 0.5D + 0.25D * (double)var18;
            var16 = (double)(var4.nextFloat() * 2.0F * (float)var18);
         }

         var1.spawnParticle(EnumParticleTypes.PORTAL, var6, var8, var10, var12, var14, var16);
      }

   }

   public boolean isFullCube() {
      return false;
   }

   public int quantityDropped(Random var1) {
      return 0;
   }

   public void onEntityCollidedWithBlock(World var1, BlockPos var2, IBlockState var3, Entity var4) {
      if (var4.ridingEntity == null && var4.riddenByEntity == null) {
         var4.setInPortal();
      }

   }

   public int getMetaFromState(IBlockState var1) {
      return func_176549_a((EnumFacing.Axis)var1.getValue(field_176550_a));
   }

   public boolean func_176548_d(World var1, BlockPos var2) {
      BlockPortal.Size var3 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.X);
      if (var3.func_150860_b() && BlockPortal.Size.access$0(var3) == 0) {
         var3.func_150859_c();
         return true;
      } else {
         BlockPortal.Size var4 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.Z);
         if (var4.func_150860_b() && BlockPortal.Size.access$0(var4) == 0) {
            var4.func_150859_c();
            return true;
         } else {
            return false;
         }
      }
   }

   public void updateTick(World var1, BlockPos var2, IBlockState var3, Random var4) {
      super.updateTick(var1, var2, var3, var4);
      if (var1.provider.isSurfaceWorld() && var1.getGameRules().getGameRuleBooleanValue("doMobSpawning") && var4.nextInt(2000) < var1.getDifficulty().getDifficultyId()) {
         int var5 = var2.getY();

         BlockPos var6;
         for(var6 = var2; !World.doesBlockHaveSolidTopSurface(var1, var6) && var6.getY() > 0; var6 = var6.offsetDown()) {
         }

         if (var5 > 0 && !var1.getBlockState(var6.offsetUp()).getBlock().isNormalCube()) {
            Entity var7 = ItemMonsterPlacer.spawnCreature(var1, 57, (double)var6.getX() + 0.5D, (double)var6.getY() + 1.1D, (double)var6.getZ() + 0.5D);
            if (var7 != null) {
               var7.timeUntilPortal = var7.getPortalCooldown();
            }
         }
      }

   }

   public void onNeighborBlockChange(World var1, BlockPos var2, IBlockState var3, Block var4) {
      EnumFacing.Axis var5 = (EnumFacing.Axis)var3.getValue(field_176550_a);
      BlockPortal.Size var6;
      if (var5 == EnumFacing.Axis.X) {
         var6 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.X);
         if (!var6.func_150860_b() || BlockPortal.Size.access$0(var6) < BlockPortal.Size.access$1(var6) * BlockPortal.Size.access$2(var6)) {
            var1.setBlockState(var2, Blocks.air.getDefaultState());
         }
      } else if (var5 == EnumFacing.Axis.Z) {
         var6 = new BlockPortal.Size(var1, var2, EnumFacing.Axis.Z);
         if (!var6.func_150860_b() || BlockPortal.Size.access$0(var6) < BlockPortal.Size.access$1(var6) * BlockPortal.Size.access$2(var6)) {
            var1.setBlockState(var2, Blocks.air.getDefaultState());
         }
      }

   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      EnumFacing.Axis var3 = (EnumFacing.Axis)var1.getBlockState(var2).getValue(field_176550_a);
      float var4 = 0.125F;
      float var5 = 0.125F;
      if (var3 == EnumFacing.Axis.X) {
         var4 = 0.5F;
      }

      if (var3 == EnumFacing.Axis.Z) {
         var5 = 0.5F;
      }

      this.setBlockBounds(0.5F - var4, 0.0F, 0.5F - var5, 0.5F + var4, 1.0F, 0.5F + var5);
   }

   static {
      field_176550_a = PropertyEnum.create("axis", EnumFacing.Axis.class, (Enum[])(EnumFacing.Axis.X, EnumFacing.Axis.Z));
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      return null;
   }

   public EnumWorldBlockLayer getBlockLayer() {
      return EnumWorldBlockLayer.TRANSLUCENT;
   }

   public Item getItem(World var1, BlockPos var2) {
      return null;
   }

   public static class Size {
      private final EnumFacing field_150866_c;
      private static final String __OBFID = "CL_00000285";
      private final EnumFacing field_150863_d;
      private int field_150868_h;
      private int field_150864_e = 0;
      private final EnumFacing.Axis field_150865_b;
      private final World field_150867_a;
      private int field_150862_g;
      private BlockPos field_150861_f;

      protected int func_150858_a() {
         int var1;
         label63:
         for(this.field_150862_g = 0; this.field_150862_g < 21; ++this.field_150862_g) {
            for(var1 = 0; var1 < this.field_150868_h; ++var1) {
               BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1).offsetUp(this.field_150862_g);
               Block var3 = this.field_150867_a.getBlockState(var2).getBlock();
               if (!this.func_150857_a(var3)) {
                  break label63;
               }

               if (var3 == Blocks.portal) {
                  ++this.field_150864_e;
               }

               if (var1 == 0) {
                  var3 = this.field_150867_a.getBlockState(var2.offset(this.field_150863_d)).getBlock();
                  if (var3 != Blocks.obsidian) {
                     break label63;
                  }
               } else if (var1 == this.field_150868_h - 1) {
                  var3 = this.field_150867_a.getBlockState(var2.offset(this.field_150866_c)).getBlock();
                  if (var3 != Blocks.obsidian) {
                     break label63;
                  }
               }
            }
         }

         for(var1 = 0; var1 < this.field_150868_h; ++var1) {
            if (this.field_150867_a.getBlockState(this.field_150861_f.offset(this.field_150866_c, var1).offsetUp(this.field_150862_g)).getBlock() != Blocks.obsidian) {
               this.field_150862_g = 0;
               break;
            }
         }

         if (this.field_150862_g <= 21 && this.field_150862_g >= 3) {
            return this.field_150862_g;
         } else {
            this.field_150861_f = null;
            this.field_150868_h = 0;
            this.field_150862_g = 0;
            return 0;
         }
      }

      protected int func_180120_a(BlockPos var1, EnumFacing var2) {
         int var3;
         for(var3 = 0; var3 < 22; ++var3) {
            BlockPos var4 = var1.offset(var2, var3);
            if (!this.func_150857_a(this.field_150867_a.getBlockState(var4).getBlock()) || this.field_150867_a.getBlockState(var4.offsetDown()).getBlock() != Blocks.obsidian) {
               break;
            }
         }

         Block var5 = this.field_150867_a.getBlockState(var1.offset(var2, var3)).getBlock();
         return var5 == Blocks.obsidian ? var3 : 0;
      }

      protected boolean func_150857_a(Block var1) {
         return var1.blockMaterial == Material.air || var1 == Blocks.fire || var1 == Blocks.portal;
      }

      static int access$1(BlockPortal.Size var0) {
         return var0.field_150868_h;
      }

      public Size(World var1, BlockPos var2, EnumFacing.Axis var3) {
         this.field_150867_a = var1;
         this.field_150865_b = var3;
         if (var3 == EnumFacing.Axis.X) {
            this.field_150863_d = EnumFacing.EAST;
            this.field_150866_c = EnumFacing.WEST;
         } else {
            this.field_150863_d = EnumFacing.NORTH;
            this.field_150866_c = EnumFacing.SOUTH;
         }

         for(BlockPos var4 = var2; var2.getY() > var4.getY() - 21 && var2.getY() > 0 && this.func_150857_a(var1.getBlockState(var2.offsetDown()).getBlock()); var2 = var2.offsetDown()) {
         }

         int var5 = this.func_180120_a(var2, this.field_150863_d) - 1;
         if (var5 >= 0) {
            this.field_150861_f = var2.offset(this.field_150863_d, var5);
            this.field_150868_h = this.func_180120_a(this.field_150861_f, this.field_150866_c);
            if (this.field_150868_h < 2 || this.field_150868_h > 21) {
               this.field_150861_f = null;
               this.field_150868_h = 0;
            }
         }

         if (this.field_150861_f != null) {
            this.field_150862_g = this.func_150858_a();
         }

      }

      public void func_150859_c() {
         for(int var1 = 0; var1 < this.field_150868_h; ++var1) {
            BlockPos var2 = this.field_150861_f.offset(this.field_150866_c, var1);

            for(int var3 = 0; var3 < this.field_150862_g; ++var3) {
               this.field_150867_a.setBlockState(var2.offsetUp(var3), Blocks.portal.getDefaultState().withProperty(BlockPortal.field_176550_a, this.field_150865_b), 2);
            }
         }

      }

      static int access$0(BlockPortal.Size var0) {
         return var0.field_150864_e;
      }

      public boolean func_150860_b() {
         return this.field_150861_f != null && this.field_150868_h >= 2 && this.field_150868_h <= 21 && this.field_150862_g >= 3 && this.field_150862_g <= 21;
      }

      static int access$2(BlockPortal.Size var0) {
         return var0.field_150862_g;
      }
   }
}
