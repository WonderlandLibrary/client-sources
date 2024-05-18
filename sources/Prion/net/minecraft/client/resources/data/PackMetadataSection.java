package net.minecraft.client.resources.data;

import net.minecraft.util.IChatComponent;

public class PackMetadataSection implements IMetadataSection
{
  private final IChatComponent packDescription;
  private final int packFormat;
  private static final String __OBFID = "CL_00001112";
  
  public PackMetadataSection(IChatComponent p_i1034_1_, int p_i1034_2_)
  {
    packDescription = p_i1034_1_;
    packFormat = p_i1034_2_;
  }
  
  public IChatComponent func_152805_a()
  {
    return packDescription;
  }
  
  public int getPackFormat()
  {
    return packFormat;
  }
}
