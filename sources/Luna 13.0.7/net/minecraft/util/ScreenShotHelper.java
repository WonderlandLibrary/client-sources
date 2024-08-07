package net.minecraft.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.nio.IntBuffer;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.imageio.ImageIO;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.shader.Framebuffer;
import net.minecraft.event.ClickEvent;
import net.minecraft.event.ClickEvent.Action;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class ScreenShotHelper
{
  private static final Logger logger = ;
  private static final DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss");
  private static IntBuffer pixelBuffer;
  private static int[] pixelValues;
  private static final String __OBFID = "CL_00000656";
  
  public ScreenShotHelper() {}
  
  public static IChatComponent saveScreenshot(File p_148260_0_, int p_148260_1_, int p_148260_2_, Framebuffer p_148260_3_)
  {
    return saveScreenshot(p_148260_0_, (String)null, p_148260_1_, p_148260_2_, p_148260_3_);
  }
  
  public static IChatComponent saveScreenshot(File p_148259_0_, String p_148259_1_, int p_148259_2_, int p_148259_3_, Framebuffer p_148259_4_)
  {
    try
    {
      File var5 = new File(p_148259_0_, "screenshots");
      var5.mkdir();
      if (OpenGlHelper.isFramebufferEnabled())
      {
        p_148259_2_ = p_148259_4_.framebufferTextureWidth;
        p_148259_3_ = p_148259_4_.framebufferTextureHeight;
      }
      int var6 = p_148259_2_ * p_148259_3_;
      if ((pixelBuffer == null) || (pixelBuffer.capacity() < var6))
      {
        pixelBuffer = BufferUtils.createIntBuffer(var6);
        pixelValues = new int[var6];
      }
      GL11.glPixelStorei(3333, 1);
      GL11.glPixelStorei(3317, 1);
      pixelBuffer.clear();
      if (OpenGlHelper.isFramebufferEnabled())
      {
        GlStateManager.func_179144_i(p_148259_4_.framebufferTexture);
        GL11.glGetTexImage(3553, 0, 32993, 33639, pixelBuffer);
      }
      else
      {
        GL11.glReadPixels(0, 0, p_148259_2_, p_148259_3_, 32993, 33639, pixelBuffer);
      }
      pixelBuffer.get(pixelValues);
      TextureUtil.func_147953_a(pixelValues, p_148259_2_, p_148259_3_);
      BufferedImage var7 = null;
      if (OpenGlHelper.isFramebufferEnabled())
      {
        var7 = new BufferedImage(p_148259_4_.framebufferWidth, p_148259_4_.framebufferHeight, 1);
        int var8 = p_148259_4_.framebufferTextureHeight - p_148259_4_.framebufferHeight;
        for (int var9 = var8; var9 < p_148259_4_.framebufferTextureHeight; var9++) {
          for (int var10 = 0; var10 < p_148259_4_.framebufferWidth; var10++) {
            var7.setRGB(var10, var9 - var8, pixelValues[(var9 * p_148259_4_.framebufferTextureWidth + var10)]);
          }
        }
      }
      else
      {
        var7 = new BufferedImage(p_148259_2_, p_148259_3_, 1);
        var7.setRGB(0, 0, p_148259_2_, p_148259_3_, pixelValues, 0, p_148259_2_);
      }
      File var12;
      File var12;
      if (p_148259_1_ == null) {
        var12 = getTimestampedPNGFileForDirectory(var5);
      } else {
        var12 = new File(var5, p_148259_1_);
      }
      ImageIO.write(var7, "png", var12);
      ChatComponentText var13 = new ChatComponentText(var12.getName());
      var13.getChatStyle().setChatClickEvent(new ClickEvent(ClickEvent.Action.OPEN_FILE, var12.getAbsolutePath()));
      var13.getChatStyle().setUnderlined(Boolean.valueOf(true));
      return new ChatComponentTranslation("screenshot.success", new Object[] { var13 });
    }
    catch (Exception var11)
    {
      logger.warn("Couldn't save screenshot", var11);
    }
    return new ChatComponentTranslation("screenshot.failure", tmp424_421);
  }
  
  private static File getTimestampedPNGFileForDirectory(File p_74290_0_)
  {
    String var2 = dateFormat.format(new Date()).toString();
    int var3 = 1;
    for (;;)
    {
      File var1 = new File(p_74290_0_, var2 + (var3 == 1 ? "" : new StringBuilder().append("_").append(var3).toString()) + ".png");
      if (!var1.exists()) {
        return var1;
      }
      var3++;
    }
  }
}
