package org.spongycastle.pqc.crypto.ntru;

import java.nio.ByteBuffer;
import org.spongycastle.crypto.Digest;











public class NTRUSignerPrng
{
  private int counter;
  private byte[] seed;
  private Digest hashAlg;
  
  NTRUSignerPrng(byte[] seed, Digest hashAlg)
  {
    counter = 0;
    this.seed = seed;
    this.hashAlg = hashAlg;
  }
  






  byte[] nextBytes(int n)
  {
    ByteBuffer buf = ByteBuffer.allocate(n);
    
    while (buf.hasRemaining())
    {
      ByteBuffer cbuf = ByteBuffer.allocate(seed.length + 4);
      cbuf.put(seed);
      cbuf.putInt(counter);
      byte[] array = cbuf.array();
      byte[] hash = new byte[hashAlg.getDigestSize()];
      
      hashAlg.update(array, 0, array.length);
      
      hashAlg.doFinal(hash, 0);
      
      if (buf.remaining() < hash.length)
      {
        buf.put(hash, 0, buf.remaining());
      }
      else
      {
        buf.put(hash);
      }
      counter += 1;
    }
    
    return buf.array();
  }
}
