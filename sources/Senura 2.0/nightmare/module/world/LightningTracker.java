/*    */ package nightmare.module.world;
/*    */ 
/*    */ import net.minecraft.network.play.server.S29PacketSoundEffect;
/*    */ import net.minecraft.util.EnumChatFormatting;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventReceivePacket;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ import nightmare.utils.ChatUtils;
/*    */ 
/*    */ public class LightningTracker
/*    */   extends Module {
/*    */   public LightningTracker() {
/* 14 */     super("LightningTracker", 0, Category.WORLD);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onPacket(EventReceivePacket e) {
/* 19 */     if (e.getPacket() instanceof S29PacketSoundEffect) {
/* 20 */       S29PacketSoundEffect packet = (S29PacketSoundEffect)e.getPacket();
/*    */       
/* 22 */       if (packet.func_149212_c().equals("ambient.weather.thunder")) {
/* 23 */         int x = (int)packet.func_149207_d(), y = (int)packet.func_149211_e(), z = (int)packet.func_149210_f();
/* 24 */         ChatUtils.sendPrivateChatMessage(EnumChatFormatting.YELLOW + "Lightning detected " + EnumChatFormatting.RESET + "X: " + x + " Y: " + y + " Z:" + z);
/*    */       } 
/*    */     } 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\world\LightningTracker.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */