/*     */ package io.netty.handler.codec.http.multipart;
/*     */ 
/*     */ import io.netty.handler.codec.http.HttpRequest;
/*     */ import io.netty.util.internal.PlatformDependent;
/*     */ import java.io.IOException;
/*     */ import java.nio.charset.Charset;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Iterator;
/*     */ import java.util.List;
/*     */ import java.util.Map;
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
/*     */ public class DefaultHttpDataFactory
/*     */   implements HttpDataFactory
/*     */ {
/*     */   public static final long MINSIZE = 16384L;
/*     */   private final boolean useDisk;
/*     */   private final boolean checkSize;
/*     */   private long minSize;
/*  52 */   private final Map<HttpRequest, List<HttpData>> requestFileDeleteMap = PlatformDependent.newConcurrentHashMap();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory() {
/*  59 */     this.useDisk = false;
/*  60 */     this.checkSize = true;
/*  61 */     this.minSize = 16384L;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(boolean useDisk) {
/*  68 */     this.useDisk = useDisk;
/*  69 */     this.checkSize = false;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public DefaultHttpDataFactory(long minSize) {
/*  77 */     this.useDisk = false;
/*  78 */     this.checkSize = true;
/*  79 */     this.minSize = minSize;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private List<HttpData> getList(HttpRequest request) {
/*  86 */     List<HttpData> list = this.requestFileDeleteMap.get(request);
/*  87 */     if (list == null) {
/*  88 */       list = new ArrayList<HttpData>();
/*  89 */       this.requestFileDeleteMap.put(request, list);
/*     */     } 
/*  91 */     return list;
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name) {
/*  96 */     if (this.useDisk) {
/*  97 */       Attribute attribute = new DiskAttribute(name);
/*  98 */       List<HttpData> fileToDelete = getList(request);
/*  99 */       fileToDelete.add(attribute);
/* 100 */       return attribute;
/*     */     } 
/* 102 */     if (this.checkSize) {
/* 103 */       Attribute attribute = new MixedAttribute(name, this.minSize);
/* 104 */       List<HttpData> fileToDelete = getList(request);
/* 105 */       fileToDelete.add(attribute);
/* 106 */       return attribute;
/*     */     } 
/* 108 */     return new MemoryAttribute(name);
/*     */   }
/*     */ 
/*     */   
/*     */   public Attribute createAttribute(HttpRequest request, String name, String value) {
/* 113 */     if (this.useDisk) {
/*     */       Attribute attribute;
/*     */       try {
/* 116 */         attribute = new DiskAttribute(name, value);
/* 117 */       } catch (IOException e) {
/*     */         
/* 119 */         attribute = new MixedAttribute(name, value, this.minSize);
/*     */       } 
/* 121 */       List<HttpData> fileToDelete = getList(request);
/* 122 */       fileToDelete.add(attribute);
/* 123 */       return attribute;
/*     */     } 
/* 125 */     if (this.checkSize) {
/* 126 */       Attribute attribute = new MixedAttribute(name, value, this.minSize);
/* 127 */       List<HttpData> fileToDelete = getList(request);
/* 128 */       fileToDelete.add(attribute);
/* 129 */       return attribute;
/*     */     } 
/*     */     try {
/* 132 */       return new MemoryAttribute(name, value);
/* 133 */     } catch (IOException e) {
/* 134 */       throw new IllegalArgumentException(e);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public FileUpload createFileUpload(HttpRequest request, String name, String filename, String contentType, String contentTransferEncoding, Charset charset, long size) {
/* 142 */     if (this.useDisk) {
/* 143 */       FileUpload fileUpload = new DiskFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */       
/* 145 */       List<HttpData> fileToDelete = getList(request);
/* 146 */       fileToDelete.add(fileUpload);
/* 147 */       return fileUpload;
/*     */     } 
/* 149 */     if (this.checkSize) {
/* 150 */       FileUpload fileUpload = new MixedFileUpload(name, filename, contentType, contentTransferEncoding, charset, size, this.minSize);
/*     */       
/* 152 */       List<HttpData> fileToDelete = getList(request);
/* 153 */       fileToDelete.add(fileUpload);
/* 154 */       return fileUpload;
/*     */     } 
/* 156 */     return new MemoryFileUpload(name, filename, contentType, contentTransferEncoding, charset, size);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public void removeHttpDataFromClean(HttpRequest request, InterfaceHttpData data) {
/* 162 */     if (data instanceof HttpData) {
/* 163 */       List<HttpData> fileToDelete = getList(request);
/* 164 */       fileToDelete.remove(data);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanRequestHttpDatas(HttpRequest request) {
/* 170 */     List<HttpData> fileToDelete = this.requestFileDeleteMap.remove(request);
/* 171 */     if (fileToDelete != null) {
/* 172 */       for (HttpData data : fileToDelete) {
/* 173 */         data.delete();
/*     */       }
/* 175 */       fileToDelete.clear();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void cleanAllHttpDatas() {
/* 181 */     Iterator<Map.Entry<HttpRequest, List<HttpData>>> i = this.requestFileDeleteMap.entrySet().iterator();
/* 182 */     while (i.hasNext()) {
/* 183 */       Map.Entry<HttpRequest, List<HttpData>> e = i.next();
/* 184 */       i.remove();
/*     */       
/* 186 */       List<HttpData> fileToDelete = e.getValue();
/* 187 */       if (fileToDelete != null) {
/* 188 */         for (HttpData data : fileToDelete) {
/* 189 */           data.delete();
/*     */         }
/* 191 */         fileToDelete.clear();
/*     */       } 
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\codec\http\multipart\DefaultHttpDataFactory.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */