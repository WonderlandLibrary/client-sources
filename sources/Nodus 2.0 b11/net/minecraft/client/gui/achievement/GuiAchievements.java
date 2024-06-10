/*   1:    */ package net.minecraft.client.gui.achievement;
/*   2:    */ 
/*   3:    */ import java.util.List;
/*   4:    */ import java.util.Random;
/*   5:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   6:    */ import net.minecraft.block.Block;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.gui.FontRenderer;
/*   9:    */ import net.minecraft.client.gui.GuiOptionButton;
/*  10:    */ import net.minecraft.client.gui.GuiScreen;
/*  11:    */ import net.minecraft.client.gui.IProgressMeter;
/*  12:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*  13:    */ import net.minecraft.client.renderer.RenderHelper;
/*  14:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  16:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  17:    */ import net.minecraft.client.resources.I18n;
/*  18:    */ import net.minecraft.client.settings.GameSettings;
/*  19:    */ import net.minecraft.client.settings.KeyBinding;
/*  20:    */ import net.minecraft.init.Blocks;
/*  21:    */ import net.minecraft.network.play.client.C16PacketClientStatus;
/*  22:    */ import net.minecraft.network.play.client.C16PacketClientStatus.EnumState;
/*  23:    */ import net.minecraft.stats.Achievement;
/*  24:    */ import net.minecraft.stats.AchievementList;
/*  25:    */ import net.minecraft.stats.StatFileWriter;
/*  26:    */ import net.minecraft.util.ChatComponentTranslation;
/*  27:    */ import net.minecraft.util.IChatComponent;
/*  28:    */ import net.minecraft.util.IIcon;
/*  29:    */ import net.minecraft.util.MathHelper;
/*  30:    */ import net.minecraft.util.ResourceLocation;
/*  31:    */ import net.minecraft.util.Session;
/*  32:    */ import org.lwjgl.input.Mouse;
/*  33:    */ import org.lwjgl.opengl.GL11;
/*  34:    */ 
/*  35:    */ public class GuiAchievements
/*  36:    */   extends GuiScreen
/*  37:    */   implements IProgressMeter
/*  38:    */ {
/*  39: 30 */   private static final int field_146572_y = AchievementList.minDisplayColumn * 24 - 112;
/*  40: 31 */   private static final int field_146571_z = AchievementList.minDisplayRow * 24 - 112;
/*  41: 32 */   private static final int field_146559_A = AchievementList.maxDisplayColumn * 24 - 77;
/*  42: 33 */   private static final int field_146560_B = AchievementList.maxDisplayRow * 24 - 77;
/*  43: 34 */   private static final ResourceLocation field_146561_C = new ResourceLocation("textures/gui/achievement/achievement_background.png");
/*  44:    */   protected GuiScreen field_146562_a;
/*  45: 36 */   protected int field_146555_f = 256;
/*  46: 37 */   protected int field_146557_g = 202;
/*  47:    */   protected int field_146563_h;
/*  48:    */   protected int field_146564_i;
/*  49: 40 */   protected float field_146570_r = 1.0F;
/*  50:    */   protected double field_146569_s;
/*  51:    */   protected double field_146568_t;
/*  52:    */   protected double field_146567_u;
/*  53:    */   protected double field_146566_v;
/*  54:    */   protected double field_146565_w;
/*  55:    */   protected double field_146573_x;
/*  56:    */   private int field_146554_D;
/*  57:    */   private StatFileWriter field_146556_E;
/*  58: 49 */   private boolean field_146558_F = true;
/*  59:    */   private static final String __OBFID = "CL_00000722";
/*  60:    */   
/*  61:    */   public GuiAchievements(GuiScreen p_i45026_1_, StatFileWriter p_i45026_2_)
/*  62:    */   {
/*  63: 54 */     this.field_146562_a = p_i45026_1_;
/*  64: 55 */     this.field_146556_E = p_i45026_2_;
/*  65: 56 */     short var3 = 141;
/*  66: 57 */     short var4 = 141;
/*  67: 58 */     this.field_146569_s = (this.field_146567_u = this.field_146565_w = AchievementList.openInventory.displayColumn * 24 - var3 / 2 - 12);
/*  68: 59 */     this.field_146568_t = (this.field_146566_v = this.field_146573_x = AchievementList.openInventory.displayRow * 24 - var4 / 2);
/*  69:    */   }
/*  70:    */   
/*  71:    */   public void initGui()
/*  72:    */   {
/*  73: 67 */     this.mc.getNetHandler().addToSendQueue(new C16PacketClientStatus(C16PacketClientStatus.EnumState.REQUEST_STATS));
/*  74: 68 */     this.buttonList.clear();
/*  75: 69 */     this.buttonList.add(new GuiOptionButton(1, width / 2 + 24, height / 2 + 74, 80, 20, I18n.format("gui.done", new Object[0])));
/*  76:    */   }
/*  77:    */   
/*  78:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/*  79:    */   {
/*  80: 74 */     if (!this.field_146558_F) {
/*  81: 76 */       if (p_146284_1_.id == 1) {
/*  82: 78 */         this.mc.displayGuiScreen(this.field_146562_a);
/*  83:    */       }
/*  84:    */     }
/*  85:    */   }
/*  86:    */   
/*  87:    */   protected void keyTyped(char par1, int par2)
/*  88:    */   {
/*  89: 88 */     if (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())
/*  90:    */     {
/*  91: 90 */       this.mc.displayGuiScreen(null);
/*  92: 91 */       this.mc.setIngameFocus();
/*  93:    */     }
/*  94:    */     else
/*  95:    */     {
/*  96: 95 */       super.keyTyped(par1, par2);
/*  97:    */     }
/*  98:    */   }
/*  99:    */   
/* 100:    */   public void drawScreen(int par1, int par2, float par3)
/* 101:    */   {
/* 102:104 */     if (this.field_146558_F)
/* 103:    */     {
/* 104:106 */       drawDefaultBackground();
/* 105:107 */       drawCenteredString(this.fontRendererObj, I18n.format("multiplayer.downloadingStats", new Object[0]), width / 2, height / 2, 16777215);
/* 106:108 */       drawCenteredString(this.fontRendererObj, field_146510_b_[((int)(Minecraft.getSystemTime() / 150L % field_146510_b_.length))], width / 2, height / 2 + this.fontRendererObj.FONT_HEIGHT * 2, 16777215);
/* 107:    */     }
/* 108:    */     else
/* 109:    */     {
/* 110:114 */       if (Mouse.isButtonDown(0))
/* 111:    */       {
/* 112:116 */         int var4 = (width - this.field_146555_f) / 2;
/* 113:117 */         int var5 = (height - this.field_146557_g) / 2;
/* 114:118 */         int var6 = var4 + 8;
/* 115:119 */         int var7 = var5 + 17;
/* 116:121 */         if (((this.field_146554_D == 0) || (this.field_146554_D == 1)) && (par1 >= var6) && (par1 < var6 + 224) && (par2 >= var7) && (par2 < var7 + 155))
/* 117:    */         {
/* 118:123 */           if (this.field_146554_D == 0)
/* 119:    */           {
/* 120:125 */             this.field_146554_D = 1;
/* 121:    */           }
/* 122:    */           else
/* 123:    */           {
/* 124:129 */             this.field_146567_u -= (par1 - this.field_146563_h) * this.field_146570_r;
/* 125:130 */             this.field_146566_v -= (par2 - this.field_146564_i) * this.field_146570_r;
/* 126:131 */             this.field_146565_w = (this.field_146569_s = this.field_146567_u);
/* 127:132 */             this.field_146573_x = (this.field_146568_t = this.field_146566_v);
/* 128:    */           }
/* 129:135 */           this.field_146563_h = par1;
/* 130:136 */           this.field_146564_i = par2;
/* 131:    */         }
/* 132:    */       }
/* 133:    */       else
/* 134:    */       {
/* 135:141 */         this.field_146554_D = 0;
/* 136:    */       }
/* 137:144 */       int var4 = Mouse.getDWheel();
/* 138:145 */       float var11 = this.field_146570_r;
/* 139:147 */       if (var4 < 0) {
/* 140:149 */         this.field_146570_r += 0.25F;
/* 141:151 */       } else if (var4 > 0) {
/* 142:153 */         this.field_146570_r -= 0.25F;
/* 143:    */       }
/* 144:156 */       this.field_146570_r = MathHelper.clamp_float(this.field_146570_r, 1.0F, 2.0F);
/* 145:158 */       if (this.field_146570_r != var11)
/* 146:    */       {
/* 147:160 */         float var10000 = var11 - this.field_146570_r;
/* 148:161 */         float var12 = var11 * this.field_146555_f;
/* 149:162 */         float var8 = var11 * this.field_146557_g;
/* 150:163 */         float var9 = this.field_146570_r * this.field_146555_f;
/* 151:164 */         float var10 = this.field_146570_r * this.field_146557_g;
/* 152:165 */         this.field_146567_u -= (var9 - var12) * 0.5F;
/* 153:166 */         this.field_146566_v -= (var10 - var8) * 0.5F;
/* 154:167 */         this.field_146565_w = (this.field_146569_s = this.field_146567_u);
/* 155:168 */         this.field_146573_x = (this.field_146568_t = this.field_146566_v);
/* 156:    */       }
/* 157:171 */       if (this.field_146565_w < field_146572_y) {
/* 158:173 */         this.field_146565_w = field_146572_y;
/* 159:    */       }
/* 160:176 */       if (this.field_146573_x < field_146571_z) {
/* 161:178 */         this.field_146573_x = field_146571_z;
/* 162:    */       }
/* 163:181 */       if (this.field_146565_w >= field_146559_A) {
/* 164:183 */         this.field_146565_w = (field_146559_A - 1);
/* 165:    */       }
/* 166:186 */       if (this.field_146573_x >= field_146560_B) {
/* 167:188 */         this.field_146573_x = (field_146560_B - 1);
/* 168:    */       }
/* 169:191 */       drawDefaultBackground();
/* 170:192 */       func_146552_b(par1, par2, par3);
/* 171:193 */       GL11.glDisable(2896);
/* 172:194 */       GL11.glDisable(2929);
/* 173:195 */       func_146553_h();
/* 174:196 */       GL11.glEnable(2896);
/* 175:197 */       GL11.glEnable(2929);
/* 176:    */     }
/* 177:    */   }
/* 178:    */   
/* 179:    */   public void func_146509_g()
/* 180:    */   {
/* 181:203 */     if (this.field_146558_F) {
/* 182:205 */       this.field_146558_F = false;
/* 183:    */     }
/* 184:    */   }
/* 185:    */   
/* 186:    */   public void updateScreen()
/* 187:    */   {
/* 188:214 */     if (!this.field_146558_F)
/* 189:    */     {
/* 190:216 */       this.field_146569_s = this.field_146567_u;
/* 191:217 */       this.field_146568_t = this.field_146566_v;
/* 192:218 */       double var1 = this.field_146565_w - this.field_146567_u;
/* 193:219 */       double var3 = this.field_146573_x - this.field_146566_v;
/* 194:221 */       if (var1 * var1 + var3 * var3 < 4.0D)
/* 195:    */       {
/* 196:223 */         this.field_146567_u += var1;
/* 197:224 */         this.field_146566_v += var3;
/* 198:    */       }
/* 199:    */       else
/* 200:    */       {
/* 201:228 */         this.field_146567_u += var1 * 0.85D;
/* 202:229 */         this.field_146566_v += var3 * 0.85D;
/* 203:    */       }
/* 204:    */     }
/* 205:    */   }
/* 206:    */   
/* 207:    */   protected void func_146553_h()
/* 208:    */   {
/* 209:236 */     int var1 = (width - this.field_146555_f) / 2;
/* 210:237 */     int var2 = (height - this.field_146557_g) / 2;
/* 211:238 */     this.fontRendererObj.drawString("Achievements", var1 + 15, var2 + 5, 4210752);
/* 212:    */   }
/* 213:    */   
/* 214:    */   protected void func_146552_b(int p_146552_1_, int p_146552_2_, float p_146552_3_)
/* 215:    */   {
/* 216:243 */     int var4 = MathHelper.floor_double(this.field_146569_s + (this.field_146567_u - this.field_146569_s) * p_146552_3_);
/* 217:244 */     int var5 = MathHelper.floor_double(this.field_146568_t + (this.field_146566_v - this.field_146568_t) * p_146552_3_);
/* 218:246 */     if (var4 < field_146572_y) {
/* 219:248 */       var4 = field_146572_y;
/* 220:    */     }
/* 221:251 */     if (var5 < field_146571_z) {
/* 222:253 */       var5 = field_146571_z;
/* 223:    */     }
/* 224:256 */     if (var4 >= field_146559_A) {
/* 225:258 */       var4 = field_146559_A - 1;
/* 226:    */     }
/* 227:261 */     if (var5 >= field_146560_B) {
/* 228:263 */       var5 = field_146560_B - 1;
/* 229:    */     }
/* 230:266 */     int var6 = (width - this.field_146555_f) / 2;
/* 231:267 */     int var7 = (height - this.field_146557_g) / 2;
/* 232:268 */     int var8 = var6 + 16;
/* 233:269 */     int var9 = var7 + 17;
/* 234:270 */     zLevel = 0.0F;
/* 235:271 */     GL11.glDepthFunc(518);
/* 236:272 */     GL11.glPushMatrix();
/* 237:273 */     GL11.glTranslatef(var8, var9, -200.0F);
/* 238:274 */     GL11.glScalef(1.0F / this.field_146570_r, 1.0F / this.field_146570_r, 0.0F);
/* 239:275 */     GL11.glEnable(3553);
/* 240:276 */     GL11.glDisable(2896);
/* 241:277 */     GL11.glEnable(32826);
/* 242:278 */     GL11.glEnable(2903);
/* 243:279 */     int var10 = var4 + 288 >> 4;
/* 244:280 */     int var11 = var5 + 288 >> 4;
/* 245:281 */     int var12 = (var4 + 288) % 16;
/* 246:282 */     int var13 = (var5 + 288) % 16;
/* 247:283 */     boolean var14 = true;
/* 248:284 */     boolean var15 = true;
/* 249:285 */     boolean var16 = true;
/* 250:286 */     boolean var17 = true;
/* 251:287 */     boolean var18 = true;
/* 252:288 */     Random var19 = new Random();
/* 253:289 */     float var20 = 16.0F / this.field_146570_r;
/* 254:290 */     float var21 = 16.0F / this.field_146570_r;
/* 255:295 */     for (int var22 = 0; var22 * var20 - var13 < 155.0F; var22++)
/* 256:    */     {
/* 257:297 */       float var23 = 0.6F - (var11 + var22) / 25.0F * 0.3F;
/* 258:298 */       GL11.glColor4f(var23, var23, var23, 1.0F);
/* 259:300 */       for (int var24 = 0; var24 * var21 - var12 < 224.0F; var24++)
/* 260:    */       {
/* 261:302 */         var19.setSeed(this.mc.getSession().getPlayerID().hashCode() + var10 + var24 + (var11 + var22) * 16);
/* 262:303 */         int var25 = var19.nextInt(1 + var11 + var22) + (var11 + var22) / 2;
/* 263:304 */         IIcon var26 = Blocks.sand.getIcon(0, 0);
/* 264:306 */         if ((var25 <= 37) && (var11 + var22 != 35))
/* 265:    */         {
/* 266:308 */           if (var25 == 22)
/* 267:    */           {
/* 268:310 */             if (var19.nextInt(2) == 0) {
/* 269:312 */               var26 = Blocks.diamond_ore.getIcon(0, 0);
/* 270:    */             } else {
/* 271:316 */               var26 = Blocks.redstone_ore.getIcon(0, 0);
/* 272:    */             }
/* 273:    */           }
/* 274:319 */           else if (var25 == 10) {
/* 275:321 */             var26 = Blocks.iron_ore.getIcon(0, 0);
/* 276:323 */           } else if (var25 == 8) {
/* 277:325 */             var26 = Blocks.coal_ore.getIcon(0, 0);
/* 278:327 */           } else if (var25 > 4) {
/* 279:329 */             var26 = Blocks.stone.getIcon(0, 0);
/* 280:331 */           } else if (var25 > 0) {
/* 281:333 */             var26 = Blocks.dirt.getIcon(0, 0);
/* 282:    */           }
/* 283:    */         }
/* 284:    */         else {
/* 285:338 */           var26 = Blocks.bedrock.getIcon(0, 0);
/* 286:    */         }
/* 287:341 */         this.mc.getTextureManager().bindTexture(TextureMap.locationBlocksTexture);
/* 288:342 */         drawTexturedModelRectFromIcon(var24 * 16 - var12, var22 * 16 - var13, var26, 16, 16);
/* 289:    */       }
/* 290:    */     }
/* 291:346 */     GL11.glEnable(2929);
/* 292:347 */     GL11.glDepthFunc(515);
/* 293:348 */     this.mc.getTextureManager().bindTexture(field_146561_C);
/* 294:353 */     for (var22 = 0; var22 < AchievementList.achievementList.size(); var22++)
/* 295:    */     {
/* 296:355 */       Achievement var35 = (Achievement)AchievementList.achievementList.get(var22);
/* 297:357 */       if (var35.parentAchievement != null)
/* 298:    */       {
/* 299:359 */         int var24 = var35.displayColumn * 24 - var4 + 11;
/* 300:360 */         int var25 = var35.displayRow * 24 - var5 + 11;
/* 301:361 */         int var43 = var35.parentAchievement.displayColumn * 24 - var4 + 11;
/* 302:362 */         int var27 = var35.parentAchievement.displayRow * 24 - var5 + 11;
/* 303:363 */         boolean var28 = this.field_146556_E.hasAchievementUnlocked(var35);
/* 304:364 */         boolean var29 = this.field_146556_E.canUnlockAchievement(var35);
/* 305:365 */         int var30 = this.field_146556_E.func_150874_c(var35);
/* 306:367 */         if (var30 <= 4)
/* 307:    */         {
/* 308:369 */           int var31 = -16777216;
/* 309:371 */           if (var28) {
/* 310:373 */             var31 = -6250336;
/* 311:375 */           } else if (var29) {
/* 312:377 */             var31 = -16711936;
/* 313:    */           }
/* 314:380 */           drawHorizontalLine(var24, var43, var25, var31);
/* 315:381 */           drawVerticalLine(var43, var25, var27, var31);
/* 316:383 */           if (var24 > var43) {
/* 317:385 */             drawTexturedModalRect(var24 - 11 - 7, var25 - 5, 114, 234, 7, 11);
/* 318:387 */           } else if (var24 < var43) {
/* 319:389 */             drawTexturedModalRect(var24 + 11, var25 - 5, 107, 234, 7, 11);
/* 320:391 */           } else if (var25 > var27) {
/* 321:393 */             drawTexturedModalRect(var24 - 5, var25 - 11 - 7, 96, 234, 11, 7);
/* 322:395 */           } else if (var25 < var27) {
/* 323:397 */             drawTexturedModalRect(var24 - 5, var25 + 11, 96, 241, 11, 7);
/* 324:    */           }
/* 325:    */         }
/* 326:    */       }
/* 327:    */     }
/* 328:403 */     Achievement var34 = null;
/* 329:404 */     RenderItem var38 = new RenderItem();
/* 330:405 */     float var36 = (p_146552_1_ - var8) * this.field_146570_r;
/* 331:406 */     float var37 = (p_146552_2_ - var9) * this.field_146570_r;
/* 332:407 */     RenderHelper.enableGUIStandardItemLighting();
/* 333:408 */     GL11.glDisable(2896);
/* 334:409 */     GL11.glEnable(32826);
/* 335:410 */     GL11.glEnable(2903);
/* 336:414 */     for (int var43 = 0; var43 < AchievementList.achievementList.size(); var43++)
/* 337:    */     {
/* 338:416 */       Achievement var41 = (Achievement)AchievementList.achievementList.get(var43);
/* 339:417 */       int var40 = var41.displayColumn * 24 - var4;
/* 340:418 */       int var42 = var41.displayRow * 24 - var5;
/* 341:420 */       if ((var40 >= -24) && (var42 >= -24) && (var40 <= 224.0F * this.field_146570_r) && (var42 <= 155.0F * this.field_146570_r))
/* 342:    */       {
/* 343:422 */         int var30 = this.field_146556_E.func_150874_c(var41);
/* 344:    */         float var45;
/* 345:425 */         if (this.field_146556_E.hasAchievementUnlocked(var41))
/* 346:    */         {
/* 347:427 */           float var45 = 0.75F;
/* 348:428 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 349:    */         }
/* 350:430 */         else if (this.field_146556_E.canUnlockAchievement(var41))
/* 351:    */         {
/* 352:432 */           float var45 = 1.0F;
/* 353:433 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 354:    */         }
/* 355:435 */         else if (var30 < 3)
/* 356:    */         {
/* 357:437 */           float var45 = 0.3F;
/* 358:438 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 359:    */         }
/* 360:440 */         else if (var30 == 3)
/* 361:    */         {
/* 362:442 */           float var45 = 0.2F;
/* 363:443 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 364:    */         }
/* 365:    */         else
/* 366:    */         {
/* 367:447 */           if (var30 != 4) {
/* 368:    */             continue;
/* 369:    */           }
/* 370:452 */           var45 = 0.1F;
/* 371:453 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 372:    */         }
/* 373:456 */         this.mc.getTextureManager().bindTexture(field_146561_C);
/* 374:458 */         if (var41.getSpecial()) {
/* 375:460 */           drawTexturedModalRect(var40 - 2, var42 - 2, 26, 202, 26, 26);
/* 376:    */         } else {
/* 377:464 */           drawTexturedModalRect(var40 - 2, var42 - 2, 0, 202, 26, 26);
/* 378:    */         }
/* 379:467 */         if (!this.field_146556_E.canUnlockAchievement(var41))
/* 380:    */         {
/* 381:469 */           var45 = 0.1F;
/* 382:470 */           GL11.glColor4f(var45, var45, var45, 1.0F);
/* 383:471 */           var38.renderWithColor = false;
/* 384:    */         }
/* 385:474 */         GL11.glEnable(2896);
/* 386:475 */         GL11.glEnable(2884);
/* 387:476 */         var38.renderItemAndEffectIntoGUI(this.mc.fontRenderer, this.mc.getTextureManager(), var41.theItemStack, var40 + 3, var42 + 3);
/* 388:477 */         GL11.glBlendFunc(770, 771);
/* 389:478 */         GL11.glDisable(2896);
/* 390:480 */         if (!this.field_146556_E.canUnlockAchievement(var41)) {
/* 391:482 */           var38.renderWithColor = true;
/* 392:    */         }
/* 393:485 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 394:487 */         if ((var36 >= var40) && (var36 <= var40 + 22) && (var37 >= var42) && (var37 <= var42 + 22)) {
/* 395:489 */           var34 = var41;
/* 396:    */         }
/* 397:    */       }
/* 398:    */     }
/* 399:494 */     GL11.glDisable(2929);
/* 400:495 */     GL11.glEnable(3042);
/* 401:496 */     GL11.glPopMatrix();
/* 402:497 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 403:498 */     this.mc.getTextureManager().bindTexture(field_146561_C);
/* 404:499 */     drawTexturedModalRect(var6, var7, 0, 0, this.field_146555_f, this.field_146557_g);
/* 405:500 */     zLevel = 0.0F;
/* 406:501 */     GL11.glDepthFunc(515);
/* 407:502 */     GL11.glDisable(2929);
/* 408:503 */     GL11.glEnable(3553);
/* 409:504 */     super.drawScreen(p_146552_1_, p_146552_2_, p_146552_3_);
/* 410:506 */     if (var34 != null)
/* 411:    */     {
/* 412:508 */       String var44 = var34.func_150951_e().getUnformattedText();
/* 413:509 */       String var39 = var34.getDescription();
/* 414:510 */       int var40 = p_146552_1_ + 12;
/* 415:511 */       int var42 = p_146552_2_ - 4;
/* 416:512 */       int var30 = this.field_146556_E.func_150874_c(var34);
/* 417:514 */       if (!this.field_146556_E.canUnlockAchievement(var34))
/* 418:    */       {
/* 419:519 */         if (var30 == 3)
/* 420:    */         {
/* 421:521 */           var44 = I18n.format("achievement.unknown", new Object[0]);
/* 422:522 */           int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
/* 423:523 */           String var32 = new ChatComponentTranslation("achievement.requires", new Object[] { var34.parentAchievement.func_150951_e() }).getUnformattedText();
/* 424:524 */           int var33 = this.fontRendererObj.splitStringWidth(var32, var31);
/* 425:525 */           drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var33 + 12 + 3, -1073741824, -1073741824);
/* 426:526 */           this.fontRendererObj.drawSplitString(var32, var40, var42 + 12, var31, -9416624);
/* 427:    */         }
/* 428:528 */         else if (var30 < 3)
/* 429:    */         {
/* 430:530 */           int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
/* 431:531 */           String var32 = new ChatComponentTranslation("achievement.requires", new Object[] { var34.parentAchievement.func_150951_e() }).getUnformattedText();
/* 432:532 */           int var33 = this.fontRendererObj.splitStringWidth(var32, var31);
/* 433:533 */           drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var33 + 12 + 3, -1073741824, -1073741824);
/* 434:534 */           this.fontRendererObj.drawSplitString(var32, var40, var42 + 12, var31, -9416624);
/* 435:    */         }
/* 436:    */         else
/* 437:    */         {
/* 438:538 */           var44 = null;
/* 439:    */         }
/* 440:    */       }
/* 441:    */       else
/* 442:    */       {
/* 443:543 */         int var31 = Math.max(this.fontRendererObj.getStringWidth(var44), 120);
/* 444:544 */         int var46 = this.fontRendererObj.splitStringWidth(var39, var31);
/* 445:546 */         if (this.field_146556_E.hasAchievementUnlocked(var34)) {
/* 446:548 */           var46 += 12;
/* 447:    */         }
/* 448:551 */         drawGradientRect(var40 - 3, var42 - 3, var40 + var31 + 3, var42 + var46 + 3 + 12, -1073741824, -1073741824);
/* 449:552 */         this.fontRendererObj.drawSplitString(var39, var40, var42 + 12, var31, -6250336);
/* 450:554 */         if (this.field_146556_E.hasAchievementUnlocked(var34)) {
/* 451:556 */           this.fontRendererObj.drawStringWithShadow(I18n.format("achievement.taken", new Object[0]), var40, var42 + var46 + 4, -7302913);
/* 452:    */         }
/* 453:    */       }
/* 454:560 */       if (var44 != null) {
/* 455:562 */         this.fontRendererObj.drawStringWithShadow(var44, var40, var42, var34.getSpecial() ? -8355776 : this.field_146556_E.canUnlockAchievement(var34) ? -1 : var34.getSpecial() ? -128 : -8355712);
/* 456:    */       }
/* 457:    */     }
/* 458:566 */     GL11.glEnable(2929);
/* 459:567 */     GL11.glEnable(2896);
/* 460:568 */     RenderHelper.disableStandardItemLighting();
/* 461:    */   }
/* 462:    */   
/* 463:    */   public boolean doesGuiPauseGame()
/* 464:    */   {
/* 465:576 */     return !this.field_146558_F;
/* 466:    */   }
/* 467:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.achievement.GuiAchievements
 * JD-Core Version:    0.7.0.1
 */