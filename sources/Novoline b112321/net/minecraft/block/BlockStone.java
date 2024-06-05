package net.minecraft.block;

import java.util.List;
import java.util.Random;
import net.minecraft.block.Block;
import net.minecraft.block.BlockStone$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;

public class BlockStone extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockStone$EnumType.class);

   public BlockStone() {
      super(Material.rock);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockStone$EnumType.STONE));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public String getLocalizedName() {
      return StatCollector.translateToLocal(this.getUnlocalizedName() + "." + BlockStone$EnumType.STONE.getUnlocalizedName() + ".name");
   }

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockStone$EnumType)var1.getValue(VARIANT)).func_181072_c();
   }

   public Item getItemDropped(IBlockState var1, Random var2, int var3) {
      return var1.getValue(VARIANT) == BlockStone$EnumType.STONE?Item.getItemFromBlock(Blocks.cobblestone):Item.getItemFromBlock(Blocks.stone);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockStone$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockStone$EnumType var7 : BlockStone$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockStone$EnumType.byMetadata(var1));
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockStone$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
