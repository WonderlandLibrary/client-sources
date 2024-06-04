package net.minecraft.client.resources;

public class Language implements Comparable
{
  private final String languageCode;
  private final String region;
  private final String name;
  private final boolean bidirectional;
  private static final String __OBFID = "CL_00001095";
  
  public Language(String p_i1303_1_, String p_i1303_2_, String p_i1303_3_, boolean p_i1303_4_)
  {
    languageCode = p_i1303_1_;
    region = p_i1303_2_;
    name = p_i1303_3_;
    bidirectional = p_i1303_4_;
  }
  
  public String getLanguageCode()
  {
    return languageCode;
  }
  
  public boolean isBidirectional()
  {
    return bidirectional;
  }
  
  public String toString()
  {
    return String.format("%s (%s)", new Object[] { name, region });
  }
  
  public boolean equals(Object p_equals_1_)
  {
    return !(p_equals_1_ instanceof Language) ? false : this == p_equals_1_ ? true : languageCode.equals(languageCode);
  }
  
  public int hashCode()
  {
    return languageCode.hashCode();
  }
  
  public int compareTo(Language p_compareTo_1_)
  {
    return languageCode.compareTo(languageCode);
  }
  
  public int compareTo(Object p_compareTo_1_)
  {
    return compareTo((Language)p_compareTo_1_);
  }
}
