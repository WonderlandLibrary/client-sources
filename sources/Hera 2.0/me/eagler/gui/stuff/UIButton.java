/*    */ package me.eagler.gui.stuff;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.FontRenderer;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class UIButton extends GuiButton {
/*    */   private int fade;
/*    */   
/*    */   public UIButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/* 13 */     super(buttonId, x, y, widthIn, heightIn, buttonText);
/*    */   }
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 17 */     if (this.visible) {
/* 18 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && 
/* 19 */         mouseY < this.yPosition + this.height);
/* 20 */       if (!this.hovered) {
/* 21 */         this.fade = 0;
/* 22 */         Color a = new Color(0, 0, 0, 155);
/* 23 */         Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 
/* 24 */             a.getRGB());
/*    */       } else {
/* 26 */         if (this.fade < 255)
/* 27 */           if (this.fade < 235) {
/* 28 */             this.fade += 20;
/*    */           } else {
/* 30 */             this.fade += 255 - this.fade;
/*    */           }  
/* 32 */         Color a2 = new Color(this.fade, this.fade, this.fade, 100);
/* 33 */         Gui.drawRect(this.xPosition, this.yPosition, this.xPosition + this.width, this.yPosition + this.height, 
/* 34 */             a2.getRGB());
/*    */       } 
/* 36 */       FontRenderer var4 = (Minecraft.getMinecraft()).fontRendererObj;
/* 37 */       drawCenteredString(var4, this.displayString, this.xPosition + this.width / 2, 
/* 38 */           this.yPosition + (this.height - 8) / 2, Color.white.getRGB());
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\stuff\UIButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */