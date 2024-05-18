/*    */ package me.eagler.clickgui.stuff;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import me.eagler.clickgui.Colors;
/*    */ import me.eagler.file.ConfigSaver;
/*    */ import me.eagler.font.FontHelper;
/*    */ import me.eagler.setting.Setting;
/*    */ import me.eagler.utils.ColorUtil;
/*    */ import net.minecraft.client.gui.Gui;
/*    */ 
/*    */ public class CheckBox
/*    */   extends Element
/*    */ {
/*    */   public CheckBox(ModuleButton moduleButton, Setting setting) {
/* 15 */     this.moduleButton = moduleButton;
/* 16 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 20 */     Color c = ColorUtil.getClickGuiColor2();
/* 21 */     Color c2 = new Color(30, 30, 30);
/* 22 */     Gui.drawRect(this.x + 4.0D, this.y, this.x + this.width + 4.0D, this.y + this.height, Color.black.getRGB());
/*    */     
/* 24 */     Color c3 = new Color(150, 150, 150, 255);
/*    */     
/* 26 */     if (this.moduleButton.elements.get(0) == this) {
/* 27 */       Gui.drawRect(this.x + 4.0D + 1.0D, this.y + 1.0D, this.x + this.width + 4.0D - 1.0D, 
/* 28 */           this.y + this.height - 1.0D, 
/* 29 */           this.setting.getBoolean() ? Colors.getSecondary().getRGB() : Colors.getPrimary().getRGB());
/*    */     } else {
/* 31 */       Gui.drawRect(this.x + 4.0D + 1.0D, this.y, this.x + this.width + 4.0D - 1.0D, this.y + this.height - 1.0D, 
/* 32 */           this.setting.getBoolean() ? Colors.getSecondary().getRGB() : Colors.getPrimary().getRGB());
/*    */     } 
/*    */     
/* 35 */     FontHelper.cfClickGui.drawCenteredString(this.settingstrg, this.x + this.width / 2.0D + 3.0D, 
/* 36 */         this.y + this.height / 2.0D - 7.0D, Colors.getText().getRGB(), false);
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 41 */     if (mouseButton == 0 && hovered(mouseX, mouseY)) {
/* 42 */       this.setting.setBoolean(!this.setting.getBoolean());
/* 43 */       ConfigSaver.saveConfig("latest");
/*    */     } 
/* 45 */     return super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */   
/*    */   public boolean hovered(int mouseX, int mouseY) {
/* 49 */     return (mouseX >= this.x + 4.0D + 1.0D && mouseX <= this.x + this.width + 4.0D - 1.0D && mouseY >= this.y + 1.0D && 
/* 50 */       mouseY <= this.y + this.height - 1.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\stuff\CheckBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */