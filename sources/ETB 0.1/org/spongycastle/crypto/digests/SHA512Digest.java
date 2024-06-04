package org.spongycastle.crypto.digests;

import org.spongycastle.util.Memoable;
import org.spongycastle.util.Pack;






















public class SHA512Digest
  extends LongDigest
{
  private static final int DIGEST_LENGTH = 64;
  
  public SHA512Digest() {}
  
  public SHA512Digest(SHA512Digest t)
  {
    super(t);
  }
  





  public SHA512Digest(byte[] encodedState)
  {
    restoreState(encodedState);
  }
  
  public String getAlgorithmName()
  {
    return "SHA-512";
  }
  
  public int getDigestSize()
  {
    return 64;
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
    Pack.longToBigEndian(H7, out, outOff + 48);
    Pack.longToBigEndian(H8, out, outOff + 56);
    
    reset();
    
    return 64;
  }
  



  public void reset()
  {
    super.reset();
    




    H1 = 7640891576956012808L;
    H2 = -4942790177534073029L;
    H3 = 4354685564936845355L;
    H4 = -6534734903238641935L;
    H5 = 5840696475078001361L;
    H6 = -7276294671716946913L;
    H7 = 2270897969802886507L;
    H8 = 6620516959819538809L;
  }
  
  public Memoable copy()
  {
    return new SHA512Digest(this);
  }
  
  public void reset(Memoable other)
  {
    SHA512Digest d = (SHA512Digest)other;
    
    copyIn(d);
  }
  
  public byte[] getEncodedState()
  {
    byte[] encoded = new byte[getEncodedStateSize()];
    super.populateState(encoded);
    return encoded;
  }
}
