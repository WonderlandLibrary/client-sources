package net.minecraft.block;

import java.util.List;
import net.minecraft.block.Block;
import net.minecraft.block.BlockPlanks$EnumType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

public class BlockPlanks extends Block {
   public static final PropertyEnum VARIANT = PropertyEnum.create("variant", BlockPlanks$EnumType.class);

   public BlockPlanks() {
      super(Material.wood);
      this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, BlockPlanks$EnumType.OAK));
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public int damageDropped(IBlockState var1) {
      return ((BlockPlanks$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   public void getSubBlocks(Item var1, CreativeTabs var2, List var3) {
      for(BlockPlanks$EnumType var7 : BlockPlanks$EnumType.values()) {
         var3.add(new ItemStack(var1, 1, var7.getMetadata()));
      }

   }

   public IBlockState getStateFromMeta(int var1) {
      return this.getDefaultState().withProperty(VARIANT, BlockPlanks$EnumType.byMetadata(var1));
   }

   public MapColor getMapColor(IBlockState var1) {
      return ((BlockPlanks$EnumType)var1.getValue(VARIANT)).func_181070_c();
   }

   public int getMetaFromState(IBlockState var1) {
      return ((BlockPlanks$EnumType)var1.getValue(VARIANT)).getMetadata();
   }

   protected BlockState createBlockState() {
      return new BlockState(this, new IProperty[]{VARIANT});
   }
}
