/*     */ package io.netty.channel.nio;
/*     */ 
/*     */ import io.netty.channel.AbstractChannel;
/*     */ import io.netty.channel.Channel;
/*     */ import io.netty.channel.ChannelConfig;
/*     */ import io.netty.channel.ChannelOutboundBuffer;
/*     */ import io.netty.channel.ChannelPipeline;
/*     */ import java.io.IOException;
/*     */ import java.nio.channels.SelectableChannel;
/*     */ import java.nio.channels.SelectionKey;
/*     */ import java.util.ArrayList;
/*     */ import java.util.List;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public abstract class AbstractNioMessageChannel
/*     */   extends AbstractNioChannel
/*     */ {
/*     */   protected AbstractNioMessageChannel(Channel parent, SelectableChannel ch, int readInterestOp) {
/*  39 */     super(parent, ch, readInterestOp);
/*     */   }
/*     */ 
/*     */   
/*     */   protected AbstractNioChannel.AbstractNioUnsafe newUnsafe() {
/*  44 */     return new NioMessageUnsafe();
/*     */   }
/*     */   
/*     */   private final class NioMessageUnsafe extends AbstractNioChannel.AbstractNioUnsafe {
/*     */     private NioMessageUnsafe() {
/*  49 */       this.readBuf = new ArrayList();
/*     */     }
/*     */     private final List<Object> readBuf;
/*     */     public void read() {
/*  53 */       assert AbstractNioMessageChannel.this.eventLoop().inEventLoop();
/*  54 */       ChannelConfig config = AbstractNioMessageChannel.this.config();
/*  55 */       if (!config.isAutoRead() && !AbstractNioMessageChannel.this.isReadPending()) {
/*     */         
/*  57 */         removeReadOp();
/*     */         
/*     */         return;
/*     */       } 
/*  61 */       int maxMessagesPerRead = config.getMaxMessagesPerRead();
/*  62 */       ChannelPipeline pipeline = AbstractNioMessageChannel.this.pipeline();
/*  63 */       boolean closed = false;
/*  64 */       Throwable exception = null;
/*     */       try {
/*     */         while (true) {
/*     */           try {
/*  68 */             int localRead = AbstractNioMessageChannel.this.doReadMessages(this.readBuf);
/*  69 */             if (localRead == 0) {
/*     */               break;
/*     */             }
/*  72 */             if (localRead < 0) {
/*  73 */               closed = true;
/*     */               
/*     */               break;
/*     */             } 
/*     */             
/*  78 */             if (!config.isAutoRead()) {
/*     */               break;
/*     */             }
/*     */             
/*  82 */             if (this.readBuf.size() >= maxMessagesPerRead) {
/*     */               break;
/*     */             }
/*     */           }
/*  86 */           catch (Throwable t) {
/*  87 */             exception = t; break;
/*     */           } 
/*  89 */         }  AbstractNioMessageChannel.this.setReadPending(false);
/*  90 */         int size = this.readBuf.size();
/*  91 */         for (int i = 0; i < size; i++) {
/*  92 */           pipeline.fireChannelRead(this.readBuf.get(i));
/*     */         }
/*     */         
/*  95 */         this.readBuf.clear();
/*  96 */         pipeline.fireChannelReadComplete();
/*     */         
/*  98 */         if (exception != null) {
/*  99 */           if (exception instanceof IOException)
/*     */           {
/*     */             
/* 102 */             closed = !(AbstractNioMessageChannel.this instanceof io.netty.channel.ServerChannel);
/*     */           }
/*     */           
/* 105 */           pipeline.fireExceptionCaught(exception);
/*     */         } 
/*     */         
/* 108 */         if (closed && 
/* 109 */           AbstractNioMessageChannel.this.isOpen()) {
/* 110 */           close(voidPromise());
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       finally {
/*     */ 
/*     */         
/* 120 */         if (!config.isAutoRead() && !AbstractNioMessageChannel.this.isReadPending()) {
/* 121 */           removeReadOp();
/*     */         }
/*     */       } 
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   protected void doWrite(ChannelOutboundBuffer in) throws Exception {
/* 129 */     SelectionKey key = selectionKey();
/* 130 */     int interestOps = key.interestOps();
/*     */     
/*     */     while (true) {
/* 133 */       Object msg = in.current();
/* 134 */       if (msg == null) {
/*     */         
/* 136 */         if ((interestOps & 0x4) != 0) {
/* 137 */           key.interestOps(interestOps & 0xFFFFFFFB);
/*     */         }
/*     */         break;
/*     */       } 
/*     */       try {
/* 142 */         boolean done = false;
/* 143 */         for (int i = config().getWriteSpinCount() - 1; i >= 0; i--) {
/* 144 */           if (doWriteMessage(msg, in)) {
/* 145 */             done = true;
/*     */             
/*     */             break;
/*     */           } 
/*     */         } 
/* 150 */         if (done) {
/* 151 */           in.remove();
/*     */           continue;
/*     */         } 
/* 154 */         if ((interestOps & 0x4) == 0) {
/* 155 */           key.interestOps(interestOps | 0x4);
/*     */         
/*     */         }
/*     */       }
/* 159 */       catch (IOException e) {
/* 160 */         if (continueOnWriteError()) {
/* 161 */           in.remove(e); continue;
/*     */         } 
/* 163 */         throw e;
/*     */       } 
/*     */       break;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   protected boolean continueOnWriteError() {
/* 173 */     return false;
/*     */   }
/*     */   
/*     */   protected abstract int doReadMessages(List<Object> paramList) throws Exception;
/*     */   
/*     */   protected abstract boolean doWriteMessage(Object paramObject, ChannelOutboundBuffer paramChannelOutboundBuffer) throws Exception;
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\nio\AbstractNioMessageChannel.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */