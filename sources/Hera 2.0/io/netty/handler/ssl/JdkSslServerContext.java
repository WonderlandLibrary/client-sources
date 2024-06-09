/*     */ package io.netty.handler.ssl;
/*     */ 
/*     */ import io.netty.buffer.ByteBuf;
/*     */ import io.netty.buffer.ByteBufInputStream;
/*     */ import java.io.File;
/*     */ import java.io.IOException;
/*     */ import java.io.InputStream;
/*     */ import java.security.InvalidAlgorithmParameterException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyStore;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.Security;
/*     */ import java.security.cert.Certificate;
/*     */ import java.security.cert.CertificateFactory;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.PKCS8EncodedKeySpec;
/*     */ import java.util.ArrayList;
/*     */ import java.util.Collections;
/*     */ import java.util.List;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.EncryptedPrivateKeyInfo;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.SecretKeyFactory;
/*     */ import javax.crypto.spec.PBEKeySpec;
/*     */ import javax.net.ssl.KeyManagerFactory;
/*     */ import javax.net.ssl.SSLContext;
/*     */ import javax.net.ssl.SSLException;
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public final class JdkSslServerContext
/*     */   extends JdkSslContext
/*     */ {
/*     */   private final SSLContext ctx;
/*     */   private final List<String> nextProtocols;
/*     */   
/*     */   public JdkSslServerContext(File certChainFile, File keyFile) throws SSLException {
/*  64 */     this(certChainFile, keyFile, null);
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
/*     */   public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword) throws SSLException {
/*  76 */     this(certChainFile, keyFile, keyPassword, null, null, 0L, 0L);
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
/*     */   public JdkSslServerContext(File certChainFile, File keyFile, String keyPassword, Iterable<String> ciphers, Iterable<String> nextProtocols, long sessionCacheSize, long sessionTimeout) throws SSLException {
/* 100 */     super(ciphers);
/*     */     
/* 102 */     if (certChainFile == null) {
/* 103 */       throw new NullPointerException("certChainFile");
/*     */     }
/* 105 */     if (keyFile == null) {
/* 106 */       throw new NullPointerException("keyFile");
/*     */     }
/*     */     
/* 109 */     if (keyPassword == null) {
/* 110 */       keyPassword = "";
/*     */     }
/*     */     
/* 113 */     if (nextProtocols != null && nextProtocols.iterator().hasNext()) {
/* 114 */       if (!JettyNpnSslEngine.isAvailable()) {
/* 115 */         throw new SSLException("NPN/ALPN unsupported: " + nextProtocols);
/*     */       }
/*     */       
/* 118 */       List<String> list = new ArrayList<String>();
/* 119 */       for (String p : nextProtocols) {
/* 120 */         if (p == null) {
/*     */           break;
/*     */         }
/* 123 */         list.add(p);
/*     */       } 
/*     */       
/* 126 */       this.nextProtocols = Collections.unmodifiableList(list);
/*     */     } else {
/* 128 */       this.nextProtocols = Collections.emptyList();
/*     */     } 
/*     */     
/* 131 */     String algorithm = Security.getProperty("ssl.KeyManagerFactory.algorithm");
/* 132 */     if (algorithm == null) {
/* 133 */       algorithm = "SunX509";
/*     */     }
/*     */     try {
/*     */       PrivateKey privateKey;
/* 137 */       KeyStore ks = KeyStore.getInstance("JKS");
/* 138 */       ks.load(null, null);
/* 139 */       CertificateFactory cf = CertificateFactory.getInstance("X.509");
/* 140 */       KeyFactory rsaKF = KeyFactory.getInstance("RSA");
/* 141 */       KeyFactory dsaKF = KeyFactory.getInstance("DSA");
/*     */       
/* 143 */       ByteBuf encodedKeyBuf = PemReader.readPrivateKey(keyFile);
/* 144 */       byte[] encodedKey = new byte[encodedKeyBuf.readableBytes()];
/* 145 */       encodedKeyBuf.readBytes(encodedKey).release();
/*     */       
/* 147 */       char[] keyPasswordChars = keyPassword.toCharArray();
/* 148 */       PKCS8EncodedKeySpec encodedKeySpec = generateKeySpec(keyPasswordChars, encodedKey);
/*     */ 
/*     */       
/*     */       try {
/* 152 */         privateKey = rsaKF.generatePrivate(encodedKeySpec);
/* 153 */       } catch (InvalidKeySpecException ignore) {
/* 154 */         privateKey = dsaKF.generatePrivate(encodedKeySpec);
/*     */       } 
/*     */       
/* 157 */       List<Certificate> certChain = new ArrayList<Certificate>();
/* 158 */       ByteBuf[] certs = PemReader.readCertificates(certChainFile);
/*     */       try {
/* 160 */         for (ByteBuf buf : certs) {
/* 161 */           certChain.add(cf.generateCertificate((InputStream)new ByteBufInputStream(buf)));
/*     */         }
/*     */       } finally {
/* 164 */         for (ByteBuf buf : certs) {
/* 165 */           buf.release();
/*     */         }
/*     */       } 
/*     */       
/* 169 */       ks.setKeyEntry("key", privateKey, keyPasswordChars, certChain.<Certificate>toArray(new Certificate[certChain.size()]));
/*     */ 
/*     */       
/* 172 */       KeyManagerFactory kmf = KeyManagerFactory.getInstance(algorithm);
/* 173 */       kmf.init(ks, keyPasswordChars);
/*     */ 
/*     */       
/* 176 */       this.ctx = SSLContext.getInstance("TLS");
/* 177 */       this.ctx.init(kmf.getKeyManagers(), null, null);
/*     */       
/* 179 */       SSLSessionContext sessCtx = this.ctx.getServerSessionContext();
/* 180 */       if (sessionCacheSize > 0L) {
/* 181 */         sessCtx.setSessionCacheSize((int)Math.min(sessionCacheSize, 2147483647L));
/*     */       }
/* 183 */       if (sessionTimeout > 0L) {
/* 184 */         sessCtx.setSessionTimeout((int)Math.min(sessionTimeout, 2147483647L));
/*     */       }
/* 186 */     } catch (Exception e) {
/* 187 */       throw new SSLException("failed to initialize the server-side SSL context", e);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public boolean isClient() {
/* 193 */     return false;
/*     */   }
/*     */ 
/*     */   
/*     */   public List<String> nextProtocols() {
/* 198 */     return this.nextProtocols;
/*     */   }
/*     */ 
/*     */   
/*     */   public SSLContext context() {
/* 203 */     return this.ctx;
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
/*     */   private static PKCS8EncodedKeySpec generateKeySpec(char[] password, byte[] key) throws IOException, NoSuchAlgorithmException, NoSuchPaddingException, InvalidKeySpecException, InvalidKeyException, InvalidAlgorithmParameterException {
/* 226 */     if (password == null || password.length == 0) {
/* 227 */       return new PKCS8EncodedKeySpec(key);
/*     */     }
/*     */     
/* 230 */     EncryptedPrivateKeyInfo encryptedPrivateKeyInfo = new EncryptedPrivateKeyInfo(key);
/* 231 */     SecretKeyFactory keyFactory = SecretKeyFactory.getInstance(encryptedPrivateKeyInfo.getAlgName());
/* 232 */     PBEKeySpec pbeKeySpec = new PBEKeySpec(password);
/* 233 */     SecretKey pbeKey = keyFactory.generateSecret(pbeKeySpec);
/*     */     
/* 235 */     Cipher cipher = Cipher.getInstance(encryptedPrivateKeyInfo.getAlgName());
/* 236 */     cipher.init(2, pbeKey, encryptedPrivateKeyInfo.getAlgParameters());
/*     */     
/* 238 */     return encryptedPrivateKeyInfo.getKeySpec(cipher);
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\io\netty\handler\ssl\JdkSslServerContext.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */