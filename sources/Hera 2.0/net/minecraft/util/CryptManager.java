/*     */ package net.minecraft.util;
/*     */ 
/*     */ import java.io.UnsupportedEncodingException;
/*     */ import java.security.GeneralSecurityException;
/*     */ import java.security.InvalidKeyException;
/*     */ import java.security.Key;
/*     */ import java.security.KeyFactory;
/*     */ import java.security.KeyPair;
/*     */ import java.security.KeyPairGenerator;
/*     */ import java.security.MessageDigest;
/*     */ import java.security.NoSuchAlgorithmException;
/*     */ import java.security.PrivateKey;
/*     */ import java.security.PublicKey;
/*     */ import java.security.spec.EncodedKeySpec;
/*     */ import java.security.spec.InvalidKeySpecException;
/*     */ import java.security.spec.X509EncodedKeySpec;
/*     */ import javax.crypto.BadPaddingException;
/*     */ import javax.crypto.Cipher;
/*     */ import javax.crypto.IllegalBlockSizeException;
/*     */ import javax.crypto.KeyGenerator;
/*     */ import javax.crypto.NoSuchPaddingException;
/*     */ import javax.crypto.SecretKey;
/*     */ import javax.crypto.spec.IvParameterSpec;
/*     */ import javax.crypto.spec.SecretKeySpec;
/*     */ import org.apache.logging.log4j.LogManager;
/*     */ import org.apache.logging.log4j.Logger;
/*     */ 
/*     */ 
/*     */ public class CryptManager
/*     */ {
/*  31 */   private static final Logger LOGGER = LogManager.getLogger();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey createNewSharedKey() {
/*     */     try {
/*  40 */       KeyGenerator keygenerator = KeyGenerator.getInstance("AES");
/*  41 */       keygenerator.init(128);
/*  42 */       return keygenerator.generateKey();
/*     */     }
/*  44 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  46 */       throw new Error(nosuchalgorithmexception);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static KeyPair generateKeyPair() {
/*     */     try {
/*  57 */       KeyPairGenerator keypairgenerator = KeyPairGenerator.getInstance("RSA");
/*  58 */       keypairgenerator.initialize(1024);
/*  59 */       return keypairgenerator.generateKeyPair();
/*     */     }
/*  61 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/*  63 */       nosuchalgorithmexception.printStackTrace();
/*  64 */       LOGGER.error("Key pair generation failed!");
/*  65 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] getServerIdHash(String serverId, PublicKey publicKey, SecretKey secretKey) {
/*     */     try {
/*  76 */       return digestOperation("SHA-1", new byte[][] { serverId.getBytes("ISO_8859_1"), secretKey.getEncoded(), publicKey.getEncoded() });
/*     */     }
/*  78 */     catch (UnsupportedEncodingException unsupportedencodingexception) {
/*     */       
/*  80 */       unsupportedencodingexception.printStackTrace();
/*  81 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] digestOperation(String algorithm, byte[]... data) {
/*     */     try {
/*  92 */       MessageDigest messagedigest = MessageDigest.getInstance(algorithm); byte b; int i;
/*     */       byte[][] arrayOfByte;
/*  94 */       for (i = (arrayOfByte = data).length, b = 0; b < i; ) { byte[] abyte = arrayOfByte[b];
/*     */         
/*  96 */         messagedigest.update(abyte);
/*     */         b++; }
/*     */       
/*  99 */       return messagedigest.digest();
/*     */     }
/* 101 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 103 */       nosuchalgorithmexception.printStackTrace();
/* 104 */       return null;
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static PublicKey decodePublicKey(byte[] encodedKey) {
/*     */     try {
/* 115 */       EncodedKeySpec encodedkeyspec = new X509EncodedKeySpec(encodedKey);
/* 116 */       KeyFactory keyfactory = KeyFactory.getInstance("RSA");
/* 117 */       return keyfactory.generatePublic(encodedkeyspec);
/*     */     }
/* 119 */     catch (NoSuchAlgorithmException noSuchAlgorithmException) {
/*     */ 
/*     */     
/*     */     }
/* 123 */     catch (InvalidKeySpecException invalidKeySpecException) {}
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 128 */     LOGGER.error("Public key reconstitute failed!");
/* 129 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static SecretKey decryptSharedKey(PrivateKey key, byte[] secretKeyEncrypted) {
/* 137 */     return new SecretKeySpec(decryptData(key, secretKeyEncrypted), "AES");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] encryptData(Key key, byte[] data) {
/* 145 */     return cipherOperation(1, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static byte[] decryptData(Key key, byte[] data) {
/* 153 */     return cipherOperation(2, key, data);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static byte[] cipherOperation(int opMode, Key key, byte[] data) {
/*     */     try {
/* 163 */       return createTheCipherInstance(opMode, key.getAlgorithm(), key).doFinal(data);
/*     */     }
/* 165 */     catch (IllegalBlockSizeException illegalblocksizeexception) {
/*     */       
/* 167 */       illegalblocksizeexception.printStackTrace();
/*     */     }
/* 169 */     catch (BadPaddingException badpaddingexception) {
/*     */       
/* 171 */       badpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 174 */     LOGGER.error("Cipher data failed!");
/* 175 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static Cipher createTheCipherInstance(int opMode, String transformation, Key key) {
/*     */     try {
/* 185 */       Cipher cipher = Cipher.getInstance(transformation);
/* 186 */       cipher.init(opMode, key);
/* 187 */       return cipher;
/*     */     }
/* 189 */     catch (InvalidKeyException invalidkeyexception) {
/*     */       
/* 191 */       invalidkeyexception.printStackTrace();
/*     */     }
/* 193 */     catch (NoSuchAlgorithmException nosuchalgorithmexception) {
/*     */       
/* 195 */       nosuchalgorithmexception.printStackTrace();
/*     */     }
/* 197 */     catch (NoSuchPaddingException nosuchpaddingexception) {
/*     */       
/* 199 */       nosuchpaddingexception.printStackTrace();
/*     */     } 
/*     */     
/* 202 */     LOGGER.error("Cipher creation failed!");
/* 203 */     return null;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static Cipher createNetCipherInstance(int opMode, Key key) {
/*     */     try {
/* 213 */       Cipher cipher = Cipher.getInstance("AES/CFB8/NoPadding");
/* 214 */       cipher.init(opMode, key, new IvParameterSpec(key.getEncoded()));
/* 215 */       return cipher;
/*     */     }
/* 217 */     catch (GeneralSecurityException generalsecurityexception) {
/*     */       
/* 219 */       throw new RuntimeException(generalsecurityexception);
/*     */     } 
/*     */   }
/*     */ }


/* Location:              C:\Users\mymon\AppData\Roaming\.minecraft\versions\Hera\Hera.jar!\net\minecraf\\util\CryptManager.class
 * Java compiler version: 6 (50.0)
 * JD-Core Version:       1.1.3
 */