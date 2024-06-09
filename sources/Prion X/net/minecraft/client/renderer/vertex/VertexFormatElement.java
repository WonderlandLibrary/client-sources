package net.minecraft.client.renderer.vertex;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class VertexFormatElement
{
  private static final Logger field_177381_a = ;
  private final EnumType field_177379_b;
  private final EnumUseage field_177380_c;
  private int field_177377_d;
  private int field_177378_e;
  private int field_177376_f;
  private static final String __OBFID = "CL_00002399";
  
  public VertexFormatElement(int p_i46096_1_, EnumType p_i46096_2_, EnumUseage p_i46096_3_, int p_i46096_4_)
  {
    if (!func_177372_a(p_i46096_1_, p_i46096_3_))
    {
      field_177381_a.warn("Multiple vertex elements of the same type other than UVs are not supported. Forcing type to UV.");
      field_177380_c = EnumUseage.UV;
    }
    else
    {
      field_177380_c = p_i46096_3_;
    }
    
    field_177379_b = p_i46096_2_;
    field_177377_d = p_i46096_1_;
    field_177378_e = p_i46096_4_;
    field_177376_f = 0;
  }
  
  public void func_177371_a(int p_177371_1_)
  {
    field_177376_f = p_177371_1_;
  }
  
  public int func_177373_a()
  {
    return field_177376_f;
  }
  
  private final boolean func_177372_a(int p_177372_1_, EnumUseage p_177372_2_)
  {
    return (p_177372_1_ == 0) || (p_177372_2_ == EnumUseage.UV);
  }
  
  public final EnumType func_177367_b()
  {
    return field_177379_b;
  }
  
  public final EnumUseage func_177375_c()
  {
    return field_177380_c;
  }
  
  public final int func_177370_d()
  {
    return field_177378_e;
  }
  
  public final int func_177369_e()
  {
    return field_177377_d;
  }
  
  public String toString()
  {
    return field_177378_e + "," + field_177380_c.func_177384_a() + "," + field_177379_b.func_177396_b();
  }
  
  public final int func_177368_f()
  {
    return field_177379_b.func_177395_a() * field_177378_e;
  }
  
  public final boolean func_177374_g()
  {
    return field_177380_c == EnumUseage.POSITION;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (this == p_equals_1_)
    {
      return true;
    }
    if ((p_equals_1_ != null) && (getClass() == p_equals_1_.getClass()))
    {
      VertexFormatElement var2 = (VertexFormatElement)p_equals_1_;
      return field_177378_e == field_177378_e;
    }
    

    return false;
  }
  

  public int hashCode()
  {
    int var1 = field_177379_b.hashCode();
    var1 = 31 * var1 + field_177380_c.hashCode();
    var1 = 31 * var1 + field_177377_d;
    var1 = 31 * var1 + field_177378_e;
    var1 = 31 * var1 + field_177376_f;
    return var1;
  }
  
  public static enum EnumType
  {
    FLOAT("FLOAT", 0, 4, "Float", 5126), 
    UBYTE("UBYTE", 1, 1, "Unsigned Byte", 5121), 
    BYTE("BYTE", 2, 1, "Byte", 5120), 
    USHORT("USHORT", 3, 2, "Unsigned Short", 5123), 
    SHORT("SHORT", 4, 2, "Short", 5122), 
    UINT("UINT", 5, 4, "Unsigned Int", 5125), 
    INT("INT", 6, 4, "Int", 5124);
    
    private final int field_177407_h;
    private final String field_177408_i;
    private final int field_177405_j;
    private static final EnumType[] $VALUES = { FLOAT, UBYTE, BYTE, USHORT, SHORT, UINT, INT };
    private static final String __OBFID = "CL_00002398";
    
    private EnumType(String p_i46095_1_, int p_i46095_2_, int p_i46095_3_, String p_i46095_4_, int p_i46095_5_)
    {
      field_177407_h = p_i46095_3_;
      field_177408_i = p_i46095_4_;
      field_177405_j = p_i46095_5_;
    }
    
    public int func_177395_a()
    {
      return field_177407_h;
    }
    
    public String func_177396_b()
    {
      return field_177408_i;
    }
    
    public int func_177397_c()
    {
      return field_177405_j;
    }
  }
  
  public static enum EnumUseage
  {
    POSITION("POSITION", 0, "Position"), 
    NORMAL("NORMAL", 1, "Normal"), 
    COLOR("COLOR", 2, "Vertex Color"), 
    UV("UV", 3, "UV"), 
    MATRIX("MATRIX", 4, "Bone Matrix"), 
    BLEND_WEIGHT("BLEND_WEIGHT", 5, "Blend Weight"), 
    PADDING("PADDING", 6, "Padding");
    
    private final String field_177392_h;
    private static final EnumUseage[] $VALUES = { POSITION, NORMAL, COLOR, UV, MATRIX, BLEND_WEIGHT, PADDING };
    private static final String __OBFID = "CL_00002397";
    
    private EnumUseage(String p_i46094_1_, int p_i46094_2_, String p_i46094_3_)
    {
      field_177392_h = p_i46094_3_;
    }
    
    public String func_177384_a()
    {
      return field_177392_h;
    }
  }
}
