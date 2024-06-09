package net.minecraft.client.audio;

import com.google.common.collect.Lists;
import java.util.List;

public class SoundList
{
  private final List soundList = Lists.newArrayList();
  
  private boolean replaceExisting;
  
  private SoundCategory category;
  private static final String __OBFID = "CL_00001121";
  
  public SoundList() {}
  
  public List getSoundList()
  {
    return soundList;
  }
  
  public boolean canReplaceExisting()
  {
    return replaceExisting;
  }
  
  public void setReplaceExisting(boolean p_148572_1_)
  {
    replaceExisting = p_148572_1_;
  }
  
  public SoundCategory getSoundCategory()
  {
    return category;
  }
  
  public void setSoundCategory(SoundCategory p_148571_1_)
  {
    category = p_148571_1_;
  }
  
  public static class SoundEntry
  {
    private String name;
    private float volume = 1.0F;
    private float pitch = 1.0F;
    private int field_148565_d = 1;
    private Type field_148566_e;
    private boolean field_148564_f;
    private static final String __OBFID = "CL_00001122";
    
    public SoundEntry()
    {
      field_148566_e = Type.FILE;
      field_148564_f = false;
    }
    
    public String getSoundEntryName()
    {
      return name;
    }
    
    public void setSoundEntryName(String p_148561_1_)
    {
      name = p_148561_1_;
    }
    
    public float getSoundEntryVolume()
    {
      return volume;
    }
    
    public void setSoundEntryVolume(float p_148553_1_)
    {
      volume = p_148553_1_;
    }
    
    public float getSoundEntryPitch()
    {
      return pitch;
    }
    
    public void setSoundEntryPitch(float p_148559_1_)
    {
      pitch = p_148559_1_;
    }
    
    public int getSoundEntryWeight()
    {
      return field_148565_d;
    }
    
    public void setSoundEntryWeight(int p_148554_1_)
    {
      field_148565_d = p_148554_1_;
    }
    
    public Type getSoundEntryType()
    {
      return field_148566_e;
    }
    
    public void setSoundEntryType(Type p_148562_1_)
    {
      field_148566_e = p_148562_1_;
    }
    
    public boolean isStreaming()
    {
      return field_148564_f;
    }
    
    public void setStreaming(boolean p_148557_1_)
    {
      field_148564_f = p_148557_1_;
    }
    
    public static enum Type
    {
      FILE("FILE", 0, "file"), 
      SOUND_EVENT("SOUND_EVENT", 1, "event");
      
      private final String field_148583_c;
      private static final Type[] $VALUES = { FILE, SOUND_EVENT };
      private static final String __OBFID = "CL_00001123";
      
      private Type(String p_i45109_1_, int p_i45109_2_, String p_i45109_3_)
      {
        field_148583_c = p_i45109_3_;
      }
      
      public static Type getType(String p_148580_0_)
      {
        Type[] var1 = values();
        int var2 = var1.length;
        
        for (int var3 = 0; var3 < var2; var3++)
        {
          Type var4 = var1[var3];
          
          if (field_148583_c.equals(p_148580_0_))
          {
            return var4;
          }
        }
        
        return null;
      }
    }
  }
}
