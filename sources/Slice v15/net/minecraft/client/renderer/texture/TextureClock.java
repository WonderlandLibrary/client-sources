package net.minecraft.client.renderer.texture;

import java.util.List;
import net.minecraft.client.Minecraft;

public class TextureClock extends TextureAtlasSprite
{
  private double field_94239_h;
  private double field_94240_i;
  private static final String __OBFID = "CL_00001070";
  
  public TextureClock(String p_i1285_1_)
  {
    super(p_i1285_1_);
  }
  
  public void updateAnimation()
  {
    if (!framesTextureData.isEmpty())
    {
      Minecraft var1 = Minecraft.getMinecraft();
      double var2 = 0.0D;
      
      if ((Minecraft.theWorld != null) && (Minecraft.thePlayer != null))
      {
        float var4 = Minecraft.theWorld.getCelestialAngle(1.0F);
        var2 = var4;
        
        if (!theWorldprovider.isSurfaceWorld())
        {
          var2 = Math.random();
        }
      }
      


      for (double var7 = var2 - field_94239_h; var7 < -0.5D; var7 += 1.0D) {}
      



      while (var7 >= 0.5D)
      {
        var7 -= 1.0D;
      }
      
      var7 = net.minecraft.util.MathHelper.clamp_double(var7, -1.0D, 1.0D);
      field_94240_i += var7 * 0.1D;
      field_94240_i *= 0.8D;
      field_94239_h += field_94240_i;
      

      for (int var6 = (int)((field_94239_h + 1.0D) * framesTextureData.size()) % framesTextureData.size(); var6 < 0; var6 = (var6 + framesTextureData.size()) % framesTextureData.size()) {}
      



      if (var6 != frameCounter)
      {
        frameCounter = var6;
        TextureUtil.uploadTextureMipmap((int[][])framesTextureData.get(frameCounter), width, height, originX, originY, false, false);
      }
    }
  }
}
