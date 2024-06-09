package net.minecraft.client.audio;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;

public class MovingSoundMinecartRiding extends MovingSound
{
  private final EntityPlayer player;
  private final EntityMinecart minecart;
  private static final String __OBFID = "CL_00001119";
  
  public MovingSoundMinecartRiding(EntityPlayer p_i45106_1_, EntityMinecart minecart)
  {
    super(new ResourceLocation("minecraft:minecart.inside"));
    player = p_i45106_1_;
    this.minecart = minecart;
    attenuationType = ISound.AttenuationType.NONE;
    repeat = true;
    repeatDelay = 0;
  }
  



  public void update()
  {
    if ((!minecart.isDead) && (player.isRiding()) && (player.ridingEntity == minecart))
    {
      float var1 = MathHelper.sqrt_double(minecart.motionX * minecart.motionX + minecart.motionZ * minecart.motionZ);
      
      if (var1 >= 0.01D)
      {
        volume = (0.0F + MathHelper.clamp_float(var1, 0.0F, 1.0F) * 0.75F);
      }
      else
      {
        volume = 0.0F;
      }
    }
    else
    {
      donePlaying = true;
    }
  }
}
