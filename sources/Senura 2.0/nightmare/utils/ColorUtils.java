/*    */ package nightmare.utils;
/*    */ 
/*    */ import java.awt.Color;
/*    */ import nightmare.Nightmare;
/*    */ 
/*    */ public class ColorUtils
/*    */ {
/*    */   public static int getBackgroundColor() {
/*  9 */     return (new Color(0, 0, 0, 110)).getRGB();
/*    */   }
/*    */   
/*    */   public static int getClientColor() {
/* 13 */     return (new Color((int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Red").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Green").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Blue").getValDouble())).getRGB();
/*    */   }
/*    */   
/*    */   public static Color getClientColorRaw() {
/* 17 */     return new Color((int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Red").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Green").getValDouble(), (int)Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Blue").getValDouble());
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\ColorUtils.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */