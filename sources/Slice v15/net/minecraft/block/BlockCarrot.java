package net.minecraft.block;

import net.minecraft.init.Items;

public class BlockCarrot extends BlockCrops
{
  private static final String __OBFID = "CL_00000212";
  
  public BlockCarrot() {}
  
  protected net.minecraft.item.Item getSeed() {
    return Items.carrot;
  }
  
  protected net.minecraft.item.Item getCrop()
  {
    return Items.carrot;
  }
}
