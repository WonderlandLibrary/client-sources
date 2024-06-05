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
import net.minecraft.util.BlockPos;
import net.minecraft.util.IStringSerializable;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockDirt extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockDirt.DirtType.class);
   private static final String __OBFID = "CL_00000228";
   public static final PropertyBool SNOWY = PropertyBool.create("snowy");

   public int damageDropped(IBlockState var1) {
      BlockDirt.DirtType var2 = (BlockDirt.DirtType)var1.getValue(VARIANT);
      if (var2 == BlockDirt.DirtType.PODZOL) {
         var2 = BlockDirt.DirtType.DIRT;
      }

      return var2.getMetadata();
   }

   public int getDamageValue(World var1, BlockPos var2) {
      IBlockState var3 = var1.getBlockState(var2);
      return var3.getBlock() != this ? 0 : ((BlockDirt.DirtType)var3.getValue(VARIANT)).getMetadata();
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockDirt.DirtType.byMetadata(var1));
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT, SNOWY});
   }

   public IBlockState getActualState(IBlockState var1, IBlockAccess var2, BlockPos var3) {
      if (var1.getValue(VARIANT) == BlockDirt.DirtType.PODZOL) {
         Block var4 = var2.getBlockState(var3.offsetUp()).getBlock();
         var1 = var1.withProperty(SNOWY, var4 == Blocks.snow || var4 == Blocks.snow_layer);
      }

      return var1;
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.DIRT.getMetadata()));
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.COARSE_DIRT.getMetadata()));
      var3.add(new ItemStack(this, 1, BlockDirt.DirtType.PODZOL.getMetadata()));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockDirt.DirtType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockDirt() {
      super(Material.ground);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockDirt.DirtType.DIRT).withProperty(SNOWY, false));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public static enum DirtType implements IStringSerializable {
      private final int metadata;
      private static final BlockDirt.DirtType[] $VALUES = new BlockDirt.DirtType[]{DIRT, COARSE_DIRT, PODZOL};
      DIRT("DIRT", 0, 0, "dirt", "default"),
      PODZOL("PODZOL", 2, 2, "podzol"),
      COARSE_DIRT("COARSE_DIRT", 1, 1, "coarse_dirt", "coarse");

      private static final String __OBFID = "CL_00002125";
      private final String unlocalizedName;
      private final String name;
      private static final BlockDirt.DirtType[] METADATA_LOOKUP = new BlockDirt.DirtType[values().length];
      private static final BlockDirt.DirtType[] ENUM$VALUES = new BlockDirt.DirtType[]{DIRT, COARSE_DIRT, PODZOL};

      static {
         BlockDirt.DirtType[] var0 = values();
         int var1 = var0.length;

         for(int var2 = 0; var2 < var1; ++var2) {
            BlockDirt.DirtType var3 = var0[var2];
            METADATA_LOOKUP[var3.getMetadata()] = var3;
         }

      }

      public static BlockDirt.DirtType byMetadata(int var0) {
         if (var0 < 0 || var0 >= METADATA_LOOKUP.length) {
            var0 = 0;
         }

         return METADATA_LOOKUP[var0];
      }

      private DirtType(String var3, int var4, int var5, String var6, String var7) {
         this.metadata = var5;
         this.name = var6;
         this.unlocalizedName = var7;
      }

      public String getName() {
         return this.name;
      }

      private DirtType(String var3, int var4, int var5, String var6) {
         this(var3, var4, var5, var6, var6);
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      public String toString() {
         return this.name;
      }

      public int getMetadata() {
         return this.metadata;
      }
   }
}
