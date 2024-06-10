/*    */ package nightmare.mixin.mixins.network;
/*    */ 
/*    */ import net.minecraft.client.network.NetHandlerPlayClient;
/*    */ import net.minecraft.network.play.server.S01PacketJoinGame;
/*    */ import nightmare.event.impl.EventRespawn;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({NetHandlerPlayClient.class})
/*    */ public class MixinNetHandlerPlayClient
/*    */ {
/*    */   @Inject(method = {"handleJoinGame"}, at = {@At("TAIL")})
/*    */   public void handleJoinGame(S01PacketJoinGame packetIn, CallbackInfo ci) {
/* 17 */     EventRespawn event = new EventRespawn();
/* 18 */     event.call();
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\network\MixinNetHandlerPlayClient.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */