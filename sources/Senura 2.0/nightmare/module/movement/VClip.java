/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class VClip
/*    */   extends Module {
/*    */   public VClip() {
/* 11 */     super("VClip", 0, Category.MOVEMENT);
/*    */     
/* 13 */     Nightmare.instance.settingsManager.rSetting(new Setting("Distance", this, -3.0D, -5.0D, 5.0D, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 18 */     super.onEnable();
/* 19 */     if (Nightmare.instance.settingsManager.getSettingByName(this, "Distance").getValDouble() != 0.0D) {
/* 20 */       mc.field_71439_g.func_70107_b(mc.field_71439_g.field_70165_t, mc.field_71439_g.field_70163_u + Nightmare.instance.settingsManager.getSettingByName(this, "Distance").getValDouble(), mc.field_71439_g.field_70161_v);
/* 21 */       toggle();
/*    */     } else {
/* 23 */       setToggled(false);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\VClip.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */