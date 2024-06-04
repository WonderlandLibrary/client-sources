package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecart extends MovingSound
{
  private final EntityMinecart minecart;
  private float field_147669_l = 0.0F;
  private static final String __OBFID = "CL_00001118";
  
  public MovingSoundMinecart(EntityMinecart p_i45105_1_)
  {
    super(new ResourceLocation("minecraft:minecart.base"));
    minecart = p_i45105_1_;
    repeat = true;
    repeatDelay = 0;
  }
  



  public void update()
  {
    if (minecart.isDead)
    {
      donePlaying = true;
    }
    else
    {
      xPosF = ((float)minecart.posX);
      yPosF = ((float)minecart.posY);
      zPosF = ((float)minecart.posZ);
      float var1 = MathHelper.sqrt_double(minecart.motionX * minecart.motionX + minecart.motionZ * minecart.motionZ);
      
      if (var1 >= 0.01D)
      {
        field_147669_l = MathHelper.clamp_float(field_147669_l + 0.0025F, 0.0F, 1.0F);
        volume = (0.0F + MathHelper.clamp_float(var1, 0.0F, 0.5F) * 0.7F);
      }
      else
      {
        field_147669_l = 0.0F;
        volume = 0.0F;
      }
    }
  }
}
