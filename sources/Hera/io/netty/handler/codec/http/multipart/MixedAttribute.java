/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.nio.charset.Charset;
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
/*     */ public class MixedAttribute
/*     */   implements Attribute
/*     */ {
/*     */   private Attribute attribute;
/*     */   private final long limitSize;
/*     */   
/*     */   public MixedAttribute(String name, long limitSize) {
/*  34 */     this.limitSize = limitSize;
/*  35 */     this.attribute = new MemoryAttribute(name);
/*     */   }
/*     */   
/*     */   public MixedAttribute(String name, String value, long limitSize) {
/*  39 */     this.limitSize = limitSize;
/*  40 */     if (value.length() > this.limitSize) {
/*     */       try {
/*  42 */         this.attribute = new DiskAttribute(name, value);
/*  43 */       } catch (IOException e) {
/*     */         
/*     */         try {
/*  46 */           this.attribute = new MemoryAttribute(name, value);
/*  47 */         } catch (IOException e1) {
/*  48 */           throw new IllegalArgumentException(e);
/*     */         } 
/*     */       } 
/*     */     } else {
/*     */       try {
/*  53 */         this.attribute = new MemoryAttribute(name, value);
/*  54 */       } catch (IOException e) {
/*  55 */         throw new IllegalArgumentException(e);
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  62 */     if (this.attribute instanceof MemoryAttribute && 
/*  63 */       this.attribute.length() + buffer.readableBytes() > this.limitSize) {
/*  64 */       DiskAttribute diskAttribute = new DiskAttribute(this.attribute.getName());
/*     */       
/*  66 */       if (((MemoryAttribute)this.attribute).getByteBuf() != null) {
/*  67 */         diskAttribute.addContent(((MemoryAttribute)this.attribute).getByteBuf(), false);
/*     */       }
/*     */       
/*  70 */       this.attribute = diskAttribute;
/*     */     } 
/*     */     
/*  73 */     this.attribute.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/*  78 */     this.attribute.delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/*  83 */     return this.attribute.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/*  88 */     return this.attribute.getByteBuf();
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset getCharset() {
/*  93 */     return this.attribute.getCharset();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/*  98 */     return this.attribute.getString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 103 */     return this.attribute.getString(encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 108 */     return this.attribute.isCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 113 */     return this.attribute.isInMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 118 */     return this.attribute.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 123 */     return this.attribute.renameTo(dest);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharset(Charset charset) {
/* 128 */     this.attribute.setCharset(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/* 133 */     if (buffer.readableBytes() > this.limitSize && 
/* 134 */       this.attribute instanceof MemoryAttribute)
/*     */     {
/* 136 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/*     */     
/* 139 */     this.attribute.setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 144 */     if (file.length() > this.limitSize && 
/* 145 */       this.attribute instanceof MemoryAttribute)
/*     */     {
/* 147 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/*     */     
/* 150 */     this.attribute.setContent(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 155 */     if (this.attribute instanceof MemoryAttribute)
/*     */     {
/* 157 */       this.attribute = new DiskAttribute(this.attribute.getName());
/*     */     }
/* 159 */     this.attribute.setContent(inputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/* 164 */     return this.attribute.getHttpDataType();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 169 */     return this.attribute.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/* 174 */     return this.attribute.compareTo(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 179 */     return "Mixed: " + this.attribute.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getValue() throws IOException {
/* 184 */     return this.attribute.getValue();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setValue(String value) throws IOException {
/* 189 */     this.attribute.setValue(value);
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 194 */     return this.attribute.getChunk(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 199 */     return this.attribute.getFile();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute copy() {
/* 204 */     return this.attribute.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute duplicate() {
/* 209 */     return this.attribute.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/* 214 */     return this.attribute.content();
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 219 */     return this.attribute.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain() {
/* 224 */     this.attribute.retain();
/* 225 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute retain(int increment) {
/* 230 */     this.attribute.retain(increment);
/* 231 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 236 */     return this.attribute.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 241 */     return this.attribute.release(decrement);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\MixedAttribute.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */