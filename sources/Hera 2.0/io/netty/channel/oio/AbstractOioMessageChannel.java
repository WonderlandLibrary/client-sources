/*    */ package io.netty.channel.oio;
/*    */ 
/*    */ import io.netty.channel.Channel;
/*    */ import io.netty.channel.ChannelConfig;
/*    */ import io.netty.channel.ChannelPipeline;
/*    */ import java.util.ArrayList;
/*    */ import java.util.List;
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
/*    */ public abstract class AbstractOioMessageChannel
/*    */   extends AbstractOioChannel
/*    */ {
/* 31 */   private final List<Object> readBuf = new ArrayList();
/*    */   
/*    */   protected AbstractOioMessageChannel(Channel parent) {
/* 34 */     super(parent);
/*    */   }
/*    */ 
/*    */   
/*    */   protected void doRead() {
/* 39 */     ChannelConfig config = config();
/* 40 */     ChannelPipeline pipeline = pipeline();
/* 41 */     boolean closed = false;
/* 42 */     int maxMessagesPerRead = config.getMaxMessagesPerRead();
/*    */     
/* 44 */     Throwable exception = null;
/* 45 */     int localRead = 0;
/*    */     try {
/*    */       do {
/* 48 */         localRead = doReadMessages(this.readBuf);
/* 49 */         if (localRead == 0) {
/*    */           break;
/*    */         }
/* 52 */         if (localRead < 0) {
/* 53 */           closed = true;
/*    */           
/*    */           break;
/*    */         } 
/* 57 */       } while (this.readBuf.size() < maxMessagesPerRead && config.isAutoRead());
/*    */ 
/*    */     
/*    */     }
/* 61 */     catch (Throwable t) {
/* 62 */       exception = t;
/*    */     } 
/*    */     
/* 65 */     int size = this.readBuf.size();
/* 66 */     for (int i = 0; i < size; i++) {
/* 67 */       pipeline.fireChannelRead(this.readBuf.get(i));
/*    */     }
/* 69 */     this.readBuf.clear();
/* 70 */     pipeline.fireChannelReadComplete();
/*    */     
/* 72 */     if (exception != null) {
/* 73 */       if (exception instanceof java.io.IOException) {
/* 74 */         closed = true;
/*    */       }
/*    */       
/* 77 */       pipeline().fireExceptionCaught(exception);
/*    */     } 
/*    */     
/* 80 */     if (closed) {
/* 81 */       if (isOpen()) {
/* 82 */         unsafe().close(unsafe().voidPromise());
/*    */       }
/* 84 */     } else if (localRead == 0 && isActive()) {
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */       
/* 91 */       read();
/*    */     } 
/*    */   }
/*    */   
/*    */   protected abstract int doReadMessages(List<Object> paramList) throws Exception;
/*    */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\oio\AbstractOioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */