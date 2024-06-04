package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;






















public class SHA384Digest
  extends LongDigest
{
  private static final int DIGEST_LENGTH = 48;
  
  public SHA384Digest() {}
  
  public SHA384Digest(SHA384Digest t)
  {
    super(t);
  }
  





  public SHA384Digest(byte[] encodedState)
  {
    restoreState(encodedState);
  }
  
  public String getAlgorithmName()
  {
    return "SHA-384";
  }
  
  public int getDigestSize()
  {
    return 48;
  }
  


  public int doFinal(byte[] out, int outOff)
  {
    finish();
    
    Pack.longToBigEndian(H1, out, outOff);
    Pack.longToBigEndian(H2, out, outOff + 8);
    Pack.longToBigEndian(H3, out, outOff + 16);
    Pack.longToBigEndian(H4, out, outOff + 24);
    Pack.longToBigEndian(H5, out, outOff + 32);
    Pack.longToBigEndian(H6, out, outOff + 40);
    
    reset();
    
    return 48;
  }
  



  public void reset()
  {
    super.reset();
    




    H1 = -3766243637369397544L;
    H2 = 7105036623409894663L;
    H3 = -7973340178411365097L;
    H4 = 1526699215303891257L;
    H5 = 7436329637833083697L;
    H6 = -8163818279084223215L;
    H7 = -2662702644619276377L;
    H8 = 5167115440072839076L;
  }
  
  public Memoable copy()
  {
    return new SHA384Digest(this);
  }
  
  public void reset(Memoable other)
  {
    SHA384Digest d = (SHA384Digest)other;
    
    super.copyIn(d);
  }
  
  public byte[] getEncodedState()
  {
    byte[] encoded = new byte[getEncodedStateSize()];
    super.populateState(encoded);
    return encoded;
  }
}
