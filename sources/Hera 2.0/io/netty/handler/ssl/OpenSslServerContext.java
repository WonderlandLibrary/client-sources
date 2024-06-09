/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.io.File;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLException;
/*     */ import org.apache.tomcat.jni.Pool;
/*     */ import org.apache.tomcat.jni.SSL;
/*     */ import org.apache.tomcat.jni.SSLContext;
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
/*     */ public final class OpenSslServerContext
/*     */   extends SslContext
/*     */ {
/*  37 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(OpenSslServerContext.class);
/*     */   private static final List<String> DEFAULT_CIPHERS;
/*     */   
/*     */   static {
/*  41 */     List<String> ciphers = new ArrayList<String>();
/*     */     
/*  43 */     Collections.addAll(ciphers, new String[] { "ECDHE-RSA-AES128-GCM-SHA256", "ECDHE-RSA-RC4-SHA", "ECDHE-RSA-AES128-SHA", "ECDHE-RSA-AES256-SHA", "AES128-GCM-SHA256", "RC4-SHA", "RC4-MD5", "AES128-SHA", "AES256-SHA", "DES-CBC3-SHA" });
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
/*  55 */     DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
/*     */     
/*  57 */     if (logger.isDebugEnabled()) {
/*  58 */       logger.debug("Default cipher suite (OpenSSL): " + ciphers);
/*     */     }
/*     */   }
/*     */ 
/*     */   
/*     */   private final long aprPool;
/*  64 */   private final List<String> ciphers = new ArrayList<String>();
/*  65 */   private final List<String> unmodifiableCiphers = Collections.unmodifiableList(this.ciphers);
/*     */ 
/*     */   
/*     */   private final long sessionCacheSize;
/*     */ 
/*     */   
/*     */   private final long sessionTimeout;
/*     */   
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   private final long ctx;
/*     */   
/*     */   private final OpenSslSessionStats stats;
/*     */ 
/*     */   
/*     */   public OpenSslServerContext(File certChainFile, File keyFile) throws SSLException {
/*  81 */     this(certChainFile, keyFile, null);
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
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
/*  93 */     this(certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
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
/*     */   public OpenSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 117 */     OpenSsl.ensureAvailability();
/*     */     
/* 119 */     if (certChainFile == null) {
/* 120 */       throw new NullPointerException("certChainFile");
/*     */     }
/* 122 */     if (!certChainFile.isFile()) {
/* 123 */       throw new IllegalArgumentException("certChainFile is not a file: " + certChainFile);
/*     */     }
/* 125 */     if (keyFile == null) {
/* 126 */       throw new NullPointerException("keyPath");
/*     */     }
/* 128 */     if (!keyFile.isFile()) {
/* 129 */       throw new IllegalArgumentException("keyPath is not a file: " + keyFile);
/*     */     }
/* 131 */     if (ciphers == null) {
/* 132 */       ciphers = DEFAULT_CIPHERS;
/*     */     }
/*     */     
/* 135 */     if (keyPassword == null) {
/* 136 */       keyPassword = "";
/*     */     }
/* 138 */     if (nextProtocols == null) {
/* 139 */       nextProtocols = Collections.emptyList();
/*     */     }
/*     */     
/* 142 */     for (String c : ciphers) {
/* 143 */       if (c == null) {
/*     */         break;
/*     */       }
/* 146 */       this.ciphers.add(c);
/*     */     } 
/*     */     
/* 149 */     List<String> nextProtoList = new ArrayList<String>();
/* 150 */     for (String p : nextProtocols) {
/* 151 */       if (p == null) {
/*     */         break;
/*     */       }
/* 154 */       nextProtoList.add(p);
/*     */     } 
/* 156 */     this.nextProtocols = Collections.unmodifiableList(nextProtoList);
/*     */ 
/*     */     
/* 159 */     this.aprPool = Pool.create(0L);
/*     */ 
/*     */     
/* 162 */     boolean success = false;
/*     */     try {
/* 164 */       synchronized (OpenSslServerContext.class) {
/*     */         try {
/* 166 */           this.ctx = SSLContext.make(this.aprPool, 6, 1);
/* 167 */         } catch (Exception e) {
/* 168 */           throw new SSLException("failed to create an SSL_CTX", e);
/*     */         } 
/*     */         
/* 171 */         SSLContext.setOptions(this.ctx, 4095);
/* 172 */         SSLContext.setOptions(this.ctx, 16777216);
/* 173 */         SSLContext.setOptions(this.ctx, 4194304);
/* 174 */         SSLContext.setOptions(this.ctx, 524288);
/* 175 */         SSLContext.setOptions(this.ctx, 1048576);
/* 176 */         SSLContext.setOptions(this.ctx, 65536);
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 181 */           StringBuilder cipherBuf = new StringBuilder();
/* 182 */           for (String c : this.ciphers) {
/* 183 */             cipherBuf.append(c);
/* 184 */             cipherBuf.append(':');
/*     */           } 
/* 186 */           cipherBuf.setLength(cipherBuf.length() - 1);
/*     */           
/* 188 */           SSLContext.setCipherSuite(this.ctx, cipherBuf.toString());
/* 189 */         } catch (SSLException e) {
/* 190 */           throw e;
/* 191 */         } catch (Exception e) {
/* 192 */           throw new SSLException("failed to set cipher suite: " + this.ciphers, e);
/*     */         } 
/*     */ 
/*     */         
/* 196 */         SSLContext.setVerify(this.ctx, 0, 10);
/*     */ 
/*     */         
/*     */         try {
/* 200 */           if (!SSLContext.setCertificate(this.ctx, certChainFile.getPath(), keyFile.getPath(), keyPassword, 0))
/*     */           {
/* 202 */             throw new SSLException("failed to set certificate: " + certChainFile + " and " + keyFile + " (" + SSL.getLastError() + ')');
/*     */           }
/*     */         }
/* 205 */         catch (SSLException e) {
/* 206 */           throw e;
/* 207 */         } catch (Exception e) {
/* 208 */           throw new SSLException("failed to set certificate: " + certChainFile + " and " + keyFile, e);
/*     */         } 
/*     */ 
/*     */         
/* 212 */         if (!SSLContext.setCertificateChainFile(this.ctx, certChainFile.getPath(), true)) {
/* 213 */           String error = SSL.getLastError();
/* 214 */           if (!error.startsWith("error:00000000:")) {
/* 215 */             throw new SSLException("failed to set certificate chain: " + certChainFile + " (" + SSL.getLastError() + ')');
/*     */           }
/*     */         } 
/*     */ 
/*     */ 
/*     */         
/* 221 */         if (!nextProtoList.isEmpty()) {
/*     */           
/* 223 */           StringBuilder nextProtocolBuf = new StringBuilder();
/* 224 */           for (String p : nextProtoList) {
/* 225 */             nextProtocolBuf.append(p);
/* 226 */             nextProtocolBuf.append(',');
/*     */           } 
/* 228 */           nextProtocolBuf.setLength(nextProtocolBuf.length() - 1);
/*     */           
/* 230 */           SSLContext.setNextProtos(this.ctx, nextProtocolBuf.toString());
/*     */         } 
/*     */ 
/*     */         
/* 234 */         if (sessionCacheSize > 0L) {
/* 235 */           this.sessionCacheSize = sessionCacheSize;
/* 236 */           SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
/*     */         } else {
/*     */           
/* 239 */           this.sessionCacheSize = sessionCacheSize = SSLContext.setSessionCacheSize(this.ctx, 20480L);
/*     */           
/* 241 */           SSLContext.setSessionCacheSize(this.ctx, sessionCacheSize);
/*     */         } 
/*     */ 
/*     */         
/* 245 */         if (sessionTimeout > 0L) {
/* 246 */           this.sessionTimeout = sessionTimeout;
/* 247 */           SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
/*     */         } else {
/*     */           
/* 250 */           this.sessionTimeout = sessionTimeout = SSLContext.setSessionCacheTimeout(this.ctx, 300L);
/*     */           
/* 252 */           SSLContext.setSessionCacheTimeout(this.ctx, sessionTimeout);
/*     */         } 
/*     */       } 
/* 255 */       success = true;
/*     */     } finally {
/* 257 */       if (!success) {
/* 258 */         destroyPools();
/*     */       }
/*     */     } 
/*     */     
/* 262 */     this.stats = new OpenSslSessionStats(this.ctx);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClient() {
/* 267 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> cipherSuites() {
/* 272 */     return this.unmodifiableCiphers;
/*     */   }
/*     */ 
/*     */   
/*     */   public long sessionCacheSize() {
/* 277 */     return this.sessionCacheSize;
/*     */   }
/*     */ 
/*     */   
/*     */   public long sessionTimeout() {
/* 282 */     return this.sessionTimeout;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> nextProtocols() {
/* 287 */     return this.nextProtocols;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public long context() {
/* 294 */     return this.ctx;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public OpenSslSessionStats stats() {
/* 301 */     return this.stats;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLEngine newEngine(ByteBufAllocator alloc) {
/* 309 */     if (this.nextProtocols.isEmpty()) {
/* 310 */       return new OpenSslEngine(this.ctx, alloc, null);
/*     */     }
/* 312 */     return new OpenSslEngine(this.ctx, alloc, this.nextProtocols.get(this.nextProtocols.size() - 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
/* 318 */     throw new UnsupportedOperationException();
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public void setTicketKeys(byte[] keys) {
/* 325 */     if (keys == null) {
/* 326 */       throw new NullPointerException("keys");
/*     */     }
/* 328 */     SSLContext.setSessionTicketKeys(this.ctx, keys);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   protected void finalize() throws Throwable {
/* 334 */     super.finalize();
/* 335 */     synchronized (OpenSslServerContext.class) {
/* 336 */       if (this.ctx != 0L) {
/* 337 */         SSLContext.free(this.ctx);
/*     */       }
/*     */     } 
/*     */     
/* 341 */     destroyPools();
/*     */   }
/*     */   
/*     */   private void destroyPools() {
/* 345 */     if (this.aprPool != 0L)
/* 346 */       Pool.destroy(this.aprPool); 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\OpenSslServerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */