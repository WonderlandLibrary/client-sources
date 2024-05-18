/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import java.nio.ByteBuffer;
/*     */ import java.util.List;
/*     */ import javax.net.ssl.SSLEngine;
/*     */ import javax.net.ssl.SSLEngineResult;
/*     */ import javax.net.ssl.SSLException;
/*     */ import javax.net.ssl.SSLParameters;
/*     */ import javax.net.ssl.SSLSession;
/*     */ import org.eclipse.jetty.npn.NextProtoNego;
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
/*     */ final class JettyNpnSslEngine
/*     */   extends SSLEngine
/*     */ {
/*     */   private static boolean available;
/*     */   private final SSLEngine engine;
/*     */   private final JettyNpnSslSession session;
/*     */   
/*     */   static boolean isAvailable() {
/*  37 */     updateAvailability();
/*  38 */     return available;
/*     */   }
/*     */   
/*     */   private static void updateAvailability() {
/*  42 */     if (available) {
/*     */       return;
/*     */     }
/*     */     
/*     */     try {
/*  47 */       ClassLoader bootloader = ClassLoader.getSystemClassLoader().getParent();
/*  48 */       if (bootloader == null)
/*     */       {
/*     */         
/*  51 */         bootloader = ClassLoader.getSystemClassLoader();
/*     */       }
/*  53 */       Class.forName("sun.security.ssl.NextProtoNegoExtension", true, bootloader);
/*  54 */       available = true;
/*  55 */     } catch (Exception ignore) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   JettyNpnSslEngine(SSLEngine engine, final List<String> nextProtocols, boolean server) {
/*  64 */     assert !nextProtocols.isEmpty();
/*     */     
/*  66 */     this.engine = engine;
/*  67 */     this.session = new JettyNpnSslSession(engine);
/*     */     
/*  69 */     if (server) {
/*  70 */       NextProtoNego.put(engine, (NextProtoNego.Provider)new NextProtoNego.ServerProvider()
/*     */           {
/*     */             public void unsupported() {
/*  73 */               JettyNpnSslEngine.this.getSession().setApplicationProtocol(nextProtocols.get(nextProtocols.size() - 1));
/*     */             }
/*     */ 
/*     */             
/*     */             public List<String> protocols() {
/*  78 */               return nextProtocols;
/*     */             }
/*     */ 
/*     */             
/*     */             public void protocolSelected(String protocol) {
/*  83 */               JettyNpnSslEngine.this.getSession().setApplicationProtocol(protocol);
/*     */             }
/*     */           });
/*     */     } else {
/*  87 */       final String[] list = nextProtocols.<String>toArray(new String[nextProtocols.size()]);
/*  88 */       final String fallback = list[list.length - 1];
/*     */       
/*  90 */       NextProtoNego.put(engine, (NextProtoNego.Provider)new NextProtoNego.ClientProvider()
/*     */           {
/*     */             public boolean supports() {
/*  93 */               return true;
/*     */             }
/*     */ 
/*     */             
/*     */             public void unsupported() {
/*  98 */               JettyNpnSslEngine.this.session.setApplicationProtocol(null);
/*     */             }
/*     */ 
/*     */             
/*     */             public String selectProtocol(List<String> protocols) {
/* 103 */               for (String p : list) {
/* 104 */                 if (protocols.contains(p)) {
/* 105 */                   return p;
/*     */                 }
/*     */               } 
/* 108 */               return fallback;
/*     */             }
/*     */           });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public JettyNpnSslSession getSession() {
/* 116 */     return this.session;
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeInbound() throws SSLException {
/* 121 */     NextProtoNego.remove(this.engine);
/* 122 */     this.engine.closeInbound();
/*     */   }
/*     */ 
/*     */   
/*     */   public void closeOutbound() {
/* 127 */     NextProtoNego.remove(this.engine);
/* 128 */     this.engine.closeOutbound();
/*     */   }
/*     */ 
/*     */   
/*     */   public String getPeerHost() {
/* 133 */     return this.engine.getPeerHost();
/*     */   }
/*     */ 
/*     */   
/*     */   public int getPeerPort() {
/* 138 */     return this.engine.getPeerPort();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
/* 143 */     return this.engine.wrap(byteBuffer, byteBuffer2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] byteBuffers, ByteBuffer byteBuffer) throws SSLException {
/* 148 */     return this.engine.wrap(byteBuffers, byteBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult wrap(ByteBuffer[] byteBuffers, int i, int i2, ByteBuffer byteBuffer) throws SSLException {
/* 153 */     return this.engine.wrap(byteBuffers, i, i2, byteBuffer);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer byteBuffer2) throws SSLException {
/* 158 */     return this.engine.unwrap(byteBuffer, byteBuffer2);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBuffers) throws SSLException {
/* 163 */     return this.engine.unwrap(byteBuffer, byteBuffers);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult unwrap(ByteBuffer byteBuffer, ByteBuffer[] byteBuffers, int i, int i2) throws SSLException {
/* 168 */     return this.engine.unwrap(byteBuffer, byteBuffers, i, i2);
/*     */   }
/*     */ 
/*     */   
/*     */   public Runnable getDelegatedTask() {
/* 173 */     return this.engine.getDelegatedTask();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isInboundDone() {
/* 178 */     return this.engine.isInboundDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isOutboundDone() {
/* 183 */     return this.engine.isOutboundDone();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedCipherSuites() {
/* 188 */     return this.engine.getSupportedCipherSuites();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledCipherSuites() {
/* 193 */     return this.engine.getEnabledCipherSuites();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledCipherSuites(String[] strings) {
/* 198 */     this.engine.setEnabledCipherSuites(strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getSupportedProtocols() {
/* 203 */     return this.engine.getSupportedProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   public String[] getEnabledProtocols() {
/* 208 */     return this.engine.getEnabledProtocols();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnabledProtocols(String[] strings) {
/* 213 */     this.engine.setEnabledProtocols(strings);
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLSession getHandshakeSession() {
/* 218 */     return this.engine.getHandshakeSession();
/*     */   }
/*     */ 
/*     */   
/*     */   public void beginHandshake() throws SSLException {
/* 223 */     this.engine.beginHandshake();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLEngineResult.HandshakeStatus getHandshakeStatus() {
/* 228 */     return this.engine.getHandshakeStatus();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setUseClientMode(boolean b) {
/* 233 */     this.engine.setUseClientMode(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getUseClientMode() {
/* 238 */     return this.engine.getUseClientMode();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setNeedClientAuth(boolean b) {
/* 243 */     this.engine.setNeedClientAuth(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getNeedClientAuth() {
/* 248 */     return this.engine.getNeedClientAuth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setWantClientAuth(boolean b) {
/* 253 */     this.engine.setWantClientAuth(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getWantClientAuth() {
/* 258 */     return this.engine.getWantClientAuth();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setEnableSessionCreation(boolean b) {
/* 263 */     this.engine.setEnableSessionCreation(b);
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean getEnableSessionCreation() {
/* 268 */     return this.engine.getEnableSessionCreation();
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLParameters getSSLParameters() {
/* 273 */     return this.engine.getSSLParameters();
/*     */   }
/*     */ 
/*     */   
/*     */   public void setSSLParameters(SSLParameters sslParameters) {
/* 278 */     this.engine.setSSLParameters(sslParameters);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\JettyNpnSslEngine.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */