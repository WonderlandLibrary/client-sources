package org.spongycastle.crypto.prng.drbg;

import java.util.Hashtable;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.prng.EntropySource;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.Integers;





public class HashSP800DRBG
  implements SP80090DRBG
{
  private static final byte[] ONE = { 1 };
  
  private static final long RESEED_MAX = 140737488355328L;
  
  private static final int MAX_BITS_REQUEST = 262144;
  private static final Hashtable seedlens = new Hashtable();
  private Digest _digest;
  
  static {
    seedlens.put("SHA-1", Integers.valueOf(440));
    seedlens.put("SHA-224", Integers.valueOf(440));
    seedlens.put("SHA-256", Integers.valueOf(440));
    seedlens.put("SHA-512/256", Integers.valueOf(440));
    seedlens.put("SHA-512/224", Integers.valueOf(440));
    seedlens.put("SHA-384", Integers.valueOf(888));
    seedlens.put("SHA-512", Integers.valueOf(888));
  }
  






  private byte[] _V;
  





  private byte[] _C;
  




  public HashSP800DRBG(Digest digest, int securityStrength, EntropySource entropySource, byte[] personalizationString, byte[] nonce)
  {
    if (securityStrength > Utils.getMaxSecurityStrength(digest))
    {
      throw new IllegalArgumentException("Requested security strength is not supported by the derivation function");
    }
    
    if (entropySource.entropySize() < securityStrength)
    {
      throw new IllegalArgumentException("Not enough entropy for security strength required");
    }
    
    _digest = digest;
    _entropySource = entropySource;
    _securityStrength = securityStrength;
    _seedLength = ((Integer)seedlens.get(digest.getAlgorithmName())).intValue();
    








    byte[] entropy = getEntropy();
    byte[] seedMaterial = Arrays.concatenate(entropy, nonce, personalizationString);
    byte[] seed = Utils.hash_df(_digest, seedMaterial, _seedLength);
    
    _V = seed;
    byte[] subV = new byte[_V.length + 1];
    System.arraycopy(_V, 0, subV, 1, _V.length);
    _C = Utils.hash_df(_digest, subV, _seedLength);
    
    _reseedCounter = 1L;
  }
  





  public int getBlockSize()
  {
    return _digest.getDigestSize() * 8;
  }
  




  private long _reseedCounter;
  



  private EntropySource _entropySource;
  



  private int _securityStrength;
  


  private int _seedLength;
  


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
      byte[] newInput = new byte[1 + _V.length + additionalInput.length];
      newInput[0] = 2;
      System.arraycopy(_V, 0, newInput, 1, _V.length);
      
      System.arraycopy(additionalInput, 0, newInput, 1 + _V.length, additionalInput.length);
      byte[] w = hash(newInput);
      
      addTo(_V, w);
    }
    

    byte[] rv = hashgen(_V, numberOfBits);
    

    byte[] subH = new byte[_V.length + 1];
    System.arraycopy(_V, 0, subH, 1, _V.length);
    subH[0] = 3;
    
    byte[] H = hash(subH);
    

    addTo(_V, H);
    addTo(_V, _C);
    byte[] c = new byte[4];
    c[0] = ((byte)(int)(_reseedCounter >> 24));
    c[1] = ((byte)(int)(_reseedCounter >> 16));
    c[2] = ((byte)(int)(_reseedCounter >> 8));
    c[3] = ((byte)(int)_reseedCounter);
    
    addTo(_V, c);
    
    _reseedCounter += 1L;
    
    System.arraycopy(rv, 0, output, 0, output.length);
    
    return numberOfBits;
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
  



  private void addTo(byte[] longer, byte[] shorter)
  {
    int carry = 0;
    for (int i = 1; i <= shorter.length; i++)
    {
      int res = (longer[(longer.length - i)] & 0xFF) + (shorter[(shorter.length - i)] & 0xFF) + carry;
      carry = res > 255 ? 1 : 0;
      longer[(longer.length - i)] = ((byte)res);
    }
    
    for (int i = shorter.length + 1; i <= longer.length; i++)
    {
      int res = (longer[(longer.length - i)] & 0xFF) + carry;
      carry = res > 255 ? 1 : 0;
      longer[(longer.length - i)] = ((byte)res);
    }
  }
  


















  public void reseed(byte[] additionalInput)
  {
    byte[] entropy = getEntropy();
    byte[] seedMaterial = Arrays.concatenate(ONE, _V, entropy, additionalInput);
    byte[] seed = Utils.hash_df(_digest, seedMaterial, _seedLength);
    
    _V = seed;
    byte[] subV = new byte[_V.length + 1];
    subV[0] = 0;
    System.arraycopy(_V, 0, subV, 1, _V.length);
    _C = Utils.hash_df(_digest, subV, _seedLength);
    
    _reseedCounter = 1L;
  }
  
  private byte[] hash(byte[] input)
  {
    byte[] hash = new byte[_digest.getDigestSize()];
    doHash(input, hash);
    return hash;
  }
  
  private void doHash(byte[] input, byte[] output)
  {
    _digest.update(input, 0, input.length);
    _digest.doFinal(output, 0);
  }
  









  private byte[] hashgen(byte[] input, int lengthInBits)
  {
    int digestSize = _digest.getDigestSize();
    int m = lengthInBits / 8 / digestSize;
    
    byte[] data = new byte[input.length];
    System.arraycopy(input, 0, data, 0, input.length);
    
    byte[] W = new byte[lengthInBits / 8];
    
    byte[] dig = new byte[_digest.getDigestSize()];
    for (int i = 0; i <= m; i++)
    {
      doHash(data, dig);
      
      int bytesToCopy = W.length - i * dig.length > dig.length ? dig.length : W.length - i * dig.length;
      

      System.arraycopy(dig, 0, W, i * dig.length, bytesToCopy);
      
      addTo(data, ONE);
    }
    
    return W;
  }
}
