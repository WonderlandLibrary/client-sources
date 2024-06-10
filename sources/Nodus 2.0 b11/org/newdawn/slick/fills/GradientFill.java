/*   1:    */ package org.newdawn.slick.fills;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.Color;
/*   4:    */ import org.newdawn.slick.ShapeFill;
/*   5:    */ import org.newdawn.slick.geom.Shape;
/*   6:    */ import org.newdawn.slick.geom.Vector2f;
/*   7:    */ 
/*   8:    */ public class GradientFill
/*   9:    */   implements ShapeFill
/*  10:    */ {
/*  11: 19 */   private Vector2f none = new Vector2f(0.0F, 0.0F);
/*  12:    */   private Vector2f start;
/*  13:    */   private Vector2f end;
/*  14:    */   private Color startCol;
/*  15:    */   private Color endCol;
/*  16: 29 */   private boolean local = false;
/*  17:    */   
/*  18:    */   public GradientFill(float sx, float sy, Color startCol, float ex, float ey, Color endCol)
/*  19:    */   {
/*  20: 43 */     this(sx, sy, startCol, ex, ey, endCol, false);
/*  21:    */   }
/*  22:    */   
/*  23:    */   public GradientFill(float sx, float sy, Color startCol, float ex, float ey, Color endCol, boolean local)
/*  24:    */   {
/*  25: 59 */     this(new Vector2f(sx, sy), startCol, new Vector2f(ex, ey), endCol, local);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public GradientFill(Vector2f start, Color startCol, Vector2f end, Color endCol, boolean local)
/*  29:    */   {
/*  30: 72 */     this.start = new Vector2f(start);
/*  31: 73 */     this.end = new Vector2f(end);
/*  32: 74 */     this.startCol = new Color(startCol);
/*  33: 75 */     this.endCol = new Color(endCol);
/*  34: 76 */     this.local = local;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public GradientFill getInvertedCopy()
/*  38:    */   {
/*  39: 85 */     return new GradientFill(this.start, this.endCol, this.end, this.startCol, this.local);
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setLocal(boolean local)
/*  43:    */   {
/*  44: 94 */     this.local = local;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public Vector2f getStart()
/*  48:    */   {
/*  49:103 */     return this.start;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public Vector2f getEnd()
/*  53:    */   {
/*  54:112 */     return this.end;
/*  55:    */   }
/*  56:    */   
/*  57:    */   public Color getStartColor()
/*  58:    */   {
/*  59:121 */     return this.startCol;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public Color getEndColor()
/*  63:    */   {
/*  64:130 */     return this.endCol;
/*  65:    */   }
/*  66:    */   
/*  67:    */   public void setStart(float x, float y)
/*  68:    */   {
/*  69:140 */     setStart(new Vector2f(x, y));
/*  70:    */   }
/*  71:    */   
/*  72:    */   public void setStart(Vector2f start)
/*  73:    */   {
/*  74:149 */     this.start = new Vector2f(start);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void setEnd(float x, float y)
/*  78:    */   {
/*  79:159 */     setEnd(new Vector2f(x, y));
/*  80:    */   }
/*  81:    */   
/*  82:    */   public void setEnd(Vector2f end)
/*  83:    */   {
/*  84:168 */     this.end = new Vector2f(end);
/*  85:    */   }
/*  86:    */   
/*  87:    */   public void setStartColor(Color color)
/*  88:    */   {
/*  89:177 */     this.startCol = new Color(color);
/*  90:    */   }
/*  91:    */   
/*  92:    */   public void setEndColor(Color color)
/*  93:    */   {
/*  94:186 */     this.endCol = new Color(color);
/*  95:    */   }
/*  96:    */   
/*  97:    */   public Color colorAt(Shape shape, float x, float y)
/*  98:    */   {
/*  99:198 */     if (this.local) {
/* 100:199 */       return colorAt(x - shape.getCenterX(), y - shape.getCenterY());
/* 101:    */     }
/* 102:201 */     return colorAt(x, y);
/* 103:    */   }
/* 104:    */   
/* 105:    */   public Color colorAt(float x, float y)
/* 106:    */   {
/* 107:213 */     float dx1 = this.end.getX() - this.start.getX();
/* 108:214 */     float dy1 = this.end.getY() - this.start.getY();
/* 109:    */     
/* 110:216 */     float dx2 = -dy1;
/* 111:217 */     float dy2 = dx1;
/* 112:218 */     float denom = dy2 * dx1 - dx2 * dy1;
/* 113:220 */     if (denom == 0.0F) {
/* 114:221 */       return Color.black;
/* 115:    */     }
/* 116:224 */     float ua = dx2 * (this.start.getY() - y) - dy2 * (this.start.getX() - x);
/* 117:225 */     ua /= denom;
/* 118:226 */     float ub = dx1 * (this.start.getY() - y) - dy1 * (this.start.getX() - x);
/* 119:227 */     ub /= denom;
/* 120:228 */     float u = ua;
/* 121:229 */     if (u < 0.0F) {
/* 122:230 */       u = 0.0F;
/* 123:    */     }
/* 124:232 */     if (u > 1.0F) {
/* 125:233 */       u = 1.0F;
/* 126:    */     }
/* 127:235 */     float v = 1.0F - u;
/* 128:    */     
/* 129:    */ 
/* 130:238 */     Color col = new Color(1, 1, 1, 1);
/* 131:239 */     col.r = (u * this.endCol.r + v * this.startCol.r);
/* 132:240 */     col.b = (u * this.endCol.b + v * this.startCol.b);
/* 133:241 */     col.g = (u * this.endCol.g + v * this.startCol.g);
/* 134:242 */     col.a = (u * this.endCol.a + v * this.startCol.a);
/* 135:    */     
/* 136:244 */     return col;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public Vector2f getOffsetAt(Shape shape, float x, float y)
/* 140:    */   {
/* 141:251 */     return this.none;
/* 142:    */   }
/* 143:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.fills.GradientFill
 * JD-Core Version:    0.7.0.1
 */