/*   1:    */ package me.connorm.Nodus.utils;
/*   2:    */ 
/*   3:    */ import java.awt.Color;
/*   4:    */ import me.connorm.Nodus.Nodus;
/*   5:    */ import net.minecraft.client.Minecraft;
/*   6:    */ import net.minecraft.client.gui.Gui;
/*   7:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   8:    */ import net.minecraft.client.renderer.Tessellator;
/*   9:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  10:    */ import net.minecraft.item.ItemStack;
/*  11:    */ import net.minecraft.util.AxisAlignedBB;
/*  12:    */ import org.lwjgl.opengl.GL11;
/*  13:    */ 
/*  14:    */ public class RenderUtils
/*  15:    */   extends Gui
/*  16:    */ {
/*  17: 17 */   private static RenderItem itemRenderer = new RenderItem();
/*  18:    */   
/*  19:    */   public static void drawOutlinedBoundingBox(AxisAlignedBB aa)
/*  20:    */   {
/*  21: 21 */     Tessellator t = Tessellator.instance;
/*  22: 22 */     t.startDrawing(3);
/*  23: 23 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  24: 24 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  25: 25 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  26: 26 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  27: 27 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  28: 28 */     t.draw();
/*  29: 29 */     t.startDrawing(3);
/*  30: 30 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  31: 31 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  32: 32 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  33: 33 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  34: 34 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  35: 35 */     t.draw();
/*  36: 36 */     t.startDrawing(1);
/*  37: 37 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  38: 38 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  39: 39 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  40: 40 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  41: 41 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  42: 42 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  43: 43 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  44: 44 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  45: 45 */     t.draw();
/*  46:    */   }
/*  47:    */   
/*  48:    */   public static void drawBoundingBox(AxisAlignedBB aa)
/*  49:    */   {
/*  50: 50 */     Tessellator t = Tessellator.instance;
/*  51: 51 */     t.startDrawingQuads();
/*  52: 52 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  53: 53 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  54: 54 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  55: 55 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  56: 56 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  57: 57 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  58: 58 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  59: 59 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  60: 60 */     t.draw();
/*  61: 61 */     t.startDrawingQuads();
/*  62: 62 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  63: 63 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  64: 64 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  65: 65 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  66: 66 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  67: 67 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  68: 68 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  69: 69 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  70: 70 */     t.draw();
/*  71: 71 */     t.startDrawingQuads();
/*  72: 72 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  73: 73 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  74: 74 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  75: 75 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  76: 76 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  77: 77 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  78: 78 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  79: 79 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/*  80: 80 */     t.draw();
/*  81: 81 */     t.startDrawingQuads();
/*  82: 82 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  83: 83 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  84: 84 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  85: 85 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  86: 86 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  87: 87 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  88: 88 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  89: 89 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  90: 90 */     t.draw();
/*  91: 91 */     t.startDrawingQuads();
/*  92: 92 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/*  93: 93 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/*  94: 94 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/*  95: 95 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/*  96: 96 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/*  97: 97 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/*  98: 98 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/*  99: 99 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/* 100:100 */     t.draw();
/* 101:101 */     t.startDrawingQuads();
/* 102:102 */     t.addVertex(aa.minX, aa.maxY, aa.maxZ);
/* 103:103 */     t.addVertex(aa.minX, aa.minY, aa.maxZ);
/* 104:104 */     t.addVertex(aa.minX, aa.maxY, aa.minZ);
/* 105:105 */     t.addVertex(aa.minX, aa.minY, aa.minZ);
/* 106:106 */     t.addVertex(aa.maxX, aa.maxY, aa.minZ);
/* 107:107 */     t.addVertex(aa.maxX, aa.minY, aa.minZ);
/* 108:108 */     t.addVertex(aa.maxX, aa.maxY, aa.maxZ);
/* 109:109 */     t.addVertex(aa.maxX, aa.minY, aa.maxZ);
/* 110:110 */     t.draw();
/* 111:    */   }
/* 112:    */   
/* 113:    */   public static void drawESP(double d, double d1, double d2, double r, double b, double g, double alpha)
/* 114:    */   {
/* 115:115 */     GL11.glPushMatrix();
/* 116:116 */     GL11.glEnable(3042);
/* 117:117 */     GL11.glBlendFunc(770, 771);
/* 118:118 */     GL11.glLineWidth(1.0F);
/* 119:119 */     GL11.glDisable(2896);
/* 120:120 */     GL11.glDisable(3553);
/* 121:121 */     GL11.glEnable(2848);
/* 122:122 */     GL11.glDisable(2929);
/* 123:123 */     GL11.glDepthMask(false);
/* 124:124 */     GL11.glColor4d(r, g, b, alpha);
/* 125:125 */     drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D));
/* 126:126 */     GL11.glColor4d(r, g, b, 1.0D);
/* 127:127 */     drawOutlinedBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D));
/* 128:128 */     GL11.glLineWidth(2.0F);
/* 129:129 */     GL11.glDisable(2848);
/* 130:130 */     GL11.glEnable(3553);
/* 131:131 */     GL11.glEnable(2896);
/* 132:132 */     GL11.glEnable(2929);
/* 133:133 */     GL11.glDepthMask(true);
/* 134:134 */     GL11.glDisable(3042);
/* 135:135 */     GL11.glPopMatrix();
/* 136:    */   }
/* 137:    */   
/* 138:    */   public static void drawNukerESP(double d, double d1, double d2, double r, double b, double g, double alpha)
/* 139:    */   {
/* 140:140 */     GL11.glPushMatrix();
/* 141:141 */     GL11.glEnable(3042);
/* 142:142 */     GL11.glBlendFunc(770, 771);
/* 143:143 */     GL11.glLineWidth(1.0F);
/* 144:144 */     GL11.glDisable(2896);
/* 145:145 */     GL11.glDisable(3553);
/* 146:146 */     GL11.glEnable(2848);
/* 147:147 */     GL11.glDisable(2929);
/* 148:148 */     GL11.glDepthMask(false);
/* 149:149 */     GL11.glColor4d(r, g, b, alpha);
/* 150:150 */     drawBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D));
/* 151:151 */     GL11.glColor4d(r, g, b, 0.6D);
/* 152:152 */     drawOutlinedBoundingBox(new AxisAlignedBB(d, d1, d2, d + 1.0D, d1 + 1.0D, d2 + 1.0D));
/* 153:153 */     GL11.glLineWidth(2.0F);
/* 154:154 */     GL11.glDisable(2848);
/* 155:155 */     GL11.glEnable(3553);
/* 156:156 */     GL11.glEnable(2896);
/* 157:157 */     GL11.glEnable(2929);
/* 158:158 */     GL11.glDepthMask(true);
/* 159:159 */     GL11.glDisable(3042);
/* 160:160 */     GL11.glPopMatrix();
/* 161:    */   }
/* 162:    */   
/* 163:    */   public static void drawGradientRect(double x, double y, double x2, double y2, int col1, int col2)
/* 164:    */   {
/* 165:165 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 166:166 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/* 167:167 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/* 168:168 */     float f3 = (col1 & 0xFF) / 255.0F;
/* 169:    */     
/* 170:170 */     float f4 = (col2 >> 24 & 0xFF) / 255.0F;
/* 171:171 */     float f5 = (col2 >> 16 & 0xFF) / 255.0F;
/* 172:172 */     float f6 = (col2 >> 8 & 0xFF) / 255.0F;
/* 173:173 */     float f7 = (col2 & 0xFF) / 255.0F;
/* 174:    */     
/* 175:175 */     GL11.glEnable(3042);
/* 176:176 */     GL11.glDisable(3553);
/* 177:177 */     GL11.glBlendFunc(770, 771);
/* 178:178 */     GL11.glEnable(2848);
/* 179:179 */     GL11.glShadeModel(7425);
/* 180:    */     
/* 181:181 */     GL11.glPushMatrix();
/* 182:182 */     GL11.glBegin(7);
/* 183:183 */     GL11.glColor4f(f1, f2, f3, f);
/* 184:184 */     GL11.glVertex2d(x2, y);
/* 185:185 */     GL11.glVertex2d(x, y);
/* 186:    */     
/* 187:187 */     GL11.glColor4f(f5, f6, f7, f4);
/* 188:188 */     GL11.glVertex2d(x, y2);
/* 189:189 */     GL11.glVertex2d(x2, y2);
/* 190:190 */     GL11.glEnd();
/* 191:191 */     GL11.glPopMatrix();
/* 192:    */     
/* 193:193 */     GL11.glEnable(3553);
/* 194:194 */     GL11.glDisable(3042);
/* 195:195 */     GL11.glDisable(2848);
/* 196:196 */     GL11.glShadeModel(7424);
/* 197:    */   }
/* 198:    */   
/* 199:    */   public static void drawGradientBorderedRect(double x, double y, double x2, double y2, float l1, int col1, int col2, int col3)
/* 200:    */   {
/* 201:201 */     float f = (col1 >> 24 & 0xFF) / 255.0F;
/* 202:202 */     float f1 = (col1 >> 16 & 0xFF) / 255.0F;
/* 203:203 */     float f2 = (col1 >> 8 & 0xFF) / 255.0F;
/* 204:204 */     float f3 = (col1 & 0xFF) / 255.0F;
/* 205:    */     
/* 206:206 */     GL11.glDisable(3553);
/* 207:207 */     GL11.glBlendFunc(770, 771);
/* 208:208 */     GL11.glEnable(2848);
/* 209:209 */     GL11.glDisable(3042);
/* 210:    */     
/* 211:211 */     GL11.glPushMatrix();
/* 212:212 */     GL11.glColor4f(f1, f2, f3, f);
/* 213:213 */     GL11.glLineWidth(1.0F);
/* 214:214 */     GL11.glBegin(1);
/* 215:215 */     GL11.glVertex2d(x, y);
/* 216:216 */     GL11.glVertex2d(x, y2);
/* 217:217 */     GL11.glVertex2d(x2, y2);
/* 218:218 */     GL11.glVertex2d(x2, y);
/* 219:219 */     GL11.glVertex2d(x, y);
/* 220:220 */     GL11.glVertex2d(x2, y);
/* 221:221 */     GL11.glVertex2d(x, y2);
/* 222:222 */     GL11.glVertex2d(x2, y2);
/* 223:223 */     GL11.glEnd();
/* 224:224 */     GL11.glPopMatrix();
/* 225:    */     
/* 226:226 */     drawGradientRect(x, y, x2, y2, col2, col3);
/* 227:    */     
/* 228:228 */     GL11.glEnable(3042);
/* 229:229 */     GL11.glEnable(3553);
/* 230:230 */     GL11.glDisable(2848);
/* 231:    */   }
/* 232:    */   
/* 233:    */   public static void drawRect(float par1, float f, float g, float par3, int par4)
/* 234:    */   {
/* 235:235 */     if (par1 < g)
/* 236:    */     {
/* 237:237 */       float var5 = par1;
/* 238:238 */       par1 = g;
/* 239:239 */       g = var5;
/* 240:    */     }
/* 241:242 */     if (f < par3)
/* 242:    */     {
/* 243:244 */       float var5 = f;
/* 244:245 */       f = par3;
/* 245:246 */       par3 = var5;
/* 246:    */     }
/* 247:249 */     float var10 = (par4 >> 24 & 0xFF) / 255.0F;
/* 248:250 */     float var6 = (par4 >> 16 & 0xFF) / 255.0F;
/* 249:251 */     float var7 = (par4 >> 8 & 0xFF) / 255.0F;
/* 250:252 */     float var8 = (par4 & 0xFF) / 255.0F;
/* 251:253 */     Tessellator var9 = Tessellator.instance;
/* 252:254 */     GL11.glEnable(3042);
/* 253:255 */     GL11.glDisable(3553);
/* 254:256 */     GL11.glDisable(2896);
/* 255:257 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 256:258 */     GL11.glColor4f(var6, var7, var8, var10);
/* 257:259 */     var9.startDrawingQuads();
/* 258:260 */     var9.addVertex(par1, par3, 0.0D);
/* 259:261 */     var9.addVertex(g, par3, 0.0D);
/* 260:262 */     var9.addVertex(g, f, 0.0D);
/* 261:263 */     var9.addVertex(par1, f, 0.0D);
/* 262:264 */     var9.draw();
/* 263:265 */     GL11.glEnable(3553);
/* 264:266 */     GL11.glDisable(3042);
/* 265:    */   }
/* 266:    */   
/* 267:    */   public static void drawBorderedRect(float x, float y, float x1, float y1, int borderC, int insideC)
/* 268:    */   {
/* 269:271 */     x *= 2.0F;x1 *= 2.0F;y *= 2.0F;y1 *= 2.0F;
/* 270:272 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 271:273 */     drawVLine(x, y, y1 - 1.0F, borderC);
/* 272:274 */     drawVLine(x1 - 1.0F, y, y1, borderC);
/* 273:275 */     drawHLine(x, x1 - 1.0F, y, borderC);
/* 274:276 */     drawHLine(x, x1 - 2.0F, y1 - 1.0F, borderC);
/* 275:277 */     drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
/* 276:278 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 277:    */   }
/* 278:    */   
/* 279:    */   public static void drawHLine(float par1, float par2, float par3, int par4)
/* 280:    */   {
/* 281:283 */     if (par2 < par1)
/* 282:    */     {
/* 283:285 */       float var5 = par1;
/* 284:286 */       par1 = par2;
/* 285:287 */       par2 = var5;
/* 286:    */     }
/* 287:290 */     drawRect(par1, par3, par2 + 1.0F, par3 + 1.0F, par4);
/* 288:    */   }
/* 289:    */   
/* 290:    */   public static void drawVLine(float par1, float par2, float par3, int par4)
/* 291:    */   {
/* 292:295 */     if (par3 < par2)
/* 293:    */     {
/* 294:297 */       float var5 = par2;
/* 295:298 */       par2 = par3;
/* 296:299 */       par3 = var5;
/* 297:    */     }
/* 298:302 */     drawRect(par1, par2 + 1.0F, par1 + 1.0F, par3, par4);
/* 299:    */   }
/* 300:    */   
/* 301:    */   public static void drawRoundedRect(float x, float y, float x1, float y1, int borderC, int insideC)
/* 302:    */   {
/* 303:306 */     x *= 2.0F;y *= 2.0F;x1 *= 2.0F;y1 *= 2.0F;
/* 304:307 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 305:308 */     drawVLine(x, y + 1.0F, y1 - 2.0F, borderC);
/* 306:309 */     drawVLine(x1 - 1.0F, y + 1.0F, y1 - 2.0F, borderC);
/* 307:310 */     drawHLine(x + 2.0F, x1 - 3.0F, y, borderC);
/* 308:311 */     drawHLine(x + 2.0F, x1 - 3.0F, y1 - 1.0F, borderC);
/* 309:312 */     drawHLine(x + 1.0F, x + 1.0F, y + 1.0F, borderC);
/* 310:313 */     drawHLine(x1 - 2.0F, x1 - 2.0F, y + 1.0F, borderC);
/* 311:314 */     drawHLine(x1 - 2.0F, x1 - 2.0F, y1 - 2.0F, borderC);
/* 312:315 */     drawHLine(x + 1.0F, x + 1.0F, y1 - 2.0F, borderC);
/* 313:316 */     drawRect(x + 1.0F, y + 1.0F, x1 - 1.0F, y1 - 1.0F, insideC);
/* 314:317 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 315:    */   }
/* 316:    */   
/* 317:    */   public static void drawRGBRect(float par1, float f, float g, float par3, Color par4)
/* 318:    */   {
/* 319:322 */     if (par1 < g)
/* 320:    */     {
/* 321:324 */       float var5 = par1;
/* 322:325 */       par1 = g;
/* 323:326 */       g = var5;
/* 324:    */     }
/* 325:329 */     if (f < par3)
/* 326:    */     {
/* 327:331 */       float var5 = f;
/* 328:332 */       f = par3;
/* 329:333 */       par3 = var5;
/* 330:    */     }
/* 331:336 */     Tessellator var9 = Tessellator.instance;
/* 332:337 */     GL11.glEnable(3042);
/* 333:338 */     GL11.glDisable(3553);
/* 334:339 */     GL11.glDisable(2896);
/* 335:340 */     OpenGlHelper.glBlendFunc(770, 771, 1, 0);
/* 336:341 */     GL11.glColor4f(par4.getRed(), par4.getGreen(), par4.getBlue(), 63.0F);
/* 337:342 */     var9.startDrawingQuads();
/* 338:343 */     var9.addVertex(par1, par3, 0.0D);
/* 339:344 */     var9.addVertex(g, par3, 0.0D);
/* 340:345 */     var9.addVertex(g, f, 0.0D);
/* 341:346 */     var9.addVertex(par1, f, 0.0D);
/* 342:347 */     var9.draw();
/* 343:348 */     GL11.glEnable(3553);
/* 344:349 */     GL11.glDisable(3042);
/* 345:    */   }
/* 346:    */   
/* 347:    */   public static void drawItem(int x, int y, ItemStack stack)
/* 348:    */   {
/* 349:354 */     itemRenderer.renderItemIntoGUI(Nodus.theNodus.getMinecraft().fontRenderer, Nodus.theNodus.getMinecraft().renderEngine, stack, x, y);
/* 350:355 */     itemRenderer.renderItemAndEffectIntoGUI(Nodus.theNodus.getMinecraft().fontRenderer, Nodus.theNodus.getMinecraft().renderEngine, stack, x, y);
/* 351:    */     
/* 352:357 */     GL11.glDisable(2884);
/* 353:358 */     GL11.glEnable(3008);
/* 354:359 */     GL11.glDisable(3042);
/* 355:360 */     GL11.glDisable(2896);
/* 356:361 */     GL11.glDisable(2884);
/* 357:362 */     GL11.glClear(256);
/* 358:    */   }
/* 359:    */   
/* 360:    */   public static final void drawNametagRect(double x, double y, double x1, double y1, int color2, int color)
/* 361:    */   {
/* 362:367 */     GL11.glEnable(3042);
/* 363:368 */     GL11.glEnable(2848);
/* 364:369 */     drawRect((int)x, (int)y, (int)x1, (int)y1, color);
/* 365:370 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 366:371 */     drawRect((int)x * 2 - 1, (int)y * 2, (int)x * 2, (int)y1 * 2 - 1, color2);
/* 367:372 */     drawRect((int)x * 2, (int)y * 2 - 1, (int)x1 * 2, (int)y * 2, color2);
/* 368:373 */     drawRect((int)x1 * 2, (int)y * 2, (int)x1 * 2 + 1, (int)y1 * 2 - 1, color2);
/* 369:374 */     drawRect((int)x * 2, (int)y1 * 2 - 1, (int)x1 * 2, (int)y1 * 2, color2);
/* 370:375 */     GL11.glDisable(3042);
/* 371:376 */     GL11.glScalef(2.0F, 2.0F, 2.0F);
/* 372:    */   }
/* 373:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.utils.RenderUtils
 * JD-Core Version:    0.7.0.1
 */