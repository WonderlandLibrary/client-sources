package org.newdawn.slick;

import java.io.IOException;
import java.io.InputStream;
import org.newdawn.slick.opengl.ImageData;
import org.newdawn.slick.opengl.InternalTextureLoader;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.pbuffer.GraphicsFactory;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.Log;











public class Image
  implements Renderable
{
  public static final int TOP_LEFT = 0;
  public static final int TOP_RIGHT = 1;
  public static final int BOTTOM_RIGHT = 2;
  public static final int BOTTOM_LEFT = 3;
  protected static SGL GL = ;
  

  protected static Image inUse;
  

  public static final int FILTER_LINEAR = 1;
  
  public static final int FILTER_NEAREST = 2;
  
  protected Texture texture;
  
  protected int width;
  
  protected int height;
  
  protected float textureWidth;
  
  protected float textureHeight;
  
  protected float textureOffsetX;
  
  protected float textureOffsetY;
  
  protected float angle;
  
  protected float alpha = 1.0F;
  
  protected String ref;
  
  protected boolean inited = false;
  

  protected byte[] pixelData;
  

  protected boolean destroyed;
  

  protected float centerX;
  
  protected float centerY;
  
  protected String name;
  
  protected Color[] corners;
  
  private int filter = 9729;
  


  private boolean flipped;
  

  private Color transparent;
  


  protected Image(Image other)
  {
    width = other.getWidth();
    height = other.getHeight();
    texture = texture;
    textureWidth = textureWidth;
    textureHeight = textureHeight;
    ref = ref;
    textureOffsetX = textureOffsetX;
    textureOffsetY = textureOffsetY;
    
    centerX = (width / 2);
    centerY = (height / 2);
    inited = true;
  }
  





  protected Image() {}
  




  public Image(Texture texture)
  {
    this.texture = texture;
    ref = texture.toString();
    clampTexture();
  }
  






  public Image(String ref)
    throws SlickException
  {
    this(ref, false);
  }
  





  public Image(String ref, Color trans)
    throws SlickException
  {
    this(ref, false, 1, trans);
  }
  





  public Image(String ref, boolean flipped)
    throws SlickException
  {
    this(ref, flipped, 1);
  }
  






  public Image(String ref, boolean flipped, int filter)
    throws SlickException
  {
    this(ref, flipped, filter, null);
  }
  







  public Image(String ref, boolean flipped, int f, Color transparent)
    throws SlickException
  {
    filter = (f == 1 ? 9729 : 9728);
    this.transparent = transparent;
    this.flipped = flipped;
    try
    {
      this.ref = ref;
      int[] trans = null;
      if (transparent != null) {
        trans = new int[3];
        trans[0] = ((int)(r * 255.0F));
        trans[1] = ((int)(g * 255.0F));
        trans[2] = ((int)(b * 255.0F));
      }
      texture = InternalTextureLoader.get().getTexture(ref, flipped, filter, trans);
    } catch (IOException e) {
      Log.error(e);
      throw new SlickException("Failed to load image from: " + ref, e);
    }
  }
  





  public void setFilter(int f)
  {
    filter = (f == 1 ? 9729 : 9728);
    
    texture.bind();
    GL.glTexParameteri(3553, 10241, filter);
    GL.glTexParameteri(3553, 10240, filter);
  }
  





  public Image(int width, int height)
    throws SlickException
  {
    this(width, height, 2);
  }
  






  public Image(int width, int height, int f)
    throws SlickException
  {
    ref = super.toString();
    filter = (f == 1 ? 9729 : 9728);
    try
    {
      texture = InternalTextureLoader.get().createTexture(width, height, filter);
    } catch (IOException e) {
      Log.error(e);
      throw new SlickException("Failed to create empty image " + width + "x" + height);
    }
    
    init();
  }
  






  public Image(InputStream in, String ref, boolean flipped)
    throws SlickException
  {
    this(in, ref, flipped, 1);
  }
  







  public Image(InputStream in, String ref, boolean flipped, int filter)
    throws SlickException
  {
    load(in, ref, flipped, filter, null);
  }
  




  Image(ImageBuffer buffer)
  {
    this(buffer, 1);
    TextureImpl.bindNone();
  }
  





  Image(ImageBuffer buffer, int filter)
  {
    this(buffer, filter);
    TextureImpl.bindNone();
  }
  




  public Image(ImageData data)
  {
    this(data, 1);
  }
  




  public Image(ImageData data, int f)
  {
    try
    {
      filter = (f == 1 ? 9729 : 9728);
      texture = InternalTextureLoader.get().getTexture(data, filter);
      ref = texture.toString();
    } catch (IOException e) {
      Log.error(e);
    }
  }
  




  public int getFilter()
  {
    return filter;
  }
  





  public String getResourceReference()
  {
    return ref;
  }
  







  public void setImageColor(float r, float g, float b, float a)
  {
    setColor(0, r, g, b, a);
    setColor(1, r, g, b, a);
    setColor(3, r, g, b, a);
    setColor(2, r, g, b, a);
  }
  






  public void setImageColor(float r, float g, float b)
  {
    setColor(0, r, g, b);
    setColor(1, r, g, b);
    setColor(3, r, g, b);
    setColor(2, r, g, b);
  }
  









  public void setColor(int corner, float r, float g, float b, float a)
  {
    if (corners == null) {
      corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
    }
    
    corners[corner].r = r;
    corners[corner].g = g;
    corners[corner].b = b;
    corners[corner].a = a;
  }
  








  public void setColor(int corner, float r, float g, float b)
  {
    if (corners == null) {
      corners = new Color[] { new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F), new Color(1.0F, 1.0F, 1.0F, 1.0F) };
    }
    
    corners[corner].r = r;
    corners[corner].g = g;
    corners[corner].b = b;
  }
  


  public void clampTexture()
  {
    if (GL.canTextureMirrorClamp()) {
      GL.glTexParameteri(3553, 10242, 34627);
      GL.glTexParameteri(3553, 10243, 34627);
    } else {
      GL.glTexParameteri(3553, 10242, 10496);
      GL.glTexParameteri(3553, 10243, 10496);
    }
  }
  





  public void setName(String name)
  {
    this.name = name;
  }
  




  public String getName()
  {
    return name;
  }
  




  public Graphics getGraphics()
    throws SlickException
  {
    return GraphicsFactory.getGraphicsForImage(this);
  }
  








  private void load(InputStream in, String ref, boolean flipped, int f, Color transparent)
    throws SlickException
  {
    filter = (f == 1 ? 9729 : 9728);
    try
    {
      this.ref = ref;
      int[] trans = null;
      if (transparent != null) {
        trans = new int[3];
        trans[0] = ((int)(r * 255.0F));
        trans[1] = ((int)(g * 255.0F));
        trans[2] = ((int)(b * 255.0F));
      }
      texture = InternalTextureLoader.get().getTexture(in, ref, flipped, filter, trans);
    } catch (IOException e) {
      Log.error(e);
      throw new SlickException("Failed to load image from: " + ref, e);
    }
  }
  


  public void bind()
  {
    texture.bind();
  }
  


  protected void reinit()
  {
    inited = false;
    init();
  }
  


  protected final void init()
  {
    if (inited) {
      return;
    }
    
    inited = true;
    if (texture != null) {
      width = texture.getImageWidth();
      height = texture.getImageHeight();
      textureOffsetX = 0.0F;
      textureOffsetY = 0.0F;
      textureWidth = texture.getWidth();
      textureHeight = texture.getHeight();
    }
    
    initImpl();
    
    centerX = (width / 2);
    centerY = (height / 2);
  }
  




  protected void initImpl() {}
  



  public void draw()
  {
    draw(0.0F, 0.0F);
  }
  





  public void drawCentered(float x, float y)
  {
    draw(x - getWidth() / 2, y - getHeight() / 2);
  }
  





  public void draw(float x, float y)
  {
    init();
    draw(x, y, width, height);
  }
  






  public void draw(float x, float y, Color filter)
  {
    init();
    draw(x, y, width, height, filter);
  }
  







  public void drawEmbedded(float x, float y, float width, float height)
  {
    init();
    
    if (corners == null) {
      GL.glTexCoord2f(textureOffsetX, textureOffsetY);
      GL.glVertex3f(x, y, 0.0F);
      GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
      GL.glVertex3f(x, y + height, 0.0F);
      GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY + 
        textureHeight);
      GL.glVertex3f(x + width, y + height, 0.0F);
      GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
      GL.glVertex3f(x + width, y, 0.0F);
    } else {
      corners[0].bind();
      GL.glTexCoord2f(textureOffsetX, textureOffsetY);
      GL.glVertex3f(x, y, 0.0F);
      corners[3].bind();
      GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
      GL.glVertex3f(x, y + height, 0.0F);
      corners[2].bind();
      GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY + 
        textureHeight);
      GL.glVertex3f(x + width, y + height, 0.0F);
      corners[1].bind();
      GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
      GL.glVertex3f(x + width, y, 0.0F);
    }
  }
  




  public float getTextureOffsetX()
  {
    init();
    
    return textureOffsetX;
  }
  




  public float getTextureOffsetY()
  {
    init();
    
    return textureOffsetY;
  }
  




  public float getTextureWidth()
  {
    init();
    
    return textureWidth;
  }
  




  public float getTextureHeight()
  {
    init();
    
    return textureHeight;
  }
  






  public void draw(float x, float y, float scale)
  {
    init();
    draw(x, y, width * scale, height * scale, Color.white);
  }
  







  public void draw(float x, float y, float scale, Color filter)
  {
    init();
    draw(x, y, width * scale, height * scale, filter);
  }
  











  public void draw(float x, float y, float width, float height)
  {
    init();
    draw(x, y, width, height, Color.white);
  }
  







  public void drawSheared(float x, float y, float hshear, float vshear)
  {
    drawSheared(x, y, hshear, vshear, Color.white);
  }
  







  public void drawSheared(float x, float y, float hshear, float vshear, Color filter)
  {
    if (alpha != 1.0F) {
      if (filter == null) {
        filter = Color.white;
      }
      
      filter = new Color(filter);
      a *= alpha;
    }
    if (filter != null) {
      filter.bind();
    }
    
    texture.bind();
    
    GL.glTranslatef(x, y, 0.0F);
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    
    GL.glBegin(7);
    init();
    
    GL.glTexCoord2f(textureOffsetX, textureOffsetY);
    GL.glVertex3f(0.0F, 0.0F, 0.0F);
    GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
    GL.glVertex3f(hshear, height, 0.0F);
    GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY + 
      textureHeight);
    GL.glVertex3f(width + hshear, height + vshear, 0.0F);
    GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
    GL.glVertex3f(width, vshear, 0.0F);
    GL.glEnd();
    
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(-angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    GL.glTranslatef(-x, -y, 0.0F);
  }
  








  public void draw(float x, float y, float width, float height, Color filter)
  {
    if (alpha != 1.0F) {
      if (filter == null) {
        filter = Color.white;
      }
      
      filter = new Color(filter);
      a *= alpha;
    }
    if (filter != null) {
      filter.bind();
    }
    
    texture.bind();
    
    GL.glTranslatef(x, y, 0.0F);
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    
    GL.glBegin(7);
    drawEmbedded(0.0F, 0.0F, width, height);
    GL.glEnd();
    
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(-angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    GL.glTranslatef(-x, -y, 0.0F);
  }
  







  public void drawFlash(float x, float y, float width, float height)
  {
    drawFlash(x, y, width, height, Color.white);
  }
  





  public void setCenterOfRotation(float x, float y)
  {
    centerX = x;
    centerY = y;
  }
  




  public float getCenterOfRotationX()
  {
    init();
    
    return centerX;
  }
  




  public float getCenterOfRotationY()
  {
    init();
    
    return centerY;
  }
  








  public void drawFlash(float x, float y, float width, float height, Color col)
  {
    init();
    
    col.bind();
    texture.bind();
    
    if (GL.canSecondaryColor()) {
      GL.glEnable(33880);
      GL.glSecondaryColor3ubEXT((byte)(int)(r * 255.0F), 
        (byte)(int)(g * 255.0F), 
        (byte)(int)(b * 255.0F));
    }
    
    GL.glTexEnvi(8960, 8704, 8448);
    
    GL.glTranslatef(x, y, 0.0F);
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    
    GL.glBegin(7);
    drawEmbedded(0.0F, 0.0F, width, height);
    GL.glEnd();
    
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(-angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    GL.glTranslatef(-x, -y, 0.0F);
    
    if (GL.canSecondaryColor()) {
      GL.glDisable(33880);
    }
  }
  





  public void drawFlash(float x, float y)
  {
    drawFlash(x, y, getWidth(), getHeight());
  }
  





  public void setRotation(float angle)
  {
    this.angle = (angle % 360.0F);
  }
  





  public float getRotation()
  {
    return angle;
  }
  




  public float getAlpha()
  {
    return alpha;
  }
  




  public void setAlpha(float alpha)
  {
    this.alpha = alpha;
  }
  





  public void rotate(float angle)
  {
    this.angle += angle;
    this.angle %= 360.0F;
  }
  









  public Image getSubImage(int x, int y, int width, int height)
  {
    init();
    
    float newTextureOffsetX = x / this.width * textureWidth + textureOffsetX;
    float newTextureOffsetY = y / this.height * textureHeight + textureOffsetY;
    float newTextureWidth = width / this.width * textureWidth;
    float newTextureHeight = height / this.height * textureHeight;
    
    Image sub = new Image();
    inited = true;
    texture = texture;
    textureOffsetX = newTextureOffsetX;
    textureOffsetY = newTextureOffsetY;
    textureWidth = newTextureWidth;
    textureHeight = newTextureHeight;
    
    width = width;
    height = height;
    ref = ref;
    centerX = (width / 2);
    centerY = (height / 2);
    
    return sub;
  }
  









  public void draw(float x, float y, float srcx, float srcy, float srcx2, float srcy2)
  {
    draw(x, y, x + width, y + height, srcx, srcy, srcx2, srcy2);
  }
  











  public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
  {
    draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, Color.white);
  }
  












  public void draw(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
  {
    init();
    
    if (alpha != 1.0F) {
      if (filter == null) {
        filter = Color.white;
      }
      
      filter = new Color(filter);
      a *= alpha;
    }
    filter.bind();
    texture.bind();
    
    GL.glTranslatef(x, y, 0.0F);
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    
    GL.glBegin(7);
    drawEmbedded(0.0F, 0.0F, x2 - x, y2 - y, srcx, srcy, srcx2, srcy2);
    GL.glEnd();
    
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(-angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    GL.glTranslatef(-x, -y, 0.0F);
  }
  
















  public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
  {
    drawEmbedded(x, y, x2, y2, srcx, srcy, srcx2, srcy2, null);
  }
  













  public void drawEmbedded(float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color filter)
  {
    if (filter != null) {
      filter.bind();
    }
    
    float mywidth = x2 - x;
    float myheight = y2 - y;
    float texwidth = srcx2 - srcx;
    float texheight = srcy2 - srcy;
    
    float newTextureOffsetX = srcx / width * textureWidth + 
      textureOffsetX;
    float newTextureOffsetY = srcy / height * textureHeight + 
      textureOffsetY;
    float newTextureWidth = texwidth / width * 
      textureWidth;
    float newTextureHeight = texheight / height * 
      textureHeight;
    
    GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY);
    GL.glVertex3f(x, y, 0.0F);
    GL.glTexCoord2f(newTextureOffsetX, newTextureOffsetY + 
      newTextureHeight);
    GL.glVertex3f(x, y + myheight, 0.0F);
    GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, 
      newTextureOffsetY + newTextureHeight);
    GL.glVertex3f(x + mywidth, y + myheight, 0.0F);
    GL.glTexCoord2f(newTextureOffsetX + newTextureWidth, 
      newTextureOffsetY);
    GL.glVertex3f(x + mywidth, y, 0.0F);
  }
  












  public void drawWarped(float x1, float y1, float x2, float y2, float x3, float y3, float x4, float y4)
  {
    Color.white.bind();
    texture.bind();
    
    GL.glTranslatef(x1, y1, 0.0F);
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    
    GL.glBegin(7);
    init();
    
    GL.glTexCoord2f(textureOffsetX, textureOffsetY);
    GL.glVertex3f(0.0F, 0.0F, 0.0F);
    GL.glTexCoord2f(textureOffsetX, textureOffsetY + textureHeight);
    GL.glVertex3f(x2 - x1, y2 - y1, 0.0F);
    GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY + 
      textureHeight);
    GL.glVertex3f(x3 - x1, y3 - y1, 0.0F);
    GL.glTexCoord2f(textureOffsetX + textureWidth, textureOffsetY);
    GL.glVertex3f(x4 - x1, y4 - y1, 0.0F);
    GL.glEnd();
    
    if (angle != 0.0F) {
      GL.glTranslatef(centerX, centerY, 0.0F);
      GL.glRotatef(-angle, 0.0F, 0.0F, 1.0F);
      GL.glTranslatef(-centerX, -centerY, 0.0F);
    }
    GL.glTranslatef(-x1, -y1, 0.0F);
  }
  




  public int getWidth()
  {
    init();
    return width;
  }
  




  public int getHeight()
  {
    init();
    return height;
  }
  





  public Image copy()
  {
    init();
    return getSubImage(0, 0, width, height);
  }
  





  public Image getScaledCopy(float scale)
  {
    init();
    return getScaledCopy((int)(width * scale), (int)(height * scale));
  }
  






  public Image getScaledCopy(int width, int height)
  {
    init();
    Image image = copy();
    width = width;
    height = height;
    centerX = (width / 2);
    centerY = (height / 2);
    return image;
  }
  


  public void ensureInverted()
  {
    if (textureHeight > 0.0F) {
      textureOffsetY += textureHeight;
      textureHeight = (-textureHeight);
    }
  }
  






  public Image getFlippedCopy(boolean flipHorizontal, boolean flipVertical)
  {
    init();
    Image image = copy();
    
    if (flipHorizontal) {
      textureOffsetX += textureWidth;
      textureWidth = (-textureWidth);
    }
    if (flipVertical) {
      textureOffsetY += textureHeight;
      textureHeight = (-textureHeight);
    }
    
    return image;
  }
  




  public void endUse()
  {
    if (inUse != this) {
      throw new RuntimeException("The sprite sheet is not currently in use");
    }
    inUse = null;
    GL.glEnd();
  }
  





  public void startUse()
  {
    if (inUse != null) {
      throw new RuntimeException("Attempt to start use of a sprite sheet before ending use with another - see endUse()");
    }
    inUse = this;
    init();
    
    Color.white.bind();
    texture.bind();
    GL.glBegin(7);
  }
  


  public String toString()
  {
    init();
    
    return "[Image " + ref + " " + width + "x" + height + "  " + textureOffsetX + "," + textureOffsetY + "," + textureWidth + "," + textureHeight + "]";
  }
  




  public Texture getTexture()
  {
    return texture;
  }
  




  public void setTexture(Texture texture)
  {
    this.texture = texture;
    reinit();
  }
  





  private int translate(byte b)
  {
    if (b < 0) {
      return 256 + b;
    }
    
    return b;
  }
  






  public Color getColor(int x, int y)
  {
    if (pixelData == null) {
      pixelData = texture.getTextureData();
    }
    
    int xo = (int)(textureOffsetX * texture.getTextureWidth());
    int yo = (int)(textureOffsetY * texture.getTextureHeight());
    
    if (textureWidth < 0.0F) {
      x = xo - x;
    } else {
      x = xo + x;
    }
    
    if (textureHeight < 0.0F) {
      y = yo - y;
    } else {
      y = yo + y;
    }
    
    int offset = x + y * texture.getTextureWidth();
    offset *= (texture.hasAlpha() ? 4 : 3);
    
    if (texture.hasAlpha()) {
      return new Color(translate(pixelData[offset]), translate(pixelData[(offset + 1)]), 
        translate(pixelData[(offset + 2)]), translate(pixelData[(offset + 3)]));
    }
    return new Color(translate(pixelData[offset]), translate(pixelData[(offset + 1)]), 
      translate(pixelData[(offset + 2)]));
  }
  





  public boolean isDestroyed()
  {
    return destroyed;
  }
  




  public void destroy()
    throws SlickException
  {
    if (isDestroyed()) {
      return;
    }
    
    destroyed = true;
    texture.release();
    GraphicsFactory.releaseGraphicsForImage(this);
  }
  


  public void flushPixelData()
  {
    pixelData = null;
  }
}
