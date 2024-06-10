/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import com.google.common.collect.Lists;
/*   4:    */ import com.google.common.util.concurrent.ThreadFactoryBuilder;
/*   5:    */ import java.io.IOException;
/*   6:    */ import java.net.UnknownHostException;
/*   7:    */ import java.util.Iterator;
/*   8:    */ import java.util.List;
/*   9:    */ import java.util.concurrent.ScheduledThreadPoolExecutor;
/*  10:    */ import java.util.concurrent.ThreadPoolExecutor;
/*  11:    */ import java.util.concurrent.atomic.AtomicInteger;
/*  12:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*  13:    */ import net.minecraft.client.Minecraft;
/*  14:    */ import net.minecraft.client.gui.mco.GuiScreenCreateOnlineWorld;
/*  15:    */ import net.minecraft.client.gui.mco.GuiScreenPendingInvitation;
/*  16:    */ import net.minecraft.client.mco.ExceptionMcoService;
/*  17:    */ import net.minecraft.client.mco.McoClient;
/*  18:    */ import net.minecraft.client.mco.McoServer;
/*  19:    */ import net.minecraft.client.mco.McoServer.State;
/*  20:    */ import net.minecraft.client.mco.McoServerList;
/*  21:    */ import net.minecraft.client.renderer.Tessellator;
/*  22:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  23:    */ import net.minecraft.client.resources.I18n;
/*  24:    */ import net.minecraft.client.settings.GameSettings;
/*  25:    */ import net.minecraft.util.MathHelper;
/*  26:    */ import net.minecraft.util.ResourceLocation;
/*  27:    */ import net.minecraft.util.Session;
/*  28:    */ import org.apache.logging.log4j.LogManager;
/*  29:    */ import org.apache.logging.log4j.Logger;
/*  30:    */ import org.lwjgl.input.Keyboard;
/*  31:    */ import org.lwjgl.opengl.GL11;
/*  32:    */ 
/*  33:    */ public class GuiScreenOnlineServers
/*  34:    */   extends GuiScreen
/*  35:    */ {
/*  36: 35 */   private static final AtomicInteger field_146701_a = new AtomicInteger(0);
/*  37: 36 */   private static final Logger logger = LogManager.getLogger();
/*  38: 37 */   private static final ResourceLocation field_146697_g = new ResourceLocation("textures/gui/widgets.png");
/*  39: 38 */   private static final ResourceLocation field_146702_h = new ResourceLocation("textures/gui/title/minecraft.png");
/*  40: 39 */   private static McoServerList field_146703_i = new McoServerList();
/*  41: 40 */   private static GuiScreenRealmsPinger field_146709_r = new GuiScreenRealmsPinger();
/*  42: 41 */   private static final ThreadPoolExecutor field_146708_s = new ScheduledThreadPoolExecutor(5, new ThreadFactoryBuilder().setNameFormat("Server Pinger #%d").setDaemon(true).build());
/*  43:    */   private GuiScreen field_146707_t;
/*  44:    */   private OnlineServerList field_146706_u;
/*  45: 44 */   private long field_146705_v = -1L;
/*  46:    */   private NodusGuiButton field_146704_w;
/*  47:    */   private NodusGuiButton field_146712_x;
/*  48:    */   private GuiButtonLink field_146711_y;
/*  49:    */   private NodusGuiButton field_146710_z;
/*  50:    */   private String field_146698_A;
/*  51:    */   private boolean field_146699_B;
/*  52: 51 */   private List field_146700_C = Lists.newArrayList();
/*  53: 52 */   private volatile int field_146694_D = 0;
/*  54:    */   private int field_146696_E;
/*  55:    */   private static final String __OBFID = "CL_00000792";
/*  56:    */   
/*  57:    */   public GuiScreenOnlineServers(GuiScreen par1GuiScreen)
/*  58:    */   {
/*  59: 58 */     this.field_146707_t = par1GuiScreen;
/*  60:    */   }
/*  61:    */   
/*  62:    */   public void initGui()
/*  63:    */   {
/*  64: 66 */     Keyboard.enableRepeatEvents(true);
/*  65: 67 */     this.buttonList.clear();
/*  66: 68 */     field_146703_i.func_148475_a(this.mc.getSession());
/*  67: 70 */     if (!this.field_146699_B)
/*  68:    */     {
/*  69: 72 */       this.field_146699_B = true;
/*  70: 73 */       this.field_146706_u = new OnlineServerList();
/*  71:    */     }
/*  72:    */     else
/*  73:    */     {
/*  74: 77 */       this.field_146706_u.func_148346_a(width, height, 32, height - 64);
/*  75:    */     }
/*  76: 80 */     func_146688_g();
/*  77:    */   }
/*  78:    */   
/*  79:    */   public void func_146688_g()
/*  80:    */   {
/*  81: 85 */     this.buttonList.add(this.field_146710_z = new NodusGuiButton(1, width / 2 - 154, height - 52, 100, 20, I18n.format("mco.selectServer.play", new Object[0])));
/*  82: 86 */     String var1 = this.field_146694_D > 0 ? I18n.format("mco.selectServer.create", new Object[0]) : I18n.format("mco.selectServer.buy", new Object[0]);
/*  83: 87 */     this.buttonList.add(this.field_146712_x = new NodusGuiButton(2, width / 2 - 48, height - 52, 100, 20, var1));
/*  84: 88 */     this.buttonList.add(this.field_146704_w = new NodusGuiButton(3, width / 2 + 58, height - 52, 100, 20, I18n.format("mco.selectServer.configure", new Object[0])));
/*  85: 89 */     this.buttonList.add(this.field_146711_y = new GuiButtonLink(4, width / 2 - 154, height - 28, 154, 20, I18n.format("mco.selectServer.moreinfo", new Object[0])));
/*  86: 90 */     this.buttonList.add(new NodusGuiButton(0, width / 2 + 6, height - 28, 153, 20, I18n.format("gui.cancel", new Object[0])));
/*  87: 91 */     McoServer var2 = func_146691_a(this.field_146705_v);
/*  88: 92 */     this.field_146710_z.enabled = ((var2 != null) && (var2.field_148808_d.equals(McoServer.State.OPEN.name())) && (!var2.field_148819_h));
/*  89: 93 */     this.field_146704_w.enabled = ((var2 != null) && (!var2.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name())));
/*  90: 95 */     if ((var2 != null) && (!var2.field_148809_e.equals(this.mc.getSession().getUsername()))) {
/*  91: 97 */       this.field_146704_w.displayString = I18n.format("mco.selectServer.leave", new Object[0]);
/*  92:    */     }
/*  93:    */   }
/*  94:    */   
/*  95:    */   public void updateScreen()
/*  96:    */   {
/*  97:106 */     super.updateScreen();
/*  98:107 */     this.field_146696_E += 1;
/*  99:109 */     if (field_146703_i.func_148472_a())
/* 100:    */     {
/* 101:111 */       List var1 = field_146703_i.func_148473_c();
/* 102:112 */       Iterator var2 = var1.iterator();
/* 103:114 */       while (var2.hasNext())
/* 104:    */       {
/* 105:116 */         McoServer var3 = (McoServer)var2.next();
/* 106:117 */         Iterator var4 = this.field_146700_C.iterator();
/* 107:119 */         while (var4.hasNext())
/* 108:    */         {
/* 109:121 */           McoServer var5 = (McoServer)var4.next();
/* 110:123 */           if (var3.field_148812_a == var5.field_148812_a)
/* 111:    */           {
/* 112:125 */             var3.func_148799_a(var5);
/* 113:126 */             break;
/* 114:    */           }
/* 115:    */         }
/* 116:    */       }
/* 117:131 */       this.field_146694_D = field_146703_i.func_148469_e();
/* 118:132 */       this.field_146700_C = var1;
/* 119:133 */       field_146703_i.func_148479_b();
/* 120:    */     }
/* 121:136 */     this.field_146712_x.displayString = (this.field_146694_D > 0 ? I18n.format("mco.selectServer.create", new Object[0]) : I18n.format("mco.selectServer.buy", new Object[0]));
/* 122:    */   }
/* 123:    */   
/* 124:    */   public void onGuiClosed()
/* 125:    */   {
/* 126:144 */     Keyboard.enableRepeatEvents(false);
/* 127:    */   }
/* 128:    */   
/* 129:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 130:    */   {
/* 131:149 */     if (p_146284_1_.enabled) {
/* 132:151 */       if (p_146284_1_.id == 1)
/* 133:    */       {
/* 134:153 */         func_146656_d(this.field_146705_v);
/* 135:    */       }
/* 136:155 */       else if (p_146284_1_.id == 3)
/* 137:    */       {
/* 138:157 */         func_146667_u();
/* 139:    */       }
/* 140:159 */       else if (p_146284_1_.id == 0)
/* 141:    */       {
/* 142:161 */         func_146669_s();
/* 143:162 */         this.mc.displayGuiScreen(this.field_146707_t);
/* 144:    */       }
/* 145:164 */       else if (p_146284_1_.id == 2)
/* 146:    */       {
/* 147:166 */         func_146669_s();
/* 148:167 */         this.mc.displayGuiScreen(this.field_146694_D > 0 ? new GuiScreenCreateOnlineWorld(this) : new GuiScreenBuyRealms(this));
/* 149:    */       }
/* 150:169 */       else if (p_146284_1_.id == 4)
/* 151:    */       {
/* 152:171 */         func_146660_t();
/* 153:    */       }
/* 154:    */       else
/* 155:    */       {
/* 156:175 */         this.field_146706_u.func_148357_a(p_146284_1_);
/* 157:    */       }
/* 158:    */     }
/* 159:    */   }
/* 160:    */   
/* 161:    */   private void func_146669_s()
/* 162:    */   {
/* 163:182 */     field_146703_i.func_148476_f();
/* 164:183 */     field_146709_r.func_148507_b();
/* 165:    */   }
/* 166:    */   
/* 167:    */   private void func_146660_t()
/* 168:    */   {
/* 169:188 */     String var1 = I18n.format("mco.more.info.question.line1", new Object[0]);
/* 170:189 */     String var2 = I18n.format("mco.more.info.question.line2", new Object[0]);
/* 171:190 */     this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var1, var2, 4));
/* 172:    */   }
/* 173:    */   
/* 174:    */   private void func_146667_u()
/* 175:    */   {
/* 176:195 */     McoServer var1 = func_146691_a(this.field_146705_v);
/* 177:197 */     if (var1 != null) {
/* 178:199 */       if (this.mc.getSession().getUsername().equals(var1.field_148809_e))
/* 179:    */       {
/* 180:201 */         McoServer var2 = func_146677_c(var1.field_148812_a);
/* 181:203 */         if (var2 != null)
/* 182:    */         {
/* 183:205 */           func_146669_s();
/* 184:206 */           this.mc.displayGuiScreen(new GuiScreenConfigureWorld(this, var2));
/* 185:    */         }
/* 186:    */       }
/* 187:    */       else
/* 188:    */       {
/* 189:211 */         String var4 = I18n.format("mco.configure.world.leave.question.line1", new Object[0]);
/* 190:212 */         String var3 = I18n.format("mco.configure.world.leave.question.line2", new Object[0]);
/* 191:213 */         this.mc.displayGuiScreen(new GuiScreenConfirmation(this, GuiScreenConfirmation.ConfirmationType.Info, var4, var3, 3));
/* 192:    */       }
/* 193:    */     }
/* 194:    */   }
/* 195:    */   
/* 196:    */   private McoServer func_146691_a(long p_146691_1_)
/* 197:    */   {
/* 198:220 */     Iterator var3 = this.field_146700_C.iterator();
/* 199:    */     McoServer var4;
/* 200:    */     do
/* 201:    */     {
/* 202:225 */       if (!var3.hasNext()) {
/* 203:227 */         return null;
/* 204:    */       }
/* 205:230 */       var4 = (McoServer)var3.next();
/* 206:232 */     } while (var4.field_148812_a != p_146691_1_);
/* 207:234 */     return var4;
/* 208:    */   }
/* 209:    */   
/* 210:    */   private int func_146672_b(long p_146672_1_)
/* 211:    */   {
/* 212:239 */     for (int var3 = 0; var3 < this.field_146700_C.size(); var3++) {
/* 213:241 */       if (((McoServer)this.field_146700_C.get(var3)).field_148812_a == p_146672_1_) {
/* 214:243 */         return var3;
/* 215:    */       }
/* 216:    */     }
/* 217:247 */     return -1;
/* 218:    */   }
/* 219:    */   
/* 220:    */   public void confirmClicked(boolean par1, int par2)
/* 221:    */   {
/* 222:252 */     if ((par2 == 3) && (par1)) {
/* 223:280 */       new Thread("MCO Configure Requester #" + field_146701_a.incrementAndGet())
/* 224:    */       {
/* 225:    */         private static final String __OBFID = "CL_00000793";
/* 226:    */         
/* 227:    */         public void run()
/* 228:    */         {
/* 229:    */           try
/* 230:    */           {
/* 231:261 */             McoServer var1 = GuiScreenOnlineServers.this.func_146691_a(GuiScreenOnlineServers.this.field_146705_v);
/* 232:263 */             if (var1 != null)
/* 233:    */             {
/* 234:265 */               Session var2 = GuiScreenOnlineServers.this.mc.getSession();
/* 235:266 */               McoClient var3 = new McoClient(var2.getSessionID(), var2.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 236:267 */               GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
/* 237:268 */               GuiScreenOnlineServers.this.field_146700_C.remove(var1);
/* 238:269 */               var3.func_148698_c(var1.field_148812_a);
/* 239:270 */               GuiScreenOnlineServers.field_146703_i.func_148470_a(var1);
/* 240:271 */               GuiScreenOnlineServers.this.field_146700_C.remove(var1);
/* 241:272 */               GuiScreenOnlineServers.this.func_146685_v();
/* 242:    */             }
/* 243:    */           }
/* 244:    */           catch (ExceptionMcoService var4)
/* 245:    */           {
/* 246:277 */             GuiScreenOnlineServers.logger.error("Couldn't configure world");
/* 247:    */           }
/* 248:    */         }
/* 249:    */       }.start();
/* 250:282 */     } else if ((par2 == 4) && (par1)) {
/* 251:284 */       this.field_146711_y.func_146138_a("http://realms.minecraft.net/");
/* 252:    */     }
/* 253:287 */     this.mc.displayGuiScreen(this);
/* 254:    */   }
/* 255:    */   
/* 256:    */   private void func_146685_v()
/* 257:    */   {
/* 258:292 */     int var1 = func_146672_b(this.field_146705_v);
/* 259:294 */     if (this.field_146700_C.size() - 1 == var1) {
/* 260:296 */       var1--;
/* 261:    */     }
/* 262:299 */     if (this.field_146700_C.size() == 0) {
/* 263:301 */       var1 = -1;
/* 264:    */     }
/* 265:304 */     if ((var1 >= 0) && (var1 < this.field_146700_C.size())) {
/* 266:306 */       this.field_146705_v = ((McoServer)this.field_146700_C.get(var1)).field_148812_a;
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   public void func_146670_h()
/* 271:    */   {
/* 272:312 */     this.field_146705_v = -1L;
/* 273:    */   }
/* 274:    */   
/* 275:    */   private McoServer func_146677_c(long p_146677_1_)
/* 276:    */   {
/* 277:317 */     Session var3 = this.mc.getSession();
/* 278:318 */     McoClient var4 = new McoClient(var3.getSessionID(), var3.getUsername(), "1.7.2", Minecraft.getMinecraft().getProxy());
/* 279:    */     try
/* 280:    */     {
/* 281:322 */       return var4.func_148709_a(p_146677_1_);
/* 282:    */     }
/* 283:    */     catch (ExceptionMcoService var6)
/* 284:    */     {
/* 285:326 */       logger.error("Couldn't get own world");
/* 286:    */     }
/* 287:    */     catch (IOException var7)
/* 288:    */     {
/* 289:330 */       logger.error("Couldn't parse response getting own world");
/* 290:    */     }
/* 291:333 */     return null;
/* 292:    */   }
/* 293:    */   
/* 294:    */   protected void keyTyped(char par1, int par2)
/* 295:    */   {
/* 296:341 */     if (par2 == 59)
/* 297:    */     {
/* 298:343 */       this.mc.gameSettings.hideServerAddress = (!this.mc.gameSettings.hideServerAddress);
/* 299:344 */       this.mc.gameSettings.saveOptions();
/* 300:    */     }
/* 301:348 */     else if ((par2 != 28) && (par2 != 156))
/* 302:    */     {
/* 303:350 */       super.keyTyped(par1, par2);
/* 304:    */     }
/* 305:    */     else
/* 306:    */     {
/* 307:354 */       actionPerformed((NodusGuiButton)this.buttonList.get(0));
/* 308:    */     }
/* 309:    */   }
/* 310:    */   
/* 311:    */   public void drawScreen(int par1, int par2, float par3)
/* 312:    */   {
/* 313:364 */     this.field_146698_A = null;
/* 314:365 */     drawDefaultBackground();
/* 315:366 */     this.field_146706_u.func_148350_a(par1, par2, par3);
/* 316:367 */     func_146665_b(width / 2 - 50, 7);
/* 317:368 */     super.drawScreen(par1, par2, par3);
/* 318:370 */     if (this.field_146698_A != null) {
/* 319:372 */       func_146658_b(this.field_146698_A, par1, par2);
/* 320:    */     }
/* 321:375 */     func_146659_c(par1, par2);
/* 322:    */   }
/* 323:    */   
/* 324:    */   private void func_146665_b(int p_146665_1_, int p_146665_2_)
/* 325:    */   {
/* 326:380 */     this.mc.getTextureManager().bindTexture(field_146702_h);
/* 327:381 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 328:382 */     GL11.glPushMatrix();
/* 329:383 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 330:384 */     drawTexturedModalRect(p_146665_1_ * 2, p_146665_2_ * 2, 0, 97, 200, 50);
/* 331:385 */     GL11.glPopMatrix();
/* 332:    */   }
/* 333:    */   
/* 334:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 335:    */   {
/* 336:393 */     super.mouseClicked(par1, par2, par3);
/* 337:395 */     if ((func_146662_d(par1, par2)) && (field_146703_i.func_148468_d() != 0))
/* 338:    */     {
/* 339:397 */       func_146669_s();
/* 340:398 */       GuiScreenPendingInvitation var4 = new GuiScreenPendingInvitation(this.field_146707_t);
/* 341:399 */       this.mc.displayGuiScreen(var4);
/* 342:    */     }
/* 343:    */   }
/* 344:    */   
/* 345:    */   private void func_146659_c(int p_146659_1_, int p_146659_2_)
/* 346:    */   {
/* 347:405 */     int var3 = field_146703_i.func_148468_d();
/* 348:406 */     boolean var4 = func_146662_d(p_146659_1_, p_146659_2_);
/* 349:407 */     this.mc.getTextureManager().bindTexture(field_146697_g);
/* 350:408 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 351:409 */     GL11.glPushMatrix();
/* 352:410 */     drawTexturedModalRect(width / 2 + 58, 12, var4 ? 166 : 182, 22, 16, 16);
/* 353:411 */     GL11.glPopMatrix();
/* 354:415 */     if (var3 != 0)
/* 355:    */     {
/* 356:417 */       int var5 = 198 + (Math.min(var3, 6) - 1) * 8;
/* 357:418 */       int var6 = (int)(Math.max(0.0F, Math.max(MathHelper.sin((10 + this.field_146696_E) * 0.57F), MathHelper.cos(this.field_146696_E * 0.35F))) * -6.0F);
/* 358:419 */       this.mc.getTextureManager().bindTexture(field_146697_g);
/* 359:420 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 360:421 */       GL11.glPushMatrix();
/* 361:422 */       drawTexturedModalRect(width / 2 + 58 + 4, 16 + var6, var5, 22, 8, 8);
/* 362:423 */       GL11.glPopMatrix();
/* 363:    */     }
/* 364:426 */     if (var4)
/* 365:    */     {
/* 366:428 */       int var5 = p_146659_1_ + 12;
/* 367:429 */       int var6 = p_146659_2_ - 12;
/* 368:430 */       String var7 = "";
/* 369:432 */       if (var3 != 0) {
/* 370:434 */         var7 = I18n.format("mco.invites.pending", new Object[0]);
/* 371:    */       } else {
/* 372:438 */         var7 = I18n.format("mco.invites.nopending", new Object[0]);
/* 373:    */       }
/* 374:441 */       int var8 = this.fontRendererObj.getStringWidth(var7);
/* 375:442 */       drawGradientRect(var5 - 3, var6 - 3, var5 + var8 + 3, var6 + 8 + 3, -1073741824, -1073741824);
/* 376:443 */       this.fontRendererObj.drawStringWithShadow(var7, var5, var6, -1);
/* 377:    */     }
/* 378:    */   }
/* 379:    */   
/* 380:    */   private boolean func_146662_d(int p_146662_1_, int p_146662_2_)
/* 381:    */   {
/* 382:449 */     int var3 = width / 2 + 56;
/* 383:450 */     int var4 = width / 2 + 78;
/* 384:451 */     byte var5 = 13;
/* 385:452 */     byte var6 = 27;
/* 386:453 */     return (var3 <= p_146662_1_) && (p_146662_1_ <= var4) && (var5 <= p_146662_2_) && (p_146662_2_ <= var6);
/* 387:    */   }
/* 388:    */   
/* 389:    */   private void func_146656_d(long p_146656_1_)
/* 390:    */   {
/* 391:458 */     McoServer var3 = func_146691_a(p_146656_1_);
/* 392:460 */     if (var3 != null)
/* 393:    */     {
/* 394:462 */       func_146669_s();
/* 395:463 */       GuiScreenLongRunningTask var4 = new GuiScreenLongRunningTask(this.mc, this, new TaskOnlineConnect(this, var3));
/* 396:464 */       var4.func_146902_g();
/* 397:465 */       this.mc.displayGuiScreen(var4);
/* 398:    */     }
/* 399:    */   }
/* 400:    */   
/* 401:    */   private void func_146661_c(int p_146661_1_, int p_146661_2_, int p_146661_3_, int p_146661_4_)
/* 402:    */   {
/* 403:471 */     this.mc.getTextureManager().bindTexture(field_146697_g);
/* 404:472 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 405:473 */     GL11.glPushMatrix();
/* 406:474 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 407:475 */     drawTexturedModalRect(p_146661_1_ * 2, p_146661_2_ * 2, 191, 0, 16, 15);
/* 408:476 */     GL11.glPopMatrix();
/* 409:478 */     if ((p_146661_3_ >= p_146661_1_) && (p_146661_3_ <= p_146661_1_ + 9) && (p_146661_4_ >= p_146661_2_) && (p_146661_4_ <= p_146661_2_ + 9)) {
/* 410:480 */       this.field_146698_A = I18n.format("mco.selectServer.expired", new Object[0]);
/* 411:    */     }
/* 412:    */   }
/* 413:    */   
/* 414:    */   private void func_146687_b(int p_146687_1_, int p_146687_2_, int p_146687_3_, int p_146687_4_, int p_146687_5_)
/* 415:    */   {
/* 416:486 */     if (this.field_146696_E % 20 < 10)
/* 417:    */     {
/* 418:488 */       this.mc.getTextureManager().bindTexture(field_146697_g);
/* 419:489 */       GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 420:490 */       GL11.glPushMatrix();
/* 421:491 */       GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 422:492 */       drawTexturedModalRect(p_146687_1_ * 2, p_146687_2_ * 2, 207, 0, 16, 15);
/* 423:493 */       GL11.glPopMatrix();
/* 424:    */     }
/* 425:496 */     if ((p_146687_3_ >= p_146687_1_) && (p_146687_3_ <= p_146687_1_ + 9) && (p_146687_4_ >= p_146687_2_) && (p_146687_4_ <= p_146687_2_ + 9)) {
/* 426:498 */       if (p_146687_5_ == 0) {
/* 427:500 */         this.field_146698_A = I18n.format("mco.selectServer.expires.soon", new Object[0]);
/* 428:502 */       } else if (p_146687_5_ == 1) {
/* 429:504 */         this.field_146698_A = I18n.format("mco.selectServer.expires.day", new Object[0]);
/* 430:    */       } else {
/* 431:508 */         this.field_146698_A = I18n.format("mco.selectServer.expires.days", new Object[] { Integer.valueOf(p_146687_5_) });
/* 432:    */       }
/* 433:    */     }
/* 434:    */   }
/* 435:    */   
/* 436:    */   private void func_146683_d(int p_146683_1_, int p_146683_2_, int p_146683_3_, int p_146683_4_)
/* 437:    */   {
/* 438:515 */     this.mc.getTextureManager().bindTexture(field_146697_g);
/* 439:516 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 440:517 */     GL11.glPushMatrix();
/* 441:518 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 442:519 */     drawTexturedModalRect(p_146683_1_ * 2, p_146683_2_ * 2, 207, 0, 16, 15);
/* 443:520 */     GL11.glPopMatrix();
/* 444:522 */     if ((p_146683_3_ >= p_146683_1_) && (p_146683_3_ <= p_146683_1_ + 9) && (p_146683_4_ >= p_146683_2_) && (p_146683_4_ <= p_146683_2_ + 9)) {
/* 445:524 */       this.field_146698_A = I18n.format("mco.selectServer.open", new Object[0]);
/* 446:    */     }
/* 447:    */   }
/* 448:    */   
/* 449:    */   private void func_146671_e(int p_146671_1_, int p_146671_2_, int p_146671_3_, int p_146671_4_)
/* 450:    */   {
/* 451:530 */     this.mc.getTextureManager().bindTexture(field_146697_g);
/* 452:531 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 453:532 */     GL11.glPushMatrix();
/* 454:533 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 455:534 */     drawTexturedModalRect(p_146671_1_ * 2, p_146671_2_ * 2, 223, 0, 16, 15);
/* 456:535 */     GL11.glPopMatrix();
/* 457:537 */     if ((p_146671_3_ >= p_146671_1_) && (p_146671_3_ <= p_146671_1_ + 9) && (p_146671_4_ >= p_146671_2_) && (p_146671_4_ <= p_146671_2_ + 9)) {
/* 458:539 */       this.field_146698_A = I18n.format("mco.selectServer.closed", new Object[0]);
/* 459:    */     }
/* 460:    */   }
/* 461:    */   
/* 462:    */   private void func_146666_f(int p_146666_1_, int p_146666_2_, int p_146666_3_, int p_146666_4_)
/* 463:    */   {
/* 464:545 */     this.mc.getTextureManager().bindTexture(field_146697_g);
/* 465:546 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 466:547 */     GL11.glPushMatrix();
/* 467:548 */     GL11.glScalef(0.5F, 0.5F, 0.5F);
/* 468:549 */     drawTexturedModalRect(p_146666_1_ * 2, p_146666_2_ * 2, 223, 0, 16, 15);
/* 469:550 */     GL11.glPopMatrix();
/* 470:552 */     if ((p_146666_3_ >= p_146666_1_) && (p_146666_3_ <= p_146666_1_ + 9) && (p_146666_4_ >= p_146666_2_) && (p_146666_4_ <= p_146666_2_ + 9)) {
/* 471:554 */       this.field_146698_A = I18n.format("mco.selectServer.locked", new Object[0]);
/* 472:    */     }
/* 473:    */   }
/* 474:    */   
/* 475:    */   protected void func_146658_b(String p_146658_1_, int p_146658_2_, int p_146658_3_)
/* 476:    */   {
/* 477:560 */     if (p_146658_1_ != null)
/* 478:    */     {
/* 479:562 */       int var4 = p_146658_2_ + 12;
/* 480:563 */       int var5 = p_146658_3_ - 12;
/* 481:564 */       int var6 = this.fontRendererObj.getStringWidth(p_146658_1_);
/* 482:565 */       drawGradientRect(var4 - 3, var5 - 3, var4 + var6 + 3, var5 + 8 + 3, -1073741824, -1073741824);
/* 483:566 */       this.fontRendererObj.drawStringWithShadow(p_146658_1_, var4, var5, -1);
/* 484:    */     }
/* 485:    */   }
/* 486:    */   
/* 487:    */   class OnlineServerList
/* 488:    */     extends GuiScreenSelectLocation
/* 489:    */   {
/* 490:    */     private static final String __OBFID = "CL_00000794";
/* 491:    */     
/* 492:    */     public OnlineServerList()
/* 493:    */     {
/* 494:576 */       super(GuiScreenOnlineServers.width, GuiScreenOnlineServers.height, 32, GuiScreenOnlineServers.height - 64, 36);
/* 495:    */     }
/* 496:    */     
/* 497:    */     protected int func_148355_a()
/* 498:    */     {
/* 499:581 */       return GuiScreenOnlineServers.this.field_146700_C.size() + 1;
/* 500:    */     }
/* 501:    */     
/* 502:    */     protected void func_148352_a(int p_148352_1_, boolean p_148352_2_)
/* 503:    */     {
/* 504:586 */       if (p_148352_1_ < GuiScreenOnlineServers.this.field_146700_C.size())
/* 505:    */       {
/* 506:588 */         McoServer var3 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148352_1_);
/* 507:589 */         GuiScreenOnlineServers.this.field_146705_v = var3.field_148812_a;
/* 508:591 */         if (!GuiScreenOnlineServers.this.mc.getSession().getUsername().equals(var3.field_148809_e)) {
/* 509:593 */           GuiScreenOnlineServers.this.field_146704_w.displayString = I18n.format("mco.selectServer.leave", new Object[0]);
/* 510:    */         } else {
/* 511:597 */           GuiScreenOnlineServers.this.field_146704_w.displayString = I18n.format("mco.selectServer.configure", new Object[0]);
/* 512:    */         }
/* 513:600 */         GuiScreenOnlineServers.this.field_146704_w.enabled = (!var3.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name()));
/* 514:601 */         GuiScreenOnlineServers.this.field_146710_z.enabled = ((var3.field_148808_d.equals(McoServer.State.OPEN.name())) && (!var3.field_148819_h));
/* 515:603 */         if ((p_148352_2_) && (GuiScreenOnlineServers.this.field_146710_z.enabled)) {
/* 516:605 */           GuiScreenOnlineServers.this.func_146656_d(GuiScreenOnlineServers.this.field_146705_v);
/* 517:    */         }
/* 518:    */       }
/* 519:    */     }
/* 520:    */     
/* 521:    */     protected boolean func_148356_a(int p_148356_1_)
/* 522:    */     {
/* 523:612 */       return p_148356_1_ == GuiScreenOnlineServers.this.func_146672_b(GuiScreenOnlineServers.this.field_146705_v);
/* 524:    */     }
/* 525:    */     
/* 526:    */     protected boolean func_148349_b(int p_148349_1_)
/* 527:    */     {
/* 528:    */       try
/* 529:    */       {
/* 530:619 */         return (p_148349_1_ >= 0) && (p_148349_1_ < GuiScreenOnlineServers.this.field_146700_C.size()) && (((McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148349_1_)).field_148809_e.toLowerCase().equals(GuiScreenOnlineServers.this.mc.getSession().getUsername()));
/* 531:    */       }
/* 532:    */       catch (Exception var3) {}
/* 533:623 */       return false;
/* 534:    */     }
/* 535:    */     
/* 536:    */     protected int func_148351_b()
/* 537:    */     {
/* 538:629 */       return func_148355_a() * 36;
/* 539:    */     }
/* 540:    */     
/* 541:    */     protected void func_148358_c()
/* 542:    */     {
/* 543:634 */       GuiScreenOnlineServers.this.drawDefaultBackground();
/* 544:    */     }
/* 545:    */     
/* 546:    */     protected void func_148348_a(int p_148348_1_, int p_148348_2_, int p_148348_3_, int p_148348_4_, Tessellator p_148348_5_)
/* 547:    */     {
/* 548:639 */       if (p_148348_1_ < GuiScreenOnlineServers.this.field_146700_C.size()) {
/* 549:641 */         func_148390_b(p_148348_1_, p_148348_2_, p_148348_3_, p_148348_4_, p_148348_5_);
/* 550:    */       }
/* 551:    */     }
/* 552:    */     
/* 553:    */     private void func_148390_b(int p_148390_1_, int p_148390_2_, int p_148390_3_, int p_148390_4_, Tessellator p_148390_5_)
/* 554:    */     {
/* 555:647 */       final McoServer var6 = (McoServer)GuiScreenOnlineServers.this.field_146700_C.get(p_148390_1_);
/* 556:649 */       if (!var6.field_148814_o)
/* 557:    */       {
/* 558:651 */         var6.field_148814_o = true;
/* 559:652 */         GuiScreenOnlineServers.field_146708_s.submit(new Runnable()
/* 560:    */         {
/* 561:    */           private static final String __OBFID = "CL_00000795";
/* 562:    */           
/* 563:    */           public void run()
/* 564:    */           {
/* 565:    */             try
/* 566:    */             {
/* 567:659 */               GuiScreenOnlineServers.field_146709_r.func_148506_a(var6);
/* 568:    */             }
/* 569:    */             catch (UnknownHostException var2)
/* 570:    */             {
/* 571:663 */               GuiScreenOnlineServers.logger.error("Pinger: Could not resolve host");
/* 572:    */             }
/* 573:    */           }
/* 574:    */         });
/* 575:    */       }
/* 576:669 */       GuiScreenOnlineServers.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.func_148801_b(), p_148390_2_ + 2, p_148390_3_ + 1, 16777215);
/* 577:670 */       short var7 = 207;
/* 578:671 */       byte var8 = 1;
/* 579:673 */       if (var6.field_148819_h)
/* 580:    */       {
/* 581:675 */         GuiScreenOnlineServers.this.func_146661_c(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
/* 582:    */       }
/* 583:677 */       else if (var6.field_148808_d.equals(McoServer.State.CLOSED.name()))
/* 584:    */       {
/* 585:679 */         GuiScreenOnlineServers.this.func_146671_e(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
/* 586:    */       }
/* 587:681 */       else if ((var6.field_148809_e.equals(GuiScreenOnlineServers.this.mc.getSession().getUsername())) && (var6.field_148818_k < 7))
/* 588:    */       {
/* 589:683 */         func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
/* 590:684 */         GuiScreenOnlineServers.this.func_146687_b(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f, var6.field_148818_k);
/* 591:    */       }
/* 592:686 */       else if (var6.field_148808_d.equals(McoServer.State.OPEN.name()))
/* 593:    */       {
/* 594:688 */         GuiScreenOnlineServers.this.func_146683_d(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
/* 595:689 */         func_148389_a(p_148390_1_, p_148390_2_ - 14, p_148390_3_, var6);
/* 596:    */       }
/* 597:691 */       else if (var6.field_148808_d.equals(McoServer.State.ADMIN_LOCK.name()))
/* 598:    */       {
/* 599:693 */         GuiScreenOnlineServers.this.func_146666_f(p_148390_2_ + var7, p_148390_3_ + var8, this.field_148365_e, this.field_148362_f);
/* 600:    */       }
/* 601:696 */       GuiScreenOnlineServers.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.field_148813_n, p_148390_2_ + 200 - GuiScreenOnlineServers.this.fontRendererObj.getStringWidth(var6.field_148813_n), p_148390_3_ + 1, 8421504);
/* 602:697 */       GuiScreenOnlineServers.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.func_148800_a(), p_148390_2_ + 2, p_148390_3_ + 12, 7105644);
/* 603:698 */       GuiScreenOnlineServers.drawString(GuiScreenOnlineServers.this.fontRendererObj, var6.field_148809_e, p_148390_2_ + 2, p_148390_3_ + 12 + 11, 5000268);
/* 604:    */     }
/* 605:    */     
/* 606:    */     private void func_148389_a(int p_148389_1_, int p_148389_2_, int p_148389_3_, McoServer p_148389_4_)
/* 607:    */     {
/* 608:703 */       if (p_148389_4_.field_148807_g != null)
/* 609:    */       {
/* 610:705 */         if (p_148389_4_.field_148816_m != null) {
/* 611:707 */           GuiScreenOnlineServers.drawString(GuiScreenOnlineServers.this.fontRendererObj, p_148389_4_.field_148816_m, p_148389_2_ + 215 - GuiScreenOnlineServers.this.fontRendererObj.getStringWidth(p_148389_4_.field_148816_m), p_148389_3_ + 1, 8421504);
/* 612:    */         }
/* 613:710 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 614:711 */         GuiScreenOnlineServers.this.mc.getTextureManager().bindTexture(Gui.icons);
/* 615:    */       }
/* 616:    */     }
/* 617:    */   }
/* 618:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenOnlineServers
 * JD-Core Version:    0.7.0.1
 */