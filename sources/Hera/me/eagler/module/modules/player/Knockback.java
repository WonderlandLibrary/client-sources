/*    */ package me.eagler.module.modules.player;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ 
/*    */ public class Knockback
/*    */   extends Module
/*    */ {
/*    */   public Knockback() {
/* 12 */     super("Knockback", Category.Player);
/*    */     
/* 14 */     ArrayList<String> options = new ArrayList<String>();
/*    */     
/* 16 */     options.add("Null");
/* 17 */     options.add("Reverse");
/*    */     
/* 19 */     this.settingManager.addSetting(new Setting("KBMode", this, "Null", options));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 24 */     String mode = this.settingManager.getSettingByName("KBMode").getMode();
/*    */     
/* 26 */     setExtraTag(mode);
/*    */     
/* 28 */     if (mode.equalsIgnoreCase("Reverse"))
/*    */     {
/* 30 */       if (!this.mc.thePlayer.onGround)
/*    */       {
/* 32 */         if (this.mc.thePlayer.hurtTime > 0)
/*    */         {
/* 34 */           this.mc.thePlayer.onGround = true;
/*    */         }
/*    */       }
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\player\Knockback.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */