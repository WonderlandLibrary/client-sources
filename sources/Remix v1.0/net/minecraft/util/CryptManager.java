package net.minecraft.util;

import java.io.*;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.spec.*;
import java.security.*;
import org.apache.logging.log4j.*;

public class CryptManager
{
    private static final Logger field_180198_a;
    
    public static SecretKey createNewSharedKey() {
        try {
            final KeyGenerator var0 = KeyGenerator.getInstance("AES");
            var0.init(128);
            return var0.generateKey();
        }
        catch (NoSuchAlgorithmException var2) {
            throw new Error(var2);
        }
    }
    
    public static KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            CryptManager.field_180198_a.error("Key pair generation failed!");
            return null;
        }
    }
    
    public static byte[] getServerIdHash(final String p_75895_0_, final PublicKey p_75895_1_, final SecretKey p_75895_2_) {
        try {
            return digestOperation("SHA-1", new byte[][] { p_75895_0_.getBytes("ISO_8859_1"), p_75895_2_.getEncoded(), p_75895_1_.getEncoded() });
        }
        catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    private static byte[] digestOperation(final String p_75893_0_, final byte[]... p_75893_1_) {
        try {
            final MessageDigest var2 = MessageDigest.getInstance(p_75893_0_);
            final byte[][] var3 = p_75893_1_;
            for (int var4 = p_75893_1_.length, var5 = 0; var5 < var4; ++var5) {
                final byte[] var6 = var3[var5];
                var2.update(var6);
            }
            return var2.digest();
        }
        catch (NoSuchAlgorithmException var7) {
            var7.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey decodePublicKey(final byte[] p_75896_0_) {
        try {
            final X509EncodedKeySpec var1 = new X509EncodedKeySpec(p_75896_0_);
            final KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        }
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException ex2) {}
        CryptManager.field_180198_a.error("Public key reconstitute failed!");
        return null;
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey p_75887_0_, final byte[] p_75887_1_) {
        return new SecretKeySpec(decryptData(p_75887_0_, p_75887_1_), "AES");
    }
    
    public static byte[] encryptData(final Key p_75894_0_, final byte[] p_75894_1_) {
        return cipherOperation(1, p_75894_0_, p_75894_1_);
    }
    
    public static byte[] decryptData(final Key p_75889_0_, final byte[] p_75889_1_) {
        return cipherOperation(2, p_75889_0_, p_75889_1_);
    }
    
    private static byte[] cipherOperation(final int p_75885_0_, final Key p_75885_1_, final byte[] p_75885_2_) {
        try {
            return createTheCipherInstance(p_75885_0_, p_75885_1_.getAlgorithm(), p_75885_1_).doFinal(p_75885_2_);
        }
        catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5) {
            var5.printStackTrace();
        }
        CryptManager.field_180198_a.error("Cipher data failed!");
        return null;
    }
    
    private static Cipher createTheCipherInstance(final int p_75886_0_, final String p_75886_1_, final Key p_75886_2_) {
        try {
            final Cipher var3 = Cipher.getInstance(p_75886_1_);
            var3.init(p_75886_0_, p_75886_2_);
            return var3;
        }
        catch (InvalidKeyException var4) {
            var4.printStackTrace();
        }
        catch (NoSuchAlgorithmException var5) {
            var5.printStackTrace();
        }
        catch (NoSuchPaddingException var6) {
            var6.printStackTrace();
        }
        CryptManager.field_180198_a.error("Cipher creation failed!");
        return null;
    }
    
    public static Cipher func_151229_a(final int p_151229_0_, final Key p_151229_1_) {
        try {
            final Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
            var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
            return var2;
        }
        catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }
    
    static {
        field_180198_a = LogManager.getLogger();
    }
}
