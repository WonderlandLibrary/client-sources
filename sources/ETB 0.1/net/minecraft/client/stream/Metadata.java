package net.minecraft.client.stream;

import com.google.common.base.Objects;
import com.google.common.base.Objects.ToStringHelper;
import com.google.gson.Gson;
import java.util.Map;

public class Metadata
{
  private static final Gson field_152811_a = new Gson();
  private final String field_152812_b;
  private String field_152813_c;
  private Map field_152814_d;
  private static final String __OBFID = "CL_00001823";
  
  public Metadata(String p_i46345_1_, String p_i46345_2_)
  {
    field_152812_b = p_i46345_1_;
    field_152813_c = p_i46345_2_;
  }
  
  public Metadata(String p_i1030_1_)
  {
    this(p_i1030_1_, null);
  }
  
  public void func_152807_a(String p_152807_1_)
  {
    field_152813_c = p_152807_1_;
  }
  
  public String func_152809_a()
  {
    return field_152813_c == null ? field_152812_b : field_152813_c;
  }
  
  public void func_152808_a(String p_152808_1_, String p_152808_2_)
  {
    if (field_152814_d == null)
    {
      field_152814_d = com.google.common.collect.Maps.newHashMap();
    }
    
    if (field_152814_d.size() > 50)
    {
      throw new IllegalArgumentException("Metadata payload is full, cannot add more to it!");
    }
    if (p_152808_1_ == null)
    {
      throw new IllegalArgumentException("Metadata payload key cannot be null!");
    }
    if (p_152808_1_.length() > 255)
    {
      throw new IllegalArgumentException("Metadata payload key is too long!");
    }
    if (p_152808_2_ == null)
    {
      throw new IllegalArgumentException("Metadata payload value cannot be null!");
    }
    if (p_152808_2_.length() > 255)
    {
      throw new IllegalArgumentException("Metadata payload value is too long!");
    }
    

    field_152814_d.put(p_152808_1_, p_152808_2_);
  }
  

  public String func_152806_b()
  {
    return (field_152814_d != null) && (!field_152814_d.isEmpty()) ? field_152811_a.toJson(field_152814_d) : null;
  }
  
  public String func_152810_c()
  {
    return field_152812_b;
  }
  
  public String toString()
  {
    return Objects.toStringHelper(this).add("name", field_152812_b).add("description", field_152813_c).add("data", func_152806_b()).toString();
  }
}
