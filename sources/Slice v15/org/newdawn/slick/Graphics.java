package org.newdawn.slick;

import java.nio.ByteBuffer;
import java.nio.DoubleBuffer;
import java.nio.FloatBuffer;
import java.security.AccessController;
import java.security.PrivilegedAction;
import java.util.ArrayList;
import org.lwjgl.BufferUtils;
import org.newdawn.slick.geom.Rectangle;
import org.newdawn.slick.geom.Shape;
import org.newdawn.slick.geom.ShapeRenderer;
import org.newdawn.slick.opengl.Texture;
import org.newdawn.slick.opengl.TextureImpl;
import org.newdawn.slick.opengl.renderer.LineStripRenderer;
import org.newdawn.slick.opengl.renderer.Renderer;
import org.newdawn.slick.opengl.renderer.SGL;
import org.newdawn.slick.util.FastTrig;
import org.newdawn.slick.util.Log;







public class Graphics
{
  protected static SGL GL = ;
  
  private static LineStripRenderer LSR = Renderer.getLineStripRenderer();
  

  public static int MODE_NORMAL = 1;
  

  public static int MODE_ALPHA_MAP = 2;
  

  public static int MODE_ALPHA_BLEND = 3;
  

  public static int MODE_COLOR_MULTIPLY = 4;
  

  public static int MODE_ADD = 5;
  

  public static int MODE_SCREEN = 6;
  

  private static final int DEFAULT_SEGMENTS = 50;
  

  protected static Graphics currentGraphics = null;
  

  protected static Font DEFAULT_FONT;
  

  private float sx = 1.0F;
  
  private float sy = 1.0F;
  

  private Font font;
  

  public static void setCurrent(Graphics current)
  {
    if (currentGraphics != current) {
      if (currentGraphics != null) {
        currentGraphics.disable();
      }
      currentGraphics = current;
      currentGraphics.enable();
    }
  }
  




  private Color currentColor = Color.white;
  

  protected int screenWidth;
  

  protected int screenHeight;
  

  private boolean pushed;
  

  private Rectangle clip;
  

  private DoubleBuffer worldClip = BufferUtils.createDoubleBuffer(4);
  

  private ByteBuffer readBuffer = BufferUtils.createByteBuffer(4);
  

  private boolean antialias;
  

  private Rectangle worldClipRecord;
  

  private int currentDrawingMode = MODE_NORMAL;
  

  private float lineWidth = 1.0F;
  

  private ArrayList stack = new ArrayList();
  




  private int stackIndex;
  




  public Graphics() {}
  




  public Graphics(int width, int height)
  {
    if (DEFAULT_FONT == null) {
      AccessController.doPrivileged(new PrivilegedAction() {
        public Object run() {
          try {
            Graphics.DEFAULT_FONT = new AngelCodeFont(
              "org/newdawn/slick/data/defaultfont.fnt", 
              "org/newdawn/slick/data/defaultfont.png");
          } catch (SlickException e) {
            Log.error(e);
          }
          return null;
        }
      });
    }
    
    font = DEFAULT_FONT;
    screenWidth = width;
    screenHeight = height;
  }
  





  void setDimensions(int width, int height)
  {
    screenWidth = width;
    screenHeight = height;
  }
  









  public void setDrawMode(int mode)
  {
    predraw();
    currentDrawingMode = mode;
    if (currentDrawingMode == MODE_NORMAL) {
      GL.glEnable(3042);
      GL.glColorMask(true, true, true, true);
      GL.glBlendFunc(770, 771);
    }
    if (currentDrawingMode == MODE_ALPHA_MAP) {
      GL.glDisable(3042);
      GL.glColorMask(false, false, false, true);
    }
    if (currentDrawingMode == MODE_ALPHA_BLEND) {
      GL.glEnable(3042);
      GL.glColorMask(true, true, true, false);
      GL.glBlendFunc(772, 773);
    }
    if (currentDrawingMode == MODE_COLOR_MULTIPLY) {
      GL.glEnable(3042);
      GL.glColorMask(true, true, true, true);
      GL.glBlendFunc(769, 768);
    }
    if (currentDrawingMode == MODE_ADD) {
      GL.glEnable(3042);
      GL.glColorMask(true, true, true, true);
      GL.glBlendFunc(1, 1);
    }
    if (currentDrawingMode == MODE_SCREEN) {
      GL.glEnable(3042);
      GL.glColorMask(true, true, true, true);
      GL.glBlendFunc(1, 769);
    }
    postdraw();
  }
  




  public void clearAlphaMap()
  {
    pushTransform();
    GL.glLoadIdentity();
    
    int originalMode = currentDrawingMode;
    setDrawMode(MODE_ALPHA_MAP);
    setColor(new Color(0, 0, 0, 0));
    fillRect(0.0F, 0.0F, screenWidth, screenHeight);
    setColor(currentColor);
    setDrawMode(originalMode);
    
    popTransform();
  }
  



  private void predraw()
  {
    setCurrent(this);
  }
  




  private void postdraw() {}
  




  protected void enable() {}
  



  public void flush()
  {
    if (currentGraphics == this) {
      currentGraphics.disable();
      currentGraphics = null;
    }
  }
  




  protected void disable() {}
  




  public Font getFont()
  {
    return font;
  }
  







  public void setBackground(Color color)
  {
    predraw();
    GL.glClearColor(r, g, b, a);
    postdraw();
  }
  




  public Color getBackground()
  {
    predraw();
    FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
    GL.glGetFloat(3106, buffer);
    postdraw();
    
    return new Color(buffer);
  }
  


  public void clear()
  {
    predraw();
    GL.glClear(16384);
    postdraw();
  }
  


  public void resetTransform()
  {
    sx = 1.0F;
    sy = 1.0F;
    
    if (pushed) {
      predraw();
      GL.glPopMatrix();
      pushed = false;
      postdraw();
    }
  }
  


  private void checkPush()
  {
    if (!pushed) {
      predraw();
      GL.glPushMatrix();
      pushed = true;
      postdraw();
    }
  }
  







  public void scale(float sx, float sy)
  {
    this.sx *= sx;
    this.sy *= sy;
    
    checkPush();
    
    predraw();
    GL.glScalef(sx, sy, 1.0F);
    postdraw();
  }
  









  public void rotate(float rx, float ry, float ang)
  {
    checkPush();
    
    predraw();
    translate(rx, ry);
    GL.glRotatef(ang, 0.0F, 0.0F, 1.0F);
    translate(-rx, -ry);
    postdraw();
  }
  







  public void translate(float x, float y)
  {
    checkPush();
    
    predraw();
    GL.glTranslatef(x, y, 0.0F);
    postdraw();
  }
  





  public void setFont(Font font)
  {
    this.font = font;
  }
  


  public void resetFont()
  {
    font = DEFAULT_FONT;
  }
  





  public void setColor(Color color)
  {
    if (color == null) {
      return;
    }
    
    currentColor = new Color(color);
    predraw();
    currentColor.bind();
    postdraw();
  }
  




  public Color getColor()
  {
    return new Color(currentColor);
  }
  











  public void drawLine(float x1, float y1, float x2, float y2)
  {
    float lineWidth = this.lineWidth - 1.0F;
    
    if (LSR.applyGLLineFixes()) {
      if (x1 == x2) {
        if (y1 > y2) {
          float temp = y2;
          y2 = y1;
          y1 = temp;
        }
        float step = 1.0F / sy;
        lineWidth /= sy;
        fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, lineWidth + step, y2 - y1 + lineWidth + step);
        return; }
      if (y1 == y2) {
        if (x1 > x2) {
          float temp = x2;
          x2 = x1;
          x1 = temp;
        }
        float step = 1.0F / sx;
        lineWidth /= sx;
        fillRect(x1 - lineWidth / 2.0F, y1 - lineWidth / 2.0F, x2 - x1 + lineWidth + step, lineWidth + step);
        return;
      }
    }
    
    predraw();
    currentColor.bind();
    TextureImpl.bindNone();
    
    LSR.start();
    LSR.vertex(x1, y1);
    LSR.vertex(x2, y2);
    LSR.end();
    
    postdraw();
  }
  







  public void draw(Shape shape, ShapeFill fill)
  {
    predraw();
    TextureImpl.bindNone();
    
    ShapeRenderer.draw(shape, fill);
    
    currentColor.bind();
    postdraw();
  }
  







  public void fill(Shape shape, ShapeFill fill)
  {
    predraw();
    TextureImpl.bindNone();
    
    ShapeRenderer.fill(shape, fill);
    
    currentColor.bind();
    postdraw();
  }
  





  public void draw(Shape shape)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    ShapeRenderer.draw(shape);
    
    postdraw();
  }
  





  public void fill(Shape shape)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    ShapeRenderer.fill(shape);
    
    postdraw();
  }
  







  public void texture(Shape shape, Image image)
  {
    texture(shape, image, 0.01F, 0.01F, false);
  }
  









  public void texture(Shape shape, Image image, ShapeFill fill)
  {
    texture(shape, image, 0.01F, 0.01F, fill);
  }
  









  public void texture(Shape shape, Image image, boolean fit)
  {
    if (fit) {
      texture(shape, image, 1.0F, 1.0F, true);
    } else {
      texture(shape, image, 0.01F, 0.01F, false);
    }
  }
  











  public void texture(Shape shape, Image image, float scaleX, float scaleY)
  {
    texture(shape, image, scaleX, scaleY, false);
  }
  














  public void texture(Shape shape, Image image, float scaleX, float scaleY, boolean fit)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    if (fit) {
      ShapeRenderer.textureFit(shape, image, scaleX, scaleY);
    } else {
      ShapeRenderer.texture(shape, image, scaleX, scaleY);
    }
    
    postdraw();
  }
  














  public void texture(Shape shape, Image image, float scaleX, float scaleY, ShapeFill fill)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    ShapeRenderer.texture(shape, image, scaleX, scaleY, fill);
    
    postdraw();
  }
  











  public void drawRect(float x1, float y1, float width, float height)
  {
    float lineWidth = getLineWidth();
    
    drawLine(x1, y1, x1 + width, y1);
    drawLine(x1 + width, y1, x1 + width, y1 + height);
    drawLine(x1 + width, y1 + height, x1, y1 + height);
    drawLine(x1, y1 + height, x1, y1);
  }
  



  public void clearClip()
  {
    clip = null;
    predraw();
    GL.glDisable(3089);
    postdraw();
  }
  














  public void setWorldClip(float x, float y, float width, float height)
  {
    predraw();
    worldClipRecord = new Rectangle(x, y, width, height);
    
    GL.glEnable(12288);
    worldClip.put(1.0D).put(0.0D).put(0.0D).put(-x).flip();
    GL.glClipPlane(12288, worldClip);
    GL.glEnable(12289);
    worldClip.put(-1.0D).put(0.0D).put(0.0D).put(x + width).flip();
    GL.glClipPlane(12289, worldClip);
    
    GL.glEnable(12290);
    worldClip.put(0.0D).put(1.0D).put(0.0D).put(-y).flip();
    GL.glClipPlane(12290, worldClip);
    GL.glEnable(12291);
    worldClip.put(0.0D).put(-1.0D).put(0.0D).put(y + height).flip();
    GL.glClipPlane(12291, worldClip);
    postdraw();
  }
  


  public void clearWorldClip()
  {
    predraw();
    worldClipRecord = null;
    GL.glDisable(12288);
    GL.glDisable(12289);
    GL.glDisable(12290);
    GL.glDisable(12291);
    postdraw();
  }
  






  public void setWorldClip(Rectangle clip)
  {
    if (clip == null) {
      clearWorldClip();
    } else {
      setWorldClip(clip.getX(), clip.getY(), clip.getWidth(), clip
        .getHeight());
    }
  }
  




  public Rectangle getWorldClip()
  {
    return worldClipRecord;
  }
  













  public void setClip(int x, int y, int width, int height)
  {
    predraw();
    
    if (clip == null) {
      GL.glEnable(3089);
      clip = new Rectangle(x, y, width, height);
    } else {
      clip.setBounds(x, y, width, height);
    }
    
    GL.glScissor(x, screenHeight - y - height, width, height);
    postdraw();
  }
  








  public void setClip(Rectangle rect)
  {
    if (rect == null) {
      clearClip();
      return;
    }
    
    setClip((int)rect.getX(), (int)rect.getY(), (int)rect.getWidth(), 
      (int)rect.getHeight());
  }
  





  public Rectangle getClip()
  {
    return clip;
  }
  



















  public void fillRect(float x, float y, float width, float height, Image pattern, float offX, float offY)
  {
    int cols = (int)Math.ceil(width / pattern.getWidth()) + 2;
    int rows = (int)Math.ceil(height / pattern.getHeight()) + 2;
    
    Rectangle preClip = getWorldClip();
    setWorldClip(x, y, width, height);
    
    predraw();
    
    for (int c = 0; c < cols; c++) {
      for (int r = 0; r < rows; r++) {
        pattern.draw(c * pattern.getWidth() + x - offX, r * 
          pattern.getHeight() + y - offY);
      }
    }
    postdraw();
    
    setWorldClip(preClip);
  }
  











  public void fillRect(float x1, float y1, float width, float height)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    GL.glBegin(7);
    GL.glVertex2f(x1, y1);
    GL.glVertex2f(x1 + width, y1);
    GL.glVertex2f(x1 + width, y1 + height);
    GL.glVertex2f(x1, y1 + height);
    GL.glEnd();
    postdraw();
  }
  













  public void drawOval(float x1, float y1, float width, float height)
  {
    drawOval(x1, y1, width, height, 50);
  }
  
















  public void drawOval(float x1, float y1, float width, float height, int segments)
  {
    drawArc(x1, y1, width, height, segments, 0.0F, 360.0F);
  }
  


















  public void drawArc(float x1, float y1, float width, float height, float start, float end)
  {
    drawArc(x1, y1, width, height, 50, start, end);
  }
  




















  public void drawArc(float x1, float y1, float width, float height, int segments, float start, float end)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    while (end < start) {
      end += 360.0F;
    }
    
    float cx = x1 + width / 2.0F;
    float cy = y1 + height / 2.0F;
    
    LSR.start();
    int step = 360 / segments;
    
    for (int a = (int)start; a < (int)(end + step); a += step) {
      float ang = a;
      if (ang > end) {
        ang = end;
      }
      float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
      float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
      
      LSR.vertex(x, y);
    }
    LSR.end();
    postdraw();
  }
  













  public void fillOval(float x1, float y1, float width, float height)
  {
    fillOval(x1, y1, width, height, 50);
  }
  
















  public void fillOval(float x1, float y1, float width, float height, int segments)
  {
    fillArc(x1, y1, width, height, segments, 0.0F, 360.0F);
  }
  


















  public void fillArc(float x1, float y1, float width, float height, float start, float end)
  {
    fillArc(x1, y1, width, height, 50, start, end);
  }
  




















  public void fillArc(float x1, float y1, float width, float height, int segments, float start, float end)
  {
    predraw();
    TextureImpl.bindNone();
    currentColor.bind();
    
    while (end < start) {
      end += 360.0F;
    }
    
    float cx = x1 + width / 2.0F;
    float cy = y1 + height / 2.0F;
    
    GL.glBegin(6);
    int step = 360 / segments;
    
    GL.glVertex2f(cx, cy);
    
    for (int a = (int)start; a < (int)(end + step); a += step) {
      float ang = a;
      if (ang > end) {
        ang = end;
      }
      
      float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * width / 2.0D);
      float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * height / 2.0D);
      
      GL.glVertex2f(x, y);
    }
    GL.glEnd();
    
    if (antialias) {
      GL.glBegin(6);
      GL.glVertex2f(cx, cy);
      if (end != 360.0F) {
        end -= 10.0F;
      }
      
      for (int a = (int)start; a < (int)(end + step); a += step) {
        float ang = a;
        if (ang > end) {
          ang = end;
        }
        
        float x = (float)(cx + FastTrig.cos(Math.toRadians(ang + 10.0F)) * 
          width / 2.0D);
        float y = (float)(cy + FastTrig.sin(Math.toRadians(ang + 10.0F)) * 
          height / 2.0D);
        
        GL.glVertex2f(x, y);
      }
      GL.glEnd();
    }
    
    postdraw();
  }
  














  public void drawRoundRect(float x, float y, float width, float height, int cornerRadius)
  {
    drawRoundRect(x, y, width, height, cornerRadius, 50);
  }
  
















  public void drawRoundRect(float x, float y, float width, float height, int cornerRadius, int segs)
  {
    if (cornerRadius < 0)
      throw new IllegalArgumentException("corner radius must be > 0");
    if (cornerRadius == 0) {
      drawRect(x, y, width, height);
      return;
    }
    
    int mr = (int)Math.min(width, height) / 2;
    
    if (cornerRadius > mr) {
      cornerRadius = mr;
    }
    
    drawLine(x + cornerRadius, y, x + width - cornerRadius, y);
    drawLine(x, y + cornerRadius, x, y + height - cornerRadius);
    drawLine(x + width, y + cornerRadius, x + width, y + height - 
      cornerRadius);
    drawLine(x + cornerRadius, y + height, x + width - cornerRadius, y + 
      height);
    
    float d = cornerRadius * 2;
    
    drawArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
    
    drawArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
    
    drawArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
    
    drawArc(x, y, d, d, segs, 180.0F, 270.0F);
  }
  














  public void fillRoundRect(float x, float y, float width, float height, int cornerRadius)
  {
    fillRoundRect(x, y, width, height, cornerRadius, 50);
  }
  
















  public void fillRoundRect(float x, float y, float width, float height, int cornerRadius, int segs)
  {
    if (cornerRadius < 0)
      throw new IllegalArgumentException("corner radius must be > 0");
    if (cornerRadius == 0) {
      fillRect(x, y, width, height);
      return;
    }
    
    int mr = (int)Math.min(width, height) / 2;
    
    if (cornerRadius > mr) {
      cornerRadius = mr;
    }
    
    float d = cornerRadius * 2;
    
    fillRect(x + cornerRadius, y, width - d, cornerRadius);
    fillRect(x, y + cornerRadius, cornerRadius, height - d);
    fillRect(x + width - cornerRadius, y + cornerRadius, cornerRadius, 
      height - d);
    fillRect(x + cornerRadius, y + height - cornerRadius, width - d, 
      cornerRadius);
    fillRect(x + cornerRadius, y + cornerRadius, width - d, height - d);
    

    fillArc(x + width - d, y + height - d, d, d, segs, 0.0F, 90.0F);
    
    fillArc(x, y + height - d, d, d, segs, 90.0F, 180.0F);
    
    fillArc(x + width - d, y, d, d, segs, 270.0F, 360.0F);
    
    fillArc(x, y, d, d, segs, 180.0F, 270.0F);
  }
  






  public void setLineWidth(float width)
  {
    predraw();
    lineWidth = width;
    LSR.setWidth(width);
    GL.glPointSize(width);
    postdraw();
  }
  




  public float getLineWidth()
  {
    return lineWidth;
  }
  


  public void resetLineWidth()
  {
    predraw();
    
    Renderer.getLineStripRenderer().setWidth(1.0F);
    GL.glLineWidth(1.0F);
    GL.glPointSize(1.0F);
    
    postdraw();
  }
  





  public void setAntiAlias(boolean anti)
  {
    predraw();
    antialias = anti;
    LSR.setAntiAlias(anti);
    if (anti) {
      GL.glEnable(2881);
    } else {
      GL.glDisable(2881);
    }
    postdraw();
  }
  




  public boolean isAntiAlias()
  {
    return antialias;
  }
  









  public void drawString(String str, float x, float y)
  {
    predraw();
    font.drawString(x, y, str, currentColor);
    postdraw();
  }
  











  public void drawImage(Image image, float x, float y, Color col)
  {
    predraw();
    image.draw(x, y, col);
    currentColor.bind();
    postdraw();
  }
  









  public void drawAnimation(Animation anim, float x, float y)
  {
    drawAnimation(anim, x, y, Color.white);
  }
  











  public void drawAnimation(Animation anim, float x, float y, Color col)
  {
    predraw();
    anim.draw(x, y, col);
    currentColor.bind();
    postdraw();
  }
  









  public void drawImage(Image image, float x, float y)
  {
    drawImage(image, x, y, Color.white);
  }
  



























  public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2)
  {
    predraw();
    image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2);
    currentColor.bind();
    postdraw();
  }
  























  public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2)
  {
    drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), 
      srcx, srcy, srcx2, srcy2);
  }
  










  public void copyArea(Image target, int x, int y)
  {
    int format = target.getTexture().hasAlpha() ? 6408 : 6407;
    target.bind();
    GL.glCopyTexImage2D(3553, 0, format, x, screenHeight - (
      y + target.getHeight()), target.getTexture()
      .getTextureWidth(), target.getTexture().getTextureHeight(), 0);
    target.ensureInverted();
  }
  






  private int translate(byte b)
  {
    if (b < 0) {
      return 256 + b;
    }
    
    return b;
  }
  








  public Color getPixel(int x, int y)
  {
    predraw();
    GL.glReadPixels(x, screenHeight - y, 1, 1, 6408, 
      5121, readBuffer);
    postdraw();
    
    return new Color(translate(readBuffer.get(0)), translate(readBuffer
      .get(1)), translate(readBuffer.get(2)), translate(readBuffer
      .get(3)));
  }
  









  public void getArea(int x, int y, int width, int height, ByteBuffer target)
  {
    if (target.capacity() < width * height * 4)
    {
      throw new IllegalArgumentException("Byte buffer provided to get area is not big enough");
    }
    
    predraw();
    GL.glReadPixels(x, screenHeight - y - height, width, height, 6408, 
      5121, target);
    postdraw();
  }
  





























  public void drawImage(Image image, float x, float y, float x2, float y2, float srcx, float srcy, float srcx2, float srcy2, Color col)
  {
    predraw();
    image.draw(x, y, x2, y2, srcx, srcy, srcx2, srcy2, col);
    currentColor.bind();
    postdraw();
  }
  

























  public void drawImage(Image image, float x, float y, float srcx, float srcy, float srcx2, float srcy2, Color col)
  {
    drawImage(image, x, y, x + image.getWidth(), y + image.getHeight(), 
      srcx, srcy, srcx2, srcy2, col);
  }
  





























  public void drawGradientLine(float x1, float y1, float red1, float green1, float blue1, float alpha1, float x2, float y2, float red2, float green2, float blue2, float alpha2)
  {
    predraw();
    
    TextureImpl.bindNone();
    
    GL.glBegin(1);
    
    GL.glColor4f(red1, green1, blue1, alpha1);
    GL.glVertex2f(x1, y1);
    
    GL.glColor4f(red2, green2, blue2, alpha2);
    GL.glVertex2f(x2, y2);
    
    GL.glEnd();
    
    postdraw();
  }
  
















  public void drawGradientLine(float x1, float y1, Color Color1, float x2, float y2, Color Color2)
  {
    predraw();
    
    TextureImpl.bindNone();
    
    GL.glBegin(1);
    
    Color1.bind();
    GL.glVertex2f(x1, y1);
    
    Color2.bind();
    GL.glVertex2f(x2, y2);
    
    GL.glEnd();
    
    postdraw();
  }
  





  public void pushTransform()
  {
    predraw();
    
    FloatBuffer buffer;
    if (stackIndex >= stack.size()) {
      FloatBuffer buffer = BufferUtils.createFloatBuffer(18);
      stack.add(buffer);
    } else {
      buffer = (FloatBuffer)stack.get(stackIndex);
    }
    
    GL.glGetFloat(2982, buffer);
    buffer.put(16, sx);
    buffer.put(17, sy);
    stackIndex += 1;
    
    postdraw();
  }
  



  public void popTransform()
  {
    if (stackIndex == 0) {
      throw new RuntimeException("Attempt to pop a transform that hasn't be pushed");
    }
    
    predraw();
    
    stackIndex -= 1;
    FloatBuffer oldBuffer = (FloatBuffer)stack.get(stackIndex);
    GL.glLoadMatrix(oldBuffer);
    sx = oldBuffer.get(16);
    sy = oldBuffer.get(17);
    
    postdraw();
  }
  
  public void destroy() {}
}
