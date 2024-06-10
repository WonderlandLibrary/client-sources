/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ public class Circle
/*   4:    */   extends Ellipse
/*   5:    */ {
/*   6:    */   public float radius;
/*   7:    */   
/*   8:    */   public strictfp Circle(float centerPointX, float centerPointY, float radius)
/*   9:    */   {
/*  10: 20 */     this(centerPointX, centerPointY, radius, 50);
/*  11:    */   }
/*  12:    */   
/*  13:    */   public strictfp Circle(float centerPointX, float centerPointY, float radius, int segmentCount)
/*  14:    */   {
/*  15: 32 */     super(centerPointX, centerPointY, radius, radius, segmentCount);
/*  16: 33 */     this.x = (centerPointX - radius);
/*  17: 34 */     this.y = (centerPointY - radius);
/*  18: 35 */     this.radius = radius;
/*  19: 36 */     this.boundingCircleRadius = radius;
/*  20:    */   }
/*  21:    */   
/*  22:    */   public strictfp float getCenterX()
/*  23:    */   {
/*  24: 45 */     return getX() + this.radius;
/*  25:    */   }
/*  26:    */   
/*  27:    */   public strictfp float getCenterY()
/*  28:    */   {
/*  29: 54 */     return getY() + this.radius;
/*  30:    */   }
/*  31:    */   
/*  32:    */   public strictfp float[] getCenter()
/*  33:    */   {
/*  34: 64 */     return new float[] { getCenterX(), getCenterY() };
/*  35:    */   }
/*  36:    */   
/*  37:    */   public strictfp void setRadius(float radius)
/*  38:    */   {
/*  39: 73 */     if (radius != this.radius)
/*  40:    */     {
/*  41: 74 */       this.pointsDirty = true;
/*  42: 75 */       this.radius = radius;
/*  43: 76 */       setRadii(radius, radius);
/*  44:    */     }
/*  45:    */   }
/*  46:    */   
/*  47:    */   public strictfp float getRadius()
/*  48:    */   {
/*  49: 86 */     return this.radius;
/*  50:    */   }
/*  51:    */   
/*  52:    */   public strictfp boolean intersects(Shape shape)
/*  53:    */   {
/*  54: 96 */     if ((shape instanceof Circle))
/*  55:    */     {
/*  56: 97 */       Circle other = (Circle)shape;
/*  57: 98 */       float totalRad2 = getRadius() + other.getRadius();
/*  58:100 */       if (Math.abs(other.getCenterX() - getCenterX()) > totalRad2) {
/*  59:101 */         return false;
/*  60:    */       }
/*  61:103 */       if (Math.abs(other.getCenterY() - getCenterY()) > totalRad2) {
/*  62:104 */         return false;
/*  63:    */       }
/*  64:107 */       totalRad2 *= totalRad2;
/*  65:    */       
/*  66:109 */       float dx = Math.abs(other.getCenterX() - getCenterX());
/*  67:110 */       float dy = Math.abs(other.getCenterY() - getCenterY());
/*  68:    */       
/*  69:112 */       return totalRad2 >= dx * dx + dy * dy;
/*  70:    */     }
/*  71:114 */     if ((shape instanceof Rectangle)) {
/*  72:115 */       return intersects((Rectangle)shape);
/*  73:    */     }
/*  74:118 */     return super.intersects(shape);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public strictfp boolean contains(float x, float y)
/*  78:    */   {
/*  79:131 */     float xDelta = x - getCenterX();float yDelta = y - getCenterY();
/*  80:132 */     return xDelta * xDelta + yDelta * yDelta < getRadius() * getRadius();
/*  81:    */   }
/*  82:    */   
/*  83:    */   private strictfp boolean contains(Line line)
/*  84:    */   {
/*  85:141 */     return (contains(line.getX1(), line.getY1())) && (contains(line.getX2(), line.getY2()));
/*  86:    */   }
/*  87:    */   
/*  88:    */   protected strictfp void findCenter()
/*  89:    */   {
/*  90:148 */     this.center = new float[2];
/*  91:149 */     this.center[0] = (this.x + this.radius);
/*  92:150 */     this.center[1] = (this.y + this.radius);
/*  93:    */   }
/*  94:    */   
/*  95:    */   protected strictfp void calculateRadius()
/*  96:    */   {
/*  97:157 */     this.boundingCircleRadius = this.radius;
/*  98:    */   }
/*  99:    */   
/* 100:    */   private strictfp boolean intersects(Rectangle other)
/* 101:    */   {
/* 102:167 */     Rectangle box = other;
/* 103:168 */     Circle circle = this;
/* 104:170 */     if (box.contains(this.x + this.radius, this.y + this.radius)) {
/* 105:171 */       return true;
/* 106:    */     }
/* 107:174 */     float x1 = box.getX();
/* 108:175 */     float y1 = box.getY();
/* 109:176 */     float x2 = box.getX() + box.getWidth();
/* 110:177 */     float y2 = box.getY() + box.getHeight();
/* 111:    */     
/* 112:179 */     Line[] lines = new Line[4];
/* 113:180 */     lines[0] = new Line(x1, y1, x2, y1);
/* 114:181 */     lines[1] = new Line(x2, y1, x2, y2);
/* 115:182 */     lines[2] = new Line(x2, y2, x1, y2);
/* 116:183 */     lines[3] = new Line(x1, y2, x1, y1);
/* 117:    */     
/* 118:185 */     float r2 = circle.getRadius() * circle.getRadius();
/* 119:    */     
/* 120:187 */     Vector2f pos = new Vector2f(circle.getCenterX(), circle.getCenterY());
/* 121:189 */     for (int i = 0; i < 4; i++)
/* 122:    */     {
/* 123:190 */       float dis = lines[i].distanceSquared(pos);
/* 124:191 */       if (dis < r2) {
/* 125:192 */         return true;
/* 126:    */       }
/* 127:    */     }
/* 128:196 */     return false;
/* 129:    */   }
/* 130:    */   
/* 131:    */   private strictfp boolean intersects(Line other)
/* 132:    */   {
/* 133:206 */     Vector2f lineSegmentStart = new Vector2f(other.getX1(), other.getY1());
/* 134:207 */     Vector2f lineSegmentEnd = new Vector2f(other.getX2(), other.getY2());
/* 135:208 */     Vector2f circleCenter = new Vector2f(getCenterX(), getCenterY());
/* 136:    */     
/* 137:    */ 
/* 138:    */ 
/* 139:    */ 
/* 140:213 */     Vector2f segv = lineSegmentEnd.copy().sub(lineSegmentStart);
/* 141:214 */     Vector2f ptv = circleCenter.copy().sub(lineSegmentStart);
/* 142:215 */     float segvLength = segv.length();
/* 143:216 */     float projvl = ptv.dot(segv) / segvLength;
/* 144:    */     Vector2f closest;
/* 145:    */     Vector2f closest;
/* 146:217 */     if (projvl < 0.0F)
/* 147:    */     {
/* 148:219 */       closest = lineSegmentStart;
/* 149:    */     }
/* 150:    */     else
/* 151:    */     {
/* 152:    */       Vector2f closest;
/* 153:221 */       if (projvl > segvLength)
/* 154:    */       {
/* 155:223 */         closest = lineSegmentEnd;
/* 156:    */       }
/* 157:    */       else
/* 158:    */       {
/* 159:227 */         Vector2f projv = segv.copy().scale(projvl / segvLength);
/* 160:228 */         closest = lineSegmentStart.copy().add(projv);
/* 161:    */       }
/* 162:    */     }
/* 163:230 */     boolean intersects = circleCenter.copy().sub(closest).lengthSquared() <= getRadius() * getRadius();
/* 164:    */     
/* 165:232 */     return intersects;
/* 166:    */   }
/* 167:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Circle
 * JD-Core Version:    0.7.0.1
 */