package net.minecraft.block;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockLog$EnumAxis;
import net.minecraft.block.BlockRotatedPillar;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public abstract class BlockLog extends BlockRotatedPillar {
   public static final PropertyEnum LOG_AXIS = PropertyEnum.create("axis", BlockLog$EnumAxis.class);

   public BlockLog() {
      super(Material.wood);
      this.setCreativeTab(CreativeTabs.tabBlock);
      this.setHardness(2.0F);
      this.setStepSound(soundTypeWood);
   }

   public void breakBlock(World var1, BlockPos var2, IBlockState var3) {
      boolean var4 = true;
      boolean var5 = true;
      if(var1.isAreaLoaded(var2.a(-5, -5, -5), var2.a(5, 5, 5))) {
         for(BlockPos var7 : BlockPos.getAllInBox(var2.a(-4, -4, -4), var2.a(4, 4, 4))) {
            IBlockState var8 = var1.getBlockState(var7);
            if(var8.getBlock().getMaterial() == Material.leaves && !((Boolean)var8.getValue(BlockLeaves.CHECK_DECAY)).booleanValue()) {
               var1.setBlockState(var7, var8.withProperty(BlockLeaves.CHECK_DECAY, Boolean.TRUE), 4);
            }
         }
      }

   }

   public IBlockState onBlockPlaced(World var1, BlockPos var2, EnumFacing var3, float var4, float var5, float var6, int var7, EntityLivingBase var8) {
      return super.onBlockPlaced(var1, var2, var3, var4, var5, var6, var7, var8).withProperty(LOG_AXIS, BlockLog$EnumAxis.fromFacingAxis(var3.getAxis()));
   }
}
