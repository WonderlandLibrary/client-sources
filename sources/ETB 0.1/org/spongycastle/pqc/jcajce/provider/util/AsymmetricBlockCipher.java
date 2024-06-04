package org.spongycastle.pqc.jcajce.provider.util;

import java.io.ByteArrayOutputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.ShortBufferException;





























public abstract class AsymmetricBlockCipher
  extends CipherSpiExt
{
  protected AlgorithmParameterSpec paramSpec;
  protected ByteArrayOutputStream buf;
  protected int maxPlainTextSize;
  protected int cipherTextSize;
  
  public AsymmetricBlockCipher()
  {
    buf = new ByteArrayOutputStream();
  }
  









  public final int getBlockSize()
  {
    return opMode == 1 ? maxPlainTextSize : cipherTextSize;
  }
  



  public final byte[] getIV()
  {
    return null;
  }
  
















  public final int getOutputSize(int inLen)
  {
    int totalLen = inLen + buf.size();
    
    int maxLen = getBlockSize();
    
    if (totalLen > maxLen)
    {

      return 0;
    }
    
    return maxLen;
  }
  














  public final AlgorithmParameterSpec getParameters()
  {
    return paramSpec;
  }
  
















  public final void initEncrypt(Key key)
    throws InvalidKeyException
  {
    try
    {
      initEncrypt(key, null, new SecureRandom());
    }
    catch (InvalidAlgorithmParameterException e)
    {
      throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
    }
  }
  



















  public final void initEncrypt(Key key, SecureRandom random)
    throws InvalidKeyException
  {
    try
    {
      initEncrypt(key, null, random);
    }
    catch (InvalidAlgorithmParameterException iape)
    {
      throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
    }
  }
  














  public final void initEncrypt(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    initEncrypt(key, params, new SecureRandom());
  }
  

























  public final void initEncrypt(Key key, AlgorithmParameterSpec params, SecureRandom secureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    opMode = 1;
    initCipherEncrypt(key, params, secureRandom);
  }
  
















  public final void initDecrypt(Key key)
    throws InvalidKeyException
  {
    try
    {
      initDecrypt(key, null);
    }
    catch (InvalidAlgorithmParameterException iape)
    {
      throw new InvalidParameterException("This cipher needs algorithm parameters for initialization (cannot be null).");
    }
  }
  























  public final void initDecrypt(Key key, AlgorithmParameterSpec params)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    opMode = 2;
    initCipherDecrypt(key, params);
  }
  









  public final byte[] update(byte[] input, int inOff, int inLen)
  {
    if (inLen != 0)
    {
      buf.write(input, inOff, inLen);
    }
    return new byte[0];
  }
  












  public final int update(byte[] input, int inOff, int inLen, byte[] output, int outOff)
  {
    update(input, inOff, inLen);
    return 0;
  }
  












  public final byte[] doFinal(byte[] input, int inOff, int inLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    checkLength(inLen);
    update(input, inOff, inLen);
    byte[] mBytes = buf.toByteArray();
    buf.reset();
    
    switch (opMode)
    {
    case 1: 
      return messageEncrypt(mBytes);
    
    case 2: 
      return messageDecrypt(mBytes);
    }
    
    return null;
  }
  



















  public final int doFinal(byte[] input, int inOff, int inLen, byte[] output, int outOff)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
  {
    if (output.length < getOutputSize(inLen))
    {
      throw new ShortBufferException("Output buffer too short.");
    }
    
    byte[] out = doFinal(input, inOff, inLen);
    System.arraycopy(out, 0, output, outOff, out.length);
    return out.length;
  }
  











  protected final void setMode(String modeName) {}
  










  protected final void setPadding(String paddingName) {}
  










  protected void checkLength(int inLen)
    throws IllegalBlockSizeException
  {
    int inLength = inLen + buf.size();
    
    if (opMode == 1)
    {
      if (inLength > maxPlainTextSize)
      {
        throw new IllegalBlockSizeException("The length of the plaintext (" + inLength + " bytes) is not supported by the cipher (max. " + maxPlainTextSize + " bytes).");

      }
      


    }
    else if (opMode == 2)
    {
      if (inLength != cipherTextSize)
      {
        throw new IllegalBlockSizeException("Illegal ciphertext length (expected " + cipherTextSize + " bytes, was " + inLength + " bytes).");
      }
    }
  }
  
  protected abstract void initCipherEncrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
  
  protected abstract void initCipherDecrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
  
  protected abstract byte[] messageEncrypt(byte[] paramArrayOfByte)
    throws IllegalBlockSizeException, BadPaddingException;
  
  protected abstract byte[] messageDecrypt(byte[] paramArrayOfByte)
    throws IllegalBlockSizeException, BadPaddingException;
}
