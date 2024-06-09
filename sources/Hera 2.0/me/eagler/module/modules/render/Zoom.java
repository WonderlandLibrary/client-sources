/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ import me.eagler.setting.Setting;
/*    */ import net.minecraft.client.settings.GameSettings;
/*    */ 
/*    */ public class Zoom
/*    */   extends Module {
/* 10 */   public static float smooth = 1.0F;
/*    */   
/*    */   public Zoom() {
/* 13 */     super("Zoom", Category.Render);
/*    */     
/* 15 */     this.settingManager.addSetting(new Setting("Multiplier", this, 4.0D, 2.0D, 18.0D, true));
/* 16 */     this.settingManager.addSetting(new Setting("Smooth", this, true));
/* 17 */     this.settingManager.addSetting(new Setting("SmSpeed", this, 10.0D, 1.0D, 20.0D, true));
/* 18 */     this.settingManager.addSetting(new Setting("SmoothCam", this, true));
/*    */   }
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 23 */     double multiplier = this.settingManager.getSettingByName("Multiplier").getValue();
/*    */     
/* 25 */     setExtraTag(multiplier);
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onRender() {
/* 31 */     float multiplier = (float)this.settingManager.getSettingByName("Multiplier").getValue();
/*    */     
/* 33 */     float smoothspeed = (float)this.settingManager.getSettingByName("SmSpeed").getValue() / 10.0F * 100.0F;
/*    */     
/* 35 */     float ppt = multiplier / smoothspeed;
/*    */     
/* 37 */     if (GameSettings.isKeyDown(this.mc.gameSettings.ofKeyBindZoom)) {
/*    */       
/* 39 */       if (smooth != multiplier)
/*    */       {
/* 41 */         if (smooth < multiplier - ppt)
/*    */         {
/* 43 */           smooth += ppt;
/*    */         }
/* 45 */         else if (smooth > multiplier - ppt)
/*    */         {
/* 47 */           smooth += multiplier - smooth;
/*    */ 
/*    */         
/*    */         }
/*    */ 
/*    */ 
/*    */       
/*    */       }
/*    */     
/*    */     }
/* 57 */     else if (smooth != 1.0F) {
/*    */       
/* 59 */       if (smooth > 1.0F + ppt) {
/*    */         
/* 61 */         smooth -= ppt;
/*    */       }
/* 63 */       else if (smooth < 1.0F + ppt) {
/*    */         
/* 65 */         smooth += 1.0F - smooth;
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\Zoom.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */