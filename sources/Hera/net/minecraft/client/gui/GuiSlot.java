/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ import org.lwjgl.input.Mouse;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class GuiSlot
/*     */ {
/*     */   protected final Minecraft mc;
/*     */   protected int width;
/*     */   protected int height;
/*     */   protected int top;
/*     */   protected int bottom;
/*     */   protected int right;
/*     */   protected int left;
/*     */   protected final int slotHeight;
/*     */   private int scrollUpButtonID;
/*     */   private int scrollDownButtonID;
/*     */   protected int mouseX;
/*     */   protected int mouseY;
/*     */   protected boolean field_148163_i = true;
/*  42 */   protected int initialClickY = -2;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float scrollMultiplier;
/*     */ 
/*     */ 
/*     */   
/*     */   protected float amountScrolled;
/*     */ 
/*     */ 
/*     */   
/*  54 */   protected int selectedElement = -1;
/*     */   
/*     */   protected long lastClicked;
/*     */   
/*     */   protected boolean field_178041_q = true;
/*     */   
/*     */   protected boolean showSelectionBox = true;
/*     */   
/*     */   protected boolean hasListHeader;
/*     */   
/*     */   protected int headerPadding;
/*     */   
/*     */   private boolean enabled = true;
/*     */ 
/*     */   
/*     */   public GuiSlot(Minecraft mcIn, int width, int height, int topIn, int bottomIn, int slotHeightIn) {
/*  70 */     this.mc = mcIn;
/*  71 */     this.width = width;
/*  72 */     this.height = height;
/*  73 */     this.top = topIn;
/*  74 */     this.bottom = bottomIn;
/*  75 */     this.slotHeight = slotHeightIn;
/*  76 */     this.left = 0;
/*  77 */     this.right = width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDimensions(int widthIn, int heightIn, int topIn, int bottomIn) {
/*  82 */     this.width = widthIn;
/*  83 */     this.height = heightIn;
/*  84 */     this.top = topIn;
/*  85 */     this.bottom = bottomIn;
/*  86 */     this.left = 0;
/*  87 */     this.right = widthIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setShowSelectionBox(boolean showSelectionBoxIn) {
/*  92 */     this.showSelectionBox = showSelectionBoxIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void setHasListHeader(boolean hasListHeaderIn, int headerPaddingIn) {
/* 101 */     this.hasListHeader = hasListHeaderIn;
/* 102 */     this.headerPadding = headerPaddingIn;
/*     */     
/* 104 */     if (!hasListHeaderIn)
/*     */     {
/* 106 */       this.headerPadding = 0;
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract int getSize();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void elementClicked(int paramInt1, boolean paramBoolean, int paramInt2, int paramInt3);
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract boolean isSelected(int paramInt);
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/* 127 */     return getSize() * this.slotHeight + this.headerPadding;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawBackground();
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_178040_a(int p_178040_1_, int p_178040_2_, int p_178040_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected abstract void drawSlot(int paramInt1, int paramInt2, int paramInt3, int paramInt4, int paramInt5, int paramInt6);
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawListHeader(int p_148129_1_, int p_148129_2_, Tessellator p_148129_3_) {}
/*     */ 
/*     */ 
/*     */   
/*     */   protected void func_148132_a(int p_148132_1_, int p_148132_2_) {}
/*     */ 
/*     */   
/*     */   protected void func_148142_b(int p_148142_1_, int p_148142_2_) {}
/*     */ 
/*     */   
/*     */   public int getSlotIndexFromScreenCoords(int p_148124_1_, int p_148124_2_) {
/* 155 */     int i = this.left + this.width / 2 - getListWidth() / 2;
/* 156 */     int j = this.left + this.width / 2 + getListWidth() / 2;
/* 157 */     int k = p_148124_2_ - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 158 */     int l = k / this.slotHeight;
/* 159 */     return (p_148124_1_ < getScrollBarX() && p_148124_1_ >= i && p_148124_1_ <= j && l >= 0 && k >= 0 && l < getSize()) ? l : -1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void registerScrollButtons(int scrollUpButtonIDIn, int scrollDownButtonIDIn) {
/* 167 */     this.scrollUpButtonID = scrollUpButtonIDIn;
/* 168 */     this.scrollDownButtonID = scrollDownButtonIDIn;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void bindAmountScrolled() {
/* 176 */     this.amountScrolled = MathHelper.clamp_float(this.amountScrolled, 0.0F, func_148135_f());
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_148135_f() {
/* 181 */     return Math.max(0, getContentHeight() - this.bottom - this.top - 4);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getAmountScrolled() {
/* 189 */     return (int)this.amountScrolled;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isMouseYWithinSlotBounds(int p_148141_1_) {
/* 194 */     return (p_148141_1_ >= this.top && p_148141_1_ <= this.bottom && this.mouseX >= this.left && this.mouseX <= this.right);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void scrollBy(int amount) {
/* 202 */     this.amountScrolled += amount;
/* 203 */     bindAmountScrolled();
/* 204 */     this.initialClickY = -2;
/*     */   }
/*     */ 
/*     */   
/*     */   public void actionPerformed(GuiButton button) {
/* 209 */     if (button.enabled)
/*     */     {
/* 211 */       if (button.id == this.scrollUpButtonID) {
/*     */         
/* 213 */         this.amountScrolled -= (this.slotHeight * 2 / 3);
/* 214 */         this.initialClickY = -2;
/* 215 */         bindAmountScrolled();
/*     */       }
/* 217 */       else if (button.id == this.scrollDownButtonID) {
/*     */         
/* 219 */         this.amountScrolled += (this.slotHeight * 2 / 3);
/* 220 */         this.initialClickY = -2;
/* 221 */         bindAmountScrolled();
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/* 228 */     if (this.field_178041_q) {
/*     */       
/* 230 */       this.mouseX = mouseXIn;
/* 231 */       this.mouseY = mouseYIn;
/*     */ 
/*     */ 
/*     */       
/* 235 */       drawBackground();
/*     */ 
/*     */ 
/*     */       
/* 239 */       int i = getScrollBarX();
/* 240 */       int j = i + 6;
/* 241 */       bindAmountScrolled();
/* 242 */       GlStateManager.disableLighting();
/* 243 */       GlStateManager.disableFog();
/* 244 */       Tessellator tessellator = Tessellator.getInstance();
/* 245 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */       
/* 247 */       this.mc.getTextureManager().bindTexture(new ResourceLocation("eagler/background.png"));
/* 248 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 249 */       float f = 32.0F;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 256 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 257 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 259 */       if (this.hasListHeader)
/*     */       {
/* 261 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 264 */       drawSelectionBox(k, l, mouseXIn, mouseYIn);
/* 265 */       GlStateManager.disableDepth();
/* 266 */       int i1 = 4;
/*     */ 
/*     */ 
/*     */       
/* 270 */       overlayBackground(0, this.top, 255, 255);
/* 271 */       overlayBackground(this.bottom, this.height, 255, 255);
/*     */       
/* 273 */       GlStateManager.enableBlend();
/* 274 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 275 */       GlStateManager.disableAlpha();
/* 276 */       GlStateManager.shadeModel(7425);
/* 277 */       GlStateManager.disableTexture2D();
/* 278 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 279 */       worldrenderer.pos(this.left, (this.top + i1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 280 */       worldrenderer.pos(this.right, (this.top + i1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 0).endVertex();
/* 281 */       worldrenderer.pos(this.right, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 282 */       worldrenderer.pos(this.left, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 283 */       tessellator.draw();
/* 284 */       worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 285 */       worldrenderer.pos(this.left, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 286 */       worldrenderer.pos(this.right, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 287 */       worldrenderer.pos(this.right, (this.bottom - i1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 288 */       worldrenderer.pos(this.left, (this.bottom - i1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 0).endVertex();
/* 289 */       tessellator.draw();
/* 290 */       int j1 = func_148135_f();
/*     */       
/* 292 */       if (j1 > 0) {
/*     */         
/* 294 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 295 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 296 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 298 */         if (l1 < this.top)
/*     */         {
/* 300 */           l1 = this.top;
/*     */         }
/*     */         
/* 303 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 304 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 305 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 306 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 307 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 308 */         tessellator.draw();
/* 309 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 310 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 311 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 312 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 313 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 314 */         tessellator.draw();
/* 315 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 316 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 317 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 318 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 319 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 320 */         tessellator.draw();
/*     */       } 
/*     */       
/* 323 */       func_148142_b(mouseXIn, mouseYIn);
/* 324 */       GlStateManager.enableTexture2D();
/* 325 */       GlStateManager.shadeModel(7424);
/* 326 */       GlStateManager.enableAlpha();
/* 327 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/* 333 */     if (isMouseYWithinSlotBounds(this.mouseY)) {
/*     */       
/* 335 */       if (Mouse.getEventButton() == 0 && Mouse.getEventButtonState() && this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */         
/* 337 */         int i = (this.width - getListWidth()) / 2;
/* 338 */         int j = (this.width + getListWidth()) / 2;
/* 339 */         int k = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 340 */         int l = k / this.slotHeight;
/*     */         
/* 342 */         if (l < getSize() && this.mouseX >= i && this.mouseX <= j && l >= 0 && k >= 0) {
/*     */           
/* 344 */           elementClicked(l, false, this.mouseX, this.mouseY);
/* 345 */           this.selectedElement = l;
/*     */         }
/* 347 */         else if (this.mouseX >= i && this.mouseX <= j && k < 0) {
/*     */           
/* 349 */           func_148132_a(this.mouseX - i, this.mouseY - this.top + (int)this.amountScrolled - 4);
/*     */         } 
/*     */       } 
/*     */       
/* 353 */       if (Mouse.isButtonDown(0) && getEnabled()) {
/*     */         
/* 355 */         if (this.initialClickY == -1) {
/*     */           
/* 357 */           boolean flag1 = true;
/*     */           
/* 359 */           if (this.mouseY >= this.top && this.mouseY <= this.bottom) {
/*     */             
/* 361 */             int j2 = (this.width - getListWidth()) / 2;
/* 362 */             int k2 = (this.width + getListWidth()) / 2;
/* 363 */             int l2 = this.mouseY - this.top - this.headerPadding + (int)this.amountScrolled - 4;
/* 364 */             int i1 = l2 / this.slotHeight;
/*     */             
/* 366 */             if (i1 < getSize() && this.mouseX >= j2 && this.mouseX <= k2 && i1 >= 0 && l2 >= 0) {
/*     */               
/* 368 */               boolean flag = (i1 == this.selectedElement && Minecraft.getSystemTime() - this.lastClicked < 250L);
/* 369 */               elementClicked(i1, flag, this.mouseX, this.mouseY);
/* 370 */               this.selectedElement = i1;
/* 371 */               this.lastClicked = Minecraft.getSystemTime();
/*     */             }
/* 373 */             else if (this.mouseX >= j2 && this.mouseX <= k2 && l2 < 0) {
/*     */               
/* 375 */               func_148132_a(this.mouseX - j2, this.mouseY - this.top + (int)this.amountScrolled - 4);
/* 376 */               flag1 = false;
/*     */             } 
/*     */             
/* 379 */             int i3 = getScrollBarX();
/* 380 */             int j1 = i3 + 6;
/*     */             
/* 382 */             if (this.mouseX >= i3 && this.mouseX <= j1) {
/*     */               
/* 384 */               this.scrollMultiplier = -1.0F;
/* 385 */               int k1 = func_148135_f();
/*     */               
/* 387 */               if (k1 < 1)
/*     */               {
/* 389 */                 k1 = 1;
/*     */               }
/*     */               
/* 392 */               int l1 = (int)(((this.bottom - this.top) * (this.bottom - this.top)) / getContentHeight());
/* 393 */               l1 = MathHelper.clamp_int(l1, 32, this.bottom - this.top - 8);
/* 394 */               this.scrollMultiplier /= (this.bottom - this.top - l1) / k1;
/*     */             }
/*     */             else {
/*     */               
/* 398 */               this.scrollMultiplier = 1.0F;
/*     */             } 
/*     */             
/* 401 */             if (flag1)
/*     */             {
/* 403 */               this.initialClickY = this.mouseY;
/*     */             }
/*     */             else
/*     */             {
/* 407 */               this.initialClickY = -2;
/*     */             }
/*     */           
/*     */           } else {
/*     */             
/* 412 */             this.initialClickY = -2;
/*     */           }
/*     */         
/* 415 */         } else if (this.initialClickY >= 0) {
/*     */           
/* 417 */           this.amountScrolled -= (this.mouseY - this.initialClickY) * this.scrollMultiplier;
/* 418 */           this.initialClickY = this.mouseY;
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 423 */         this.initialClickY = -1;
/*     */       } 
/*     */       
/* 426 */       int i2 = Mouse.getEventDWheel();
/*     */       
/* 428 */       if (i2 != 0) {
/*     */         
/* 430 */         if (i2 > 0) {
/*     */           
/* 432 */           i2 = -1;
/*     */         }
/* 434 */         else if (i2 < 0) {
/*     */           
/* 436 */           i2 = 1;
/*     */         } 
/*     */         
/* 439 */         this.amountScrolled += (i2 * this.slotHeight / 2);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean enabledIn) {
/* 446 */     this.enabled = enabledIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnabled() {
/* 451 */     return this.enabled;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getListWidth() {
/* 459 */     return 220;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void drawSelectionBox(int p_148120_1_, int p_148120_2_, int mouseXIn, int mouseYIn) {
/* 467 */     int i = getSize();
/* 468 */     Tessellator tessellator = Tessellator.getInstance();
/* 469 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 471 */     for (int j = 0; j < i; j++) {
/*     */       
/* 473 */       int k = p_148120_2_ + j * this.slotHeight + this.headerPadding;
/* 474 */       int l = this.slotHeight - 4;
/*     */       
/* 476 */       if (k > this.bottom || k + l < this.top)
/*     */       {
/* 478 */         func_178040_a(j, p_148120_1_, k);
/*     */       }
/*     */       
/* 481 */       if (this.showSelectionBox && isSelected(j)) {
/*     */         
/* 483 */         int i1 = this.left + this.width / 2 - getListWidth() / 2;
/* 484 */         int j1 = this.left + this.width / 2 + getListWidth() / 2;
/* 485 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 486 */         GlStateManager.disableTexture2D();
/* 487 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 488 */         worldrenderer.pos(i1, (k + l + 2), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 489 */         worldrenderer.pos(j1, (k + l + 2), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 490 */         worldrenderer.pos(j1, (k - 2), 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 491 */         worldrenderer.pos(i1, (k - 2), 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 492 */         worldrenderer.pos((i1 + 1), (k + l + 1), 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 493 */         worldrenderer.pos((j1 - 1), (k + l + 1), 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 494 */         worldrenderer.pos((j1 - 1), (k - 1), 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 495 */         worldrenderer.pos((i1 + 1), (k - 1), 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 496 */         tessellator.draw();
/* 497 */         GlStateManager.enableTexture2D();
/*     */       } 
/*     */       
/* 500 */       drawSlot(j, p_148120_1_, k, l, mouseXIn, mouseYIn);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/* 506 */     return this.width / 2 + 124;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void overlayBackground(int startY, int endY, int startAlpha, int endAlpha) {
/* 514 */     Tessellator tessellator = Tessellator.getInstance();
/* 515 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*     */     
/* 517 */     ScaledResolution sr = new ScaledResolution(Minecraft.getMinecraft());
/*     */     
/* 519 */     if (Client.instance.getUtils().isTheme("Dark")) {
/*     */       
/* 521 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/background.png"));
/*     */     }
/* 523 */     else if (Client.instance.getUtils().isTheme("Bright")) {
/*     */       
/* 525 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/brightback.png"));
/*     */     } 
/*     */ 
/*     */     
/* 529 */     Gui.drawModalRectWithCustomSizedTexture(0, 0, 0.0F, 0.0F, sr.getScaledWidth(), 33, 
/* 530 */         sr.getScaledWidth(), 33.0F);
/*     */ 
/*     */ 
/*     */     
/* 534 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 535 */     float f = 32.0F;
/* 536 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 537 */     worldrenderer.pos(this.left, endY, 0.0D).tex(0.0D, (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 538 */     worldrenderer.pos((this.left + this.width), endY, 0.0D).tex((this.width / 32.0F), (endY / 32.0F)).color(64, 64, 64, endAlpha).endVertex();
/* 539 */     worldrenderer.pos((this.left + this.width), startY, 0.0D).tex((this.width / 32.0F), (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 540 */     worldrenderer.pos(this.left, startY, 0.0D).tex(0.0D, (startY / 32.0F)).color(64, 64, 64, startAlpha).endVertex();
/* 541 */     tessellator.draw();
/*     */ 
/*     */     
/* 544 */     if (Client.instance.getUtils().isTheme("Dark")) {
/*     */       
/* 546 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/background2.png"));
/*     */     }
/* 548 */     else if (Client.instance.getUtils().isTheme("Bright")) {
/*     */       
/* 550 */       Minecraft.getMinecraft().getTextureManager().bindTexture(new ResourceLocation("eagler/brightback2.png"));
/*     */     } 
/*     */ 
/*     */     
/* 554 */     Gui.drawModalRectWithCustomSizedTexture(0, sr.getScaledHeight() - 65, 0.0F, 0.0F, sr.getScaledWidth(), 75, sr.getScaledWidth(), 75.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSlotXBoundsFromLeft(int leftIn) {
/* 562 */     this.left = leftIn;
/* 563 */     this.right = leftIn + this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getSlotHeight() {
/* 568 */     return this.slotHeight;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSlot.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */