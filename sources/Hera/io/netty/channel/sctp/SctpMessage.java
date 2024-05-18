/*     */ package io.netty.channel.sctp;
/*     */ 
/*     */ import com.sun.nio.sctp.MessageInfo;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.ByteBufUtil;
/*     */ import io.netty.buffer.DefaultByteBufHolder;
/*     */ import io.netty.util.ReferenceCounted;
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
/*     */ public final class SctpMessage
/*     */   extends DefaultByteBufHolder
/*     */ {
/*     */   private final int streamIdentifier;
/*     */   private final int protocolIdentifier;
/*     */   private final MessageInfo msgInfo;
/*     */   
/*     */   public SctpMessage(int protocolIdentifier, int streamIdentifier, ByteBuf payloadBuffer) {
/*  39 */     super(payloadBuffer);
/*  40 */     this.protocolIdentifier = protocolIdentifier;
/*  41 */     this.streamIdentifier = streamIdentifier;
/*  42 */     this.msgInfo = null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpMessage(MessageInfo msgInfo, ByteBuf payloadBuffer) {
/*  51 */     super(payloadBuffer);
/*  52 */     if (msgInfo == null) {
/*  53 */       throw new NullPointerException("msgInfo");
/*     */     }
/*  55 */     this.msgInfo = msgInfo;
/*  56 */     this.streamIdentifier = msgInfo.streamNumber();
/*  57 */     this.protocolIdentifier = msgInfo.payloadProtocolID();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int streamIdentifier() {
/*  64 */     return this.streamIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public int protocolIdentifier() {
/*  71 */     return this.protocolIdentifier;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MessageInfo messageInfo() {
/*  79 */     return this.msgInfo;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean isComplete() {
/*  86 */     if (this.msgInfo != null) {
/*  87 */       return this.msgInfo.isComplete();
/*     */     }
/*     */     
/*  90 */     return true;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  96 */     if (this == o) {
/*  97 */       return true;
/*     */     }
/*     */     
/* 100 */     if (o == null || getClass() != o.getClass()) {
/* 101 */       return false;
/*     */     }
/*     */     
/* 104 */     SctpMessage sctpFrame = (SctpMessage)o;
/*     */     
/* 106 */     if (this.protocolIdentifier != sctpFrame.protocolIdentifier) {
/* 107 */       return false;
/*     */     }
/*     */     
/* 110 */     if (this.streamIdentifier != sctpFrame.streamIdentifier) {
/* 111 */       return false;
/*     */     }
/*     */     
/* 114 */     if (!content().equals(sctpFrame.content())) {
/* 115 */       return false;
/*     */     }
/*     */     
/* 118 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/* 123 */     int result = this.streamIdentifier;
/* 124 */     result = 31 * result + this.protocolIdentifier;
/* 125 */     result = 31 * result + content().hashCode();
/* 126 */     return result;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpMessage copy() {
/* 131 */     if (this.msgInfo == null) {
/* 132 */       return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, content().copy());
/*     */     }
/* 134 */     return new SctpMessage(this.msgInfo, content().copy());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpMessage duplicate() {
/* 140 */     if (this.msgInfo == null) {
/* 141 */       return new SctpMessage(this.protocolIdentifier, this.streamIdentifier, content().duplicate());
/*     */     }
/* 143 */     return new SctpMessage(this.msgInfo, content().copy());
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SctpMessage retain() {
/* 149 */     super.retain();
/* 150 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public SctpMessage retain(int increment) {
/* 155 */     super.retain(increment);
/* 156 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 161 */     if (refCnt() == 0) {
/* 162 */       return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=(FREED)}";
/*     */     }
/*     */ 
/*     */     
/* 166 */     return "SctpFrame{streamIdentifier=" + this.streamIdentifier + ", protocolIdentifier=" + this.protocolIdentifier + ", data=" + ByteBufUtil.hexDump(content()) + '}';
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\channel\sctp\SctpMessage.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */