/*    */ package me.eagler.module.modules.misc;
/*    */ 
/*    */ import me.eagler.module.Category;
/*    */ import me.eagler.module.Module;
/*    */ 
/*    */ public class AutoConfig
/*    */   extends Module {
/*  8 */   public static String currentconfig = "";
/*    */   
/*    */   public AutoConfig() {
/* 11 */     super("AutoConfig", Category.Misc);
/*    */   }
/*    */ 
/*    */   
/*    */   public void onDisable() {
/* 16 */     currentconfig = "";
/*    */   }
/*    */ 
/*    */ 
/*    */   
/*    */   public void onUpdate() {
/* 22 */     if (this.mc.thePlayer != null && this.mc.theWorld != null)
/*    */     {
/* 24 */       if (this.mc.getCurrentServerData() != null) {
/*    */         
/* 26 */         String serverip = (this.mc.getCurrentServerData()).serverIP;
/*    */         
/* 28 */         if (serverip.toLowerCase().contains("cubecraft"))
/*    */         {
/* 30 */           if (currentconfig != "cubecraft") {
/*    */             
/* 32 */             this.mc.thePlayer.sendChatMessage(".config load CubeCraft");
/* 33 */             currentconfig = "cubecraft";
/*    */           } 
/*    */         }
/*    */ 
/*    */ 
/*    */         
/* 39 */         if (serverip.toLowerCase().contains("gomme"))
/*    */         {
/* 41 */           if (currentconfig != "gomme") {
/*    */             
/* 43 */             this.mc.thePlayer.sendChatMessage(".config load Gomme");
/* 44 */             currentconfig = "gomme";
/*    */           } 
/*    */         }
/*    */         
/* 48 */         if (serverip.toLowerCase().contains("hive"))
/*    */         {
/* 50 */           if (currentconfig != "hive") {
/*    */             
/* 52 */             this.mc.thePlayer.sendChatMessage(".config load Hive");
/* 53 */             currentconfig = "hive";
/*    */           } 
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\me\eagler\module\modules\misc\AutoConfig.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */