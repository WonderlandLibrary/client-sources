package net.minecraft.src;

import java.nio.ByteBuffer;
import java.util.Properties;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.GlStateManager;
import org.lwjgl.opengl.GL11;

public class TextureAnimation
{
  private String srcTex = null;
  private String dstTex = null;
  private int dstTextId = -1;
  private int dstX = 0;
  private int dstY = 0;
  private int frameWidth = 0;
  private int frameHeight = 0;
  private CustomAnimationFrame[] frames = null;
  private int activeFrame = 0;
  private ByteBuffer imageData = null;
  
  public TextureAnimation(String texFrom, byte[] srcData, String texTo, int dstTexId, int dstX, int dstY, int frameWidth, int frameHeight, Properties props, int durDef)
  {
    srcTex = texFrom;
    dstTex = texTo;
    dstTextId = dstTexId;
    this.dstX = dstX;
    this.dstY = dstY;
    this.frameWidth = frameWidth;
    this.frameHeight = frameHeight;
    int frameLen = frameWidth * frameHeight * 4;
    
    if (srcData.length % frameLen != 0)
    {
      Config.warn("Invalid animated texture length: " + srcData.length + ", frameWidth: " + frameHeight + ", frameHeight: " + frameHeight);
    }
    
    imageData = GLAllocation.createDirectByteBuffer(srcData.length);
    imageData.put(srcData);
    int numFrames = srcData.length / frameLen;
    
    if (props.get("tile.0") != null)
    {
      for (int durationDefStr = 0; props.get("tile." + durationDefStr) != null; durationDefStr++)
      {
        numFrames = durationDefStr + 1;
      }
    }
    
    String var21 = (String)props.get("duration");
    int durationDef = Config.parseInt(var21, durDef);
    frames = new CustomAnimationFrame[numFrames];
    
    for (int i = 0; i < frames.length; i++)
    {
      String indexStr = (String)props.get("tile." + i);
      int index = Config.parseInt(indexStr, i);
      String durationStr = (String)props.get("duration." + i);
      int duration = Config.parseInt(durationStr, durationDef);
      CustomAnimationFrame frm = new CustomAnimationFrame(index, duration);
      frames[i] = frm;
    }
  }
  
  public boolean nextFrame()
  {
    if (frames.length <= 0)
    {
      return false;
    }
    

    if (activeFrame >= frames.length)
    {
      activeFrame = 0;
    }
    
    CustomAnimationFrame frame = frames[activeFrame];
    counter += 1;
    
    if (counter < duration)
    {
      return false;
    }
    

    counter = 0;
    activeFrame += 1;
    
    if (activeFrame >= frames.length)
    {
      activeFrame = 0;
    }
    
    return true;
  }
  


  public int getActiveFrameIndex()
  {
    if (frames.length <= 0)
    {
      return 0;
    }
    

    if (activeFrame >= frames.length)
    {
      activeFrame = 0;
    }
    
    CustomAnimationFrame frame = frames[activeFrame];
    return index;
  }
  

  public int getFrameCount()
  {
    return frames.length;
  }
  
  public boolean updateTexture()
  {
    if (!nextFrame())
    {
      return false;
    }
    

    int frameLen = frameWidth * frameHeight * 4;
    int imgNum = getActiveFrameIndex();
    int offset = frameLen * imgNum;
    
    if (offset + frameLen > imageData.capacity())
    {
      return false;
    }
    

    imageData.position(offset);
    GlStateManager.func_179144_i(dstTextId);
    GL11.glTexSubImage2D(3553, 0, dstX, dstY, frameWidth, frameHeight, 6408, 5121, imageData);
    return true;
  }
  


  public String getSrcTex()
  {
    return srcTex;
  }
  
  public String getDstTex()
  {
    return dstTex;
  }
  
  public int getDstTextId()
  {
    return dstTextId;
  }
}
