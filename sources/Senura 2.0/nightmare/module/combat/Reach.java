/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Reach
/*    */   extends Module {
/*    */   public Reach() {
/* 14 */     super("Reach", 0, Category.COMBAT);
/*    */     
/* 16 */     Nightmare.instance.settingsManager.rSetting(new Setting("Range", this, 3.5D, 3.0D, 6.0D, false));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 21 */     setDisplayName("Reach " + EnumChatFormatting.GRAY + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble());
/*    */   }
/*    */   
/*    */   public static double getMaxRange() {
/* 25 */     double range = Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("Reach"), "Range").getValDouble();
/* 26 */     double buildRange = range = 4.5D;
/*    */     
/* 28 */     if (range > buildRange) {
/* 29 */       return range;
/*    */     }
/* 31 */     return buildRange;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\Reach.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */