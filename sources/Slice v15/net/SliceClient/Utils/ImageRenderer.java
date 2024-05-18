package net.SliceClient.Utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import org.lwjgl.opengl.GL11;


public class ImageRenderer
{
  public ImageRenderer(String directory)
  {
    this.directory = directory;
    resources = new HashMap();
  }
  
  public ImageRenderer(ImageRenderer base, String sub)
  {
    this(directory + File.pathSeparator + sub);
  }
  
  public void renderImage(String fileName, double startX, double startY, double width, double height)
  {
    String fullFileName = fileName + ".png";
    ResourceLocation resourceLocation = (ResourceLocation)resources.get(fullFileName);
    if (resourceLocation == null)
    {
      resourceLocation = new ResourceLocation(String.format("textures/energetic/%s/%s", new Object[] { directory, fullFileName }));
      resources.put(fullFileName, resourceLocation);
    }
    renderImage(resourceLocation, startX, startY, width, height);
  }
  
  public void renderImage(ResourceLocation resourceLocation, double startX, double startY, double width, double height)
  {
    Minecraft.getMinecraft().getTextureManager().bindTexture(resourceLocation);
    double scaleX = width / 256.0D;
    double scaleY = height / 256.0D;
    if ((scaleX <= 0.0D) || (scaleY <= 0.0D)) {
      return;
    }
    GL11.glPushMatrix();
    GL11.glTranslated(startX, startY, 0.0D);
    GL11.glScaled(scaleX, scaleY, 1.0D);
    RenderUtil.drawTexturedModalRect(0, 0, 0, 0, 256, 256);
    GL11.glScaled(1.0D / scaleX, 1.0D / scaleY, 1.0D);
    GL11.glPopMatrix();
  }
  
  public static final ImageRenderer GENERIC_RENDERER = new ImageRenderer("/");
  private String directory;
  private Map<String, ResourceLocation> resources;
}
