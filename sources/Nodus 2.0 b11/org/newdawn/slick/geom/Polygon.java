/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ 
/*   5:    */ public class Polygon
/*   6:    */   extends Shape
/*   7:    */ {
/*   8: 13 */   private boolean allowDups = false;
/*   9: 15 */   private boolean closed = true;
/*  10:    */   
/*  11:    */   public Polygon(float[] points)
/*  12:    */   {
/*  13: 25 */     int length = points.length;
/*  14:    */     
/*  15: 27 */     this.points = new float[length];
/*  16: 28 */     this.maxX = -1.401299E-045F;
/*  17: 29 */     this.maxY = -1.401299E-045F;
/*  18: 30 */     this.minX = 3.4028235E+38F;
/*  19: 31 */     this.minY = 3.4028235E+38F;
/*  20: 32 */     this.x = 3.4028235E+38F;
/*  21: 33 */     this.y = 3.4028235E+38F;
/*  22: 35 */     for (int i = 0; i < length; i++)
/*  23:    */     {
/*  24: 36 */       this.points[i] = points[i];
/*  25: 37 */       if (i % 2 == 0)
/*  26:    */       {
/*  27: 38 */         if (points[i] > this.maxX) {
/*  28: 39 */           this.maxX = points[i];
/*  29:    */         }
/*  30: 41 */         if (points[i] < this.minX) {
/*  31: 42 */           this.minX = points[i];
/*  32:    */         }
/*  33: 44 */         if (points[i] < this.x) {
/*  34: 45 */           this.x = points[i];
/*  35:    */         }
/*  36:    */       }
/*  37:    */       else
/*  38:    */       {
/*  39: 49 */         if (points[i] > this.maxY) {
/*  40: 50 */           this.maxY = points[i];
/*  41:    */         }
/*  42: 52 */         if (points[i] < this.minY) {
/*  43: 53 */           this.minY = points[i];
/*  44:    */         }
/*  45: 55 */         if (points[i] < this.y) {
/*  46: 56 */           this.y = points[i];
/*  47:    */         }
/*  48:    */       }
/*  49:    */     }
/*  50: 61 */     findCenter();
/*  51: 62 */     calculateRadius();
/*  52: 63 */     this.pointsDirty = true;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public Polygon()
/*  56:    */   {
/*  57: 70 */     this.points = new float[0];
/*  58: 71 */     this.maxX = -1.401299E-045F;
/*  59: 72 */     this.maxY = -1.401299E-045F;
/*  60: 73 */     this.minX = 3.4028235E+38F;
/*  61: 74 */     this.minY = 3.4028235E+38F;
/*  62:    */   }
/*  63:    */   
/*  64:    */   public void setAllowDuplicatePoints(boolean allowDups)
/*  65:    */   {
/*  66: 83 */     this.allowDups = allowDups;
/*  67:    */   }
/*  68:    */   
/*  69:    */   public void addPoint(float x, float y)
/*  70:    */   {
/*  71: 93 */     if ((hasVertex(x, y)) && (!this.allowDups)) {
/*  72: 94 */       return;
/*  73:    */     }
/*  74: 97 */     ArrayList tempPoints = new ArrayList();
/*  75: 98 */     for (int i = 0; i < this.points.length; i++) {
/*  76: 99 */       tempPoints.add(new Float(this.points[i]));
/*  77:    */     }
/*  78:101 */     tempPoints.add(new Float(x));
/*  79:102 */     tempPoints.add(new Float(y));
/*  80:103 */     int length = tempPoints.size();
/*  81:104 */     this.points = new float[length];
/*  82:105 */     for (int i = 0; i < length; i++) {
/*  83:106 */       this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/*  84:    */     }
/*  85:108 */     if (x > this.maxX) {
/*  86:109 */       this.maxX = x;
/*  87:    */     }
/*  88:111 */     if (y > this.maxY) {
/*  89:112 */       this.maxY = y;
/*  90:    */     }
/*  91:114 */     if (x < this.minX) {
/*  92:115 */       this.minX = x;
/*  93:    */     }
/*  94:117 */     if (y < this.minY) {
/*  95:118 */       this.minY = y;
/*  96:    */     }
/*  97:120 */     findCenter();
/*  98:121 */     calculateRadius();
/*  99:    */     
/* 100:123 */     this.pointsDirty = true;
/* 101:    */   }
/* 102:    */   
/* 103:    */   public Shape transform(Transform transform)
/* 104:    */   {
/* 105:135 */     checkPoints();
/* 106:    */     
/* 107:137 */     Polygon resultPolygon = new Polygon();
/* 108:    */     
/* 109:139 */     float[] result = new float[this.points.length];
/* 110:140 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 111:141 */     resultPolygon.points = result;
/* 112:142 */     resultPolygon.findCenter();
/* 113:143 */     resultPolygon.closed = this.closed;
/* 114:    */     
/* 115:145 */     return resultPolygon;
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void setX(float x)
/* 119:    */   {
/* 120:152 */     super.setX(x);
/* 121:    */     
/* 122:154 */     this.pointsDirty = false;
/* 123:    */   }
/* 124:    */   
/* 125:    */   public void setY(float y)
/* 126:    */   {
/* 127:161 */     super.setY(y);
/* 128:    */     
/* 129:163 */     this.pointsDirty = false;
/* 130:    */   }
/* 131:    */   
/* 132:    */   protected void createPoints() {}
/* 133:    */   
/* 134:    */   public boolean closed()
/* 135:    */   {
/* 136:177 */     return this.closed;
/* 137:    */   }
/* 138:    */   
/* 139:    */   public void setClosed(boolean closed)
/* 140:    */   {
/* 141:186 */     this.closed = closed;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public Polygon copy()
/* 145:    */   {
/* 146:195 */     float[] copyPoints = new float[this.points.length];
/* 147:196 */     System.arraycopy(this.points, 0, copyPoints, 0, copyPoints.length);
/* 148:    */     
/* 149:198 */     return new Polygon(copyPoints);
/* 150:    */   }
/* 151:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Polygon
 * JD-Core Version:    0.7.0.1
 */