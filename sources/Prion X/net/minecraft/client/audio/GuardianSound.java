package net.minecraft.client.audio;

import net.minecraft.entity.monster.EntityGuardian;
import net.minecraft.util.ResourceLocation;

public class GuardianSound extends MovingSound
{
  private final EntityGuardian guardian;
  private static final String __OBFID = "CL_00002381";
  
  public GuardianSound(EntityGuardian guardian)
  {
    super(new ResourceLocation("minecraft:mob.guardian.attack"));
    this.guardian = guardian;
    attenuationType = ISound.AttenuationType.NONE;
    repeat = true;
    repeatDelay = 0;
  }
  



  public void update()
  {
    if ((!guardian.isDead) && (guardian.func_175474_cn()))
    {
      xPosF = ((float)guardian.posX);
      yPosF = ((float)guardian.posY);
      zPosF = ((float)guardian.posZ);
      float var1 = guardian.func_175477_p(0.0F);
      volume = (0.0F + 1.0F * var1 * var1);
      pitch = (0.7F + 0.5F * var1);
    }
    else
    {
      donePlaying = true;
    }
  }
}
