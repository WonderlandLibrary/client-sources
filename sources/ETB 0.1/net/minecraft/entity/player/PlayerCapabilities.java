package net.minecraft.entity.player;

import net.minecraft.nbt.NBTTagCompound;












public class PlayerCapabilities
{
  public boolean disableDamage;
  public boolean isFlying;
  public boolean allowFlying;
  public boolean isCreativeMode;
  public boolean allowEdit = true;
  private float flySpeed = 0.05F;
  private float walkSpeed = 0.1F;
  private static final String __OBFID = "CL_00001708";
  
  public PlayerCapabilities() {}
  
  public void writeCapabilitiesToNBT(NBTTagCompound p_75091_1_) { NBTTagCompound var2 = new NBTTagCompound();
    var2.setBoolean("invulnerable", disableDamage);
    var2.setBoolean("flying", isFlying);
    var2.setBoolean("mayfly", allowFlying);
    var2.setBoolean("instabuild", isCreativeMode);
    var2.setBoolean("mayBuild", allowEdit);
    var2.setFloat("flySpeed", flySpeed);
    var2.setFloat("walkSpeed", walkSpeed);
    p_75091_1_.setTag("abilities", var2);
  }
  
  public void readCapabilitiesFromNBT(NBTTagCompound p_75095_1_)
  {
    if (p_75095_1_.hasKey("abilities", 10))
    {
      NBTTagCompound var2 = p_75095_1_.getCompoundTag("abilities");
      disableDamage = var2.getBoolean("invulnerable");
      isFlying = var2.getBoolean("flying");
      allowFlying = var2.getBoolean("mayfly");
      isCreativeMode = var2.getBoolean("instabuild");
      
      if (var2.hasKey("flySpeed", 99))
      {
        flySpeed = var2.getFloat("flySpeed");
        walkSpeed = var2.getFloat("walkSpeed");
      }
      
      if (var2.hasKey("mayBuild", 1))
      {
        allowEdit = var2.getBoolean("mayBuild");
      }
    }
  }
  
  public float getFlySpeed()
  {
    return flySpeed;
  }
  
  public void setFlySpeed(float p_75092_1_)
  {
    flySpeed = p_75092_1_;
  }
  
  public float getWalkSpeed()
  {
    return walkSpeed;
  }
  
  public void setPlayerWalkSpeed(float p_82877_1_)
  {
    walkSpeed = p_82877_1_;
  }
}
