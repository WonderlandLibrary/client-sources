/*   1:    */ package net.minecraft.src;
/*   2:    */ 
/*   3:    */ import java.util.Properties;
/*   4:    */ import net.minecraft.client.renderer.Tessellator;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ 
/*   7:    */ public class CustomSkyLayer
/*   8:    */ {
/*   9:  9 */   public String source = null;
/*  10: 10 */   private int startFadeIn = -1;
/*  11: 11 */   private int endFadeIn = -1;
/*  12: 12 */   private int startFadeOut = -1;
/*  13: 13 */   private int endFadeOut = -1;
/*  14: 14 */   private int blend = 0;
/*  15: 15 */   private boolean rotate = false;
/*  16: 16 */   private float speed = 1.0F;
/*  17:    */   private float[] axis;
/*  18:    */   public int textureId;
/*  19:    */   public static final int BLEND_ADD = 0;
/*  20:    */   public static final int BLEND_SUBSTRACT = 1;
/*  21:    */   public static final int BLEND_MULTIPLY = 2;
/*  22:    */   public static final int BLEND_DODGE = 3;
/*  23:    */   public static final int BLEND_BURN = 4;
/*  24:    */   public static final int BLEND_SCREEN = 5;
/*  25:    */   public static final int BLEND_REPLACE = 6;
/*  26: 26 */   public static final float[] DEFAULT_AXIS = { 1.0F, 0.0F, 0.0F };
/*  27:    */   
/*  28:    */   public CustomSkyLayer(Properties props, String defSource)
/*  29:    */   {
/*  30: 30 */     this.axis = DEFAULT_AXIS;
/*  31: 31 */     this.textureId = -1;
/*  32: 32 */     this.source = props.getProperty("source", defSource);
/*  33: 33 */     this.startFadeIn = parseTime(props.getProperty("startFadeIn"));
/*  34: 34 */     this.endFadeIn = parseTime(props.getProperty("endFadeIn"));
/*  35: 35 */     this.startFadeOut = parseTime(props.getProperty("startFadeOut"));
/*  36: 36 */     this.endFadeOut = parseTime(props.getProperty("endFadeOut"));
/*  37: 37 */     this.blend = parseBlend(props.getProperty("blend"));
/*  38: 38 */     this.rotate = parseBoolean(props.getProperty("rotate"), true);
/*  39: 39 */     this.speed = parseFloat(props.getProperty("speed"), 1.0F);
/*  40: 40 */     this.axis = parseAxis(props.getProperty("axis"), DEFAULT_AXIS);
/*  41:    */   }
/*  42:    */   
/*  43:    */   private int parseTime(String str)
/*  44:    */   {
/*  45: 45 */     if (str == null) {
/*  46: 47 */       return -1;
/*  47:    */     }
/*  48: 51 */     String[] strs = Config.tokenize(str, ":");
/*  49: 53 */     if (strs.length != 2)
/*  50:    */     {
/*  51: 55 */       Config.warn("Invalid time: " + str);
/*  52: 56 */       return -1;
/*  53:    */     }
/*  54: 60 */     String hourStr = strs[0];
/*  55: 61 */     String minStr = strs[1];
/*  56: 62 */     int hour = Config.parseInt(hourStr, -1);
/*  57: 63 */     int min = Config.parseInt(minStr, -1);
/*  58: 65 */     if ((hour >= 0) && (hour <= 23) && (min >= 0) && (min <= 59))
/*  59:    */     {
/*  60: 67 */       hour -= 6;
/*  61: 69 */       if (hour < 0) {
/*  62: 71 */         hour += 24;
/*  63:    */       }
/*  64: 74 */       int time = hour * 1000 + (int)(min / 60.0D * 1000.0D);
/*  65: 75 */       return time;
/*  66:    */     }
/*  67: 79 */     Config.warn("Invalid time: " + str);
/*  68: 80 */     return -1;
/*  69:    */   }
/*  70:    */   
/*  71:    */   private int parseBlend(String str)
/*  72:    */   {
/*  73: 88 */     if (str == null) {
/*  74: 90 */       return 0;
/*  75:    */     }
/*  76: 92 */     if (str.equals("add")) {
/*  77: 94 */       return 0;
/*  78:    */     }
/*  79: 96 */     if (str.equals("subtract")) {
/*  80: 98 */       return 1;
/*  81:    */     }
/*  82:100 */     if (str.equals("multiply")) {
/*  83:102 */       return 2;
/*  84:    */     }
/*  85:104 */     if (str.equals("dodge")) {
/*  86:106 */       return 3;
/*  87:    */     }
/*  88:108 */     if (str.equals("burn")) {
/*  89:110 */       return 4;
/*  90:    */     }
/*  91:112 */     if (str.equals("screen")) {
/*  92:114 */       return 5;
/*  93:    */     }
/*  94:116 */     if (str.equals("replace")) {
/*  95:118 */       return 6;
/*  96:    */     }
/*  97:122 */     Config.warn("Unknown blend: " + str);
/*  98:123 */     return 0;
/*  99:    */   }
/* 100:    */   
/* 101:    */   private boolean parseBoolean(String str, boolean defVal)
/* 102:    */   {
/* 103:129 */     if (str == null) {
/* 104:131 */       return defVal;
/* 105:    */     }
/* 106:133 */     if (str.toLowerCase().equals("true")) {
/* 107:135 */       return true;
/* 108:    */     }
/* 109:137 */     if (str.toLowerCase().equals("false")) {
/* 110:139 */       return false;
/* 111:    */     }
/* 112:143 */     Config.warn("Unknown boolean: " + str);
/* 113:144 */     return defVal;
/* 114:    */   }
/* 115:    */   
/* 116:    */   private float parseFloat(String str, float defVal)
/* 117:    */   {
/* 118:150 */     if (str == null) {
/* 119:152 */       return defVal;
/* 120:    */     }
/* 121:156 */     float val = Config.parseFloat(str, 1.4E-45F);
/* 122:158 */     if (val == 1.4E-45F)
/* 123:    */     {
/* 124:160 */       Config.warn("Invalid value: " + str);
/* 125:161 */       return defVal;
/* 126:    */     }
/* 127:165 */     return val;
/* 128:    */   }
/* 129:    */   
/* 130:    */   private float[] parseAxis(String str, float[] defVal)
/* 131:    */   {
/* 132:172 */     if (str == null) {
/* 133:174 */       return defVal;
/* 134:    */     }
/* 135:178 */     String[] strs = Config.tokenize(str, " ");
/* 136:180 */     if (strs.length != 3)
/* 137:    */     {
/* 138:182 */       Config.warn("Invalid axis: " + str);
/* 139:183 */       return defVal;
/* 140:    */     }
/* 141:187 */     float[] fs = new float[3];
/* 142:189 */     for (int ax = 0; ax < strs.length; ax++)
/* 143:    */     {
/* 144:191 */       fs[ax] = Config.parseFloat(strs[ax], 1.4E-45F);
/* 145:193 */       if (fs[ax] == 1.4E-45F)
/* 146:    */       {
/* 147:195 */         Config.warn("Invalid axis: " + str);
/* 148:196 */         return defVal;
/* 149:    */       }
/* 150:199 */       if ((fs[ax] < -1.0F) || (fs[ax] > 1.0F))
/* 151:    */       {
/* 152:201 */         Config.warn("Invalid axis values: " + str);
/* 153:202 */         return defVal;
/* 154:    */       }
/* 155:    */     }
/* 156:206 */     float var9 = fs[0];
/* 157:207 */     float ay = fs[1];
/* 158:208 */     float az = fs[2];
/* 159:210 */     if (var9 * var9 + ay * ay + az * az < 1.0E-005F)
/* 160:    */     {
/* 161:212 */       Config.warn("Invalid axis values: " + str);
/* 162:213 */       return defVal;
/* 163:    */     }
/* 164:217 */     float[] as = { az, ay, -var9 };
/* 165:218 */     return as;
/* 166:    */   }
/* 167:    */   
/* 168:    */   public boolean isValid(String path)
/* 169:    */   {
/* 170:226 */     if (this.source == null)
/* 171:    */     {
/* 172:228 */       Config.warn("No source texture: " + path);
/* 173:229 */       return false;
/* 174:    */     }
/* 175:233 */     this.source = TextureUtils.fixResourcePath(this.source, TextureUtils.getBasePath(path));
/* 176:235 */     if ((this.startFadeIn >= 0) && (this.endFadeIn >= 0) && (this.endFadeOut >= 0))
/* 177:    */     {
/* 178:237 */       int timeFadeIn = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 179:239 */       if (this.startFadeOut < 0) {
/* 180:241 */         this.startFadeOut = normalizeTime(this.endFadeOut - timeFadeIn);
/* 181:    */       }
/* 182:244 */       int timeOn = normalizeTime(this.startFadeOut - this.endFadeIn);
/* 183:245 */       int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 184:246 */       int timeOff = normalizeTime(this.startFadeIn - this.endFadeOut);
/* 185:247 */       int timeSum = timeFadeIn + timeOn + timeFadeOut + timeOff;
/* 186:249 */       if (timeSum != 24000)
/* 187:    */       {
/* 188:251 */         Config.warn("Invalid fadeIn/fadeOut times, sum is more than 24h: " + timeSum);
/* 189:252 */         return false;
/* 190:    */       }
/* 191:254 */       if (this.speed < 0.0F)
/* 192:    */       {
/* 193:256 */         Config.warn("Invalid speed: " + this.speed);
/* 194:257 */         return false;
/* 195:    */       }
/* 196:261 */       return true;
/* 197:    */     }
/* 198:266 */     Config.warn("Invalid times, required are: startFadeIn, endFadeIn and endFadeOut.");
/* 199:267 */     return false;
/* 200:    */   }
/* 201:    */   
/* 202:    */   private int normalizeTime(int timeMc)
/* 203:    */   {
/* 204:274 */     while (timeMc >= 24000) {
/* 205:276 */       timeMc -= 24000;
/* 206:    */     }
/* 207:279 */     while (timeMc < 0) {
/* 208:281 */       timeMc += 24000;
/* 209:    */     }
/* 210:284 */     return timeMc;
/* 211:    */   }
/* 212:    */   
/* 213:    */   public void render(int timeOfDay, float celestialAngle, float rainBrightness)
/* 214:    */   {
/* 215:289 */     float brightness = rainBrightness * getFadeBrightness(timeOfDay);
/* 216:290 */     brightness = Config.limit(brightness, 0.0F, 1.0F);
/* 217:292 */     if (brightness >= 1.0E-004F)
/* 218:    */     {
/* 219:294 */       GL11.glBindTexture(3553, this.textureId);
/* 220:295 */       setupBlend(brightness);
/* 221:296 */       GL11.glPushMatrix();
/* 222:298 */       if (this.rotate) {
/* 223:300 */         GL11.glRotatef(celestialAngle * 360.0F * this.speed, this.axis[0], this.axis[1], this.axis[2]);
/* 224:    */       }
/* 225:303 */       Tessellator tess = Tessellator.instance;
/* 226:304 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 227:305 */       GL11.glRotatef(-90.0F, 0.0F, 0.0F, 1.0F);
/* 228:306 */       renderSide(tess, 4);
/* 229:307 */       GL11.glPushMatrix();
/* 230:308 */       GL11.glRotatef(90.0F, 1.0F, 0.0F, 0.0F);
/* 231:309 */       renderSide(tess, 1);
/* 232:310 */       GL11.glPopMatrix();
/* 233:311 */       GL11.glPushMatrix();
/* 234:312 */       GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
/* 235:313 */       renderSide(tess, 0);
/* 236:314 */       GL11.glPopMatrix();
/* 237:315 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 238:316 */       renderSide(tess, 5);
/* 239:317 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 240:318 */       renderSide(tess, 2);
/* 241:319 */       GL11.glRotatef(90.0F, 0.0F, 0.0F, 1.0F);
/* 242:320 */       renderSide(tess, 3);
/* 243:321 */       GL11.glPopMatrix();
/* 244:    */     }
/* 245:    */   }
/* 246:    */   
/* 247:    */   private float getFadeBrightness(int timeOfDay)
/* 248:    */   {
/* 249:330 */     if (timeBetween(timeOfDay, this.startFadeIn, this.endFadeIn))
/* 250:    */     {
/* 251:332 */       int timeFadeOut = normalizeTime(this.endFadeIn - this.startFadeIn);
/* 252:333 */       int timeDiff = normalizeTime(timeOfDay - this.startFadeIn);
/* 253:334 */       return timeDiff / timeFadeOut;
/* 254:    */     }
/* 255:336 */     if (timeBetween(timeOfDay, this.endFadeIn, this.startFadeOut)) {
/* 256:338 */       return 1.0F;
/* 257:    */     }
/* 258:340 */     if (timeBetween(timeOfDay, this.startFadeOut, this.endFadeOut))
/* 259:    */     {
/* 260:342 */       int timeFadeOut = normalizeTime(this.endFadeOut - this.startFadeOut);
/* 261:343 */       int timeDiff = normalizeTime(timeOfDay - this.startFadeOut);
/* 262:344 */       return 1.0F - timeDiff / timeFadeOut;
/* 263:    */     }
/* 264:348 */     return 0.0F;
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void renderSide(Tessellator tess, int side)
/* 268:    */   {
/* 269:354 */     double tx = side % 3 / 3.0D;
/* 270:355 */     double ty = side / 3 / 2.0D;
/* 271:356 */     tess.startDrawingQuads();
/* 272:357 */     tess.addVertexWithUV(-100.0D, -100.0D, -100.0D, tx, ty);
/* 273:358 */     tess.addVertexWithUV(-100.0D, -100.0D, 100.0D, tx, ty + 0.5D);
/* 274:359 */     tess.addVertexWithUV(100.0D, -100.0D, 100.0D, tx + 0.3333333333333333D, ty + 0.5D);
/* 275:360 */     tess.addVertexWithUV(100.0D, -100.0D, -100.0D, tx + 0.3333333333333333D, ty);
/* 276:361 */     tess.draw();
/* 277:    */   }
/* 278:    */   
/* 279:    */   void setupBlend(float brightness)
/* 280:    */   {
/* 281:366 */     switch (this.blend)
/* 282:    */     {
/* 283:    */     case 0: 
/* 284:369 */       GL11.glDisable(3008);
/* 285:370 */       GL11.glEnable(3042);
/* 286:371 */       GL11.glBlendFunc(770, 1);
/* 287:372 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
/* 288:373 */       break;
/* 289:    */     case 1: 
/* 290:376 */       GL11.glDisable(3008);
/* 291:377 */       GL11.glEnable(3042);
/* 292:378 */       GL11.glBlendFunc(775, 0);
/* 293:379 */       GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/* 294:380 */       break;
/* 295:    */     case 2: 
/* 296:383 */       GL11.glDisable(3008);
/* 297:384 */       GL11.glEnable(3042);
/* 298:385 */       GL11.glBlendFunc(774, 771);
/* 299:386 */       GL11.glColor4f(brightness, brightness, brightness, brightness);
/* 300:387 */       break;
/* 301:    */     case 3: 
/* 302:390 */       GL11.glDisable(3008);
/* 303:391 */       GL11.glEnable(3042);
/* 304:392 */       GL11.glBlendFunc(1, 1);
/* 305:393 */       GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/* 306:394 */       break;
/* 307:    */     case 4: 
/* 308:397 */       GL11.glDisable(3008);
/* 309:398 */       GL11.glEnable(3042);
/* 310:399 */       GL11.glBlendFunc(0, 769);
/* 311:400 */       GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/* 312:401 */       break;
/* 313:    */     case 5: 
/* 314:404 */       GL11.glDisable(3008);
/* 315:405 */       GL11.glEnable(3042);
/* 316:406 */       GL11.glBlendFunc(1, 769);
/* 317:407 */       GL11.glColor4f(brightness, brightness, brightness, 1.0F);
/* 318:408 */       break;
/* 319:    */     case 6: 
/* 320:411 */       GL11.glEnable(3008);
/* 321:412 */       GL11.glDisable(3042);
/* 322:413 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, brightness);
/* 323:    */     }
/* 324:416 */     GL11.glEnable(3553);
/* 325:    */   }
/* 326:    */   
/* 327:    */   public boolean isActive(int timeOfDay)
/* 328:    */   {
/* 329:421 */     return !timeBetween(timeOfDay, this.endFadeOut, this.startFadeIn);
/* 330:    */   }
/* 331:    */   
/* 332:    */   private boolean timeBetween(int timeOfDay, int timeStart, int timeEnd)
/* 333:    */   {
/* 334:426 */     return (timeOfDay >= timeStart) && (timeOfDay <= timeEnd);
/* 335:    */   }
/* 336:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.src.CustomSkyLayer
 * JD-Core Version:    0.7.0.1
 */