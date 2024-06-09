package org.newdawn.slick;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.IntBuffer;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.ImageDataFactory;
import org.newdawn.slick.opengl.LoadableImageData;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.OperationNotSupportedException;
import org.newdawn.slick.util.ResourceLoader;
















public class BigImage
  extends Image
{
  protected static SGL GL = ;
  private static Image lastBind;
  private Image[][] images;
  private int xcount;
  private int ycount;
  private int realWidth;
  private int realHeight;
  
  public static final int getMaxSingleImageSize()
  {
    IntBuffer buffer = BufferUtils.createIntBuffer(16);
    GL.glGetInteger(3379, buffer);
    
    return buffer.get(0);
  }
  
















  private BigImage()
  {
    inited = true;
  }
  




  public BigImage(String ref)
    throws SlickException
  {
    this(ref, 2);
  }
  






  public BigImage(String ref, int filter)
    throws SlickException
  {
    build(ref, filter, getMaxSingleImageSize());
  }
  






  public BigImage(String ref, int filter, int tileSize)
    throws SlickException
  {
    build(ref, filter, tileSize);
  }
  






  public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter)
  {
    build(data, imageBuffer, filter, getMaxSingleImageSize());
  }
  







  public BigImage(LoadableImageData data, ByteBuffer imageBuffer, int filter, int tileSize)
  {
    build(data, imageBuffer, filter, tileSize);
  }
  






  public Image getTile(int x, int y)
  {
    return images[x][y];
  }
  





  private void build(String ref, int filter, int tileSize)
    throws SlickException
  {
    try
    {
      LoadableImageData data = ImageDataFactory.getImageDataFor(ref);
      ByteBuffer imageBuffer = data.loadImage(ResourceLoader.getResourceAsStream(ref), false, null);
      build(data, imageBuffer, filter, tileSize);
    } catch (IOException e) {
      throw new SlickException("Failed to load: " + ref, e);
    }
  }
  







  private void build(final LoadableImageData data, final ByteBuffer imageBuffer, int filter, int tileSize)
  {
    final int dataWidth = data.getTexWidth();
    final int dataHeight = data.getTexHeight();
    
    realWidth = (this.width = data.getWidth());
    realHeight = (this.height = data.getHeight());
    
    if ((dataWidth <= tileSize) && (dataHeight <= tileSize)) {
      images = new Image[1][1];
      ImageData tempData = new ImageData() {
        public int getDepth() {
          return data.getDepth();
        }
        
        public int getHeight() {
          return dataHeight;
        }
        
        public ByteBuffer getImageBufferData() {
          return imageBuffer;
        }
        
        public int getTexHeight() {
          return dataHeight;
        }
        
        public int getTexWidth() {
          return dataWidth;
        }
        
        public int getWidth() {
          return dataWidth;
        }
      };
      images[0][0] = new Image(tempData, filter);
      xcount = 1;
      ycount = 1;
      inited = true;
      return;
    }
    
    xcount = ((realWidth - 1) / tileSize + 1);
    ycount = ((realHeight - 1) / tileSize + 1);
    
    images = new Image[xcount][ycount];
    int components = data.getDepth() / 8;
    
    for (int x = 0; x < xcount; x++) {
      for (int y = 0; y < ycount; y++) {
        int finalX = (x + 1) * tileSize;
        int finalY = (y + 1) * tileSize;
        final int imageWidth = Math.min(realWidth - x * tileSize, tileSize);
        final int imageHeight = Math.min(realHeight - y * tileSize, tileSize);
        
        final int xSize = tileSize;
        final int ySize = tileSize;
        
        final ByteBuffer subBuffer = BufferUtils.createByteBuffer(tileSize * tileSize * components);
        int xo = x * tileSize * components;
        
        byte[] byteData = new byte[xSize * components];
        for (int i = 0; i < ySize; i++) {
          int yo = (y * tileSize + i) * dataWidth * components;
          imageBuffer.position(yo + xo);
          
          imageBuffer.get(byteData, 0, xSize * components);
          subBuffer.put(byteData);
        }
        
        subBuffer.flip();
        ImageData imgData = new ImageData() {
          public int getDepth() {
            return data.getDepth();
          }
          
          public int getHeight() {
            return imageHeight;
          }
          
          public int getWidth() {
            return imageWidth;
          }
          
          public ByteBuffer getImageBufferData() {
            return subBuffer;
          }
          
          public int getTexHeight() {
            return ySize;
          }
          
          public int getTexWidth() {
            return xSize;
          }
        };
        images[x][y] = new Image(imgData, filter);
      }
    }
    
    inited = true;
  }
  




  public void bind()
  {
    throw new OperationNotSupportedException("Can't bind big images yet");
  }
  




  public Image copy()
  {
    throw new OperationNotSupportedException("Can't copy big images yet");
  }
  


  public void draw()
  {
    draw(0.0F, 0.0F);
  }
  


  public void draw(float x, float y, Color filter)
  {
    draw(x, y, width, height, filter);
  }
  


  public void draw(float x, float y, float scale, Color filter)
  {
    draw(x, y, width * scale, height * scale, filter);
  }
  


  public void draw(float x, float y, float width, float height, Color filter)
  {
    float sx = width / realWidth;
    float sy = height / realHeight;
    
    GL.glTranslatef(x, y, 0.0F);
    GL.glScalef(sx, sy, 1.0F);
    
    float xp = 0.0F;
    float yp = 0.0F;
    
    for (int tx = 0; tx < xcount; tx++) {
      yp = 0.0F;
      for (int ty = 0; ty < ycount; ty++) {
        Image image = images[tx][ty];
        
        image.draw(xp, yp, image.getWidth(), image.getHeight(), filter);
        
        yp += image.getHeight();
        if (ty == ycount - 1) {
          xp += image.getWidth();
        }
      }
    }
    

    GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
    GL.glTranslatef(-x, -y, 0.0F);
  }
  


  public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
  {
    int srcwidth = (int)(srcx2 - srcx);
    int srcheight = (int)(srcy2 - srcy);
    
    Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
    
    int width = (int)(x2 - x);
    int height = (int)(y2 - y);
    
    subImage.draw(x, y, width, height);
  }
  


  public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2)
  {
    int srcwidth = (int)(srcx2 - srcx);
    int srcheight = (int)(srcy2 - srcy);
    
    draw(x, y, srcwidth, srcheight, srcx, srcy, srcx2, srcy2);
  }
  


  public void draw(float x, float y, float width, float height)
  {
    draw(x, y, width, height, Color.white);
  }
  


  public void draw(float x, float y, float scale)
  {
    draw(x, y, scale, Color.white);
  }
  


  public void draw(float x, float y)
  {
    draw(x, y, Color.white);
  }
  


  public void drawEmbedded(float x, float y, float width, float height)
  {
    float sx = width / realWidth;
    float sy = height / realHeight;
    
    float xp = 0.0F;
    float yp = 0.0F;
    
    for (int tx = 0; tx < xcount; tx++) {
      yp = 0.0F;
      for (int ty = 0; ty < ycount; ty++) {
        Image image = images[tx][ty];
        
        if ((lastBind == null) || (image.getTexture() != lastBind.getTexture())) {
          if (lastBind != null) {
            lastBind.endUse();
          }
          lastBind = image;
          lastBind.startUse();
        }
        image.drawEmbedded(xp + x, yp + y, image.getWidth(), image.getHeight());
        
        yp += image.getHeight();
        if (ty == ycount - 1) {
          xp += image.getWidth();
        }
      }
    }
  }
  



  public void drawFlash(float x, float y, float width, float height)
  {
    float sx = width / realWidth;
    float sy = height / realHeight;
    
    GL.glTranslatef(x, y, 0.0F);
    GL.glScalef(sx, sy, 1.0F);
    
    float xp = 0.0F;
    float yp = 0.0F;
    
    for (int tx = 0; tx < xcount; tx++) {
      yp = 0.0F;
      for (int ty = 0; ty < ycount; ty++) {
        Image image = images[tx][ty];
        
        image.drawFlash(xp, yp, image.getWidth(), image.getHeight());
        
        yp += image.getHeight();
        if (ty == ycount - 1) {
          xp += image.getWidth();
        }
      }
    }
    

    GL.glScalef(1.0F / sx, 1.0F / sy, 1.0F);
    GL.glTranslatef(-x, -y, 0.0F);
  }
  


  public void drawFlash(float x, float y)
  {
    drawFlash(x, y, width, height);
  }
  




  public void endUse()
  {
    if (lastBind != null) {
      lastBind.endUse();
    }
    lastBind = null;
  }
  





  public void startUse() {}
  





  public void ensureInverted()
  {
    throw new OperationNotSupportedException("Doesn't make sense for tiled operations");
  }
  




  public Color getColor(int x, int y)
  {
    throw new OperationNotSupportedException("Can't use big images as buffers");
  }
  


  public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical)
  {
    BigImage image = new BigImage();
    
    images = this.images;
    xcount = xcount;
    ycount = ycount;
    width = width;
    height = height;
    realWidth = realWidth;
    realHeight = realHeight;
    
    if (flipHorizontal) {
      Image[][] images = images;
      images = new Image[xcount][ycount];
      
      for (int x = 0; x < xcount; x++) {
        for (int y = 0; y < ycount; y++) {
          images[x][y] = images[(xcount - 1 - x)][y].getFlippedCopy(true, false);
        }
      }
    }
    
    if (flipVertical) {
      Image[][] images = images;
      images = new Image[xcount][ycount];
      
      for (int x = 0; x < xcount; x++) {
        for (int y = 0; y < ycount; y++) {
          images[x][y] = images[x][(ycount - 1 - y)].getFlippedCopy(false, true);
        }
      }
    }
    
    return image;
  }
  



  public Graphics getGraphics()
    throws SlickException
  {
    throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
  }
  


  public Image getScaledCopy(float scale)
  {
    return getScaledCopy((int)(scale * width), (int)(scale * height));
  }
  


  public Image getScaledCopy(int width, int height)
  {
    BigImage image = new BigImage();
    
    images = images;
    xcount = xcount;
    ycount = ycount;
    width = width;
    height = height;
    realWidth = realWidth;
    realHeight = realHeight;
    
    return image;
  }
  


  public Image getSubImage(int x, int y, int width, int height)
  {
    BigImage image = new BigImage();
    
    width = width;
    height = height;
    realWidth = width;
    realHeight = height;
    images = new Image[xcount][ycount];
    
    float xp = 0.0F;
    float yp = 0.0F;
    int x2 = x + width;
    int y2 = y + height;
    
    int startx = 0;
    int starty = 0;
    boolean foundStart = false;
    
    for (int xt = 0; xt < xcount; xt++) {
      yp = 0.0F;
      starty = 0;
      foundStart = false;
      for (int yt = 0; yt < ycount; yt++) {
        Image current = images[xt][yt];
        
        int xp2 = (int)(xp + current.getWidth());
        int yp2 = (int)(yp + current.getHeight());
        





        int targetX1 = (int)Math.max(x, xp);
        int targetY1 = (int)Math.max(y, yp);
        int targetX2 = Math.min(x2, xp2);
        int targetY2 = Math.min(y2, yp2);
        
        int targetWidth = targetX2 - targetX1;
        int targetHeight = targetY2 - targetY1;
        
        if ((targetWidth > 0) && (targetHeight > 0)) {
          Image subImage = current.getSubImage((int)(targetX1 - xp), (int)(targetY1 - yp), 
            targetX2 - targetX1, 
            targetY2 - targetY1);
          foundStart = true;
          images[startx][starty] = subImage;
          starty++;
          ycount = Math.max(ycount, starty);
        }
        
        yp += current.getHeight();
        if (yt == ycount - 1) {
          xp += current.getWidth();
        }
      }
      if (foundStart) {
        startx++;
        xcount += 1;
      }
    }
    
    return image;
  }
  




  public Texture getTexture()
  {
    throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
  }
  


  protected void initImpl()
  {
    throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
  }
  


  protected void reinit()
  {
    throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
  }
  




  public void setTexture(Texture texture)
  {
    throw new OperationNotSupportedException("Can't use big images as offscreen buffers");
  }
  







  public Image getSubImage(int offsetX, int offsetY)
  {
    return images[offsetX][offsetY];
  }
  




  public int getHorizontalImageCount()
  {
    return xcount;
  }
  




  public int getVerticalImageCount()
  {
    return ycount;
  }
  


  public String toString()
  {
    return "[BIG IMAGE]";
  }
  


  public void destroy()
    throws SlickException
  {
    for (int tx = 0; tx < xcount; tx++) {
      for (int ty = 0; ty < ycount; ty++) {
        Image image = images[tx][ty];
        image.destroy();
      }
    }
  }
  



  public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
  {
    int srcwidth = (int)(srcx2 - srcx);
    int srcheight = (int)(srcy2 - srcy);
    
    Image subImage = getSubImage((int)srcx, (int)srcy, srcwidth, srcheight);
    
    int width = (int)(x2 - x);
    int height = (int)(y2 - y);
    
    subImage.draw(x, y, width, height, filter);
  }
  


  public void drawCentered(float x, float y)
  {
    throw new UnsupportedOperationException();
  }
  



  public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
  {
    throw new UnsupportedOperationException();
  }
  



  public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
  {
    throw new UnsupportedOperationException();
  }
  


  public void drawFlash(float x, float y, float width, float height, Color col)
  {
    throw new UnsupportedOperationException();
  }
  


  public void drawSheared(float x, float y, float hshear, float vshear)
  {
    throw new UnsupportedOperationException();
  }
}
