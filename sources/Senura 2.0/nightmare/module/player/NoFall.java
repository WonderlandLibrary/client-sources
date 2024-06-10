/*    */ package nightmare.module.player;
/*    */ 
/*    */ import net.minecraft.network.Packet;
/*    */ import net.minecraft.network.play.client.C03PacketPlayer;
/*    */ import nightmare.event.EventTarget;
/*    */ import nightmare.event.impl.EventUpdate;
/*    */ import nightmare.module.Category;
/*    */ import nightmare.module.Module;
/*    */ 
/*    */ public class NoFall extends Module {
/*    */   public NoFall() {
/* 12 */     super("NoFall", 0, Category.PLAYER);
/*    */     
/* 14 */     setBlatantModule(true);
/*    */   }
/*    */   
/*    */   @EventTarget
/*    */   public void onUpdate(EventUpdate event) {
/* 19 */     if (mc.field_71439_g.field_70143_R > 2.0F)
/* 20 */       mc.field_71439_g.field_71174_a.func_147297_a((Packet)new C03PacketPlayer(true)); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\module\player\NoFall.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */