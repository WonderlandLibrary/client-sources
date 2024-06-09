/*    */ package me.eagler.module.modules.render;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ 
/*    */ 
/*    */ public class Brightness
/*    */   extends Module
/*    */ {
/* 10 */   private float oldlevel = 1.0F;
/*    */   
/*    */   public Brightness() {
/* 13 */     super("Brightness", "Brightness", 0, Category.Render);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onEnable() {
/* 18 */     this.oldlevel = this.mc.gameSettings.gammaSetting;
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 24 */     if (this.mc.gameSettings.gammaSetting < 100.0F)
/*    */     {
/* 26 */       this.mc.gameSettings.gammaSetting += 0.2F;
/*    */     }
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 34 */     this.mc.gameSettings.gammaSetting = this.oldlevel;
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\render\Brightness.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */