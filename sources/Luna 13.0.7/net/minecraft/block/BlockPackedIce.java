package net.minecraft.block;

import java.util.Random;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;

public class BlockPackedIce
  extends Block
{
  private static final String __OBFID = "CL_00000283";
  
  public BlockPackedIce()
  {
    super(Material.packedIce);
    this.slipperiness = 0.98F;
    setCreativeTab(CreativeTabs.tabBlock);
  }
  
  public int quantityDropped(Random random)
  {
    return 0;
  }
}
