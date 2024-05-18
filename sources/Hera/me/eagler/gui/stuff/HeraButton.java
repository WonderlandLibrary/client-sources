/*    */ package me.eagler.gui.stuff;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.clickgui.Colors;
/*    */ import me.eagler.font.FontHelper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class HeraButton extends GuiButton {
/*    */   float fade;
/*    */   
/*    */   public HeraButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/* 14 */     super(buttonId, x, y, widthIn, heightIn, buttonText);
/*    */ 
/*    */     
/* 17 */     this.fade = 0.0F;
/*    */   }
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 21 */     if (this.visible) {
/*    */       
/* 23 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && 
/* 24 */         mouseY < this.yPosition + this.height);
/*    */       
/* 26 */       Color c = Colors.getPrimary();
/*    */       
/* 28 */       if (!this.hovered) {
/*    */         
/* 30 */         c = Colors.getPrimary();
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       }
/*    */       else {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */         
/* 54 */         c = Colors.getSecondary();
/*    */       } 
/*    */ 
/*    */       
/* 58 */       Gui.drawRect(this.xPosition, this.yPosition, this.width, this.height, Color.black);
/*    */       
/* 60 */       Gui.drawRectWithEdge(this.xPosition, this.yPosition, this.width, this.height, c);
/*    */       
/* 62 */       FontHelper.cfButton.drawCenteredString2(this.displayString, (
/* 63 */           this.xPosition + this.width / 2 - FontHelper.cfButton.getStringWidth(this.displayString) / 2), (
/* 64 */           this.yPosition + this.height / 2 - FontHelper.cfButton.getStringHeight(this.displayString) / 2 - 2), 
/* 65 */           Colors.getText().getRGB(), false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\stuff\HeraButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */