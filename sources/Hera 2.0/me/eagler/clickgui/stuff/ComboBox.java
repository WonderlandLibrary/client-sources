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
/*    */ 
/*    */ public class ComboBox
/*    */   extends Element
/*    */ {
/*    */   public ComboBox(ModuleButton moduleButton, Setting setting) {
/* 16 */     this.moduleButton = moduleButton;
/* 17 */     this.setting = setting;
/*    */   }
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {
/* 21 */     Color c = new Color(0, 0, 0, 255);
/* 22 */     Gui.drawRect(this.x + 4.0D, this.y, this.x + this.width + 4.0D, this.y + this.height, 
/* 23 */         Color.black.getRGB());
/* 24 */     Gui.drawRect(this.x + 4.0D + 1.0D, this.y + 1.0D, this.x + this.width + 4.0D - 1.0D, 
/* 25 */         this.y + this.height - 1.0D, Colors.getPrimary().getRGB());
/* 26 */     if (this.settingstrg.contains("Mode")) {
/* 27 */       FontHelper.cfClickGui.drawCenteredString("Mode", this.x + this.width / 2.0D + 4.0D, this.y + 7.0D - 6.0D, 
/* 28 */           Colors.getText().getRGB(), false);
/*    */     } else {
/* 30 */       FontHelper.cfClickGui.drawCenteredString(this.settingstrg, this.x + this.width / 2.0D + 4.0D, 
/* 31 */           this.y + 7.0D - 6.0D, Colors.getText().getRGB(), false);
/*    */     } 
/* 33 */     if (this.extended) {
/* 34 */       Color c3 = new Color(30, 30, 30);
/* 35 */       double modeY = this.y + 15.0D;
/* 36 */       for (String mode : this.setting.getOptions()) {
/* 37 */         String elementtitle = String.valueOf(String.valueOf(mode.substring(0, 1).toUpperCase())) + 
/* 38 */           mode.substring(1, mode.length());
/* 39 */         if (mode.equalsIgnoreCase(this.setting.getMode())) {
/* 40 */           Color c2 = ColorUtil.getClickGuiColor2();
/* 41 */           Gui.drawRect(this.x + 4.0D + 1.0D, modeY + 1.0D, this.x + 86.0D + 4.0D - 1.0D, modeY + 14.0D, 
/* 42 */               Colors.getSecondary().getRGB());
/* 43 */           FontHelper.cfClickGui.drawCenteredString(elementtitle, this.x + this.width / 2.0D + 4.0D, modeY, 
/* 44 */               Colors.getText().getRGB(), false);
/*    */         } else {
/* 46 */           FontHelper.cfClickGui.drawCenteredString(elementtitle, this.x + this.width / 2.0D + 4.0D, modeY, 
/* 47 */               Colors.getText().getRGB(), false);
/*    */         } 
/* 49 */         modeY += (FontHelper.cfClickGui.FONT_HEIGHT + 2);
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 55 */     if (mouseButton == 0) {
/* 56 */       if (hovered(mouseX, mouseY)) {
/* 57 */         this.extended = !this.extended;
/* 58 */         return true;
/*    */       } 
/* 60 */       if (!this.extended)
/* 61 */         return false; 
/* 62 */       double ay = this.y + 15.0D;
/* 63 */       for (String slcd : this.setting.getOptions()) {
/* 64 */         if (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= ay && 
/* 65 */           mouseY <= ay + FontHelper.cfClickGui.FONT_HEIGHT + 2.0D) {
/* 66 */           this.clickgui.settingmanager.getSettingByName(this.setting.getSettingname())
/* 67 */             .setMode(slcd.toLowerCase());
/* 68 */           ConfigSaver.saveConfig("latest");
/* 69 */           return true;
/*    */         } 
/* 71 */         ay += (FontHelper.cfClickGui.FONT_HEIGHT + 2);
/*    */       } 
/*    */     } 
/* 74 */     return super.mouseClicked(mouseX, mouseY, mouseButton);
/*    */   }
/*    */   
/*    */   public boolean hovered(int mouseX, int mouseY) {
/* 78 */     return (mouseX >= this.x + 4.0D && mouseX <= this.x + this.width + 4.0D && mouseY >= this.y && 
/* 79 */       mouseY <= this.y + 15.0D);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\stuff\ComboBox.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */