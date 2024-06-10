/*    */ package nightmare.module.misc;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.gui.notification.NotificationManager;
/*    */ import nightmare.gui.notification.NotificationType;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ public class BlatantMode
/*    */   extends Module {
/*    */   public BlatantMode() {
/* 12 */     super("BlatantMode", 0, Category.MISC);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 17 */     NotificationManager.show(NotificationType.WARNING, "Module", "Not recommended to use as it is likely to be banned", 5000);
/* 18 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 23 */     for (Module m : Nightmare.instance.moduleManager.getModules()) {
/* 24 */       if (m.isBlatantModule())
/* 25 */         m.setToggled(false); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\misc\BlatantMode.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */