/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import java.io.File;
/*     */ import java.io.InputStream;
/*     */ import java.security.KeyStore;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.cert.X509Certificate;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.net.ssl.TrustManagerFactory;
/*     */ import javax.security.auth.x500.X500Principal;
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
/*     */ public final class JdkSslClientContext
/*     */   extends JdkSslContext
/*     */ {
/*     */   private final SSLContext ctx;
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   public JdkSslClientContext() throws SSLException {
/*  48 */     this(null, null, null, null, 0L, 0L);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkSslClientContext(File certChainFile) throws SSLException {
/*  58 */     this(certChainFile, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public JdkSslClientContext(TrustManagerFactory trustManagerFactory) throws SSLException {
/*  69 */     this(null, trustManagerFactory);
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
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory) throws SSLException {
/*  82 */     this(certChainFile, trustManagerFactory, null, null, 0L, 0L);
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
/*     */   
/*     */   public JdkSslClientContext(File certChainFile, TrustManagerFactory trustManagerFactory, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 107 */     super(ciphers);
/*     */     
/* 109 */     if (nextProtocols != null && nextProtocols.iterator().hasNext()) {
/* 110 */       if (!JettyNpnSslEngine.isAvailable()) {
/* 111 */         throw new SSLException("NPN/ALPN unsupported: " + nextProtocols);
/*     */       }
/*     */       
/* 114 */       List<String> nextProtoList = new ArrayList<String>();
/* 115 */       for (String p : nextProtocols) {
/* 116 */         if (p == null) {
/*     */           break;
/*     */         }
/* 119 */         nextProtoList.add(p);
/*     */       } 
/* 121 */       this.nextProtocols = Collections.unmodifiableList(nextProtoList);
/*     */     } else {
/* 123 */       this.nextProtocols = Collections.emptyList();
/*     */     } 
/*     */     
/*     */     try {
/* 127 */       if (certChainFile == null) {
/* 128 */         this.ctx = SSLContext.getInstance("TLS");
/* 129 */         if (trustManagerFactory == null) {
/* 130 */           this.ctx.init(null, null, null);
/*     */         } else {
/* 132 */           trustManagerFactory.init((KeyStore)null);
/* 133 */           this.ctx.init(null, trustManagerFactory.getTrustManagers(), null);
/*     */         } 
/*     */       } else {
/* 136 */         KeyStore ks = KeyStore.getInstance("JKS");
/* 137 */         ks.load(null, null);
/* 138 */         CertificateFactory cf = CertificateFactory.getInstance("X.509");
/*     */         
/* 140 */         ByteBuf[] certs = PemReader.readCertificates(certChainFile);
/*     */         try {
/* 142 */           for (ByteBuf buf : certs) {
/* 143 */             X509Certificate cert = (X509Certificate)cf.generateCertificate((InputStream)new ByteBufInputStream(buf));
/* 144 */             X500Principal principal = cert.getSubjectX500Principal();
/* 145 */             ks.setCertificateEntry(principal.getName("RFC2253"), cert);
/*     */           } 
/*     */         } finally {
/* 148 */           for (ByteBuf buf : certs) {
/* 149 */             buf.release();
/*     */           }
/*     */         } 
/*     */ 
/*     */         
/* 154 */         if (trustManagerFactory == null) {
/* 155 */           trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
/*     */         }
/* 157 */         trustManagerFactory.init(ks);
/*     */ 
/*     */         
/* 160 */         this.ctx = SSLContext.getInstance("TLS");
/* 161 */         this.ctx.init(null, trustManagerFactory.getTrustManagers(), null);
/*     */       } 
/*     */       
/* 164 */       SSLSessionContext sessCtx = this.ctx.getClientSessionContext();
/* 165 */       if (sessionCacheSize > 0L) {
/* 166 */         sessCtx.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
/*     */       }
/* 168 */       if (sessionTimeout > 0L) {
/* 169 */         sessCtx.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
/*     */       }
/* 171 */     } catch (Exception e) {
/* 172 */       throw new SSLException("failed to initialize the server-side SSL context", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClient() {
/* 178 */     return true;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> nextProtocols() {
/* 183 */     return this.nextProtocols;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLContext context() {
/* 188 */     return this.ctx;
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\JdkSslClientContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */