/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*   6:    */ import net.minecraft.client.renderer.Tessellator;
/*   7:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   8:    */ import net.minecraft.client.settings.GameSettings;
/*   9:    */ import org.lwjgl.input.Mouse;
/*  10:    */ import org.lwjgl.opengl.GL11;
/*  11:    */ 
/*  12:    */ public abstract class GuiSlot
/*  13:    */ {
/*  14:    */   private final Minecraft field_148161_k;
/*  15:    */   protected int field_148155_a;
/*  16:    */   private int field_148158_l;
/*  17:    */   protected int field_148153_b;
/*  18:    */   protected int field_148154_c;
/*  19:    */   protected int field_148151_d;
/*  20:    */   protected int field_148152_e;
/*  21:    */   protected final int field_148149_f;
/*  22:    */   private int field_148159_m;
/*  23:    */   private int field_148156_n;
/*  24:    */   protected int field_148150_g;
/*  25:    */   protected int field_148162_h;
/*  26: 25 */   protected boolean field_148163_i = true;
/*  27: 26 */   private float field_148157_o = -2.0F;
/*  28:    */   private float field_148170_p;
/*  29:    */   private float field_148169_q;
/*  30: 29 */   private int field_148168_r = -1;
/*  31:    */   private long field_148167_s;
/*  32: 31 */   private boolean field_148166_t = true;
/*  33:    */   private boolean field_148165_u;
/*  34:    */   protected int field_148160_j;
/*  35: 34 */   private boolean field_148164_v = true;
/*  36:    */   private static final String __OBFID = "CL_00000679";
/*  37:    */   
/*  38:    */   public GuiSlot(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6)
/*  39:    */   {
/*  40: 39 */     this.field_148161_k = par1Minecraft;
/*  41: 40 */     this.field_148155_a = par2;
/*  42: 41 */     this.field_148158_l = par3;
/*  43: 42 */     this.field_148153_b = par4;
/*  44: 43 */     this.field_148154_c = par5;
/*  45: 44 */     this.field_148149_f = par6;
/*  46: 45 */     this.field_148152_e = 0;
/*  47: 46 */     this.field_148151_d = par2;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void func_148122_a(int p_148122_1_, int p_148122_2_, int p_148122_3_, int p_148122_4_)
/*  51:    */   {
/*  52: 51 */     this.field_148155_a = p_148122_1_;
/*  53: 52 */     this.field_148158_l = p_148122_2_;
/*  54: 53 */     this.field_148153_b = p_148122_3_;
/*  55: 54 */     this.field_148154_c = p_148122_4_;
/*  56: 55 */     this.field_148152_e = 0;
/*  57: 56 */     this.field_148151_d = p_148122_1_;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public void func_148130_a(boolean p_148130_1_)
/*  61:    */   {
/*  62: 61 */     this.field_148166_t = p_148130_1_;
/*  63:    */   }
/*  64:    */   
/*  65:    */   protected void func_148133_a(boolean p_148133_1_, int p_148133_2_)
/*  66:    */   {
/*  67: 66 */     this.field_148165_u = p_148133_1_;
/*  68: 67 */     this.field_148160_j = p_148133_2_;
/*  69: 69 */     if (!p_148133_1_) {
/*  70: 71 */       this.field_148160_j = 0;
/*  71:    */     }
/*  72:    */   }
/*  73:    */   
/*  74:    */   protected abstract int getSize();
/*  75:    */   
/*  76:    */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*  77:    */   
/*  78:    */   protected abstract boolean isSelected(int paramInt);
/*  79:    */   
/*  80:    */   protected int func_148138_e()
/*  81:    */   {
/*  82: 83 */     return getSize() * this.field_148149_f + this.field_148160_j;
/*  83:    */   }
/*  84:    */   
/*  85:    */   protected abstract void drawBackground();
/*  86:    */   
/*  87:    */   protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Tessellator paramTessellator, int paramInt5, int paramInt6);
/*  88:    */   
/*  89:    */   protected void func_148129_a(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
/*  90:    */   
/*  91:    */   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
/*  92:    */   
/*  93:    */   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
/*  94:    */   
/*  95:    */   public int func_148124_c(int p_148124_1_, int p_148124_2_)
/*  96:    */   {
/*  97: 98 */     int var3 = this.field_148152_e + this.field_148155_a / 2 - func_148139_c() / 2;
/*  98: 99 */     int var4 = this.field_148152_e + this.field_148155_a / 2 + func_148139_c() / 2;
/*  99:100 */     int var5 = p_148124_2_ - this.field_148153_b - this.field_148160_j + (int)this.field_148169_q - 4;
/* 100:101 */     int var6 = var5 / this.field_148149_f;
/* 101:102 */     return (p_148124_1_ < func_148137_d()) && (p_148124_1_ >= var3) && (p_148124_1_ <= var4) && (var6 >= 0) && (var5 >= 0) && (var6 < getSize()) ? var6 : -1;
/* 102:    */   }
/* 103:    */   
/* 104:    */   public void func_148134_d(int p_148134_1_, int p_148134_2_)
/* 105:    */   {
/* 106:107 */     this.field_148159_m = p_148134_1_;
/* 107:108 */     this.field_148156_n = p_148134_2_;
/* 108:    */   }
/* 109:    */   
/* 110:    */   private void func_148121_k()
/* 111:    */   {
/* 112:113 */     int var1 = func_148135_f();
/* 113:115 */     if (var1 < 0) {
/* 114:117 */       var1 /= 2;
/* 115:    */     }
/* 116:120 */     if ((!this.field_148163_i) && (var1 < 0)) {
/* 117:122 */       var1 = 0;
/* 118:    */     }
/* 119:125 */     if (this.field_148169_q < 0.0F) {
/* 120:127 */       this.field_148169_q = 0.0F;
/* 121:    */     }
/* 122:130 */     if (this.field_148169_q > var1) {
/* 123:132 */       this.field_148169_q = var1;
/* 124:    */     }
/* 125:    */   }
/* 126:    */   
/* 127:    */   public int func_148135_f()
/* 128:    */   {
/* 129:138 */     return func_148138_e() - (this.field_148154_c - this.field_148153_b - 4);
/* 130:    */   }
/* 131:    */   
/* 132:    */   public int func_148148_g()
/* 133:    */   {
/* 134:143 */     return (int)this.field_148169_q;
/* 135:    */   }
/* 136:    */   
/* 137:    */   public boolean func_148141_e(int p_148141_1_)
/* 138:    */   {
/* 139:148 */     return (p_148141_1_ >= this.field_148153_b) && (p_148141_1_ <= this.field_148154_c);
/* 140:    */   }
/* 141:    */   
/* 142:    */   public void func_148145_f(int p_148145_1_)
/* 143:    */   {
/* 144:153 */     this.field_148169_q += p_148145_1_;
/* 145:154 */     func_148121_k();
/* 146:155 */     this.field_148157_o = -2.0F;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void func_148147_a(NodusGuiButton p_148147_1_)
/* 150:    */   {
/* 151:160 */     if (p_148147_1_.enabled) {
/* 152:162 */       if (p_148147_1_.id == this.field_148159_m)
/* 153:    */       {
/* 154:164 */         this.field_148169_q -= this.field_148149_f * 2 / 3;
/* 155:165 */         this.field_148157_o = -2.0F;
/* 156:166 */         func_148121_k();
/* 157:    */       }
/* 158:168 */       else if (p_148147_1_.id == this.field_148156_n)
/* 159:    */       {
/* 160:170 */         this.field_148169_q += this.field_148149_f * 2 / 3;
/* 161:171 */         this.field_148157_o = -2.0F;
/* 162:172 */         func_148121_k();
/* 163:    */       }
/* 164:    */     }
/* 165:    */   }
/* 166:    */   
/* 167:    */   public void func_148128_a(int p_148128_1_, int p_148128_2_, float p_148128_3_)
/* 168:    */   {
/* 169:179 */     this.field_148150_g = p_148128_1_;
/* 170:180 */     this.field_148162_h = p_148128_2_;
/* 171:181 */     drawBackground();
/* 172:182 */     int var4 = getSize();
/* 173:183 */     int var5 = func_148137_d();
/* 174:184 */     int var6 = var5 + 6;
/* 175:190 */     if ((p_148128_1_ > this.field_148152_e) && (p_148128_1_ < this.field_148151_d) && (p_148128_2_ > this.field_148153_b) && (p_148128_2_ < this.field_148154_c)) {
/* 176:192 */       if ((Mouse.isButtonDown(0)) && (func_148125_i()))
/* 177:    */       {
/* 178:194 */         if (this.field_148157_o == -1.0F)
/* 179:    */         {
/* 180:196 */           var15 = true;
/* 181:198 */           if ((p_148128_2_ >= this.field_148153_b) && (p_148128_2_ <= this.field_148154_c))
/* 182:    */           {
/* 183:200 */             var8 = this.field_148155_a / 2 - func_148139_c() / 2;
/* 184:201 */             var9 = this.field_148155_a / 2 + func_148139_c() / 2;
/* 185:202 */             var10 = p_148128_2_ - this.field_148153_b - this.field_148160_j + (int)this.field_148169_q - 4;
/* 186:203 */             var11 = var10 / this.field_148149_f;
/* 187:205 */             if ((p_148128_1_ >= var8) && (p_148128_1_ <= var9) && (var11 >= 0) && (var10 >= 0) && (var11 < var4))
/* 188:    */             {
/* 189:207 */               var12 = (var11 == this.field_148168_r) && (Minecraft.getSystemTime() - this.field_148167_s < 250L);
/* 190:208 */               elementClicked(var11, var12, p_148128_1_, p_148128_2_);
/* 191:209 */               this.field_148168_r = var11;
/* 192:210 */               this.field_148167_s = Minecraft.getSystemTime();
/* 193:    */             }
/* 194:212 */             else if ((p_148128_1_ >= var8) && (p_148128_1_ <= var9) && (var10 < 0))
/* 195:    */             {
/* 196:214 */               func_148132_a(p_148128_1_ - var8, p_148128_2_ - this.field_148153_b + (int)this.field_148169_q - 4);
/* 197:215 */               var15 = false;
/* 198:    */             }
/* 199:218 */             if ((p_148128_1_ >= var5) && (p_148128_1_ <= var6))
/* 200:    */             {
/* 201:220 */               this.field_148170_p = -1.0F;
/* 202:221 */               var19 = func_148135_f();
/* 203:223 */               if (var19 < 1) {
/* 204:225 */                 var19 = 1;
/* 205:    */               }
/* 206:228 */               var13 = (int)((this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b) / func_148138_e());
/* 207:230 */               if (var13 < 32) {
/* 208:232 */                 var13 = 32;
/* 209:    */               }
/* 210:235 */               if (var13 > this.field_148154_c - this.field_148153_b - 8) {
/* 211:237 */                 var13 = this.field_148154_c - this.field_148153_b - 8;
/* 212:    */               }
/* 213:240 */               this.field_148170_p /= (this.field_148154_c - this.field_148153_b - var13) / var19;
/* 214:    */             }
/* 215:    */             else
/* 216:    */             {
/* 217:244 */               this.field_148170_p = 1.0F;
/* 218:    */             }
/* 219:247 */             if (var15) {
/* 220:249 */               this.field_148157_o = p_148128_2_;
/* 221:    */             } else {
/* 222:253 */               this.field_148157_o = -2.0F;
/* 223:    */             }
/* 224:    */           }
/* 225:    */           else
/* 226:    */           {
/* 227:258 */             this.field_148157_o = -2.0F;
/* 228:    */           }
/* 229:    */         }
/* 230:261 */         else if (this.field_148157_o >= 0.0F)
/* 231:    */         {
/* 232:263 */           this.field_148169_q -= (p_148128_2_ - this.field_148157_o) * this.field_148170_p;
/* 233:264 */           this.field_148157_o = p_148128_2_;
/* 234:    */         }
/* 235:    */       }
/* 236:    */       else
/* 237:    */       {
/* 238:269 */         for (; (!this.field_148161_k.gameSettings.touchscreen) && (Mouse.next()); this.field_148161_k.currentScreen.handleMouseInput())
/* 239:    */         {
/* 240:    */           boolean var15;
/* 241:    */           int var8;
/* 242:    */           int var9;
/* 243:    */           int var10;
/* 244:    */           int var11;
/* 245:    */           boolean var12;
/* 246:    */           int var19;
/* 247:    */           int var13;
/* 248:271 */           int var7 = Mouse.getEventDWheel();
/* 249:273 */           if (var7 != 0)
/* 250:    */           {
/* 251:275 */             if (var7 > 0) {
/* 252:277 */               var7 = -1;
/* 253:279 */             } else if (var7 < 0) {
/* 254:281 */               var7 = 1;
/* 255:    */             }
/* 256:284 */             this.field_148169_q += var7 * this.field_148149_f / 2;
/* 257:    */           }
/* 258:    */         }
/* 259:288 */         this.field_148157_o = -1.0F;
/* 260:    */       }
/* 261:    */     }
/* 262:292 */     func_148121_k();
/* 263:293 */     GL11.glDisable(2896);
/* 264:294 */     GL11.glDisable(2912);
/* 265:295 */     Tessellator var17 = Tessellator.instance;
/* 266:296 */     this.field_148161_k.getTextureManager().bindTexture(Gui.optionsBackground);
/* 267:297 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 268:298 */     float var16 = 32.0F;
/* 269:299 */     var17.startDrawingQuads();
/* 270:300 */     var17.setColorOpaque_I(2105376);
/* 271:301 */     var17.addVertexWithUV(this.field_148152_e, this.field_148154_c, 0.0D, this.field_148152_e / var16, (this.field_148154_c + (int)this.field_148169_q) / var16);
/* 272:302 */     var17.addVertexWithUV(this.field_148151_d, this.field_148154_c, 0.0D, this.field_148151_d / var16, (this.field_148154_c + (int)this.field_148169_q) / var16);
/* 273:303 */     var17.addVertexWithUV(this.field_148151_d, this.field_148153_b, 0.0D, this.field_148151_d / var16, (this.field_148153_b + (int)this.field_148169_q) / var16);
/* 274:304 */     var17.addVertexWithUV(this.field_148152_e, this.field_148153_b, 0.0D, this.field_148152_e / var16, (this.field_148153_b + (int)this.field_148169_q) / var16);
/* 275:305 */     var17.draw();
/* 276:306 */     int var9 = this.field_148152_e + this.field_148155_a / 2 - func_148139_c() / 2 + 2;
/* 277:307 */     int var10 = this.field_148153_b + 4 - (int)this.field_148169_q;
/* 278:309 */     if (this.field_148165_u) {
/* 279:311 */       func_148129_a(var9, var10, var17);
/* 280:    */     }
/* 281:314 */     func_148120_b(var9, var10, p_148128_1_, p_148128_2_);
/* 282:315 */     GL11.glDisable(2929);
/* 283:316 */     byte var18 = 4;
/* 284:317 */     func_148136_c(0, this.field_148153_b, 255, 255);
/* 285:318 */     func_148136_c(this.field_148154_c, this.field_148158_l, 255, 255);
/* 286:319 */     GL11.glEnable(3042);
/* 287:320 */     OpenGlHelper.glBlendFunc(770, 771, 0, 1);
/* 288:321 */     GL11.glDisable(3008);
/* 289:322 */     GL11.glShadeModel(7425);
/* 290:323 */     GL11.glDisable(3553);
/* 291:324 */     var17.startDrawingQuads();
/* 292:325 */     var17.setColorRGBA_I(0, 0);
/* 293:326 */     var17.addVertexWithUV(this.field_148152_e, this.field_148153_b + var18, 0.0D, 0.0D, 1.0D);
/* 294:327 */     var17.addVertexWithUV(this.field_148151_d, this.field_148153_b + var18, 0.0D, 1.0D, 1.0D);
/* 295:328 */     var17.setColorRGBA_I(0, 255);
/* 296:329 */     var17.addVertexWithUV(this.field_148151_d, this.field_148153_b, 0.0D, 1.0D, 0.0D);
/* 297:330 */     var17.addVertexWithUV(this.field_148152_e, this.field_148153_b, 0.0D, 0.0D, 0.0D);
/* 298:331 */     var17.draw();
/* 299:332 */     var17.startDrawingQuads();
/* 300:333 */     var17.setColorRGBA_I(0, 255);
/* 301:334 */     var17.addVertexWithUV(this.field_148152_e, this.field_148154_c, 0.0D, 0.0D, 1.0D);
/* 302:335 */     var17.addVertexWithUV(this.field_148151_d, this.field_148154_c, 0.0D, 1.0D, 1.0D);
/* 303:336 */     var17.setColorRGBA_I(0, 0);
/* 304:337 */     var17.addVertexWithUV(this.field_148151_d, this.field_148154_c - var18, 0.0D, 1.0D, 0.0D);
/* 305:338 */     var17.addVertexWithUV(this.field_148152_e, this.field_148154_c - var18, 0.0D, 0.0D, 0.0D);
/* 306:339 */     var17.draw();
/* 307:340 */     int var19 = func_148135_f();
/* 308:342 */     if (var19 > 0)
/* 309:    */     {
/* 310:344 */       int var13 = (this.field_148154_c - this.field_148153_b) * (this.field_148154_c - this.field_148153_b) / func_148138_e();
/* 311:346 */       if (var13 < 32) {
/* 312:348 */         var13 = 32;
/* 313:    */       }
/* 314:351 */       if (var13 > this.field_148154_c - this.field_148153_b - 8) {
/* 315:353 */         var13 = this.field_148154_c - this.field_148153_b - 8;
/* 316:    */       }
/* 317:356 */       int var14 = (int)this.field_148169_q * (this.field_148154_c - this.field_148153_b - var13) / var19 + this.field_148153_b;
/* 318:358 */       if (var14 < this.field_148153_b) {
/* 319:360 */         var14 = this.field_148153_b;
/* 320:    */       }
/* 321:363 */       var17.startDrawingQuads();
/* 322:364 */       var17.setColorRGBA_I(0, 255);
/* 323:365 */       var17.addVertexWithUV(var5, this.field_148154_c, 0.0D, 0.0D, 1.0D);
/* 324:366 */       var17.addVertexWithUV(var6, this.field_148154_c, 0.0D, 1.0D, 1.0D);
/* 325:367 */       var17.addVertexWithUV(var6, this.field_148153_b, 0.0D, 1.0D, 0.0D);
/* 326:368 */       var17.addVertexWithUV(var5, this.field_148153_b, 0.0D, 0.0D, 0.0D);
/* 327:369 */       var17.draw();
/* 328:370 */       var17.startDrawingQuads();
/* 329:371 */       var17.setColorRGBA_I(8421504, 255);
/* 330:372 */       var17.addVertexWithUV(var5, var14 + var13, 0.0D, 0.0D, 1.0D);
/* 331:373 */       var17.addVertexWithUV(var6, var14 + var13, 0.0D, 1.0D, 1.0D);
/* 332:374 */       var17.addVertexWithUV(var6, var14, 0.0D, 1.0D, 0.0D);
/* 333:375 */       var17.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 334:376 */       var17.draw();
/* 335:377 */       var17.startDrawingQuads();
/* 336:378 */       var17.setColorRGBA_I(12632256, 255);
/* 337:379 */       var17.addVertexWithUV(var5, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
/* 338:380 */       var17.addVertexWithUV(var6 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
/* 339:381 */       var17.addVertexWithUV(var6 - 1, var14, 0.0D, 1.0D, 0.0D);
/* 340:382 */       var17.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 341:383 */       var17.draw();
/* 342:    */     }
/* 343:386 */     func_148142_b(p_148128_1_, p_148128_2_);
/* 344:387 */     GL11.glEnable(3553);
/* 345:388 */     GL11.glShadeModel(7424);
/* 346:389 */     GL11.glEnable(3008);
/* 347:390 */     GL11.glDisable(3042);
/* 348:    */   }
/* 349:    */   
/* 350:    */   public void func_148143_b(boolean p_148143_1_)
/* 351:    */   {
/* 352:395 */     this.field_148164_v = p_148143_1_;
/* 353:    */   }
/* 354:    */   
/* 355:    */   public boolean func_148125_i()
/* 356:    */   {
/* 357:400 */     return this.field_148164_v;
/* 358:    */   }
/* 359:    */   
/* 360:    */   public int func_148139_c()
/* 361:    */   {
/* 362:405 */     return 220;
/* 363:    */   }
/* 364:    */   
/* 365:    */   protected void func_148120_b(int p_148120_1_, int p_148120_2_, int p_148120_3_, int p_148120_4_)
/* 366:    */   {
/* 367:410 */     int var5 = getSize();
/* 368:411 */     Tessellator var6 = Tessellator.instance;
/* 369:413 */     for (int var7 = 0; var7 < var5; var7++)
/* 370:    */     {
/* 371:415 */       int var8 = p_148120_2_ + var7 * this.field_148149_f + this.field_148160_j;
/* 372:416 */       int var9 = this.field_148149_f - 4;
/* 373:418 */       if ((var8 <= this.field_148154_c) && (var8 + var9 >= this.field_148153_b))
/* 374:    */       {
/* 375:420 */         if ((this.field_148166_t) && (isSelected(var7)))
/* 376:    */         {
/* 377:422 */           int var10 = this.field_148152_e + (this.field_148155_a / 2 - func_148139_c() / 2);
/* 378:423 */           int var11 = this.field_148152_e + this.field_148155_a / 2 + func_148139_c() / 2;
/* 379:424 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 380:425 */           GL11.glDisable(3553);
/* 381:426 */           var6.startDrawingQuads();
/* 382:427 */           var6.setColorOpaque_I(8421504);
/* 383:428 */           var6.addVertexWithUV(var10, var8 + var9 + 2, 0.0D, 0.0D, 1.0D);
/* 384:429 */           var6.addVertexWithUV(var11, var8 + var9 + 2, 0.0D, 1.0D, 1.0D);
/* 385:430 */           var6.addVertexWithUV(var11, var8 - 2, 0.0D, 1.0D, 0.0D);
/* 386:431 */           var6.addVertexWithUV(var10, var8 - 2, 0.0D, 0.0D, 0.0D);
/* 387:432 */           var6.setColorOpaque_I(0);
/* 388:433 */           var6.addVertexWithUV(var10 + 1, var8 + var9 + 1, 0.0D, 0.0D, 1.0D);
/* 389:434 */           var6.addVertexWithUV(var11 - 1, var8 + var9 + 1, 0.0D, 1.0D, 1.0D);
/* 390:435 */           var6.addVertexWithUV(var11 - 1, var8 - 1, 0.0D, 1.0D, 0.0D);
/* 391:436 */           var6.addVertexWithUV(var10 + 1, var8 - 1, 0.0D, 0.0D, 0.0D);
/* 392:437 */           var6.draw();
/* 393:438 */           GL11.glEnable(3553);
/* 394:    */         }
/* 395:441 */         drawSlot(var7, p_148120_1_, var8, var9, var6, p_148120_3_, p_148120_4_);
/* 396:    */       }
/* 397:    */     }
/* 398:    */   }
/* 399:    */   
/* 400:    */   protected int func_148137_d()
/* 401:    */   {
/* 402:448 */     return this.field_148155_a / 2 + 124;
/* 403:    */   }
/* 404:    */   
/* 405:    */   private void func_148136_c(int p_148136_1_, int p_148136_2_, int p_148136_3_, int p_148136_4_)
/* 406:    */   {
/* 407:453 */     Tessellator var5 = Tessellator.instance;
/* 408:454 */     this.field_148161_k.getTextureManager().bindTexture(Gui.optionsBackground);
/* 409:455 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 410:456 */     float var6 = 32.0F;
/* 411:457 */     var5.startDrawingQuads();
/* 412:458 */     var5.setColorRGBA_I(4210752, p_148136_4_);
/* 413:459 */     var5.addVertexWithUV(this.field_148152_e, p_148136_2_, 0.0D, 0.0D, p_148136_2_ / var6);
/* 414:460 */     var5.addVertexWithUV(this.field_148152_e + this.field_148155_a, p_148136_2_, 0.0D, this.field_148155_a / var6, p_148136_2_ / var6);
/* 415:461 */     var5.setColorRGBA_I(4210752, p_148136_3_);
/* 416:462 */     var5.addVertexWithUV(this.field_148152_e + this.field_148155_a, p_148136_1_, 0.0D, this.field_148155_a / var6, p_148136_1_ / var6);
/* 417:463 */     var5.addVertexWithUV(this.field_148152_e, p_148136_1_, 0.0D, 0.0D, p_148136_1_ / var6);
/* 418:464 */     var5.draw();
/* 419:    */   }
/* 420:    */   
/* 421:    */   public void func_148140_g(int p_148140_1_)
/* 422:    */   {
/* 423:469 */     this.field_148152_e = p_148140_1_;
/* 424:470 */     this.field_148151_d = (p_148140_1_ + this.field_148155_a);
/* 425:    */   }
/* 426:    */   
/* 427:    */   public int func_148146_j()
/* 428:    */   {
/* 429:475 */     return this.field_148149_f;
/* 430:    */   }
/* 431:    */   
/* 432:    */   public void registerScrollButtons(int par1, int par2)
/* 433:    */   {
/* 434:480 */     func_148134_d(par1, par2);
/* 435:    */   }
/* 436:    */   
/* 437:    */   public void drawScreen(int i, int j, float f)
/* 438:    */   {
/* 439:484 */     func_148128_a(i, j, f);
/* 440:    */   }
/* 441:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiSlot
 * JD-Core Version:    0.7.0.1
 */