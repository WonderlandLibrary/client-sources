/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.Tessellator;
/*     */ import net.minecraft.client.renderer.WorldRenderer;
/*     */ import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiStreamIndicator
/*     */ {
/*  12 */   private static final ResourceLocation locationStreamIndicator = new ResourceLocation("textures/gui/stream_indicator.png");
/*     */   private final Minecraft mc;
/*  14 */   private float field_152443_c = 1.0F;
/*  15 */   private int field_152444_d = 1;
/*     */ 
/*     */   
/*     */   public GuiStreamIndicator(Minecraft mcIn) {
/*  19 */     this.mc = mcIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void render(int p_152437_1_, int p_152437_2_) {
/*  24 */     if (this.mc.getTwitchStream().isBroadcasting()) {
/*     */       
/*  26 */       GlStateManager.enableBlend();
/*  27 */       int i = this.mc.getTwitchStream().func_152920_A();
/*     */       
/*  29 */       if (i > 0) {
/*     */         
/*  31 */         int m = i;
/*  32 */         int j = this.mc.fontRendererObj.getStringWidth(m);
/*  33 */         int k = 20;
/*  34 */         int l = p_152437_1_ - j - 1;
/*  35 */         int i1 = p_152437_2_ + 20 - 1;
/*  36 */         int j1 = p_152437_2_ + 20 + this.mc.fontRendererObj.FONT_HEIGHT - 1;
/*  37 */         GlStateManager.disableTexture2D();
/*  38 */         Tessellator tessellator = Tessellator.getInstance();
/*  39 */         WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  40 */         GlStateManager.color(0.0F, 0.0F, 0.0F, (0.65F + 0.35000002F * this.field_152443_c) / 2.0F);
/*  41 */         worldrenderer.begin(7, DefaultVertexFormats.POSITION);
/*  42 */         worldrenderer.pos(l, j1, 0.0D).endVertex();
/*  43 */         worldrenderer.pos(p_152437_1_, j1, 0.0D).endVertex();
/*  44 */         worldrenderer.pos(p_152437_1_, i1, 0.0D).endVertex();
/*  45 */         worldrenderer.pos(l, i1, 0.0D).endVertex();
/*  46 */         tessellator.draw();
/*  47 */         GlStateManager.enableTexture2D();
/*  48 */         this.mc.fontRendererObj.drawString(m, p_152437_1_ - j, p_152437_2_ + 20, 16777215);
/*     */       } 
/*     */       
/*  51 */       render(p_152437_1_, p_152437_2_, func_152440_b(), 0);
/*  52 */       render(p_152437_1_, p_152437_2_, func_152438_c(), 17);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void render(int p_152436_1_, int p_152436_2_, int p_152436_3_, int p_152436_4_) {
/*  58 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 0.65F + 0.35000002F * this.field_152443_c);
/*  59 */     this.mc.getTextureManager().bindTexture(locationStreamIndicator);
/*  60 */     float f = 150.0F;
/*  61 */     float f1 = 0.0F;
/*  62 */     float f2 = p_152436_3_ * 0.015625F;
/*  63 */     float f3 = 1.0F;
/*  64 */     float f4 = (p_152436_3_ + 16) * 0.015625F;
/*  65 */     Tessellator tessellator = Tessellator.getInstance();
/*  66 */     WorldRenderer worldrenderer = tessellator.getWorldRenderer();
/*  67 */     worldrenderer.begin(7, DefaultVertexFormats.POSITION_TEX);
/*  68 */     worldrenderer.pos((p_152436_1_ - 16 - p_152436_4_), (p_152436_2_ + 16), f).tex(f1, f4).endVertex();
/*  69 */     worldrenderer.pos((p_152436_1_ - p_152436_4_), (p_152436_2_ + 16), f).tex(f3, f4).endVertex();
/*  70 */     worldrenderer.pos((p_152436_1_ - p_152436_4_), (p_152436_2_ + 0), f).tex(f3, f2).endVertex();
/*  71 */     worldrenderer.pos((p_152436_1_ - 16 - p_152436_4_), (p_152436_2_ + 0), f).tex(f1, f2).endVertex();
/*  72 */     tessellator.draw();
/*  73 */     GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_152440_b() {
/*  78 */     return this.mc.getTwitchStream().isPaused() ? 16 : 0;
/*     */   }
/*     */ 
/*     */   
/*     */   private int func_152438_c() {
/*  83 */     return this.mc.getTwitchStream().func_152929_G() ? 48 : 32;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_152439_a() {
/*  88 */     if (this.mc.getTwitchStream().isBroadcasting()) {
/*     */       
/*  90 */       this.field_152443_c += 0.025F * this.field_152444_d;
/*     */       
/*  92 */       if (this.field_152443_c < 0.0F)
/*     */       {
/*  94 */         this.field_152444_d *= -1;
/*  95 */         this.field_152443_c = 0.0F;
/*     */       }
/*  97 */       else if (this.field_152443_c > 1.0F)
/*     */       {
/*  99 */         this.field_152444_d *= -1;
/* 100 */         this.field_152443_c = 1.0F;
/*     */       }
/*     */     
/*     */     } else {
/*     */       
/* 105 */       this.field_152443_c = 1.0F;
/* 106 */       this.field_152444_d = 1;
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiStreamIndicator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */