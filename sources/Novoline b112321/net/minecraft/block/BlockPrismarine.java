package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPrismarine$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BlockPrismarine extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockPrismarine$EnumType.class);
   public static final int ROUGH_META = BlockPrismarine$EnumType.ROUGH.getMetadata();
   public static final int BRICKS_META = BlockPrismarine$EnumType.BRICKS.getMetadata();
   public static final int DARK_META = BlockPrismarine$EnumType.DARK.getMetadata();

   public BlockPrismarine() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPrismarine$EnumType.ROUGH));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + BlockPrismarine$EnumType.ROUGH.getUnlocalizedName() + ".name");
   }

   public MapColor getMapColor(IBlockState var1) {
      return var1.getValue(VARIANT) == BlockPrismarine$EnumType.ROUGH?MapColor.cyanColor:MapColor.diamondColor;
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPrismarine$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockPrismarine$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockPrismarine$EnumType.byMetadata(var1));
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      var3.add(new ItemStack(var1, 1, ROUGH_META));
      var3.add(new ItemStack(var1, 1, BRICKS_META));
      var3.add(new ItemStack(var1, 1, DARK_META));
   }
}
