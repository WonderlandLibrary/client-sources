/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.security.Principal;
/*     */ import java.security.cert.Certificate;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLPeerUnverifiedException;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import javax.net.ssl.SSLSessionContext;
/*     */ import javax.security.cert.X509Certificate;
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
/*     */ final class JettyNpnSslSession
/*     */   implements SSLSession
/*     */ {
/*     */   private final SSLEngine engine;
/*     */   private volatile String applicationProtocol;
/*     */   
/*     */   JettyNpnSslSession(SSLEngine engine) {
/*  33 */     this.engine = engine;
/*     */   }
/*     */   
/*     */   void setApplicationProtocol(String applicationProtocol) {
/*  37 */     if (applicationProtocol != null) {
/*  38 */       applicationProtocol = applicationProtocol.replace(':', '_');
/*     */     }
/*  40 */     this.applicationProtocol = applicationProtocol;
/*     */   }
/*     */ 
/*     */   
/*     */   public String getProtocol() {
/*  45 */     String protocol = unwrap().getProtocol();
/*  46 */     String applicationProtocol = this.applicationProtocol;
/*     */     
/*  48 */     if (applicationProtocol == null) {
/*  49 */       if (protocol != null) {
/*  50 */         return protocol.replace(':', '_');
/*     */       }
/*  52 */       return null;
/*     */     } 
/*     */ 
/*     */     
/*  56 */     StringBuilder buf = new StringBuilder(32);
/*  57 */     if (protocol != null) {
/*  58 */       buf.append(protocol.replace(':', '_'));
/*  59 */       buf.append(':');
/*     */     } else {
/*  61 */       buf.append("null:");
/*     */     } 
/*  63 */     buf.append(applicationProtocol);
/*  64 */     return buf.toString();
/*     */   }
/*     */   
/*     */   private SSLSession unwrap() {
/*  68 */     return this.engine.getSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public byte[] getId() {
/*  73 */     return unwrap().getId();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSessionContext getSessionContext() {
/*  78 */     return unwrap().getSessionContext();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getCreationTime() {
/*  83 */     return unwrap().getCreationTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public long getLastAccessedTime() {
/*  88 */     return unwrap().getLastAccessedTime();
/*     */   }
/*     */ 
/*     */   
/*     */   public void invalidate() {
/*  93 */     unwrap().invalidate();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isValid() {
/*  98 */     return unwrap().isValid();
/*     */   }
/*     */ 
/*     */   
/*     */   public void putValue(String s, Object o) {
/* 103 */     unwrap().putValue(s, o);
/*     */   }
/*     */ 
/*     */   
/*     */   public Object getValue(String s) {
/* 108 */     return unwrap().getValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public void removeValue(String s) {
/* 113 */     unwrap().removeValue(s);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getValueNames() {
/* 118 */     return unwrap().getValueNames();
/*     */   }
/*     */ 
/*     */   
/*     */   public Certificate[] getPeerCertificates() throws SSLPeerUnverifiedException {
/* 123 */     return unwrap().getPeerCertificates();
/*     */   }
/*     */ 
/*     */   
/*     */   public Certificate[] getLocalCertificates() {
/* 128 */     return unwrap().getLocalCertificates();
/*     */   }
/*     */ 
/*     */   
/*     */   public X509Certificate[] getPeerCertificateChain() throws SSLPeerUnverifiedException {
/* 133 */     return unwrap().getPeerCertificateChain();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getPeerPrincipal() throws SSLPeerUnverifiedException {
/* 138 */     return unwrap().getPeerPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public Principal getLocalPrincipal() {
/* 143 */     return unwrap().getLocalPrincipal();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getCipherSuite() {
/* 148 */     return unwrap().getCipherSuite();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPeerHost() {
/* 153 */     return unwrap().getPeerHost();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPeerPort() {
/* 158 */     return unwrap().getPeerPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPacketBufferSize() {
/* 163 */     return unwrap().getPacketBufferSize();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getApplicationBufferSize() {
/* 168 */     return unwrap().getApplicationBufferSize();
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\JettyNpnSslSession.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */