/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   4:    */ import net.minecraft.client.Minecraft;
/*   5:    */ import net.minecraft.client.renderer.Tessellator;
/*   6:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*   7:    */ import net.minecraft.client.settings.GameSettings;
/*   8:    */ import org.lwjgl.input.Mouse;
/*   9:    */ import org.lwjgl.opengl.GL11;
/*  10:    */ 
/*  11:    */ public abstract class GuiScreenSelectLocation
/*  12:    */ {
/*  13:    */   private final Minecraft field_148368_a;
/*  14:    */   private int field_148363_g;
/*  15:    */   private int field_148375_h;
/*  16:    */   protected int field_148366_b;
/*  17:    */   protected int field_148367_c;
/*  18:    */   private int field_148376_i;
/*  19:    */   private int field_148373_j;
/*  20:    */   protected final int field_148364_d;
/*  21:    */   private int field_148374_k;
/*  22:    */   private int field_148371_l;
/*  23:    */   protected int field_148365_e;
/*  24:    */   protected int field_148362_f;
/*  25: 24 */   private float field_148372_m = -2.0F;
/*  26:    */   private float field_148369_n;
/*  27:    */   private float field_148370_o;
/*  28: 27 */   private int field_148381_p = -1;
/*  29:    */   private long field_148380_q;
/*  30: 29 */   private boolean field_148379_r = true;
/*  31:    */   private boolean field_148378_s;
/*  32:    */   private int field_148377_t;
/*  33:    */   private static final String __OBFID = "CL_00000785";
/*  34:    */   
/*  35:    */   public GuiScreenSelectLocation(Minecraft par1Minecraft, int par2, int par3, int par4, int par5, int par6)
/*  36:    */   {
/*  37: 36 */     this.field_148368_a = par1Minecraft;
/*  38: 37 */     this.field_148363_g = par2;
/*  39: 38 */     this.field_148375_h = par3;
/*  40: 39 */     this.field_148366_b = par4;
/*  41: 40 */     this.field_148367_c = par5;
/*  42: 41 */     this.field_148364_d = par6;
/*  43: 42 */     this.field_148373_j = 0;
/*  44: 43 */     this.field_148376_i = par2;
/*  45:    */   }
/*  46:    */   
/*  47:    */   public void func_148346_a(int p_148346_1_, int p_148346_2_, int p_148346_3_, int p_148346_4_)
/*  48:    */   {
/*  49: 48 */     this.field_148363_g = p_148346_1_;
/*  50: 49 */     this.field_148375_h = p_148346_2_;
/*  51: 50 */     this.field_148366_b = p_148346_3_;
/*  52: 51 */     this.field_148367_c = p_148346_4_;
/*  53: 52 */     this.field_148373_j = 0;
/*  54: 53 */     this.field_148376_i = p_148346_1_;
/*  55:    */   }
/*  56:    */   
/*  57:    */   protected abstract int func_148355_a();
/*  58:    */   
/*  59:    */   protected abstract void func_148352_a(int paramInt, boolean paramBoolean);
/*  60:    */   
/*  61:    */   protected abstract boolean func_148356_a(int paramInt);
/*  62:    */   
/*  63:    */   protected abstract boolean func_148349_b(int paramInt);
/*  64:    */   
/*  65:    */   protected int func_148351_b()
/*  66:    */   {
/*  67: 66 */     return func_148355_a() * this.field_148364_d + this.field_148377_t;
/*  68:    */   }
/*  69:    */   
/*  70:    */   protected abstract void func_148358_c();
/*  71:    */   
/*  72:    */   protected abstract void func_148348_a(int paramInt1, int paramInt2, int paramInt3, int paramInt4, Tessellator paramTessellator);
/*  73:    */   
/*  74:    */   protected void func_148354_a(int p_148354_1_, int p_148354_2_, Tessellator p_148354_3_) {}
/*  75:    */   
/*  76:    */   protected void func_148359_a(int p_148359_1_, int p_148359_2_) {}
/*  77:    */   
/*  78:    */   protected void func_148353_b(int p_148353_1_, int p_148353_2_) {}
/*  79:    */   
/*  80:    */   private void func_148361_h()
/*  81:    */   {
/*  82: 81 */     int var1 = func_148347_d();
/*  83: 83 */     if (var1 < 0) {
/*  84: 85 */       var1 /= 2;
/*  85:    */     }
/*  86: 88 */     if (this.field_148370_o < 0.0F) {
/*  87: 90 */       this.field_148370_o = 0.0F;
/*  88:    */     }
/*  89: 93 */     if (this.field_148370_o > var1) {
/*  90: 95 */       this.field_148370_o = var1;
/*  91:    */     }
/*  92:    */   }
/*  93:    */   
/*  94:    */   public int func_148347_d()
/*  95:    */   {
/*  96:101 */     return func_148351_b() - (this.field_148367_c - this.field_148366_b - 4);
/*  97:    */   }
/*  98:    */   
/*  99:    */   public void func_148357_a(NodusGuiButton p_148357_1_)
/* 100:    */   {
/* 101:106 */     if (p_148357_1_.enabled) {
/* 102:108 */       if (p_148357_1_.id == this.field_148374_k)
/* 103:    */       {
/* 104:110 */         this.field_148370_o -= this.field_148364_d * 2 / 3;
/* 105:111 */         this.field_148372_m = -2.0F;
/* 106:112 */         func_148361_h();
/* 107:    */       }
/* 108:114 */       else if (p_148357_1_.id == this.field_148371_l)
/* 109:    */       {
/* 110:116 */         this.field_148370_o += this.field_148364_d * 2 / 3;
/* 111:117 */         this.field_148372_m = -2.0F;
/* 112:118 */         func_148361_h();
/* 113:    */       }
/* 114:    */     }
/* 115:    */   }
/* 116:    */   
/* 117:    */   public void func_148350_a(int p_148350_1_, int p_148350_2_, float p_148350_3_)
/* 118:    */   {
/* 119:125 */     this.field_148365_e = p_148350_1_;
/* 120:126 */     this.field_148362_f = p_148350_2_;
/* 121:127 */     func_148358_c();
/* 122:128 */     int var4 = func_148355_a();
/* 123:129 */     int var5 = func_148360_g();
/* 124:130 */     int var6 = var5 + 6;
/* 125:137 */     if (Mouse.isButtonDown(0))
/* 126:    */     {
/* 127:139 */       if (this.field_148372_m == -1.0F)
/* 128:    */       {
/* 129:141 */         var7 = true;
/* 130:143 */         if ((p_148350_2_ >= this.field_148366_b) && (p_148350_2_ <= this.field_148367_c))
/* 131:    */         {
/* 132:145 */           var8 = this.field_148363_g / 2 - 110;
/* 133:146 */           var9 = this.field_148363_g / 2 + 110;
/* 134:147 */           var10 = p_148350_2_ - this.field_148366_b - this.field_148377_t + (int)this.field_148370_o - 4;
/* 135:148 */           var11 = var10 / this.field_148364_d;
/* 136:150 */           if ((p_148350_1_ >= var8) && (p_148350_1_ <= var9) && (var11 >= 0) && (var10 >= 0) && (var11 < var4))
/* 137:    */           {
/* 138:152 */             var12 = (var11 == this.field_148381_p) && (Minecraft.getSystemTime() - this.field_148380_q < 250L);
/* 139:153 */             func_148352_a(var11, var12);
/* 140:154 */             this.field_148381_p = var11;
/* 141:155 */             this.field_148380_q = Minecraft.getSystemTime();
/* 142:    */           }
/* 143:157 */           else if ((p_148350_1_ >= var8) && (p_148350_1_ <= var9) && (var10 < 0))
/* 144:    */           {
/* 145:159 */             func_148359_a(p_148350_1_ - var8, p_148350_2_ - this.field_148366_b + (int)this.field_148370_o - 4);
/* 146:160 */             var7 = false;
/* 147:    */           }
/* 148:163 */           if ((p_148350_1_ >= var5) && (p_148350_1_ <= var6))
/* 149:    */           {
/* 150:165 */             this.field_148369_n = -1.0F;
/* 151:166 */             var19 = func_148347_d();
/* 152:168 */             if (var19 < 1) {
/* 153:170 */               var19 = 1;
/* 154:    */             }
/* 155:173 */             var13 = (int)((this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b) / func_148351_b());
/* 156:175 */             if (var13 < 32) {
/* 157:177 */               var13 = 32;
/* 158:    */             }
/* 159:180 */             if (var13 > this.field_148367_c - this.field_148366_b - 8) {
/* 160:182 */               var13 = this.field_148367_c - this.field_148366_b - 8;
/* 161:    */             }
/* 162:185 */             this.field_148369_n /= (this.field_148367_c - this.field_148366_b - var13) / var19;
/* 163:    */           }
/* 164:    */           else
/* 165:    */           {
/* 166:189 */             this.field_148369_n = 1.0F;
/* 167:    */           }
/* 168:192 */           if (var7) {
/* 169:194 */             this.field_148372_m = p_148350_2_;
/* 170:    */           } else {
/* 171:198 */             this.field_148372_m = -2.0F;
/* 172:    */           }
/* 173:    */         }
/* 174:    */         else
/* 175:    */         {
/* 176:203 */           this.field_148372_m = -2.0F;
/* 177:    */         }
/* 178:    */       }
/* 179:206 */       else if (this.field_148372_m >= 0.0F)
/* 180:    */       {
/* 181:208 */         this.field_148370_o -= (p_148350_2_ - this.field_148372_m) * this.field_148369_n;
/* 182:209 */         this.field_148372_m = p_148350_2_;
/* 183:    */       }
/* 184:    */     }
/* 185:    */     else
/* 186:    */     {
/* 187:214 */       while ((!this.field_148368_a.gameSettings.touchscreen) && (Mouse.next()))
/* 188:    */       {
/* 189:    */         boolean var7;
/* 190:    */         int var8;
/* 191:    */         int var9;
/* 192:    */         int var10;
/* 193:    */         int var11;
/* 194:    */         boolean var12;
/* 195:    */         int var19;
/* 196:    */         int var13;
/* 197:216 */         int var16 = Mouse.getEventDWheel();
/* 198:218 */         if (var16 != 0)
/* 199:    */         {
/* 200:220 */           if (var16 > 0) {
/* 201:222 */             var16 = -1;
/* 202:224 */           } else if (var16 < 0) {
/* 203:226 */             var16 = 1;
/* 204:    */           }
/* 205:229 */           this.field_148370_o += var16 * this.field_148364_d / 2;
/* 206:    */         }
/* 207:    */       }
/* 208:233 */       this.field_148372_m = -1.0F;
/* 209:    */     }
/* 210:236 */     func_148361_h();
/* 211:237 */     GL11.glDisable(2896);
/* 212:238 */     GL11.glDisable(2912);
/* 213:239 */     Tessellator var18 = Tessellator.instance;
/* 214:240 */     this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
/* 215:241 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 216:242 */     float var17 = 32.0F;
/* 217:243 */     var18.startDrawingQuads();
/* 218:244 */     var18.setColorOpaque_I(2105376);
/* 219:245 */     var18.addVertexWithUV(this.field_148373_j, this.field_148367_c, 0.0D, this.field_148373_j / var17, (this.field_148367_c + (int)this.field_148370_o) / var17);
/* 220:246 */     var18.addVertexWithUV(this.field_148376_i, this.field_148367_c, 0.0D, this.field_148376_i / var17, (this.field_148367_c + (int)this.field_148370_o) / var17);
/* 221:247 */     var18.addVertexWithUV(this.field_148376_i, this.field_148366_b, 0.0D, this.field_148376_i / var17, (this.field_148366_b + (int)this.field_148370_o) / var17);
/* 222:248 */     var18.addVertexWithUV(this.field_148373_j, this.field_148366_b, 0.0D, this.field_148373_j / var17, (this.field_148366_b + (int)this.field_148370_o) / var17);
/* 223:249 */     var18.draw();
/* 224:250 */     int var9 = this.field_148363_g / 2 - 92 - 16;
/* 225:251 */     int var10 = this.field_148366_b + 4 - (int)this.field_148370_o;
/* 226:253 */     if (this.field_148378_s) {
/* 227:255 */       func_148354_a(var9, var10, var18);
/* 228:    */     }
/* 229:260 */     for (int var11 = 0; var11 < var4; var11++)
/* 230:    */     {
/* 231:262 */       int var19 = var10 + var11 * this.field_148364_d + this.field_148377_t;
/* 232:263 */       int var13 = this.field_148364_d - 4;
/* 233:265 */       if ((var19 <= this.field_148367_c) && (var19 + var13 >= this.field_148366_b))
/* 234:    */       {
/* 235:269 */         if ((this.field_148379_r) && (func_148349_b(var11)))
/* 236:    */         {
/* 237:271 */           int var14 = this.field_148363_g / 2 - 110;
/* 238:272 */           int var15 = this.field_148363_g / 2 + 110;
/* 239:273 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 240:274 */           GL11.glDisable(3553);
/* 241:275 */           var18.startDrawingQuads();
/* 242:276 */           var18.setColorOpaque_I(0);
/* 243:277 */           var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0D, 0.0D, 1.0D);
/* 244:278 */           var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0D, 1.0D, 1.0D);
/* 245:279 */           var18.addVertexWithUV(var15, var19 - 2, 0.0D, 1.0D, 0.0D);
/* 246:280 */           var18.addVertexWithUV(var14, var19 - 2, 0.0D, 0.0D, 0.0D);
/* 247:281 */           var18.draw();
/* 248:282 */           GL11.glEnable(3553);
/* 249:    */         }
/* 250:285 */         if ((this.field_148379_r) && (func_148356_a(var11)))
/* 251:    */         {
/* 252:287 */           int var14 = this.field_148363_g / 2 - 110;
/* 253:288 */           int var15 = this.field_148363_g / 2 + 110;
/* 254:289 */           GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 255:290 */           GL11.glDisable(3553);
/* 256:291 */           var18.startDrawingQuads();
/* 257:292 */           var18.setColorOpaque_I(8421504);
/* 258:293 */           var18.addVertexWithUV(var14, var19 + var13 + 2, 0.0D, 0.0D, 1.0D);
/* 259:294 */           var18.addVertexWithUV(var15, var19 + var13 + 2, 0.0D, 1.0D, 1.0D);
/* 260:295 */           var18.addVertexWithUV(var15, var19 - 2, 0.0D, 1.0D, 0.0D);
/* 261:296 */           var18.addVertexWithUV(var14, var19 - 2, 0.0D, 0.0D, 0.0D);
/* 262:297 */           var18.setColorOpaque_I(0);
/* 263:298 */           var18.addVertexWithUV(var14 + 1, var19 + var13 + 1, 0.0D, 0.0D, 1.0D);
/* 264:299 */           var18.addVertexWithUV(var15 - 1, var19 + var13 + 1, 0.0D, 1.0D, 1.0D);
/* 265:300 */           var18.addVertexWithUV(var15 - 1, var19 - 1, 0.0D, 1.0D, 0.0D);
/* 266:301 */           var18.addVertexWithUV(var14 + 1, var19 - 1, 0.0D, 0.0D, 0.0D);
/* 267:302 */           var18.draw();
/* 268:303 */           GL11.glEnable(3553);
/* 269:    */         }
/* 270:306 */         func_148348_a(var11, var9, var19, var13, var18);
/* 271:    */       }
/* 272:    */     }
/* 273:310 */     GL11.glDisable(2929);
/* 274:311 */     byte var20 = 4;
/* 275:312 */     func_148345_b(0, this.field_148366_b, 255, 255);
/* 276:313 */     func_148345_b(this.field_148367_c, this.field_148375_h, 255, 255);
/* 277:314 */     GL11.glEnable(3042);
/* 278:315 */     GL11.glBlendFunc(770, 771);
/* 279:316 */     GL11.glDisable(3008);
/* 280:317 */     GL11.glShadeModel(7425);
/* 281:318 */     GL11.glDisable(3553);
/* 282:319 */     var18.startDrawingQuads();
/* 283:320 */     var18.setColorRGBA_I(0, 0);
/* 284:321 */     var18.addVertexWithUV(this.field_148373_j, this.field_148366_b + var20, 0.0D, 0.0D, 1.0D);
/* 285:322 */     var18.addVertexWithUV(this.field_148376_i, this.field_148366_b + var20, 0.0D, 1.0D, 1.0D);
/* 286:323 */     var18.setColorRGBA_I(0, 255);
/* 287:324 */     var18.addVertexWithUV(this.field_148376_i, this.field_148366_b, 0.0D, 1.0D, 0.0D);
/* 288:325 */     var18.addVertexWithUV(this.field_148373_j, this.field_148366_b, 0.0D, 0.0D, 0.0D);
/* 289:326 */     var18.draw();
/* 290:327 */     var18.startDrawingQuads();
/* 291:328 */     var18.setColorRGBA_I(0, 255);
/* 292:329 */     var18.addVertexWithUV(this.field_148373_j, this.field_148367_c, 0.0D, 0.0D, 1.0D);
/* 293:330 */     var18.addVertexWithUV(this.field_148376_i, this.field_148367_c, 0.0D, 1.0D, 1.0D);
/* 294:331 */     var18.setColorRGBA_I(0, 0);
/* 295:332 */     var18.addVertexWithUV(this.field_148376_i, this.field_148367_c - var20, 0.0D, 1.0D, 0.0D);
/* 296:333 */     var18.addVertexWithUV(this.field_148373_j, this.field_148367_c - var20, 0.0D, 0.0D, 0.0D);
/* 297:334 */     var18.draw();
/* 298:335 */     int var19 = func_148347_d();
/* 299:337 */     if (var19 > 0)
/* 300:    */     {
/* 301:339 */       int var13 = (this.field_148367_c - this.field_148366_b) * (this.field_148367_c - this.field_148366_b) / func_148351_b();
/* 302:341 */       if (var13 < 32) {
/* 303:343 */         var13 = 32;
/* 304:    */       }
/* 305:346 */       if (var13 > this.field_148367_c - this.field_148366_b - 8) {
/* 306:348 */         var13 = this.field_148367_c - this.field_148366_b - 8;
/* 307:    */       }
/* 308:351 */       int var14 = (int)this.field_148370_o * (this.field_148367_c - this.field_148366_b - var13) / var19 + this.field_148366_b;
/* 309:353 */       if (var14 < this.field_148366_b) {
/* 310:355 */         var14 = this.field_148366_b;
/* 311:    */       }
/* 312:358 */       var18.startDrawingQuads();
/* 313:359 */       var18.setColorRGBA_I(0, 255);
/* 314:360 */       var18.addVertexWithUV(var5, this.field_148367_c, 0.0D, 0.0D, 1.0D);
/* 315:361 */       var18.addVertexWithUV(var6, this.field_148367_c, 0.0D, 1.0D, 1.0D);
/* 316:362 */       var18.addVertexWithUV(var6, this.field_148366_b, 0.0D, 1.0D, 0.0D);
/* 317:363 */       var18.addVertexWithUV(var5, this.field_148366_b, 0.0D, 0.0D, 0.0D);
/* 318:364 */       var18.draw();
/* 319:365 */       var18.startDrawingQuads();
/* 320:366 */       var18.setColorRGBA_I(8421504, 255);
/* 321:367 */       var18.addVertexWithUV(var5, var14 + var13, 0.0D, 0.0D, 1.0D);
/* 322:368 */       var18.addVertexWithUV(var6, var14 + var13, 0.0D, 1.0D, 1.0D);
/* 323:369 */       var18.addVertexWithUV(var6, var14, 0.0D, 1.0D, 0.0D);
/* 324:370 */       var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 325:371 */       var18.draw();
/* 326:372 */       var18.startDrawingQuads();
/* 327:373 */       var18.setColorRGBA_I(12632256, 255);
/* 328:374 */       var18.addVertexWithUV(var5, var14 + var13 - 1, 0.0D, 0.0D, 1.0D);
/* 329:375 */       var18.addVertexWithUV(var6 - 1, var14 + var13 - 1, 0.0D, 1.0D, 1.0D);
/* 330:376 */       var18.addVertexWithUV(var6 - 1, var14, 0.0D, 1.0D, 0.0D);
/* 331:377 */       var18.addVertexWithUV(var5, var14, 0.0D, 0.0D, 0.0D);
/* 332:378 */       var18.draw();
/* 333:    */     }
/* 334:381 */     func_148353_b(p_148350_1_, p_148350_2_);
/* 335:382 */     GL11.glEnable(3553);
/* 336:383 */     GL11.glShadeModel(7424);
/* 337:384 */     GL11.glEnable(3008);
/* 338:385 */     GL11.glDisable(3042);
/* 339:    */   }
/* 340:    */   
/* 341:    */   protected int func_148360_g()
/* 342:    */   {
/* 343:390 */     return this.field_148363_g / 2 + 124;
/* 344:    */   }
/* 345:    */   
/* 346:    */   private void func_148345_b(int p_148345_1_, int p_148345_2_, int p_148345_3_, int p_148345_4_)
/* 347:    */   {
/* 348:395 */     Tessellator var5 = Tessellator.instance;
/* 349:396 */     this.field_148368_a.getTextureManager().bindTexture(Gui.optionsBackground);
/* 350:397 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 351:398 */     float var6 = 32.0F;
/* 352:399 */     var5.startDrawingQuads();
/* 353:400 */     var5.setColorRGBA_I(4210752, p_148345_4_);
/* 354:401 */     var5.addVertexWithUV(0.0D, p_148345_2_, 0.0D, 0.0D, p_148345_2_ / var6);
/* 355:402 */     var5.addVertexWithUV(this.field_148363_g, p_148345_2_, 0.0D, this.field_148363_g / var6, p_148345_2_ / var6);
/* 356:403 */     var5.setColorRGBA_I(4210752, p_148345_3_);
/* 357:404 */     var5.addVertexWithUV(this.field_148363_g, p_148345_1_, 0.0D, this.field_148363_g / var6, p_148345_1_ / var6);
/* 358:405 */     var5.addVertexWithUV(0.0D, p_148345_1_, 0.0D, 0.0D, p_148345_1_ / var6);
/* 359:406 */     var5.draw();
/* 360:    */   }
/* 361:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenSelectLocation
 * JD-Core Version:    0.7.0.1
 */