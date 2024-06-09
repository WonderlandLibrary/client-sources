/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufHolder;
/*     */ import io.netty.channel.ChannelException;
/*     */ import io.netty.util.ReferenceCounted;
/*     */ import java.io.File;
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
/*     */ 
/*     */ public class DiskFileUpload
/*     */   extends AbstractDiskHttpData
/*     */   implements FileUpload
/*     */ {
/*     */   public static String baseDirectory;
/*     */   public static boolean deleteOnExitTemporaryFile = true;
/*     */   public static final String prefix = "FUp_";
/*     */   public static final String postfix = ".tmp";
/*     */   private String filename;
/*     */   private String contentType;
/*     */   private String contentTransferEncoding;
/*     */   
/*     */   public DiskFileUpload(String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/*  46 */     super(name, charset, size);
/*  47 */     setFilename(filename);
/*  48 */     setContentType(contentType);
/*  49 */     setContentTransferEncoding(contentTransferEncoding);
/*     */   }
/*     */ 
/*     */   
/*     */   public InterfaceHttpData.HttpDataType getHttpDataType() {
/*  54 */     return InterfaceHttpData.HttpDataType.FileUpload;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getFilename() {
/*  59 */     return this.filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setFilename(String filename) {
/*  64 */     if (filename == null) {
/*  65 */       throw new NullPointerException("filename");
/*     */     }
/*  67 */     this.filename = filename;
/*     */   }
/*     */ 
/*     */   
/*     */   public int hashCode() {
/*  72 */     return getName().hashCode();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean equals(Object o) {
/*  77 */     if (!(o instanceof Attribute)) {
/*  78 */       return false;
/*     */     }
/*  80 */     Attribute attribute = (Attribute)o;
/*  81 */     return getName().equalsIgnoreCase(attribute.getName());
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(InterfaceHttpData o) {
/*  86 */     if (!(o instanceof FileUpload)) {
/*  87 */       throw new ClassCastException("Cannot compare " + getHttpDataType() + " with " + o.getHttpDataType());
/*     */     }
/*     */     
/*  90 */     return compareTo((FileUpload)o);
/*     */   }
/*     */ 
/*     */   
/*     */   public int compareTo(FileUpload o) {
/*  95 */     int v = getName().compareToIgnoreCase(o.getName());
/*  96 */     if (v != 0) {
/*  97 */       return v;
/*     */     }
/*     */     
/* 100 */     return v;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentType(String contentType) {
/* 105 */     if (contentType == null) {
/* 106 */       throw new NullPointerException("contentType");
/*     */     }
/* 108 */     this.contentType = contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentType() {
/* 113 */     return this.contentType;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getContentTransferEncoding() {
/* 118 */     return this.contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public void setContentTransferEncoding(String contentTransferEncoding) {
/* 123 */     this.contentTransferEncoding = contentTransferEncoding;
/*     */   }
/*     */ 
/*     */   
/*     */   public String toString() {
/* 128 */     return "Content-Disposition: form-data; name=\"" + getName() + "\"; " + "filename" + "=\"" + this.filename + "\"\r\n" + "Content-Type" + ": " + this.contentType + ((this.charset != null) ? ("; charset=" + this.charset + "\r\n") : "\r\n") + "Content-Length" + ": " + length() + "\r\n" + "Completed: " + isCompleted() + "\r\nIsInMemory: " + isInMemory() + "\r\nRealFile: " + ((this.file != null) ? this.file.getAbsolutePath() : "null") + " DefaultDeleteAfter: " + deleteOnExitTemporaryFile;
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
/*     */ 
/*     */   
/*     */   protected boolean deleteOnExit() {
/* 142 */     return deleteOnExitTemporaryFile;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getBaseDirectory() {
/* 147 */     return baseDirectory;
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getDiskFilename() {
/* 152 */     File file = new File(this.filename);
/* 153 */     return file.getName();
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPostfix() {
/* 158 */     return ".tmp";
/*     */   }
/*     */ 
/*     */   
/*     */   protected String getPrefix() {
/* 163 */     return "FUp_";
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload copy() {
/* 168 */     DiskFileUpload upload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 170 */     ByteBuf buf = content();
/* 171 */     if (buf != null) {
/*     */       try {
/* 173 */         upload.setContent(buf.copy());
/* 174 */       } catch (IOException e) {
/* 175 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 178 */     return upload;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload duplicate() {
/* 183 */     DiskFileUpload upload = new DiskFileUpload(getName(), getFilename(), getContentType(), getContentTransferEncoding(), getCharset(), this.size);
/*     */     
/* 185 */     ByteBuf buf = content();
/* 186 */     if (buf != null) {
/*     */       try {
/* 188 */         upload.setContent(buf.duplicate());
/* 189 */       } catch (IOException e) {
/* 190 */         throw new ChannelException(e);
/*     */       } 
/*     */     }
/* 193 */     return upload;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain(int increment) {
/* 198 */     super.retain(increment);
/* 199 */     return this;
/*     */   }
/*     */ 
/*     */   
/*     */   public FileUpload retain() {
/* 204 */     super.retain();
/* 205 */     return this;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\DiskFileUpload.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */