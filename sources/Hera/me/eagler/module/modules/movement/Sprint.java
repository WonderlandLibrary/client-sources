/*    */ package me.eagler.module.modules.movement;
/*    */ 
/*    */ import me.eagler.Client;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ 
/*    */ public class Sprint
/*    */   extends Module
/*    */ {
/*    */   public Sprint() {
/* 11 */     super("Sprint", Category.Movement);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 16 */     if (this.mc.theWorld != null && this.mc.thePlayer != null)
/*    */     {
/* 18 */       this.mc.thePlayer.setSprinting(false);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 26 */     if (this.mc.thePlayer.getFoodStats().getFoodLevel() > 6 && this.mc.gameSettings.keyBindForward.pressed && 
/* 27 */       !this.mc.gameSettings.keyBindBack.isPressed() && !this.mc.gameSettings.keyBindLeft.pressed && !this.mc.gameSettings.keyBindRight.pressed)
/*    */     {
/* 29 */       if (!Client.instance.getModuleManager().getModuleByName("ScaffoldWalk").isEnabled()) {
/*    */         
/* 31 */         this.mc.thePlayer.setSprinting(true);
/*    */ 
/*    */       
/*    */       }
/* 35 */       else if (!this.settingManager.getSettingByName("NoSprint").getBoolean()) {
/*    */         
/* 37 */         this.mc.thePlayer.setSprinting(true);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\Sprint.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */