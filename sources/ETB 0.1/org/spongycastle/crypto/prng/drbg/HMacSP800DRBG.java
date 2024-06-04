package org.spongycastle.crypto.prng.drbg;

import org.spongycastle.crypto.Mac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.util.Arrays;
















public class HMacSP800DRBG
  implements SP80090DRBG
{
  private static final long RESEED_MAX = 140737488355328L;
  private static final int MAX_BITS_REQUEST = 262144;
  private byte[] _K;
  private byte[] _V;
  private long _reseedCounter;
  private EntropySource _entropySource;
  private Mac _hMac;
  private int _securityStrength;
  
  public HMacSP800DRBG(Mac hMac, int securityStrength, EntropySource entropySource, byte[] personalizationString, byte[] nonce)
  {
    if (securityStrength > Utils.getMaxSecurityStrength(hMac))
    {
      throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
    }
    
    if (entropySource.entropySize() < securityStrength)
    {
      throw new IllegalArgumentException("Not enough entropy for security strength required");
    }
    
    _securityStrength = securityStrength;
    _entropySource = entropySource;
    _hMac = hMac;
    
    byte[] entropy = getEntropy();
    byte[] seedMaterial = Arrays.concatenate(entropy, nonce, personalizationString);
    
    _K = new byte[hMac.getMacSize()];
    _V = new byte[_K.length];
    Arrays.fill(_V, (byte)1);
    
    hmac_DRBG_Update(seedMaterial);
    
    _reseedCounter = 1L;
  }
  
  private void hmac_DRBG_Update(byte[] seedMaterial)
  {
    hmac_DRBG_Update_Func(seedMaterial, (byte)0);
    if (seedMaterial != null)
    {
      hmac_DRBG_Update_Func(seedMaterial, (byte)1);
    }
  }
  
  private void hmac_DRBG_Update_Func(byte[] seedMaterial, byte vValue)
  {
    _hMac.init(new KeyParameter(_K));
    
    _hMac.update(_V, 0, _V.length);
    _hMac.update(vValue);
    
    if (seedMaterial != null)
    {
      _hMac.update(seedMaterial, 0, seedMaterial.length);
    }
    
    _hMac.doFinal(_K, 0);
    
    _hMac.init(new KeyParameter(_K));
    _hMac.update(_V, 0, _V.length);
    
    _hMac.doFinal(_V, 0);
  }
  





  public int getBlockSize()
  {
    return _V.length * 8;
  }
  









  public int generate(byte[] output, byte[] additionalInput, boolean predictionResistant)
  {
    int numberOfBits = output.length * 8;
    
    if (numberOfBits > 262144)
    {
      throw new IllegalArgumentException("Number of bits per request limited to 262144");
    }
    
    if (_reseedCounter > 140737488355328L)
    {
      return -1;
    }
    
    if (predictionResistant)
    {
      reseed(additionalInput);
      additionalInput = null;
    }
    

    if (additionalInput != null)
    {
      hmac_DRBG_Update(additionalInput);
    }
    

    byte[] rv = new byte[output.length];
    
    int m = output.length / _V.length;
    
    _hMac.init(new KeyParameter(_K));
    
    for (int i = 0; i < m; i++)
    {
      _hMac.update(_V, 0, _V.length);
      _hMac.doFinal(_V, 0);
      
      System.arraycopy(_V, 0, rv, i * _V.length, _V.length);
    }
    
    if (m * _V.length < rv.length)
    {
      _hMac.update(_V, 0, _V.length);
      _hMac.doFinal(_V, 0);
      
      System.arraycopy(_V, 0, rv, m * _V.length, rv.length - m * _V.length);
    }
    
    hmac_DRBG_Update(additionalInput);
    
    _reseedCounter += 1L;
    
    System.arraycopy(rv, 0, output, 0, output.length);
    
    return numberOfBits;
  }
  





  public void reseed(byte[] additionalInput)
  {
    byte[] entropy = getEntropy();
    byte[] seedMaterial = Arrays.concatenate(entropy, additionalInput);
    
    hmac_DRBG_Update(seedMaterial);
    
    _reseedCounter = 1L;
  }
  
  private byte[] getEntropy()
  {
    byte[] entropy = _entropySource.getEntropy();
    
    if (entropy.length < (_securityStrength + 7) / 8)
    {
      throw new IllegalStateException("Insufficient entropy provided by entropy source");
    }
    return entropy;
  }
}
