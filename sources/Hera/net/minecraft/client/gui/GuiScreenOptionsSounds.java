/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import java.io.IOException;
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundCategory;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.client.resources.I18n;
/*     */ import net.minecraft.client.settings.GameSettings;
/*     */ import net.minecraft.util.MathHelper;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ public class GuiScreenOptionsSounds
/*     */   extends GuiScreen
/*     */ {
/*     */   private final GuiScreen field_146505_f;
/*     */   private final GameSettings game_settings_4;
/*  20 */   protected String field_146507_a = "Options";
/*     */   
/*     */   private String field_146508_h;
/*     */   
/*     */   public GuiScreenOptionsSounds(GuiScreen p_i45025_1_, GameSettings p_i45025_2_) {
/*  25 */     this.field_146505_f = p_i45025_1_;
/*  26 */     this.game_settings_4 = p_i45025_2_;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void initGui() {
/*  35 */     int i = 0;
/*  36 */     this.field_146507_a = I18n.format("options.sounds.title", new Object[0]);
/*  37 */     this.field_146508_h = I18n.format("options.off", new Object[0]);
/*  38 */     this.buttonList.add(new Button(SoundCategory.MASTER.getCategoryId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), SoundCategory.MASTER, true));
/*  39 */     i += 2; byte b; int j;
/*     */     SoundCategory[] arrayOfSoundCategory;
/*  41 */     for (j = (arrayOfSoundCategory = SoundCategory.values()).length, b = 0; b < j; ) { SoundCategory soundcategory = arrayOfSoundCategory[b];
/*     */       
/*  43 */       if (soundcategory != SoundCategory.MASTER) {
/*     */         
/*  45 */         this.buttonList.add(new Button(soundcategory.getCategoryId(), this.width / 2 - 155 + i % 2 * 160, this.height / 6 - 12 + 24 * (i >> 1), soundcategory, false));
/*  46 */         i++;
/*     */       } 
/*     */       b++; }
/*     */     
/*  50 */     this.buttonList.add(new GuiButton(200, this.width / 2 - 100, this.height / 6 + 168, I18n.format("gui.done", new Object[0])));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void actionPerformed(GuiButton button) throws IOException {
/*  58 */     if (button.enabled)
/*     */     {
/*  60 */       if (button.id == 200) {
/*     */         
/*  62 */         this.mc.gameSettings.saveOptions();
/*  63 */         this.mc.displayGuiScreen(this.field_146505_f);
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/*  73 */     drawDefaultBackground();
/*  74 */     drawCenteredString(this.fontRendererObj, this.field_146507_a, this.width / 2, 15, 16777215);
/*  75 */     super.drawScreen(mouseX, mouseY, partialTicks);
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getSoundVolume(SoundCategory p_146504_1_) {
/*  80 */     float f = this.game_settings_4.getSoundLevel(p_146504_1_);
/*  81 */     return (f == 0.0F) ? this.field_146508_h : (String.valueOf((int)(f * 100.0F)) + "%");
/*     */   }
/*     */   
/*     */   class Button
/*     */     extends GuiButton {
/*     */     private final SoundCategory field_146153_r;
/*     */     private final String field_146152_s;
/*  88 */     public float field_146156_o = 1.0F;
/*     */     
/*     */     public boolean field_146155_p;
/*     */     
/*     */     public Button(int p_i45024_2_, int p_i45024_3_, int p_i45024_4_, SoundCategory p_i45024_5_, boolean p_i45024_6_) {
/*  93 */       super(p_i45024_2_, p_i45024_3_, p_i45024_4_, p_i45024_6_ ? 310 : 150, 20, "");
/*  94 */       this.field_146153_r = p_i45024_5_;
/*  95 */       this.field_146152_s = I18n.format("soundCategory." + p_i45024_5_.getCategoryName(), new Object[0]);
/*  96 */       this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(p_i45024_5_);
/*  97 */       this.field_146156_o = GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(p_i45024_5_);
/*     */     }
/*     */ 
/*     */     
/*     */     protected int getHoverState(boolean mouseOver) {
/* 102 */       return 0;
/*     */     }
/*     */ 
/*     */     
/*     */     protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {
/* 107 */       if (this.visible) {
/*     */         
/* 109 */         if (this.field_146155_p) {
/*     */           
/* 111 */           this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 112 */           this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 113 */           mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 114 */           mc.gameSettings.saveOptions();
/* 115 */           this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/*     */         } 
/*     */         
/* 118 */         GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 119 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)), this.yPosition, 0, 66, 4, 20);
/* 120 */         drawTexturedModalRect(this.xPosition + (int)(this.field_146156_o * (this.width - 8)) + 4, this.yPosition, 196, 66, 4, 20);
/*     */       } 
/*     */     }
/*     */ 
/*     */     
/*     */     public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 126 */       if (super.mousePressed(mc, mouseX, mouseY)) {
/*     */         
/* 128 */         this.field_146156_o = (mouseX - this.xPosition + 4) / (this.width - 8);
/* 129 */         this.field_146156_o = MathHelper.clamp_float(this.field_146156_o, 0.0F, 1.0F);
/* 130 */         mc.gameSettings.setSoundLevel(this.field_146153_r, this.field_146156_o);
/* 131 */         mc.gameSettings.saveOptions();
/* 132 */         this.displayString = String.valueOf(this.field_146152_s) + ": " + GuiScreenOptionsSounds.this.getSoundVolume(this.field_146153_r);
/* 133 */         this.field_146155_p = true;
/* 134 */         return true;
/*     */       } 
/*     */ 
/*     */       
/* 138 */       return false;
/*     */     }
/*     */ 
/*     */ 
/*     */     
/*     */     public void playPressSound(SoundHandler soundHandlerIn) {}
/*     */ 
/*     */ 
/*     */     
/*     */     public void mouseReleased(int mouseX, int mouseY) {
/* 148 */       if (this.field_146155_p) {
/*     */         
/* 150 */         if (this.field_146153_r == SoundCategory.MASTER) {
/*     */           
/* 152 */           float f = 1.0F;
/*     */         }
/*     */         else {
/*     */           
/* 156 */           GuiScreenOptionsSounds.this.game_settings_4.getSoundLevel(this.field_146153_r);
/*     */         } 
/*     */         
/* 159 */         GuiScreenOptionsSounds.this.mc.getSoundHandler().playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */       } 
/*     */       
/* 162 */       this.field_146155_p = false;
/*     */     }
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiScreenOptionsSounds.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */