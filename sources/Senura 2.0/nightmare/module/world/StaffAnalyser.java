/*    */ package nightmare.module.world;
/*    */ 
/*    */ import net.minecraft.network.play.server.S02PacketChat;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventPreMotionUpdate;
/*    */ import nightmare.event.impl.EventReceivePacket;
/*    */ import nightmare.event.impl.EventRespawn;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.ServerUtils;
/*    */ import nightmare.utils.TimerUtils;
/*    */ import nightmare.utils.staffanalyser.CheckThread;
/*    */ 
/*    */ public class StaffAnalyser
/*    */   extends Module {
/* 18 */   public static String key = null;
/* 19 */   private TimerUtils timer = new TimerUtils();
/*    */   private CheckThread thread;
/*    */   
/*    */   public StaffAnalyser() {
/* 23 */     super("StaffAnalyser", 0, Category.WORLD);
/*    */     
/* 25 */     this.thread = new CheckThread();
/* 26 */     this.thread.start();
/*    */     
/* 28 */     Nightmare.instance.settingsManager.rSetting(new Setting("Delay", this, 60.0D, 10.0D, 120.0D, true));
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onPreUpdate(EventPreMotionUpdate event) {
/* 33 */     if (ServerUtils.isOnHypixel() && this.timer.delay(3000.0D) && key == null) {
/* 34 */       mc.field_71439_g.func_71165_d("/api new");
/* 35 */       this.timer.reset();
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onReceivePacket(EventReceivePacket event) {
/* 41 */     if (event.getPacket() instanceof S02PacketChat) {
/* 42 */       S02PacketChat chatPacket = (S02PacketChat)event.getPacket();
/* 43 */       String chatMessage = chatPacket.func_148915_c().func_150260_c();
/* 44 */       if (chatMessage.matches("Your new API key is ........-....-....-....-............")) {
/* 45 */         event.setCancelled(true);
/* 46 */         key = chatMessage.replace("Your new API key is ", "");
/*    */       } 
/*    */     } 
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onRespawn(EventRespawn event) {
/* 53 */     key = null;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\StaffAnalyser.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */