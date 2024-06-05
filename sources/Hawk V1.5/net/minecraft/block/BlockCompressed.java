package net.minecraft.block;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;

public class BlockCompressed extends Block {
   private static final String __OBFID = "CL_00000268";
   private final MapColor mapColor;

   public BlockCompressed(MapColor var1) {
      super(Material.iron);
      this.mapColor = var1;
      this.setCreativeTab(CreativeTabs.tabBlock);
   }

   public MapColor getMapColor(IBlockState var1) {
      return this.mapColor;
   }
}
