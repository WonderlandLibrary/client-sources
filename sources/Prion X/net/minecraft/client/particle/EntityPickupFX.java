package net.minecraft.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;

public class EntityPickupFX extends EntityFX
{
  private Entity field_174840_a;
  private Entity field_174843_ax;
  private int age;
  private int maxAge;
  private float field_174841_aA;
  private RenderManager field_174842_aB = Minecraft.getMinecraft().getRenderManager();
  private static final String __OBFID = "CL_00000930";
  
  public EntityPickupFX(World worldIn, Entity p_i1233_2_, Entity p_i1233_3_, float p_i1233_4_)
  {
    super(worldIn, posX, posY, posZ, motionX, motionY, motionZ);
    field_174840_a = p_i1233_2_;
    field_174843_ax = p_i1233_3_;
    maxAge = 3;
    field_174841_aA = p_i1233_4_;
  }
  
  public void func_180434_a(WorldRenderer p_180434_1_, Entity p_180434_2_, float p_180434_3_, float p_180434_4_, float p_180434_5_, float p_180434_6_, float p_180434_7_, float p_180434_8_)
  {
    float var9 = (age + p_180434_3_) / maxAge;
    var9 *= var9;
    double var10 = field_174840_a.posX;
    double var12 = field_174840_a.posY;
    double var14 = field_174840_a.posZ;
    double var16 = field_174843_ax.lastTickPosX + (field_174843_ax.posX - field_174843_ax.lastTickPosX) * p_180434_3_;
    double var18 = field_174843_ax.lastTickPosY + (field_174843_ax.posY - field_174843_ax.lastTickPosY) * p_180434_3_ + field_174841_aA;
    double var20 = field_174843_ax.lastTickPosZ + (field_174843_ax.posZ - field_174843_ax.lastTickPosZ) * p_180434_3_;
    double var22 = var10 + (var16 - var10) * var9;
    double var24 = var12 + (var18 - var12) * var9;
    double var26 = var14 + (var20 - var14) * var9;
    int var28 = getBrightnessForRender(p_180434_3_);
    int var29 = var28 % 65536;
    int var30 = var28 / 65536;
    OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var29 / 1.0F, var30 / 1.0F);
    GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
    var22 -= interpPosX;
    var24 -= interpPosY;
    var26 -= interpPosZ;
    field_174842_aB.renderEntityWithPosYaw(field_174840_a, (float)var22, (float)var24, (float)var26, field_174840_a.rotationYaw, p_180434_3_);
  }
  



  public void onUpdate()
  {
    age += 1;
    
    if (age == maxAge)
    {
      setDead();
    }
  }
  
  public int getFXLayer()
  {
    return 3;
  }
}
