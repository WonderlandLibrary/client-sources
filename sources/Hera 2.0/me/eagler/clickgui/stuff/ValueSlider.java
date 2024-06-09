/*    */ package me.eagler.clickgui.stuff;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.clickgui.Colors;
/*    */ import me.eagler.file.ConfigSaver;
/*    */ import me.eagler.font.FontHelper;
/*    */ import me.eagler.setting.Setting;
/*    */ import me.eagler.utils.ColorUtil;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ import net.minecraft.util.MathHelper;
/*    */ 
/*    */ public class ValueSlider
/*    */   extends Element
/*    */ {
/*    */   public boolean dragging;
/*    */   
/*    */   public ValueSlider(ModuleButton moduleButton, Setting setting) {
/* 18 */     this.moduleButton = moduleButton;
/* 19 */     this.setting = setting;
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 24 */     double d1 = Math.round(this.setting.getValue() * 100.0D) / 100.0D;
/* 25 */     double percentBar = (this.setting.getValue() - this.setting.getMinValue()) / (
/* 26 */       this.setting.getMaxValue() - this.setting.getMinValue());
/* 27 */     if (this.moduleButton.elements.get(0) == this) {
/* 28 */       Gui.drawRect(this.x + 4.0D, this.y - 1.0D, this.x + this.width + 4.0D, this.y + this.height + 1.0D, 
/* 29 */           Color.black.getRGB());
/*    */     } else {
/* 31 */       Gui.drawRect(this.x + 4.0D, this.y, this.x + this.width + 4.0D, this.y + this.height + 1.0D, 
/* 32 */           Color.black.getRGB());
/*    */     } 
/* 34 */     Gui.drawRect(this.x + 4.0D + 1.0D, this.y, this.x + this.width + 4.0D - 1.0D, 
/* 35 */         this.y + 13.5D + 1.5D - 1.0D + 1.0D, Colors.getPrimary().getRGB());
/* 36 */     Color c2 = ColorUtil.getClickGuiColor2();
/* 37 */     Color c3 = new Color(30, 30, 30);
/* 38 */     if (this.x + percentBar * this.width >= this.x + 2.0D && 
/* 39 */       this.x + percentBar * this.width >= this.x + 2.0D)
/* 40 */       if (this.x + percentBar * this.width > this.x + this.width - 2.0D) {
/* 41 */         Gui.drawRect(this.x + 1.0D + 4.0D, this.y + 1.0D, this.x + this.width - 2.0D + 5.0D, 
/* 42 */             this.y + 13.5D + 3.0D - 2.5D, Colors.getSecondary().getRGB());
/*    */       } else {
/* 44 */         Gui.drawRect(this.x + 1.0D + 4.0D, this.y + 1.0D, this.x + percentBar * this.width + 5.0D, 
/* 45 */             this.y + 13.5D + 3.0D - 2.5D, Colors.getSecondary().getRGB());
/*    */       }  
/* 47 */     if (this.settingstrg.contains("Delay")) {
/* 48 */       FontHelper.cfClickGui.drawCenteredString("Delay:" + d1, this.x + this.width / 2.0D + 4.0D, this.y, 
/* 49 */           Colors.getText().getRGB(), true);
/*    */     } else {
/* 51 */       FontHelper.cfClickGui.drawCenteredString(String.valueOf(String.valueOf(this.settingstrg)) + ":" + d1, 
/* 52 */           this.x + this.width / 2.0D + 4.0D, this.y, Colors.getText().getRGB(), true);
/*    */     } 
/* 54 */     if (this.dragging) {
/* 55 */       double diff = this.setting.getMaxValue() - this.setting.getMinValue();
/* 56 */       double val = this.setting.getMinValue() + 
/* 57 */         MathHelper.clamp_double((mouseX - this.x) / this.width, 0.0D, 1.0D) * diff;
/* 58 */       this.setting.setValue(myRound(val, 1));
/* 59 */       ConfigSaver.saveConfig("latest");
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 65 */     if (mouseButton == 0 && hovered(mouseX, mouseY)) {
/* 66 */       this.dragging = true;
/* 67 */       return true;
/*    */     } 
/* 69 */     return super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int state) {
/* 73 */     this.dragging = false;
/*    */   }
/*    */   
/*    */   public boolean hovered(int mouseX, int mouseY) {
/* 77 */     return (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y + 2.5D && 
/* 78 */       mouseY <= this.y + 12.5D);
/*    */   }
/*    */   
/*    */   static double myRound(double wert, int stellen) {
/* 82 */     return Math.round(wert * Math.pow(10.0D, stellen)) / Math.pow(10.0D, stellen);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\stuff\ValueSlider.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */