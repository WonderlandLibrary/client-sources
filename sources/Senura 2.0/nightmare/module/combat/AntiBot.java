/*    */ package nightmare.module.combat;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import net.minecraft.client.network.NetworkPlayerInfo;
/*    */ import net.minecraft.entity.Entity;
/*    */ import net.minecraft.network.play.server.S0CPacketSpawnPlayer;
/*    */ import nightmare.Nightmare;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventReceivePacket;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.settings.Setting;
/*    */ import nightmare.utils.ServerUtils;
/*    */ 
/*    */ public class AntiBot
/*    */   extends Module
/*    */ {
/*    */   public AntiBot() {
/* 20 */     super("AntiBot", 0, Category.COMBAT);
/*    */     
/* 22 */     ArrayList<String> options = new ArrayList<>();
/*    */     
/* 24 */     options.add("Normal");
/* 25 */     options.add("Hypixel");
/* 26 */     options.add("Advanced");
/*    */     
/* 28 */     Nightmare.instance.settingsManager.rSetting(new Setting("Mode", this, "Normal", options));
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 34 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*    */     
/* 36 */     if (mode.equals("Normal")) {
/* 37 */       for (Object entity : mc.field_71441_e.field_72996_f) {
/* 38 */         if (((Entity)entity).func_82150_aj() && entity != mc.field_71439_g) {
/* 39 */           mc.field_71441_e.func_72900_e((Entity)entity);
/*    */         }
/*    */       } 
/*    */     }
/*    */     
/* 44 */     if (mode.equals("Hypixel")) {
/* 45 */       for (Entity entity : mc.field_71441_e.field_72996_f) {
/* 46 */         if (entity.func_82150_aj() && entity != mc.field_71439_g && !isInTablist(entity)) {
/* 47 */           mc.field_71441_e.func_72900_e(entity);
/*    */         }
/*    */       } 
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @EventTarget
/*    */   public void onReceivePacket(EventReceivePacket event) {
/* 56 */     String mode = Nightmare.instance.settingsManager.getSettingByName(this, "Mode").getValString();
/*    */     
/* 58 */     if (mode.equalsIgnoreCase("Advanced") && event.getPacket() instanceof S0CPacketSpawnPlayer) {
/* 59 */       S0CPacketSpawnPlayer packet = (S0CPacketSpawnPlayer)event.getPacket();
/* 60 */       double posX = packet.func_148942_f() / 32.0D;
/* 61 */       double posY = packet.func_148949_g() / 32.0D;
/* 62 */       double posZ = packet.func_148946_h() / 32.0D;
/*    */       
/* 64 */       double diffX = mc.field_71439_g.field_70165_t - posX;
/* 65 */       double diffY = mc.field_71439_g.field_70163_u - posY;
/* 66 */       double diffZ = mc.field_71439_g.field_70161_v - posZ;
/*    */       
/* 68 */       double dist = Math.sqrt(diffX * diffX + diffY * diffY + diffZ * diffZ);
/*    */       
/* 70 */       if (dist <= 17.0D && posX != mc.field_71439_g.field_70165_t && posY != mc.field_71439_g.field_70163_u && posZ != mc.field_71439_g.field_70161_v) {
/* 71 */         event.setCancelled(true);
/*    */       }
/*    */     } 
/*    */   }
/*    */ 
/*    */   
/*    */   public boolean isInTablist(Entity entity) {
/* 78 */     if (ServerUtils.isOnServer()) {
/* 79 */       for (Object item : mc.func_147114_u().func_175106_d()) {
/* 80 */         NetworkPlayerInfo playerInfo = (NetworkPlayerInfo)item;
/*    */         
/* 82 */         if (playerInfo != null && playerInfo.func_178845_a() != null && playerInfo.func_178845_a().getName().contains(entity.func_70005_c_())) {
/* 83 */           return true;
/*    */         }
/*    */       } 
/*    */     }
/* 87 */     return false;
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\combat\AntiBot.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */