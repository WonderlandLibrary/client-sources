/*   1:    */ package net.minecraft.client.gui.inventory;
/*   2:    */ 
/*   3:    */ import java.util.HashSet;
/*   4:    */ import java.util.Iterator;
/*   5:    */ import java.util.List;
/*   6:    */ import java.util.Set;
/*   7:    */ import net.minecraft.client.Minecraft;
/*   8:    */ import net.minecraft.client.entity.EntityClientPlayerMP;
/*   9:    */ import net.minecraft.client.gui.GuiScreen;
/*  10:    */ import net.minecraft.client.multiplayer.PlayerControllerMP;
/*  11:    */ import net.minecraft.client.renderer.OpenGlHelper;
/*  12:    */ import net.minecraft.client.renderer.RenderHelper;
/*  13:    */ import net.minecraft.client.renderer.entity.RenderItem;
/*  14:    */ import net.minecraft.client.renderer.texture.TextureManager;
/*  15:    */ import net.minecraft.client.renderer.texture.TextureMap;
/*  16:    */ import net.minecraft.client.settings.GameSettings;
/*  17:    */ import net.minecraft.client.settings.KeyBinding;
/*  18:    */ import net.minecraft.entity.player.InventoryPlayer;
/*  19:    */ import net.minecraft.inventory.Container;
/*  20:    */ import net.minecraft.inventory.Slot;
/*  21:    */ import net.minecraft.item.ItemStack;
/*  22:    */ import net.minecraft.util.EnumChatFormatting;
/*  23:    */ import net.minecraft.util.IIcon;
/*  24:    */ import net.minecraft.util.MathHelper;
/*  25:    */ import net.minecraft.util.ResourceLocation;
/*  26:    */ import org.lwjgl.input.Keyboard;
/*  27:    */ import org.lwjgl.opengl.GL11;
/*  28:    */ 
/*  29:    */ public abstract class GuiContainer
/*  30:    */   extends GuiScreen
/*  31:    */ {
/*  32: 25 */   protected static final ResourceLocation field_147001_a = new ResourceLocation("textures/gui/container/inventory.png");
/*  33: 26 */   protected int field_146999_f = 176;
/*  34: 27 */   protected int field_147000_g = 166;
/*  35:    */   public Container field_147002_h;
/*  36:    */   protected int field_147003_i;
/*  37:    */   protected int field_147009_r;
/*  38:    */   private Slot field_147006_u;
/*  39:    */   private Slot field_147005_v;
/*  40:    */   private boolean field_147004_w;
/*  41:    */   private ItemStack field_147012_x;
/*  42:    */   private int field_147011_y;
/*  43:    */   private int field_147010_z;
/*  44:    */   private Slot field_146989_A;
/*  45:    */   private long field_146990_B;
/*  46:    */   private ItemStack field_146991_C;
/*  47:    */   private Slot field_146985_D;
/*  48:    */   private long field_146986_E;
/*  49: 42 */   protected final Set field_147008_s = new HashSet();
/*  50:    */   protected boolean field_147007_t;
/*  51:    */   private int field_146987_F;
/*  52:    */   private int field_146988_G;
/*  53:    */   private boolean field_146995_H;
/*  54:    */   private int field_146996_I;
/*  55:    */   private long field_146997_J;
/*  56:    */   private Slot field_146998_K;
/*  57:    */   private int field_146992_L;
/*  58:    */   private boolean field_146993_M;
/*  59:    */   private ItemStack field_146994_N;
/*  60:    */   private static final String __OBFID = "CL_00000737";
/*  61:    */   
/*  62:    */   public GuiContainer(Container par1Container)
/*  63:    */   {
/*  64: 57 */     this.field_147002_h = par1Container;
/*  65: 58 */     this.field_146995_H = true;
/*  66:    */   }
/*  67:    */   
/*  68:    */   public void initGui()
/*  69:    */   {
/*  70: 66 */     super.initGui();
/*  71: 67 */     this.mc.thePlayer.openContainer = this.field_147002_h;
/*  72: 68 */     this.field_147003_i = ((width - this.field_146999_f) / 2);
/*  73: 69 */     this.field_147009_r = ((height - this.field_147000_g) / 2);
/*  74:    */   }
/*  75:    */   
/*  76:    */   public void drawScreen(int par1, int par2, float par3)
/*  77:    */   {
/*  78: 77 */     drawDefaultBackground();
/*  79: 78 */     int var4 = this.field_147003_i;
/*  80: 79 */     int var5 = this.field_147009_r;
/*  81: 80 */     func_146976_a(par3, par1, par2);
/*  82: 81 */     GL11.glDisable(32826);
/*  83: 82 */     RenderHelper.disableStandardItemLighting();
/*  84: 83 */     GL11.glDisable(2896);
/*  85: 84 */     GL11.glDisable(2929);
/*  86: 85 */     super.drawScreen(par1, par2, par3);
/*  87: 86 */     RenderHelper.enableGUIStandardItemLighting();
/*  88: 87 */     GL11.glPushMatrix();
/*  89: 88 */     GL11.glTranslatef(var4, var5, 0.0F);
/*  90: 89 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  91: 90 */     GL11.glEnable(32826);
/*  92: 91 */     this.field_147006_u = null;
/*  93: 92 */     short var6 = 240;
/*  94: 93 */     short var7 = 240;
/*  95: 94 */     OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6 / 1.0F, var7 / 1.0F);
/*  96: 95 */     GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
/*  97: 98 */     for (int var8 = 0; var8 < this.field_147002_h.inventorySlots.size(); var8++)
/*  98:    */     {
/*  99:100 */       Slot var9 = (Slot)this.field_147002_h.inventorySlots.get(var8);
/* 100:101 */       func_146977_a(var9);
/* 101:103 */       if ((func_146981_a(var9, par1, par2)) && (var9.func_111238_b()))
/* 102:    */       {
/* 103:105 */         this.field_147006_u = var9;
/* 104:106 */         GL11.glDisable(2896);
/* 105:107 */         GL11.glDisable(2929);
/* 106:108 */         int var10 = var9.xDisplayPosition;
/* 107:109 */         int var11 = var9.yDisplayPosition;
/* 108:110 */         GL11.glColorMask(true, true, true, false);
/* 109:111 */         drawGradientRect(var10, var11, var10 + 16, var11 + 16, -2130706433, -2130706433);
/* 110:112 */         GL11.glColorMask(true, true, true, true);
/* 111:113 */         GL11.glEnable(2896);
/* 112:114 */         GL11.glEnable(2929);
/* 113:    */       }
/* 114:    */     }
/* 115:118 */     func_146979_b(par1, par2);
/* 116:119 */     InventoryPlayer var15 = this.mc.thePlayer.inventory;
/* 117:120 */     ItemStack var16 = this.field_147012_x == null ? var15.getItemStack() : this.field_147012_x;
/* 118:122 */     if (var16 != null)
/* 119:    */     {
/* 120:124 */       byte var17 = 8;
/* 121:125 */       int var11 = this.field_147012_x == null ? 8 : 16;
/* 122:126 */       String var12 = null;
/* 123:128 */       if ((this.field_147012_x != null) && (this.field_147004_w))
/* 124:    */       {
/* 125:130 */         var16 = var16.copy();
/* 126:131 */         var16.stackSize = MathHelper.ceiling_float_int(var16.stackSize / 2.0F);
/* 127:    */       }
/* 128:133 */       else if ((this.field_147007_t) && (this.field_147008_s.size() > 1))
/* 129:    */       {
/* 130:135 */         var16 = var16.copy();
/* 131:136 */         var16.stackSize = this.field_146996_I;
/* 132:138 */         if (var16.stackSize == 0) {
/* 133:140 */           var12 = EnumChatFormatting.YELLOW + "0";
/* 134:    */         }
/* 135:    */       }
/* 136:144 */       func_146982_a(var16, par1 - var4 - var17, par2 - var5 - var11, var12);
/* 137:    */     }
/* 138:147 */     if (this.field_146991_C != null)
/* 139:    */     {
/* 140:149 */       float var18 = (float)(Minecraft.getSystemTime() - this.field_146990_B) / 100.0F;
/* 141:151 */       if (var18 >= 1.0F)
/* 142:    */       {
/* 143:153 */         var18 = 1.0F;
/* 144:154 */         this.field_146991_C = null;
/* 145:    */       }
/* 146:157 */       int var11 = this.field_146989_A.xDisplayPosition - this.field_147011_y;
/* 147:158 */       int var20 = this.field_146989_A.yDisplayPosition - this.field_147010_z;
/* 148:159 */       int var13 = this.field_147011_y + (int)(var11 * var18);
/* 149:160 */       int var14 = this.field_147010_z + (int)(var20 * var18);
/* 150:161 */       func_146982_a(this.field_146991_C, var13, var14, null);
/* 151:    */     }
/* 152:164 */     GL11.glPopMatrix();
/* 153:166 */     if ((var15.getItemStack() == null) && (this.field_147006_u != null) && (this.field_147006_u.getHasStack()))
/* 154:    */     {
/* 155:168 */       ItemStack var19 = this.field_147006_u.getStack();
/* 156:169 */       func_146285_a(var19, par1, par2);
/* 157:    */     }
/* 158:172 */     GL11.glEnable(2896);
/* 159:173 */     GL11.glEnable(2929);
/* 160:174 */     RenderHelper.enableStandardItemLighting();
/* 161:    */   }
/* 162:    */   
/* 163:    */   private void func_146982_a(ItemStack p_146982_1_, int p_146982_2_, int p_146982_3_, String p_146982_4_)
/* 164:    */   {
/* 165:179 */     GL11.glTranslatef(0.0F, 0.0F, 32.0F);
/* 166:180 */     zLevel = 200.0F;
/* 167:181 */     itemRender.zLevel = 200.0F;
/* 168:182 */     itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_);
/* 169:183 */     itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), p_146982_1_, p_146982_2_, p_146982_3_ - (this.field_147012_x == null ? 0 : 8), p_146982_4_);
/* 170:184 */     zLevel = 0.0F;
/* 171:185 */     itemRender.zLevel = 0.0F;
/* 172:    */   }
/* 173:    */   
/* 174:    */   protected void func_146979_b(int p_146979_1_, int p_146979_2_) {}
/* 175:    */   
/* 176:    */   protected abstract void func_146976_a(float paramFloat, int paramInt1, int paramInt2);
/* 177:    */   
/* 178:    */   private void func_146977_a(Slot p_146977_1_)
/* 179:    */   {
/* 180:194 */     int var2 = p_146977_1_.xDisplayPosition;
/* 181:195 */     int var3 = p_146977_1_.yDisplayPosition;
/* 182:196 */     ItemStack var4 = p_146977_1_.getStack();
/* 183:197 */     boolean var5 = false;
/* 184:198 */     boolean var6 = (p_146977_1_ == this.field_147005_v) && (this.field_147012_x != null) && (!this.field_147004_w);
/* 185:199 */     ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
/* 186:200 */     String var8 = null;
/* 187:202 */     if ((p_146977_1_ == this.field_147005_v) && (this.field_147012_x != null) && (this.field_147004_w) && (var4 != null))
/* 188:    */     {
/* 189:204 */       var4 = var4.copy();
/* 190:205 */       var4.stackSize /= 2;
/* 191:    */     }
/* 192:207 */     else if ((this.field_147007_t) && (this.field_147008_s.contains(p_146977_1_)) && (var7 != null))
/* 193:    */     {
/* 194:209 */       if (this.field_147008_s.size() == 1) {
/* 195:211 */         return;
/* 196:    */       }
/* 197:214 */       if ((Container.func_94527_a(p_146977_1_, var7, true)) && (this.field_147002_h.canDragIntoSlot(p_146977_1_)))
/* 198:    */       {
/* 199:216 */         var4 = var7.copy();
/* 200:217 */         var5 = true;
/* 201:218 */         Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, p_146977_1_.getStack() == null ? 0 : p_146977_1_.getStack().stackSize);
/* 202:220 */         if (var4.stackSize > var4.getMaxStackSize())
/* 203:    */         {
/* 204:222 */           var8 = EnumChatFormatting.YELLOW + var4.getMaxStackSize();
/* 205:223 */           var4.stackSize = var4.getMaxStackSize();
/* 206:    */         }
/* 207:226 */         if (var4.stackSize > p_146977_1_.getSlotStackLimit())
/* 208:    */         {
/* 209:228 */           var8 = EnumChatFormatting.YELLOW + p_146977_1_.getSlotStackLimit();
/* 210:229 */           var4.stackSize = p_146977_1_.getSlotStackLimit();
/* 211:    */         }
/* 212:    */       }
/* 213:    */       else
/* 214:    */       {
/* 215:234 */         this.field_147008_s.remove(p_146977_1_);
/* 216:235 */         func_146980_g();
/* 217:    */       }
/* 218:    */     }
/* 219:239 */     zLevel = 100.0F;
/* 220:240 */     itemRender.zLevel = 100.0F;
/* 221:242 */     if (var4 == null)
/* 222:    */     {
/* 223:244 */       IIcon var9 = p_146977_1_.getBackgroundIconIndex();
/* 224:246 */       if (var9 != null)
/* 225:    */       {
/* 226:248 */         GL11.glDisable(2896);
/* 227:249 */         this.mc.getTextureManager().bindTexture(TextureMap.locationItemsTexture);
/* 228:250 */         drawTexturedModelRectFromIcon(var2, var3, var9, 16, 16);
/* 229:251 */         GL11.glEnable(2896);
/* 230:252 */         var6 = true;
/* 231:    */       }
/* 232:    */     }
/* 233:256 */     if (!var6)
/* 234:    */     {
/* 235:258 */       if (var5) {
/* 236:260 */         drawRect(var2, var3, var2 + 16, var3 + 16, -2130706433);
/* 237:    */       }
/* 238:263 */       GL11.glEnable(2929);
/* 239:264 */       itemRender.renderItemAndEffectIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3);
/* 240:265 */       itemRender.renderItemOverlayIntoGUI(this.fontRendererObj, this.mc.getTextureManager(), var4, var2, var3, var8);
/* 241:    */     }
/* 242:268 */     itemRender.zLevel = 0.0F;
/* 243:269 */     zLevel = 0.0F;
/* 244:    */   }
/* 245:    */   
/* 246:    */   private void func_146980_g()
/* 247:    */   {
/* 248:274 */     ItemStack var1 = this.mc.thePlayer.inventory.getItemStack();
/* 249:276 */     if ((var1 != null) && (this.field_147007_t))
/* 250:    */     {
/* 251:278 */       this.field_146996_I = var1.stackSize;
/* 252:    */       ItemStack var4;
/* 253:    */       int var5;
/* 254:282 */       for (Iterator var2 = this.field_147008_s.iterator(); var2.hasNext(); this.field_146996_I -= var4.stackSize - var5)
/* 255:    */       {
/* 256:284 */         Slot var3 = (Slot)var2.next();
/* 257:285 */         var4 = var1.copy();
/* 258:286 */         var5 = var3.getStack() == null ? 0 : var3.getStack().stackSize;
/* 259:287 */         Container.func_94525_a(this.field_147008_s, this.field_146987_F, var4, var5);
/* 260:289 */         if (var4.stackSize > var4.getMaxStackSize()) {
/* 261:291 */           var4.stackSize = var4.getMaxStackSize();
/* 262:    */         }
/* 263:294 */         if (var4.stackSize > var3.getSlotStackLimit()) {
/* 264:296 */           var4.stackSize = var3.getSlotStackLimit();
/* 265:    */         }
/* 266:    */       }
/* 267:    */     }
/* 268:    */   }
/* 269:    */   
/* 270:    */   private Slot func_146975_c(int p_146975_1_, int p_146975_2_)
/* 271:    */   {
/* 272:304 */     for (int var3 = 0; var3 < this.field_147002_h.inventorySlots.size(); var3++)
/* 273:    */     {
/* 274:306 */       Slot var4 = (Slot)this.field_147002_h.inventorySlots.get(var3);
/* 275:308 */       if (func_146981_a(var4, p_146975_1_, p_146975_2_)) {
/* 276:310 */         return var4;
/* 277:    */       }
/* 278:    */     }
/* 279:314 */     return null;
/* 280:    */   }
/* 281:    */   
/* 282:    */   protected void mouseClicked(int par1, int par2, int par3)
/* 283:    */   {
/* 284:322 */     super.mouseClicked(par1, par2, par3);
/* 285:323 */     boolean var4 = par3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100;
/* 286:324 */     Slot var5 = func_146975_c(par1, par2);
/* 287:325 */     long var6 = Minecraft.getSystemTime();
/* 288:326 */     this.field_146993_M = ((this.field_146998_K == var5) && (var6 - this.field_146997_J < 250L) && (this.field_146992_L == par3));
/* 289:327 */     this.field_146995_H = false;
/* 290:329 */     if ((par3 == 0) || (par3 == 1) || (var4))
/* 291:    */     {
/* 292:331 */       int var8 = this.field_147003_i;
/* 293:332 */       int var9 = this.field_147009_r;
/* 294:333 */       boolean var10 = (par1 < var8) || (par2 < var9) || (par1 >= var8 + this.field_146999_f) || (par2 >= var9 + this.field_147000_g);
/* 295:334 */       int var11 = -1;
/* 296:336 */       if (var5 != null) {
/* 297:338 */         var11 = var5.slotNumber;
/* 298:    */       }
/* 299:341 */       if (var10) {
/* 300:343 */         var11 = -999;
/* 301:    */       }
/* 302:346 */       if ((this.mc.gameSettings.touchscreen) && (var10) && (this.mc.thePlayer.inventory.getItemStack() == null))
/* 303:    */       {
/* 304:348 */         this.mc.displayGuiScreen(null);
/* 305:349 */         return;
/* 306:    */       }
/* 307:352 */       if (var11 != -1) {
/* 308:354 */         if (this.mc.gameSettings.touchscreen)
/* 309:    */         {
/* 310:356 */           if ((var5 != null) && (var5.getHasStack()))
/* 311:    */           {
/* 312:358 */             this.field_147005_v = var5;
/* 313:359 */             this.field_147012_x = null;
/* 314:360 */             this.field_147004_w = (par3 == 1);
/* 315:    */           }
/* 316:    */           else
/* 317:    */           {
/* 318:364 */             this.field_147005_v = null;
/* 319:    */           }
/* 320:    */         }
/* 321:367 */         else if (!this.field_147007_t) {
/* 322:369 */           if (this.mc.thePlayer.inventory.getItemStack() == null)
/* 323:    */           {
/* 324:371 */             if (par3 == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
/* 325:    */             {
/* 326:373 */               func_146984_a(var5, var11, par3, 3);
/* 327:    */             }
/* 328:    */             else
/* 329:    */             {
/* 330:377 */               boolean var12 = (var11 != -999) && ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)));
/* 331:378 */               byte var13 = 0;
/* 332:380 */               if (var12)
/* 333:    */               {
/* 334:382 */                 this.field_146994_N = ((var5 != null) && (var5.getHasStack()) ? var5.getStack() : null);
/* 335:383 */                 var13 = 1;
/* 336:    */               }
/* 337:385 */               else if (var11 == -999)
/* 338:    */               {
/* 339:387 */                 var13 = 4;
/* 340:    */               }
/* 341:390 */               func_146984_a(var5, var11, par3, var13);
/* 342:    */             }
/* 343:393 */             this.field_146995_H = true;
/* 344:    */           }
/* 345:    */           else
/* 346:    */           {
/* 347:397 */             this.field_147007_t = true;
/* 348:398 */             this.field_146988_G = par3;
/* 349:399 */             this.field_147008_s.clear();
/* 350:401 */             if (par3 == 0) {
/* 351:403 */               this.field_146987_F = 0;
/* 352:405 */             } else if (par3 == 1) {
/* 353:407 */               this.field_146987_F = 1;
/* 354:    */             }
/* 355:    */           }
/* 356:    */         }
/* 357:    */       }
/* 358:    */     }
/* 359:414 */     this.field_146998_K = var5;
/* 360:415 */     this.field_146997_J = var6;
/* 361:416 */     this.field_146992_L = par3;
/* 362:    */   }
/* 363:    */   
/* 364:    */   protected void mouseClickMove(int p_146273_1_, int p_146273_2_, int p_146273_3_, long p_146273_4_)
/* 365:    */   {
/* 366:421 */     Slot var6 = func_146975_c(p_146273_1_, p_146273_2_);
/* 367:422 */     ItemStack var7 = this.mc.thePlayer.inventory.getItemStack();
/* 368:424 */     if ((this.field_147005_v != null) && (this.mc.gameSettings.touchscreen))
/* 369:    */     {
/* 370:426 */       if ((p_146273_3_ == 0) || (p_146273_3_ == 1)) {
/* 371:428 */         if (this.field_147012_x == null)
/* 372:    */         {
/* 373:430 */           if (var6 != this.field_147005_v) {
/* 374:432 */             this.field_147012_x = this.field_147005_v.getStack().copy();
/* 375:    */           }
/* 376:    */         }
/* 377:435 */         else if ((this.field_147012_x.stackSize > 1) && (var6 != null) && (Container.func_94527_a(var6, this.field_147012_x, false)))
/* 378:    */         {
/* 379:437 */           long var8 = Minecraft.getSystemTime();
/* 380:439 */           if (this.field_146985_D == var6)
/* 381:    */           {
/* 382:441 */             if (var8 - this.field_146986_E > 500L)
/* 383:    */             {
/* 384:443 */               func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
/* 385:444 */               func_146984_a(var6, var6.slotNumber, 1, 0);
/* 386:445 */               func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, 0, 0);
/* 387:446 */               this.field_146986_E = (var8 + 750L);
/* 388:447 */               this.field_147012_x.stackSize -= 1;
/* 389:    */             }
/* 390:    */           }
/* 391:    */           else
/* 392:    */           {
/* 393:452 */             this.field_146985_D = var6;
/* 394:453 */             this.field_146986_E = var8;
/* 395:    */           }
/* 396:    */         }
/* 397:    */       }
/* 398:    */     }
/* 399:458 */     else if ((this.field_147007_t) && (var6 != null) && (var7 != null) && (var7.stackSize > this.field_147008_s.size()) && (Container.func_94527_a(var6, var7, true)) && (var6.isItemValid(var7)) && (this.field_147002_h.canDragIntoSlot(var6)))
/* 400:    */     {
/* 401:460 */       this.field_147008_s.add(var6);
/* 402:461 */       func_146980_g();
/* 403:    */     }
/* 404:    */   }
/* 405:    */   
/* 406:    */   protected void mouseMovedOrUp(int p_146286_1_, int p_146286_2_, int p_146286_3_)
/* 407:    */   {
/* 408:467 */     Slot var4 = func_146975_c(p_146286_1_, p_146286_2_);
/* 409:468 */     int var5 = this.field_147003_i;
/* 410:469 */     int var6 = this.field_147009_r;
/* 411:470 */     boolean var7 = (p_146286_1_ < var5) || (p_146286_2_ < var6) || (p_146286_1_ >= var5 + this.field_146999_f) || (p_146286_2_ >= var6 + this.field_147000_g);
/* 412:471 */     int var8 = -1;
/* 413:473 */     if (var4 != null) {
/* 414:475 */       var8 = var4.slotNumber;
/* 415:    */     }
/* 416:478 */     if (var7) {
/* 417:480 */       var8 = -999;
/* 418:    */     }
/* 419:486 */     if ((this.field_146993_M) && (var4 != null) && (p_146286_3_ == 0) && (this.field_147002_h.func_94530_a(null, var4)))
/* 420:    */     {
/* 421:488 */       if (isShiftKeyDown())
/* 422:    */       {
/* 423:490 */         if ((var4 != null) && (var4.inventory != null) && (this.field_146994_N != null))
/* 424:    */         {
/* 425:492 */           Iterator var11 = this.field_147002_h.inventorySlots.iterator();
/* 426:494 */           while (var11.hasNext())
/* 427:    */           {
/* 428:496 */             Slot var10 = (Slot)var11.next();
/* 429:498 */             if ((var10 != null) && (var10.canTakeStack(this.mc.thePlayer)) && (var10.getHasStack()) && (var10.inventory == var4.inventory) && (Container.func_94527_a(var10, this.field_146994_N, true))) {
/* 430:500 */               func_146984_a(var10, var10.slotNumber, p_146286_3_, 1);
/* 431:    */             }
/* 432:    */           }
/* 433:    */         }
/* 434:    */       }
/* 435:    */       else {
/* 436:507 */         func_146984_a(var4, var8, p_146286_3_, 6);
/* 437:    */       }
/* 438:510 */       this.field_146993_M = false;
/* 439:511 */       this.field_146997_J = 0L;
/* 440:    */     }
/* 441:    */     else
/* 442:    */     {
/* 443:515 */       if ((this.field_147007_t) && (this.field_146988_G != p_146286_3_))
/* 444:    */       {
/* 445:517 */         this.field_147007_t = false;
/* 446:518 */         this.field_147008_s.clear();
/* 447:519 */         this.field_146995_H = true;
/* 448:520 */         return;
/* 449:    */       }
/* 450:523 */       if (this.field_146995_H)
/* 451:    */       {
/* 452:525 */         this.field_146995_H = false;
/* 453:526 */         return;
/* 454:    */       }
/* 455:531 */       if ((this.field_147005_v != null) && (this.mc.gameSettings.touchscreen))
/* 456:    */       {
/* 457:533 */         if ((p_146286_3_ == 0) || (p_146286_3_ == 1))
/* 458:    */         {
/* 459:535 */           if ((this.field_147012_x == null) && (var4 != this.field_147005_v)) {
/* 460:537 */             this.field_147012_x = this.field_147005_v.getStack();
/* 461:    */           }
/* 462:540 */           boolean var9 = Container.func_94527_a(var4, this.field_147012_x, false);
/* 463:542 */           if ((var8 != -1) && (this.field_147012_x != null) && (var9))
/* 464:    */           {
/* 465:544 */             func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
/* 466:545 */             func_146984_a(var4, var8, 0, 0);
/* 467:547 */             if (this.mc.thePlayer.inventory.getItemStack() != null)
/* 468:    */             {
/* 469:549 */               func_146984_a(this.field_147005_v, this.field_147005_v.slotNumber, p_146286_3_, 0);
/* 470:550 */               this.field_147011_y = (p_146286_1_ - var5);
/* 471:551 */               this.field_147010_z = (p_146286_2_ - var6);
/* 472:552 */               this.field_146989_A = this.field_147005_v;
/* 473:553 */               this.field_146991_C = this.field_147012_x;
/* 474:554 */               this.field_146990_B = Minecraft.getSystemTime();
/* 475:    */             }
/* 476:    */             else
/* 477:    */             {
/* 478:558 */               this.field_146991_C = null;
/* 479:    */             }
/* 480:    */           }
/* 481:561 */           else if (this.field_147012_x != null)
/* 482:    */           {
/* 483:563 */             this.field_147011_y = (p_146286_1_ - var5);
/* 484:564 */             this.field_147010_z = (p_146286_2_ - var6);
/* 485:565 */             this.field_146989_A = this.field_147005_v;
/* 486:566 */             this.field_146991_C = this.field_147012_x;
/* 487:567 */             this.field_146990_B = Minecraft.getSystemTime();
/* 488:    */           }
/* 489:570 */           this.field_147012_x = null;
/* 490:571 */           this.field_147005_v = null;
/* 491:    */         }
/* 492:    */       }
/* 493:574 */       else if ((this.field_147007_t) && (!this.field_147008_s.isEmpty()))
/* 494:    */       {
/* 495:576 */         func_146984_a(null, -999, Container.func_94534_d(0, this.field_146987_F), 5);
/* 496:577 */         Iterator var11 = this.field_147008_s.iterator();
/* 497:579 */         while (var11.hasNext())
/* 498:    */         {
/* 499:581 */           Slot var10 = (Slot)var11.next();
/* 500:582 */           func_146984_a(var10, var10.slotNumber, Container.func_94534_d(1, this.field_146987_F), 5);
/* 501:    */         }
/* 502:585 */         func_146984_a(null, -999, Container.func_94534_d(2, this.field_146987_F), 5);
/* 503:    */       }
/* 504:587 */       else if (this.mc.thePlayer.inventory.getItemStack() != null)
/* 505:    */       {
/* 506:589 */         if (p_146286_3_ == this.mc.gameSettings.keyBindPickBlock.getKeyCode() + 100)
/* 507:    */         {
/* 508:591 */           func_146984_a(var4, var8, p_146286_3_, 3);
/* 509:    */         }
/* 510:    */         else
/* 511:    */         {
/* 512:595 */           boolean var9 = (var8 != -999) && ((Keyboard.isKeyDown(42)) || (Keyboard.isKeyDown(54)));
/* 513:597 */           if (var9) {
/* 514:599 */             this.field_146994_N = ((var4 != null) && (var4.getHasStack()) ? var4.getStack() : null);
/* 515:    */           }
/* 516:602 */           func_146984_a(var4, var8, p_146286_3_, var9 ? 1 : 0);
/* 517:    */         }
/* 518:    */       }
/* 519:    */     }
/* 520:607 */     if (this.mc.thePlayer.inventory.getItemStack() == null) {
/* 521:609 */       this.field_146997_J = 0L;
/* 522:    */     }
/* 523:612 */     this.field_147007_t = false;
/* 524:    */   }
/* 525:    */   
/* 526:    */   private boolean func_146981_a(Slot p_146981_1_, int p_146981_2_, int p_146981_3_)
/* 527:    */   {
/* 528:617 */     return func_146978_c(p_146981_1_.xDisplayPosition, p_146981_1_.yDisplayPosition, 16, 16, p_146981_2_, p_146981_3_);
/* 529:    */   }
/* 530:    */   
/* 531:    */   protected boolean func_146978_c(int p_146978_1_, int p_146978_2_, int p_146978_3_, int p_146978_4_, int p_146978_5_, int p_146978_6_)
/* 532:    */   {
/* 533:622 */     int var7 = this.field_147003_i;
/* 534:623 */     int var8 = this.field_147009_r;
/* 535:624 */     p_146978_5_ -= var7;
/* 536:625 */     p_146978_6_ -= var8;
/* 537:626 */     return (p_146978_5_ >= p_146978_1_ - 1) && (p_146978_5_ < p_146978_1_ + p_146978_3_ + 1) && (p_146978_6_ >= p_146978_2_ - 1) && (p_146978_6_ < p_146978_2_ + p_146978_4_ + 1);
/* 538:    */   }
/* 539:    */   
/* 540:    */   protected void func_146984_a(Slot p_146984_1_, int p_146984_2_, int p_146984_3_, int p_146984_4_)
/* 541:    */   {
/* 542:631 */     if (p_146984_1_ != null) {
/* 543:633 */       p_146984_2_ = p_146984_1_.slotNumber;
/* 544:    */     }
/* 545:636 */     this.mc.playerController.windowClick(this.field_147002_h.windowId, p_146984_2_, p_146984_3_, p_146984_4_, this.mc.thePlayer);
/* 546:    */   }
/* 547:    */   
/* 548:    */   protected void keyTyped(char par1, int par2)
/* 549:    */   {
/* 550:644 */     if ((par2 == 1) || (par2 == this.mc.gameSettings.keyBindInventory.getKeyCode())) {
/* 551:646 */       this.mc.thePlayer.closeScreen();
/* 552:    */     }
/* 553:649 */     func_146983_a(par2);
/* 554:651 */     if ((this.field_147006_u != null) && (this.field_147006_u.getHasStack())) {
/* 555:653 */       if (par2 == this.mc.gameSettings.keyBindPickBlock.getKeyCode()) {
/* 556:655 */         func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, 0, 3);
/* 557:657 */       } else if (par2 == this.mc.gameSettings.keyBindDrop.getKeyCode()) {
/* 558:659 */         func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, isCtrlKeyDown() ? 1 : 0, 4);
/* 559:    */       }
/* 560:    */     }
/* 561:    */   }
/* 562:    */   
/* 563:    */   protected boolean func_146983_a(int p_146983_1_)
/* 564:    */   {
/* 565:666 */     if ((this.mc.thePlayer.inventory.getItemStack() == null) && (this.field_147006_u != null)) {
/* 566:668 */       for (int var2 = 0; var2 < 9; var2++) {
/* 567:670 */         if (p_146983_1_ == this.mc.gameSettings.keyBindsHotbar[var2].getKeyCode())
/* 568:    */         {
/* 569:672 */           func_146984_a(this.field_147006_u, this.field_147006_u.slotNumber, var2, 2);
/* 570:673 */           return true;
/* 571:    */         }
/* 572:    */       }
/* 573:    */     }
/* 574:678 */     return false;
/* 575:    */   }
/* 576:    */   
/* 577:    */   public void onGuiClosed()
/* 578:    */   {
/* 579:686 */     if (this.mc.thePlayer != null) {
/* 580:688 */       this.field_147002_h.onContainerClosed(this.mc.thePlayer);
/* 581:    */     }
/* 582:    */   }
/* 583:    */   
/* 584:    */   public boolean doesGuiPauseGame()
/* 585:    */   {
/* 586:697 */     return false;
/* 587:    */   }
/* 588:    */   
/* 589:    */   public void updateScreen()
/* 590:    */   {
/* 591:705 */     super.updateScreen();
/* 592:707 */     if ((!this.mc.thePlayer.isEntityAlive()) || (this.mc.thePlayer.isDead)) {
/* 593:709 */       this.mc.thePlayer.closeScreen();
/* 594:    */     }
/* 595:    */   }
/* 596:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.client.gui.inventory.GuiContainer
 * JD-Core Version:    0.7.0.1
 */