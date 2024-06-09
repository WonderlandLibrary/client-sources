package lunadevs.luna.utils.faithsminiutils;


import java.awt.Color;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import javax.imageio.ImageIO;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.entity.RenderItem;
import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import lunadevs.luna.utils.Box;
import lunadevs.luna.utils.faithsminiutils.MathUtils;

public class RenderUtils {

	public static final RenderItem RENDER_ITEM = new RenderItem(Minecraft.getMinecraft().renderEngine, 
		    Minecraft.getMinecraft().modelManager);
		  public static float playerViewY;
		  public static float playerViewX;
		  private static ScaledResolution scaledResolution;
		  
		  public static int applyTexture(int texId, BufferedImage image, boolean linear, boolean repeat)
		  {
		    int[] pixels = new int[image.getWidth() * image.getHeight()];
		    image.getRGB(0, 0, image.getWidth(), image.getHeight(), pixels, 0, image.getWidth());
		    
		    ByteBuffer buffer = BufferUtils.createByteBuffer(image.getWidth() * image.getHeight() * 4);
		    for (int y = 0; y < image.getHeight(); y++) {
		      for (int x = 0; x < image.getWidth(); x++)
		      {
		        int pixel = pixels[(y * image.getWidth() + x)];
		        buffer.put((byte)(pixel >> 16 & 0xFF));
		        buffer.put((byte)(pixel >> 8 & 0xFF));
		        buffer.put((byte)(pixel & 0xFF));
		        buffer.put((byte)(pixel >> 24 & 0xFF));
		      }
		    }
		    buffer.flip();
		    applyTexture(texId, image.getWidth(), image.getHeight(), buffer, linear, repeat);
		    
		    return texId;
		  }
		  
		  public static int applyTexture(int texId, File file, boolean linear, boolean repeat)
		    throws IOException
		  {
		    applyTexture(texId, ImageIO.read(file), linear, repeat);
		    
		    return texId;
		  }
		  
		  public static int applyTexture(int texId, int width, int height, ByteBuffer pixels, boolean linear, boolean repeat)
		  {
		    GL11.glBindTexture(3553, texId);
		    GL11.glTexParameteri(3553, 10241, linear ? 9729 : 9728);
		    GL11.glTexParameteri(3553, 10240, linear ? 9729 : 9728);
		    GL11.glTexParameteri(3553, 10242, repeat ? 10497 : 10496);
		    GL11.glTexParameteri(3553, 10243, repeat ? 10497 : 10496);
		    GL11.glPixelStorei(3317, 1);
		    GL11.glTexImage2D(3553, 0, 32856, width, height, 0, 6408, 5121, 
		      pixels);
		    return texId;
		  }
		  
		  public static void disableGL2D()
		  {
		    GL11.glEnable(3553);
		    GL11.glDisable(3042);
		    GL11.glEnable(2929);
		    GL11.glDisable(2848);
		    GL11.glHint(3154, 4352);
		    GL11.glHint(3155, 4352);
		  }
		  
		  public static void disableGL3D()
		  {
		    GL11.glEnable(3553);
		    GL11.glEnable(2929);
		    GL11.glDisable(3042);
		    GL11.glEnable(3008);
		    GL11.glDepthMask(true);
		    GL11.glCullFace(1029);
		    GL11.glDisable(2848);
		    GL11.glHint(3154, 4352);
		    GL11.glHint(3155, 4352);
		  }
		  
		  public static void drawBorderedRect(float x, float y, float x1, float y1, float width, int internalColor, int borderColor)
		  {
		    enableGL2D();
		    glColor(internalColor);
		    drawRect(x + width, y + width, x1 - width, y1 - width);
		    glColor(borderColor);
		    drawRect(x + width, y, x1 - width, y + width);
		    drawRect(x, y, x + width, y1);
		    drawRect(x1 - width, y, x1, y1);
		    drawRect(x + width, y1 - width, x1 - width, y1);
		    disableGL2D();
		  }
		  
		  public static void drawBorderedRect(float x, float y, float x1, float y1, int insideC, int borderC)
		  {
		    enableGL2D();
		    x *= 2.0F;
		    x1 *= 2.0F;
		    y *= 2.0F;
		    y1 *= 2.0F;
		    GL11.glScalef(0.5F, 0.5F, 0.5F);
		    drawVLine(x, y, y1 - 1.0F, borderC);
		    drawVLine(x1 - 1.0F, y, y1, borderC);
		    drawHLine(x, x1 - 1.0F, y, borderC);
		    drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
		    drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
		    GL11.glScalef(2.0F, 2.0F, 2.0F);
		    disableGL2D();
		  }
		  
		  public static void drawBorderedRect(Rectangle rectangle, float width, int internalColor, int borderColor)
		  {
		    float x = rectangle.x;
		    float y = rectangle.y;
		    float x1 = rectangle.x + rectangle.width;
		    float y1 = rectangle.y + rectangle.height;
		    enableGL2D();
		    glColor(internalColor);
		    drawRect(x + width, y + width, x1 - width, y1 - width);
		    glColor(borderColor);
		    drawRect(x + 1.0F, y, x1 - 1.0F, y + width);
		    drawRect(x, y, x + width, y1);
		    drawRect(x1 - width, y, x1, y1);
		    drawRect(x + 1.0F, y1 - width, x1 - 1.0F, y1);
		    disableGL2D();
		  }
		  
		  public static void drawBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int inside, int border)
		  {
		    enableGL2D();
		    drawRect(x, y, x1, y1, inside);
		    glColor(border);
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glLineWidth(lineWidth);
		    GL11.glBegin(3);
		    GL11.glVertex2f(x, y);
		    GL11.glVertex2f(x, y1);
		    GL11.glVertex2f(x1, y1);
		    GL11.glVertex2f(x1, y);
		    GL11.glVertex2f(x, y);
		    GL11.glEnd();
		    GL11.glEnable(3553);
		    GL11.glDisable(3042);
		    disableGL2D();
		  }
		  
		  public static void drawBox(Box box)
		  {
		    if (box == null) {
		      return;
		    }
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glEnd();
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glEnd();
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glEnd();
		    
		    GL11.glBegin(7);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glEnd();
		  }
		  
		  public static void drawCircle(float cx, float cy, float r, int num_segments, int c)
		  {
		    r *= 2.0F;
		    cx *= 2.0F;
		    cy *= 2.0F;
		    float f = (c >> 24 & 0xFF) / 255.0F;
		    float f1 = (c >> 16 & 0xFF) / 255.0F;
		    float f2 = (c >> 8 & 0xFF) / 255.0F;
		    float f3 = (c & 0xFF) / 255.0F;
		    float theta = (float)(6.2831852D / num_segments);
		    float p = (float)Math.cos(theta);
		    float s = (float)Math.sin(theta);
		    
		    float x = r;
		    float y = 0.0F;
		    enableGL2D();
		    GL11.glScalef(0.5F, 0.5F, 0.5F);
		    GL11.glColor4f(f1, f2, f3, f);
		    GL11.glBegin(2);
		    for (int ii = 0; ii < num_segments; ii++)
		    {
		      GL11.glVertex2f(x + cx, y + cy);
		      
		      float t = x;
		      x = p * x - s * y;
		      y = s * t + p * y;
		    }
		    GL11.glEnd();
		    GL11.glScalef(2.0F, 2.0F, 2.0F);
		    disableGL2D();
		  }
		  
		  public static void drawFullCircle(int cx, int cy, double r, int c)
		  {
		    r *= 2.0D;
		    cx *= 2;
		    cy *= 2;
		    float f = (c >> 24 & 0xFF) / 255.0F;
		    float f1 = (c >> 16 & 0xFF) / 255.0F;
		    float f2 = (c >> 8 & 0xFF) / 255.0F;
		    float f3 = (c & 0xFF) / 255.0F;
		    enableGL2D();
		    GL11.glScalef(0.5F, 0.5F, 0.5F);
		    GL11.glColor4f(f1, f2, f3, f);
		    GL11.glBegin(6);
		    for (int i = 0; i <= 360; i++)
		    {
		      double x = Math.sin(i * 3.141592653589793D / 180.0D) * r;
		      double y = Math.cos(i * 3.141592653589793D / 180.0D) * r;
		      GL11.glVertex2d(cx + x, cy + y);
		    }
		    GL11.glEnd();
		    GL11.glScalef(2.0F, 2.0F, 2.0F);
		    disableGL2D();
		  }
		  
		  public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
		  {
		    enableGL2D();
		    GL11.glPushMatrix();
		    glColor(col1);
		    GL11.glLineWidth(1.0F);
		    GL11.glBegin(1);
		    GL11.glVertex2d(x, y);
		    GL11.glVertex2d(x, y2);
		    GL11.glVertex2d(x2, y2);
		    GL11.glVertex2d(x2, y);
		    GL11.glVertex2d(x, y);
		    GL11.glVertex2d(x2, y);
		    GL11.glVertex2d(x, y2);
		    GL11.glVertex2d(x2, y2);
		    GL11.glEnd();
		    GL11.glPopMatrix();
		    drawGradientRect(x, y, x2, y2, col2, col3);
		    disableGL2D();
		  }
		  
		  public static void drawGradientBorderedRectReliant(float x, float y, float x1, float y1, float lineWidth, int border, int bottom, int top)
		  {
		    enableGL2D();
		    drawGradientRect(x, y, x1, y1, top, bottom);
		    glColor(border);
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glLineWidth(lineWidth);
		    GL11.glBegin(3);
		    GL11.glVertex2f(x, y);
		    GL11.glVertex2f(x, y1);
		    GL11.glVertex2f(x1, y1);
		    GL11.glVertex2f(x1, y);
		    GL11.glVertex2f(x, y);
		    GL11.glEnd();
		    GL11.glEnable(3553);
		    GL11.glDisable(3042);
		    disableGL2D();
		  }
		  
		  public static void drawGradientHRect(float x, float y, float x1, float y1, int topColor, int bottomColor)
		  {
		    enableGL2D();
		    GL11.glShadeModel(7425);
		    GL11.glBegin(7);
		    glColor(topColor);
		    GL11.glVertex2f(x, y);
		    GL11.glVertex2f(x, y1);
		    glColor(bottomColor);
		    GL11.glVertex2f(x1, y1);
		    GL11.glVertex2f(x1, y);
		    GL11.glEnd();
		    GL11.glShadeModel(7424);
		    disableGL2D();
		  }
		  
		  public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2)
		  {
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(2848);
		    GL11.glShadeModel(7425);
		    GL11.glPushMatrix();
		    GL11.glBegin(7);
		    glColor(col1);
		    GL11.glVertex2d(x2, y);
		    GL11.glVertex2d(x, y);
		    glColor(col2);
		    GL11.glVertex2d(x, y2);
		    GL11.glVertex2d(x2, y2);
		    GL11.glEnd();
		    GL11.glPopMatrix();
		    GL11.glEnable(3553);
		    GL11.glDisable(3042);
		    GL11.glDisable(2848);
		    GL11.glShadeModel(7424);
		  }
		  
		  public static void drawGradientRect(float x, float y, float x1, float y1, int topColor, int bottomColor)
		  {
		    enableGL2D();
		    GL11.glShadeModel(7425);
		    GL11.glBegin(7);
		    glColor(topColor);
		    GL11.glVertex2f(x, y1);
		    GL11.glVertex2f(x1, y1);
		    glColor(bottomColor);
		    GL11.glVertex2f(x1, y);
		    GL11.glVertex2f(x, y);
		    GL11.glEnd();
		    GL11.glShadeModel(7424);
		    disableGL2D();
		  }
		  
		  public static void drawHLine(float x, float y, float x1, int y1)
		  {
		    if (y < x)
		    {
		      float var5 = x;
		      x = y;
		      y = var5;
		    }
		    drawRect(x, x1, y + 1.0F, x1 + 1.0F, y1);
		  }
		  
		  public static void drawHLine(float x, float y, float x1, int y1, int y2)
		  {
		    if (y < x)
		    {
		      float var5 = x;
		      x = y;
		      y = var5;
		    }
		    drawGradientRect(x, x1, y + 1.0F, x1 + 1.0F, y1, y2);
		  }
		  
		  public static void drawLine(float x, float y, float x1, float y1, float width)
		  {
		    GL11.glDisable(3553);
		    GL11.glLineWidth(width);
		    GL11.glBegin(1);
		    GL11.glVertex2f(x, y);
		    GL11.glVertex2f(x1, y1);
		    GL11.glEnd();
		    GL11.glEnable(3553);
		  }
		  
		  public static void drawOutlinedBox(Box box)
		  {
		    if (box == null) {
		      return;
		    }
		    GL11.glBegin(3);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glEnd();
		    GL11.glBegin(3);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glEnd();
		    GL11.glBegin(1);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glEnd();
		  }
		  
		  public static void drawRect(float x, float y, float x1, float y1)
		  {
		    GL11.glBegin(7);
		    GL11.glVertex2f(x, y1);
		    GL11.glVertex2f(x1, y1);
		    GL11.glVertex2f(x1, y);
		    GL11.glVertex2f(x, y);
		    GL11.glEnd();
		  }
		  
		  public static void drawRect(float x, float y, float x1, float y1, float r, float g, float b, float a)
		  {
		    enableGL2D();
		    GL11.glColor4f(r, g, b, a);
		    drawRect(x, y, x1, y1);
		    disableGL2D();
		  }
		  
		  public static void drawRect(float x, float y, float x1, float y1, int color)
		  {
		    enableGL2D();
		    glColor(color);
		    drawRect(x, y, x1, y1);
		    disableGL2D();
		  }
		  
		  public static void drawRect(Rectangle rectangle, int color)
		  {
		    drawRect(rectangle.x, rectangle.y, rectangle.x + rectangle.width, rectangle.y + rectangle.height, 
		      color);
		  }
		  
		  public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC)
		  {
		    enableGL2D();
		    x *= 2.0F;
		    y *= 2.0F;
		    x1 *= 2.0F;
		    y1 *= 2.0F;
		    GL11.glScalef(0.5F, 0.5F, 0.5F);
		    drawVLine(x, y + 1.0F, y1 - 2.0F, borderC);
		    drawVLine(x1 - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
		    drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
		    drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
		    drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
		    drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
		    drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
		    drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
		    drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
		    GL11.glScalef(2.0F, 2.0F, 2.0F);
		    disableGL2D();
		  }
		  
		  public static void drawStrip(int x, int y, float width, double angle, float points, float radius, int color)
		  {
		    float f1 = (color >> 24 & 0xFF) / 255.0F;
		    float f2 = (color >> 16 & 0xFF) / 255.0F;
		    float f3 = (color >> 8 & 0xFF) / 255.0F;
		    float f4 = (color & 0xFF) / 255.0F;
		    GL11.glPushMatrix();
		    GL11.glTranslated(x, y, 0.0D);
		    GL11.glColor4f(f2, f3, f4, f1);
		    GL11.glLineWidth(width);
		    if (angle > 0.0D)
		    {
		      GL11.glBegin(3);
		      for (int i = 0; i < angle; i++)
		      {
		        float a = (float)(i * (angle * 3.141592653589793D / points));
		        float xc = (float)(Math.cos(a) * radius);
		        float yc = (float)(Math.sin(a) * radius);
		        GL11.glVertex2f(xc, yc);
		      }
		      GL11.glEnd();
		    }
		    if (angle < 0.0D)
		    {
		      GL11.glBegin(3);
		      for (int i = 0; i > angle; i--)
		      {
		        float a = (float)(i * (angle * 3.141592653589793D / points));
		        float xc = (float)(Math.cos(a) * -radius);
		        float yc = (float)(Math.sin(a) * -radius);
		        GL11.glVertex2f(xc, yc);
		      }
		      GL11.glEnd();
		    }
		    disableGL2D();
		    GL11.glDisable(3479);
		    GL11.glPopMatrix();
		  }
		  
		  public static void drawTexturedModalRect(int par1, int par2, int par3, int par4, int par5, int par6)
		  {
		    float var7 = 0.00390625F;
		    float var8 = 0.00390625F;
		    WorldRenderer var9 = Tessellator.getInstance().getWorldRenderer();
		    var9.startDrawingQuads();
		    var9.addVertexWithUV(par1 + 0, par2 + par6, 0.0D, (par3 + 0) * var7, (par4 + par6) * var8);
		    var9.addVertexWithUV(par1 + par5, par2 + par6, 0.0D, (par3 + par5) * var7, (par4 + par6) * var8);
		    var9.addVertexWithUV(par1 + par5, par2 + 0, 0.0D, (par3 + par5) * var7, (par4 + 0) * var8);
		    var9.addVertexWithUV(par1 + 0, par2 + 0, 0.0D, (par3 + 0) * var7, (par4 + 0) * var8);
		    var9.draw();
		  }
		  
		  public static void drawVLine(float x, float y, float x1, int y1)
		  {
		    if (x1 < y)
		    {
		      float var5 = y;
		      y = x1;
		      x1 = var5;
		    }
		    drawRect(x, y + 1.0F, x + 1.0F, x1, y1);
		  }
		  
		  public static void enableGL2D()
		  {
		    GL11.glDisable(2929);
		    GL11.glEnable(3042);
		    GL11.glDisable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glDepthMask(true);
		    GL11.glEnable(2848);
		    GL11.glHint(3154, 4354);
		    GL11.glHint(3155, 4354);
		  }
		  
		  public static void enableGL3D(float lineWidth)
		  {
		    GL11.glDisable(3008);
		    GL11.glEnable(3042);
		    GL11.glBlendFunc(770, 771);
		    GL11.glDisable(3553);
		    GL11.glDisable(2929);
		    GL11.glDepthMask(false);
		    GL11.glEnable(2884);
		    Minecraft.getMinecraft().entityRenderer.func_175072_h();
		    GL11.glEnable(2848);
		    GL11.glHint(3154, 4354);
		    GL11.glHint(3155, 4354);
		    GL11.glLineWidth(lineWidth);
		  }
		  
		  public static int genTexture()
		  {
		    return GL11.glGenTextures();
		  }
		  
		  public static Color getRandomColor()
		  {
		    return getRandomColor(1000, 0.6F);
		  }
		  
		  public static Color getRandomColor(int saturationRandom, float luminance)
		  {
		    float randomFloat = MathUtils.getRandom();
		    float saturation = (MathUtils.getRandom(saturationRandom) + 1000) / saturationRandom + 1000.0F;
		    
		    return Color.getHSBColor(randomFloat, saturation, luminance);
		  }
		  
		  public static ScaledResolution getScaledResolution()
		  {
		    return scaledResolution;
		  }
		  
		  public static void glColor(Color color)
		  {
		    GL11.glColor4f(color.getRed() / 255.0F, color.getGreen() / 255.0F, color.getBlue() / 255.0F, color.getAlpha() / 255.0F);
		  }
		  
		  public static void glColor(float alpha, int redRGB, int greenRGB, int blueRGB)
		  {
		    float red = 0.003921569F * redRGB;
		    float green = 0.003921569F * greenRGB;
		    float blue = 0.003921569F * blueRGB;
		    GL11.glColor4f(red, green, blue, alpha);
		  }
		  
		  public static void glColor(int hex)
		  {
		    float alpha = (hex >> 24 & 0xFF) / 255.0F;
		    float red = (hex >> 16 & 0xFF) / 255.0F;
		    float green = (hex >> 8 & 0xFF) / 255.0F;
		    float blue = (hex & 0xFF) / 255.0F;
		    GL11.glColor4f(red, green, blue, alpha);
		  }
		  
		  public static void prepareScissorBox(float x, float y, float x2, float y2)
		  {
		    updateScaledResolution();
		    int factor = scaledResolution.getScaleFactor();
		    GL11.glScissor((int)(x * factor), (int)((scaledResolution.getScaledHeight() - y2) * factor), 
		      (int)((x2 - x) * factor), (int)((y2 - y) * factor));
		  }
		  
		  public static void renderCrosses(Box box)
		  {
		    GL11.glBegin(1);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.minZ);
		    
		    GL11.glVertex3d(box.minX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.minZ);
		    
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    GL11.glVertex3d(box.maxX, box.maxY, box.maxZ);
		    GL11.glVertex3d(box.minX, box.minY, box.maxZ);
		    
		    GL11.glVertex3d(box.minX, box.maxY, box.minZ);
		    GL11.glVertex3d(box.minX, box.minY, box.minZ);
		    GL11.glVertex3d(box.maxX, box.minY, box.maxZ);
		    GL11.glEnd();
		  }
		  
		  public static void renderTexture(float x, float y, float width, float height)
		  {
		    boolean tex2D = GL11.glGetBoolean(3553);
		    boolean blend = GL11.glGetBoolean(3042);
		    GL11.glPushMatrix();
		    GL11.glEnable(3042);
		    GL11.glEnable(3553);
		    GL11.glBlendFunc(770, 771);
		    GL11.glScalef(0.5F, 0.5F, 0.5F);
		    x *= 2.0F;
		    y *= 2.0F;
		    width *= 2.0F;
		    height *= 2.0F;
		    GL11.glBegin(4);
		    GL11.glTexCoord2f(1.0F, 0.0F);
		    GL11.glVertex2f(x + width, y);
		    GL11.glTexCoord2f(0.0F, 0.0F);
		    GL11.glVertex2f(x, y);
		    GL11.glTexCoord2f(0.0F, 1.0F);
		    GL11.glVertex2f(x, y + height);
		    GL11.glTexCoord2f(0.0F, 1.0F);
		    GL11.glVertex2f(x, y + height);
		    GL11.glTexCoord2f(1.0F, 1.0F);
		    GL11.glVertex2f(x + width, y + height);
		    GL11.glTexCoord2f(1.0F, 0.0F);
		    GL11.glVertex2f(x + width, y);
		    GL11.glEnd();
		    if (!tex2D) {
		      GL11.glDisable(3553);
		    }
		    if (!blend) {
		      GL11.glDisable(3042);
		    }
		    GL11.glPopMatrix();
		  }
		  
		  public static void renderTexture(int texID, float x, float y, float width, float height)
		  {
		    GL11.glBindTexture(3553, texID);
		    renderTexture(x, y, width, height);
		  }
		  
		  public static void renderTexture(int textureWidth, int textureHeight, float x, float y, float width, float height, float srcX, float srcY, float srcWidth, float srcHeight)
		  {
		    float renderSRCX = srcX / textureWidth;
		    float renderSRCY = srcY / textureHeight;
		    float renderSRCWidth = srcWidth / textureWidth;
		    float renderSRCHeight = srcHeight / textureHeight;
		    boolean tex2D = GL11.glGetBoolean(3553);
		    boolean blend = GL11.glGetBoolean(3042);
		    GL11.glPushMatrix();
		    GL11.glEnable(3042);
		    GL11.glBlendFunc(770, 771);
		    GL11.glEnable(3553);
		    GL11.glBegin(4);
		    GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
		    GL11.glVertex2f(x + width, y);
		    GL11.glTexCoord2f(renderSRCX, renderSRCY);
		    GL11.glVertex2f(x, y);
		    GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
		    GL11.glVertex2f(x, y + height);
		    GL11.glTexCoord2f(renderSRCX, renderSRCY + renderSRCHeight);
		    GL11.glVertex2f(x, y + height);
		    GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY + renderSRCHeight);
		    GL11.glVertex2f(x + width, y + height);
		    GL11.glTexCoord2f(renderSRCX + renderSRCWidth, renderSRCY);
		    GL11.glVertex2f(x + width, y);
		    GL11.glEnd();
		    if (!tex2D) {
		      GL11.glDisable(3553);
		    }
		    if (!blend) {
		      GL11.glDisable(3042);
		    }
		    GL11.glPopMatrix();
		  }
		  
		  public static void updateScaledResolution()
		  {
		    scaledResolution = new ScaledResolution(Minecraft.getMinecraft(), 
		      Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		  }
	
}
