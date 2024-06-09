package HORIZON-6-0-SKIDPROTECTION;

import java.security.GeneralSecurityException;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import javax.crypto.Cipher;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.spec.SecretKeySpec;
import java.security.Key;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.security.KeyFactory;
import java.security.spec.X509EncodedKeySpec;
import java.security.MessageDigest;
import java.io.UnsupportedEncodingException;
import java.security.PublicKey;
import java.security.KeyPairGenerator;
import java.security.KeyPair;
import java.security.NoSuchAlgorithmException;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CryptManager
{
    private static final Logger HorizonCode_Horizon_È;
    private static final String Â = "CL_00001483";
    
    static {
        HorizonCode_Horizon_È = LogManager.getLogger();
    }
    
    public static SecretKey HorizonCode_Horizon_È() {
        try {
            final KeyGenerator var0 = KeyGenerator.getInstance("AES");
            var0.init(128);
            return var0.generateKey();
        }
        catch (NoSuchAlgorithmException var2) {
            throw new Error(var2);
        }
    }
    
    public static KeyPair Â() {
        try {
            final KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            CryptManager.HorizonCode_Horizon_È.error("Key pair generation failed!");
            return null;
        }
    }
    
    public static byte[] HorizonCode_Horizon_È(final String p_75895_0_, final PublicKey p_75895_1_, final SecretKey p_75895_2_) {
        try {
            return HorizonCode_Horizon_È("SHA-1", new byte[][] { p_75895_0_.getBytes("ISO_8859_1"), p_75895_2_.getEncoded(), p_75895_1_.getEncoded() });
        }
        catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    private static byte[] HorizonCode_Horizon_È(final String p_75893_0_, final byte[]... p_75893_1_) {
        try {
            final MessageDigest var2 = MessageDigest.getInstance(p_75893_0_);
            for (final byte[] var5 : p_75893_1_) {
                var2.update(var5);
            }
            return var2.digest();
        }
        catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey HorizonCode_Horizon_È(final byte[] p_75896_0_) {
        try {
            final X509EncodedKeySpec var1 = new X509EncodedKeySpec(p_75896_0_);
            final KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        }
        catch (NoSuchAlgorithmException ex) {}
        catch (InvalidKeySpecException ex2) {}
        CryptManager.HorizonCode_Horizon_È.error("Public key reconstitute failed!");
        return null;
    }
    
    public static SecretKey HorizonCode_Horizon_È(final PrivateKey p_75887_0_, final byte[] p_75887_1_) {
        return new SecretKeySpec(Â(p_75887_0_, p_75887_1_), "AES");
    }
    
    public static byte[] HorizonCode_Horizon_È(final Key p_75894_0_, final byte[] p_75894_1_) {
        return HorizonCode_Horizon_È(1, p_75894_0_, p_75894_1_);
    }
    
    public static byte[] Â(final Key p_75889_0_, final byte[] p_75889_1_) {
        return HorizonCode_Horizon_È(2, p_75889_0_, p_75889_1_);
    }
    
    private static byte[] HorizonCode_Horizon_È(final int p_75885_0_, final Key p_75885_1_, final byte[] p_75885_2_) {
        try {
            return HorizonCode_Horizon_È(p_75885_0_, p_75885_1_.getAlgorithm(), p_75885_1_).doFinal(p_75885_2_);
        }
        catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5) {
            var5.printStackTrace();
        }
        CryptManager.HorizonCode_Horizon_È.error("Cipher data failed!");
        return null;
    }
    
    private static Cipher HorizonCode_Horizon_È(final int p_75886_0_, final String p_75886_1_, final Key p_75886_2_) {
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
        CryptManager.HorizonCode_Horizon_È.error("Cipher creation failed!");
        return null;
    }
    
    public static Cipher HorizonCode_Horizon_È(final int p_151229_0_, final Key p_151229_1_) {
        try {
            final Cipher var2 = Cipher.getInstance("AES/CFB8/NoPadding");
            var2.init(p_151229_0_, p_151229_1_, new IvParameterSpec(p_151229_1_.getEncoded()));
            return var2;
        }
        catch (GeneralSecurityException var3) {
            throw new RuntimeException(var3);
        }
    }
}
