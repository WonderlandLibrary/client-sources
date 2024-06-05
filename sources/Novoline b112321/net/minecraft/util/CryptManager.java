package net.minecraft.util;

import java.io.UnsupportedEncodingException;
import java.security.GeneralSecurityException;
import java.security.Key;
import java.security.KeyFactory;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager {
   private static final Logger LOGGER = LogManager.getLogger();

   public static SecretKey createNewSharedKey() {
      String var10000 = "AES";

      try {
         KeyGenerator var0 = KeyGenerator.getInstance(var10000);
         var0.init(128);
         return var0.generateKey();
      } catch (NoSuchAlgorithmException var1) {
         throw new Error(var1);
      }
   }

   public static KeyPair generateKeyPair() {
      String var10000 = "RSA";

      try {
         KeyPairGenerator var0 = KeyPairGenerator.getInstance(var10000);
         var0.initialize(1024);
         return var0.generateKeyPair();
      } catch (NoSuchAlgorithmException var1) {
         LOGGER.error("Key pair generation failed!", var1);
         return null;
      }
   }

   public static byte[] getServerIdHash(String var0, PublicKey var1, SecretKey var2) {
      try {
         return digestOperation("SHA-1", new byte[][]{var0.getBytes("ISO_8859_1"), var2.getEncoded(), var1.getEncoded()});
      } catch (UnsupportedEncodingException var4) {
         LOGGER.error(var4);
         return null;
      }
   }

   private static byte[] digestOperation(String var0, byte[]... var1) {
      try {
         MessageDigest var2 = MessageDigest.getInstance(var0);

         for(byte[] var6 : var1) {
            var2.update(var6);
         }

         return var2.digest();
      } catch (NoSuchAlgorithmException var7) {
         LOGGER.error(var7);
         return null;
      }
   }

   public static PublicKey decodePublicKey(byte[] var0) {
      try {
         X509EncodedKeySpec var1 = new X509EncodedKeySpec(var0);
         KeyFactory var2 = KeyFactory.getInstance("RSA");
         return var2.generatePublic(var1);
      } catch (InvalidKeySpecException | NoSuchAlgorithmException var3) {
         LOGGER.error("Public key reconstitute failed!");
         return null;
      }
   }

   public static SecretKey decryptSharedKey(PrivateKey var0, byte[] var1) {
      return new SecretKeySpec(decryptData(var0, var1), "AES");
   }

   public static byte[] encryptData(Key var0, byte[] var1) {
      return cipherOperation(1, var0, var1);
   }

   public static byte[] decryptData(Key var0, byte[] var1) {
      return cipherOperation(2, var0, var1);
   }

   private static byte[] cipherOperation(int var0, Key var1, byte[] var2) {
      int var10000 = var0;
      Key var10001 = var1;

      try {
         return createTheCipherInstance(var10000, var10001.getAlgorithm(), var1).doFinal(var2);
      } catch (BadPaddingException | IllegalBlockSizeException var4) {
         LOGGER.error("Cipher data failed!", var4);
         return null;
      }
   }

   private static Cipher createTheCipherInstance(int var0, String var1, Key var2) {
      String var10000 = var1;

      try {
         Cipher var3 = Cipher.getInstance(var10000);
         var3.init(var0, var2);
         return var3;
      } catch (Throwable var4) {
         LOGGER.error("Cipher creation failed!", var4);
         return null;
      }
   }

   public static Cipher createNetCipherInstance(int var0, Key var1) {
      try {
         Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
         var2.init(var0, var1, new IvParameterSpec(var1.getEncoded()));
         return var2;
      } catch (GeneralSecurityException var3) {
         throw new RuntimeException(var3);
      }
   }
}
