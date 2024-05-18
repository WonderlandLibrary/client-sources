package net.minecraft.util;

import org.apache.logging.log4j.*;
import java.security.spec.*;
import java.io.*;
import java.security.*;
import javax.crypto.spec.*;
import javax.crypto.*;

public class CryptManager
{
    private static final String[] I;
    private static final Logger LOGGER;
    
    private static String I(final String s, final String s2) {
        final StringBuilder sb = new StringBuilder();
        final char[] charArray = s2.toCharArray();
        int length = "".length();
        final char[] charArray2 = s.toCharArray();
        final int length2 = charArray2.length;
        int i = "".length();
        while (i < length2) {
            sb.append((char)(charArray2[i] ^ charArray[length % charArray.length]));
            ++length;
            ++i;
            "".length();
            if (3 <= -1) {
                throw null;
            }
        }
        return sb.toString();
    }
    
    static {
        I();
        LOGGER = LogManager.getLogger();
    }
    
    public static byte[] decryptData(final Key key, final byte[] array) {
        return cipherOperation("  ".length(), key, array);
    }
    
    private static Cipher createTheCipherInstance(final int n, final String s, final Key key) {
        try {
            final Cipher instance = Cipher.getInstance(s);
            instance.init(n, key);
            return instance;
        }
        catch (InvalidKeyException ex) {
            ex.printStackTrace();
            "".length();
            if (2 < 2) {
                throw null;
            }
        }
        catch (NoSuchAlgorithmException ex2) {
            ex2.printStackTrace();
            "".length();
            if (true != true) {
                throw null;
            }
        }
        catch (NoSuchPaddingException ex3) {
            ex3.printStackTrace();
        }
        CryptManager.LOGGER.error(CryptManager.I[0x83 ^ 0x8A]);
        return null;
    }
    
    public static PublicKey decodePublicKey(final byte[] array) {
        try {
            return KeyFactory.getInstance(CryptManager.I[0xBF ^ 0xBA]).generatePublic(new X509EncodedKeySpec(array));
        }
        catch (NoSuchAlgorithmException ex) {
            "".length();
            if (-1 != -1) {
                throw null;
            }
        }
        catch (InvalidKeySpecException ex2) {}
        CryptManager.LOGGER.error(CryptManager.I[0xB5 ^ 0xB3]);
        return null;
    }
    
    private static byte[] digestOperation(final String s, final byte[]... array) {
        try {
            final MessageDigest instance = MessageDigest.getInstance(s);
            final int length = array.length;
            int i = "".length();
            "".length();
            if (3 != 3) {
                throw null;
            }
            while (i < length) {
                instance.update(array[i]);
                ++i;
            }
            return instance.digest();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static Cipher createNetCipherInstance(final int n, final Key key) {
        try {
            final Cipher instance = Cipher.getInstance(CryptManager.I[0x2D ^ 0x27]);
            instance.init(n, key, new IvParameterSpec(key.getEncoded()));
            return instance;
        }
        catch (GeneralSecurityException ex) {
            throw new RuntimeException(ex);
        }
    }
    
    public static byte[] getServerIdHash(final String s, final PublicKey publicKey, final SecretKey secretKey) {
        try {
            final String s2 = CryptManager.I["   ".length()];
            final byte[][] array = new byte["   ".length()][];
            array["".length()] = s.getBytes(CryptManager.I[0x50 ^ 0x54]);
            array[" ".length()] = secretKey.getEncoded();
            array["  ".length()] = publicKey.getEncoded();
            return digestOperation(s2, array);
        }
        catch (UnsupportedEncodingException ex) {
            ex.printStackTrace();
            return null;
        }
    }
    
    public static byte[] encryptData(final Key key, final byte[] array) {
        return cipherOperation(" ".length(), key, array);
    }
    
    public static KeyPair generateKeyPair() {
        try {
            final KeyPairGenerator instance = KeyPairGenerator.getInstance(CryptManager.I[" ".length()]);
            instance.initialize(186 + 393 + 304 + 141);
            return instance.generateKeyPair();
        }
        catch (NoSuchAlgorithmException ex) {
            ex.printStackTrace();
            CryptManager.LOGGER.error(CryptManager.I["  ".length()]);
            return null;
        }
    }
    
    private static void I() {
        (I = new String[0x72 ^ 0x79])["".length()] = I("\t4\u0000", "HqSad");
        CryptManager.I[" ".length()] = I("85\u0003", "jfBSI");
        CryptManager.I["  ".length()] = I("\u001a*2v90&9v.4!.$(%&$8i7.\":,5n", "QOKVI");
        CryptManager.I["   ".length()] = I("\u0011$${i", "BleVX");
        CryptManager.I[0x8E ^ 0x8A] = I("\u0011\u0014\n8{`r|8r", "XGEgC");
        CryptManager.I[0x65 ^ 0x60] = I("\u00017\u0019", "SdXsD");
        CryptManager.I[0x99 ^ 0x9F] = I("*?+'\u001b\u0019j\".\u000bZ8,(\u001d\u00149=\"\u0006\u000f>,k\u0014\u001b#%.\u0016[", "zJIKr");
        CryptManager.I[0xA7 ^ 0xA0] = I("\u000b\u001c\u0014", "JYGsv");
        CryptManager.I[0x68 ^ 0x60] = I("\u00150\u0015!-$y\u0001(<7y\u0003(!:<\u0001h", "VYeIH");
        CryptManager.I[0xAD ^ 0xA4] = I("\u0014\u0003#\u000b\u0014%J0\u0011\u00146\u001e:\f\u001fw\f2\n\u001d2\u000er", "WjScq");
        CryptManager.I[0x7D ^ 0x77] = I("\u000448V0\u00033SV=*!\n\u001d\u0017,\u001f\f", "Eqkys");
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey privateKey, final byte[] array) {
        return new SecretKeySpec(decryptData(privateKey, array), CryptManager.I[0x68 ^ 0x6F]);
    }
    
    private static byte[] cipherOperation(final int n, final Key key, final byte[] array) {
        try {
            return createTheCipherInstance(n, key.getAlgorithm(), key).doFinal(array);
        }
        catch (IllegalBlockSizeException ex) {
            ex.printStackTrace();
            "".length();
            if (-1 >= 1) {
                throw null;
            }
        }
        catch (BadPaddingException ex2) {
            ex2.printStackTrace();
        }
        CryptManager.LOGGER.error(CryptManager.I[0xCA ^ 0xC2]);
        return null;
    }
    
    public static SecretKey createNewSharedKey() {
        try {
            final KeyGenerator instance = KeyGenerator.getInstance(CryptManager.I["".length()]);
            instance.init(15 + 25 + 35 + 53);
            return instance.generateKey();
        }
        catch (NoSuchAlgorithmException ex) {
            throw new Error(ex);
        }
    }
}
