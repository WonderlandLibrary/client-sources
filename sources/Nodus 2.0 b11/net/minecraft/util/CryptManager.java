/*   1:    */ package net.minecraft.util;
/*   2:    */ 
/*   3:    */ import java.io.PrintStream;
/*   4:    */ import java.io.UnsupportedEncodingException;
/*   5:    */ import java.security.GeneralSecurityException;
/*   6:    */ import java.security.InvalidKeyException;
/*   7:    */ import java.security.Key;
/*   8:    */ import java.security.KeyFactory;
/*   9:    */ import java.security.KeyPair;
/*  10:    */ import java.security.KeyPairGenerator;
/*  11:    */ import java.security.MessageDigest;
/*  12:    */ import java.security.NoSuchAlgorithmException;
/*  13:    */ import java.security.PrivateKey;
/*  14:    */ import java.security.PublicKey;
/*  15:    */ import java.security.spec.InvalidKeySpecException;
/*  16:    */ import java.security.spec.X509EncodedKeySpec;
/*  17:    */ import javax.crypto.BadPaddingException;
/*  18:    */ import javax.crypto.Cipher;
/*  19:    */ import javax.crypto.IllegalBlockSizeException;
/*  20:    */ import javax.crypto.KeyGenerator;
/*  21:    */ import javax.crypto.NoSuchPaddingException;
/*  22:    */ import javax.crypto.SecretKey;
/*  23:    */ import javax.crypto.spec.IvParameterSpec;
/*  24:    */ import javax.crypto.spec.SecretKeySpec;
/*  25:    */ 
/*  26:    */ public class CryptManager
/*  27:    */ {
/*  28:    */   private static final String __OBFID = "CL_00001483";
/*  29:    */   
/*  30:    */   public static SecretKey createNewSharedKey()
/*  31:    */   {
/*  32:    */     try
/*  33:    */     {
/*  34: 36 */       KeyGenerator var0 = KeyGenerator.getInstance("AES");
/*  35: 37 */       var0.init(128);
/*  36: 38 */       return var0.generateKey();
/*  37:    */     }
/*  38:    */     catch (NoSuchAlgorithmException var1)
/*  39:    */     {
/*  40: 42 */       throw new Error(var1);
/*  41:    */     }
/*  42:    */   }
/*  43:    */   
/*  44:    */   public static KeyPair createNewKeyPair()
/*  45:    */   {
/*  46:    */     try
/*  47:    */     {
/*  48: 50 */       KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
/*  49: 51 */       var0.initialize(1024);
/*  50: 52 */       return var0.generateKeyPair();
/*  51:    */     }
/*  52:    */     catch (NoSuchAlgorithmException var1)
/*  53:    */     {
/*  54: 56 */       var1.printStackTrace();
/*  55: 57 */       System.err.println("Key pair generation failed!");
/*  56:    */     }
/*  57: 58 */     return null;
/*  58:    */   }
/*  59:    */   
/*  60:    */   public static byte[] getServerIdHash(String par0Str, PublicKey par1PublicKey, SecretKey par2SecretKey)
/*  61:    */   {
/*  62:    */     try
/*  63:    */     {
/*  64: 69 */       return digestOperation("SHA-1", new byte[][] { par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded() });
/*  65:    */     }
/*  66:    */     catch (UnsupportedEncodingException var4)
/*  67:    */     {
/*  68: 73 */       var4.printStackTrace();
/*  69:    */     }
/*  70: 74 */     return null;
/*  71:    */   }
/*  72:    */   
/*  73:    */   private static byte[] digestOperation(String par0Str, byte[]... par1ArrayOfByte)
/*  74:    */   {
/*  75:    */     try
/*  76:    */     {
/*  77: 85 */       MessageDigest var2 = MessageDigest.getInstance(par0Str);
/*  78: 86 */       byte[][] var3 = par1ArrayOfByte;
/*  79: 87 */       int var4 = par1ArrayOfByte.length;
/*  80: 89 */       for (int var5 = 0; var5 < var4; var5++)
/*  81:    */       {
/*  82: 91 */         byte[] var6 = var3[var5];
/*  83: 92 */         var2.update(var6);
/*  84:    */       }
/*  85: 95 */       return var2.digest();
/*  86:    */     }
/*  87:    */     catch (NoSuchAlgorithmException var7)
/*  88:    */     {
/*  89: 99 */       var7.printStackTrace();
/*  90:    */     }
/*  91:100 */     return null;
/*  92:    */   }
/*  93:    */   
/*  94:    */   public static PublicKey decodePublicKey(byte[] par0ArrayOfByte)
/*  95:    */   {
/*  96:    */     try
/*  97:    */     {
/*  98:111 */       X509EncodedKeySpec var1 = new X509EncodedKeySpec(par0ArrayOfByte);
/*  99:112 */       KeyFactory var2 = KeyFactory.getInstance("RSA");
/* 100:113 */       return var2.generatePublic(var1);
/* 101:    */     }
/* 102:    */     catch (NoSuchAlgorithmException localNoSuchAlgorithmException) {}catch (InvalidKeySpecException localInvalidKeySpecException) {}
/* 103:124 */     System.err.println("Public key reconstitute failed!");
/* 104:125 */     return null;
/* 105:    */   }
/* 106:    */   
/* 107:    */   public static SecretKey decryptSharedKey(PrivateKey par0PrivateKey, byte[] par1ArrayOfByte)
/* 108:    */   {
/* 109:133 */     return new SecretKeySpec(decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
/* 110:    */   }
/* 111:    */   
/* 112:    */   public static byte[] encryptData(Key par0Key, byte[] par1ArrayOfByte)
/* 113:    */   {
/* 114:141 */     return cipherOperation(1, par0Key, par1ArrayOfByte);
/* 115:    */   }
/* 116:    */   
/* 117:    */   public static byte[] decryptData(Key par0Key, byte[] par1ArrayOfByte)
/* 118:    */   {
/* 119:149 */     return cipherOperation(2, par0Key, par1ArrayOfByte);
/* 120:    */   }
/* 121:    */   
/* 122:    */   private static byte[] cipherOperation(int par0, Key par1Key, byte[] par2ArrayOfByte)
/* 123:    */   {
/* 124:    */     try
/* 125:    */     {
/* 126:159 */       return createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
/* 127:    */     }
/* 128:    */     catch (IllegalBlockSizeException var4)
/* 129:    */     {
/* 130:163 */       var4.printStackTrace();
/* 131:    */     }
/* 132:    */     catch (BadPaddingException var5)
/* 133:    */     {
/* 134:167 */       var5.printStackTrace();
/* 135:    */     }
/* 136:170 */     System.err.println("Cipher data failed!");
/* 137:171 */     return null;
/* 138:    */   }
/* 139:    */   
/* 140:    */   private static Cipher createTheCipherInstance(int par0, String par1Str, Key par2Key)
/* 141:    */   {
/* 142:    */     try
/* 143:    */     {
/* 144:181 */       Cipher var3 = Cipher.getInstance(par1Str);
/* 145:182 */       var3.init(par0, par2Key);
/* 146:183 */       return var3;
/* 147:    */     }
/* 148:    */     catch (InvalidKeyException var4)
/* 149:    */     {
/* 150:187 */       var4.printStackTrace();
/* 151:    */     }
/* 152:    */     catch (NoSuchAlgorithmException var5)
/* 153:    */     {
/* 154:191 */       var5.printStackTrace();
/* 155:    */     }
/* 156:    */     catch (NoSuchPaddingException var6)
/* 157:    */     {
/* 158:195 */       var6.printStackTrace();
/* 159:    */     }
/* 160:198 */     System.err.println("Cipher creation failed!");
/* 161:199 */     return null;
/* 162:    */   }
/* 163:    */   
/* 164:    */   public static Cipher func_151229_a(int p_151229_0_, Key p_151229_1_)
/* 165:    */   {
/* 166:    */     try
/* 167:    */     {
/* 168:206 */       Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
/* 169:207 */       var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
/* 170:208 */       return var2;
/* 171:    */     }
/* 172:    */     catch (GeneralSecurityException var3)
/* 173:    */     {
/* 174:212 */       throw new RuntimeException(var3);
/* 175:    */     }
/* 176:    */   }
/* 177:    */ }


/* Location:           C:\Users\D\AppData\Roaming\.minecraft\versions\Nodus_2.0-1.7.x\Nodus_2.0-1.7.x.jar
 * Qualified Name:     net.minecraft.util.CryptManager
 * JD-Core Version:    0.7.0.1
 */