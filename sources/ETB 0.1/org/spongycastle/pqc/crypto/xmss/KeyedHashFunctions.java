package org.spongycastle.pqc.crypto.xmss;

import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.Xof;






final class KeyedHashFunctions
{
  private final Digest digest;
  private final int digestSize;
  
  protected KeyedHashFunctions(Digest digest, int digestSize)
  {
    if (digest == null)
    {
      throw new NullPointerException("digest == null");
    }
    this.digest = digest;
    this.digestSize = digestSize;
  }
  
  private byte[] coreDigest(int fixedValue, byte[] key, byte[] index)
  {
    byte[] in = XMSSUtil.toBytesBigEndian(fixedValue, digestSize);
    
    digest.update(in, 0, in.length);
    
    digest.update(key, 0, key.length);
    
    digest.update(index, 0, index.length);
    
    byte[] out = new byte[digestSize];
    if ((digest instanceof Xof))
    {
      ((Xof)digest).doFinal(out, 0, digestSize);
    }
    else
    {
      digest.doFinal(out, 0);
    }
    return out;
  }
  
  protected byte[] F(byte[] key, byte[] in)
  {
    if (key.length != digestSize)
    {
      throw new IllegalArgumentException("wrong key length");
    }
    if (in.length != digestSize)
    {
      throw new IllegalArgumentException("wrong in length");
    }
    return coreDigest(0, key, in);
  }
  
  protected byte[] H(byte[] key, byte[] in)
  {
    if (key.length != digestSize)
    {
      throw new IllegalArgumentException("wrong key length");
    }
    if (in.length != 2 * digestSize)
    {
      throw new IllegalArgumentException("wrong in length");
    }
    return coreDigest(1, key, in);
  }
  
  protected byte[] HMsg(byte[] key, byte[] in)
  {
    if (key.length != 3 * digestSize)
    {
      throw new IllegalArgumentException("wrong key length");
    }
    return coreDigest(2, key, in);
  }
  
  protected byte[] PRF(byte[] key, byte[] address)
  {
    if (key.length != digestSize)
    {
      throw new IllegalArgumentException("wrong key length");
    }
    if (address.length != 32)
    {
      throw new IllegalArgumentException("wrong address length");
    }
    return coreDigest(3, key, address);
  }
}
