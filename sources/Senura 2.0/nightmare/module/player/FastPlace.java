/*    */ package nightmare.module.player;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class FastPlace
/*    */   extends Module {
/*    */   public FastPlace() {
/* 11 */     super("FastPlace", 0, Category.PLAYER);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 1.0D, 0.0D, 4.0D, true));
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\FastPlace.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */