/*    */ package nightmare.settings;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SettingsManager
/*    */ {
/* 12 */   private ArrayList<Setting> settings = new ArrayList<>();
/*    */ 
/*    */   
/*    */   public void rSetting(Setting in) {
/* 16 */     this.settings.add(in);
/*    */   }
/*    */   
/*    */   public ArrayList<Setting> getSettings() {
/* 20 */     return this.settings;
/*    */   }
/*    */   
/*    */   public ArrayList<Setting> getSettingsByMod(Module mod) {
/* 24 */     ArrayList<Setting> out = new ArrayList<>();
/* 25 */     for (Setting s : getSettings()) {
/* 26 */       if (s.getParentMod().equals(mod)) {
/* 27 */         out.add(s);
/*    */       }
/*    */     } 
/* 30 */     if (out.isEmpty()) {
/* 31 */       return null;
/*    */     }
/* 33 */     return out;
/*    */   }
/*    */   
/*    */   public Setting getSettingByNameForCliclGUI(String name) {
/* 37 */     for (Setting set : getSettings()) {
/* 38 */       if (set.getName().equalsIgnoreCase(name)) {
/* 39 */         return set;
/*    */       }
/*    */     } 
/* 42 */     return null;
/*    */   }
/*    */   
/*    */   public Setting getSettingByName(Module mod, String name) {
/* 46 */     for (Setting set : getSettings()) {
/* 47 */       if (set.getName().equalsIgnoreCase(name) && set.getParentMod() == mod) {
/* 48 */         return set;
/*    */       }
/*    */     } 
/* 51 */     return null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\settings\SettingsManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */