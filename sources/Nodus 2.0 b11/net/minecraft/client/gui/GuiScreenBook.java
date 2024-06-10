/*   1:    */ package net.minecraft.client.gui;
/*   2:    */ 
/*   3:    */ import io.netty.buffer.ByteBuf;
/*   4:    */ import io.netty.buffer.Unpooled;
/*   5:    */ import java.util.List;
/*   6:    */ import me.connorm.Nodus.ui.NodusGuiButton;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*   9:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  10:    */ import net.minecraft.client.resources.I18n;
/*  11:    */ import net.minecraft.entity.player.EntityPlayer;
/*  12:    */ import net.minecraft.init.Items;
/*  13:    */ import net.minecraft.item.ItemStack;
/*  14:    */ import net.minecraft.nbt.NBTTagCompound;
/*  15:    */ import net.minecraft.nbt.NBTTagList;
/*  16:    */ import net.minecraft.nbt.NBTTagString;
/*  17:    */ import net.minecraft.network.PacketBuffer;
/*  18:    */ import net.minecraft.network.play.client.C17PacketCustomPayload;
/*  19:    */ import net.minecraft.util.ChatAllowedCharacters;
/*  20:    */ import net.minecraft.util.EnumChatFormatting;
/*  21:    */ import net.minecraft.util.ResourceLocation;
/*  22:    */ import org.apache.logging.log4j.LogManager;
/*  23:    */ import org.apache.logging.log4j.Logger;
/*  24:    */ import org.lwjgl.input.Keyboard;
/*  25:    */ import org.lwjgl.opengl.GL11;
/*  26:    */ 
/*  27:    */ public class GuiScreenBook
/*  28:    */   extends GuiScreen
/*  29:    */ {
/*  30: 27 */   private static final Logger logger = ;
/*  31: 28 */   private static final ResourceLocation field_146466_f = new ResourceLocation("textures/gui/book.png");
/*  32:    */   private final EntityPlayer field_146468_g;
/*  33:    */   private final ItemStack field_146474_h;
/*  34:    */   private final boolean field_146475_i;
/*  35:    */   private boolean field_146481_r;
/*  36:    */   private boolean field_146480_s;
/*  37:    */   private int field_146479_t;
/*  38: 35 */   private int field_146478_u = 192;
/*  39: 36 */   private int field_146477_v = 192;
/*  40: 37 */   private int field_146476_w = 1;
/*  41:    */   private int field_146484_x;
/*  42:    */   private NBTTagList field_146483_y;
/*  43: 40 */   private String field_146482_z = "";
/*  44:    */   private NextPageButton field_146470_A;
/*  45:    */   private NextPageButton field_146471_B;
/*  46:    */   private NodusGuiButton field_146472_C;
/*  47:    */   private NodusGuiButton field_146465_D;
/*  48:    */   private NodusGuiButton field_146467_E;
/*  49:    */   private NodusGuiButton field_146469_F;
/*  50:    */   private static final String __OBFID = "CL_00000744";
/*  51:    */   
/*  52:    */   public GuiScreenBook(EntityPlayer par1EntityPlayer, ItemStack par2ItemStack, boolean par3)
/*  53:    */   {
/*  54: 51 */     this.field_146468_g = par1EntityPlayer;
/*  55: 52 */     this.field_146474_h = par2ItemStack;
/*  56: 53 */     this.field_146475_i = par3;
/*  57: 55 */     if (par2ItemStack.hasTagCompound())
/*  58:    */     {
/*  59: 57 */       NBTTagCompound var4 = par2ItemStack.getTagCompound();
/*  60: 58 */       this.field_146483_y = var4.getTagList("pages", 8);
/*  61: 60 */       if (this.field_146483_y != null)
/*  62:    */       {
/*  63: 62 */         this.field_146483_y = ((NBTTagList)this.field_146483_y.copy());
/*  64: 63 */         this.field_146476_w = this.field_146483_y.tagCount();
/*  65: 65 */         if (this.field_146476_w < 1) {
/*  66: 67 */           this.field_146476_w = 1;
/*  67:    */         }
/*  68:    */       }
/*  69:    */     }
/*  70: 72 */     if ((this.field_146483_y == null) && (par3))
/*  71:    */     {
/*  72: 74 */       this.field_146483_y = new NBTTagList();
/*  73: 75 */       this.field_146483_y.appendTag(new NBTTagString(""));
/*  74: 76 */       this.field_146476_w = 1;
/*  75:    */     }
/*  76:    */   }
/*  77:    */   
/*  78:    */   public void updateScreen()
/*  79:    */   {
/*  80: 85 */     super.updateScreen();
/*  81: 86 */     this.field_146479_t += 1;
/*  82:    */   }
/*  83:    */   
/*  84:    */   public void initGui()
/*  85:    */   {
/*  86: 94 */     this.buttonList.clear();
/*  87: 95 */     Keyboard.enableRepeatEvents(true);
/*  88: 97 */     if (this.field_146475_i)
/*  89:    */     {
/*  90: 99 */       this.buttonList.add(this.field_146465_D = new NodusGuiButton(3, width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.signButton", new Object[0])));
/*  91:100 */       this.buttonList.add(this.field_146472_C = new NodusGuiButton(0, width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.done", new Object[0])));
/*  92:101 */       this.buttonList.add(this.field_146467_E = new NodusGuiButton(5, width / 2 - 100, 4 + this.field_146477_v, 98, 20, I18n.format("book.finalizeButton", new Object[0])));
/*  93:102 */       this.buttonList.add(this.field_146469_F = new NodusGuiButton(4, width / 2 + 2, 4 + this.field_146477_v, 98, 20, I18n.format("gui.cancel", new Object[0])));
/*  94:    */     }
/*  95:    */     else
/*  96:    */     {
/*  97:106 */       this.buttonList.add(this.field_146472_C = new NodusGuiButton(0, width / 2 - 100, 4 + this.field_146477_v, 200, 20, I18n.format("gui.done", new Object[0])));
/*  98:    */     }
/*  99:109 */     int var1 = (width - this.field_146478_u) / 2;
/* 100:110 */     byte var2 = 2;
/* 101:111 */     this.buttonList.add(this.field_146470_A = new NextPageButton(1, var1 + 120, var2 + 154, true));
/* 102:112 */     this.buttonList.add(this.field_146471_B = new NextPageButton(2, var1 + 38, var2 + 154, false));
/* 103:113 */     func_146464_h();
/* 104:    */   }
/* 105:    */   
/* 106:    */   public void onGuiClosed()
/* 107:    */   {
/* 108:121 */     Keyboard.enableRepeatEvents(false);
/* 109:    */   }
/* 110:    */   
/* 111:    */   private void func_146464_h()
/* 112:    */   {
/* 113:126 */     this.field_146470_A.field_146125_m = ((!this.field_146480_s) && ((this.field_146484_x < this.field_146476_w - 1) || (this.field_146475_i)));
/* 114:127 */     this.field_146471_B.field_146125_m = ((!this.field_146480_s) && (this.field_146484_x > 0));
/* 115:128 */     this.field_146472_C.field_146125_m = ((!this.field_146475_i) || (!this.field_146480_s));
/* 116:130 */     if (this.field_146475_i)
/* 117:    */     {
/* 118:132 */       this.field_146465_D.field_146125_m = (!this.field_146480_s);
/* 119:133 */       this.field_146469_F.field_146125_m = this.field_146480_s;
/* 120:134 */       this.field_146467_E.field_146125_m = this.field_146480_s;
/* 121:135 */       this.field_146467_E.enabled = (this.field_146482_z.trim().length() > 0);
/* 122:    */     }
/* 123:    */   }
/* 124:    */   
/* 125:    */   private void func_146462_a(boolean p_146462_1_)
/* 126:    */   {
/* 127:141 */     if ((this.field_146475_i) && (this.field_146481_r)) {
/* 128:143 */       if (this.field_146483_y != null)
/* 129:    */       {
/* 130:147 */         while (this.field_146483_y.tagCount() > 1)
/* 131:    */         {
/* 132:149 */           String var2 = this.field_146483_y.getStringTagAt(this.field_146483_y.tagCount() - 1);
/* 133:151 */           if (var2.length() != 0) {
/* 134:    */             break;
/* 135:    */           }
/* 136:156 */           this.field_146483_y.removeTag(this.field_146483_y.tagCount() - 1);
/* 137:    */         }
/* 138:159 */         if (this.field_146474_h.hasTagCompound())
/* 139:    */         {
/* 140:161 */           NBTTagCompound var10 = this.field_146474_h.getTagCompound();
/* 141:162 */           var10.setTag("pages", this.field_146483_y);
/* 142:    */         }
/* 143:    */         else
/* 144:    */         {
/* 145:166 */           this.field_146474_h.setTagInfo("pages", this.field_146483_y);
/* 146:    */         }
/* 147:169 */         String var2 = "MC|BEdit";
/* 148:171 */         if (p_146462_1_)
/* 149:    */         {
/* 150:173 */           var2 = "MC|BSign";
/* 151:174 */           this.field_146474_h.setTagInfo("author", new NBTTagString(this.field_146468_g.getCommandSenderName()));
/* 152:175 */           this.field_146474_h.setTagInfo("title", new NBTTagString(this.field_146482_z.trim()));
/* 153:176 */           this.field_146474_h.func_150996_a(Items.written_book);
/* 154:    */         }
/* 155:179 */         ByteBuf var3 = Unpooled.buffer();
/* 156:    */         try
/* 157:    */         {
/* 158:183 */           new PacketBuffer(var3).writeItemStackToBuffer(this.field_146474_h);
/* 159:184 */           this.mc.getNetHandler().addToSendQueue(new C17PacketCustomPayload(var2, var3));
/* 160:    */         }
/* 161:    */         catch (Exception var8)
/* 162:    */         {
/* 163:188 */           logger.error("Couldn't send book info", var8);
/* 164:    */         }
/* 165:    */         finally
/* 166:    */         {
/* 167:192 */           var3.release();
/* 168:    */         }
/* 169:    */       }
/* 170:    */     }
/* 171:    */   }
/* 172:    */   
/* 173:    */   protected void actionPerformed(NodusGuiButton p_146284_1_)
/* 174:    */   {
/* 175:200 */     if (p_146284_1_.enabled)
/* 176:    */     {
/* 177:202 */       if (p_146284_1_.id == 0)
/* 178:    */       {
/* 179:204 */         this.mc.displayGuiScreen(null);
/* 180:205 */         func_146462_a(false);
/* 181:    */       }
/* 182:207 */       else if ((p_146284_1_.id == 3) && (this.field_146475_i))
/* 183:    */       {
/* 184:209 */         this.field_146480_s = true;
/* 185:    */       }
/* 186:211 */       else if (p_146284_1_.id == 1)
/* 187:    */       {
/* 188:213 */         if (this.field_146484_x < this.field_146476_w - 1)
/* 189:    */         {
/* 190:215 */           this.field_146484_x += 1;
/* 191:    */         }
/* 192:217 */         else if (this.field_146475_i)
/* 193:    */         {
/* 194:219 */           func_146461_i();
/* 195:221 */           if (this.field_146484_x < this.field_146476_w - 1) {
/* 196:223 */             this.field_146484_x += 1;
/* 197:    */           }
/* 198:    */         }
/* 199:    */       }
/* 200:227 */       else if (p_146284_1_.id == 2)
/* 201:    */       {
/* 202:229 */         if (this.field_146484_x > 0) {
/* 203:231 */           this.field_146484_x -= 1;
/* 204:    */         }
/* 205:    */       }
/* 206:234 */       else if ((p_146284_1_.id == 5) && (this.field_146480_s))
/* 207:    */       {
/* 208:236 */         func_146462_a(true);
/* 209:237 */         this.mc.displayGuiScreen(null);
/* 210:    */       }
/* 211:239 */       else if ((p_146284_1_.id == 4) && (this.field_146480_s))
/* 212:    */       {
/* 213:241 */         this.field_146480_s = false;
/* 214:    */       }
/* 215:244 */       func_146464_h();
/* 216:    */     }
/* 217:    */   }
/* 218:    */   
/* 219:    */   private void func_146461_i()
/* 220:    */   {
/* 221:250 */     if ((this.field_146483_y != null) && (this.field_146483_y.tagCount() < 50))
/* 222:    */     {
/* 223:252 */       this.field_146483_y.appendTag(new NBTTagString(""));
/* 224:253 */       this.field_146476_w += 1;
/* 225:254 */       this.field_146481_r = true;
/* 226:    */     }
/* 227:    */   }
/* 228:    */   
/* 229:    */   protected void keyTyped(char par1, int par2)
/* 230:    */   {
/* 231:263 */     super.keyTyped(par1, par2);
/* 232:265 */     if (this.field_146475_i) {
/* 233:267 */       if (this.field_146480_s) {
/* 234:269 */         func_146460_c(par1, par2);
/* 235:    */       } else {
/* 236:273 */         func_146463_b(par1, par2);
/* 237:    */       }
/* 238:    */     }
/* 239:    */   }
/* 240:    */   
/* 241:    */   private void func_146463_b(char p_146463_1_, int p_146463_2_)
/* 242:    */   {
/* 243:280 */     switch (p_146463_1_)
/* 244:    */     {
/* 245:    */     case '\026': 
/* 246:283 */       func_146459_b(GuiScreen.getClipboardString());
/* 247:284 */       return;
/* 248:    */     }
/* 249:287 */     switch (p_146463_2_)
/* 250:    */     {
/* 251:    */     case 14: 
/* 252:290 */       String var3 = func_146456_p();
/* 253:292 */       if (var3.length() > 0) {
/* 254:294 */         func_146457_a(var3.substring(0, var3.length() - 1));
/* 255:    */       }
/* 256:297 */       return;
/* 257:    */     case 28: 
/* 258:    */     case 156: 
/* 259:301 */       func_146459_b("\n");
/* 260:302 */       return;
/* 261:    */     }
/* 262:305 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146463_1_)) {
/* 263:307 */       func_146459_b(Character.toString(p_146463_1_));
/* 264:    */     }
/* 265:    */   }
/* 266:    */   
/* 267:    */   private void func_146460_c(char p_146460_1_, int p_146460_2_)
/* 268:    */   {
/* 269:315 */     switch (p_146460_2_)
/* 270:    */     {
/* 271:    */     case 14: 
/* 272:318 */       if (!this.field_146482_z.isEmpty())
/* 273:    */       {
/* 274:320 */         this.field_146482_z = this.field_146482_z.substring(0, this.field_146482_z.length() - 1);
/* 275:321 */         func_146464_h();
/* 276:    */       }
/* 277:324 */       return;
/* 278:    */     case 28: 
/* 279:    */     case 156: 
/* 280:328 */       if (!this.field_146482_z.isEmpty())
/* 281:    */       {
/* 282:330 */         func_146462_a(true);
/* 283:331 */         this.mc.displayGuiScreen(null);
/* 284:    */       }
/* 285:334 */       return;
/* 286:    */     }
/* 287:337 */     if ((this.field_146482_z.length() < 16) && (ChatAllowedCharacters.isAllowedCharacter(p_146460_1_)))
/* 288:    */     {
/* 289:339 */       this.field_146482_z += Character.toString(p_146460_1_);
/* 290:340 */       func_146464_h();
/* 291:341 */       this.field_146481_r = true;
/* 292:    */     }
/* 293:    */   }
/* 294:    */   
/* 295:    */   private String func_146456_p()
/* 296:    */   {
/* 297:348 */     return (this.field_146483_y != null) && (this.field_146484_x >= 0) && (this.field_146484_x < this.field_146483_y.tagCount()) ? this.field_146483_y.getStringTagAt(this.field_146484_x) : "";
/* 298:    */   }
/* 299:    */   
/* 300:    */   private void func_146457_a(String p_146457_1_)
/* 301:    */   {
/* 302:353 */     if ((this.field_146483_y != null) && (this.field_146484_x >= 0) && (this.field_146484_x < this.field_146483_y.tagCount()))
/* 303:    */     {
/* 304:355 */       this.field_146483_y.func_150304_a(this.field_146484_x, new NBTTagString(p_146457_1_));
/* 305:356 */       this.field_146481_r = true;
/* 306:    */     }
/* 307:    */   }
/* 308:    */   
/* 309:    */   private void func_146459_b(String p_146459_1_)
/* 310:    */   {
/* 311:362 */     String var2 = func_146456_p();
/* 312:363 */     String var3 = var2 + p_146459_1_;
/* 313:364 */     int var4 = this.fontRendererObj.splitStringWidth(var3 + EnumChatFormatting.BLACK + "_", 118);
/* 314:366 */     if ((var4 <= 118) && (var3.length() < 256)) {
/* 315:368 */       func_146457_a(var3);
/* 316:    */     }
/* 317:    */   }
/* 318:    */   
/* 319:    */   public void drawScreen(int par1, int par2, float par3)
/* 320:    */   {
/* 321:377 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 322:378 */     this.mc.getTextureManager().bindTexture(field_146466_f);
/* 323:379 */     int var4 = (width - this.field_146478_u) / 2;
/* 324:380 */     byte var5 = 2;
/* 325:381 */     drawTexturedModalRect(var4, var5, 0, 0, this.field_146478_u, this.field_146477_v);
/* 326:386 */     if (this.field_146480_s)
/* 327:    */     {
/* 328:388 */       String var6 = this.field_146482_z;
/* 329:390 */       if (this.field_146475_i) {
/* 330:392 */         if (this.field_146479_t / 6 % 2 == 0) {
/* 331:394 */           var6 = var6 + EnumChatFormatting.BLACK + "_";
/* 332:    */         } else {
/* 333:398 */           var6 = var6 + EnumChatFormatting.GRAY + "_";
/* 334:    */         }
/* 335:    */       }
/* 336:402 */       String var7 = I18n.format("book.editTitle", new Object[0]);
/* 337:403 */       int var8 = this.fontRendererObj.getStringWidth(var7);
/* 338:404 */       this.fontRendererObj.drawString(var7, var4 + 36 + (116 - var8) / 2, var5 + 16 + 16, 0);
/* 339:405 */       int var9 = this.fontRendererObj.getStringWidth(var6);
/* 340:406 */       this.fontRendererObj.drawString(var6, var4 + 36 + (116 - var9) / 2, var5 + 48, 0);
/* 341:407 */       String var10 = I18n.format("book.byAuthor", new Object[] { this.field_146468_g.getCommandSenderName() });
/* 342:408 */       int var11 = this.fontRendererObj.getStringWidth(var10);
/* 343:409 */       this.fontRendererObj.drawString(EnumChatFormatting.DARK_GRAY + var10, var4 + 36 + (116 - var11) / 2, var5 + 48 + 10, 0);
/* 344:410 */       String var12 = I18n.format("book.finalizeWarning", new Object[0]);
/* 345:411 */       this.fontRendererObj.drawSplitString(var12, var4 + 36, var5 + 80, 116, 0);
/* 346:    */     }
/* 347:    */     else
/* 348:    */     {
/* 349:415 */       String var6 = I18n.format("book.pageIndicator", new Object[] { Integer.valueOf(this.field_146484_x + 1), Integer.valueOf(this.field_146476_w) });
/* 350:416 */       String var7 = "";
/* 351:418 */       if ((this.field_146483_y != null) && (this.field_146484_x >= 0) && (this.field_146484_x < this.field_146483_y.tagCount())) {
/* 352:420 */         var7 = this.field_146483_y.getStringTagAt(this.field_146484_x);
/* 353:    */       }
/* 354:423 */       if (this.field_146475_i) {
/* 355:425 */         if (this.fontRendererObj.getBidiFlag()) {
/* 356:427 */           var7 = var7 + "_";
/* 357:429 */         } else if (this.field_146479_t / 6 % 2 == 0) {
/* 358:431 */           var7 = var7 + EnumChatFormatting.BLACK + "_";
/* 359:    */         } else {
/* 360:435 */           var7 = var7 + EnumChatFormatting.GRAY + "_";
/* 361:    */         }
/* 362:    */       }
/* 363:439 */       int var8 = this.fontRendererObj.getStringWidth(var6);
/* 364:440 */       this.fontRendererObj.drawString(var6, var4 - var8 + this.field_146478_u - 44, var5 + 16, 0);
/* 365:441 */       this.fontRendererObj.drawSplitString(var7, var4 + 36, var5 + 16 + 16, 116, 0);
/* 366:    */     }
/* 367:444 */     super.drawScreen(par1, par2, par3);
/* 368:    */   }
/* 369:    */   
/* 370:    */   static class NextPageButton
/* 371:    */     extends NodusGuiButton
/* 372:    */   {
/* 373:    */     private final boolean field_146151_o;
/* 374:    */     private static final String __OBFID = "CL_00000745";
/* 375:    */     
/* 376:    */     public NextPageButton(int par1, int par2, int par3, boolean par4)
/* 377:    */     {
/* 378:454 */       super(par2, par3, 23, 13, "");
/* 379:455 */       this.field_146151_o = par4;
/* 380:    */     }
/* 381:    */     
/* 382:    */     public void drawButton(Minecraft p_146112_1_, int p_146112_2_, int p_146112_3_)
/* 383:    */     {
/* 384:460 */       if (this.field_146125_m)
/* 385:    */       {
/* 386:462 */         boolean var4 = (p_146112_2_ >= this.xPosition) && (p_146112_3_ >= this.yPosition) && (p_146112_2_ < this.xPosition + this.width) && (p_146112_3_ < this.yPosition + this.height);
/* 387:463 */         GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/* 388:464 */         p_146112_1_.getTextureManager().bindTexture(GuiScreenBook.field_146466_f);
/* 389:465 */         int var5 = 0;
/* 390:466 */         int var6 = 192;
/* 391:468 */         if (var4) {
/* 392:470 */           var5 += 23;
/* 393:    */         }
/* 394:473 */         if (!this.field_146151_o) {
/* 395:475 */           var6 += 13;
/* 396:    */         }
/* 397:478 */         drawTexturedModalRect(this.xPosition, this.yPosition, var5, var6, 23, 13);
/* 398:    */       }
/* 399:    */     }
/* 400:    */   }
/* 401:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.GuiScreenBook
 * JD-Core Version:    0.7.0.1
 */