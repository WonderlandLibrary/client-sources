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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class MixedFileUpload
/*     */   implements FileUpload
/*     */ {
/*     */   private FileUpload fileUpload;
/*     */   private final long limitSize;
/*     */   private final long definedSize;
/*     */   
/*     */   public MixedFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size, long limitSize) {
/*  39 */     this.limitSize = limitSize;
/*  40 */     if (size > this.limitSize) {
/*  41 */       this.fileUpload = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */     } else {
/*     */       
/*  44 */       this.fileUpload = new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */     } 
/*     */     
/*  47 */     this.definedSize = size;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void addContent(ByteBuf buffer, boolean last) throws IOException {
/*  53 */     if (this.fileUpload instanceof MemoryFileUpload && 
/*  54 */       this.fileUpload.length() + buffer.readableBytes() > this.limitSize) {
/*  55 */       DiskFileUpload diskFileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  61 */       ByteBuf data = this.fileUpload.getByteBuf();
/*  62 */       if (data != null && data.isReadable()) {
/*  63 */         diskFileUpload.addContent(data.retain(), false);
/*     */       }
/*     */       
/*  66 */       this.fileUpload.release();
/*     */       
/*  68 */       this.fileUpload = diskFileUpload;
/*     */     } 
/*     */     
/*  71 */     this.fileUpload.addContent(buffer, last);
/*     */   }
/*     */ 
/*     */   
/*     */   public void delete() {
/*  76 */     this.fileUpload.delete();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] get() throws IOException {
/*  81 */     return this.fileUpload.get();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getByteBuf() throws IOException {
/*  86 */     return this.fileUpload.getByteBuf();
/*     */   }
/*     */ 
/*     */   
/*     */   public Charset getCharset() {
/*  91 */     return this.fileUpload.getCharset();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/*  96 */     return this.fileUpload.getContentType();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 101 */     return this.fileUpload.getContentTransferEncoding();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/* 106 */     return this.fileUpload.getFilename();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString() throws IOException {
/* 111 */     return this.fileUpload.getString();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getString(Charset encoding) throws IOException {
/* 116 */     return this.fileUpload.getString(encoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isCompleted() {
/* 121 */     return this.fileUpload.isCompleted();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInMemory() {
/* 126 */     return this.fileUpload.isInMemory();
/*     */   }
/*     */ 
/*     */   
/*     */   public long length() {
/* 131 */     return this.fileUpload.length();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean renameTo(File dest) throws IOException {
/* 136 */     return this.fileUpload.renameTo(dest);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setCharset(Charset charset) {
/* 141 */     this.fileUpload.setCharset(charset);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(ByteBuf buffer) throws IOException {
/* 146 */     if (buffer.readableBytes() > this.limitSize && 
/* 147 */       this.fileUpload instanceof MemoryFileUpload) {
/* 148 */       FileUpload memoryUpload = this.fileUpload;
/*     */       
/* 150 */       this.fileUpload = new DiskFileUpload(memoryUpload.getName(), memoryUpload.getFilename(), memoryUpload.getContentType(), memoryUpload.getContentTransferEncoding(), memoryUpload.getCharset(), this.definedSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 157 */       memoryUpload.release();
/*     */     } 
/*     */     
/* 160 */     this.fileUpload.setContent(buffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(File file) throws IOException {
/* 165 */     if (file.length() > this.limitSize && 
/* 166 */       this.fileUpload instanceof MemoryFileUpload) {
/* 167 */       FileUpload memoryUpload = this.fileUpload;
/*     */ 
/*     */       
/* 170 */       this.fileUpload = new DiskFileUpload(memoryUpload.getName(), memoryUpload.getFilename(), memoryUpload.getContentType(), memoryUpload.getContentTransferEncoding(), memoryUpload.getCharset(), this.definedSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 177 */       memoryUpload.release();
/*     */     } 
/*     */     
/* 180 */     this.fileUpload.setContent(file);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContent(InputStream inputStream) throws IOException {
/* 185 */     if (this.fileUpload instanceof MemoryFileUpload) {
/* 186 */       FileUpload memoryUpload = this.fileUpload;
/*     */ 
/*     */       
/* 189 */       this.fileUpload = new DiskFileUpload(this.fileUpload.getName(), this.fileUpload.getFilename(), this.fileUpload.getContentType(), this.fileUpload.getContentTransferEncoding(), this.fileUpload.getCharset(), this.definedSize);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 196 */       memoryUpload.release();
/*     */     } 
/* 198 */     this.fileUpload.setContent(inputStream);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/* 203 */     this.fileUpload.setContentType(contentType);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/* 208 */     this.fileUpload.setContentTransferEncoding(contentTransferEncoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/* 213 */     this.fileUpload.setFilename(filename);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/* 218 */     return this.fileUpload.getHttpDataType();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getName() {
/* 223 */     return this.fileUpload.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/* 228 */     return this.fileUpload.compareTo(o);
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 233 */     return "Mixed: " + this.fileUpload.toString();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf getChunk(int length) throws IOException {
/* 238 */     return this.fileUpload.getChunk(length);
/*     */   }
/*     */ 
/*     */   
/*     */   public File getFile() throws IOException {
/* 243 */     return this.fileUpload.getFile();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/* 248 */     return this.fileUpload.copy();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/* 253 */     return this.fileUpload.duplicate();
/*     */   }
/*     */ 
/*     */   
/*     */   public ByteBuf content() {
/* 258 */     return this.fileUpload.content();
/*     */   }
/*     */ 
/*     */   
/*     */   public int refCnt() {
/* 263 */     return this.fileUpload.refCnt();
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain() {
/* 268 */     this.fileUpload.retain();
/* 269 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 274 */     this.fileUpload.retain(increment);
/* 275 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release() {
/* 280 */     return this.fileUpload.release();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean release(int decrement) {
/* 285 */     return this.fileUpload.release(decrement);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\MixedFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */