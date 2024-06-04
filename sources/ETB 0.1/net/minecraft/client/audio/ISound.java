package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract interface ISound
{
  public abstract ResourceLocation getSoundLocation();
  
  public abstract boolean canRepeat();
  
  public abstract int getRepeatDelay();
  
  public abstract float getVolume();
  
  public abstract float getPitch();
  
  public abstract float getXPosF();
  
  public abstract float getYPosF();
  
  public abstract float getZPosF();
  
  public abstract AttenuationType getAttenuationType();
  
  public static enum AttenuationType
  {
    NONE("NONE", 0, 0), 
    LINEAR("LINEAR", 1, 2);
    
    private final int type;
    private static final AttenuationType[] $VALUES = { NONE, LINEAR };
    private static final String __OBFID = "CL_00001126";
    
    private AttenuationType(String p_i45110_1_, int p_i45110_2_, int p_i45110_3_)
    {
      type = p_i45110_3_;
    }
    
    public int getTypeInt()
    {
      return type;
    }
  }
}
