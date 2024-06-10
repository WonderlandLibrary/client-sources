/*   1:    */ package me.connorm.Nodus.ui;
/*   2:    */ 
/*   3:    */ import me.connorm.Nodus.font.UnicodeFontRenderer;
/*   4:    */ import net.minecraft.client.gui.Gui;
/*   5:    */ import net.minecraft.client.gui.GuiScreen;
/*   6:    */ import net.minecraft.client.renderer.Tessellator;
/*   7:    */ import net.minecraft.util.ChatAllowedCharacters;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public class GuiCustomFontField
/*  11:    */   extends Gui
/*  12:    */ {
/*  13: 13 */   private String text = "";
/*  14:    */   private final UnicodeFontRenderer field_146211_a;
/*  15:    */   private final int field_146209_f;
/*  16:    */   private final int field_146210_g;
/*  17:    */   private final int field_146218_h;
/*  18:    */   private final int field_146219_i;
/*  19:    */   public int xPos;
/*  20:    */   public int yPos;
/*  21:    */   public int width;
/*  22:    */   public int height;
/*  23: 23 */   private String field_146216_j = "";
/*  24: 24 */   private int field_146217_k = 32;
/*  25:    */   private int field_146214_l;
/*  26: 26 */   private boolean field_146215_m = true;
/*  27: 27 */   private boolean field_146212_n = true;
/*  28:    */   private boolean field_146213_o;
/*  29: 29 */   private boolean field_146226_p = true;
/*  30:    */   private int field_146225_q;
/*  31:    */   private int field_146224_r;
/*  32:    */   private int field_146223_s;
/*  33: 33 */   private int field_146222_t = 14737632;
/*  34: 34 */   private int field_146221_u = 7368816;
/*  35: 35 */   private boolean field_146220_v = true;
/*  36:    */   private static final String __OBFID = "CL_00000670";
/*  37:    */   
/*  38:    */   public GuiCustomFontField(UnicodeFontRenderer par1FontRenderer, int par2, int par3, int par4, int par5)
/*  39:    */   {
/*  40: 40 */     this.field_146211_a = par1FontRenderer;
/*  41: 41 */     this.field_146209_f = par2;
/*  42: 42 */     this.field_146210_g = par3;
/*  43: 43 */     this.field_146218_h = par4;
/*  44: 44 */     this.field_146219_i = par5;
/*  45: 45 */     this.xPos = par2;
/*  46: 46 */     this.yPos = par3;
/*  47: 47 */     this.width = par4;
/*  48: 48 */     this.height = par5;
/*  49:    */   }
/*  50:    */   
/*  51:    */   public void updateCursorCounter()
/*  52:    */   {
/*  53: 53 */     this.field_146214_l += 1;
/*  54:    */   }
/*  55:    */   
/*  56:    */   public void setText(String p_146180_1_)
/*  57:    */   {
/*  58: 58 */     if (p_146180_1_.length() > this.field_146217_k) {
/*  59: 60 */       this.field_146216_j = p_146180_1_.substring(0, this.field_146217_k);
/*  60:    */     } else {
/*  61: 64 */       this.field_146216_j = p_146180_1_;
/*  62:    */     }
/*  63: 67 */     func_146202_e();
/*  64:    */   }
/*  65:    */   
/*  66:    */   public String getText()
/*  67:    */   {
/*  68: 72 */     return this.field_146216_j;
/*  69:    */   }
/*  70:    */   
/*  71:    */   public String func_146207_c()
/*  72:    */   {
/*  73: 77 */     int var1 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  74: 78 */     int var2 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  75: 79 */     return this.field_146216_j.substring(var1, var2);
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void func_146191_b(String p_146191_1_)
/*  79:    */   {
/*  80: 84 */     String var2 = "";
/*  81: 85 */     String var3 = ChatAllowedCharacters.filerAllowedCharacters(p_146191_1_);
/*  82: 86 */     int var4 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  83: 87 */     int var5 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  84: 88 */     int var6 = this.field_146217_k - this.field_146216_j.length() - (var4 - this.field_146223_s);
/*  85: 89 */     boolean var7 = false;
/*  86: 91 */     if (this.field_146216_j.length() > 0) {
/*  87: 93 */       var2 = var2 + this.field_146216_j.substring(0, var4);
/*  88:    */     }
/*  89:    */     int var8;
/*  90:    */     int var8;
/*  91: 96 */     if (var6 < var3.length())
/*  92:    */     {
/*  93: 98 */       var2 = var2 + var3.substring(0, var6);
/*  94: 99 */       var8 = var6;
/*  95:    */     }
/*  96:    */     else
/*  97:    */     {
/*  98:103 */       var2 = var2 + var3;
/*  99:104 */       var8 = var3.length();
/* 100:    */     }
/* 101:107 */     if ((this.field_146216_j.length() > 0) && (var5 < this.field_146216_j.length())) {
/* 102:109 */       var2 = var2 + this.field_146216_j.substring(var5);
/* 103:    */     }
/* 104:112 */     this.field_146216_j = var2;
/* 105:113 */     func_146182_d(var4 - this.field_146223_s + var8);
/* 106:    */   }
/* 107:    */   
/* 108:    */   public void func_146177_a(int p_146177_1_)
/* 109:    */   {
/* 110:118 */     if (this.field_146216_j.length() != 0) {
/* 111:120 */       if (this.field_146223_s != this.field_146224_r) {
/* 112:122 */         func_146191_b("");
/* 113:    */       } else {
/* 114:126 */         func_146175_b(func_146187_c(p_146177_1_) - this.field_146224_r);
/* 115:    */       }
/* 116:    */     }
/* 117:    */   }
/* 118:    */   
/* 119:    */   public void func_146175_b(int p_146175_1_)
/* 120:    */   {
/* 121:133 */     if (this.field_146216_j.length() != 0) {
/* 122:135 */       if (this.field_146223_s != this.field_146224_r)
/* 123:    */       {
/* 124:137 */         func_146191_b("");
/* 125:    */       }
/* 126:    */       else
/* 127:    */       {
/* 128:141 */         boolean var2 = p_146175_1_ < 0;
/* 129:142 */         int var3 = var2 ? this.field_146224_r + p_146175_1_ : this.field_146224_r;
/* 130:143 */         int var4 = var2 ? this.field_146224_r : this.field_146224_r + p_146175_1_;
/* 131:144 */         String var5 = "";
/* 132:146 */         if (var3 >= 0) {
/* 133:148 */           var5 = this.field_146216_j.substring(0, var3);
/* 134:    */         }
/* 135:151 */         if (var4 < this.field_146216_j.length()) {
/* 136:153 */           var5 = var5 + this.field_146216_j.substring(var4);
/* 137:    */         }
/* 138:156 */         this.field_146216_j = var5;
/* 139:158 */         if (var2) {
/* 140:160 */           func_146182_d(p_146175_1_);
/* 141:    */         }
/* 142:    */       }
/* 143:    */     }
/* 144:    */   }
/* 145:    */   
/* 146:    */   public int func_146187_c(int p_146187_1_)
/* 147:    */   {
/* 148:168 */     return func_146183_a(p_146187_1_, func_146198_h());
/* 149:    */   }
/* 150:    */   
/* 151:    */   public int func_146183_a(int p_146183_1_, int p_146183_2_)
/* 152:    */   {
/* 153:173 */     return func_146197_a(p_146183_1_, func_146198_h(), true);
/* 154:    */   }
/* 155:    */   
/* 156:    */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
/* 157:    */   {
/* 158:178 */     int var4 = p_146197_2_;
/* 159:179 */     boolean var5 = p_146197_1_ < 0;
/* 160:180 */     int var6 = Math.abs(p_146197_1_);
/* 161:182 */     for (int var7 = 0; var7 < var6; var7++) {
/* 162:184 */       if (var5)
/* 163:    */       {
/* 164:    */         for (;;)
/* 165:    */         {
/* 166:188 */           var4--;
/* 167:190 */           if ((!p_146197_3_) || (var4 <= 0)) {
/* 168:    */             break;
/* 169:    */           }
/* 170:190 */           if (this.field_146216_j.charAt(var4 - 1) != ' ') {}
/* 171:    */         }
/* 172:    */         do
/* 173:    */         {
/* 174:197 */           var4--;
/* 175:199 */           if (var4 <= 0) {
/* 176:    */             break;
/* 177:    */           }
/* 178:200 */         } while (this.field_146216_j.charAt(var4 - 1) != ' ');
/* 179:    */       }
/* 180:    */       else
/* 181:    */       {
/* 182:204 */         int var8 = this.field_146216_j.length();
/* 183:205 */         var4 = this.field_146216_j.indexOf(' ', var4);
/* 184:207 */         if (var4 == -1) {
/* 185:209 */           var4 = var8;
/* 186:    */         } else {
/* 187:    */           do
/* 188:    */           {
/* 189:215 */             var4++;
/* 190:217 */           } while ((p_146197_3_) && (var4 < var8) && (this.field_146216_j.charAt(var4) == ' '));
/* 191:    */         }
/* 192:    */       }
/* 193:    */     }
/* 194:224 */     return var4;
/* 195:    */   }
/* 196:    */   
/* 197:    */   public void func_146182_d(int p_146182_1_)
/* 198:    */   {
/* 199:229 */     func_146190_e(this.field_146223_s + p_146182_1_);
/* 200:    */   }
/* 201:    */   
/* 202:    */   public void func_146190_e(int p_146190_1_)
/* 203:    */   {
/* 204:234 */     this.field_146224_r = p_146190_1_;
/* 205:235 */     int var2 = this.field_146216_j.length();
/* 206:237 */     if (this.field_146224_r < 0) {
/* 207:239 */       this.field_146224_r = 0;
/* 208:    */     }
/* 209:242 */     if (this.field_146224_r > var2) {
/* 210:244 */       this.field_146224_r = var2;
/* 211:    */     }
/* 212:247 */     func_146199_i(this.field_146224_r);
/* 213:    */   }
/* 214:    */   
/* 215:    */   public void func_146196_d()
/* 216:    */   {
/* 217:252 */     func_146190_e(0);
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void func_146202_e()
/* 221:    */   {
/* 222:257 */     func_146190_e(this.field_146216_j.length());
/* 223:    */   }
/* 224:    */   
/* 225:    */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
/* 226:    */   {
/* 227:262 */     if (!this.field_146213_o) {
/* 228:264 */       return false;
/* 229:    */     }
/* 230:267 */     switch (p_146201_1_)
/* 231:    */     {
/* 232:    */     case '\001': 
/* 233:270 */       func_146202_e();
/* 234:271 */       func_146199_i(0);
/* 235:272 */       return true;
/* 236:    */     case '\003': 
/* 237:274 */       GuiScreen.setClipboardString(func_146207_c());
/* 238:275 */       return true;
/* 239:    */     case '\026': 
/* 240:277 */       if (this.field_146226_p) {
/* 241:279 */         func_146191_b(GuiScreen.getClipboardString());
/* 242:    */       }
/* 243:282 */       return true;
/* 244:    */     case '\030': 
/* 245:284 */       GuiScreen.setClipboardString(func_146207_c());
/* 246:286 */       if (this.field_146226_p) {
/* 247:288 */         func_146191_b("");
/* 248:    */       }
/* 249:291 */       return true;
/* 250:    */     }
/* 251:294 */     switch (p_146201_2_)
/* 252:    */     {
/* 253:    */     case 14: 
/* 254:297 */       if (GuiScreen.isCtrlKeyDown())
/* 255:    */       {
/* 256:299 */         if (this.field_146226_p) {
/* 257:301 */           func_146177_a(-1);
/* 258:    */         }
/* 259:    */       }
/* 260:304 */       else if (this.field_146226_p) {
/* 261:306 */         func_146175_b(-1);
/* 262:    */       }
/* 263:309 */       return true;
/* 264:    */     case 199: 
/* 265:311 */       if (GuiScreen.isShiftKeyDown()) {
/* 266:313 */         func_146199_i(0);
/* 267:    */       } else {
/* 268:317 */         func_146196_d();
/* 269:    */       }
/* 270:320 */       return true;
/* 271:    */     case 203: 
/* 272:322 */       if (GuiScreen.isShiftKeyDown())
/* 273:    */       {
/* 274:324 */         if (GuiScreen.isCtrlKeyDown()) {
/* 275:326 */           func_146199_i(func_146183_a(-1, func_146186_n()));
/* 276:    */         } else {
/* 277:330 */           func_146199_i(func_146186_n() - 1);
/* 278:    */         }
/* 279:    */       }
/* 280:333 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 281:335 */         func_146190_e(func_146187_c(-1));
/* 282:    */       } else {
/* 283:339 */         func_146182_d(-1);
/* 284:    */       }
/* 285:342 */       return true;
/* 286:    */     case 205: 
/* 287:344 */       if (GuiScreen.isShiftKeyDown())
/* 288:    */       {
/* 289:346 */         if (GuiScreen.isCtrlKeyDown()) {
/* 290:348 */           func_146199_i(func_146183_a(1, func_146186_n()));
/* 291:    */         } else {
/* 292:352 */           func_146199_i(func_146186_n() + 1);
/* 293:    */         }
/* 294:    */       }
/* 295:355 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 296:357 */         func_146190_e(func_146187_c(1));
/* 297:    */       } else {
/* 298:361 */         func_146182_d(1);
/* 299:    */       }
/* 300:364 */       return true;
/* 301:    */     case 207: 
/* 302:366 */       if (GuiScreen.isShiftKeyDown()) {
/* 303:368 */         func_146199_i(this.field_146216_j.length());
/* 304:    */       } else {
/* 305:372 */         func_146202_e();
/* 306:    */       }
/* 307:375 */       return true;
/* 308:    */     case 211: 
/* 309:377 */       if (GuiScreen.isCtrlKeyDown())
/* 310:    */       {
/* 311:379 */         if (this.field_146226_p) {
/* 312:381 */           func_146177_a(1);
/* 313:    */         }
/* 314:    */       }
/* 315:384 */       else if (this.field_146226_p) {
/* 316:386 */         func_146175_b(1);
/* 317:    */       }
/* 318:389 */       return true;
/* 319:    */     }
/* 320:392 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
/* 321:    */     {
/* 322:394 */       if (this.field_146226_p) {
/* 323:396 */         func_146191_b(Character.toString(p_146201_1_));
/* 324:    */       }
/* 325:399 */       return true;
/* 326:    */     }
/* 327:402 */     return false;
/* 328:    */   }
/* 329:    */   
/* 330:    */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_)
/* 331:    */   {
/* 332:407 */     boolean var4 = (p_146192_1_ >= this.field_146209_f) && (p_146192_1_ < this.field_146209_f + this.field_146218_h) && (p_146192_2_ >= this.field_146210_g) && (p_146192_2_ < this.field_146210_g + this.field_146219_i);
/* 333:409 */     if (this.field_146212_n) {
/* 334:411 */       setFocused(var4);
/* 335:    */     }
/* 336:414 */     if ((this.field_146213_o) && (p_146192_3_ == 0))
/* 337:    */     {
/* 338:416 */       int var5 = p_146192_1_ - this.field_146209_f;
/* 339:418 */       if (this.field_146215_m) {
/* 340:420 */         var5 -= 4;
/* 341:    */       }
/* 342:423 */       String var6 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 343:424 */       func_146190_e(this.field_146211_a.trimStringToWidth(var6, var5).length() + this.field_146225_q);
/* 344:    */     }
/* 345:    */   }
/* 346:    */   
/* 347:    */   public void drawTextBox()
/* 348:    */   {
/* 349:430 */     if (func_146176_q())
/* 350:    */     {
/* 351:432 */       if (func_146181_i())
/* 352:    */       {
/* 353:434 */         drawRect(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
/* 354:435 */         drawRect(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
/* 355:    */       }
/* 356:438 */       int var1 = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
/* 357:439 */       int var2 = this.field_146224_r - this.field_146225_q;
/* 358:440 */       int var3 = this.field_146223_s - this.field_146225_q;
/* 359:441 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 360:442 */       boolean var5 = (var2 >= 0) && (var2 <= var4.length());
/* 361:443 */       boolean var6 = (this.field_146213_o) && (this.field_146214_l / 6 % 2 == 0) && (var5);
/* 362:444 */       int var7 = this.field_146215_m ? this.field_146209_f + 4 : this.field_146209_f;
/* 363:445 */       int var8 = this.field_146215_m ? this.field_146210_g + (this.field_146219_i - 8) / 2 : this.field_146210_g;
/* 364:446 */       int var9 = var7;
/* 365:448 */       if (var3 > var4.length()) {
/* 366:450 */         var3 = var4.length();
/* 367:    */       }
/* 368:453 */       if (var4.length() > 0)
/* 369:    */       {
/* 370:455 */         String var10 = var5 ? var4.substring(0, var2) : var4;
/* 371:456 */         var9 = this.field_146211_a.drawStringWithShadow(var10, var7, var8 - 2, var1);
/* 372:457 */         this.text = var10;
/* 373:    */       }
/* 374:460 */       boolean var13 = (this.field_146224_r < this.field_146216_j.length()) || (this.field_146216_j.length() >= func_146208_g());
/* 375:461 */       int var11 = var9;
/* 376:463 */       if (!var5)
/* 377:    */       {
/* 378:465 */         var11 = var2 > 0 ? var7 + this.field_146218_h : var7;
/* 379:    */       }
/* 380:467 */       else if (var13)
/* 381:    */       {
/* 382:469 */         var11 = var9 - 1;
/* 383:470 */         var9--;
/* 384:    */       }
/* 385:473 */       if ((var4.length() > 0) && (var5) && (var2 < var4.length())) {
/* 386:475 */         this.field_146211_a.drawStringWithShadow(var4.substring(var2), var9, var8 - 2, var1);
/* 387:    */       }
/* 388:478 */       if (var6) {
/* 389:480 */         if (var13) {
/* 390:482 */           Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.field_146211_a.getStringHeight(this.text), -3092272);
/* 391:    */         }
/* 392:    */       }
/* 393:487 */       if (var3 != var2)
/* 394:    */       {
/* 395:489 */         int var12 = var7 + this.field_146211_a.getStringWidth(var4.substring(0, var3));
/* 396:490 */         func_146188_c(var11, var8 - 1, var12 - 1, var8 + 1 + this.field_146211_a.getStringHeight(this.text));
/* 397:    */       }
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   private void func_146188_c(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
/* 402:    */   {
/* 403:497 */     if (p_146188_1_ < p_146188_3_)
/* 404:    */     {
/* 405:499 */       int var5 = p_146188_1_;
/* 406:500 */       p_146188_1_ = p_146188_3_;
/* 407:501 */       p_146188_3_ = var5;
/* 408:    */     }
/* 409:504 */     if (p_146188_2_ < p_146188_4_)
/* 410:    */     {
/* 411:506 */       int var5 = p_146188_2_;
/* 412:507 */       p_146188_2_ = p_146188_4_;
/* 413:508 */       p_146188_4_ = var5;
/* 414:    */     }
/* 415:511 */     if (p_146188_3_ > this.field_146209_f + this.field_146218_h) {
/* 416:513 */       p_146188_3_ = this.field_146209_f + this.field_146218_h;
/* 417:    */     }
/* 418:516 */     if (p_146188_1_ > this.field_146209_f + this.field_146218_h) {
/* 419:518 */       p_146188_1_ = this.field_146209_f + this.field_146218_h;
/* 420:    */     }
/* 421:521 */     Tessellator var6 = Tessellator.instance;
/* 422:522 */     GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 423:523 */     GL11.glDisable(3553);
/* 424:524 */     GL11.glEnable(3058);
/* 425:525 */     GL11.glLogicOp(5387);
/* 426:526 */     var6.startDrawingQuads();
/* 427:527 */     var6.addVertex(p_146188_1_, p_146188_4_, 0.0D);
/* 428:528 */     var6.addVertex(p_146188_3_, p_146188_4_, 0.0D);
/* 429:529 */     var6.addVertex(p_146188_3_, p_146188_2_, 0.0D);
/* 430:530 */     var6.addVertex(p_146188_1_, p_146188_2_, 0.0D);
/* 431:531 */     var6.draw();
/* 432:532 */     GL11.glDisable(3058);
/* 433:533 */     GL11.glEnable(3553);
/* 434:    */   }
/* 435:    */   
/* 436:    */   public void func_146203_f(int p_146203_1_)
/* 437:    */   {
/* 438:538 */     this.field_146217_k = p_146203_1_;
/* 439:540 */     if (this.field_146216_j.length() > p_146203_1_) {
/* 440:542 */       this.field_146216_j = this.field_146216_j.substring(0, p_146203_1_);
/* 441:    */     }
/* 442:    */   }
/* 443:    */   
/* 444:    */   public int func_146208_g()
/* 445:    */   {
/* 446:548 */     return this.field_146217_k;
/* 447:    */   }
/* 448:    */   
/* 449:    */   public int func_146198_h()
/* 450:    */   {
/* 451:553 */     return this.field_146224_r;
/* 452:    */   }
/* 453:    */   
/* 454:    */   public boolean func_146181_i()
/* 455:    */   {
/* 456:558 */     return this.field_146215_m;
/* 457:    */   }
/* 458:    */   
/* 459:    */   public void func_146185_a(boolean p_146185_1_)
/* 460:    */   {
/* 461:563 */     this.field_146215_m = p_146185_1_;
/* 462:    */   }
/* 463:    */   
/* 464:    */   public void func_146193_g(int p_146193_1_)
/* 465:    */   {
/* 466:568 */     this.field_146222_t = p_146193_1_;
/* 467:    */   }
/* 468:    */   
/* 469:    */   public void func_146204_h(int p_146204_1_)
/* 470:    */   {
/* 471:573 */     this.field_146221_u = p_146204_1_;
/* 472:    */   }
/* 473:    */   
/* 474:    */   public void setFocused(boolean p_146195_1_)
/* 475:    */   {
/* 476:578 */     if ((p_146195_1_) && (!this.field_146213_o)) {
/* 477:580 */       this.field_146214_l = 0;
/* 478:    */     }
/* 479:583 */     this.field_146213_o = p_146195_1_;
/* 480:    */   }
/* 481:    */   
/* 482:    */   public boolean isFocused()
/* 483:    */   {
/* 484:588 */     return this.field_146213_o;
/* 485:    */   }
/* 486:    */   
/* 487:    */   public void func_146184_c(boolean p_146184_1_)
/* 488:    */   {
/* 489:593 */     this.field_146226_p = p_146184_1_;
/* 490:    */   }
/* 491:    */   
/* 492:    */   public int func_146186_n()
/* 493:    */   {
/* 494:598 */     return this.field_146223_s;
/* 495:    */   }
/* 496:    */   
/* 497:    */   public int func_146200_o()
/* 498:    */   {
/* 499:603 */     return func_146181_i() ? this.field_146218_h - 8 : this.field_146218_h;
/* 500:    */   }
/* 501:    */   
/* 502:    */   public void func_146199_i(int p_146199_1_)
/* 503:    */   {
/* 504:608 */     int var2 = this.field_146216_j.length();
/* 505:610 */     if (p_146199_1_ > var2) {
/* 506:612 */       p_146199_1_ = var2;
/* 507:    */     }
/* 508:615 */     if (p_146199_1_ < 0) {
/* 509:617 */       p_146199_1_ = 0;
/* 510:    */     }
/* 511:620 */     this.field_146223_s = p_146199_1_;
/* 512:622 */     if (this.field_146211_a != null)
/* 513:    */     {
/* 514:624 */       if (this.field_146225_q > var2) {
/* 515:626 */         this.field_146225_q = var2;
/* 516:    */       }
/* 517:629 */       int var3 = func_146200_o();
/* 518:630 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), var3);
/* 519:631 */       int var5 = var4.length() + this.field_146225_q;
/* 520:633 */       if (p_146199_1_ == this.field_146225_q) {
/* 521:635 */         this.field_146225_q -= this.field_146211_a.trimStringToWidth(this.field_146216_j, var3).length();
/* 522:    */       }
/* 523:638 */       if (p_146199_1_ > var5) {
/* 524:640 */         this.field_146225_q += p_146199_1_ - var5;
/* 525:642 */       } else if (p_146199_1_ <= this.field_146225_q) {
/* 526:644 */         this.field_146225_q -= this.field_146225_q - p_146199_1_;
/* 527:    */       }
/* 528:647 */       if (this.field_146225_q < 0) {
/* 529:649 */         this.field_146225_q = 0;
/* 530:    */       }
/* 531:652 */       if (this.field_146225_q > var2) {
/* 532:654 */         this.field_146225_q = var2;
/* 533:    */       }
/* 534:    */     }
/* 535:    */   }
/* 536:    */   
/* 537:    */   public void func_146205_d(boolean p_146205_1_)
/* 538:    */   {
/* 539:661 */     this.field_146212_n = p_146205_1_;
/* 540:    */   }
/* 541:    */   
/* 542:    */   public boolean func_146176_q()
/* 543:    */   {
/* 544:666 */     return this.field_146220_v;
/* 545:    */   }
/* 546:    */   
/* 547:    */   public void func_146189_e(boolean p_146189_1_)
/* 548:    */   {
/* 549:671 */     this.field_146220_v = p_146189_1_;
/* 550:    */   }
/* 551:    */   
/* 552:    */   public void setCanLoseFocus(boolean flag)
/* 553:    */   {
/* 554:675 */     func_146205_d(flag);
/* 555:    */   }
/* 556:    */   
/* 557:    */   public void setMaxStringLength(int i)
/* 558:    */   {
/* 559:678 */     this.field_146217_k = i;
/* 560:680 */     if (this.field_146216_j.length() > i) {
/* 561:682 */       this.field_146216_j = this.field_146216_j.substring(0, i);
/* 562:    */     }
/* 563:    */   }
/* 564:    */   
/* 565:    */   public void setEnableBackgroundDrawing(boolean flag)
/* 566:    */   {
/* 567:687 */     func_146185_a(flag);
/* 568:    */   }
/* 569:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.ui.GuiCustomFontField
 * JD-Core Version:    0.7.0.1
 */