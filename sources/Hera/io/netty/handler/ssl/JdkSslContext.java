/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBufAllocator;
/*     */ import io.netty.util.internal.logging.InternalLogger;
/*     */ import io.netty.util.internal.logging.InternalLoggerFactory;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Arrays;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLSessionContext;
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
/*     */ public abstract class JdkSslContext
/*     */   extends SslContext
/*     */ {
/*  36 */   private static final InternalLogger logger = InternalLoggerFactory.getInstance(JdkSslContext.class);
/*     */   
/*     */   static final String PROTOCOL = "TLS";
/*     */   
/*     */   static final String[] PROTOCOLS;
/*     */   static final List<String> DEFAULT_CIPHERS;
/*     */   
/*     */   static {
/*     */     try {
/*  45 */       context = SSLContext.getInstance("TLS");
/*  46 */       context.init(null, null, null);
/*  47 */     } catch (Exception e) {
/*  48 */       throw new Error("failed to initialize the default SSL context", e);
/*     */     } 
/*     */     
/*  51 */     SSLEngine engine = context.createSSLEngine();
/*     */ 
/*     */     
/*  54 */     String[] supportedProtocols = engine.getSupportedProtocols();
/*  55 */     List<String> protocols = new ArrayList<String>();
/*  56 */     addIfSupported(supportedProtocols, protocols, new String[] { "TLSv1.2", "TLSv1.1", "TLSv1", "SSLv3" });
/*     */ 
/*     */ 
/*     */     
/*  60 */     if (!protocols.isEmpty()) {
/*  61 */       PROTOCOLS = protocols.<String>toArray(new String[protocols.size()]);
/*     */     } else {
/*  63 */       PROTOCOLS = engine.getEnabledProtocols();
/*     */     } 
/*     */ 
/*     */     
/*  67 */     String[] supportedCiphers = engine.getSupportedCipherSuites();
/*  68 */     List<String> ciphers = new ArrayList<String>();
/*  69 */     addIfSupported(supportedCiphers, ciphers, new String[] { "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", "TLS_ECDHE_RSA_WITH_RC4_128_SHA", "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", "TLS_RSA_WITH_AES_128_GCM_SHA256", "SSL_RSA_WITH_RC4_128_SHA", "SSL_RSA_WITH_RC4_128_MD5", "TLS_RSA_WITH_AES_128_CBC_SHA", "TLS_RSA_WITH_AES_256_CBC_SHA", "SSL_RSA_WITH_DES_CBC_SHA" });
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
/*  87 */     if (!ciphers.isEmpty()) {
/*  88 */       DEFAULT_CIPHERS = Collections.unmodifiableList(ciphers);
/*     */     } else {
/*     */       
/*  91 */       DEFAULT_CIPHERS = Collections.unmodifiableList(Arrays.asList(engine.getEnabledCipherSuites()));
/*     */     } 
/*     */     
/*  94 */     if (logger.isDebugEnabled()) {
/*  95 */       logger.debug("Default protocols (JDK): {} ", Arrays.asList(PROTOCOLS));
/*  96 */       logger.debug("Default cipher suites (JDK): {}", DEFAULT_CIPHERS);
/*     */     } 
/*     */   } private final String[] cipherSuites; private final List<String> unmodifiableCipherSuites; static {
/*     */     SSLContext context;
/*     */   } private static void addIfSupported(String[] supported, List<String> enabled, String... names) {
/* 101 */     for (String n : names) {
/* 102 */       for (String s : supported) {
/* 103 */         if (n.equals(s)) {
/* 104 */           enabled.add(s);
/*     */           break;
/*     */         } 
/*     */       } 
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JdkSslContext(Iterable<String> ciphers) {
/* 115 */     this.cipherSuites = toCipherSuiteArray(ciphers);
/* 116 */     this.unmodifiableCipherSuites = Collections.unmodifiableList(Arrays.asList(this.cipherSuites));
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
/*     */   public final SSLSessionContext sessionContext() {
/* 128 */     if (isServer()) {
/* 129 */       return context().getServerSessionContext();
/*     */     }
/* 131 */     return context().getClientSessionContext();
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public final List<String> cipherSuites() {
/* 137 */     return this.unmodifiableCipherSuites;
/*     */   }
/*     */ 
/*     */   
/*     */   public final long sessionCacheSize() {
/* 142 */     return sessionContext().getSessionCacheSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public final long sessionTimeout() {
/* 147 */     return sessionContext().getSessionTimeout();
/*     */   }
/*     */ 
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator alloc) {
/* 152 */     SSLEngine engine = context().createSSLEngine();
/* 153 */     engine.setEnabledCipherSuites(this.cipherSuites);
/* 154 */     engine.setEnabledProtocols(PROTOCOLS);
/* 155 */     engine.setUseClientMode(isClient());
/* 156 */     return wrapEngine(engine);
/*     */   }
/*     */ 
/*     */   
/*     */   public final SSLEngine newEngine(ByteBufAllocator alloc, String peerHost, int peerPort) {
/* 161 */     SSLEngine engine = context().createSSLEngine(peerHost, peerPort);
/* 162 */     engine.setEnabledCipherSuites(this.cipherSuites);
/* 163 */     engine.setEnabledProtocols(PROTOCOLS);
/* 164 */     engine.setUseClientMode(isClient());
/* 165 */     return wrapEngine(engine);
/*     */   }
/*     */   
/*     */   private SSLEngine wrapEngine(SSLEngine engine) {
/* 169 */     if (nextProtocols().isEmpty()) {
/* 170 */       return engine;
/*     */     }
/* 172 */     return new JettyNpnSslEngine(engine, nextProtocols(), isServer());
/*     */   }
/*     */ 
/*     */   
/*     */   private static String[] toCipherSuiteArray(Iterable<String> ciphers) {
/* 177 */     if (ciphers == null) {
/* 178 */       return DEFAULT_CIPHERS.<String>toArray(new String[DEFAULT_CIPHERS.size()]);
/*     */     }
/* 180 */     List<String> newCiphers = new ArrayList<String>();
/* 181 */     for (String c : ciphers) {
/* 182 */       if (c == null) {
/*     */         break;
/*     */       }
/* 185 */       newCiphers.add(c);
/*     */     } 
/* 187 */     return newCiphers.<String>toArray(new String[newCiphers.size()]);
/*     */   }
/*     */   
/*     */   public abstract SSLContext context();
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\JdkSslContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */