/*     */ package io.netty.handler.codec.protobuf;
/*     */ 
/*     */ import com.google.protobuf.ExtensionRegistry;
/*     */ import com.google.protobuf.ExtensionRegistryLite;
/*     */ import com.google.protobuf.MessageLite;
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.channel.ChannelHandler.Sharable;
/*     */ import io.netty.channel.ChannelHandlerContext;
/*     */ import io.netty.handler.codec.MessageToMessageDecoder;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ @Sharable
/*     */ public class ProtobufDecoder
/*     */   extends MessageToMessageDecoder<ByteBuf>
/*     */ {
/*     */   private static final boolean HAS_PARSER;
/*     */   private final MessageLite prototype;
/*     */   private final ExtensionRegistry extensionRegistry;
/*     */   
/*     */   static {
/*  68 */     boolean hasParser = false;
/*     */     
/*     */     try {
/*  71 */       MessageLite.class.getDeclaredMethod("getParserForType", new Class[0]);
/*  72 */       hasParser = true;
/*  73 */     } catch (Throwable t) {}
/*     */ 
/*     */ 
/*     */     
/*  77 */     HAS_PARSER = hasParser;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public ProtobufDecoder(MessageLite prototype) {
/*  87 */     this(prototype, null);
/*     */   }
/*     */   
/*     */   public ProtobufDecoder(MessageLite prototype, ExtensionRegistry extensionRegistry) {
/*  91 */     if (prototype == null) {
/*  92 */       throw new NullPointerException("prototype");
/*     */     }
/*  94 */     this.prototype = prototype.getDefaultInstanceForType();
/*  95 */     this.extensionRegistry = extensionRegistry;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
/*     */     byte[] array;
/* 102 */     int offset, length = msg.readableBytes();
/* 103 */     if (msg.hasArray()) {
/* 104 */       array = msg.array();
/* 105 */       offset = msg.arrayOffset() + msg.readerIndex();
/*     */     } else {
/* 107 */       array = new byte[length];
/* 108 */       msg.getBytes(msg.readerIndex(), array, 0, length);
/* 109 */       offset = 0;
/*     */     } 
/*     */     
/* 112 */     if (this.extensionRegistry == null) {
/* 113 */       if (HAS_PARSER) {
/* 114 */         out.add(this.prototype.getParserForType().parseFrom(array, offset, length));
/*     */       } else {
/* 116 */         out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length).build());
/*     */       }
/*     */     
/* 119 */     } else if (HAS_PARSER) {
/* 120 */       out.add(this.prototype.getParserForType().parseFrom(array, offset, length, (ExtensionRegistryLite)this.extensionRegistry));
/*     */     } else {
/* 122 */       out.add(this.prototype.newBuilderForType().mergeFrom(array, offset, length, (ExtensionRegistryLite)this.extensionRegistry).build());
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\protobuf\ProtobufDecoder.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */