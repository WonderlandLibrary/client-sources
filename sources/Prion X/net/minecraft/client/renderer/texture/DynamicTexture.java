package net.minecraft.client.renderer.texture;

import java.awt.image.BufferedImage;
import java.io.IOException;
import net.minecraft.client.resources.IResourceManager;




public class DynamicTexture
  extends AbstractTexture
{
  private final int[] dynamicTextureData;
  private final int width;
  private final int height;
  private static final String __OBFID = "CL_00001048";
  
  public DynamicTexture(BufferedImage p_i1270_1_)
  {
    this(p_i1270_1_.getWidth(), p_i1270_1_.getHeight());
    p_i1270_1_.getRGB(0, 0, p_i1270_1_.getWidth(), p_i1270_1_.getHeight(), dynamicTextureData, 0, p_i1270_1_.getWidth());
    updateDynamicTexture();
  }
  
  public DynamicTexture(int p_i1271_1_, int p_i1271_2_)
  {
    width = p_i1271_1_;
    height = p_i1271_2_;
    dynamicTextureData = new int[p_i1271_1_ * p_i1271_2_];
    TextureUtil.allocateTexture(getGlTextureId(), p_i1271_1_, p_i1271_2_);
  }
  
  public void loadTexture(IResourceManager p_110551_1_) throws IOException
  {}
  
  public void updateDynamicTexture() {
    TextureUtil.uploadTexture(getGlTextureId(), dynamicTextureData, width, height);
  }
  
  public int[] getTextureData()
  {
    return dynamicTextureData;
  }
}
