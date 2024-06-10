/*    */ package nightmare.utils.staffanalyser;
/*    */ 
/*    */ import com.google.gson.Gson;
/*    */ import java.net.URL;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.gui.notification.NotificationManager;
/*    */ import nightmare.gui.notification.NotificationType;
/*    */ import nightmare.module.world.StaffAnalyser;
/*    */ import nightmare.utils.ChatUtils;
/*    */ 
/*    */ 
/*    */ public class CheckThread
/*    */   extends Thread
/*    */ {
/* 15 */   int lastBannedCount = 0;
/*    */ 
/*    */ 
/*    */   
/*    */   public void run() {
/*    */     while (true) {
/*    */       try {
/* 22 */         if (StaffAnalyser.key == null) {
/* 23 */           Thread.sleep(1000L);
/*    */           continue;
/*    */         } 
/* 26 */         Thread.sleep((long)(Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() * 1000.0D));
/* 27 */         String result = HttpUtils.performGetRequest(new URL("https://api.hypixel.net/watchdogStats?key=" + StaffAnalyser.key));
/* 28 */         Gson gson = new Gson();
/* 29 */         BanQuantityListJSON banQuantityListJSON = (BanQuantityListJSON)gson.fromJson(result, BanQuantityListJSON.class);
/* 30 */         int staffTotal = banQuantityListJSON.getStaffTotal();
/* 31 */         if (this.lastBannedCount == 0) {
/* 32 */           this.lastBannedCount = staffTotal;
/*    */           continue;
/*    */         } 
/* 35 */         int banned = staffTotal - this.lastBannedCount;
/* 36 */         this.lastBannedCount = staffTotal;
/* 37 */         if (banned > 1) {
/* 38 */           if (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Notification").getValBoolean()) {
/* 39 */             if (banned >= 5) {
/* 40 */               NotificationManager.show(NotificationType.ERROR, "StaffAnalyser", "Staff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000); continue;
/*    */             } 
/* 42 */             NotificationManager.show(NotificationType.WARNING, "StaffAnalyser", "Staff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000);
/*    */             
/*    */             continue;
/*    */           } 
/* 46 */           ChatUtils.sendPrivateChatMessage("��cStaff banned " + banned + " players in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.");
/*    */           continue;
/*    */         } 
/* 49 */         if (Nightmare.instance.moduleManager.getModuleByName("HUD").isToggled() && Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("HUD"), "Notification").getValBoolean()) {
/* 50 */           NotificationManager.show(NotificationType.SUCCESS, "StaffAnalyser", "Staff didn't ban any player in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.", 10000);
/*    */           continue;
/*    */         } 
/* 53 */         ChatUtils.sendPrivateChatMessage("��aStaff didn't ban any player in " + Nightmare.instance.settingsManager.getSettingByName(Nightmare.instance.moduleManager.getModuleByName("StaffAnalyser"), "Delay").getValDouble() + "s.");
/*    */ 
/*    */ 
/*    */ 
/*    */       
/*    */       }
/* 59 */       catch (Exception e) {
/* 60 */         e.printStackTrace();
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmar\\utils\staffanalyser\CheckThread.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */