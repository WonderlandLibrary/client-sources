/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.realms.RealmsSimpleScrolledSelectionList;
/*     */ import net.minecraft.util.MathHelper;
/*     */ 
/*     */ public class GuiSimpleScrolledSelectionListProxy
/*     */   extends GuiSlot
/*     */ {
/*     */   private final RealmsSimpleScrolledSelectionList field_178050_u;
/*     */   
/*     */   public GuiSimpleScrolledSelectionListProxy(RealmsSimpleScrolledSelectionList p_i45525_1_, int widthIn, int heightIn, int topIn, int bottomIn, int slotHeightIn) {
/*  17 */     super(Minecraft.getMinecraft(), widthIn, heightIn, topIn, bottomIn, slotHeightIn);
/*  18 */     this.field_178050_u = p_i45525_1_;
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getSize() {
/*  23 */     return this.field_178050_u.getItemCount();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void elementClicked(int slotIndex, boolean isDoubleClick, int mouseX, int mouseY) {
/*  31 */     this.field_178050_u.selectItem(slotIndex, isDoubleClick, mouseX, mouseY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean isSelected(int slotIndex) {
/*  39 */     return this.field_178050_u.isSelectedItem(slotIndex);
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawBackground() {
/*  44 */     this.field_178050_u.renderBackground();
/*     */   }
/*     */ 
/*     */   
/*     */   protected void drawSlot(int entryID, int p_180791_2_, int p_180791_3_, int p_180791_4_, int mouseXIn, int mouseYIn) {
/*  49 */     this.field_178050_u.renderItem(entryID, p_180791_2_, p_180791_3_, p_180791_4_, mouseXIn, mouseYIn);
/*     */   }
/*     */ 
/*     */   
/*     */   public int getWidth() {
/*  54 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseY() {
/*  59 */     return this.mouseY;
/*     */   }
/*     */ 
/*     */   
/*     */   public int getMouseX() {
/*  64 */     return this.mouseX;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getContentHeight() {
/*  72 */     return this.field_178050_u.getMaxPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   protected int getScrollBarX() {
/*  77 */     return this.field_178050_u.getScrollbarPosition();
/*     */   }
/*     */ 
/*     */   
/*     */   public void handleMouseInput() {
/*  82 */     super.handleMouseInput();
/*     */   }
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseXIn, int mouseYIn, float p_148128_3_) {
/*  87 */     if (this.field_178041_q) {
/*     */       
/*  89 */       this.mouseX = mouseXIn;
/*  90 */       this.mouseY = mouseYIn;
/*  91 */       drawBackground();
/*  92 */       int i = getScrollBarX();
/*  93 */       int j = i + 6;
/*  94 */       bindAmountScrolled();
/*  95 */       GlStateManager.disableLighting();
/*  96 */       GlStateManager.disableFog();
/*  97 */       Tessellator tessellator = Tessellator.getInstance();
/*  98 */       WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  99 */       int k = this.left + this.width / 2 - getListWidth() / 2 + 2;
/* 100 */       int l = this.top + 4 - (int)this.amountScrolled;
/*     */       
/* 102 */       if (this.hasListHeader)
/*     */       {
/* 104 */         drawListHeader(k, l, tessellator);
/*     */       }
/*     */       
/* 107 */       drawSelectionBox(k, l, mouseXIn, mouseYIn);
/* 108 */       GlStateManager.disableDepth();
/* 109 */       int i1 = 4;
/* 110 */       overlayBackground(0, this.top, 255, 255);
/* 111 */       overlayBackground(this.bottom, this.height, 255, 255);
/* 112 */       GlStateManager.enableBlend();
/* 113 */       GlStateManager.tryBlendFuncSeparate(770, 771, 0, 1);
/* 114 */       GlStateManager.disableAlpha();
/* 115 */       GlStateManager.shadeModel(7425);
/* 116 */       GlStateManager.disableTexture2D();
/* 117 */       int j1 = func_148135_f();
/*     */       
/* 119 */       if (j1 > 0) {
/*     */         
/* 121 */         int k1 = (this.bottom - this.top) * (this.bottom - this.top) / getContentHeight();
/* 122 */         k1 = MathHelper.clamp_int(k1, 32, this.bottom - this.top - 8);
/* 123 */         int l1 = (int)this.amountScrolled * (this.bottom - this.top - k1) / j1 + this.top;
/*     */         
/* 125 */         if (l1 < this.top)
/*     */         {
/* 127 */           l1 = this.top;
/*     */         }
/*     */         
/* 130 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 131 */         worldrenderer.pos(i, this.bottom, 0.0D).tex(0.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 132 */         worldrenderer.pos(j, this.bottom, 0.0D).tex(1.0D, 1.0D).color(0, 0, 0, 255).endVertex();
/* 133 */         worldrenderer.pos(j, this.top, 0.0D).tex(1.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 134 */         worldrenderer.pos(i, this.top, 0.0D).tex(0.0D, 0.0D).color(0, 0, 0, 255).endVertex();
/* 135 */         tessellator.draw();
/* 136 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 137 */         worldrenderer.pos(i, (l1 + k1), 0.0D).tex(0.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 138 */         worldrenderer.pos(j, (l1 + k1), 0.0D).tex(1.0D, 1.0D).color(128, 128, 128, 255).endVertex();
/* 139 */         worldrenderer.pos(j, l1, 0.0D).tex(1.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 140 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(128, 128, 128, 255).endVertex();
/* 141 */         tessellator.draw();
/* 142 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX_COLOR);
/* 143 */         worldrenderer.pos(i, (l1 + k1 - 1), 0.0D).tex(0.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 144 */         worldrenderer.pos((j - 1), (l1 + k1 - 1), 0.0D).tex(1.0D, 1.0D).color(192, 192, 192, 255).endVertex();
/* 145 */         worldrenderer.pos((j - 1), l1, 0.0D).tex(1.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 146 */         worldrenderer.pos(i, l1, 0.0D).tex(0.0D, 0.0D).color(192, 192, 192, 255).endVertex();
/* 147 */         tessellator.draw();
/*     */       } 
/*     */       
/* 150 */       func_148142_b(mouseXIn, mouseYIn);
/* 151 */       GlStateManager.enableTexture2D();
/* 152 */       GlStateManager.shadeModel(7424);
/* 153 */       GlStateManager.enableAlpha();
/* 154 */       GlStateManager.disableBlend();
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSimpleScrolledSelectionListProxy.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */