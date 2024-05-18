package net.SliceClient.Utils;

import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;

public enum SkinComponent
{
  HEAD(8, 8, 8, 8, false),  HAT(40, 8, 8, 8, false),  BODY(20, 20, 8, 12, false),  LEFT_LEG(4, 20, 4, 12, true),  LEFT_ARM(44, 20, 4, 12, true),  RIGHT_LEG(4, 20, 4, 12, false),  RIGHT_ARM(44, 20, 4, 12, false);
  
  private int posX;
  private int posY;
  private int width;
  private int height;
  private boolean flip;
  
  private SkinComponent(int posX, int posY, int width, int height, boolean flip)
  {
    this.posX = posX;
    this.posY = posY;
    this.width = width;
    this.height = height;
    this.flip = flip;
  }
  
  public ResourceLocation crop(BufferedImage bufferImage, String resourceName)
  {
    BufferedImage cropped = bufferImage.getSubimage(posX, posY, width, height);
    if (flip) {
      cropped = flip(cropped);
    }
    return Minecraft.getMinecraft().getTextureManager().getDynamicTextureLocation(String.format(resourceName, new Object[] { name() }), new DynamicTexture(cropped));
  }
  
  private BufferedImage flip(BufferedImage bufferedImage)
  {
    AffineTransform transform = AffineTransform.getScaleInstance(-1.0D, 1.0D);
    transform.translate(-bufferedImage.getWidth(), 0.0D);
    AffineTransformOp flip = new AffineTransformOp(transform, 1);
    return flip.filter(bufferedImage, null);
  }
  
  public int getWidth()
  {
    return width;
  }
  
  public int getHeight()
  {
    return height;
  }
}
