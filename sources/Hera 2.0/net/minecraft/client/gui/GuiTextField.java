/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import com.google.common.base.Predicate;
/*     */ import com.google.common.base.Predicates;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ChatAllowedCharacters;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiTextField
/*     */   extends Gui
/*     */ {
/*     */   private final int id;
/*     */   private final FontRenderer fontRendererInstance;
/*     */   public int xPosition;
/*     */   public int yPosition;
/*     */   private final int width;
/*     */   private final int height;
/*  24 */   private String text = "";
/*  25 */   private int maxStringLength = 32;
/*     */ 
/*     */   
/*     */   private int cursorCounter;
/*     */ 
/*     */   
/*     */   private boolean enableBackgroundDrawing = true;
/*     */ 
/*     */   
/*     */   private boolean canLoseFocus = true;
/*     */ 
/*     */   
/*     */   private boolean isFocused;
/*     */ 
/*     */   
/*     */   private boolean isEnabled = true;
/*     */ 
/*     */   
/*     */   private int lineScrollOffset;
/*     */ 
/*     */   
/*     */   private int cursorPosition;
/*     */ 
/*     */   
/*     */   private int selectionEnd;
/*     */ 
/*     */   
/*  52 */   private int enabledColor = 14737632;
/*  53 */   private int disabledColor = 7368816;
/*     */   
/*     */   private boolean visible = true;
/*     */   
/*     */   private GuiPageButtonList.GuiResponder field_175210_x;
/*  58 */   private Predicate<String> field_175209_y = Predicates.alwaysTrue();
/*     */ 
/*     */   
/*     */   public GuiTextField(int componentId, FontRenderer fontrendererObj, int x, int y, int par5Width, int par6Height) {
/*  62 */     this.id = componentId;
/*  63 */     this.fontRendererInstance = fontrendererObj;
/*  64 */     this.xPosition = x;
/*  65 */     this.yPosition = y;
/*  66 */     this.width = par5Width;
/*  67 */     this.height = par6Height;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175207_a(GuiPageButtonList.GuiResponder p_175207_1_) {
/*  72 */     this.field_175210_x = p_175207_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void updateCursorCounter() {
/*  80 */     this.cursorCounter++;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setText(String p_146180_1_) {
/*  88 */     if (this.field_175209_y.apply(p_146180_1_)) {
/*     */       
/*  90 */       if (p_146180_1_.length() > this.maxStringLength) {
/*     */         
/*  92 */         this.text = p_146180_1_.substring(0, this.maxStringLength);
/*     */       }
/*     */       else {
/*     */         
/*  96 */         this.text = p_146180_1_;
/*     */       } 
/*     */       
/*  99 */       setCursorPositionEnd();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getText() {
/* 108 */     return this.text;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public String getSelectedText() {
/* 116 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 117 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 118 */     return this.text.substring(i, j);
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175205_a(Predicate<String> p_175205_1_) {
/* 123 */     this.field_175209_y = p_175205_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void writeText(String p_146191_1_) {
/* 131 */     String s = "";
/* 132 */     String s1 = ChatAllowedCharacters.filterAllowedCharacters(p_146191_1_);
/* 133 */     int i = (this.cursorPosition < this.selectionEnd) ? this.cursorPosition : this.selectionEnd;
/* 134 */     int j = (this.cursorPosition < this.selectionEnd) ? this.selectionEnd : this.cursorPosition;
/* 135 */     int k = this.maxStringLength - this.text.length() - i - j;
/* 136 */     int l = 0;
/*     */     
/* 138 */     if (this.text.length() > 0)
/*     */     {
/* 140 */       s = String.valueOf(s) + this.text.substring(0, i);
/*     */     }
/*     */     
/* 143 */     if (k < s1.length()) {
/*     */       
/* 145 */       s = String.valueOf(s) + s1.substring(0, k);
/* 146 */       l = k;
/*     */     }
/*     */     else {
/*     */       
/* 150 */       s = String.valueOf(s) + s1;
/* 151 */       l = s1.length();
/*     */     } 
/*     */     
/* 154 */     if (this.text.length() > 0 && j < this.text.length())
/*     */     {
/* 156 */       s = String.valueOf(s) + this.text.substring(j);
/*     */     }
/*     */     
/* 159 */     if (this.field_175209_y.apply(s)) {
/*     */       
/* 161 */       this.text = s;
/* 162 */       moveCursorBy(i - this.selectionEnd + l);
/*     */       
/* 164 */       if (this.field_175210_x != null)
/*     */       {
/* 166 */         this.field_175210_x.func_175319_a(this.id, this.text);
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteWords(int p_146177_1_) {
/* 177 */     if (this.text.length() != 0)
/*     */     {
/* 179 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 181 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 185 */         deleteFromCursor(getNthWordFromCursor(p_146177_1_) - this.cursorPosition);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void deleteFromCursor(int p_146175_1_) {
/* 195 */     if (this.text.length() != 0)
/*     */     {
/* 197 */       if (this.selectionEnd != this.cursorPosition) {
/*     */         
/* 199 */         writeText("");
/*     */       }
/*     */       else {
/*     */         
/* 203 */         boolean flag = (p_146175_1_ < 0);
/* 204 */         int i = flag ? (this.cursorPosition + p_146175_1_) : this.cursorPosition;
/* 205 */         int j = flag ? this.cursorPosition : (this.cursorPosition + p_146175_1_);
/* 206 */         String s = "";
/*     */         
/* 208 */         if (i >= 0)
/*     */         {
/* 210 */           s = this.text.substring(0, i);
/*     */         }
/*     */         
/* 213 */         if (j < this.text.length())
/*     */         {
/* 215 */           s = String.valueOf(s) + this.text.substring(j);
/*     */         }
/*     */         
/* 218 */         if (this.field_175209_y.apply(s)) {
/*     */           
/* 220 */           this.text = s;
/*     */           
/* 222 */           if (flag)
/*     */           {
/* 224 */             moveCursorBy(p_146175_1_);
/*     */           }
/*     */           
/* 227 */           if (this.field_175210_x != null)
/*     */           {
/* 229 */             this.field_175210_x.func_175319_a(this.id, this.text);
/*     */           }
/*     */         } 
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public int getId() {
/* 238 */     return this.id;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromCursor(int p_146187_1_) {
/* 246 */     return getNthWordFromPos(p_146187_1_, getCursorPosition());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getNthWordFromPos(int p_146183_1_, int p_146183_2_) {
/* 254 */     return func_146197_a(p_146183_1_, p_146183_2_, true);
/*     */   }
/*     */ 
/*     */   
/*     */   public int func_146197_a(int p_146197_1_, int p_146197_2_, boolean p_146197_3_) {
/* 259 */     int i = p_146197_2_;
/* 260 */     boolean flag = (p_146197_1_ < 0);
/* 261 */     int j = Math.abs(p_146197_1_);
/*     */     
/* 263 */     for (int k = 0; k < j; k++) {
/*     */       
/* 265 */       if (!flag) {
/*     */         
/* 267 */         int l = this.text.length();
/* 268 */         i = this.text.indexOf(' ', i);
/*     */         
/* 270 */         if (i == -1)
/*     */         {
/* 272 */           i = l;
/*     */         }
/*     */         else
/*     */         {
/* 276 */           while (p_146197_3_ && i < l && this.text.charAt(i) == ' ')
/*     */           {
/* 278 */             i++;
/*     */           }
/*     */         }
/*     */       
/*     */       } else {
/*     */         
/* 284 */         while (p_146197_3_ && i > 0 && this.text.charAt(i - 1) == ' ')
/*     */         {
/* 286 */           i--;
/*     */         }
/*     */         
/* 289 */         while (i > 0 && this.text.charAt(i - 1) != ' ')
/*     */         {
/* 291 */           i--;
/*     */         }
/*     */       } 
/*     */     } 
/*     */     
/* 296 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void moveCursorBy(int p_146182_1_) {
/* 304 */     setCursorPosition(this.selectionEnd + p_146182_1_);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPosition(int p_146190_1_) {
/* 312 */     this.cursorPosition = p_146190_1_;
/* 313 */     int i = this.text.length();
/* 314 */     this.cursorPosition = MathHelper.clamp_int(this.cursorPosition, 0, i);
/* 315 */     setSelectionPos(this.cursorPosition);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionZero() {
/* 323 */     setCursorPosition(0);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCursorPositionEnd() {
/* 331 */     setCursorPosition(this.text.length());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean textboxKeyTyped(char p_146201_1_, int p_146201_2_) {
/* 339 */     if (!this.isFocused)
/*     */     {
/* 341 */       return false;
/*     */     }
/* 343 */     if (GuiScreen.isKeyComboCtrlA(p_146201_2_)) {
/*     */       
/* 345 */       setCursorPositionEnd();
/* 346 */       setSelectionPos(0);
/* 347 */       return true;
/*     */     } 
/* 349 */     if (GuiScreen.isKeyComboCtrlC(p_146201_2_)) {
/*     */       
/* 351 */       GuiScreen.setClipboardString(getSelectedText());
/* 352 */       return true;
/*     */     } 
/* 354 */     if (GuiScreen.isKeyComboCtrlV(p_146201_2_)) {
/*     */       
/* 356 */       if (this.isEnabled)
/*     */       {
/* 358 */         writeText(GuiScreen.getClipboardString());
/*     */       }
/*     */       
/* 361 */       return true;
/*     */     } 
/* 363 */     if (GuiScreen.isKeyComboCtrlX(p_146201_2_)) {
/*     */       
/* 365 */       GuiScreen.setClipboardString(getSelectedText());
/*     */       
/* 367 */       if (this.isEnabled)
/*     */       {
/* 369 */         writeText("");
/*     */       }
/*     */       
/* 372 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 376 */     switch (p_146201_2_) {
/*     */       
/*     */       case 14:
/* 379 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 381 */           if (this.isEnabled)
/*     */           {
/* 383 */             deleteWords(-1);
/*     */           }
/*     */         }
/* 386 */         else if (this.isEnabled) {
/*     */           
/* 388 */           deleteFromCursor(-1);
/*     */         } 
/*     */         
/* 391 */         return true;
/*     */       
/*     */       case 199:
/* 394 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 396 */           setSelectionPos(0);
/*     */         }
/*     */         else {
/*     */           
/* 400 */           setCursorPositionZero();
/*     */         } 
/*     */         
/* 403 */         return true;
/*     */       
/*     */       case 203:
/* 406 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 408 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 410 */             setSelectionPos(getNthWordFromPos(-1, getSelectionEnd()));
/*     */           }
/*     */           else
/*     */           {
/* 414 */             setSelectionPos(getSelectionEnd() - 1);
/*     */           }
/*     */         
/* 417 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 419 */           setCursorPosition(getNthWordFromCursor(-1));
/*     */         }
/*     */         else {
/*     */           
/* 423 */           moveCursorBy(-1);
/*     */         } 
/*     */         
/* 426 */         return true;
/*     */       
/*     */       case 205:
/* 429 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 431 */           if (GuiScreen.isCtrlKeyDown())
/*     */           {
/* 433 */             setSelectionPos(getNthWordFromPos(1, getSelectionEnd()));
/*     */           }
/*     */           else
/*     */           {
/* 437 */             setSelectionPos(getSelectionEnd() + 1);
/*     */           }
/*     */         
/* 440 */         } else if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 442 */           setCursorPosition(getNthWordFromCursor(1));
/*     */         }
/*     */         else {
/*     */           
/* 446 */           moveCursorBy(1);
/*     */         } 
/*     */         
/* 449 */         return true;
/*     */       
/*     */       case 207:
/* 452 */         if (GuiScreen.isShiftKeyDown()) {
/*     */           
/* 454 */           setSelectionPos(this.text.length());
/*     */         }
/*     */         else {
/*     */           
/* 458 */           setCursorPositionEnd();
/*     */         } 
/*     */         
/* 461 */         return true;
/*     */       
/*     */       case 211:
/* 464 */         if (GuiScreen.isCtrlKeyDown()) {
/*     */           
/* 466 */           if (this.isEnabled)
/*     */           {
/* 468 */             deleteWords(1);
/*     */           }
/*     */         }
/* 471 */         else if (this.isEnabled) {
/*     */           
/* 473 */           deleteFromCursor(1);
/*     */         } 
/*     */         
/* 476 */         return true;
/*     */     } 
/*     */     
/* 479 */     if (ChatAllowedCharacters.isAllowedCharacter(p_146201_1_)) {
/*     */       
/* 481 */       if (this.isEnabled)
/*     */       {
/* 483 */         writeText(Character.toString(p_146201_1_));
/*     */       }
/*     */       
/* 486 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 490 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseClicked(int p_146192_1_, int p_146192_2_, int p_146192_3_) {
/* 501 */     boolean flag = (p_146192_1_ >= this.xPosition && p_146192_1_ < this.xPosition + this.width && p_146192_2_ >= this.yPosition && p_146192_2_ < this.yPosition + this.height);
/*     */     
/* 503 */     if (this.canLoseFocus)
/*     */     {
/* 505 */       setFocused(flag);
/*     */     }
/*     */     
/* 508 */     if (this.isFocused && flag && p_146192_3_ == 0) {
/*     */       
/* 510 */       int i = p_146192_1_ - this.xPosition;
/*     */       
/* 512 */       if (this.enableBackgroundDrawing)
/*     */       {
/* 514 */         i -= 4;
/*     */       }
/*     */       
/* 517 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 518 */       setCursorPosition(this.fontRendererInstance.trimStringToWidth(s, i).length() + this.lineScrollOffset);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawTextBox() {
/* 527 */     if (getVisible()) {
/*     */       
/* 529 */       if (getEnableBackgroundDrawing()) {
/*     */         
/* 531 */         drawRect(this.xPosition - 1, this.yPosition - 1, this.xPosition + this.width + 1, this.yPosition + this.height + 1, -6250336);
/* 532 */         drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, -16777216);
/*     */       } 
/*     */       
/* 535 */       int i = this.isEnabled ? this.enabledColor : this.disabledColor;
/* 536 */       int j = this.cursorPosition - this.lineScrollOffset;
/* 537 */       int k = this.selectionEnd - this.lineScrollOffset;
/* 538 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), getWidth());
/* 539 */       boolean flag = (j >= 0 && j <= s.length());
/* 540 */       boolean flag1 = (this.isFocused && this.cursorCounter / 6 % 2 == 0 && flag);
/* 541 */       int l = this.enableBackgroundDrawing ? (this.xPosition + 4) : this.xPosition;
/* 542 */       int i1 = this.enableBackgroundDrawing ? (this.yPosition + (this.height - 8) / 2) : this.yPosition;
/* 543 */       int j1 = l;
/*     */       
/* 545 */       if (k > s.length())
/*     */       {
/* 547 */         k = s.length();
/*     */       }
/*     */       
/* 550 */       if (s.length() > 0) {
/*     */         
/* 552 */         String s1 = flag ? s.substring(0, j) : s;
/* 553 */         j1 = this.fontRendererInstance.drawStringWithShadow(s1, l, i1, i);
/*     */       } 
/*     */       
/* 556 */       boolean flag2 = !(this.cursorPosition >= this.text.length() && this.text.length() < getMaxStringLength());
/* 557 */       int k1 = j1;
/*     */       
/* 559 */       if (!flag) {
/*     */         
/* 561 */         k1 = (j > 0) ? (l + this.width) : l;
/*     */       }
/* 563 */       else if (flag2) {
/*     */         
/* 565 */         k1 = j1 - 1;
/* 566 */         j1--;
/*     */       } 
/*     */       
/* 569 */       if (s.length() > 0 && flag && j < s.length())
/*     */       {
/* 571 */         j1 = this.fontRendererInstance.drawStringWithShadow(s.substring(j), j1, i1, i);
/*     */       }
/*     */       
/* 574 */       if (flag1)
/*     */       {
/* 576 */         if (flag2) {
/*     */           
/* 578 */           Gui.drawRect(k1, i1 - 1, k1 + 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT, -3092272);
/*     */         }
/*     */         else {
/*     */           
/* 582 */           this.fontRendererInstance.drawStringWithShadow("_", k1, i1, i);
/*     */         } 
/*     */       }
/*     */       
/* 586 */       if (k != j) {
/*     */         
/* 588 */         int l1 = l + this.fontRendererInstance.getStringWidth(s.substring(0, k));
/* 589 */         drawCursorVertical(k1, i1 - 1, l1 - 1, i1 + 1 + this.fontRendererInstance.FONT_HEIGHT);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void drawCursorVertical(int p_146188_1_, int p_146188_2_, int p_146188_3_, int p_146188_4_) {
/* 599 */     if (p_146188_1_ < p_146188_3_) {
/*     */       
/* 601 */       int i = p_146188_1_;
/* 602 */       p_146188_1_ = p_146188_3_;
/* 603 */       p_146188_3_ = i;
/*     */     } 
/*     */     
/* 606 */     if (p_146188_2_ < p_146188_4_) {
/*     */       
/* 608 */       int j = p_146188_2_;
/* 609 */       p_146188_2_ = p_146188_4_;
/* 610 */       p_146188_4_ = j;
/*     */     } 
/*     */     
/* 613 */     if (p_146188_3_ > this.xPosition + this.width)
/*     */     {
/* 615 */       p_146188_3_ = this.xPosition + this.width;
/*     */     }
/*     */     
/* 618 */     if (p_146188_1_ > this.xPosition + this.width)
/*     */     {
/* 620 */       p_146188_1_ = this.xPosition + this.width;
/*     */     }
/*     */     
/* 623 */     Tessellator tessellator = Tessellator.getInstance();
/* 624 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/* 625 */     GlStateManager.color(0.0F, 0.0F, 255.0F, 255.0F);
/* 626 */     GlStateManager.disableTexture2D();
/* 627 */     GlStateManager.enableColorLogic();
/* 628 */     GlStateManager.colorLogicOp(5387);
/* 629 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/* 630 */     worldrenderer.pos(p_146188_1_, p_146188_4_, 0.0D).endVertex();
/* 631 */     worldrenderer.pos(p_146188_3_, p_146188_4_, 0.0D).endVertex();
/* 632 */     worldrenderer.pos(p_146188_3_, p_146188_2_, 0.0D).endVertex();
/* 633 */     worldrenderer.pos(p_146188_1_, p_146188_2_, 0.0D).endVertex();
/* 634 */     tessellator.draw();
/* 635 */     GlStateManager.disableColorLogic();
/* 636 */     GlStateManager.enableTexture2D();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setMaxStringLength(int p_146203_1_) {
/* 641 */     this.maxStringLength = p_146203_1_;
/*     */     
/* 643 */     if (this.text.length() > p_146203_1_)
/*     */     {
/* 645 */       this.text = this.text.substring(0, p_146203_1_);
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getMaxStringLength() {
/* 654 */     return this.maxStringLength;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getCursorPosition() {
/* 662 */     return this.cursorPosition;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getEnableBackgroundDrawing() {
/* 670 */     return this.enableBackgroundDrawing;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setEnableBackgroundDrawing(boolean p_146185_1_) {
/* 678 */     this.enableBackgroundDrawing = p_146185_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTextColor(int p_146193_1_) {
/* 686 */     this.enabledColor = p_146193_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setDisabledTextColour(int p_146204_1_) {
/* 691 */     this.disabledColor = p_146204_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setFocused(boolean p_146195_1_) {
/* 699 */     if (p_146195_1_ && !this.isFocused)
/*     */     {
/* 701 */       this.cursorCounter = 0;
/*     */     }
/*     */     
/* 704 */     this.isFocused = p_146195_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isFocused() {
/* 712 */     return this.isFocused;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabled(boolean p_146184_1_) {
/* 717 */     this.isEnabled = p_146184_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getSelectionEnd() {
/* 725 */     return this.selectionEnd;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int getWidth() {
/* 733 */     return getEnableBackgroundDrawing() ? (this.width - 8) : this.width;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setSelectionPos(int p_146199_1_) {
/* 741 */     int i = this.text.length();
/*     */     
/* 743 */     if (p_146199_1_ > i)
/*     */     {
/* 745 */       p_146199_1_ = i;
/*     */     }
/*     */     
/* 748 */     if (p_146199_1_ < 0)
/*     */     {
/* 750 */       p_146199_1_ = 0;
/*     */     }
/*     */     
/* 753 */     this.selectionEnd = p_146199_1_;
/*     */     
/* 755 */     if (this.fontRendererInstance != null) {
/*     */       
/* 757 */       if (this.lineScrollOffset > i)
/*     */       {
/* 759 */         this.lineScrollOffset = i;
/*     */       }
/*     */       
/* 762 */       int j = getWidth();
/* 763 */       String s = this.fontRendererInstance.trimStringToWidth(this.text.substring(this.lineScrollOffset), j);
/* 764 */       int k = s.length() + this.lineScrollOffset;
/*     */       
/* 766 */       if (p_146199_1_ == this.lineScrollOffset)
/*     */       {
/* 768 */         this.lineScrollOffset -= this.fontRendererInstance.trimStringToWidth(this.text, j, true).length();
/*     */       }
/*     */       
/* 771 */       if (p_146199_1_ > k) {
/*     */         
/* 773 */         this.lineScrollOffset += p_146199_1_ - k;
/*     */       }
/* 775 */       else if (p_146199_1_ <= this.lineScrollOffset) {
/*     */         
/* 777 */         this.lineScrollOffset -= this.lineScrollOffset - p_146199_1_;
/*     */       } 
/*     */       
/* 780 */       this.lineScrollOffset = MathHelper.clamp_int(this.lineScrollOffset, 0, i);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setCanLoseFocus(boolean p_146205_1_) {
/* 789 */     this.canLoseFocus = p_146205_1_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean getVisible() {
/* 797 */     return this.visible;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setVisible(boolean p_146189_1_) {
/* 805 */     this.visible = p_146189_1_;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiTextField.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */