package net.minecraft.potion;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class PotionEffect
{
  private static final Logger LOGGER = ;
  

  private int potionID;
  

  private int duration;
  

  private int amplifier;
  
  private boolean isSplashPotion;
  
  private boolean isAmbient;
  
  private boolean isPotionDurationMax;
  
  private boolean showParticles;
  
  private static final String __OBFID = "CL_00001529";
  

  public PotionEffect(int id, int effectDuration)
  {
    this(id, effectDuration, 0);
  }
  
  public PotionEffect(int id, int effectDuration, int effectAmplifier)
  {
    this(id, effectDuration, effectAmplifier, false, true);
  }
  
  public PotionEffect(int id, int effectDuration, int effectAmplifier, boolean ambient, boolean showParticles)
  {
    potionID = id;
    duration = effectDuration;
    amplifier = effectAmplifier;
    isAmbient = ambient;
    this.showParticles = showParticles;
  }
  
  public PotionEffect(PotionEffect other)
  {
    potionID = potionID;
    duration = duration;
    amplifier = amplifier;
    isAmbient = isAmbient;
    showParticles = showParticles;
  }
  




  public void combine(PotionEffect other)
  {
    if (potionID != potionID)
    {
      LOGGER.warn("This method should only be called for matching effects!");
    }
    
    if (amplifier > amplifier)
    {
      amplifier = amplifier;
      duration = duration;
    }
    else if ((amplifier == amplifier) && (duration < duration))
    {
      duration = duration;
    }
    else if ((!isAmbient) && (isAmbient))
    {
      isAmbient = isAmbient;
    }
    
    showParticles = showParticles;
  }
  



  public int getPotionID()
  {
    return potionID;
  }
  
  public int getDuration()
  {
    return duration;
  }
  
  public int getAmplifier()
  {
    return amplifier;
  }
  



  public void setSplashPotion(boolean splashPotion)
  {
    isSplashPotion = splashPotion;
  }
  



  public boolean getIsAmbient()
  {
    return isAmbient;
  }
  
  public boolean func_180154_f()
  {
    return showParticles;
  }
  
  public boolean onUpdate(EntityLivingBase entityIn)
  {
    if (duration > 0)
    {
      if (Potion.potionTypes[potionID].isReady(duration, amplifier))
      {
        performEffect(entityIn);
      }
      
      deincrementDuration();
    }
    
    return duration > 0;
  }
  
  private int deincrementDuration()
  {
    return --duration;
  }
  
  public void performEffect(EntityLivingBase entityIn)
  {
    if (duration > 0)
    {
      Potion.potionTypes[potionID].performEffect(entityIn, amplifier);
    }
  }
  
  public String getEffectName()
  {
    return Potion.potionTypes[potionID].getName();
  }
  
  public int hashCode()
  {
    return potionID;
  }
  
  public String toString()
  {
    String var1 = "";
    
    if (getAmplifier() > 0)
    {
      var1 = getEffectName() + " x " + (getAmplifier() + 1) + ", Duration: " + getDuration();
    }
    else
    {
      var1 = getEffectName() + ", Duration: " + getDuration();
    }
    
    if (isSplashPotion)
    {
      var1 = var1 + ", Splash: true";
    }
    
    if (!showParticles)
    {
      var1 = var1 + ", Particles: false";
    }
    
    return Potion.potionTypes[potionID].isUsable() ? "(" + var1 + ")" : var1;
  }
  
  public boolean equals(Object p_equals_1_)
  {
    if (!(p_equals_1_ instanceof PotionEffect))
    {
      return false;
    }
    

    PotionEffect var2 = (PotionEffect)p_equals_1_;
    return (potionID == potionID) && (amplifier == amplifier) && (duration == duration) && (isSplashPotion == isSplashPotion) && (isAmbient == isAmbient);
  }
  




  public NBTTagCompound writeCustomPotionEffectToNBT(NBTTagCompound nbt)
  {
    nbt.setByte("Id", (byte)getPotionID());
    nbt.setByte("Amplifier", (byte)getAmplifier());
    nbt.setInteger("Duration", getDuration());
    nbt.setBoolean("Ambient", getIsAmbient());
    nbt.setBoolean("ShowParticles", func_180154_f());
    return nbt;
  }
  



  public static PotionEffect readCustomPotionEffectFromNBT(NBTTagCompound nbt)
  {
    byte var1 = nbt.getByte("Id");
    
    if ((var1 >= 0) && (var1 < Potion.potionTypes.length) && (Potion.potionTypes[var1] != null))
    {
      byte var2 = nbt.getByte("Amplifier");
      int var3 = nbt.getInteger("Duration");
      boolean var4 = nbt.getBoolean("Ambient");
      boolean var5 = true;
      
      if (nbt.hasKey("ShowParticles", 1))
      {
        var5 = nbt.getBoolean("ShowParticles");
      }
      
      return new PotionEffect(var1, var3, var2, var4, var5);
    }
    

    return null;
  }
  




  public void setPotionDurationMax(boolean maxDuration)
  {
    isPotionDurationMax = maxDuration;
  }
  
  public boolean getIsPotionDurationMax()
  {
    return isPotionDurationMax;
  }
}
