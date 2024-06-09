/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.IOException;
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
/*     */ 
/*     */ public class MemoryFileUpload
/*     */   extends AbstractMemoryHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   private String filename;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public MemoryFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/*  40 */     super(name, charset, size);
/*  41 */     setFilename(filename);
/*  42 */     setContentType(contentType);
/*  43 */     setContentTransferEncoding(contentTransferEncoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  48 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  53 */     return this.filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/*  58 */     if (filename == null) {
/*  59 */       throw new NullPointerException("filename");
/*     */     }
/*  61 */     this.filename = filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  66 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  71 */     if (!(o instanceof Attribute)) {
/*  72 */       return false;
/*     */     }
/*  74 */     Attribute attribute = (Attribute)o;
/*  75 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  80 */     if (!(o instanceof FileUpload)) {
/*  81 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
/*     */     }
/*     */     
/*  84 */     return compareTo((FileUpload)o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(FileUpload o) {
/*  89 */     int v = getName().compareToIgnoreCase(o.getName());
/*  90 */     if (v != 0) {
/*  91 */       return v;
/*     */     }
/*     */     
/*  94 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/*  99 */     if (contentType == null) {
/* 100 */       throw new NullPointerException("contentType");
/*     */     }
/* 102 */     this.contentType = contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 107 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 112 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/* 117 */     this.contentTransferEncoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 122 */     return "Content-Disposition: form-data; name=\"" + getName() + "\"; " + "filename" + "=\"" + this.filename + "\"\r\n" + "Content-Type" + ": " + this.contentType + ((this.charset != null) ? ("; charset=" + this.charset + "\r\n") : "\r\n") + "Content-Length" + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/* 134 */     MemoryFileUpload upload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 136 */     ByteBuf buf = content();
/* 137 */     if (buf != null) {
/*     */       try {
/* 139 */         upload.setContent(buf.copy());
/* 140 */         return upload;
/* 141 */       } catch (IOException e) {
/* 142 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 145 */     return upload;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/* 150 */     MemoryFileUpload upload = new MemoryFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 152 */     ByteBuf buf = content();
/* 153 */     if (buf != null) {
/*     */       try {
/* 155 */         upload.setContent(buf.duplicate());
/* 156 */         return upload;
/* 157 */       } catch (IOException e) {
/* 158 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 161 */     return upload;
/*     */   }
/*     */   
/*     */   public FileUpload retain() {
/* 165 */     super.retain();
/* 166 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 171 */     super.retain(increment);
/* 172 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\MemoryFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */