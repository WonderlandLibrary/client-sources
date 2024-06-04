package org.silvertunnel_ng.netlib.layer.tor.util;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


























public class HybridEncryption
{
  private static final int PK_ENC_LEN = 128;
  private static final int PK_PAD_LEN = 42;
  private static final int PK_DATA_LEN = 86;
  private static final int PK_DATA_LEN_WITH_KEY = 70;
  private final Cipher cipher;
  
  public HybridEncryption()
    throws TorException
  {
    try
    {
      cipher = Cipher.getInstance("RSA/ECB/OAEPWithSHA1AndMGF1Padding");
    }
    catch (NoSuchAlgorithmException e)
    {
      throw new TorException(e);
    }
    catch (NoSuchPaddingException e)
    {
      throw new TorException(e);
    }
  }
  











  public final byte[] encrypt(byte[] data, PublicKey publicKey, byte[] secretKey)
    throws TorException
  {
    if (data.length < 86)
    {
      return encryptSimple(data, publicKey);
    }
    

    TorStreamCipher randomKeyCipher = TorStreamCipher.createFromKeyBytes(secretKey);
    byte[] kAndM1 = new byte[86];
    System.arraycopy(randomKeyCipher.getKeyBytes(), 0, kAndM1, 0, 16);
    System.arraycopy(data, 0, kAndM1, 16, 70);
    byte[] c1 = encryptSimple(kAndM1, publicKey);
    

    byte[] c2 = new byte[data.length - 70];
    System.arraycopy(data, 70, c2, 0, c2.length);
    randomKeyCipher.encrypt(c2);
    



    byte[] output = new byte[c1.length + c2.length];
    System.arraycopy(c1, 0, output, 0, c1.length);
    System.arraycopy(c2, 0, output, c1.length, c2.length);
    return output;
  }
  
  private byte[] encryptSimple(byte[] data, PublicKey publicKey) throws TorException
  {
    try
    {
      cipher.init(1, publicKey);
      return cipher.doFinal(data);
    }
    catch (InvalidKeyException e)
    {
      throw new TorException(e);
    }
    catch (IllegalBlockSizeException e)
    {
      throw new TorException(e);
    }
    catch (BadPaddingException e)
    {
      throw new TorException(e);
    }
  }
  












  public byte[] decrypt(byte[] data, PrivateKey privateKey)
    throws TorException
  {
    if (data.length < 128)
    {
      throw new TorException("Message is too short");
    }
    
    if (data.length == 128)
    {
      return decryptSimple(data, privateKey);
    }
    

    byte[] c1 = new byte[''];
    byte[] c2 = new byte[data.length - 128];
    System.arraycopy(data, 0, c1, 0, 128);
    System.arraycopy(data, 128, c2, 0, c2.length);
    

    byte[] kAndM1 = decryptSimple(c1, privateKey);
    byte[] streamKey = new byte[16];
    int m1Length = kAndM1.length - 16;
    byte[] m1 = new byte[m1Length];
    System.arraycopy(kAndM1, 0, streamKey, 0, 16);
    System.arraycopy(kAndM1, 16, m1, 0, m1Length);
    

    AESCounterMode streamCipher = new AESCounterMode(streamKey);
    streamCipher.processStream(c2);
    byte[] m2 = c2;
    
    byte[] output = new byte[m1.length + m2.length];
    System.arraycopy(m1, 0, output, 0, m1.length);
    System.arraycopy(m2, 0, output, m1.length, m2.length);
    return output;
  }
  
  private byte[] decryptSimple(byte[] data, PrivateKey privateKey) throws TorException
  {
    try
    {
      cipher.init(2, privateKey);
      return cipher.doFinal(data);
    }
    catch (InvalidKeyException e)
    {
      throw new TorException(e);
    }
    catch (IllegalBlockSizeException e)
    {
      throw new TorException(e);
    }
    catch (BadPaddingException e)
    {
      throw new TorException(e);
    }
  }
}
