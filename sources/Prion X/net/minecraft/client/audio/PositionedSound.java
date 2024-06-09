package net.minecraft.client.audio;

import net.minecraft.util.ResourceLocation;

public abstract class PositionedSound implements ISound
{
  protected final ResourceLocation positionedSoundLocation;
  protected float volume = 1.0F;
  protected float pitch = 1.0F;
  protected float xPosF;
  protected float yPosF;
  protected float zPosF;
  protected boolean repeat = false;
  

  protected int repeatDelay = 0;
  protected ISound.AttenuationType attenuationType;
  private static final String __OBFID = "CL_00001116";
  
  protected PositionedSound(ResourceLocation soundResource)
  {
    attenuationType = ISound.AttenuationType.LINEAR;
    positionedSoundLocation = soundResource;
  }
  
  public ResourceLocation getSoundLocation()
  {
    return positionedSoundLocation;
  }
  
  public boolean canRepeat()
  {
    return repeat;
  }
  
  public int getRepeatDelay()
  {
    return repeatDelay;
  }
  
  public float getVolume()
  {
    return volume;
  }
  
  public float getPitch()
  {
    return pitch;
  }
  
  public float getXPosF()
  {
    return xPosF;
  }
  
  public float getYPosF()
  {
    return yPosF;
  }
  
  public float getZPosF()
  {
    return zPosF;
  }
  
  public ISound.AttenuationType getAttenuationType()
  {
    return attenuationType;
  }
}
