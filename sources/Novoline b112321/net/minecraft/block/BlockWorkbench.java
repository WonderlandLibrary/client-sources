package net.minecraft.block;

import net.minecraft.block.Block;
import net.minecraft.block.BlockWorkbench$InterfaceCraftingTable;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.stats.StatList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class BlockWorkbench extends Block {
   protected BlockWorkbench() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabDecorations);
   }

   public boolean onBlockActivated(World var1, BlockPos var2, IBlockState var3, EntityPlayer var4, EnumFacing var5, float var6, float var7, float var8) {
      if(!var1.isRemote) {
         var4.displayGui(new BlockWorkbench$InterfaceCraftingTable(var1, var2));
         var4.triggerAchievement(StatList.field_181742_Z);
      }

      return true;
   }
}
