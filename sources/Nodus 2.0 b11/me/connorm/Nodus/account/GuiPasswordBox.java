/*   1:    */ package me.connorm.Nodus.account;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.gui.FontRenderer;
/*   4:    */ import net.minecraft.client.gui.Gui;
/*   5:    */ import net.minecraft.client.gui.GuiScreen;
/*   6:    */ import net.minecraft.client.renderer.Tessellator;
/*   7:    */ import net.minecraft.util.ChatAllowedCharacters;
/*   8:    */ import org.lwjgl.opengl.GL11;
/*   9:    */ 
/*  10:    */ public class GuiPasswordBox
/*  11:    */   extends Gui
/*  12:    */ {
/*  13:    */   private final FontRenderer field_146211_a;
/*  14:    */   private final int field_146209_f;
/*  15:    */   private final int field_146210_g;
/*  16:    */   private final int field_146218_h;
/*  17:    */   private final int field_146219_i;
/*  18:    */   public int xPos;
/*  19:    */   public int yPos;
/*  20:    */   public int width;
/*  21:    */   public int height;
/*  22: 21 */   private String field_146216_j = "";
/*  23: 22 */   private int field_146217_k = 32;
/*  24:    */   private int field_146214_l;
/*  25: 24 */   private boolean field_146215_m = true;
/*  26: 25 */   private boolean field_146212_n = true;
/*  27:    */   private boolean field_146213_o;
/*  28: 27 */   private boolean field_146226_p = true;
/*  29:    */   private int field_146225_q;
/*  30:    */   private int field_146224_r;
/*  31:    */   private int field_146223_s;
/*  32: 31 */   private int field_146222_t = 14737632;
/*  33: 32 */   private int field_146221_u = 7368816;
/*  34: 33 */   private boolean field_146220_v = true;
/*  35:    */   private static final String __OBFID = "CL_00000670";
/*  36:    */   
/*  37:    */   public GuiPasswordBox(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5)
/*  38:    */   {
/*  39: 38 */     this.field_146211_a = par1FontRenderer;
/*  40: 39 */     this.field_146209_f = par2;
/*  41: 40 */     this.field_146210_g = par3;
/*  42: 41 */     this.field_146218_h = par4;
/*  43: 42 */     this.field_146219_i = par5;
/*  44: 43 */     this.xPos = par2;
/*  45: 44 */     this.yPos = par3;
/*  46: 45 */     this.width = par4;
/*  47: 46 */     this.height = par5;
/*  48:    */   }
/*  49:    */   
/*  50:    */   public void updateCursorCounter()
/*  51:    */   {
/*  52: 51 */     this.field_146214_l += 1;
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void setText(String p_146180_1_)
/*  56:    */   {
/*  57: 56 */     if (p_146180_1_.length() > this.field_146217_k) {
/*  58: 58 */       this.field_146216_j = p_146180_1_.substring(0, this.field_146217_k);
/*  59:    */     } else {
/*  60: 62 */       this.field_146216_j = p_146180_1_;
/*  61:    */     }
/*  62: 65 */     func_146202_e();
/*  63:    */   }
/*  64:    */   
/*  65:    */   public String getText()
/*  66:    */   {
/*  67: 70 */     return this.field_146216_j;
/*  68:    */   }
/*  69:    */   
/*  70:    */   public String func_146207_c()
/*  71:    */   {
/*  72: 75 */     int var1 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  73: 76 */     int var2 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  74: 77 */     return this.field_146216_j.substring(var1, var2);
/*  75:    */   }
/*  76:    */   
/*  77:    */   public void func_146191_b(String p_146191_1_)
/*  78:    */   {
/*  79: 82 */     String var2 = "";
/*  80: 83 */     String var3 = ChatAllowedCharacters.filerAllowedCharacters(p_146191_1_);
/*  81: 84 */     int var4 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  82: 85 */     int var5 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  83: 86 */     int var6 = this.field_146217_k - this.field_146216_j.length() - (var4 - this.field_146223_s);
/*  84: 87 */     boolean var7 = false;
/*  85: 89 */     if (this.field_146216_j.length() > 0) {
/*  86: 91 */       var2 = var2 + this.field_146216_j.substring(0, var4);
/*  87:    */     }
/*  88:    */     int var8;
/*  89:    */     int var8;
/*  90: 94 */     if (var6 < var3.length())
/*  91:    */     {
/*  92: 96 */       var2 = var2 + var3.substring(0, var6);
/*  93: 97 */       var8 = var6;
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:101 */       var2 = var2 + var3;
/*  98:102 */       var8 = var3.length();
/*  99:    */     }
/* 100:105 */     if ((this.field_146216_j.length() > 0) && (var5 < this.field_146216_j.length())) {
/* 101:107 */       var2 = var2 + this.field_146216_j.substring(var5);
/* 102:    */     }
/* 103:110 */     this.field_146216_j = var2;
/* 104:111 */     func_146182_d(var4 - this.field_146223_s + var8);
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void func_146177_a(int p_146177_1_)
/* 108:    */   {
/* 109:116 */     if (this.field_146216_j.length() != 0) {
/* 110:118 */       if (this.field_146223_s != this.field_146224_r) {
/* 111:120 */         func_146191_b("");
/* 112:    */       } else {
/* 113:124 */         func_146175_b(func_146187_c(p_146177_1_) - this.field_146224_r);
/* 114:    */       }
/* 115:    */     }
/* 116:    */   }
/* 117:    */   
/* 118:    */   public void func_146175_b(int p_146175_1_)
/* 119:    */   {
/* 120:131 */     if (this.field_146216_j.length() != 0) {
/* 121:133 */       if (this.field_146223_s != this.field_146224_r)
/* 122:    */       {
/* 123:135 */         func_146191_b("");
/* 124:    */       }
/* 125:    */       else
/* 126:    */       {
/* 127:139 */         boolean var2 = p_146175_1_ < 0;
/* 128:140 */         int var3 = var2 ? this.field_146224_r + p_146175_1_ : this.field_146224_r;
/* 129:141 */         int var4 = var2 ? this.field_146224_r : this.field_146224_r + p_146175_1_;
/* 130:142 */         String var5 = "";
/* 131:144 */         if (var3 >= 0) {
/* 132:146 */           var5 = this.field_146216_j.substring(0, var3);
/* 133:    */         }
/* 134:149 */         if (var4 < this.field_146216_j.length()) {
/* 135:151 */           var5 = var5 + this.field_146216_j.substring(var4);
/* 136:    */         }
/* 137:154 */         this.field_146216_j = var5;
/* 138:156 */         if (var2) {
/* 139:158 */           func_146182_d(p_146175_1_);
/* 140:    */         }
/* 141:    */       }
/* 142:    */     }
/* 143:    */   }
/* 144:    */   
/* 145:    */   public int func_146187_c(int p_146187_1_)
/* 146:    */   {
/* 147:166 */     return func_146183_a(p_146187_1_, func_146198_h());
/* 148:    */   }
/* 149:    */   
/* 150:    */   public int func_146183_a(int p_146183_1_, int p_146183_2_)
/* 151:    */   {
/* 152:171 */     return func_146197_a(p_146183_1_, func_146198_h(), true);
/* 153:    */   }
/* 154:    */   
/* 155:    */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
/* 156:    */   {
/* 157:176 */     int var4 = p_146197_2_;
/* 158:177 */     boolean var5 = p_146197_1_ < 0;
/* 159:178 */     int var6 = Math.abs(p_146197_1_);
/* 160:180 */     for (int var7 = 0; var7 < var6; var7++) {
/* 161:182 */       if (var5)
/* 162:    */       {
/* 163:    */         for (;;)
/* 164:    */         {
/* 165:186 */           var4--;
/* 166:188 */           if ((!p_146197_3_) || (var4 <= 0)) {
/* 167:    */             break;
/* 168:    */           }
/* 169:188 */           if (this.field_146216_j.charAt(var4 - 1) != ' ') {}
/* 170:    */         }
/* 171:    */         do
/* 172:    */         {
/* 173:195 */           var4--;
/* 174:197 */           if (var4 <= 0) {
/* 175:    */             break;
/* 176:    */           }
/* 177:198 */         } while (this.field_146216_j.charAt(var4 - 1) != ' ');
/* 178:    */       }
/* 179:    */       else
/* 180:    */       {
/* 181:202 */         int var8 = this.field_146216_j.length();
/* 182:203 */         var4 = this.field_146216_j.indexOf(' ', var4);
/* 183:205 */         if (var4 == -1) {
/* 184:207 */           var4 = var8;
/* 185:    */         } else {
/* 186:    */           do
/* 187:    */           {
/* 188:213 */             var4++;
/* 189:215 */           } while ((p_146197_3_) && (var4 < var8) && (this.field_146216_j.charAt(var4) == ' '));
/* 190:    */         }
/* 191:    */       }
/* 192:    */     }
/* 193:222 */     return var4;
/* 194:    */   }
/* 195:    */   
/* 196:    */   public void func_146182_d(int p_146182_1_)
/* 197:    */   {
/* 198:227 */     func_146190_e(this.field_146223_s + p_146182_1_);
/* 199:    */   }
/* 200:    */   
/* 201:    */   public void func_146190_e(int p_146190_1_)
/* 202:    */   {
/* 203:232 */     this.field_146224_r = p_146190_1_;
/* 204:233 */     int var2 = this.field_146216_j.length();
/* 205:235 */     if (this.field_146224_r < 0) {
/* 206:237 */       this.field_146224_r = 0;
/* 207:    */     }
/* 208:240 */     if (this.field_146224_r > var2) {
/* 209:242 */       this.field_146224_r = var2;
/* 210:    */     }
/* 211:245 */     func_146199_i(this.field_146224_r);
/* 212:    */   }
/* 213:    */   
/* 214:    */   public void func_146196_d()
/* 215:    */   {
/* 216:250 */     func_146190_e(0);
/* 217:    */   }
/* 218:    */   
/* 219:    */   public void func_146202_e()
/* 220:    */   {
/* 221:255 */     func_146190_e(this.field_146216_j.length());
/* 222:    */   }
/* 223:    */   
/* 224:    */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
/* 225:    */   {
/* 226:260 */     if (!this.field_146213_o) {
/* 227:262 */       return false;
/* 228:    */     }
/* 229:265 */     switch (p_146201_1_)
/* 230:    */     {
/* 231:    */     case '\001': 
/* 232:268 */       func_146202_e();
/* 233:269 */       func_146199_i(0);
/* 234:270 */       return true;
/* 235:    */     case '\003': 
/* 236:272 */       GuiScreen.setClipboardString(func_146207_c());
/* 237:273 */       return true;
/* 238:    */     case '\026': 
/* 239:275 */       if (this.field_146226_p) {
/* 240:277 */         func_146191_b(GuiScreen.getClipboardString());
/* 241:    */       }
/* 242:280 */       return true;
/* 243:    */     case '\030': 
/* 244:282 */       GuiScreen.setClipboardString(func_146207_c());
/* 245:284 */       if (this.field_146226_p) {
/* 246:286 */         func_146191_b("");
/* 247:    */       }
/* 248:289 */       return true;
/* 249:    */     }
/* 250:292 */     switch (p_146201_2_)
/* 251:    */     {
/* 252:    */     case 14: 
/* 253:295 */       if (GuiScreen.isCtrlKeyDown())
/* 254:    */       {
/* 255:297 */         if (this.field_146226_p) {
/* 256:299 */           func_146177_a(-1);
/* 257:    */         }
/* 258:    */       }
/* 259:302 */       else if (this.field_146226_p) {
/* 260:304 */         func_146175_b(-1);
/* 261:    */       }
/* 262:307 */       return true;
/* 263:    */     case 199: 
/* 264:309 */       if (GuiScreen.isShiftKeyDown()) {
/* 265:311 */         func_146199_i(0);
/* 266:    */       } else {
/* 267:315 */         func_146196_d();
/* 268:    */       }
/* 269:318 */       return true;
/* 270:    */     case 203: 
/* 271:320 */       if (GuiScreen.isShiftKeyDown())
/* 272:    */       {
/* 273:322 */         if (GuiScreen.isCtrlKeyDown()) {
/* 274:324 */           func_146199_i(func_146183_a(-1, func_146186_n()));
/* 275:    */         } else {
/* 276:328 */           func_146199_i(func_146186_n() - 1);
/* 277:    */         }
/* 278:    */       }
/* 279:331 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 280:333 */         func_146190_e(func_146187_c(-1));
/* 281:    */       } else {
/* 282:337 */         func_146182_d(-1);
/* 283:    */       }
/* 284:340 */       return true;
/* 285:    */     case 205: 
/* 286:342 */       if (GuiScreen.isShiftKeyDown())
/* 287:    */       {
/* 288:344 */         if (GuiScreen.isCtrlKeyDown()) {
/* 289:346 */           func_146199_i(func_146183_a(1, func_146186_n()));
/* 290:    */         } else {
/* 291:350 */           func_146199_i(func_146186_n() + 1);
/* 292:    */         }
/* 293:    */       }
/* 294:353 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 295:355 */         func_146190_e(func_146187_c(1));
/* 296:    */       } else {
/* 297:359 */         func_146182_d(1);
/* 298:    */       }
/* 299:362 */       return true;
/* 300:    */     case 207: 
/* 301:364 */       if (GuiScreen.isShiftKeyDown()) {
/* 302:366 */         func_146199_i(this.field_146216_j.length());
/* 303:    */       } else {
/* 304:370 */         func_146202_e();
/* 305:    */       }
/* 306:373 */       return true;
/* 307:    */     case 211: 
/* 308:375 */       if (GuiScreen.isCtrlKeyDown())
/* 309:    */       {
/* 310:377 */         if (this.field_146226_p) {
/* 311:379 */           func_146177_a(1);
/* 312:    */         }
/* 313:    */       }
/* 314:382 */       else if (this.field_146226_p) {
/* 315:384 */         func_146175_b(1);
/* 316:    */       }
/* 317:387 */       return true;
/* 318:    */     }
/* 319:390 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
/* 320:    */     {
/* 321:392 */       if (this.field_146226_p) {
/* 322:394 */         func_146191_b(Character.toString(p_146201_1_));
/* 323:    */       }
/* 324:397 */       return true;
/* 325:    */     }
/* 326:400 */     return false;
/* 327:    */   }
/* 328:    */   
/* 329:    */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_)
/* 330:    */   {
/* 331:405 */     boolean var4 = (p_146192_1_ >= this.field_146209_f) && (p_146192_1_ < this.field_146209_f + this.field_146218_h) && (p_146192_2_ >= this.field_146210_g) && (p_146192_2_ < this.field_146210_g + this.field_146219_i);
/* 332:407 */     if (this.field_146212_n) {
/* 333:409 */       setFocused(var4);
/* 334:    */     }
/* 335:412 */     if ((this.field_146213_o) && (p_146192_3_ == 0))
/* 336:    */     {
/* 337:414 */       int var5 = p_146192_1_ - this.field_146209_f;
/* 338:416 */       if (this.field_146215_m) {
/* 339:418 */         var5 -= 4;
/* 340:    */       }
/* 341:421 */       String var6 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 342:422 */       func_146190_e(this.field_146211_a.trimStringToWidth(var6, var5).length() + this.field_146225_q);
/* 343:    */     }
/* 344:    */   }
/* 345:    */   
/* 346:    */   public void drawTextBox()
/* 347:    */   {
/* 348:428 */     if (func_146176_q())
/* 349:    */     {
/* 350:430 */       if (func_146181_i())
/* 351:    */       {
/* 352:432 */         drawRect(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
/* 353:433 */         drawRect(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
/* 354:    */       }
/* 355:436 */       int var1 = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
/* 356:437 */       int var2 = this.field_146224_r - this.field_146225_q;
/* 357:438 */       int var3 = this.field_146223_s - this.field_146225_q;
/* 358:439 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 359:440 */       boolean var5 = (var2 >= 0) && (var2 <= var4.length());
/* 360:441 */       boolean var6 = (this.field_146213_o) && (this.field_146214_l / 6 % 2 == 0) && (var5);
/* 361:442 */       int var7 = this.field_146215_m ? this.field_146209_f + 4 : this.field_146209_f;
/* 362:443 */       int var8 = this.field_146215_m ? this.field_146210_g + (this.field_146219_i - 8) / 2 : this.field_146210_g;
/* 363:444 */       int var9 = var7;
/* 364:446 */       if (var3 > var4.length()) {
/* 365:448 */         var3 = var4.length();
/* 366:    */       }
/* 367:451 */       if (var4.length() > 0)
/* 368:    */       {
/* 369:453 */         String var10 = var5 ? var4.substring(0, var2) : var4;
/* 370:454 */         var9 = this.field_146211_a.drawStringWithShadow(var10.replaceAll("(?s).", "*"), var7, var8, var1);
/* 371:    */       }
/* 372:457 */       boolean var13 = (this.field_146224_r < this.field_146216_j.length()) || (this.field_146216_j.length() >= func_146208_g());
/* 373:458 */       int var11 = var9;
/* 374:460 */       if (!var5)
/* 375:    */       {
/* 376:462 */         var11 = var2 > 0 ? var7 + this.field_146218_h : var7;
/* 377:    */       }
/* 378:464 */       else if (var13)
/* 379:    */       {
/* 380:466 */         var11 = var9 - 1;
/* 381:467 */         var9--;
/* 382:    */       }
/* 383:470 */       if ((var4.length() > 0) && (var5) && (var2 < var4.length())) {
/* 384:472 */         this.field_146211_a.drawStringWithShadow(var4.substring(var2), var9, var8, var1);
/* 385:    */       }
/* 386:475 */       if (var6) {
/* 387:477 */         if (var13) {
/* 388:479 */           Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.field_146211_a.FONT_HEIGHT, -3092272);
/* 389:    */         } else {
/* 390:483 */           this.field_146211_a.drawStringWithShadow("_", var11, var8, var1);
/* 391:    */         }
/* 392:    */       }
/* 393:487 */       if (var3 != var2)
/* 394:    */       {
/* 395:489 */         int var12 = var7 + this.field_146211_a.getStringWidth(var4.substring(0, var3));
/* 396:490 */         func_146188_c(var11, var8 - 1, var12 - 1, var8 + 1 + this.field_146211_a.FONT_HEIGHT);
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
/* 521:635 */         this.field_146225_q -= this.field_146211_a.trimStringToWidth(this.field_146216_j, var3, true).length();
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
/* 551:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     me.connorm.Nodus.account.GuiPasswordBox
 * JD-Core Version:    0.7.0.1
 */