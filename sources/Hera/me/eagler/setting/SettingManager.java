/*    */ package me.eagler.setting;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
/*    */ import java.util.concurrent.CopyOnWriteArrayList;
/*    */ import me.eagler.module.Module;
/*    */ 
/*    */ public class SettingManager
/*    */ {
/*    */   private List<Setting> settings;
/*    */   
/*    */   public void load() {
/* 13 */     this.settings = new CopyOnWriteArrayList<Setting>();
/*    */   }
/*    */   
/*    */   public void addSetting(Setting setting) {
/* 17 */     this.settings.add(setting);
/*    */   }
/*    */   
/*    */   public List<Setting> getSettings() {
/* 21 */     return this.settings;
/*    */   }
/*    */   
/*    */   public Setting getSettingByName(String settingname) {
/* 25 */     for (Setting setting : getSettings()) {
/* 26 */       if (setting.getSettingname().equalsIgnoreCase(settingname))
/* 27 */         return setting; 
/*    */     } 
/* 29 */     return null;
/*    */   }
/*    */   
/*    */   public ArrayList<Setting> getSettingsByMod(Module mod) {
/* 33 */     ArrayList<Setting> out = new ArrayList<Setting>();
/* 34 */     for (Setting s : getSettings()) {
/* 35 */       if (s.getModule().equals(mod))
/* 36 */         out.add(s); 
/*    */     } 
/* 38 */     if (out.isEmpty())
/* 39 */       return null; 
/* 40 */     return out;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\setting\SettingManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */