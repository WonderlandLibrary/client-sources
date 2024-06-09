/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.buffer.Unpooled;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.handler.codec.http.HttpConstants;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.IOException;
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
/*     */ public class MemoryAttribute
/*     */   extends AbstractMemoryHttpData
/*     */   implements Attribute
/*     */ {
/*     */   public MemoryAttribute(String name) {
/*  32 */     super(name, HttpConstants.DEFAULT_CHARSET, 0L);
/*     */   }
/*     */   
/*     */   public MemoryAttribute(String name, String value) throws IOException {
/*  36 */     super(name, HttpConstants.DEFAULT_CHARSET, 0L);
/*  37 */     setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  42 */     return InterfaceHttpData.HttpDataType.Attribute;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() {
/*  47 */     return getByteBuf().toString(this.charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/*  52 */     if (value == null) {
/*  53 */       throw new NullPointerException("value");
/*     */     }
/*  55 */     byte[] bytes = value.getBytes(this.charset.name());
/*  56 */     ByteBuf buffer = Unpooled.wrappedBuffer(bytes);
/*  57 */     if (this.definedSize > 0L) {
/*  58 */       this.definedSize = buffer.readableBytes();
/*     */     }
/*  60 */     setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  65 */     int localsize = buffer.readableBytes();
/*  66 */     if (this.definedSize > 0L && this.definedSize < this.size + localsize) {
/*  67 */       this.definedSize = this.size + localsize;
/*     */     }
/*  69 */     super.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  74 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  79 */     if (!(o instanceof Attribute)) {
/*  80 */       return false;
/*     */     }
/*  82 */     Attribute attribute = (Attribute)o;
/*  83 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData other) {
/*  88 */     if (!(other instanceof Attribute)) {
/*  89 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + other.getHttpDataType());
/*     */     }
/*     */     
/*  92 */     return compareTo((Attribute)other);
/*     */   }
/*     */   
/*     */   public int compareTo(Attribute o) {
/*  96 */     return getName().compareToIgnoreCase(o.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 101 */     return getName() + '=' + getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 106 */     MemoryAttribute attr = new MemoryAttribute(getName());
/* 107 */     attr.setCharset(getCharset());
/* 108 */     ByteBuf content = content();
/* 109 */     if (content != null) {
/*     */       try {
/* 111 */         attr.setContent(content.copy());
/* 112 */       } catch (IOException e) {
/* 113 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 116 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 121 */     MemoryAttribute attr = new MemoryAttribute(getName());
/* 122 */     attr.setCharset(getCharset());
/* 123 */     ByteBuf content = content();
/* 124 */     if (content != null) {
/*     */       try {
/* 126 */         attr.setContent(content.duplicate());
/* 127 */       } catch (IOException e) {
/* 128 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 131 */     return attr;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 136 */     super.retain();
/* 137 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 142 */     super.retain(increment);
/* 143 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\MemoryAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */