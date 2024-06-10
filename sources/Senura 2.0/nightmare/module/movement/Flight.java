/*    */ package nightmare.module.movement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import org.lwjgl.input.Keyboard;
/*    */ 
/*    */ 
/*    */ public class Flight
/*    */   extends Module
/*    */ {
/* 16 */   public float speed = 1.0F;
/*    */   
/*    */   public Flight() {
/* 19 */     super("Flight", 0, Category.MOVEMENT);
/*    */     
/* 21 */     setBlatantModule(true);
/*    */     
/* 23 */     ArrayList<String> options = new ArrayList<>();
/*    */     
/* 25 */     options.add("Vanilla");
/* 26 */     options.add("FastFly");
/* 27 */     options.add("AirWalk");
/*    */     
/* 29 */     Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 1.0D, 1.0D, 5.0D, false));
/* 30 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Vanilla", options));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 36 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*    */     
/* 38 */     if (mode.equals("Vanilla")) {
/* 39 */       mc.field_71439_g.field_71075_bZ.field_75100_b = true;
/* 40 */     } else if (mode.equals("FastFly")) {
/* 41 */       if (mc.field_71439_g.func_70051_ag()) {
/* 42 */         this.speed = 1.5F;
/*    */       } else {
/* 44 */         this.speed = 1.0F;
/*    */       } 
/*    */       
/* 47 */       mc.field_71439_g.field_70145_X = true;
/* 48 */       mc.field_71439_g.field_70143_R = 0.0F;
/* 49 */       mc.field_71439_g.field_70122_E = false;
/* 50 */       mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/* 51 */       mc.field_71439_g.field_70159_w = 0.0D;
/* 52 */       mc.field_71439_g.field_70181_x = 0.0D;
/* 53 */       mc.field_71439_g.field_70179_y = 0.0D;
/* 54 */       float moveSpeed = (float)Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble();
/* 55 */       mc.field_71439_g.field_70747_aH = moveSpeed;
/* 56 */       if (Keyboard.isKeyDown(mc.field_71474_y.field_74314_A.func_151463_i())) {
/* 57 */         mc.field_71439_g.field_70181_x += moveSpeed;
/*    */       }
/* 59 */       if (Keyboard.isKeyDown(mc.field_71474_y.field_74311_E.func_151463_i())) {
/* 60 */         mc.field_71439_g.field_70181_x -= moveSpeed;
/*    */       }
/* 62 */     } else if (mode.equals("AirWalk")) {
/* 63 */       mc.field_71439_g.field_70122_E = true;
/* 64 */       mc.field_71439_g.field_70181_x = 0.0D;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 71 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*    */     
/* 73 */     if (mc.field_71439_g == null || mc.field_71441_e == null) {
/*    */       return;
/*    */     }
/*    */     
/* 77 */     if (mode.equals("Vanilla")) {
/* 78 */       mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/* 79 */     } else if (mode.equals("FastFly")) {
/* 80 */       mc.field_71439_g.field_71075_bZ.field_75098_d = false;
/* 81 */       mc.field_71439_g.field_70145_X = false;
/* 82 */       mc.field_71439_g.field_71075_bZ.field_75100_b = false;
/*    */     } 
/* 84 */     super.onDisable();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\movement\Flight.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */