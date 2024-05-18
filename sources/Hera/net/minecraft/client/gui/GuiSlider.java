/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ 
/*     */ public class GuiSlider
/*     */   extends GuiButton {
/*   9 */   private float sliderPosition = 1.0F;
/*     */   
/*     */   public boolean isMouseDown;
/*     */   private String name;
/*     */   private final float min;
/*     */   private final float max;
/*     */   private final GuiPageButtonList.GuiResponder responder;
/*     */   private FormatHelper formatHelper;
/*     */   
/*     */   public GuiSlider(GuiPageButtonList.GuiResponder guiResponder, int idIn, int x, int y, String name, float min, float max, float defaultValue, FormatHelper formatter) {
/*  19 */     super(idIn, x, y, 150, 20, "");
/*  20 */     this.name = name;
/*  21 */     this.min = min;
/*  22 */     this.max = max;
/*  23 */     this.sliderPosition = (defaultValue - min) / (max - min);
/*  24 */     this.formatHelper = formatter;
/*  25 */     this.responder = guiResponder;
/*  26 */     this.displayString = getDisplayString();
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175220_c() {
/*  31 */     return this.min + (this.max - this.min) * this.sliderPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175218_a(float p_175218_1_, boolean p_175218_2_) {
/*  36 */     this.sliderPosition = (p_175218_1_ - this.min) / (this.max - this.min);
/*  37 */     this.displayString = getDisplayString();
/*     */     
/*  39 */     if (p_175218_2_)
/*     */     {
/*  41 */       this.responder.onTick(this.id, func_175220_c());
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   public float func_175217_d() {
/*  47 */     return this.sliderPosition;
/*     */   }
/*     */ 
/*     */   
/*     */   private String getDisplayString() {
/*  52 */     return (this.formatHelper == null) ? (String.valueOf(I18n.format(this.name, new Object[0])) + ": " + func_175220_c()) : this.formatHelper.getText(this.id, I18n.format(this.name, new Object[0]), func_175220_c());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  61 */     return 0;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/*  69 */     if (this.visible) {
/*     */       
/*  71 */       if (this.isMouseDown) {
/*     */         
/*  73 */         this.sliderPosition = (mouseX - this.xPosition + 4) / (this.width - 8);
/*     */         
/*  75 */         if (this.sliderPosition < 0.0F)
/*     */         {
/*  77 */           this.sliderPosition = 0.0F;
/*     */         }
/*     */         
/*  80 */         if (this.sliderPosition > 1.0F)
/*     */         {
/*  82 */           this.sliderPosition = 1.0F;
/*     */         }
/*     */         
/*  85 */         this.displayString = getDisplayString();
/*  86 */         this.responder.onTick(this.id, func_175220_c());
/*     */       } 
/*     */       
/*  89 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*  90 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/*  91 */       drawTexturedModalRect(this.xPosition + (int)(this.sliderPosition * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175219_a(float p_175219_1_) {
/*  97 */     this.sliderPosition = p_175219_1_;
/*  98 */     this.displayString = getDisplayString();
/*  99 */     this.responder.onTick(this.id, func_175220_c());
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 108 */     if (super.mousePressed(mc, mouseX, mouseY)) {
/*     */       
/* 110 */       this.sliderPosition = (mouseX - this.xPosition + 4) / (this.width - 8);
/*     */       
/* 112 */       if (this.sliderPosition < 0.0F)
/*     */       {
/* 114 */         this.sliderPosition = 0.0F;
/*     */       }
/*     */       
/* 117 */       if (this.sliderPosition > 1.0F)
/*     */       {
/* 119 */         this.sliderPosition = 1.0F;
/*     */       }
/*     */       
/* 122 */       this.displayString = getDisplayString();
/* 123 */       this.responder.onTick(this.id, func_175220_c());
/* 124 */       this.isMouseDown = true;
/* 125 */       return true;
/*     */     } 
/*     */ 
/*     */     
/* 129 */     return false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {
/* 138 */     this.isMouseDown = false;
/*     */   }
/*     */   
/*     */   public static interface FormatHelper {
/*     */     String getText(int param1Int, String param1String, float param1Float);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSlider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */