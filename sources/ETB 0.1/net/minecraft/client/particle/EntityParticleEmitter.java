package net.minecraft.client.particle;

import java.util.Random;
import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;

public class EntityParticleEmitter extends EntityFX
{
  private Entity field_174851_a;
  private int field_174852_ax;
  private int field_174850_ay;
  private EnumParticleTypes field_174849_az;
  private static final String __OBFID = "CL_00002574";
  
  public EntityParticleEmitter(World worldIn, Entity p_i46279_2_, EnumParticleTypes p_i46279_3_)
  {
    super(worldIn, posX, getEntityBoundingBoxminY + height / 2.0F, posZ, motionX, motionY, motionZ);
    field_174851_a = p_i46279_2_;
    field_174850_ay = 3;
    field_174849_az = p_i46279_3_;
    onUpdate();
  }
  


  public void func_180434_a(net.minecraft.client.renderer.WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_) {}
  

  public void onUpdate()
  {
    for (int var1 = 0; var1 < 16; var1++)
    {
      double var2 = rand.nextFloat() * 2.0F - 1.0F;
      double var4 = rand.nextFloat() * 2.0F - 1.0F;
      double var6 = rand.nextFloat() * 2.0F - 1.0F;
      
      if (var2 * var2 + var4 * var4 + var6 * var6 <= 1.0D)
      {
        double var8 = field_174851_a.posX + var2 * field_174851_a.width / 4.0D;
        double var10 = field_174851_a.getEntityBoundingBox().minY + field_174851_a.height / 2.0F + var4 * field_174851_a.height / 4.0D;
        double var12 = field_174851_a.posZ + var6 * field_174851_a.width / 4.0D;
        worldObj.spawnParticle(field_174849_az, false, var8, var10, var12, var2, var4 + 0.2D, var6, new int[0]);
      }
    }
    
    field_174852_ax += 1;
    
    if (field_174852_ax >= field_174850_ay)
    {
      setDead();
    }
  }
  
  public int getFXLayer()
  {
    return 3;
  }
}
