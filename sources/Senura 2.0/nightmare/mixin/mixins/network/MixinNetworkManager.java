/*    */ package nightmare.mixin.mixins.network;
/*    */ 
/*    */ import io.netty.channel.ChannelHandlerContext;
/*    */ import net.minecraft.network.NetworkManager;
/*    */ import net.minecraft.network.Packet;
/*    */ import nightmare.event.impl.EventReceivePacket;
/*    */ import nightmare.event.impl.EventSendPacket;
/*    */ import org.spongepowered.asm.mixin.Mixin;
/*    */ import org.spongepowered.asm.mixin.injection.At;
/*    */ import org.spongepowered.asm.mixin.injection.Inject;
/*    */ import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
/*    */ 
/*    */ 
/*    */ @Mixin({NetworkManager.class})
/*    */ public class MixinNetworkManager
/*    */ {
/*    */   @Inject(method = {"channelRead0"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void channelRead0(ChannelHandlerContext context, Packet<?> packet, CallbackInfo ci) {
/* 19 */     EventReceivePacket event = new EventReceivePacket(packet);
/* 20 */     event.call();
/*    */     
/* 22 */     if (event.isCancelled()) {
/* 23 */       ci.cancel();
/*    */     }
/*    */   }
/*    */ 
/*    */   
/*    */   @Inject(method = {"sendPacket(Lnet/minecraft/network/Packet;)V"}, at = {@At("HEAD")}, cancellable = true)
/*    */   private void sendPacket(Packet<?> packet, CallbackInfo ci) {
/* 30 */     EventSendPacket event = new EventSendPacket(packet);
/* 31 */     event.call();
/*    */     
/* 33 */     if (event.isCancelled())
/* 34 */       ci.cancel(); 
/*    */   }
/*    */ }


/* Location:              C:\Users\Syz\Downloads\Senura (1).jar!\nightmare\mixin\mixins\network\MixinNetworkManager.class
 * Java compiler version: 8 (52.0)
 * JD-Core Version:       1.1.3
 */