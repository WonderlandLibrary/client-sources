/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import me.eagler.Client;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuObject;
/*     */ import net.minecraft.client.gui.spectator.ISpectatorMenuRecipient;
/*     */ import net.minecraft.client.gui.spectator.SpectatorMenu;
/*     */ import net.minecraft.client.gui.spectator.categories.SpectatorDetails;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.renderer.RenderHelper;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiSpectator
/*     */   extends Gui
/*     */   implements ISpectatorMenuRecipient {
/*  19 */   private static final ResourceLocation field_175267_f = new ResourceLocation("textures/gui/widgets.png");
/*  20 */   public static final ResourceLocation field_175269_a = new ResourceLocation("textures/gui/spectator_widgets.png");
/*     */   
/*     */   private final Minecraft field_175268_g;
/*     */   private long field_175270_h;
/*     */   private SpectatorMenu field_175271_i;
/*     */   
/*     */   public GuiSpectator(Minecraft mcIn) {
/*  27 */     this.field_175268_g = mcIn;
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175260_a(int p_175260_1_) {
/*  32 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/*  34 */     if (this.field_175271_i != null) {
/*     */       
/*  36 */       this.field_175271_i.func_178644_b(p_175260_1_);
/*     */     }
/*     */     else {
/*     */       
/*  40 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private float func_175265_c() {
/*  46 */     long i = this.field_175270_h - Minecraft.getSystemTime() + 5000L;
/*  47 */     return MathHelper.clamp_float((float)i / 2000.0F, 0.0F, 1.0F);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void renderTooltip(ScaledResolution p_175264_1_, float p_175264_2_) {
/*  54 */     if (this.field_175271_i != null) {
/*     */       
/*  56 */       float f = func_175265_c();
/*     */       
/*  58 */       if (f <= 0.0F) {
/*     */         
/*  60 */         this.field_175271_i.func_178641_d();
/*     */       }
/*     */       else {
/*     */         
/*  64 */         int i = p_175264_1_.getScaledWidth() / 2;
/*  65 */         float f1 = this.zLevel;
/*  66 */         this.zLevel = -90.0F;
/*  67 */         float f2 = p_175264_1_.getScaledHeight() - 22.0F * f;
/*  68 */         SpectatorDetails spectatordetails = this.field_175271_i.func_178646_f();
/*  69 */         func_175258_a(p_175264_1_, f1, i, f2, spectatordetails);
/*  70 */         this.zLevel = f1;
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void func_175258_a(ScaledResolution p_175258_1_, float p_175258_2_, int p_175258_3_, float p_175258_4_, SpectatorDetails p_175258_5_) {
/*  77 */     GlStateManager.enableRescaleNormal();
/*  78 */     GlStateManager.enableBlend();
/*  79 */     GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/*  80 */     GlStateManager.color(1.0F, 1.0F, 1.0F, p_175258_2_);
/*     */     
/*  82 */     drawRect((p_175258_3_ - 92), (p_175258_1_.getScaledHeight() - 23), 184.0D, 1.0D, Color.black);
/*     */     
/*  84 */     drawRect((p_175258_3_ - 92), (p_175258_1_.getScaledHeight() - 22), 1.0D, 20.0D, Color.black);
/*     */     
/*  86 */     drawRect((p_175258_3_ - 91 + 182), (p_175258_1_.getScaledHeight() - 22), 1.0D, 20.0D, Color.black);
/*     */     
/*  88 */     drawRect((p_175258_3_ - 92), (p_175258_1_.getScaledHeight() - 2), 184.0D, 1.0D, Color.black);
/*     */     
/*  90 */     if (Client.instance.getUtils().isTheme("Dark")) {
/*     */       
/*  92 */       drawRect((p_175258_3_ - 91), (p_175258_1_.getScaledHeight() - 22), 182.0D, 20.0D, Color.DARK_GRAY);
/*     */     }
/*  94 */     else if (Client.instance.getUtils().isTheme("Bright")) {
/*     */       
/*  96 */       drawRect((p_175258_3_ - 91), (p_175258_1_.getScaledHeight() - 22), 182.0D, 20.0D, Color.white);
/*     */       
/*  98 */       Color color = new Color(150, 150, 150, 255);
/*     */     } 
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
/* 112 */     for (int i = 0; i < 9; i++)
/*     */     {
/* 114 */       func_175266_a(i, p_175258_1_.getScaledWidth() / 2 - 90 + i * 20 + 2, p_175258_4_ + 3.0F, p_175258_2_, p_175258_5_.func_178680_a(i));
/*     */     }
/*     */     
/* 117 */     RenderHelper.disableStandardItemLighting();
/* 118 */     GlStateManager.disableRescaleNormal();
/* 119 */     GlStateManager.disableBlend();
/*     */   }
/*     */ 
/*     */   
/*     */   private void func_175266_a(int p_175266_1_, int p_175266_2_, float p_175266_3_, float p_175266_4_, ISpectatorMenuObject p_175266_5_) {
/* 124 */     this.field_175268_g.getTextureManager().bindTexture(field_175269_a);
/*     */     
/* 126 */     if (p_175266_5_ != SpectatorMenu.field_178657_a) {
/*     */       
/* 128 */       int i = (int)(p_175266_4_ * 255.0F);
/* 129 */       GlStateManager.pushMatrix();
/* 130 */       GlStateManager.translate(p_175266_2_, p_175266_3_, 0.0F);
/* 131 */       float f = p_175266_5_.func_178662_A_() ? 1.0F : 0.25F;
/* 132 */       GlStateManager.color(f, f, f, p_175266_4_);
/* 133 */       p_175266_5_.func_178663_a(f, i);
/* 134 */       GlStateManager.popMatrix();
/* 135 */       String s = String.valueOf(GameSettings.getKeyDisplayString(this.field_175268_g.gameSettings.keyBindsHotbar[p_175266_1_].getKeyCode()));
/*     */       
/* 137 */       if (i > 3 && p_175266_5_.func_178662_A_())
/*     */       {
/* 139 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, (p_175266_2_ + 19 - 2 - this.field_175268_g.fontRendererObj.getStringWidth(s)), p_175266_3_ + 6.0F + 3.0F, 16777215 + (i << 24));
/*     */       }
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175263_a(ScaledResolution p_175263_1_) {
/* 146 */     int i = (int)(func_175265_c() * 255.0F);
/*     */     
/* 148 */     if (i > 3 && this.field_175271_i != null) {
/*     */       
/* 150 */       ISpectatorMenuObject ispectatormenuobject = this.field_175271_i.func_178645_b();
/* 151 */       String s = (ispectatormenuobject != SpectatorMenu.field_178657_a) ? ispectatormenuobject.getSpectatorName().getFormattedText() : this.field_175271_i.func_178650_c().func_178670_b().getFormattedText();
/*     */       
/* 153 */       if (s != null) {
/*     */         
/* 155 */         int j = (p_175263_1_.getScaledWidth() - this.field_175268_g.fontRendererObj.getStringWidth(s)) / 2;
/* 156 */         int k = p_175263_1_.getScaledHeight() - 35;
/* 157 */         GlStateManager.pushMatrix();
/* 158 */         GlStateManager.enableBlend();
/* 159 */         GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 160 */         this.field_175268_g.fontRendererObj.drawStringWithShadow(s, j, k, 16777215 + (i << 24));
/* 161 */         GlStateManager.disableBlend();
/* 162 */         GlStateManager.popMatrix();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175257_a(SpectatorMenu p_175257_1_) {
/* 169 */     this.field_175271_i = null;
/* 170 */     this.field_175270_h = 0L;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean func_175262_a() {
/* 175 */     return (this.field_175271_i != null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void func_175259_b(int p_175259_1_) {
/*     */     int i;
/* 182 */     for (i = this.field_175271_i.func_178648_e() + p_175259_1_; i >= 0 && i <= 8 && (this.field_175271_i.func_178643_a(i) == SpectatorMenu.field_178657_a || !this.field_175271_i.func_178643_a(i).func_178662_A_()); i += p_175259_1_);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 187 */     if (i >= 0 && i <= 8) {
/*     */       
/* 189 */       this.field_175271_i.func_178644_b(i);
/* 190 */       this.field_175270_h = Minecraft.getSystemTime();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void func_175261_b() {
/* 196 */     this.field_175270_h = Minecraft.getSystemTime();
/*     */     
/* 198 */     if (func_175262_a()) {
/*     */       
/* 200 */       int i = this.field_175271_i.func_178648_e();
/*     */       
/* 202 */       if (i != -1)
/*     */       {
/* 204 */         this.field_175271_i.func_178644_b(i);
/*     */       }
/*     */     }
/*     */     else {
/*     */       
/* 209 */       this.field_175271_i = new SpectatorMenu(this);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiSpectator.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */