/*   1:    */ package org.newdawn.slick.geom;
/*   2:    */ 
/*   3:    */ import java.io.Serializable;
/*   4:    */ import org.newdawn.slick.util.FastTrig;
/*   5:    */ 
/*   6:    */ public class Vector2f
/*   7:    */   implements Serializable
/*   8:    */ {
/*   9:    */   private static final long serialVersionUID = 1339934L;
/*  10:    */   public float x;
/*  11:    */   public float y;
/*  12:    */   
/*  13:    */   public strictfp Vector2f() {}
/*  14:    */   
/*  15:    */   public strictfp Vector2f(float[] coords)
/*  16:    */   {
/*  17: 33 */     this.x = coords[0];
/*  18: 34 */     this.y = coords[1];
/*  19:    */   }
/*  20:    */   
/*  21:    */   public strictfp Vector2f(double theta)
/*  22:    */   {
/*  23: 43 */     this.x = 1.0F;
/*  24: 44 */     this.y = 0.0F;
/*  25: 45 */     setTheta(theta);
/*  26:    */   }
/*  27:    */   
/*  28:    */   public strictfp void setTheta(double theta)
/*  29:    */   {
/*  30: 56 */     if ((theta < -360.0D) || (theta > 360.0D)) {
/*  31: 57 */       theta %= 360.0D;
/*  32:    */     }
/*  33: 59 */     if (theta < 0.0D) {
/*  34: 60 */       theta += 360.0D;
/*  35:    */     }
/*  36: 62 */     double oldTheta = getTheta();
/*  37: 63 */     if ((theta < -360.0D) || (theta > 360.0D)) {
/*  38: 64 */       oldTheta %= 360.0D;
/*  39:    */     }
/*  40: 66 */     if (theta < 0.0D) {
/*  41: 67 */       oldTheta += 360.0D;
/*  42:    */     }
/*  43: 70 */     float len = length();
/*  44: 71 */     this.x = (len * (float)FastTrig.cos(StrictMath.toRadians(theta)));
/*  45: 72 */     this.y = (len * (float)FastTrig.sin(StrictMath.toRadians(theta)));
/*  46:    */   }
/*  47:    */   
/*  48:    */   public strictfp Vector2f add(double theta)
/*  49:    */   {
/*  50: 89 */     setTheta(getTheta() + theta);
/*  51:    */     
/*  52: 91 */     return this;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public strictfp Vector2f sub(double theta)
/*  56:    */   {
/*  57:101 */     setTheta(getTheta() - theta);
/*  58:    */     
/*  59:103 */     return this;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public strictfp double getTheta()
/*  63:    */   {
/*  64:112 */     double theta = StrictMath.toDegrees(StrictMath.atan2(this.y, this.x));
/*  65:113 */     if ((theta < -360.0D) || (theta > 360.0D)) {
/*  66:114 */       theta %= 360.0D;
/*  67:    */     }
/*  68:116 */     if (theta < 0.0D) {
/*  69:117 */       theta += 360.0D;
/*  70:    */     }
/*  71:120 */     return theta;
/*  72:    */   }
/*  73:    */   
/*  74:    */   public strictfp float getX()
/*  75:    */   {
/*  76:129 */     return this.x;
/*  77:    */   }
/*  78:    */   
/*  79:    */   public strictfp float getY()
/*  80:    */   {
/*  81:138 */     return this.y;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public strictfp Vector2f(Vector2f other)
/*  85:    */   {
/*  86:147 */     this(other.getX(), other.getY());
/*  87:    */   }
/*  88:    */   
/*  89:    */   public strictfp Vector2f(float x, float y)
/*  90:    */   {
/*  91:157 */     this.x = x;
/*  92:158 */     this.y = y;
/*  93:    */   }
/*  94:    */   
/*  95:    */   public strictfp void set(Vector2f other)
/*  96:    */   {
/*  97:167 */     set(other.getX(), other.getY());
/*  98:    */   }
/*  99:    */   
/* 100:    */   public strictfp float dot(Vector2f other)
/* 101:    */   {
/* 102:177 */     return this.x * other.getX() + this.y * other.getY();
/* 103:    */   }
/* 104:    */   
/* 105:    */   public strictfp Vector2f set(float x, float y)
/* 106:    */   {
/* 107:188 */     this.x = x;
/* 108:189 */     this.y = y;
/* 109:    */     
/* 110:191 */     return this;
/* 111:    */   }
/* 112:    */   
/* 113:    */   public strictfp Vector2f getPerpendicular()
/* 114:    */   {
/* 115:200 */     return new Vector2f(-this.y, this.x);
/* 116:    */   }
/* 117:    */   
/* 118:    */   public strictfp Vector2f set(float[] pt)
/* 119:    */   {
/* 120:210 */     return set(pt[0], pt[1]);
/* 121:    */   }
/* 122:    */   
/* 123:    */   public strictfp Vector2f negate()
/* 124:    */   {
/* 125:219 */     return new Vector2f(-this.x, -this.y);
/* 126:    */   }
/* 127:    */   
/* 128:    */   public strictfp Vector2f negateLocal()
/* 129:    */   {
/* 130:228 */     this.x = (-this.x);
/* 131:229 */     this.y = (-this.y);
/* 132:    */     
/* 133:231 */     return this;
/* 134:    */   }
/* 135:    */   
/* 136:    */   public strictfp Vector2f add(Vector2f v)
/* 137:    */   {
/* 138:242 */     this.x += v.getX();
/* 139:243 */     this.y += v.getY();
/* 140:    */     
/* 141:245 */     return this;
/* 142:    */   }
/* 143:    */   
/* 144:    */   public strictfp Vector2f sub(Vector2f v)
/* 145:    */   {
/* 146:256 */     this.x -= v.getX();
/* 147:257 */     this.y -= v.getY();
/* 148:    */     
/* 149:259 */     return this;
/* 150:    */   }
/* 151:    */   
/* 152:    */   public strictfp Vector2f scale(float a)
/* 153:    */   {
/* 154:270 */     this.x *= a;
/* 155:271 */     this.y *= a;
/* 156:    */     
/* 157:273 */     return this;
/* 158:    */   }
/* 159:    */   
/* 160:    */   public strictfp Vector2f normalise()
/* 161:    */   {
/* 162:282 */     float l = length();
/* 163:284 */     if (l == 0.0F) {
/* 164:285 */       return this;
/* 165:    */     }
/* 166:288 */     this.x /= l;
/* 167:289 */     this.y /= l;
/* 168:290 */     return this;
/* 169:    */   }
/* 170:    */   
/* 171:    */   public strictfp Vector2f getNormal()
/* 172:    */   {
/* 173:299 */     Vector2f cp = copy();
/* 174:300 */     cp.normalise();
/* 175:301 */     return cp;
/* 176:    */   }
/* 177:    */   
/* 178:    */   public strictfp float lengthSquared()
/* 179:    */   {
/* 180:310 */     return this.x * this.x + this.y * this.y;
/* 181:    */   }
/* 182:    */   
/* 183:    */   public strictfp float length()
/* 184:    */   {
/* 185:320 */     return (float)Math.sqrt(lengthSquared());
/* 186:    */   }
/* 187:    */   
/* 188:    */   public strictfp void projectOntoUnit(Vector2f b, Vector2f result)
/* 189:    */   {
/* 190:330 */     float dp = b.dot(this);
/* 191:    */     
/* 192:332 */     result.x = (dp * b.getX());
/* 193:333 */     result.y = (dp * b.getY());
/* 194:    */   }
/* 195:    */   
/* 196:    */   public strictfp Vector2f copy()
/* 197:    */   {
/* 198:343 */     return new Vector2f(this.x, this.y);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public strictfp String toString()
/* 202:    */   {
/* 203:350 */     return "[Vector2f " + this.x + "," + this.y + " (" + length() + ")]";
/* 204:    */   }
/* 205:    */   
/* 206:    */   public strictfp float distance(Vector2f other)
/* 207:    */   {
/* 208:360 */     return (float)Math.sqrt(distanceSquared(other));
/* 209:    */   }
/* 210:    */   
/* 211:    */   public strictfp float distanceSquared(Vector2f other)
/* 212:    */   {
/* 213:372 */     float dx = other.getX() - getX();
/* 214:373 */     float dy = other.getY() - getY();
/* 215:    */     
/* 216:375 */     return dx * dx + dy * dy;
/* 217:    */   }
/* 218:    */   
/* 219:    */   public strictfp int hashCode()
/* 220:    */   {
/* 221:382 */     return 997 * (int)this.x ^ 991 * (int)this.y;
/* 222:    */   }
/* 223:    */   
/* 224:    */   public strictfp boolean equals(Object other)
/* 225:    */   {
/* 226:389 */     if ((other instanceof Vector2f))
/* 227:    */     {
/* 228:390 */       Vector2f o = (Vector2f)other;
/* 229:391 */       return (o.x == this.x) && (o.y == this.y);
/* 230:    */     }
/* 231:394 */     return false;
/* 232:    */   }
/* 233:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     org.newdawn.slick.geom.Vector2f
 * JD-Core Version:    0.7.0.1
 */