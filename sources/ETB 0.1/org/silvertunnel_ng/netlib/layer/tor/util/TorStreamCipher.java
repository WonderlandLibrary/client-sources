package org.silvertunnel_ng.netlib.layer.tor.util;

import java.security.GeneralSecurityException;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class TorStreamCipher
{
  public static final int KEY_LEN = 16;
  private static final int BLOCK_SIZE = 16;
  private final Cipher cipher;
  private final byte[] counter;
  private final byte[] counterOut;
  
  public static TorStreamCipher createWithRandomKey()
    throws TorException
  {
    SecretKey randomKey = generateRandomKey();
    return new TorStreamCipher(randomKey.getEncoded());
  }
  
  public static TorStreamCipher createFromKeyBytes(byte[] keyBytes) throws TorException
  {
    return new TorStreamCipher(keyBytes);
  }
  





  private int keystreamPointer = -1;
  private final SecretKeySpec key;
  
  private TorStreamCipher(byte[] keyBytes) throws TorException
  {
    key = keyBytesToSecretKey(keyBytes);
    cipher = createCipher(key);
    counter = new byte[16];
    counterOut = new byte[16];
  }
  
  public void encrypt(byte[] data) throws TorException
  {
    encrypt(data, 0, data.length);
  }
  
  public synchronized void encrypt(byte[] data, int offset, int length) throws TorException
  {
    for (int i = 0; i < length; i++)
    {
      int tmp14_13 = (i + offset); byte[] tmp14_9 = data;tmp14_9[tmp14_13] = ((byte)(tmp14_9[tmp14_13] ^ nextKeystreamByte()));
    }
  }
  
  public byte[] getKeyBytes()
  {
    return key.getEncoded();
  }
  
  private static SecretKeySpec keyBytesToSecretKey(byte[] keyBytes)
  {
    return new SecretKeySpec(keyBytes, "AES");
  }
  
  private static Cipher createCipher(SecretKeySpec keySpec) throws TorException
  {
    try
    {
      Cipher cipher = Cipher.getInstance("AES/ECB/NoPadding");
      cipher.init(1, keySpec);
      return cipher;
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
  
  private static SecretKey generateRandomKey() throws TorException
  {
    try
    {
      KeyGenerator generator = KeyGenerator.getInstance("AES");
      generator.init(128);
      return generator.generateKey();
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
  
  private byte nextKeystreamByte() throws TorException
  {
    if ((keystreamPointer == -1) || (keystreamPointer >= 16))
    {
      updateCounter();
    }
    return counterOut[(keystreamPointer++)];
  }
  
  private void updateCounter() throws TorException
  {
    encryptCounter();
    incrementCounter();
    keystreamPointer = 0;
  }
  
  private void encryptCounter() throws TorException
  {
    try
    {
      cipher.doFinal(counter, 0, 16, counterOut, 0);
    }
    catch (GeneralSecurityException e)
    {
      throw new TorException(e);
    }
  }
  
  private void incrementCounter()
  {
    int carry = 1;
    for (int i = counter.length - 1; i >= 0; i--)
    {
      int x = (counter[i] & 0xFF) + carry;
      if (x > 255)
      {
        carry = 1;
      }
      else
      {
        carry = 0;
      }
      counter[i] = ((byte)x);
    }
  }
}
