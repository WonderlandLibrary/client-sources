package net.minecraft.block;

import java.util.List;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.IStringSerializable;

public class BlockSandStone extends Block
{
  public static final PropertyEnum field_176297_a = PropertyEnum.create("type", EnumType.class);
  private static final String __OBFID = "CL_00000304";
  
  public BlockSandStone()
  {
    super(Material.rock);
    setDefaultState(blockState.getBaseState().withProperty(field_176297_a, EnumType.DEFAULT));
    setCreativeTab(CreativeTabs.tabBlock);
  }
  



  public int damageDropped(IBlockState state)
  {
    return ((EnumType)state.getValue(field_176297_a)).func_176675_a();
  }
  



  public void getSubBlocks(Item itemIn, CreativeTabs tab, List list)
  {
    EnumType[] var4 = EnumType.values();
    int var5 = var4.length;
    
    for (int var6 = 0; var6 < var5; var6++)
    {
      EnumType var7 = var4[var6];
      list.add(new ItemStack(itemIn, 1, var7.func_176675_a()));
    }
  }
  



  public IBlockState getStateFromMeta(int meta)
  {
    return getDefaultState().withProperty(field_176297_a, EnumType.func_176673_a(meta));
  }
  



  public int getMetaFromState(IBlockState state)
  {
    return ((EnumType)state.getValue(field_176297_a)).func_176675_a();
  }
  
  protected BlockState createBlockState()
  {
    return new BlockState(this, new IProperty[] { field_176297_a });
  }
  
  public static enum EnumType implements IStringSerializable
  {
    DEFAULT("DEFAULT", 0, 0, "sandstone", "default"), 
    CHISELED("CHISELED", 1, 1, "chiseled_sandstone", "chiseled"), 
    SMOOTH("SMOOTH", 2, 2, "smooth_sandstone", "smooth");
    
    private static final EnumType[] field_176679_d;
    private final int field_176680_e;
    private final String field_176677_f;
    private final String field_176678_g;
    private static final EnumType[] $VALUES;
    private static final String __OBFID = "CL_00002068";
    
    private EnumType(String p_i45686_1_, int p_i45686_2_, int p_i45686_3_, String p_i45686_4_, String p_i45686_5_)
    {
      field_176680_e = p_i45686_3_;
      field_176677_f = p_i45686_4_;
      field_176678_g = p_i45686_5_;
    }
    
    public int func_176675_a()
    {
      return field_176680_e;
    }
    
    public String toString()
    {
      return field_176677_f;
    }
    
    public static EnumType func_176673_a(int p_176673_0_)
    {
      if ((p_176673_0_ < 0) || (p_176673_0_ >= field_176679_d.length))
      {
        p_176673_0_ = 0;
      }
      
      return field_176679_d[p_176673_0_];
    }
    
    public String getName()
    {
      return field_176677_f;
    }
    
    public String func_176676_c()
    {
      return field_176678_g;
    }
    
    static
    {
      field_176679_d = new EnumType[values().length];
      



      $VALUES = new EnumType[] { DEFAULT, CHISELED, SMOOTH };
      







































      EnumType[] var0 = values();
      int var1 = var0.length;
      
      for (int var2 = 0; var2 < var1; var2++)
      {
        EnumType var3 = var0[var2];
        field_176679_d[var3.func_176675_a()] = var3;
      }
    }
  }
}
