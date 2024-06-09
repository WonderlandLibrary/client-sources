/*     */ package io.netty.handler.codec.compression;
/*     */ 
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import io.netty.util.internal.SystemPropertyUtil;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
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
/*     */ public final class ZlibCodecFactory
/*     */ {
/*  27 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(ZlibCodecFactory.class);
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  32 */   private static final boolean noJdkZlibDecoder = SystemPropertyUtil.getBoolean("io.netty.noJdkZlibDecoder", true); static {
/*  33 */     logger.debug("-Dio.netty.noJdkZlibDecoder: {}", Boolean.valueOf(noJdkZlibDecoder));
/*     */   }
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel) {
/*  37 */     if (PlatformDependent.javaVersion() < 7) {
/*  38 */       return new JZlibEncoder(compressionLevel);
/*     */     }
/*  40 */     return new JdkZlibEncoder(compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper) {
/*  45 */     if (PlatformDependent.javaVersion() < 7) {
/*  46 */       return new JZlibEncoder(wrapper);
/*     */     }
/*  48 */     return new JdkZlibEncoder(wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel) {
/*  53 */     if (PlatformDependent.javaVersion() < 7) {
/*  54 */       return new JZlibEncoder(wrapper, compressionLevel);
/*     */     }
/*  56 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(ZlibWrapper wrapper, int compressionLevel, int windowBits, int memLevel) {
/*  61 */     if (PlatformDependent.javaVersion() < 7) {
/*  62 */       return new JZlibEncoder(wrapper, compressionLevel, windowBits, memLevel);
/*     */     }
/*  64 */     return new JdkZlibEncoder(wrapper, compressionLevel);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(byte[] dictionary) {
/*  69 */     if (PlatformDependent.javaVersion() < 7) {
/*  70 */       return new JZlibEncoder(dictionary);
/*     */     }
/*  72 */     return new JdkZlibEncoder(dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, byte[] dictionary) {
/*  77 */     if (PlatformDependent.javaVersion() < 7) {
/*  78 */       return new JZlibEncoder(compressionLevel, dictionary);
/*     */     }
/*  80 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibEncoder newZlibEncoder(int compressionLevel, int windowBits, int memLevel, byte[] dictionary) {
/*  85 */     if (PlatformDependent.javaVersion() < 7) {
/*  86 */       return new JZlibEncoder(compressionLevel, windowBits, memLevel, dictionary);
/*     */     }
/*  88 */     return new JdkZlibEncoder(compressionLevel, dictionary);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder() {
/*  93 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/*  94 */       return new JZlibDecoder();
/*     */     }
/*  96 */     return new JdkZlibDecoder();
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(ZlibWrapper wrapper) {
/* 101 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/* 102 */       return new JZlibDecoder(wrapper);
/*     */     }
/* 104 */     return new JdkZlibDecoder(wrapper);
/*     */   }
/*     */ 
/*     */   
/*     */   public static ZlibDecoder newZlibDecoder(byte[] dictionary) {
/* 109 */     if (PlatformDependent.javaVersion() < 7 || noJdkZlibDecoder) {
/* 110 */       return new JZlibDecoder(dictionary);
/*     */     }
/* 112 */     return new JdkZlibDecoder(dictionary);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\compression\ZlibCodecFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */