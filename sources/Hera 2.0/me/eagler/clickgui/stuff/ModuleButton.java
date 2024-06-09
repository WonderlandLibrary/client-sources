/*     */ package me.eagler.clickgui.stuff;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.io.IOException;
/*     */ import java.util.ArrayList;
/*     */ import me.eagler.Client;
/*     */ import me.eagler.clickgui.Colors;
/*     */ import me.eagler.clickgui.Panel;
/*     */ import me.eagler.font.FontHelper;
/*     */ import me.eagler.module.Module;
/*     */ import me.eagler.setting.Setting;
/*     */ import me.eagler.utils.ColorUtil;
/*     */ import net.minecraft.client.gui.Gui;
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
/*     */ public class ModuleButton
/*     */ {
/*     */   public Module module;
/*     */   public Panel panel;
/*     */   public double x;
/*     */   public double y;
/*     */   public double width;
/*     */   public double height;
/*     */   public boolean extended = false;
/*     */   public boolean listening = false;
/*     */   public ArrayList<Element> elements;
/*     */   double y2;
/*     */   
/*     */   public ModuleButton(Module module, Panel panel) {
/*  54 */     this.y2 = 0.0D; this.module = module; this.panel = panel; this.height = (FontHelper.cfClickGui.FONT_HEIGHT + 2); this.elements = new ArrayList<Element>(); if (Client.instance.getSettingManager().getSettingsByMod(module) != null)
/*     */       for (Setting setting : Client.instance.getSettingManager().getSettingsByMod(module)) { if (setting.isCheckbox()) { this.elements.add(new CheckBox(this, setting)); continue; }  if (setting.isCombobox()) { this.elements.add(new ComboBox(this, setting)); continue; }
/*     */          if (setting.isValueslider())
/*     */           this.elements.add(new ValueSlider(this, setting));  }
/*  58 */         } public void drawScreen(int mouseX, int mouseY, float partialTicks) { Color c = ColorUtil.getClickGuiColor2();
/*     */     
/*  60 */     Color c2 = new Color(30, 30, 30);
/*     */     
/*  62 */     if (this.module.isEnabled()) {
/*     */ 
/*     */       
/*  65 */       Gui.drawRect(this.x - 4.0D + 1.0D, this.y, this.x + this.width + 4.0D - 1.0D, 
/*  66 */           this.y + this.height + 3.0D - 1.0D, Colors.getSecondary().getRGB());
/*  67 */       FontHelper.cfClickGui.drawCenteredString(this.module.getName(), this.x + this.width / 2.0D, 
/*  68 */           this.y + this.height / 2.0D - 7.0D, Colors.getText().getRGB(), true);
/*     */     }
/*     */     else {
/*     */       
/*  72 */       Gui.drawRect(this.x - 4.0D + 1.0D, this.y, this.x + this.width + 4.0D - 1.0D, 
/*  73 */           this.y + this.height + 3.0D - 1.0D, Colors.getPrimary().getRGB());
/*  74 */       Client.instance.getSettingManager().getSettingsByMod(this.module);
/*     */       
/*  76 */       FontHelper.cfClickGui.drawCenteredString(this.module.getName(), this.x + this.width / 2.0D, 
/*  77 */           this.y + this.height / 2.0D - 7.0D, Colors.getText().getRGB(), true);
/*     */     }  }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean mouseClicked(int mouseX, int mouseY, int mouseButton) {
/*  84 */     if (!hovered(mouseX, mouseY))
/*  85 */       return false; 
/*  86 */     if (mouseButton == 0) {
/*  87 */       this.module.toggle();
/*  88 */     } else if (mouseButton == 1 && 
/*  89 */       this.elements != null && this.elements.size() > 0) {
/*  90 */       boolean b = !this.extended;
/*  91 */       this.extended = b;
/*     */     } 
/*     */     
/*  94 */     return true;
/*     */   }
/*     */   
/*     */   public boolean keyTyped(char typedChar, int keyCode) throws IOException {
/*  98 */     return false;
/*     */   }
/*     */   
/*     */   public boolean hovered(int mouseX, int mouseY) {
/* 102 */     return (mouseX >= this.x && mouseX <= this.x + this.width && mouseY >= this.y && 
/* 103 */       mouseY <= this.y + this.height);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\clickgui\stuff\ModuleButton.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */