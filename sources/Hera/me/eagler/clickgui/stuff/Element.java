/*    */ package me.eagler.clickgui.stuff;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import me.eagler.clickgui.ClickGUI;
/*    */ import me.eagler.font.FontHelper;
/*    */ import me.eagler.setting.Setting;
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
/*    */ public class Element
/*    */ {
/*    */   public ClickGUI clickgui;
/*    */   public ModuleButton moduleButton;
/*    */   public Setting setting;
/*    */   public double offset;
/*    */   public double x;
/*    */   public double y;
/*    */   public double width;
/*    */   public double height;
/*    */   public String settingstrg;
/*    */   public boolean extended;
/*    */   
/*    */   public void onUpdate() {
/* 31 */     this.clickgui = Client.instance.getClickGui();
/* 32 */     this.x = this.moduleButton.x + this.moduleButton.width + 2.0D;
/* 33 */     this.y = this.moduleButton.y + this.offset + 2.0D;
/* 34 */     this.width = this.moduleButton.width + 10.0D;
/* 35 */     this.height = 15.0D;
/* 36 */     String settingname = this.setting.getSettingname();
/* 37 */     if (this.setting.isCheckbox()) {
/* 38 */       this.settingstrg = String.valueOf(String.valueOf(settingname.substring(0, 1).toUpperCase())) + 
/* 39 */         settingname.substring(1, settingname.length());
/* 40 */       double textposx = this.x + this.width - FontHelper.cfClickGui.getStringWidth(this.settingstrg);
/* 41 */       if (textposx < this.x + 12.0D)
/* 42 */         this.width += this.x + 12.0D - textposx + 1.0D; 
/* 43 */     } else if (this.setting.isCombobox()) {
/* 44 */       this.height = (this.extended ? (
/* 45 */         this.setting.getOptions().size() * (FontHelper.cfClickGui.FONT_HEIGHT + 2) + 15) : 
/* 46 */         15);
/* 47 */       this.settingstrg = String.valueOf(String.valueOf(settingname.substring(0, 1).toUpperCase())) + 
/* 48 */         settingname.substring(1, settingname.length());
/* 49 */       int longestmode = FontHelper.cfClickGui.getStringWidth(this.settingstrg);
/* 50 */       for (String option : this.setting.getOptions()) {
/* 51 */         int optionwidth = FontHelper.cfClickGui.getStringWidth(this.settingstrg);
/* 52 */         if (optionwidth > longestmode)
/* 53 */           longestmode = optionwidth; 
/*    */       } 
/* 55 */       double textposx = this.x + this.width - longestmode;
/* 56 */       if (textposx < this.x)
/* 57 */         this.width += this.x - textposx + 1.0D; 
/* 58 */     } else if (this.setting.isValueslider()) {
/* 59 */       this.settingstrg = String.valueOf(String.valueOf(settingname.substring(0, 1).toUpperCase())) + 
/* 60 */         settingname.substring(1, settingname.length());
/* 61 */       double d1 = Math.round(this.setting.getValue() * 100.0D) / 100.0D;
/* 62 */       double d2 = Math.round(this.setting.getMaxValue() * 100.0D) / 100.0D;
/*    */       
/* 64 */       double d3 = d2;
/*    */       
/* 66 */       double d4 = this.x + this.width - FontHelper.cfClickGui.getStringWidth(this.settingstrg) - 
/* 67 */         FontHelper.cfClickGui.getStringWidth(d3) - 4.0D;
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void drawScreen(int mouseX, int mouseY, float partialTicks) {}
/*    */   
/*    */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/* 75 */     return isHovered(mouseX, mouseY);
/*    */   }
/*    */ 
/*    */   
/*    */   public void mouseReleased(int mouseX, int mouseY, int state) {}
/*    */   
/*    */   public boolean isHovered(int mouseX, int mouseY) {
/* 82 */     return (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && 
/* 83 */       mouseY <= this.y + this.height);
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\stuff\Element.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */