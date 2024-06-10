/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ public class Velocity
/*    */   extends Module {
/*    */   public Velocity() {
/* 13 */     super("Velocity", 0, Category.COMBAT);
/*    */     
/* 15 */     Nightmare.instance.settingsManager.rSetting(new Setting("Horizontal", this, 90.0D, 0.0D, 100.0D, true));
/* 16 */     Nightmare.instance.settingsManager.rSetting(new Setting("Vertical", this, 90.0D, 0.0D, 100.0D, true));
/* 17 */     Nightmare.instance.settingsManager.rSetting(new Setting("Chance", this, 50.0D, 0.0D, 100.0D, true));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 22 */     float horizontal = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Horizontal").getValDouble();
/* 23 */     float vertical = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Vertical").getValDouble();
/*    */     
/* 25 */     if (mc.field_71439_g.field_70737_aN == mc.field_71439_g.field_70738_aO && mc.field_71439_g.field_70738_aO > 0 && 
/* 26 */       Math.random() <= Nightmare.instance.settingsManager.getSettingByName(this, "Chance").getValDouble() / 100.0D) {
/* 27 */       mc.field_71439_g.field_70159_w *= (horizontal / 100.0F);
/* 28 */       mc.field_71439_g.field_70181_x *= (vertical / 100.0F);
/* 29 */       mc.field_71439_g.field_70179_y *= (horizontal / 100.0F);
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\Velocity.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */