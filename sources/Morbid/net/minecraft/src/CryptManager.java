package net.minecraft.src;

import java.nio.charset.*;
import org.bouncycastle.jce.provider.*;
import javax.crypto.spec.*;
import java.security.spec.*;
import java.security.*;
import javax.crypto.*;
import org.bouncycastle.crypto.engines.*;
import org.bouncycastle.crypto.modes.*;
import org.bouncycastle.crypto.params.*;
import org.bouncycastle.crypto.*;
import java.io.*;
import org.bouncycastle.crypto.io.*;

public class CryptManager
{
    public static final Charset charSet;
    
    static {
        charSet = Charset.forName("ISO_8859_1");
        Security.addProvider(new BouncyCastleProvider());
    }
    
    public static SecretKey createNewSharedKey() {
        final CipherKeyGenerator var0 = new CipherKeyGenerator();
        var0.init(new KeyGenerationParameters(new SecureRandom(), 128));
        return new SecretKeySpec(var0.generateKey(), "AES");
    }
    
    public static KeyPair createNewKeyPair() {
        try {
            final KeyPairGenerator var0 = KeyPairGenerator.getInstance("RSA");
            var0.initialize(1024);
            return var0.generateKeyPair();
        }
        catch (NoSuchAlgorithmException var2) {
            var2.printStackTrace();
            System.err.println("Key pair generation failed!");
            return null;
        }
    }
    
    public static byte[] getServerIdHash(final String par0Str, final PublicKey par1PublicKey, final SecretKey par2SecretKey) {
        try {
            return digestOperation("SHA-1", new byte[][] { par0Str.getBytes("ISO_8859_1"), par2SecretKey.getEncoded(), par1PublicKey.getEncoded() });
        }
        catch (UnsupportedEncodingException var4) {
            var4.printStackTrace();
            return null;
        }
    }
    
    private static byte[] digestOperation(final String par0Str, final byte[]... par1ArrayOfByte) {
        try {
            final MessageDigest var2 = MessageDigest.getInstance(par0Str);
            for (final byte[] var5 : par1ArrayOfByte) {
                var2.update(var5);
            }
            return var2.digest();
        }
        catch (NoSuchAlgorithmException var6) {
            var6.printStackTrace();
            return null;
        }
    }
    
    public static PublicKey decodePublicKey(final byte[] par0ArrayOfByte) {
        try {
            final X509EncodedKeySpec var1 = new X509EncodedKeySpec(par0ArrayOfByte);
            final KeyFactory var2 = KeyFactory.getInstance("RSA");
            return var2.generatePublic(var1);
        }
        catch (NoSuchAlgorithmException var3) {
            var3.printStackTrace();
        }
        catch (InvalidKeySpecException var4) {
            var4.printStackTrace();
        }
        System.err.println("Public key reconstitute failed!");
        return null;
    }
    
    public static SecretKey decryptSharedKey(final PrivateKey par0PrivateKey, final byte[] par1ArrayOfByte) {
        return new SecretKeySpec(decryptData(par0PrivateKey, par1ArrayOfByte), "AES");
    }
    
    public static byte[] encryptData(final Key par0Key, final byte[] par1ArrayOfByte) {
        return cipherOperation(1, par0Key, par1ArrayOfByte);
    }
    
    public static byte[] decryptData(final Key par0Key, final byte[] par1ArrayOfByte) {
        return cipherOperation(2, par0Key, par1ArrayOfByte);
    }
    
    private static byte[] cipherOperation(final int par0, final Key par1Key, final byte[] par2ArrayOfByte) {
        try {
            return createTheCipherInstance(par0, par1Key.getAlgorithm(), par1Key).doFinal(par2ArrayOfByte);
        }
        catch (IllegalBlockSizeException var4) {
            var4.printStackTrace();
        }
        catch (BadPaddingException var5) {
            var5.printStackTrace();
        }
        System.err.println("Cipher data failed!");
        return null;
    }
    
    private static Cipher createTheCipherInstance(final int par0, final String par1Str, final Key par2Key) {
        try {
            final Cipher var3 = Cipher.getInstance(par1Str);
            var3.init(par0, par2Key);
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
        System.err.println("Cipher creation failed!");
        return null;
    }
    
    private static BufferedBlockCipher createBufferedBlockCipher(final boolean par0, final Key par1Key) {
        final BufferedBlockCipher var2 = new BufferedBlockCipher(new CFBBlockCipher(new AESFastEngine(), 8));
        var2.init(par0, new ParametersWithIV(new KeyParameter(par1Key.getEncoded()), par1Key.getEncoded(), 0, 16));
        return var2;
    }
    
    public static OutputStream encryptOuputStream(final SecretKey par0SecretKey, final OutputStream par1OutputStream) {
        return new CipherOutputStream(par1OutputStream, createBufferedBlockCipher(true, par0SecretKey));
    }
    
    public static InputStream decryptInputStream(final SecretKey par0SecretKey, final InputStream par1InputStream) {
        return new CipherInputStream(par1InputStream, createBufferedBlockCipher(false, par0SecretKey));
    }
}
