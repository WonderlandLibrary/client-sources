/*    */ package me.eagler.gui.stuff;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.clickgui.Colors;
/*    */ import me.eagler.font.FontHelper;
/*    */ import net.minecraft.client.Minecraft;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.client.gui.GuiButton;
/*    */ 
/*    */ public class HeraShadowButton
/*    */   extends GuiButton
/*    */ {
/*    */   public HeraShadowButton(int buttonId, int x, int y, int widthIn, int heightIn, String buttonText) {
/* 14 */     super(buttonId, x, y, widthIn, heightIn, buttonText);
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawButton(Minecraft mc, int mouseX, int mouseY) {
/* 19 */     if (this.visible) {
/*    */       
/* 21 */       this.hovered = (mouseX >= this.xPosition && mouseY >= this.yPosition && mouseX < this.xPosition + this.width && 
/* 22 */         mouseY < this.yPosition + this.height);
/*    */       
/* 24 */       Color c = Colors.getPrimary();
/*    */       
/* 26 */       if (!this.hovered) {
/*    */         
/* 28 */         c = Colors.getPrimary();
/*    */       
/*    */       }
/*    */       else {
/*    */ 
/*    */         
/* 34 */         c = Colors.getSecondary();
/*    */       } 
/*    */ 
/*    */       
/* 38 */       Gui.drawRect(this.xPosition, this.yPosition, this.width, this.height, Color.black);
/*    */       
/* 40 */       Gui.drawRectWithShadow(this.xPosition, this.yPosition, this.width, this.height, "right", "bottom", c);
/*    */       
/* 42 */       FontHelper.cfButton.drawCenteredString2(this.displayString, (
/* 43 */           this.xPosition + this.width / 2 - FontHelper.cfButton.getStringWidth(this.displayString) / 2), (
/* 44 */           this.yPosition + this.height / 2 - FontHelper.cfButton.getStringHeight(this.displayString) / 2 - 
/* 45 */           2), 
/* 46 */           Colors.getText().getRGB(), false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\gui\stuff\HeraShadowButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */