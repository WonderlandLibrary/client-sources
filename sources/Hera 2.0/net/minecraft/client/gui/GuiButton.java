/*     */ package net.minecraft.client.gui;
/*     */ 
/*     */ import net.minecraft.client.Minecraft;
/*     */ import net.minecraft.client.audio.ISound;
/*     */ import net.minecraft.client.audio.PositionedSoundRecord;
/*     */ import net.minecraft.client.audio.SoundHandler;
/*     */ import net.minecraft.client.renderer.GlStateManager;
/*     */ import net.minecraft.util.ResourceLocation;
/*     */ 
/*     */ 
/*     */ 
/*     */ public class GuiButton
/*     */   extends Gui
/*     */ {
/*  15 */   protected static final ResourceLocation buttonTextures = new ResourceLocation("textures/gui/widgets.png");
/*     */ 
/*     */   
/*     */   protected int width;
/*     */ 
/*     */   
/*     */   protected int height;
/*     */ 
/*     */   
/*     */   public int xPosition;
/*     */ 
/*     */   
/*     */   public int yPosition;
/*     */ 
/*     */   
/*     */   public String displayString;
/*     */   
/*     */   public int id;
/*     */   
/*     */   public boolean enabled;
/*     */   
/*     */   public boolean visible;
/*     */   
/*     */   protected boolean hovered;
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, String buttonText) {
/*  42 */     this(buttonId, x, y, 200, 20, buttonText);
/*     */   }
/*     */ 
/*     */   
/*     */   public GuiButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/*  47 */     this.width = 200;
/*  48 */     this.height = 20;
/*  49 */     this.enabled = true;
/*  50 */     this.visible = true;
/*  51 */     this.id = buttonId;
/*  52 */     this.xPosition = x;
/*  53 */     this.yPosition = y;
/*  54 */     this.width = widthIn;
/*  55 */     this.height = heightIn;
/*  56 */     this.displayString = buttonText;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected int getHoverState(boolean mouseOver) {
/*  65 */     int i = 1;
/*     */     
/*  67 */     if (!this.enabled) {
/*     */       
/*  69 */       i = 0;
/*     */     }
/*  71 */     else if (mouseOver) {
/*     */       
/*  73 */       i = 2;
/*     */     } 
/*     */     
/*  76 */     return i;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/*  84 */     if (this.visible) {
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
/* 114 */       FontRenderer fontrenderer = mc.fontRendererObj;
/* 115 */       mc.getTextureManager().bindTexture(buttonTextures);
/* 116 */       GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
/* 117 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/* 118 */       int i = getHoverState(this.hovered);
/* 119 */       GlStateManager.enableBlend();
/* 120 */       GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
/* 121 */       GlStateManager.blendFunc(770, 771);
/* 122 */       drawTexturedModalRect(this.xPosition, this.yPosition, 0, 46 + i * 20, this.width / 2, this.height);
/* 123 */       drawTexturedModalRect(this.xPosition + this.width / 2, this.yPosition, 200 - this.width / 2, 46 + i * 20, this.width / 2, this.height);
/* 124 */       mouseDragged(mc, mouseX, mouseY);
/* 125 */       int j = 14737632;
/*     */       
/* 127 */       if (!this.enabled) {
/*     */         
/* 129 */         j = 10526880;
/*     */       }
/* 131 */       else if (this.hovered) {
/*     */         
/* 133 */         j = 16777120;
/*     */       } 
/*     */       
/* 136 */       drawCenteredString(fontrenderer, this.displayString, this.xPosition + this.width / 2, this.yPosition + (this.height - 8) / 2, j);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected void mouseDragged(Minecraft mc, int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void mouseReleased(int mouseX, int mouseY) {}
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mousePressed(Minecraft mc, int mouseX, int mouseY) {
/* 160 */     return (this.enabled && this.visible && mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && mouseY < this.yPosition + this.height);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isMouseOver() {
/* 168 */     return this.hovered;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void drawButtonForegroundLayer(int mouseX, int mouseY) {}
/*     */ 
/*     */   
/*     */   public void playPressSound(SoundHandler soundHandlerIn) {
/* 177 */     soundHandlerIn.playSound((ISound)PositionedSoundRecord.create(new ResourceLocation("gui.button.press"), 1.0F));
/*     */   }
/*     */ 
/*     */   
/*     */   public int getButtonWidth() {
/* 182 */     return this.width;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWidth(int width) {
/* 187 */     this.width = width;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraft\client\gui\GuiButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */