package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.StatCollector;

public class BlockPrismarine extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockPrismarine.EnumType.class);
   public static final int ROUGH_META = BlockPrismarine.EnumType.ROUGH.getMetadata();
   public static final int BRICKS_META = BlockPrismarine.EnumType.BRICKS.getMetadata();
   public static final int DARK_META = BlockPrismarine.EnumType.DARK.getMetadata();

   public BlockPrismarine() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPrismarine.EnumType.ROUGH));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + BlockPrismarine.EnumType.ROUGH.getUnlocalizedName() + ".name");
   }

   public int getMetaFromState(IBlockState state) {
      return ((BlockPrismarine.EnumType)state.getValue(VARIANT)).getMetadata();
   }

   public int damageDropped(IBlockState state) {
      return ((BlockPrismarine.EnumType)state.getValue(VARIANT)).getMetadata();
   }

   public IBlockState getStateFromMeta(int meta) {
      return this.getDefaultState().withProperty(VARIANT, BlockPrismarine.EnumType.byMetadata(meta));
   }

   public MapColor getMapColor(IBlockState state) {
      return state.getValue(VARIANT) == BlockPrismarine.EnumType.ROUGH?MapColor.cyanColor:MapColor.diamondColor;
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }

   public void getSubBlocks(Item itemIn, CreativeTabs tab, List list) {
      list.add(new ItemStack(itemIn, 1, ROUGH_META));
      list.add(new ItemStack(itemIn, 1, BRICKS_META));
      list.add(new ItemStack(itemIn, 1, DARK_META));
   }

   public static enum EnumType implements IStringSerializable {
      ROUGH(0, "prismarine", "rough"),
      BRICKS(1, "prismarine_bricks", "bricks"),
      DARK(2, "dark_prismarine", "dark");

      private static final BlockPrismarine.EnumType[] META_LOOKUP = new BlockPrismarine.EnumType[values().length];
      private final int meta;
      private final String name;
      private final String unlocalizedName;

      private EnumType(int meta, String name, String unlocalizedName) {
         this.meta = meta;
         this.name = name;
         this.unlocalizedName = unlocalizedName;
      }

      static {
         for(BlockPrismarine.EnumType blockprismarine$enumtype : values()) {
            META_LOOKUP[blockprismarine$enumtype.getMetadata()] = blockprismarine$enumtype;
         }

      }

      public String toString() {
         return this.name;
      }

      public String getName() {
         return this.name;
      }

      public int getMetadata() {
         return this.meta;
      }

      public String getUnlocalizedName() {
         return this.unlocalizedName;
      }

      public static BlockPrismarine.EnumType byMetadata(int meta) {
         if(meta < 0 || meta >= META_LOOKUP.length) {
            meta = 0;
         }

         return META_LOOKUP[meta];
      }
   }
}
