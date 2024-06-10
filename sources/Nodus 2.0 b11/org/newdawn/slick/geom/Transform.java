/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import org.newdawn.slick.util.FastTrig;
/*   4:    */ 
/*   5:    */ public class Transform
/*   6:    */ {
/*   7:    */   private float[] matrixPosition;
/*   8:    */   
/*   9:    */   public Transform()
/*  10:    */   {
/*  11: 25 */     this.matrixPosition = new float[] { 1.0F, 0.0F, 0.0F, 0.0F, 1.0F, 0.0F, 0.0F, 0.0F, 1.0F };
/*  12:    */   }
/*  13:    */   
/*  14:    */   public Transform(Transform other)
/*  15:    */   {
/*  16: 34 */     this.matrixPosition = new float[9];
/*  17: 35 */     for (int i = 0; i < 9; i++) {
/*  18: 36 */       this.matrixPosition[i] = other.matrixPosition[i];
/*  19:    */     }
/*  20:    */   }
/*  21:    */   
/*  22:    */   public Transform(Transform t1, Transform t2)
/*  23:    */   {
/*  24: 47 */     this(t1);
/*  25: 48 */     concatenate(t2);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public Transform(float[] matrixPosition)
/*  29:    */   {
/*  30: 58 */     if (matrixPosition.length != 6) {
/*  31: 59 */       throw new RuntimeException("The parameter must be a float array of length 6.");
/*  32:    */     }
/*  33: 61 */     this.matrixPosition = new float[] { matrixPosition[0], matrixPosition[1], matrixPosition[2], 
/*  34: 62 */       matrixPosition[3], matrixPosition[4], matrixPosition[5], 
/*  35: 63 */       0.0F, 0.0F, 1.0F };
/*  36:    */   }
/*  37:    */   
/*  38:    */   public Transform(float point00, float point01, float point02, float point10, float point11, float point12)
/*  39:    */   {
/*  40: 77 */     this.matrixPosition = new float[] { point00, point01, point02, point10, point11, point12, 0.0F, 0.0F, 1.0F };
/*  41:    */   }
/*  42:    */   
/*  43:    */   public void transform(float[] source, int sourceOffset, float[] destination, int destOffset, int numberOfPoints)
/*  44:    */   {
/*  45: 94 */     float[] result = source == destination ? new float[numberOfPoints * 2] : destination;
/*  46: 96 */     for (int i = 0; i < numberOfPoints * 2; i += 2) {
/*  47: 97 */       for (int j = 0; j < 6; j += 3) {
/*  48: 98 */         result[(i + j / 3)] = (source[(i + sourceOffset)] * this.matrixPosition[j] + source[(i + sourceOffset + 1)] * this.matrixPosition[(j + 1)] + 1.0F * this.matrixPosition[(j + 2)]);
/*  49:    */       }
/*  50:    */     }
/*  51:102 */     if (source == destination) {
/*  52:104 */       for (int i = 0; i < numberOfPoints * 2; i += 2)
/*  53:    */       {
/*  54:105 */         destination[(i + destOffset)] = result[i];
/*  55:106 */         destination[(i + destOffset + 1)] = result[(i + 1)];
/*  56:    */       }
/*  57:    */     }
/*  58:    */   }
/*  59:    */   
/*  60:    */   public Transform concatenate(Transform tx)
/*  61:    */   {
/*  62:118 */     float[] mp = new float[9];
/*  63:119 */     float n00 = this.matrixPosition[0] * tx.matrixPosition[0] + this.matrixPosition[1] * tx.matrixPosition[3];
/*  64:120 */     float n01 = this.matrixPosition[0] * tx.matrixPosition[1] + this.matrixPosition[1] * tx.matrixPosition[4];
/*  65:121 */     float n02 = this.matrixPosition[0] * tx.matrixPosition[2] + this.matrixPosition[1] * tx.matrixPosition[5] + this.matrixPosition[2];
/*  66:122 */     float n10 = this.matrixPosition[3] * tx.matrixPosition[0] + this.matrixPosition[4] * tx.matrixPosition[3];
/*  67:123 */     float n11 = this.matrixPosition[3] * tx.matrixPosition[1] + this.matrixPosition[4] * tx.matrixPosition[4];
/*  68:124 */     float n12 = this.matrixPosition[3] * tx.matrixPosition[2] + this.matrixPosition[4] * tx.matrixPosition[5] + this.matrixPosition[5];
/*  69:125 */     mp[0] = n00;
/*  70:126 */     mp[1] = n01;
/*  71:127 */     mp[2] = n02;
/*  72:128 */     mp[3] = n10;
/*  73:129 */     mp[4] = n11;
/*  74:130 */     mp[5] = n12;
/*  75:    */     
/*  76:    */ 
/*  77:    */ 
/*  78:    */ 
/*  79:    */ 
/*  80:    */ 
/*  81:    */ 
/*  82:    */ 
/*  83:139 */     this.matrixPosition = mp;
/*  84:140 */     return this;
/*  85:    */   }
/*  86:    */   
/*  87:    */   public String toString()
/*  88:    */   {
/*  89:150 */     String result = "Transform[[" + this.matrixPosition[0] + "," + this.matrixPosition[1] + "," + this.matrixPosition[2] + 
/*  90:151 */       "][" + this.matrixPosition[3] + "," + this.matrixPosition[4] + "," + this.matrixPosition[5] + 
/*  91:152 */       "][" + this.matrixPosition[6] + "," + this.matrixPosition[7] + "," + this.matrixPosition[8] + "]]";
/*  92:    */     
/*  93:154 */     return result.toString();
/*  94:    */   }
/*  95:    */   
/*  96:    */   public float[] getMatrixPosition()
/*  97:    */   {
/*  98:163 */     return this.matrixPosition;
/*  99:    */   }
/* 100:    */   
/* 101:    */   public static Transform createRotateTransform(float angle)
/* 102:    */   {
/* 103:173 */     return new Transform((float)FastTrig.cos(angle), -(float)FastTrig.sin(angle), 0.0F, (float)FastTrig.sin(angle), (float)FastTrig.cos(angle), 0.0F);
/* 104:    */   }
/* 105:    */   
/* 106:    */   public static Transform createRotateTransform(float angle, float x, float y)
/* 107:    */   {
/* 108:185 */     Transform temp = createRotateTransform(angle);
/* 109:186 */     float sinAngle = temp.matrixPosition[3];
/* 110:187 */     float oneMinusCosAngle = 1.0F - temp.matrixPosition[4];
/* 111:188 */     temp.matrixPosition[2] = (x * oneMinusCosAngle + y * sinAngle);
/* 112:189 */     temp.matrixPosition[5] = (y * oneMinusCosAngle - x * sinAngle);
/* 113:    */     
/* 114:191 */     return temp;
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static Transform createTranslateTransform(float xOffset, float yOffset)
/* 118:    */   {
/* 119:202 */     return new Transform(1.0F, 0.0F, xOffset, 0.0F, 1.0F, yOffset);
/* 120:    */   }
/* 121:    */   
/* 122:    */   public static Transform createScaleTransform(float xScale, float yScale)
/* 123:    */   {
/* 124:213 */     return new Transform(xScale, 0.0F, 0.0F, 0.0F, yScale, 0.0F);
/* 125:    */   }
/* 126:    */   
/* 127:    */   public Vector2f transform(Vector2f pt)
/* 128:    */   {
/* 129:223 */     float[] in = { pt.x, pt.y };
/* 130:224 */     float[] out = new float[2];
/* 131:    */     
/* 132:226 */     transform(in, 0, out, 0, 1);
/* 133:    */     
/* 134:228 */     return new Vector2f(out[0], out[1]);
/* 135:    */   }
/* 136:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Transform
 * JD-Core Version:    0.7.0.1
 */