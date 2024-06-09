/*    */ package me.eagler.module.modules.gui;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class Theme
/*    */   extends Module
/*    */ {
/*    */   public Theme() {
/* 12 */     super("Theme", Category.Gui);
/*    */     
/* 14 */     ArrayList<String> options = new ArrayList<String>();
/*    */     
/* 16 */     options.add("Dark");
/* 17 */     options.add("Bright");
/*    */     
/* 19 */     this.settingManager.addSetting(new Setting("ThemeMode", this, "Dark", options));
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\gui\Theme.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */