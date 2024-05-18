/*    */ package me.eagler.module.modules.movement;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ import me.eagler.utils.PlayerUtils;
/*    */ 
/*    */ public class LongJump
/*    */   extends Module
/*    */ {
/*    */   public LongJump() {
/* 13 */     super("LongJump", Category.Movement);
/*    */     
/* 15 */     ArrayList<String> options = new ArrayList<String>();
/*    */     
/* 17 */     options.add("CubeCraft");
/*    */     
/* 19 */     this.settingManager.addSetting(new Setting("LJMode", this, "CubeCraft", options));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 24 */     String mode = this.settingManager.getSettingByName("LJMode").getMode();
/*    */     
/* 26 */     if (mode.equalsIgnoreCase("CubeCraft"))
/*    */     {
/* 28 */       PlayerUtils.sendMessage("Place a block before landing.", true);
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 36 */     String mode = this.settingManager.getSettingByName("LJMode").getMode();
/*    */     
/* 38 */     if (mode.equalsIgnoreCase("CubeCraft")) {
/*    */       
/* 40 */       this.mc.timer.timerSpeed = 1.0F;
/* 41 */       this.mc.thePlayer.motionX = 0.0D;
/* 42 */       this.mc.thePlayer.motionZ = 0.0D;
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 50 */     String mode = this.settingManager.getSettingByName("LJMode").getMode();
/*    */     
/* 52 */     setExtraTag(mode);
/*    */     
/* 54 */     if (mode.equalsIgnoreCase("CubeCraft"))
/*    */     {
/* 56 */       if (this.mc.thePlayer.onGround) {
/*    */         
/* 58 */         if (this.mc.gameSettings.keyBindForward.pressed)
/*    */         {
/* 60 */           this.mc.thePlayer.jump();
/*    */ 
/*    */ 
/*    */         
/*    */         }
/*    */ 
/*    */       
/*    */       }
/* 68 */       else if (!PlayerUtils.playeriswalking()) {
/*    */         
/* 70 */         this.mc.timer.timerSpeed = 1.0F;
/* 71 */         this.mc.thePlayer.motionX = 0.0D;
/* 72 */         this.mc.thePlayer.motionZ = 0.0D;
/*    */       }
/*    */       else {
/*    */         
/* 76 */         this.mc.timer.timerSpeed = 0.2F;
/* 77 */         Speed.setSpeed(2.0D);
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\movement\LongJump.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */