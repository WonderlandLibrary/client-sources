package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockWall extends Block {
   public static final PropertyBool field_176257_M = PropertyBool.create("east");
   public static final PropertyEnum field_176255_P = PropertyEnum.create("variant", BlockWall.EnumType.class);
   private static final String __OBFID = "CL_00000331";
   public static final PropertyBool field_176259_O = PropertyBool.create("west");
   public static final PropertyBool field_176256_a = PropertyBool.create("up");
   public static final PropertyBool field_176258_N = PropertyBool.create("south");
   public static final PropertyBool field_176254_b = PropertyBool.create("north");

   public int getMetaFromState(IBlockState var1) {
      return ((BlockWall.EnumType)var1.getValue(field_176255_P)).func_176657_a();
   }

   public BlockWall(Block var1) {
      super(var1.blockMaterial);
      this.setDefaultState(this.blockState.getBaseState().withProperty(field_176256_a, false).withProperty(field_176254_b, false).withProperty(field_176257_M, false).withProperty(field_176258_N, false).withProperty(field_176259_O, false).withProperty(field_176255_P, BlockWall.EnumType.NORMAL));
      this.setHardness(var1.blockHardness);
      this.setResistance(var1.blockResistance / 3.0F);
      this.setStepSound(var1.stepSound);
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public boolean shouldSideBeRendered(IBlockAccess var1, BlockPos var2, EnumFacing var3) {
      return var3 == EnumFacing.DOWN ? super.shouldSideBeRendered(var1, var2, var3) : true;
   }

   public boolean isPassable(IBlockAccess var1, BlockPos var2) {
      return false;
   }

   public boolean func_176253_e(IBlockAccess var1, BlockPos var2) {
      Block var3 = var1.getBlockState(var2).getBlock();
      return var3 == Blocks.barrier ? false : (var3 != this && !(var3 instanceof BlockFenceGate) ? (var3.blockMaterial.isOpaque() && var3.isFullCube() ? var3.blockMaterial != Material.gourd : false) : true);
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      BlockWall.EnumType[] var4 = BlockWall.EnumType.values();
      int var5 = var4.length;

      for(int var6 = 0; var6 < var5; ++var6) {
         BlockWall.EnumType var7 = var4[var6];
         var3.add(new ItemStack(var1, 1, var7.func_176657_a()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(field_176255_P, BlockWall.EnumType.func_176660_a(var1));
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      return var1.withProperty(field_176256_a, !var2.isAirBlock(var3.offsetUp())).withProperty(field_176254_b, this.func_176253_e(var2, var3.offsetNorth())).withProperty(field_176257_M, this.func_176253_e(var2, var3.offsetEast())).withProperty(field_176258_N, this.func_176253_e(var2, var3.offsetSouth())).withProperty(field_176259_O, this.func_176253_e(var2, var3.offsetWest()));
   }

   public void setBlockBoundsBasedOnState(IBlockAccess var1, BlockPos var2) {
      boolean var3 = this.func_176253_e(var1, var2.offsetNorth());
      boolean var4 = this.func_176253_e(var1, var2.offsetSouth());
      boolean var5 = this.func_176253_e(var1, var2.offsetWest());
      boolean var6 = this.func_176253_e(var1, var2.offsetEast());
      float var7 = 0.25F;
      float var8 = 0.75F;
      float var9 = 0.25F;
      float var10 = 0.75F;
      float var11 = 1.0F;
      if (var3) {
         var9 = 0.0F;
      }

      if (var4) {
         var10 = 1.0F;
      }

      if (var5) {
         var7 = 0.0F;
      }

      if (var6) {
         var8 = 1.0F;
      }

      if (var3 && var4 && !var5 && !var6) {
         var11 = 0.8125F;
         var7 = 0.3125F;
         var8 = 0.6875F;
      } else if (!var3 && !var4 && var5 && var6) {
         var11 = 0.8125F;
         var9 = 0.3125F;
         var10 = 0.6875F;
      }

      this.setBlockBounds(var7, 0.0F, var9, var8, var11, var10);
   }

   public AxisAlignedBB getCollisionBoundingBox(World var1, BlockPos var2, IBlockState var3) {
      this.setBlockBoundsBasedOnState(var1, var2);
      this.maxY = 1.5D;
      return super.getCollisionBoundingBox(var1, var2, var3);
   }

   public boolean isOpaqueCube() {
      return false;
   }

   public boolean isFullCube() {
      return false;
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockWall.EnumType)var1.getValue(field_176255_P)).func_176657_a();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{field_176256_a, field_176254_b, field_176257_M, field_176259_O, field_176258_N, field_176255_P});
   }

   public static enum EnumType implements IStringSerializable {
      private final int field_176663_d;
      private static final BlockWall.EnumType[] $VALUES = new BlockWall.EnumType[]{NORMAL, MOSSY};
      private final String field_176664_e;
      private String field_176661_f;
      MOSSY("MOSSY", 1, 1, "mossy_cobblestone", "mossy"),
      NORMAL("NORMAL", 0, 0, "cobblestone", "normal");

      private static final BlockWall.EnumType[] ENUM$VALUES = new BlockWall.EnumType[]{NORMAL, MOSSY};
      private static final BlockWall.EnumType[] field_176666_c = new BlockWall.EnumType[values().length];
      private static final String __OBFID = "CL_00002048";

      public static BlockWall.EnumType func_176660_a(int var0) {
         if (var0 < 0 || var0 >= field_176666_c.length) {
            var0 = 0;
         }

         return field_176666_c[var0];
      }

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176663_d = var5;
         this.field_176664_e = var6;
         this.field_176661_f = var7;
      }

      public String func_176659_c() {
         return this.field_176661_f;
      }

      public String getName() {
         return this.field_176664_e;
      }

      static {
         BlockWall.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockWall.EnumType var3 = var0[var2];
            field_176666_c[var3.func_176657_a()] = var3;
         }

      }

      public String toString() {
         return this.field_176664_e;
      }

      public int func_176657_a() {
         return this.field_176663_d;
      }
   }
}
