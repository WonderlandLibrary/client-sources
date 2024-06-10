/*    */ package nightmare.module.misc;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Random;
/*    */ import net.minecraft.client.entity.EntityPlayerSP;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventTick;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ 
/*    */ 
/*    */ public class Spin
/*    */   extends Module
/*    */ {
/*    */   private float yaw;
/* 18 */   private Random random = new Random();
/*    */   private int randomval;
/*    */   
/*    */   public Spin() {
/* 22 */     super("Spin", 0, Category.MISC);
/*    */     
/* 24 */     ArrayList<String> options = new ArrayList<>();
/*    */     
/* 26 */     options.add("Left");
/* 27 */     options.add("Right");
/* 28 */     options.add("Random");
/*    */     
/* 30 */     Nightmare.instance.settingsManager.rSetting(new Setting("Speed", this, 55.0D, 1.0D, 100.0D, false));
/* 31 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Left", options));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onTick(EventTick event) {
/* 37 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*    */     
/* 39 */     if (mode.equals("Left")) {
/* 40 */       spinLeft();
/* 41 */     } else if (mode.equals("Right")) {
/* 42 */       spinRight();
/* 43 */     } else if (mode.equals("Random")) {
/*    */       
/* 45 */       if (this.randomval == 0) {
/* 46 */         spinLeft();
/*    */       }
/*    */       
/* 49 */       if (this.randomval == 1) {
/* 50 */         spinRight();
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 57 */     this.randomval = this.random.nextInt(2);
/* 58 */     if ((((mc.field_71439_g != null) ? 1 : 0) & ((mc.field_71441_e != null) ? 1 : 0)) != 0) {
/* 59 */       this.yaw = mc.field_71439_g.field_70177_z;
/*    */     }
/* 61 */     super.onEnable();
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 66 */     if ((((mc.field_71439_g != null) ? 1 : 0) & ((mc.field_71441_e != null) ? 1 : 0)) != 0) {
/* 67 */       this.yaw = 0.0F;
/*    */     }
/* 69 */     super.onDisable();
/*    */   }
/*    */   
/*    */   public void spinLeft() {
/* 73 */     double left = this.yaw + 360.0D - mc.field_71439_g.field_70177_z;
/*    */     
/* 75 */     if (left < Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble()) {
/* 76 */       EntityPlayerSP player = mc.field_71439_g;
/* 77 */       player.field_70177_z = (float)(player.field_70177_z + left);
/* 78 */       toggle();
/*    */     } else {
/* 80 */       EntityPlayerSP player = mc.field_71439_g;
/* 81 */       player.field_70177_z = (float)(player.field_70177_z + Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
/* 82 */       if (mc.field_71439_g.field_70177_z >= this.yaw + 360.0D) {
/* 83 */         toggle();
/*    */       }
/*    */     } 
/*    */   }
/*    */   
/*    */   public void spinRight() {
/* 89 */     double right = this.yaw - 360.0D - mc.field_71439_g.field_70177_z;
/*    */     
/* 91 */     if (right > -Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble()) {
/* 92 */       EntityPlayerSP player = mc.field_71439_g;
/* 93 */       player.field_70177_z = (float)(player.field_70177_z + right);
/* 94 */       toggle();
/*    */     } else {
/* 96 */       EntityPlayerSP player = mc.field_71439_g;
/* 97 */       player.field_70177_z = (float)(player.field_70177_z - Nightmare.instance.settingsManager.getSettingByName(this, "Speed").getValDouble());
/* 98 */       if (mc.field_71439_g.field_70177_z <= this.yaw - 360.0D)
/* 99 */         toggle(); 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\misc\Spin.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */