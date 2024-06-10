/*   1:    */ package org.newdawn.slick.util;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.Image;
/*   5:    */ 
/*   6:    */ public class LocatedImage
/*   7:    */ {
/*   8:    */   private Image image;
/*   9:    */   private int x;
/*  10:    */   private int y;
/*  11: 20 */   private Color filter = Color.white;
/*  12:    */   private float width;
/*  13:    */   private float height;
/*  14:    */   
/*  15:    */   public LocatedImage(Image image, int x, int y)
/*  16:    */   {
/*  17: 34 */     this.image = image;
/*  18: 35 */     this.x = x;
/*  19: 36 */     this.y = y;
/*  20: 37 */     this.width = image.getWidth();
/*  21: 38 */     this.height = image.getHeight();
/*  22:    */   }
/*  23:    */   
/*  24:    */   public float getHeight()
/*  25:    */   {
/*  26: 47 */     return this.height;
/*  27:    */   }
/*  28:    */   
/*  29:    */   public float getWidth()
/*  30:    */   {
/*  31: 56 */     return this.width;
/*  32:    */   }
/*  33:    */   
/*  34:    */   public void setHeight(float height)
/*  35:    */   {
/*  36: 65 */     this.height = height;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void setWidth(float width)
/*  40:    */   {
/*  41: 74 */     this.width = width;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setColor(Color c)
/*  45:    */   {
/*  46: 83 */     this.filter = c;
/*  47:    */   }
/*  48:    */   
/*  49:    */   public Color getColor()
/*  50:    */   {
/*  51: 92 */     return this.filter;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setX(int x)
/*  55:    */   {
/*  56:101 */     this.x = x;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public void setY(int y)
/*  60:    */   {
/*  61:110 */     this.y = y;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public int getX()
/*  65:    */   {
/*  66:119 */     return this.x;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public int getY()
/*  70:    */   {
/*  71:128 */     return this.y;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public void draw()
/*  75:    */   {
/*  76:135 */     this.image.draw(this.x, this.y, this.width, this.height, this.filter);
/*  77:    */   }
/*  78:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.util.LocatedImage
 * JD-Core Version:    0.7.0.1
 */