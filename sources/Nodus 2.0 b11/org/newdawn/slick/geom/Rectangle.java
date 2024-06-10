/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class Rectangle
/*   4:    */   extends Shape
/*   5:    */ {
/*   6:    */   protected float width;
/*   7:    */   protected float height;
/*   8:    */   
/*   9:    */   public Rectangle(float x, float y, float width, float height)
/*  10:    */   {
/*  11: 23 */     this.x = x;
/*  12: 24 */     this.y = y;
/*  13: 25 */     this.width = width;
/*  14: 26 */     this.height = height;
/*  15: 27 */     this.maxX = (x + width);
/*  16: 28 */     this.maxY = (y + height);
/*  17: 29 */     checkPoints();
/*  18:    */   }
/*  19:    */   
/*  20:    */   public boolean contains(float xp, float yp)
/*  21:    */   {
/*  22: 40 */     if (xp <= getX()) {
/*  23: 41 */       return false;
/*  24:    */     }
/*  25: 43 */     if (yp <= getY()) {
/*  26: 44 */       return false;
/*  27:    */     }
/*  28: 46 */     if (xp >= this.maxX) {
/*  29: 47 */       return false;
/*  30:    */     }
/*  31: 49 */     if (yp >= this.maxY) {
/*  32: 50 */       return false;
/*  33:    */     }
/*  34: 53 */     return true;
/*  35:    */   }
/*  36:    */   
/*  37:    */   public void setBounds(Rectangle other)
/*  38:    */   {
/*  39: 62 */     setBounds(other.getX(), other.getY(), other.getWidth(), other.getHeight());
/*  40:    */   }
/*  41:    */   
/*  42:    */   public void setBounds(float x, float y, float width, float height)
/*  43:    */   {
/*  44: 74 */     setX(x);
/*  45: 75 */     setY(y);
/*  46: 76 */     setSize(width, height);
/*  47:    */   }
/*  48:    */   
/*  49:    */   public void setSize(float width, float height)
/*  50:    */   {
/*  51: 86 */     setWidth(width);
/*  52: 87 */     setHeight(height);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public float getWidth()
/*  56:    */   {
/*  57: 97 */     return this.width;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public float getHeight()
/*  61:    */   {
/*  62:106 */     return this.height;
/*  63:    */   }
/*  64:    */   
/*  65:    */   public void grow(float h, float v)
/*  66:    */   {
/*  67:117 */     setX(getX() - h);
/*  68:118 */     setY(getY() - v);
/*  69:119 */     setWidth(getWidth() + h * 2.0F);
/*  70:120 */     setHeight(getHeight() + v * 2.0F);
/*  71:    */   }
/*  72:    */   
/*  73:    */   public void scaleGrow(float h, float v)
/*  74:    */   {
/*  75:130 */     grow(getWidth() * (h - 1.0F), getHeight() * (v - 1.0F));
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void setWidth(float width)
/*  79:    */   {
/*  80:139 */     if (width != this.width)
/*  81:    */     {
/*  82:140 */       this.pointsDirty = true;
/*  83:141 */       this.width = width;
/*  84:142 */       this.maxX = (this.x + width);
/*  85:    */     }
/*  86:    */   }
/*  87:    */   
/*  88:    */   public void setHeight(float height)
/*  89:    */   {
/*  90:152 */     if (height != this.height)
/*  91:    */     {
/*  92:153 */       this.pointsDirty = true;
/*  93:154 */       this.height = height;
/*  94:155 */       this.maxY = (this.y + height);
/*  95:    */     }
/*  96:    */   }
/*  97:    */   
/*  98:    */   public boolean intersects(Shape shape)
/*  99:    */   {
/* 100:166 */     if ((shape instanceof Rectangle))
/* 101:    */     {
/* 102:167 */       Rectangle other = (Rectangle)shape;
/* 103:168 */       if ((this.x > other.x + other.width) || (this.x + this.width < other.x)) {
/* 104:169 */         return false;
/* 105:    */       }
/* 106:171 */       if ((this.y > other.y + other.height) || (this.y + this.height < other.y)) {
/* 107:172 */         return false;
/* 108:    */       }
/* 109:174 */       return true;
/* 110:    */     }
/* 111:176 */     if ((shape instanceof Circle)) {
/* 112:177 */       return intersects((Circle)shape);
/* 113:    */     }
/* 114:180 */     return super.intersects(shape);
/* 115:    */   }
/* 116:    */   
/* 117:    */   protected void createPoints()
/* 118:    */   {
/* 119:185 */     float useWidth = this.width;
/* 120:186 */     float useHeight = this.height;
/* 121:187 */     this.points = new float[8];
/* 122:    */     
/* 123:189 */     this.points[0] = this.x;
/* 124:190 */     this.points[1] = this.y;
/* 125:    */     
/* 126:192 */     this.points[2] = (this.x + useWidth);
/* 127:193 */     this.points[3] = this.y;
/* 128:    */     
/* 129:195 */     this.points[4] = (this.x + useWidth);
/* 130:196 */     this.points[5] = (this.y + useHeight);
/* 131:    */     
/* 132:198 */     this.points[6] = this.x;
/* 133:199 */     this.points[7] = (this.y + useHeight);
/* 134:    */     
/* 135:201 */     this.maxX = this.points[2];
/* 136:202 */     this.maxY = this.points[5];
/* 137:203 */     this.minX = this.points[0];
/* 138:204 */     this.minY = this.points[1];
/* 139:    */     
/* 140:206 */     findCenter();
/* 141:207 */     calculateRadius();
/* 142:    */   }
/* 143:    */   
/* 144:    */   private boolean intersects(Circle other)
/* 145:    */   {
/* 146:217 */     return other.intersects(this);
/* 147:    */   }
/* 148:    */   
/* 149:    */   public String toString()
/* 150:    */   {
/* 151:224 */     return "[Rectangle " + this.width + "x" + this.height + "]";
/* 152:    */   }
/* 153:    */   
/* 154:    */   public static boolean contains(float xp, float yp, float xr, float yr, float widthr, float heightr)
/* 155:    */   {
/* 156:245 */     return (xp >= xr) && (yp >= yr) && (xp <= xr + widthr) && (
/* 157:246 */       yp <= yr + heightr);
/* 158:    */   }
/* 159:    */   
/* 160:    */   public Shape transform(Transform transform)
/* 161:    */   {
/* 162:257 */     checkPoints();
/* 163:    */     
/* 164:259 */     Polygon resultPolygon = new Polygon();
/* 165:    */     
/* 166:261 */     float[] result = new float[this.points.length];
/* 167:262 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 168:263 */     resultPolygon.points = result;
/* 169:264 */     resultPolygon.findCenter();
/* 170:265 */     resultPolygon.checkPoints();
/* 171:    */     
/* 172:267 */     return resultPolygon;
/* 173:    */   }
/* 174:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Rectangle
 * JD-Core Version:    0.7.0.1
 */