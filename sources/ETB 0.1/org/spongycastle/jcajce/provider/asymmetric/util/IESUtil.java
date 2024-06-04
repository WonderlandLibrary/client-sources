package org.spongycastle.jcajce.provider.asymmetric.util;

import org.spongycastle.crypto.BlockCipher;

public class IESUtil
{
  public IESUtil() {}
  
  public static org.spongycastle.jce.spec.IESParameterSpec guessParameterSpec(org.spongycastle.crypto.BufferedBlockCipher iesBlockCipher, byte[] nonce)
  {
    if (iesBlockCipher == null)
    {
      return new org.spongycastle.jce.spec.IESParameterSpec(null, null, 128);
    }
    

    BlockCipher underlyingCipher = iesBlockCipher.getUnderlyingCipher();
    
    if ((underlyingCipher.getAlgorithmName().equals("DES")) || 
      (underlyingCipher.getAlgorithmName().equals("RC2")) || 
      (underlyingCipher.getAlgorithmName().equals("RC5-32")) || 
      (underlyingCipher.getAlgorithmName().equals("RC5-64")))
    {
      return new org.spongycastle.jce.spec.IESParameterSpec(null, null, 64, 64, nonce);
    }
    if (underlyingCipher.getAlgorithmName().equals("SKIPJACK"))
    {
      return new org.spongycastle.jce.spec.IESParameterSpec(null, null, 80, 80, nonce);
    }
    if (underlyingCipher.getAlgorithmName().equals("GOST28147"))
    {
      return new org.spongycastle.jce.spec.IESParameterSpec(null, null, 256, 256, nonce);
    }
    
    return new org.spongycastle.jce.spec.IESParameterSpec(null, null, 128, 128, nonce);
  }
}
