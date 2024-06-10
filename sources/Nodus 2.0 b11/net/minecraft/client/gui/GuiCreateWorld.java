/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.client.Minecraft;
/*   7:    */ import net.minecraft.client.resources.I18n;
/*   8:    */ import net.minecraft.util.ChatAllowedCharacters;
/*   9:    */ import net.minecraft.util.MathHelper;
/*  10:    */ import net.minecraft.world.WorldSettings;
/*  11:    */ import net.minecraft.world.WorldSettings.GameType;
/*  12:    */ import net.minecraft.world.WorldType;
/*  13:    */ import net.minecraft.world.storage.ISaveFormat;
/*  14:    */ import net.minecraft.world.storage.WorldInfo;
/*  15:    */ import org.lwjgl.input.Keyboard;
/*  16:    */ 
/*  17:    */ public class GuiCreateWorld
/*  18:    */   extends GuiScreen
/*  19:    */ {
/*  20:    */   private GuiScreen field_146332_f;
/*  21:    */   private GuiTextField field_146333_g;
/*  22:    */   private GuiTextField field_146335_h;
/*  23:    */   private String field_146336_i;
/*  24: 22 */   private String field_146342_r = "survival";
/*  25: 23 */   private boolean field_146341_s = true;
/*  26:    */   private boolean field_146340_t;
/*  27:    */   private boolean field_146339_u;
/*  28:    */   private boolean field_146338_v;
/*  29:    */   private boolean field_146337_w;
/*  30:    */   private boolean field_146345_x;
/*  31:    */   private boolean field_146344_y;
/*  32:    */   private NodusGuiButton field_146343_z;
/*  33:    */   private NodusGuiButton field_146324_A;
/*  34:    */   private NodusGuiButton field_146325_B;
/*  35:    */   private NodusGuiButton field_146326_C;
/*  36:    */   private NodusGuiButton field_146320_D;
/*  37:    */   private NodusGuiButton field_146321_E;
/*  38:    */   private NodusGuiButton field_146322_F;
/*  39:    */   private String field_146323_G;
/*  40:    */   private String field_146328_H;
/*  41:    */   private String field_146329_I;
/*  42:    */   private String field_146330_J;
/*  43:    */   private int field_146331_K;
/*  44: 42 */   public String field_146334_a = "";
/*  45: 43 */   private static final String[] field_146327_L = { "CON", "COM", "PRN", "AUX", "CLOCK$", "NUL", "COM1", "COM2", "COM3", "COM4", "COM5", "COM6", "COM7", "COM8", "COM9", "LPT1", "LPT2", "LPT3", "LPT4", "LPT5", "LPT6", "LPT7", "LPT8", "LPT9" };
/*  46:    */   private static final String __OBFID = "CL_00000689";
/*  47:    */   
/*  48:    */   public GuiCreateWorld(GuiScreen par1GuiScreen)
/*  49:    */   {
/*  50: 48 */     this.field_146332_f = par1GuiScreen;
/*  51: 49 */     this.field_146329_I = "";
/*  52: 50 */     this.field_146330_J = I18n.format("selectWorld.newWorld", new Object[0]);
/*  53:    */   }
/*  54:    */   
/*  55:    */   public void updateScreen()
/*  56:    */   {
/*  57: 58 */     this.field_146333_g.updateCursorCounter();
/*  58: 59 */     this.field_146335_h.updateCursorCounter();
/*  59:    */   }
/*  60:    */   
/*  61:    */   public void initGui()
/*  62:    */   {
/*  63: 67 */     Keyboard.enableRepeatEvents(true);
/*  64: 68 */     this.buttonList.clear();
/*  65: 69 */     this.buttonList.add(new NodusGuiButton(0, width / 2 - 155, height - 28, 150, 20, I18n.format("selectWorld.create", new Object[0])));
/*  66: 70 */     this.buttonList.add(new NodusGuiButton(1, width / 2 + 5, height - 28, 150, 20, I18n.format("gui.cancel", new Object[0])));
/*  67: 71 */     this.buttonList.add(this.field_146343_z = new NodusGuiButton(2, width / 2 - 75, 115, 150, 20, I18n.format("selectWorld.gameMode", new Object[0])));
/*  68: 72 */     this.buttonList.add(this.field_146324_A = new NodusGuiButton(3, width / 2 - 75, 187, 150, 20, I18n.format("selectWorld.moreWorldOptions", new Object[0])));
/*  69: 73 */     this.buttonList.add(this.field_146325_B = new NodusGuiButton(4, width / 2 - 155, 100, 150, 20, I18n.format("selectWorld.mapFeatures", new Object[0])));
/*  70: 74 */     this.field_146325_B.field_146125_m = false;
/*  71: 75 */     this.buttonList.add(this.field_146326_C = new NodusGuiButton(7, width / 2 + 5, 151, 150, 20, I18n.format("selectWorld.bonusItems", new Object[0])));
/*  72: 76 */     this.field_146326_C.field_146125_m = false;
/*  73: 77 */     this.buttonList.add(this.field_146320_D = new NodusGuiButton(5, width / 2 + 5, 100, 150, 20, I18n.format("selectWorld.mapType", new Object[0])));
/*  74: 78 */     this.field_146320_D.field_146125_m = false;
/*  75: 79 */     this.buttonList.add(this.field_146321_E = new NodusGuiButton(6, width / 2 - 155, 151, 150, 20, I18n.format("selectWorld.allowCommands", new Object[0])));
/*  76: 80 */     this.field_146321_E.field_146125_m = false;
/*  77: 81 */     this.buttonList.add(this.field_146322_F = new NodusGuiButton(8, width / 2 + 5, 120, 150, 20, I18n.format("selectWorld.customizeType", new Object[0])));
/*  78: 82 */     this.field_146322_F.field_146125_m = false;
/*  79: 83 */     this.field_146333_g = new GuiTextField(this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/*  80: 84 */     this.field_146333_g.setFocused(true);
/*  81: 85 */     this.field_146333_g.setText(this.field_146330_J);
/*  82: 86 */     this.field_146335_h = new GuiTextField(this.fontRendererObj, width / 2 - 100, 60, 200, 20);
/*  83: 87 */     this.field_146335_h.setText(this.field_146329_I);
/*  84: 88 */     func_146316_a(this.field_146344_y);
/*  85: 89 */     func_146314_g();
/*  86: 90 */     func_146319_h();
/*  87:    */   }
/*  88:    */   
/*  89:    */   private void func_146314_g()
/*  90:    */   {
/*  91: 95 */     this.field_146336_i = this.field_146333_g.getText().trim();
/*  92: 96 */     char[] var1 = ChatAllowedCharacters.allowedCharacters;
/*  93: 97 */     int var2 = var1.length;
/*  94: 99 */     for (int var3 = 0; var3 < var2; var3++)
/*  95:    */     {
/*  96:101 */       char var4 = var1[var3];
/*  97:102 */       this.field_146336_i = this.field_146336_i.replace(var4, '_');
/*  98:    */     }
/*  99:105 */     if (MathHelper.stringNullOrLengthZero(this.field_146336_i)) {
/* 100:107 */       this.field_146336_i = "World";
/* 101:    */     }
/* 102:110 */     this.field_146336_i = func_146317_a(this.mc.getSaveLoader(), this.field_146336_i);
/* 103:    */   }
/* 104:    */   
/* 105:    */   private void func_146319_h()
/* 106:    */   {
/* 107:115 */     this.field_146343_z.displayString = (I18n.format("selectWorld.gameMode", new Object[0]) + " " + I18n.format(new StringBuilder("selectWorld.gameMode.").append(this.field_146342_r).toString(), new Object[0]));
/* 108:116 */     this.field_146323_G = I18n.format("selectWorld.gameMode." + this.field_146342_r + ".line1", new Object[0]);
/* 109:117 */     this.field_146328_H = I18n.format("selectWorld.gameMode." + this.field_146342_r + ".line2", new Object[0]);
/* 110:118 */     this.field_146325_B.displayString = (I18n.format("selectWorld.mapFeatures", new Object[0]) + " ");
/* 111:120 */     if (this.field_146341_s) {
/* 112:122 */       this.field_146325_B.displayString += I18n.format("options.on", new Object[0]);
/* 113:    */     } else {
/* 114:126 */       this.field_146325_B.displayString += I18n.format("options.off", new Object[0]);
/* 115:    */     }
/* 116:129 */     this.field_146326_C.displayString = (I18n.format("selectWorld.bonusItems", new Object[0]) + " ");
/* 117:131 */     if ((this.field_146338_v) && (!this.field_146337_w)) {
/* 118:133 */       this.field_146326_C.displayString += I18n.format("options.on", new Object[0]);
/* 119:    */     } else {
/* 120:137 */       this.field_146326_C.displayString += I18n.format("options.off", new Object[0]);
/* 121:    */     }
/* 122:140 */     this.field_146320_D.displayString = (I18n.format("selectWorld.mapType", new Object[0]) + " " + I18n.format(WorldType.worldTypes[this.field_146331_K].getTranslateName(), new Object[0]));
/* 123:141 */     this.field_146321_E.displayString = (I18n.format("selectWorld.allowCommands", new Object[0]) + " ");
/* 124:143 */     if ((this.field_146340_t) && (!this.field_146337_w)) {
/* 125:145 */       this.field_146321_E.displayString += I18n.format("options.on", new Object[0]);
/* 126:    */     } else {
/* 127:149 */       this.field_146321_E.displayString += I18n.format("options.off", new Object[0]);
/* 128:    */     }
/* 129:    */   }
/* 130:    */   
/* 131:    */   public static String func_146317_a(ISaveFormat p_146317_0_, String p_146317_1_)
/* 132:    */   {
/* 133:155 */     p_146317_1_ = p_146317_1_.replaceAll("[\\./\"]", "_");
/* 134:156 */     String[] var2 = field_146327_L;
/* 135:157 */     int var3 = var2.length;
/* 136:159 */     for (int var4 = 0; var4 < var3; var4++)
/* 137:    */     {
/* 138:161 */       String var5 = var2[var4];
/* 139:163 */       if (p_146317_1_.equalsIgnoreCase(var5)) {
/* 140:165 */         p_146317_1_ = "_" + p_146317_1_ + "_";
/* 141:    */       }
/* 142:    */     }
/* 143:169 */     while (p_146317_0_.getWorldInfo(p_146317_1_) != null) {
/* 144:171 */       p_146317_1_ = p_146317_1_ + "-";
/* 145:    */     }
/* 146:174 */     return p_146317_1_;
/* 147:    */   }
/* 148:    */   
/* 149:    */   public void onGuiClosed()
/* 150:    */   {
/* 151:182 */     Keyboard.enableRepeatEvents(false);
/* 152:    */   }
/* 153:    */   
/* 154:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 155:    */   {
/* 156:187 */     if (p_146284_1_.enabled) {
/* 157:189 */       if (p_146284_1_.id == 1)
/* 158:    */       {
/* 159:191 */         this.mc.displayGuiScreen(this.field_146332_f);
/* 160:    */       }
/* 161:193 */       else if (p_146284_1_.id == 0)
/* 162:    */       {
/* 163:195 */         this.mc.displayGuiScreen(null);
/* 164:197 */         if (this.field_146345_x) {
/* 165:199 */           return;
/* 166:    */         }
/* 167:202 */         this.field_146345_x = true;
/* 168:203 */         long var2 = new Random().nextLong();
/* 169:204 */         String var4 = this.field_146335_h.getText();
/* 170:206 */         if (!MathHelper.stringNullOrLengthZero(var4)) {
/* 171:    */           try
/* 172:    */           {
/* 173:210 */             long var5 = Long.parseLong(var4);
/* 174:212 */             if (var5 != 0L) {
/* 175:214 */               var2 = var5;
/* 176:    */             }
/* 177:    */           }
/* 178:    */           catch (NumberFormatException var7)
/* 179:    */           {
/* 180:219 */             var2 = var4.hashCode();
/* 181:    */           }
/* 182:    */         }
/* 183:223 */         WorldSettings.GameType var8 = WorldSettings.GameType.getByName(this.field_146342_r);
/* 184:224 */         WorldSettings var6 = new WorldSettings(var2, var8, this.field_146341_s, this.field_146337_w, WorldType.worldTypes[this.field_146331_K]);
/* 185:225 */         var6.func_82750_a(this.field_146334_a);
/* 186:227 */         if ((this.field_146338_v) && (!this.field_146337_w)) {
/* 187:229 */           var6.enableBonusChest();
/* 188:    */         }
/* 189:232 */         if ((this.field_146340_t) && (!this.field_146337_w)) {
/* 190:234 */           var6.enableCommands();
/* 191:    */         }
/* 192:237 */         this.mc.launchIntegratedServer(this.field_146336_i, this.field_146333_g.getText().trim(), var6);
/* 193:    */       }
/* 194:239 */       else if (p_146284_1_.id == 3)
/* 195:    */       {
/* 196:241 */         func_146315_i();
/* 197:    */       }
/* 198:243 */       else if (p_146284_1_.id == 2)
/* 199:    */       {
/* 200:245 */         if (this.field_146342_r.equals("survival"))
/* 201:    */         {
/* 202:247 */           if (!this.field_146339_u) {
/* 203:249 */             this.field_146340_t = false;
/* 204:    */           }
/* 205:252 */           this.field_146337_w = false;
/* 206:253 */           this.field_146342_r = "hardcore";
/* 207:254 */           this.field_146337_w = true;
/* 208:255 */           this.field_146321_E.enabled = false;
/* 209:256 */           this.field_146326_C.enabled = false;
/* 210:257 */           func_146319_h();
/* 211:    */         }
/* 212:259 */         else if (this.field_146342_r.equals("hardcore"))
/* 213:    */         {
/* 214:261 */           if (!this.field_146339_u) {
/* 215:263 */             this.field_146340_t = true;
/* 216:    */           }
/* 217:266 */           this.field_146337_w = false;
/* 218:267 */           this.field_146342_r = "creative";
/* 219:268 */           func_146319_h();
/* 220:269 */           this.field_146337_w = false;
/* 221:270 */           this.field_146321_E.enabled = true;
/* 222:271 */           this.field_146326_C.enabled = true;
/* 223:    */         }
/* 224:    */         else
/* 225:    */         {
/* 226:275 */           if (!this.field_146339_u) {
/* 227:277 */             this.field_146340_t = false;
/* 228:    */           }
/* 229:280 */           this.field_146342_r = "survival";
/* 230:281 */           func_146319_h();
/* 231:282 */           this.field_146321_E.enabled = true;
/* 232:283 */           this.field_146326_C.enabled = true;
/* 233:284 */           this.field_146337_w = false;
/* 234:    */         }
/* 235:287 */         func_146319_h();
/* 236:    */       }
/* 237:289 */       else if (p_146284_1_.id == 4)
/* 238:    */       {
/* 239:291 */         this.field_146341_s = (!this.field_146341_s);
/* 240:292 */         func_146319_h();
/* 241:    */       }
/* 242:294 */       else if (p_146284_1_.id == 7)
/* 243:    */       {
/* 244:296 */         this.field_146338_v = (!this.field_146338_v);
/* 245:297 */         func_146319_h();
/* 246:    */       }
/* 247:299 */       else if (p_146284_1_.id == 5)
/* 248:    */       {
/* 249:301 */         this.field_146331_K += 1;
/* 250:303 */         if (this.field_146331_K >= WorldType.worldTypes.length) {
/* 251:305 */           this.field_146331_K = 0;
/* 252:    */         }
/* 253:308 */         while ((WorldType.worldTypes[this.field_146331_K] == null) || (!WorldType.worldTypes[this.field_146331_K].getCanBeCreated()))
/* 254:    */         {
/* 255:310 */           this.field_146331_K += 1;
/* 256:312 */           if (this.field_146331_K >= WorldType.worldTypes.length) {
/* 257:314 */             this.field_146331_K = 0;
/* 258:    */           }
/* 259:    */         }
/* 260:318 */         this.field_146334_a = "";
/* 261:319 */         func_146319_h();
/* 262:320 */         func_146316_a(this.field_146344_y);
/* 263:    */       }
/* 264:322 */       else if (p_146284_1_.id == 6)
/* 265:    */       {
/* 266:324 */         this.field_146339_u = true;
/* 267:325 */         this.field_146340_t = (!this.field_146340_t);
/* 268:326 */         func_146319_h();
/* 269:    */       }
/* 270:328 */       else if (p_146284_1_.id == 8)
/* 271:    */       {
/* 272:330 */         this.mc.displayGuiScreen(new GuiCreateFlatWorld(this, this.field_146334_a));
/* 273:    */       }
/* 274:    */     }
/* 275:    */   }
/* 276:    */   
/* 277:    */   private void func_146315_i()
/* 278:    */   {
/* 279:337 */     func_146316_a(!this.field_146344_y);
/* 280:    */   }
/* 281:    */   
/* 282:    */   private void func_146316_a(boolean p_146316_1_)
/* 283:    */   {
/* 284:342 */     this.field_146344_y = p_146316_1_;
/* 285:343 */     this.field_146343_z.field_146125_m = (!this.field_146344_y);
/* 286:344 */     this.field_146325_B.field_146125_m = this.field_146344_y;
/* 287:345 */     this.field_146326_C.field_146125_m = this.field_146344_y;
/* 288:346 */     this.field_146320_D.field_146125_m = this.field_146344_y;
/* 289:347 */     this.field_146321_E.field_146125_m = this.field_146344_y;
/* 290:348 */     this.field_146322_F.field_146125_m = ((this.field_146344_y) && (WorldType.worldTypes[this.field_146331_K] == WorldType.FLAT));
/* 291:350 */     if (this.field_146344_y) {
/* 292:352 */       this.field_146324_A.displayString = I18n.format("gui.done", new Object[0]);
/* 293:    */     } else {
/* 294:356 */       this.field_146324_A.displayString = I18n.format("selectWorld.moreWorldOptions", new Object[0]);
/* 295:    */     }
/* 296:    */   }
/* 297:    */   
/* 298:    */   protected void keyTyped(char par1, int par2)
/* 299:    */   {
/* 300:365 */     if ((this.field_146333_g.isFocused()) && (!this.field_146344_y))
/* 301:    */     {
/* 302:367 */       this.field_146333_g.textboxKeyTyped(par1, par2);
/* 303:368 */       this.field_146330_J = this.field_146333_g.getText();
/* 304:    */     }
/* 305:370 */     else if ((this.field_146335_h.isFocused()) && (this.field_146344_y))
/* 306:    */     {
/* 307:372 */       this.field_146335_h.textboxKeyTyped(par1, par2);
/* 308:373 */       this.field_146329_I = this.field_146335_h.getText();
/* 309:    */     }
/* 310:376 */     if ((par2 == 28) || (par2 == 156)) {
/* 311:378 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/* 312:    */     }
/* 313:381 */     ((NodusGuiButton)this.buttonList.get(0)).enabled = (this.field_146333_g.getText().length() > 0);
/* 314:382 */     func_146314_g();
/* 315:    */   }
/* 316:    */   
/* 317:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 318:    */   {
/* 319:390 */     super.mouseClicked(par1, par2, par3);
/* 320:392 */     if (this.field_146344_y) {
/* 321:394 */       this.field_146335_h.mouseClicked(par1, par2, par3);
/* 322:    */     } else {
/* 323:398 */       this.field_146333_g.mouseClicked(par1, par2, par3);
/* 324:    */     }
/* 325:    */   }
/* 326:    */   
/* 327:    */   public void drawScreen(int par1, int par2, float par3)
/* 328:    */   {
/* 329:407 */     drawDefaultBackground();
/* 330:408 */     drawCenteredString(this.fontRendererObj, I18n.format("selectWorld.create", new Object[0]), width / 2, 20, -1);
/* 331:410 */     if (this.field_146344_y)
/* 332:    */     {
/* 333:412 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterSeed", new Object[0]), width / 2 - 100, 47, -6250336);
/* 334:413 */       drawString(this.fontRendererObj, I18n.format("selectWorld.seedInfo", new Object[0]), width / 2 - 100, 85, -6250336);
/* 335:414 */       drawString(this.fontRendererObj, I18n.format("selectWorld.mapFeatures.info", new Object[0]), width / 2 - 150, 122, -6250336);
/* 336:415 */       drawString(this.fontRendererObj, I18n.format("selectWorld.allowCommands.info", new Object[0]), width / 2 - 150, 172, -6250336);
/* 337:416 */       this.field_146335_h.drawTextBox();
/* 338:418 */       if (WorldType.worldTypes[this.field_146331_K].func_151357_h()) {
/* 339:420 */         this.fontRendererObj.drawSplitString(I18n.format(WorldType.worldTypes[this.field_146331_K].func_151359_c(), new Object[0]), this.field_146320_D.xPosition + 2, this.field_146320_D.yPosition + 22, this.field_146320_D.func_146117_b(), 10526880);
/* 340:    */       }
/* 341:    */     }
/* 342:    */     else
/* 343:    */     {
/* 344:425 */       drawString(this.fontRendererObj, I18n.format("selectWorld.enterName", new Object[0]), width / 2 - 100, 47, -6250336);
/* 345:426 */       drawString(this.fontRendererObj, I18n.format("selectWorld.resultFolder", new Object[0]) + " " + this.field_146336_i, width / 2 - 100, 85, -6250336);
/* 346:427 */       this.field_146333_g.drawTextBox();
/* 347:428 */       drawString(this.fontRendererObj, this.field_146323_G, width / 2 - 100, 137, -6250336);
/* 348:429 */       drawString(this.fontRendererObj, this.field_146328_H, width / 2 - 100, 149, -6250336);
/* 349:    */     }
/* 350:432 */     super.drawScreen(par1, par2, par3);
/* 351:    */   }
/* 352:    */   
/* 353:    */   public void func_146318_a(WorldInfo p_146318_1_)
/* 354:    */   {
/* 355:437 */     this.field_146330_J = I18n.format("selectWorld.newWorld.copyOf", new Object[] { p_146318_1_.getWorldName() });
/* 356:438 */     this.field_146329_I = p_146318_1_.getSeed();
/* 357:439 */     this.field_146331_K = p_146318_1_.getTerrainType().getWorldTypeID();
/* 358:440 */     this.field_146334_a = p_146318_1_.getGeneratorOptions();
/* 359:441 */     this.field_146341_s = p_146318_1_.isMapFeaturesEnabled();
/* 360:442 */     this.field_146340_t = p_146318_1_.areCommandsAllowed();
/* 361:444 */     if (p_146318_1_.isHardcoreModeEnabled()) {
/* 362:446 */       this.field_146342_r = "hardcore";
/* 363:448 */     } else if (p_146318_1_.getGameType().isSurvivalOrAdventure()) {
/* 364:450 */       this.field_146342_r = "survival";
/* 365:452 */     } else if (p_146318_1_.getGameType().isCreative()) {
/* 366:454 */       this.field_146342_r = "creative";
/* 367:    */     }
/* 368:    */   }
/* 369:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiCreateWorld
 * JD-Core Version:    0.7.0.1
 */