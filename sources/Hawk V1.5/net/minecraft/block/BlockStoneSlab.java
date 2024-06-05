package net.minecraft.block;

import java.util.List;
import java.util.Random;
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
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.World;

public abstract class BlockStoneSlab extends BlockSlab {
   private static final String __OBFID = "CL_00000320";
   public static final PropertyEnum field_176556_M = PropertyEnum.create("variant", BlockStoneSlab.EnumType.class);
   public static final PropertyBool field_176555_b = PropertyBool.create("seamless");

   public BlockStoneSlab() {
      super(Material.rock);
      IBlockState var1 = this.blockState.getBaseState();
      if (this.isDouble()) {
         var1 = var1.withProperty(field_176555_b, false);
      } else {
         var1 = var1.withProperty(HALF_PROP, BlockSlab.EnumBlockHalf.BOTTOM);
      }

      this.setDefaultState(var1.withProperty(field_176556_M, BlockStoneSlab.EnumType.STONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public IProperty func_176551_l() {
      return field_176556_M;
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return Item.getItemFromBlock(Blocks.stone_slab);
   }

   public Item getItem(World var1, BlockPos var2) {
      return Item.getItemFromBlock(Blocks.stone_slab);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStoneSlab.EnumType)var1.getValue(field_176556_M)).func_176624_a();
   }

   public IBlockState getStateFromMeta(int var1) {
      IBlockState var2 = this.getDefaultState().withProperty(field_176556_M, BlockStoneSlab.EnumType.func_176625_a(var1 & 7));
      if (this.isDouble()) {
         var2 = var2.withProperty(field_176555_b, (var1 & 8) != 0);
      } else {
         var2 = var2.withProperty(HALF_PROP, (var1 & 8) == 0 ? BlockSlab.EnumBlockHalf.BOTTOM : BlockSlab.EnumBlockHalf.TOP);
      }

      return var2;
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      if (var1 != Item.getItemFromBlock(Blocks.double_stone_slab)) {
         BlockStoneSlab.EnumType[] var4 = BlockStoneSlab.EnumType.values();
         int var5 = var4.length;

         for(int var6 = 0; var6 < var5; ++var6) {
            BlockStoneSlab.EnumType var7 = var4[var6];
            if (var7 != BlockStoneSlab.EnumType.WOOD) {
               var3.add(new ItemStack(var1, 1, var7.func_176624_a()));
            }
         }
      }

   }

   public String getFullSlabName(int var1) {
      return String.valueOf((new StringBuilder(String.valueOf(super.getUnlocalizedName()))).append(".").append(BlockStoneSlab.EnumType.func_176625_a(var1).func_176627_c()));
   }

   protected BlockState createBlockState() {
      return this.isDouble() ? new BlockState(this, new IProperty[]{field_176555_b, field_176556_M}) : new BlockState(this, new IProperty[]{HALF_PROP, field_176556_M});
   }

   public Object func_176553_a(ItemStack var1) {
      return BlockStoneSlab.EnumType.func_176625_a(var1.getMetadata() & 7);
   }

   public int getMetaFromState(IBlockState var1) {
      byte var2 = 0;
      int var3 = var2 | ((BlockStoneSlab.EnumType)var1.getValue(field_176556_M)).func_176624_a();
      if (this.isDouble()) {
         if ((Boolean)var1.getValue(field_176555_b)) {
            var3 |= 8;
         }
      } else if (var1.getValue(HALF_PROP) == BlockSlab.EnumBlockHalf.TOP) {
         var3 |= 8;
      }

      return var3;
   }

   public static enum EnumType implements IStringSerializable {
      SAND("SAND", 1, 1, "sandstone", "sand"),
      COBBLESTONE("COBBLESTONE", 3, 3, "cobblestone", "cobble");

      private static final String __OBFID = "CL_00002056";
      SMOOTHBRICK("SMOOTHBRICK", 5, 5, "stone_brick", "smoothStoneBrick"),
      NETHERBRICK("NETHERBRICK", 6, 6, "nether_brick", "netherBrick");

      private static final BlockStoneSlab.EnumType[] field_176640_i = new BlockStoneSlab.EnumType[values().length];
      private static final BlockStoneSlab.EnumType[] ENUM$VALUES = new BlockStoneSlab.EnumType[]{STONE, SAND, WOOD, COBBLESTONE, BRICK, SMOOTHBRICK, NETHERBRICK, QUARTZ};
      STONE("STONE", 0, 0, "stone"),
      WOOD("WOOD", 2, 2, "wood_old", "wood");

      private final String field_176635_l;
      private final String field_176638_k;
      private final int field_176637_j;
      private static final BlockStoneSlab.EnumType[] $VALUES = new BlockStoneSlab.EnumType[]{STONE, SAND, WOOD, COBBLESTONE, BRICK, SMOOTHBRICK, NETHERBRICK, QUARTZ};
      BRICK("BRICK", 4, 4, "brick"),
      QUARTZ("QUARTZ", 7, 7, "quartz");

      private EnumType(String var3, int var4, int var5, String var6, String var7) {
         this.field_176637_j = var5;
         this.field_176638_k = var6;
         this.field_176635_l = var7;
      }

      private EnumType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      public int func_176624_a() {
         return this.field_176637_j;
      }

      static {
         BlockStoneSlab.EnumType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockStoneSlab.EnumType var3 = var0[var2];
            field_176640_i[var3.func_176624_a()] = var3;
         }

      }

      public static BlockStoneSlab.EnumType func_176625_a(int var0) {
         if (var0 < 0 || var0 >= field_176640_i.length) {
            var0 = 0;
         }

         return field_176640_i[var0];
      }

      public String func_176627_c() {
         return this.field_176635_l;
      }

      public String getName() {
         return this.field_176638_k;
      }

      public String toString() {
         return this.field_176638_k;
      }
   }
}
