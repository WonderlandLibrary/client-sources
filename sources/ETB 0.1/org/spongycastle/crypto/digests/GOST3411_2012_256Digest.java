package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;




public final class GOST3411_2012_256Digest
  extends GOST3411_2012Digest
{
  private static final byte[] IV = { 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 };
  









  public GOST3411_2012_256Digest()
  {
    super(IV);
  }
  
  public GOST3411_2012_256Digest(GOST3411_2012_256Digest other)
  {
    super(IV);
    reset(other);
  }
  
  public String getAlgorithmName()
  {
    return "GOST3411-2012-256";
  }
  
  public int getDigestSize()
  {
    return 32;
  }
  
  public int doFinal(byte[] out, int outOff)
  {
    byte[] result = new byte[64];
    super.doFinal(result, 0);
    
    System.arraycopy(result, 32, out, outOff, 32);
    
    return 32;
  }
  
  public Memoable copy()
  {
    return new GOST3411_2012_256Digest(this);
  }
}
