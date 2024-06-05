package win.sightclient.utils;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.KeySpec;
import java.util.Base64;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.PBEParameterSpec;

public class CryptUtils 
{

    static Cipher ecipher;
    static Cipher dcipher;
    // 8-byte Salt
    static byte[] salt = {
        (byte) 0xB9, (byte) 0x9B, (byte) 0xC8, (byte) 0x32,
        (byte) 0x36, (byte) 0x35, (byte) 0xE3, (byte) 0x03
    };
    // Iteration count
    static int iterationCount = 19;
    
    public static String encrypt(String secretKey, String plainText) {
        //Key generation for enc and desc
		try {
	        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	        // Prepare the parameter to the ciphers
	        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);

	        //Enc process
	        ecipher = Cipher.getInstance(key.getAlgorithm());
	        ecipher.init(Cipher.ENCRYPT_MODE, key, paramSpec);
	        String charSet = "UTF-8";
	        byte[] in = plainText.getBytes(charSet);
	        byte[] out = ecipher.doFinal(in);
	        String encStr = new String(Base64.getEncoder().encode(out));
	        return encStr;
		} catch (InvalidKeySpecException | NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		}
		return "";
    }
    public static String decrypt(String secretKey, String encryptedText) {
        //Key generation for enc and desc
		try {
	        KeySpec keySpec = new PBEKeySpec(secretKey.toCharArray(), salt, iterationCount);
	        SecretKey key = SecretKeyFactory.getInstance("PBEWithMD5AndDES").generateSecret(keySpec);
	        // Prepare the parameter to the ciphers
	        AlgorithmParameterSpec paramSpec = new PBEParameterSpec(salt, iterationCount);
	        //Decryption process; same key will be used for decr
	        dcipher = Cipher.getInstance(key.getAlgorithm());
	        dcipher.init(Cipher.DECRYPT_MODE, key, paramSpec);
	        byte[] enc = Base64.getDecoder().decode(encryptedText);
	        byte[] utf8 = dcipher.doFinal(enc);
	        String charSet = "UTF-8";
	        String plainStr = new String(utf8, charSet);
	        return plainStr;
		} catch (InvalidKeySpecException e) {
			e.printStackTrace();
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (NoSuchPaddingException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		} catch (InvalidAlgorithmParameterException e) {
			e.printStackTrace();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IllegalBlockSizeException e) {
			e.printStackTrace();
		} catch (BadPaddingException e) {
			e.printStackTrace();
		} catch (java.lang.IllegalArgumentException e) {
			e.printStackTrace();
		}
		return "";
    }
    
    private static String CIPHER_NAME = "AES/CBC/PKCS5PADDING";
    private static int CIPHER_KEY_LEN = 16; //128 bits
    
    public static String encryptWithAes(String key, String iv, String data) {
        try {
            if (key.length() < CIPHER_KEY_LEN) {
                int numPad = CIPHER_KEY_LEN - key.length();

                for(int i = 0; i < numPad; i++){
                    key += "0"; //0 pad to len 16 bytes
                }

            } else if (key.length() > CIPHER_KEY_LEN) {
                key = key.substring(0, CIPHER_KEY_LEN); //truncate to 16 bytes
            }

            Class<?> cl = Class.forName("javax.crypto.spec.IvParameterSpec");
            Constructor<?> cons = cl.getConstructor(byte[].class);
            Object initVector = cons.newInstance(iv.getBytes("UTF-8"));
            
            Class<?> scl = Class.forName("javax.crypto.spec.SecretKeySpec");
            Constructor<?> scons = scl.getConstructor(byte[].class, String.class);
            Object skeySpec = scons.newInstance(key.getBytes("UTF-8"), "AES");


            Class<?> ccl = Class.forName("javax.crypto.Cipher");
            Field mode = ccl.getDeclaredField("ENCRYPT_MODE");
            Method printlnMethod = ccl.getDeclaredMethod("getInstance", String.class);
            Object cipher = printlnMethod.invoke(null, CIPHER_NAME);
            
            Method init = ccl.getDeclaredMethod("init", int.class, Class.forName("java.security.Key"), Class.forName("java.security.spec.AlgorithmParameterSpec"));
            init.invoke(cipher, mode.get(null), skeySpec, initVector);
            
            Method dofinal = ccl.getDeclaredMethod("doFinal", byte[].class);
            byte[] encryptedData = (byte[]) dofinal.invoke(cipher, data.getBytes());

            Class<?> base = Class.forName("java.util.Base64");
            Method encoder = base.getDeclaredMethod("getEncoder");
            Object encode = encoder.invoke(null);
            
            Class<?> encoderin = Class.forName("java.util.Base64$Encoder");
            Method etos = encoderin.getDeclaredMethod("encodeToString", byte[].class);
            String base64_EncryptedData = (String) etos.invoke(encode, encryptedData);
            String base64_IV = (String) etos.invoke(encode, iv.getBytes("UTF-8"));
            
            return base64_EncryptedData + ":" + base64_IV;

        } catch (Exception ex) {ex.printStackTrace();}

        return null;
    }
    
    public static String decryptWithAes(String key, String data) {
        try {


            String[] parts = data.split(":");
            
            Class<?> base = Class.forName("java.util.Base64");
            Method getDecoder = base.getDeclaredMethod("getDecoder");
            Object decoder = getDecoder.invoke(null);
            
            Class<?> decoderIn = Class.forName("java.util.Base64$Decoder");
            Method dtos = decoderIn.getDeclaredMethod("decode", String.class);
            byte[] base64_EncryptedData = (byte[]) dtos.invoke(decoder, parts[0]);
            byte[] base64_IV = (byte[]) dtos.invoke(decoder, parts[1]);
            
            Class<?> cl = Class.forName("javax.crypto.spec.IvParameterSpec");
            Constructor<?> cons = cl.getConstructor(byte[].class);
            Object initVector = cons.newInstance(base64_IV);
            
            Class<?> scl = Class.forName("javax.crypto.spec.SecretKeySpec");
            Constructor<?> scons = scl.getConstructor(byte[].class, String.class);
            Object skeySpec = scons.newInstance(key.getBytes("UTF-8"), "AES");
            
            Class<?> ccl = Class.forName("javax.crypto.Cipher");
            Field mode = ccl.getDeclaredField("DECRYPT_MODE");
            Method printlnMethod = ccl.getDeclaredMethod("getInstance", String.class);
            Object cipher = printlnMethod.invoke(null, CIPHER_NAME);
            
            Method init = ccl.getDeclaredMethod("init", int.class, Class.forName("java.security.Key"), Class.forName("java.security.spec.AlgorithmParameterSpec"));
            init.invoke(cipher, mode.get(null), skeySpec, initVector);
            
            Method dofinal = ccl.getDeclaredMethod("doFinal", byte[].class);
            byte[] original = (byte[]) dofinal.invoke(cipher, base64_EncryptedData);
            

            return new String(original);
        } catch (Exception ex) { }

        return null;
    }
}
