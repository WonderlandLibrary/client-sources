/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.util.ArrayList;
/*   4:    */ import java.util.List;
/*   5:    */ import org.newdawn.slick.util.FastTrig;
/*   6:    */ 
/*   7:    */ public class RoundedRectangle
/*   8:    */   extends Rectangle
/*   9:    */ {
/*  10:    */   public static final int TOP_LEFT = 1;
/*  11:    */   public static final int TOP_RIGHT = 2;
/*  12:    */   public static final int BOTTOM_RIGHT = 4;
/*  13:    */   public static final int BOTTOM_LEFT = 8;
/*  14:    */   public static final int ALL = 15;
/*  15:    */   private static final int DEFAULT_SEGMENT_COUNT = 25;
/*  16:    */   private float cornerRadius;
/*  17:    */   private int segmentCount;
/*  18:    */   private int cornerFlags;
/*  19:    */   
/*  20:    */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius)
/*  21:    */   {
/*  22: 45 */     this(x, y, width, height, cornerRadius, 25);
/*  23:    */   }
/*  24:    */   
/*  25:    */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount)
/*  26:    */   {
/*  27: 59 */     this(x, y, width, height, cornerRadius, segmentCount, 15);
/*  28:    */   }
/*  29:    */   
/*  30:    */   public RoundedRectangle(float x, float y, float width, float height, float cornerRadius, int segmentCount, int cornerFlags)
/*  31:    */   {
/*  32: 75 */     super(x, y, width, height);
/*  33: 77 */     if (cornerRadius < 0.0F) {
/*  34: 78 */       throw new IllegalArgumentException("corner radius must be >= 0");
/*  35:    */     }
/*  36: 80 */     this.x = x;
/*  37: 81 */     this.y = y;
/*  38: 82 */     this.width = width;
/*  39: 83 */     this.height = height;
/*  40: 84 */     this.cornerRadius = cornerRadius;
/*  41: 85 */     this.segmentCount = segmentCount;
/*  42: 86 */     this.pointsDirty = true;
/*  43: 87 */     this.cornerFlags = cornerFlags;
/*  44:    */   }
/*  45:    */   
/*  46:    */   public float getCornerRadius()
/*  47:    */   {
/*  48: 96 */     return this.cornerRadius;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void setCornerRadius(float cornerRadius)
/*  52:    */   {
/*  53:105 */     if ((cornerRadius >= 0.0F) && 
/*  54:106 */       (cornerRadius != this.cornerRadius))
/*  55:    */     {
/*  56:107 */       this.cornerRadius = cornerRadius;
/*  57:108 */       this.pointsDirty = true;
/*  58:    */     }
/*  59:    */   }
/*  60:    */   
/*  61:    */   public float getHeight()
/*  62:    */   {
/*  63:119 */     return this.height;
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void setHeight(float height)
/*  67:    */   {
/*  68:128 */     if (this.height != height)
/*  69:    */     {
/*  70:129 */       this.height = height;
/*  71:130 */       this.pointsDirty = true;
/*  72:    */     }
/*  73:    */   }
/*  74:    */   
/*  75:    */   public float getWidth()
/*  76:    */   {
/*  77:140 */     return this.width;
/*  78:    */   }
/*  79:    */   
/*  80:    */   public void setWidth(float width)
/*  81:    */   {
/*  82:149 */     if (width != this.width)
/*  83:    */     {
/*  84:150 */       this.width = width;
/*  85:151 */       this.pointsDirty = true;
/*  86:    */     }
/*  87:    */   }
/*  88:    */   
/*  89:    */   protected void createPoints()
/*  90:    */   {
/*  91:156 */     this.maxX = (this.x + this.width);
/*  92:157 */     this.maxY = (this.y + this.height);
/*  93:158 */     this.minX = this.x;
/*  94:159 */     this.minY = this.y;
/*  95:160 */     float useWidth = this.width - 1.0F;
/*  96:161 */     float useHeight = this.height - 1.0F;
/*  97:162 */     if (this.cornerRadius == 0.0F)
/*  98:    */     {
/*  99:163 */       this.points = new float[8];
/* 100:    */       
/* 101:165 */       this.points[0] = this.x;
/* 102:166 */       this.points[1] = this.y;
/* 103:    */       
/* 104:168 */       this.points[2] = (this.x + useWidth);
/* 105:169 */       this.points[3] = this.y;
/* 106:    */       
/* 107:171 */       this.points[4] = (this.x + useWidth);
/* 108:172 */       this.points[5] = (this.y + useHeight);
/* 109:    */       
/* 110:174 */       this.points[6] = this.x;
/* 111:175 */       this.points[7] = (this.y + useHeight);
/* 112:    */     }
/* 113:    */     else
/* 114:    */     {
/* 115:178 */       float doubleRadius = this.cornerRadius * 2.0F;
/* 116:179 */       if (doubleRadius > useWidth)
/* 117:    */       {
/* 118:180 */         doubleRadius = useWidth;
/* 119:181 */         this.cornerRadius = (doubleRadius / 2.0F);
/* 120:    */       }
/* 121:183 */       if (doubleRadius > useHeight)
/* 122:    */       {
/* 123:184 */         doubleRadius = useHeight;
/* 124:185 */         this.cornerRadius = (doubleRadius / 2.0F);
/* 125:    */       }
/* 126:188 */       ArrayList tempPoints = new ArrayList();
/* 127:193 */       if ((this.cornerFlags & 0x1) != 0)
/* 128:    */       {
/* 129:194 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + this.cornerRadius, 180.0F, 270.0F));
/* 130:    */       }
/* 131:    */       else
/* 132:    */       {
/* 133:196 */         tempPoints.add(new Float(this.x));
/* 134:197 */         tempPoints.add(new Float(this.y));
/* 135:    */       }
/* 136:201 */       if ((this.cornerFlags & 0x2) != 0)
/* 137:    */       {
/* 138:202 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + this.cornerRadius, 270.0F, 360.0F));
/* 139:    */       }
/* 140:    */       else
/* 141:    */       {
/* 142:204 */         tempPoints.add(new Float(this.x + useWidth));
/* 143:205 */         tempPoints.add(new Float(this.y));
/* 144:    */       }
/* 145:209 */       if ((this.cornerFlags & 0x4) != 0)
/* 146:    */       {
/* 147:210 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + useWidth - this.cornerRadius, this.y + useHeight - this.cornerRadius, 0.0F, 90.0F));
/* 148:    */       }
/* 149:    */       else
/* 150:    */       {
/* 151:212 */         tempPoints.add(new Float(this.x + useWidth));
/* 152:213 */         tempPoints.add(new Float(this.y + useHeight));
/* 153:    */       }
/* 154:217 */       if ((this.cornerFlags & 0x8) != 0)
/* 155:    */       {
/* 156:218 */         tempPoints.addAll(createPoints(this.segmentCount, this.cornerRadius, this.x + this.cornerRadius, this.y + useHeight - this.cornerRadius, 90.0F, 180.0F));
/* 157:    */       }
/* 158:    */       else
/* 159:    */       {
/* 160:220 */         tempPoints.add(new Float(this.x));
/* 161:221 */         tempPoints.add(new Float(this.y + useHeight));
/* 162:    */       }
/* 163:224 */       this.points = new float[tempPoints.size()];
/* 164:225 */       for (int i = 0; i < tempPoints.size(); i++) {
/* 165:226 */         this.points[i] = ((Float)tempPoints.get(i)).floatValue();
/* 166:    */       }
/* 167:    */     }
/* 168:230 */     findCenter();
/* 169:231 */     calculateRadius();
/* 170:    */   }
/* 171:    */   
/* 172:    */   private List createPoints(int numberOfSegments, float radius, float cx, float cy, float start, float end)
/* 173:    */   {
/* 174:246 */     ArrayList tempPoints = new ArrayList();
/* 175:    */     
/* 176:248 */     int step = 360 / numberOfSegments;
/* 177:250 */     for (float a = start; a <= end + step; a += step)
/* 178:    */     {
/* 179:251 */       float ang = a;
/* 180:252 */       if (ang > end) {
/* 181:253 */         ang = end;
/* 182:    */       }
/* 183:255 */       float x = (float)(cx + FastTrig.cos(Math.toRadians(ang)) * radius);
/* 184:256 */       float y = (float)(cy + FastTrig.sin(Math.toRadians(ang)) * radius);
/* 185:    */       
/* 186:258 */       tempPoints.add(new Float(x));
/* 187:259 */       tempPoints.add(new Float(y));
/* 188:    */     }
/* 189:262 */     return tempPoints;
/* 190:    */   }
/* 191:    */   
/* 192:    */   public Shape transform(Transform transform)
/* 193:    */   {
/* 194:272 */     checkPoints();
/* 195:    */     
/* 196:274 */     Polygon resultPolygon = new Polygon();
/* 197:    */     
/* 198:276 */     float[] result = new float[this.points.length];
/* 199:277 */     transform.transform(this.points, 0, result, 0, this.points.length / 2);
/* 200:278 */     resultPolygon.points = result;
/* 201:279 */     resultPolygon.findCenter();
/* 202:    */     
/* 203:281 */     return resultPolygon;
/* 204:    */   }
/* 205:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.RoundedRectangle
 * JD-Core Version:    0.7.0.1
 */