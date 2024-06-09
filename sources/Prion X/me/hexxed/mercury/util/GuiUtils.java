package me.hexxed.mercury.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.item.ItemStack;
import org.lwjgl.opengl.GL11;








public class GuiUtils
{
  private static Minecraft mc = ;
  private static RenderItem itemRenderer = new RenderItem(mc.getTextureManager(), mcmodelManager);
  
  public GuiUtils() {}
  
  public void drawCheck(int x, int y) { GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glColor4f(0.0F, 0.0F, 0.75F, 0.5F);
    GL11.glBlendFunc(770, 771);
    GL11.glLineWidth(2.0F);
    GL11.glBegin(3);
    GL11.glVertex2f(x + 1, y + 4);
    GL11.glVertex2f(x + 3, y + 6.5F);
    GL11.glVertex2f(x + 7, y + 2);
    GL11.glEnd();
    GL11.glDisable(3042);
    GL11.glEnable(3553);
  }
  
  public void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC) {
    x *= 2.0F;y *= 2.0F;x1 *= 2.0F;y1 *= 2.0F;
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
  }
  
  public void drawBorderedRect(float x, float y, float x1, float y1, int borderC, int insideC)
  {
    x *= 2.0F;x1 *= 2.0F;y *= 2.0F;y1 *= 2.0F;
    GL11.glScalef(0.5F, 0.5F, 0.5F);
    drawVLine(x, y, y1 - 1.0F, borderC);
    drawVLine(x1 - 1.0F, y, y1, borderC);
    drawHLine(x, x1 - 1.0F, y, borderC);
    drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
    drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
    GL11.glScalef(2.0F, 2.0F, 2.0F);
  }
  
  public void drawBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2) {
    drawRect((float)x, (float)y, (float)x2, (float)y2, col2);
    
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glLineWidth(l1);
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
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public void drawHLine(float par1, float par2, float par3, int par4)
  {
    if (par2 < par1)
    {
      float var5 = par1;
      par1 = par2;
      par2 = var5;
    }
    
    drawRect(par1, par3, par2 + 1.0F, par3 + 1.0F, par4);
  }
  
  public void drawVLine(float par1, float par2, float par3, int par4)
  {
    if (par3 < par2)
    {
      float var5 = par2;
      par2 = par3;
      par3 = var5;
    }
    
    drawRect(par1, par2 + 1.0F, par1 + 1.0F, par3, par4);
  }
  
  public void drawRect(float paramXStart, float paramYStart, float paramXEnd, float paramYEnd, int paramColor)
  {
    float alpha = (paramColor >> 24 & 0xFF) / 255.0F;
    float red = (paramColor >> 16 & 0xFF) / 255.0F;
    float green = (paramColor >> 8 & 0xFF) / 255.0F;
    float blue = (paramColor & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    
    GL11.glPushMatrix();
    GL11.glColor4f(red, green, blue, alpha);
    GL11.glBegin(7);
    GL11.glVertex2d(paramXEnd, paramYStart);
    GL11.glVertex2d(paramXStart, paramYStart);
    GL11.glVertex2d(paramXStart, paramYEnd);
    GL11.glVertex2d(paramXEnd, paramYEnd);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
  }
  
  public void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2)
  {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    float f4 = (col2 >> 24 & 0xFF) / 255.0F;
    float f5 = (col2 >> 16 & 0xFF) / 255.0F;
    float f6 = (col2 >> 8 & 0xFF) / 255.0F;
    float f7 = (col2 & 0xFF) / 255.0F;
    
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glShadeModel(7425);
    
    GL11.glPushMatrix();
    GL11.glBegin(7);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glVertex2d(x2, y);
    GL11.glVertex2d(x, y);
    
    GL11.glColor4f(f5, f6, f7, f4);
    GL11.glVertex2d(x, y2);
    GL11.glVertex2d(x2, y2);
    GL11.glEnd();
    GL11.glPopMatrix();
    
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glShadeModel(7424);
  }
  
  public void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
  {
    float f = (col1 >> 24 & 0xFF) / 255.0F;
    float f1 = (col1 >> 16 & 0xFF) / 255.0F;
    float f2 = (col1 >> 8 & 0xFF) / 255.0F;
    float f3 = (col1 & 0xFF) / 255.0F;
    
    GL11.glDisable(3553);
    GL11.glBlendFunc(770, 771);
    GL11.glEnable(2848);
    GL11.glDisable(3042);
    
    GL11.glPushMatrix();
    GL11.glColor4f(f1, f2, f3, f);
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
    
    GL11.glEnable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
  }
  
  public void drawStrip(int x, int y, float width, double angle, float points, float radius, int color)
  {
    GL11.glPushMatrix();
    float f1 = (color >> 24 & 0xFF) / 255.0F;
    float f2 = (color >> 16 & 0xFF) / 255.0F;
    float f3 = (color >> 8 & 0xFF) / 255.0F;
    float f4 = (color & 0xFF) / 255.0F;
    GL11.glTranslatef(x, y, 0.0F);
    GL11.glColor4f(f2, f3, f4, f1);
    GL11.glLineWidth(width);
    GL11.glEnable(3042);
    GL11.glDisable(2929);
    GL11.glEnable(2848);
    GL11.glDisable(3553);
    GL11.glDisable(3008);
    GL11.glBlendFunc(770, 771);
    GL11.glHint(3154, 4354);
    GL11.glEnable(32925);
    
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
    
    GL11.glDisable(3042);
    GL11.glEnable(3553);
    GL11.glDisable(2848);
    GL11.glEnable(3008);
    GL11.glEnable(2929);
    GL11.glDisable(32925);
    GL11.glDisable(3479);
    GL11.glPopMatrix();
  }
  

  public void drawCircle(float cx, float cy, float r, int num_segments, int c)
  {
    GL11.glScalef(0.5F, 0.5F, 0.5F);
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
    
    GL11.glColor4f(f1, f2, f3, f);
    float x = r;
    float y = 0.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glBegin(2);
    for (int ii = 0; ii < num_segments; ii++)
    {
      GL11.glVertex2f(x + cx, y + cy);
      

      float t = x;
      x = p * x - s * y;
      y = s * t + p * y;
    }
    GL11.glEnd();
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glDisable(2848);
    GL11.glScalef(2.0F, 2.0F, 2.0F);
  }
  
  public void drawFullCircle(int cx, int cy, double r, int c) {
    GL11.glScalef(0.5F, 0.5F, 0.5F);
    r *= 2.0D;
    cx *= 2;
    cy *= 2;
    float f = (c >> 24 & 0xFF) / 255.0F;
    float f1 = (c >> 16 & 0xFF) / 255.0F;
    float f2 = (c >> 8 & 0xFF) / 255.0F;
    float f3 = (c & 0xFF) / 255.0F;
    GL11.glEnable(3042);
    GL11.glDisable(3553);
    GL11.glEnable(2848);
    GL11.glBlendFunc(770, 771);
    GL11.glColor4f(f1, f2, f3, f);
    GL11.glBegin(6);
    for (int i = 0; i <= 360; i++) {
      double x = Math.sin(i * 3.141592653589793D / 180.0D) * r;
      double y = Math.cos(i * 3.141592653589793D / 180.0D) * r;
      GL11.glVertex2d(cx + x, cy + y);
    }
    GL11.glEnd();
    GL11.glDisable(2848);
    GL11.glEnable(3553);
    GL11.glDisable(3042);
    GL11.glScalef(2.0F, 2.0F, 2.0F);
  }
  
  public void drawItem(int x, int y, ItemStack stack) {
    itemRenderer.func_175030_a(mcfontRendererObj, stack, x, y);
    

    GL11.glDisable(2884);
    GL11.glEnable(3008);
    GL11.glDisable(3042);
    GL11.glDisable(2896);
    GL11.glDisable(2884);
    GL11.glClear(256);
  }
  
  private static GuiUtils derp = new GuiUtils();
  
  public static GuiUtils getInstance() {
    return derp;
  }
}
