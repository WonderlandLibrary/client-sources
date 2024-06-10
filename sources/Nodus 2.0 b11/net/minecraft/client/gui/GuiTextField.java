/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import net.minecraft.client.renderer.Tessellator;
/*   4:    */ import net.minecraft.util.ChatAllowedCharacters;
/*   5:    */ import org.lwjgl.opengl.GL11;
/*   6:    */ 
/*   7:    */ public class GuiTextField
/*   8:    */   extends Gui
/*   9:    */ {
/*  10:    */   private final FontRenderer field_146211_a;
/*  11:    */   private final int field_146209_f;
/*  12:    */   private final int field_146210_g;
/*  13:    */   private final int field_146218_h;
/*  14:    */   private final int field_146219_i;
/*  15: 14 */   private String field_146216_j = "";
/*  16: 15 */   private int field_146217_k = 32;
/*  17:    */   private int field_146214_l;
/*  18: 17 */   private boolean field_146215_m = true;
/*  19: 18 */   private boolean field_146212_n = true;
/*  20:    */   private boolean field_146213_o;
/*  21: 20 */   private boolean field_146226_p = true;
/*  22:    */   private int field_146225_q;
/*  23:    */   private int field_146224_r;
/*  24:    */   private int field_146223_s;
/*  25: 24 */   private int field_146222_t = 14737632;
/*  26: 25 */   private int field_146221_u = 7368816;
/*  27: 26 */   private boolean field_146220_v = true;
/*  28:    */   private static final String __OBFID = "CL_00000670";
/*  29:    */   
/*  30:    */   public GuiTextField(FontRenderer par1FontRenderer, int par2, int par3, int par4, int par5)
/*  31:    */   {
/*  32: 31 */     this.field_146211_a = par1FontRenderer;
/*  33: 32 */     this.field_146209_f = par2;
/*  34: 33 */     this.field_146210_g = par3;
/*  35: 34 */     this.field_146218_h = par4;
/*  36: 35 */     this.field_146219_i = par5;
/*  37:    */   }
/*  38:    */   
/*  39:    */   public void updateCursorCounter()
/*  40:    */   {
/*  41: 43 */     this.field_146214_l += 1;
/*  42:    */   }
/*  43:    */   
/*  44:    */   public void setText(String p_146180_1_)
/*  45:    */   {
/*  46: 51 */     if (p_146180_1_.length() > this.field_146217_k) {
/*  47: 53 */       this.field_146216_j = p_146180_1_.substring(0, this.field_146217_k);
/*  48:    */     } else {
/*  49: 57 */       this.field_146216_j = p_146180_1_;
/*  50:    */     }
/*  51: 60 */     func_146202_e();
/*  52:    */   }
/*  53:    */   
/*  54:    */   public String getText()
/*  55:    */   {
/*  56: 68 */     return this.field_146216_j;
/*  57:    */   }
/*  58:    */   
/*  59:    */   public String func_146207_c()
/*  60:    */   {
/*  61: 73 */     int var1 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  62: 74 */     int var2 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  63: 75 */     return this.field_146216_j.substring(var1, var2);
/*  64:    */   }
/*  65:    */   
/*  66:    */   public void func_146191_b(String p_146191_1_)
/*  67:    */   {
/*  68: 80 */     String var2 = "";
/*  69: 81 */     String var3 = ChatAllowedCharacters.filerAllowedCharacters(p_146191_1_);
/*  70: 82 */     int var4 = this.field_146224_r < this.field_146223_s ? this.field_146224_r : this.field_146223_s;
/*  71: 83 */     int var5 = this.field_146224_r < this.field_146223_s ? this.field_146223_s : this.field_146224_r;
/*  72: 84 */     int var6 = this.field_146217_k - this.field_146216_j.length() - (var4 - this.field_146223_s);
/*  73: 85 */     boolean var7 = false;
/*  74: 87 */     if (this.field_146216_j.length() > 0) {
/*  75: 89 */       var2 = var2 + this.field_146216_j.substring(0, var4);
/*  76:    */     }
/*  77:    */     int var8;
/*  78:    */     int var8;
/*  79: 94 */     if (var6 < var3.length())
/*  80:    */     {
/*  81: 96 */       var2 = var2 + var3.substring(0, var6);
/*  82: 97 */       var8 = var6;
/*  83:    */     }
/*  84:    */     else
/*  85:    */     {
/*  86:101 */       var2 = var2 + var3;
/*  87:102 */       var8 = var3.length();
/*  88:    */     }
/*  89:105 */     if ((this.field_146216_j.length() > 0) && (var5 < this.field_146216_j.length())) {
/*  90:107 */       var2 = var2 + this.field_146216_j.substring(var5);
/*  91:    */     }
/*  92:110 */     this.field_146216_j = var2;
/*  93:111 */     func_146182_d(var4 - this.field_146223_s + var8);
/*  94:    */   }
/*  95:    */   
/*  96:    */   public void func_146177_a(int p_146177_1_)
/*  97:    */   {
/*  98:116 */     if (this.field_146216_j.length() != 0) {
/*  99:118 */       if (this.field_146223_s != this.field_146224_r) {
/* 100:120 */         func_146191_b("");
/* 101:    */       } else {
/* 102:124 */         func_146175_b(func_146187_c(p_146177_1_) - this.field_146224_r);
/* 103:    */       }
/* 104:    */     }
/* 105:    */   }
/* 106:    */   
/* 107:    */   public void func_146175_b(int p_146175_1_)
/* 108:    */   {
/* 109:131 */     if (this.field_146216_j.length() != 0) {
/* 110:133 */       if (this.field_146223_s != this.field_146224_r)
/* 111:    */       {
/* 112:135 */         func_146191_b("");
/* 113:    */       }
/* 114:    */       else
/* 115:    */       {
/* 116:139 */         boolean var2 = p_146175_1_ < 0;
/* 117:140 */         int var3 = var2 ? this.field_146224_r + p_146175_1_ : this.field_146224_r;
/* 118:141 */         int var4 = var2 ? this.field_146224_r : this.field_146224_r + p_146175_1_;
/* 119:142 */         String var5 = "";
/* 120:144 */         if (var3 >= 0) {
/* 121:146 */           var5 = this.field_146216_j.substring(0, var3);
/* 122:    */         }
/* 123:149 */         if (var4 < this.field_146216_j.length()) {
/* 124:151 */           var5 = var5 + this.field_146216_j.substring(var4);
/* 125:    */         }
/* 126:154 */         this.field_146216_j = var5;
/* 127:156 */         if (var2) {
/* 128:158 */           func_146182_d(p_146175_1_);
/* 129:    */         }
/* 130:    */       }
/* 131:    */     }
/* 132:    */   }
/* 133:    */   
/* 134:    */   public int func_146187_c(int p_146187_1_)
/* 135:    */   {
/* 136:166 */     return func_146183_a(p_146187_1_, func_146198_h());
/* 137:    */   }
/* 138:    */   
/* 139:    */   public int func_146183_a(int p_146183_1_, int p_146183_2_)
/* 140:    */   {
/* 141:171 */     return func_146197_a(p_146183_1_, func_146198_h(), true);
/* 142:    */   }
/* 143:    */   
/* 144:    */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_)
/* 145:    */   {
/* 146:176 */     int var4 = p_146197_2_;
/* 147:177 */     boolean var5 = p_146197_1_ < 0;
/* 148:178 */     int var6 = Math.abs(p_146197_1_);
/* 149:180 */     for (int var7 = 0; var7 < var6; var7++) {
/* 150:182 */       if (var5)
/* 151:    */       {
/* 152:    */         for (;;)
/* 153:    */         {
/* 154:186 */           var4--;
/* 155:184 */           if ((p_146197_3_) && (var4 > 0)) {
/* 156:184 */             if (this.field_146216_j.charAt(var4 - 1) != ' ') {
/* 157:    */               break;
/* 158:    */             }
/* 159:    */           }
/* 160:    */         }
/* 161:    */         do
/* 162:    */         {
/* 163:191 */           var4--;
/* 164:189 */           if (var4 <= 0) {
/* 165:    */             break;
/* 166:    */           }
/* 167:189 */         } while (this.field_146216_j.charAt(var4 - 1) != ' ');
/* 168:    */       }
/* 169:    */       else
/* 170:    */       {
/* 171:196 */         int var8 = this.field_146216_j.length();
/* 172:197 */         var4 = this.field_146216_j.indexOf(' ', var4);
/* 173:199 */         if (var4 == -1) {
/* 174:201 */           var4 = var8;
/* 175:    */         } else {
/* 176:205 */           while ((p_146197_3_) && (var4 < var8) && (this.field_146216_j.charAt(var4) == ' ')) {
/* 177:207 */             var4++;
/* 178:    */           }
/* 179:    */         }
/* 180:    */       }
/* 181:    */     }
/* 182:213 */     return var4;
/* 183:    */   }
/* 184:    */   
/* 185:    */   public void func_146182_d(int p_146182_1_)
/* 186:    */   {
/* 187:218 */     func_146190_e(this.field_146223_s + p_146182_1_);
/* 188:    */   }
/* 189:    */   
/* 190:    */   public void func_146190_e(int p_146190_1_)
/* 191:    */   {
/* 192:223 */     this.field_146224_r = p_146190_1_;
/* 193:224 */     int var2 = this.field_146216_j.length();
/* 194:226 */     if (this.field_146224_r < 0) {
/* 195:228 */       this.field_146224_r = 0;
/* 196:    */     }
/* 197:231 */     if (this.field_146224_r > var2) {
/* 198:233 */       this.field_146224_r = var2;
/* 199:    */     }
/* 200:236 */     func_146199_i(this.field_146224_r);
/* 201:    */   }
/* 202:    */   
/* 203:    */   public void func_146196_d()
/* 204:    */   {
/* 205:241 */     func_146190_e(0);
/* 206:    */   }
/* 207:    */   
/* 208:    */   public void func_146202_e()
/* 209:    */   {
/* 210:246 */     func_146190_e(this.field_146216_j.length());
/* 211:    */   }
/* 212:    */   
/* 213:    */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_)
/* 214:    */   {
/* 215:254 */     if (!this.field_146213_o) {
/* 216:256 */       return false;
/* 217:    */     }
/* 218:260 */     switch (p_146201_1_)
/* 219:    */     {
/* 220:    */     case '\001': 
/* 221:263 */       func_146202_e();
/* 222:264 */       func_146199_i(0);
/* 223:265 */       return true;
/* 224:    */     case '\003': 
/* 225:268 */       GuiScreen.setClipboardString(func_146207_c());
/* 226:269 */       return true;
/* 227:    */     case '\026': 
/* 228:272 */       if (this.field_146226_p) {
/* 229:274 */         func_146191_b(GuiScreen.getClipboardString());
/* 230:    */       }
/* 231:277 */       return true;
/* 232:    */     case '\030': 
/* 233:280 */       GuiScreen.setClipboardString(func_146207_c());
/* 234:282 */       if (this.field_146226_p) {
/* 235:284 */         func_146191_b("");
/* 236:    */       }
/* 237:287 */       return true;
/* 238:    */     }
/* 239:290 */     switch (p_146201_2_)
/* 240:    */     {
/* 241:    */     case 14: 
/* 242:293 */       if (GuiScreen.isCtrlKeyDown())
/* 243:    */       {
/* 244:295 */         if (this.field_146226_p) {
/* 245:297 */           func_146177_a(-1);
/* 246:    */         }
/* 247:    */       }
/* 248:300 */       else if (this.field_146226_p) {
/* 249:302 */         func_146175_b(-1);
/* 250:    */       }
/* 251:305 */       return true;
/* 252:    */     case 199: 
/* 253:308 */       if (GuiScreen.isShiftKeyDown()) {
/* 254:310 */         func_146199_i(0);
/* 255:    */       } else {
/* 256:314 */         func_146196_d();
/* 257:    */       }
/* 258:317 */       return true;
/* 259:    */     case 203: 
/* 260:320 */       if (GuiScreen.isShiftKeyDown())
/* 261:    */       {
/* 262:322 */         if (GuiScreen.isCtrlKeyDown()) {
/* 263:324 */           func_146199_i(func_146183_a(-1, func_146186_n()));
/* 264:    */         } else {
/* 265:328 */           func_146199_i(func_146186_n() - 1);
/* 266:    */         }
/* 267:    */       }
/* 268:331 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 269:333 */         func_146190_e(func_146187_c(-1));
/* 270:    */       } else {
/* 271:337 */         func_146182_d(-1);
/* 272:    */       }
/* 273:340 */       return true;
/* 274:    */     case 205: 
/* 275:343 */       if (GuiScreen.isShiftKeyDown())
/* 276:    */       {
/* 277:345 */         if (GuiScreen.isCtrlKeyDown()) {
/* 278:347 */           func_146199_i(func_146183_a(1, func_146186_n()));
/* 279:    */         } else {
/* 280:351 */           func_146199_i(func_146186_n() + 1);
/* 281:    */         }
/* 282:    */       }
/* 283:354 */       else if (GuiScreen.isCtrlKeyDown()) {
/* 284:356 */         func_146190_e(func_146187_c(1));
/* 285:    */       } else {
/* 286:360 */         func_146182_d(1);
/* 287:    */       }
/* 288:363 */       return true;
/* 289:    */     case 207: 
/* 290:366 */       if (GuiScreen.isShiftKeyDown()) {
/* 291:368 */         func_146199_i(this.field_146216_j.length());
/* 292:    */       } else {
/* 293:372 */         func_146202_e();
/* 294:    */       }
/* 295:375 */       return true;
/* 296:    */     case 211: 
/* 297:378 */       if (GuiScreen.isCtrlKeyDown())
/* 298:    */       {
/* 299:380 */         if (this.field_146226_p) {
/* 300:382 */           func_146177_a(1);
/* 301:    */         }
/* 302:    */       }
/* 303:385 */       else if (this.field_146226_p) {
/* 304:387 */         func_146175_b(1);
/* 305:    */       }
/* 306:390 */       return true;
/* 307:    */     }
/* 308:393 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_))
/* 309:    */     {
/* 310:395 */       if (this.field_146226_p) {
/* 311:397 */         func_146191_b(Character.toString(p_146201_1_));
/* 312:    */       }
/* 313:400 */       return true;
/* 314:    */     }
/* 315:404 */     return false;
/* 316:    */   }
/* 317:    */   
/* 318:    */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_)
/* 319:    */   {
/* 320:416 */     boolean var4 = (p_146192_1_ >= this.field_146209_f) && (p_146192_1_ < this.field_146209_f + this.field_146218_h) && (p_146192_2_ >= this.field_146210_g) && (p_146192_2_ < this.field_146210_g + this.field_146219_i);
/* 321:418 */     if (this.field_146212_n) {
/* 322:420 */       setFocused(var4);
/* 323:    */     }
/* 324:423 */     if ((this.field_146213_o) && (p_146192_3_ == 0))
/* 325:    */     {
/* 326:425 */       int var5 = p_146192_1_ - this.field_146209_f;
/* 327:427 */       if (this.field_146215_m) {
/* 328:429 */         var5 -= 4;
/* 329:    */       }
/* 330:432 */       String var6 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 331:433 */       func_146190_e(this.field_146211_a.trimStringToWidth(var6, var5).length() + this.field_146225_q);
/* 332:    */     }
/* 333:    */   }
/* 334:    */   
/* 335:    */   public void drawTextBox()
/* 336:    */   {
/* 337:442 */     if (func_146176_q())
/* 338:    */     {
/* 339:444 */       if (func_146181_i())
/* 340:    */       {
/* 341:446 */         drawRect(this.field_146209_f - 1, this.field_146210_g - 1, this.field_146209_f + this.field_146218_h + 1, this.field_146210_g + this.field_146219_i + 1, -6250336);
/* 342:447 */         drawRect(this.field_146209_f, this.field_146210_g, this.field_146209_f + this.field_146218_h, this.field_146210_g + this.field_146219_i, -16777216);
/* 343:    */       }
/* 344:450 */       int var1 = this.field_146226_p ? this.field_146222_t : this.field_146221_u;
/* 345:451 */       int var2 = this.field_146224_r - this.field_146225_q;
/* 346:452 */       int var3 = this.field_146223_s - this.field_146225_q;
/* 347:453 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), func_146200_o());
/* 348:454 */       boolean var5 = (var2 >= 0) && (var2 <= var4.length());
/* 349:455 */       boolean var6 = (this.field_146213_o) && (this.field_146214_l / 6 % 2 == 0) && (var5);
/* 350:456 */       int var7 = this.field_146215_m ? this.field_146209_f + 4 : this.field_146209_f;
/* 351:457 */       int var8 = this.field_146215_m ? this.field_146210_g + (this.field_146219_i - 8) / 2 : this.field_146210_g;
/* 352:458 */       int var9 = var7;
/* 353:460 */       if (var3 > var4.length()) {
/* 354:462 */         var3 = var4.length();
/* 355:    */       }
/* 356:465 */       if (var4.length() > 0)
/* 357:    */       {
/* 358:467 */         String var10 = var5 ? var4.substring(0, var2) : var4;
/* 359:468 */         var9 = this.field_146211_a.drawStringWithShadow(var10, var7, var8, var1);
/* 360:    */       }
/* 361:471 */       boolean var13 = (this.field_146224_r < this.field_146216_j.length()) || (this.field_146216_j.length() >= func_146208_g());
/* 362:472 */       int var11 = var9;
/* 363:474 */       if (!var5)
/* 364:    */       {
/* 365:476 */         var11 = var2 > 0 ? var7 + this.field_146218_h : var7;
/* 366:    */       }
/* 367:478 */       else if (var13)
/* 368:    */       {
/* 369:480 */         var11 = var9 - 1;
/* 370:481 */         var9--;
/* 371:    */       }
/* 372:484 */       if ((var4.length() > 0) && (var5) && (var2 < var4.length())) {
/* 373:486 */         this.field_146211_a.drawStringWithShadow(var4.substring(var2), var9, var8, var1);
/* 374:    */       }
/* 375:489 */       if (var6) {
/* 376:491 */         if (var13) {
/* 377:493 */           Gui.drawRect(var11, var8 - 1, var11 + 1, var8 + 1 + this.field_146211_a.FONT_HEIGHT, -3092272);
/* 378:    */         } else {
/* 379:497 */           this.field_146211_a.drawStringWithShadow("_", var11, var8, var1);
/* 380:    */         }
/* 381:    */       }
/* 382:501 */       if (var3 != var2)
/* 383:    */       {
/* 384:503 */         int var12 = var7 + this.field_146211_a.getStringWidth(var4.substring(0, var3));
/* 385:504 */         func_146188_c(var11, var8 - 1, var12 - 1, var8 + 1 + this.field_146211_a.FONT_HEIGHT);
/* 386:    */       }
/* 387:    */     }
/* 388:    */   }
/* 389:    */   
/* 390:    */   private void func_146188_c(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_)
/* 391:    */   {
/* 392:513 */     if (p_146188_1_ < p_146188_3_)
/* 393:    */     {
/* 394:515 */       int var5 = p_146188_1_;
/* 395:516 */       p_146188_1_ = p_146188_3_;
/* 396:517 */       p_146188_3_ = var5;
/* 397:    */     }
/* 398:520 */     if (p_146188_2_ < p_146188_4_)
/* 399:    */     {
/* 400:522 */       int var5 = p_146188_2_;
/* 401:523 */       p_146188_2_ = p_146188_4_;
/* 402:524 */       p_146188_4_ = var5;
/* 403:    */     }
/* 404:527 */     if (p_146188_3_ > this.field_146209_f + this.field_146218_h) {
/* 405:529 */       p_146188_3_ = this.field_146209_f + this.field_146218_h;
/* 406:    */     }
/* 407:532 */     if (p_146188_1_ > this.field_146209_f + this.field_146218_h) {
/* 408:534 */       p_146188_1_ = this.field_146209_f + this.field_146218_h;
/* 409:    */     }
/* 410:537 */     Tessellator var6 = Tessellator.instance;
/* 411:538 */     GL11.glColor4f(0.0F, 0.0F, 255.0F, 255.0F);
/* 412:539 */     GL11.glDisable(3553);
/* 413:540 */     GL11.glEnable(3058);
/* 414:541 */     GL11.glLogicOp(5387);
/* 415:542 */     var6.startDrawingQuads();
/* 416:543 */     var6.addVertex(p_146188_1_, p_146188_4_, 0.0D);
/* 417:544 */     var6.addVertex(p_146188_3_, p_146188_4_, 0.0D);
/* 418:545 */     var6.addVertex(p_146188_3_, p_146188_2_, 0.0D);
/* 419:546 */     var6.addVertex(p_146188_1_, p_146188_2_, 0.0D);
/* 420:547 */     var6.draw();
/* 421:548 */     GL11.glDisable(3058);
/* 422:549 */     GL11.glEnable(3553);
/* 423:    */   }
/* 424:    */   
/* 425:    */   public void func_146203_f(int p_146203_1_)
/* 426:    */   {
/* 427:554 */     this.field_146217_k = p_146203_1_;
/* 428:556 */     if (this.field_146216_j.length() > p_146203_1_) {
/* 429:558 */       this.field_146216_j = this.field_146216_j.substring(0, p_146203_1_);
/* 430:    */     }
/* 431:    */   }
/* 432:    */   
/* 433:    */   public int func_146208_g()
/* 434:    */   {
/* 435:564 */     return this.field_146217_k;
/* 436:    */   }
/* 437:    */   
/* 438:    */   public int func_146198_h()
/* 439:    */   {
/* 440:569 */     return this.field_146224_r;
/* 441:    */   }
/* 442:    */   
/* 443:    */   public boolean func_146181_i()
/* 444:    */   {
/* 445:574 */     return this.field_146215_m;
/* 446:    */   }
/* 447:    */   
/* 448:    */   public void func_146185_a(boolean p_146185_1_)
/* 449:    */   {
/* 450:579 */     this.field_146215_m = p_146185_1_;
/* 451:    */   }
/* 452:    */   
/* 453:    */   public void func_146193_g(int p_146193_1_)
/* 454:    */   {
/* 455:584 */     this.field_146222_t = p_146193_1_;
/* 456:    */   }
/* 457:    */   
/* 458:    */   public void func_146204_h(int p_146204_1_)
/* 459:    */   {
/* 460:589 */     this.field_146221_u = p_146204_1_;
/* 461:    */   }
/* 462:    */   
/* 463:    */   public void setFocused(boolean p_146195_1_)
/* 464:    */   {
/* 465:597 */     if ((p_146195_1_) && (!this.field_146213_o)) {
/* 466:599 */       this.field_146214_l = 0;
/* 467:    */     }
/* 468:602 */     this.field_146213_o = p_146195_1_;
/* 469:    */   }
/* 470:    */   
/* 471:    */   public boolean isFocused()
/* 472:    */   {
/* 473:610 */     return this.field_146213_o;
/* 474:    */   }
/* 475:    */   
/* 476:    */   public void func_146184_c(boolean p_146184_1_)
/* 477:    */   {
/* 478:615 */     this.field_146226_p = p_146184_1_;
/* 479:    */   }
/* 480:    */   
/* 481:    */   public int func_146186_n()
/* 482:    */   {
/* 483:620 */     return this.field_146223_s;
/* 484:    */   }
/* 485:    */   
/* 486:    */   public int func_146200_o()
/* 487:    */   {
/* 488:625 */     return func_146181_i() ? this.field_146218_h - 8 : this.field_146218_h;
/* 489:    */   }
/* 490:    */   
/* 491:    */   public void func_146199_i(int p_146199_1_)
/* 492:    */   {
/* 493:630 */     int var2 = this.field_146216_j.length();
/* 494:632 */     if (p_146199_1_ > var2) {
/* 495:634 */       p_146199_1_ = var2;
/* 496:    */     }
/* 497:637 */     if (p_146199_1_ < 0) {
/* 498:639 */       p_146199_1_ = 0;
/* 499:    */     }
/* 500:642 */     this.field_146223_s = p_146199_1_;
/* 501:644 */     if (this.field_146211_a != null)
/* 502:    */     {
/* 503:646 */       if (this.field_146225_q > var2) {
/* 504:648 */         this.field_146225_q = var2;
/* 505:    */       }
/* 506:651 */       int var3 = func_146200_o();
/* 507:652 */       String var4 = this.field_146211_a.trimStringToWidth(this.field_146216_j.substring(this.field_146225_q), var3);
/* 508:653 */       int var5 = var4.length() + this.field_146225_q;
/* 509:655 */       if (p_146199_1_ == this.field_146225_q) {
/* 510:657 */         this.field_146225_q -= this.field_146211_a.trimStringToWidth(this.field_146216_j, var3, true).length();
/* 511:    */       }
/* 512:660 */       if (p_146199_1_ > var5) {
/* 513:662 */         this.field_146225_q += p_146199_1_ - var5;
/* 514:664 */       } else if (p_146199_1_ <= this.field_146225_q) {
/* 515:666 */         this.field_146225_q -= this.field_146225_q - p_146199_1_;
/* 516:    */       }
/* 517:669 */       if (this.field_146225_q < 0) {
/* 518:671 */         this.field_146225_q = 0;
/* 519:    */       }
/* 520:674 */       if (this.field_146225_q > var2) {
/* 521:676 */         this.field_146225_q = var2;
/* 522:    */       }
/* 523:    */     }
/* 524:    */   }
/* 525:    */   
/* 526:    */   public void setCanLoseFocus(boolean flag)
/* 527:    */   {
/* 528:683 */     func_146205_d(flag);
/* 529:    */   }
/* 530:    */   
/* 531:    */   public void setEnableBackgroundDrawing(boolean flag)
/* 532:    */   {
/* 533:688 */     func_146185_a(flag);
/* 534:    */   }
/* 535:    */   
/* 536:    */   public void setMaxStringLength(int i)
/* 537:    */   {
/* 538:693 */     this.field_146217_k = i;
/* 539:695 */     if (this.field_146216_j.length() > i) {
/* 540:697 */       this.field_146216_j = this.field_146216_j.substring(0, i);
/* 541:    */     }
/* 542:    */   }
/* 543:    */   
/* 544:    */   public void func_146205_d(boolean p_146205_1_)
/* 545:    */   {
/* 546:703 */     this.field_146212_n = p_146205_1_;
/* 547:    */   }
/* 548:    */   
/* 549:    */   public boolean func_146176_q()
/* 550:    */   {
/* 551:708 */     return this.field_146220_v;
/* 552:    */   }
/* 553:    */   
/* 554:    */   public void func_146189_e(boolean p_146189_1_)
/* 555:    */   {
/* 556:713 */     this.field_146220_v = p_146189_1_;
/* 557:    */   }
/* 558:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiTextField
 * JD-Core Version:    0.7.0.1
 */