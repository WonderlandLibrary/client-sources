/*    */ package io.netty.channel;
/*    */ 
/*    */ import java.net.SocketAddress;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public abstract class AbstractServerChannel
/*    */   extends AbstractChannel
/*    */   implements ServerChannel
/*    */ {
/* 35 */   private static final ChannelMetadata METADATA = new ChannelMetadata(false);
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   protected AbstractServerChannel() {
/* 41 */     super(null);
/*    */   }
/*    */ 
/*    */   
/*    */   public ChannelMetadata metadata() {
/* 46 */     return METADATA;
/*    */   }
/*    */ 
/*    */   
/*    */   public SocketAddress remoteAddress() {
/* 51 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected SocketAddress remoteAddress0() {
/* 56 */     return null;
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doDisconnect() throws Exception {
/* 61 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   protected AbstractChannel.AbstractUnsafe newUnsafe() {
/* 66 */     return new DefaultServerUnsafe();
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 71 */     throw new UnsupportedOperationException();
/*    */   }
/*    */ 
/*    */   
/*    */   protected final Object filterOutboundMessage(Object msg) {
/* 76 */     throw new UnsupportedOperationException();
/*    */   }
/*    */   
/*    */   private final class DefaultServerUnsafe
/*    */     extends AbstractChannel.AbstractUnsafe {
/*    */     public void connect(SocketAddress remoteAddress, SocketAddress localAddress, ChannelPromise promise) {
/* 82 */       safeSetFailure(promise, new UnsupportedOperationException());
/*    */     }
/*    */     
/*    */     private DefaultServerUnsafe() {}
/*    */   }
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\AbstractServerChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */