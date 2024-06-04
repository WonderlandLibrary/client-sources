package org.spongycastle.pqc.jcajce.provider.util;

import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.InvalidParameterException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import javax.crypto.BadPaddingException;
import javax.crypto.CipherSpi;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.ShortBufferException;








































public abstract class CipherSpiExt
  extends CipherSpi
{
  public static final int ENCRYPT_MODE = 1;
  public static final int DECRYPT_MODE = 2;
  protected int opMode;
  
  public CipherSpiExt() {}
  
  protected final void engineInit(int opMode, Key key, SecureRandom random)
    throws InvalidKeyException
  {
    try
    {
      engineInit(opMode, key, (AlgorithmParameterSpec)null, random);

    }
    catch (InvalidAlgorithmParameterException e)
    {
      throw new InvalidParameterException(e.getMessage());
    }
  }
  





























  protected final void engineInit(int opMode, Key key, AlgorithmParameters algParams, SecureRandom random)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if (algParams == null)
    {
      engineInit(opMode, key, random);
      return;
    }
    
    AlgorithmParameterSpec paramSpec = null;
    

    engineInit(opMode, key, paramSpec, random);
  }
  



























  protected void engineInit(int opMode, Key key, AlgorithmParameterSpec params, SecureRandom javaRand)
    throws InvalidKeyException, InvalidAlgorithmParameterException
  {
    if ((params != null) && (!(params instanceof AlgorithmParameterSpec)))
    {
      throw new InvalidAlgorithmParameterException();
    }
    
    if ((key == null) || (!(key instanceof Key)))
    {
      throw new InvalidKeyException();
    }
    
    this.opMode = opMode;
    
    if (opMode == 1)
    {
      SecureRandom flexiRand = javaRand;
      initEncrypt(key, params, flexiRand);

    }
    else if (opMode == 2)
    {
      initDecrypt(key, params);
    }
  }
  




















  protected final byte[] engineDoFinal(byte[] input, int inOff, int inLen)
    throws IllegalBlockSizeException, BadPaddingException
  {
    return doFinal(input, inOff, inLen);
  }
  


























  protected final int engineDoFinal(byte[] input, int inOff, int inLen, byte[] output, int outOff)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException
  {
    return doFinal(input, inOff, inLen, output, outOff);
  }
  




  protected final int engineGetBlockSize()
  {
    return getBlockSize();
  }
  







  protected final int engineGetKeySize(Key key)
    throws InvalidKeyException
  {
    if (!(key instanceof Key))
    {
      throw new InvalidKeyException("Unsupported key.");
    }
    return getKeySize(key);
  }
  









  protected final byte[] engineGetIV()
  {
    return getIV();
  }
  














  protected final int engineGetOutputSize(int inLen)
  {
    return getOutputSize(inLen);
  }
  














  protected final AlgorithmParameters engineGetParameters()
  {
    return null;
  }
  







  protected final void engineSetMode(String modeName)
    throws NoSuchAlgorithmException
  {
    setMode(modeName);
  }
  






  protected final void engineSetPadding(String paddingName)
    throws NoSuchPaddingException
  {
    setPadding(paddingName);
  }
  












  protected final byte[] engineUpdate(byte[] input, int inOff, int inLen)
  {
    return update(input, inOff, inLen);
  }
  


















  protected final int engineUpdate(byte[] input, int inOff, int inLen, byte[] output, int outOff)
    throws ShortBufferException
  {
    return update(input, inOff, inLen, output, outOff);
  }
  












  public abstract void initEncrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec, SecureRandom paramSecureRandom)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
  












  public abstract void initDecrypt(Key paramKey, AlgorithmParameterSpec paramAlgorithmParameterSpec)
    throws InvalidKeyException, InvalidAlgorithmParameterException;
  












  public abstract String getName();
  












  public abstract int getBlockSize();
  












  public abstract int getOutputSize(int paramInt);
  












  public abstract int getKeySize(Key paramKey)
    throws InvalidKeyException;
  











  public abstract AlgorithmParameterSpec getParameters();
  











  public abstract byte[] getIV();
  











  protected abstract void setMode(String paramString)
    throws NoSuchAlgorithmException;
  











  protected abstract void setPadding(String paramString)
    throws NoSuchPaddingException;
  











  public final byte[] update(byte[] input)
  {
    return update(input, 0, input.length);
  }
  











  public abstract byte[] update(byte[] paramArrayOfByte, int paramInt1, int paramInt2);
  











  public abstract int update(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws ShortBufferException;
  











  public final byte[] doFinal()
    throws IllegalBlockSizeException, BadPaddingException
  {
    return doFinal(null, 0, 0);
  }
  












  public final byte[] doFinal(byte[] input)
    throws IllegalBlockSizeException, BadPaddingException
  {
    return doFinal(input, 0, input.length);
  }
  
  public abstract byte[] doFinal(byte[] paramArrayOfByte, int paramInt1, int paramInt2)
    throws IllegalBlockSizeException, BadPaddingException;
  
  public abstract int doFinal(byte[] paramArrayOfByte1, int paramInt1, int paramInt2, byte[] paramArrayOfByte2, int paramInt3)
    throws ShortBufferException, IllegalBlockSizeException, BadPaddingException;
}
