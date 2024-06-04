package net.minecraft.client.renderer.texture;

import java.util.List;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.BlockPos;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.WorldProvider;
import optifine.Config;
import shadersmod.client.ShadersTex;

public class TextureCompass extends TextureAtlasSprite
{
  public double currentAngle;
  public double angleDelta;
  public static String field_176608_l;
  private static final String __OBFID = "CL_00001071";
  
  public TextureCompass(String p_i1286_1_)
  {
    super(p_i1286_1_);
    field_176608_l = p_i1286_1_;
  }
  
  public void updateAnimation()
  {
    Minecraft var1 = Minecraft.getMinecraft();
    
    if ((theWorld != null) && (thePlayer != null))
    {
      updateCompass(theWorld, thePlayer.posX, thePlayer.posZ, thePlayer.rotationYaw, false, false);
    }
    else
    {
      updateCompass(null, 0.0D, 0.0D, 0.0D, true, false);
    }
  }
  



  public void updateCompass(World worldIn, double p_94241_2_, double p_94241_4_, double p_94241_6_, boolean p_94241_8_, boolean p_94241_9_)
  {
    if (!framesTextureData.isEmpty())
    {
      double var10 = 0.0D;
      
      if ((worldIn != null) && (!p_94241_8_))
      {
        BlockPos var18 = worldIn.getSpawnPoint();
        double var13 = var18.getX() - p_94241_2_;
        double var15 = var18.getZ() - p_94241_4_;
        p_94241_6_ %= 360.0D;
        var10 = -((p_94241_6_ - 90.0D) * 3.141592653589793D / 180.0D - Math.atan2(var15, var13));
        
        if (!provider.isSurfaceWorld())
        {
          var10 = Math.random() * 3.141592653589793D * 2.0D;
        }
      }
      
      if (p_94241_9_)
      {
        currentAngle = var10;

      }
      else
      {

        for (double var181 = var10 - currentAngle; var181 < -3.141592653589793D; var181 += 6.283185307179586D) {}
        



        while (var181 >= 3.141592653589793D)
        {
          var181 -= 6.283185307179586D;
        }
        
        var181 = MathHelper.clamp_double(var181, -1.0D, 1.0D);
        angleDelta += var181 * 0.1D;
        angleDelta *= 0.8D;
        currentAngle += angleDelta;
      }
      


      for (int var182 = (int)((currentAngle / 6.283185307179586D + 1.0D) * framesTextureData.size()) % framesTextureData.size(); var182 < 0; var182 = (var182 + framesTextureData.size()) % framesTextureData.size()) {}
      



      if (var182 != frameCounter)
      {
        frameCounter = var182;
        
        if (Config.isShaders())
        {
          ShadersTex.uploadTexSub((int[][])framesTextureData.get(frameCounter), width, height, originX, originY, false, false);
        }
        else
        {
          TextureUtil.uploadTextureMipmap((int[][])framesTextureData.get(frameCounter), width, height, originX, originY, false, false);
        }
      }
    }
  }
}
