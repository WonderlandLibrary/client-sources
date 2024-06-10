/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import org.newdawn.slick.util.FastTrig;
/*   5:    */ 
/*   6:    */ public class Ellipse
/*   7:    */   extends Shape
/*   8:    */ {
/*   9:    */   protected static final int DEFAULT_SEGMENT_COUNT = 50;
/*  10:    */   private int segmentCount;
/*  11:    */   private float radius1;
/*  12:    */   private float radius2;
/*  13:    */   
/*  14:    */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2)
/*  15:    */   {
/*  16: 41 */     this(centerPointX, centerPointY, radius1, radius2, 50);
/*  17:    */   }
/*  18:    */   
/*  19:    */   public Ellipse(float centerPointX, float centerPointY, float radius1, float radius2, int segmentCount)
/*  20:    */   {
/*  21: 54 */     this.x = (centerPointX - radius1);
/*  22: 55 */     this.y = (centerPointY - radius2);
/*  23: 56 */     this.radius1 = radius1;
/*  24: 57 */     this.radius2 = radius2;
/*  25: 58 */     this.segmentCount = segmentCount;
/*  26: 59 */     checkPoints();
/*  27:    */   }
/*  28:    */   
/*  29:    */   public void setRadii(float radius1, float radius2)
/*  30:    */   {
/*  31: 69 */     setRadius1(radius1);
/*  32: 70 */     setRadius2(radius2);
/*  33:    */   }
/*  34:    */   
/*  35:    */   public float getRadius1()
/*  36:    */   {
/*  37: 79 */     return this.radius1;
/*  38:    */   }
/*  39:    */   
/*  40:    */   public void setRadius1(float radius1)
/*  41:    */   {
/*  42: 88 */     if (radius1 != this.radius1)
/*  43:    */     {
/*  44: 89 */       this.radius1 = radius1;
/*  45: 90 */       this.pointsDirty = true;
/*  46:    */     }
/*  47:    */   }
/*  48:    */   
/*  49:    */   public float getRadius2()
/*  50:    */   {
/*  51:100 */     return this.radius2;
/*  52:    */   }
/*  53:    */   
/*  54:    */   public void setRadius2(float radius2)
/*  55:    */   {
/*  56:109 */     if (radius2 != this.radius2)
/*  57:    */     {
/*  58:110 */       this.radius2 = radius2;
/*  59:111 */       this.pointsDirty = true;
/*  60:    */     }
/*  61:    */   }
/*  62:    */   
/*  63:    */   protected void createPoints()
/*  64:    */   {
/*  65:120 */     ArrayList tempPoints = new ArrayList();
/*  66:    */     
/*  67:122 */     this.maxX = -1.401299E-045F;
/*  68:123 */     this.maxY = -1.401299E-045F;
/*  69:124 */     this.minX = 3.4028235E+38F;
/*  70:125 */     this.minY = 3.4028235E+38F;
/*  71:    */     
/*  72:127 */     float start = 0.0F;
/*  73:128 */     float end = 359.0F;
/*  74:    */     
/*  75:130 */     float cx = this.x + this.radius1;
/*  76:131 */     float cy = this.y + this.radius2;
/*  77:    */     
/*  78:133 */     int step = 360 / this.segmentCount;
/*  79:135 */     for (float a = start; a <= end + step; a += step)
/*  80:    */     {
/*  81:136 */       float ang = a;
/*  82:137 */       if (ang > end) {
/*  83:138 */         ang = end;
/*  84:    */       }
/*  85:140 */       float newX = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * this.radius1);
/*  86:141 */       float newY = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * this.radius2);
/*  87:143 */       if (newX > this.maxX) {
/*  88:144 */         this.maxX = newX;
/*  89:    */       }
/*  90:146 */       if (newY > this.maxY) {
/*  91:147 */         this.maxY = newY;
/*  92:    */       }
/*  93:149 */       if (newX < this.minX) {
/*  94:150 */         this.minX = newX;
/*  95:    */       }
/*  96:152 */       if (newY < this.minY) {
/*  97:153 */         this.minY = newY;
/*  98:    */       }
/*  99:156 */       tempPoints.add(new Float(newX));
/* 100:157 */       tempPoints.add(new Float(newY));
/* 101:    */     }
/* 102:159 */     this.points = new float[tempPoints.size()];
/* 103:160 */     for (int i = 0; i < this.points.length; i++) {
/* 104:161 */       this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/* 105:    */     }
/* 106:    */   }
/* 107:    */   
/* 108:    */   public Shape transform(Transform transform)
/* 109:    */   {
/* 110:169 */     checkPoints();
/* 111:    */     
/* 112:171 */     Polygon resultPolygon = new Polygon();
/* 113:    */     
/* 114:173 */     float[] result = new float[this.points.length];
/* 115:174 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 116:175 */     resultPolygon.points = result;
/* 117:176 */     resultPolygon.checkPoints();
/* 118:    */     
/* 119:178 */     return resultPolygon;
/* 120:    */   }
/* 121:    */   
/* 122:    */   protected void findCenter()
/* 123:    */   {
/* 124:185 */     this.center = new float[2];
/* 125:186 */     this.center[0] = (this.x + this.radius1);
/* 126:187 */     this.center[1] = (this.y + this.radius2);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void calculateRadius()
/* 130:    */   {
/* 131:194 */     this.boundingCircleRadius = (this.radius1 > this.radius2 ? this.radius1 : this.radius2);
/* 132:    */   }
/* 133:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Ellipse
 * JD-Core Version:    0.7.0.1
 */