/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.AbstractReferenceCounted;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.regex.Pattern;
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
/*     */ public abstract class AbstractHttpData
/*     */   extends AbstractReferenceCounted
/*     */   implements HttpData
/*     */ {
/*  32 */   private static final Pattern STRIP_PATTERN = Pattern.compile("(?:^\\s+|\\s+$|\\n)");
/*  33 */   private static final Pattern REPLACE_PATTERN = Pattern.compile("[\\r\\t]");
/*     */   
/*     */   protected final String name;
/*     */   protected long definedSize;
/*     */   protected long size;
/*  38 */   protected Charset charset = HttpConstants.DEFAULT_CHARSET;
/*     */   protected boolean completed;
/*     */   
/*     */   protected AbstractHttpData(String name, Charset charset, long size) {
/*  42 */     if (name == null) {
/*  43 */       throw new NullPointerException("name");
/*     */     }
/*     */     
/*  46 */     name = REPLACE_PATTERN.matcher(name).replaceAll(" ");
/*  47 */     name = STRIP_PATTERN.matcher(name).replaceAll("");
/*     */     
/*  49 */     if (name.isEmpty()) {
/*  50 */       throw new IllegalArgumentException("empty name");
/*     */     }
/*     */     
/*  53 */     this.name = name;
/*  54 */     if (charset != null) {
/*  55 */       setCharset(charset);
/*     */     }
/*  57 */     this.definedSize = size;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/*  62 */     return this.name;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/*  67 */     return this.completed;
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset getCharset() {
/*  72 */     return this.charset;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharset(Charset charset) {
/*  77 */     if (charset == null) {
/*  78 */       throw new NullPointerException("charset");
/*     */     }
/*  80 */     this.charset = charset;
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/*  85 */     return this.size;
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/*     */     try {
/*  91 */       return getByteBuf();
/*  92 */     } catch (IOException e) {
/*  93 */       throw new ChannelException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   protected void deallocate() {
/*  99 */     delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData retain() {
/* 104 */     super.retain();
/* 105 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public HttpData retain(int increment) {
/* 110 */     super.retain(increment);
/* 111 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\AbstractHttpData.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */