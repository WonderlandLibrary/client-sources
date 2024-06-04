package org.spongycastle.pqc.jcajce.provider.util;

import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.ShortBufferException;
































public abstract class AsymmetricHybridCipher
  extends CipherSpiExt
{
  protected AlgorithmParameterSpec paramSpec;
  
  public AsymmetricHybridCipher() {}
  
  protected final void setMode(String modeName) {}
  
  protected final void setPadding(String paddingName) {}
  
  public final byte[] getIV()
  {
    return null;
  }
  



  public final int getBlockSize()
  {
    return 0;
  }
  













  public final AlgorithmParameterSpec getParameters()
  {
    return paramSpec;
  }
  












  public final int getOutputSize(int inLen)
  {
    return opMode == 1 ? encryptOutputSize(inLen) : 
      decryptOutputSize(inLen);
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
  






















  public final void initEncrypt(Key key, AlgorithmParameterSpec params, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    opMode = 1;
    initCipherEncrypt(key, params, random);
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
  











  public abstract byte[] update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  











  public final int update(byte[] input, int inOff, int inLen, byte[] output, int outOff)
    throws ShortBufferException
  {
    if (output.length < getOutputSize(inLen))
    {
      throw new ShortBufferException("output");
    }
    byte[] out = update(input, inOff, inLen);
    System.arraycopy(out, 0, output, outOff, out.length);
    return out.length;
  }
  













  public abstract byte[] doFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws BadPaddingException;
  












  public final int doFinal(byte[] input, int inOff, int inLen, byte[] output, int outOff)
    throws ShortBufferException, BadPaddingException
  {
    if (output.length < getOutputSize(inLen))
    {
      throw new ShortBufferException("Output buffer too short.");
    }
    byte[] out = doFinal(input, inOff, inLen);
    System.arraycopy(out, 0, output, outOff, out.length);
    return out.length;
  }
  
  protected abstract int encryptOutputSize(int paramInt);
  
  protected abstract int decryptOutputSize(int paramInt);
  
  protected abstract void initCipherEncrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
  
  protected abstract void initCipherDecrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
}
