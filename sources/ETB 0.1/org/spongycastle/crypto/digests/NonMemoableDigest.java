package org.spongycastle.crypto.digests;

import org.spongycastle.crypto.ExtendedDigest;











public class NonMemoableDigest
  implements ExtendedDigest
{
  private ExtendedDigest baseDigest;
  
  public NonMemoableDigest(ExtendedDigest baseDigest)
  {
    if (baseDigest == null)
    {
      throw new IllegalArgumentException("baseDigest must not be null");
    }
    
    this.baseDigest = baseDigest;
  }
  
  public String getAlgorithmName()
  {
    return baseDigest.getAlgorithmName();
  }
  
  public int getDigestSize()
  {
    return baseDigest.getDigestSize();
  }
  
  public void update(byte in)
  {
    baseDigest.update(in);
  }
  
  public void update(byte[] in, int inOff, int len)
  {
    baseDigest.update(in, inOff, len);
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    return baseDigest.doFinal(out, outOff);
  }
  
  public void reset()
  {
    baseDigest.reset();
  }
  
  public int getByteLength()
  {
    return baseDigest.getByteLength();
  }
}
