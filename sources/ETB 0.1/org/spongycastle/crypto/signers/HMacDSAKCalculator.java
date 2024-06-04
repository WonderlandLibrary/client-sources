package org.spongycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.spongycastle.crypto.Digest;
import org.spongycastle.crypto.macs.HMac;
import org.spongycastle.crypto.params.KeyParameter;
import org.spongycastle.util.Arrays;
import org.spongycastle.util.BigIntegers;





public class HMacDSAKCalculator
  implements DSAKCalculator
{
  private static final BigInteger ZERO = BigInteger.valueOf(0L);
  

  private final HMac hMac;
  

  private final byte[] K;
  
  private final byte[] V;
  
  private BigInteger n;
  

  public HMacDSAKCalculator(Digest digest)
  {
    hMac = new HMac(digest);
    V = new byte[hMac.getMacSize()];
    K = new byte[hMac.getMacSize()];
  }
  
  public boolean isDeterministic()
  {
    return true;
  }
  
  public void init(BigInteger n, SecureRandom random)
  {
    throw new IllegalStateException("Operation not supported");
  }
  
  public void init(BigInteger n, BigInteger d, byte[] message)
  {
    this.n = n;
    
    Arrays.fill(V, (byte)1);
    Arrays.fill(K, (byte)0);
    
    byte[] x = new byte[(n.bitLength() + 7) / 8];
    byte[] dVal = BigIntegers.asUnsignedByteArray(d);
    
    System.arraycopy(dVal, 0, x, x.length - dVal.length, dVal.length);
    
    byte[] m = new byte[(n.bitLength() + 7) / 8];
    
    BigInteger mInt = bitsToInt(message);
    
    if (mInt.compareTo(n) >= 0)
    {
      mInt = mInt.subtract(n);
    }
    
    byte[] mVal = BigIntegers.asUnsignedByteArray(mInt);
    
    System.arraycopy(mVal, 0, m, m.length - mVal.length, mVal.length);
    
    hMac.init(new KeyParameter(K));
    
    hMac.update(V, 0, V.length);
    hMac.update((byte)0);
    hMac.update(x, 0, x.length);
    hMac.update(m, 0, m.length);
    
    hMac.doFinal(K, 0);
    
    hMac.init(new KeyParameter(K));
    
    hMac.update(V, 0, V.length);
    
    hMac.doFinal(V, 0);
    
    hMac.update(V, 0, V.length);
    hMac.update((byte)1);
    hMac.update(x, 0, x.length);
    hMac.update(m, 0, m.length);
    
    hMac.doFinal(K, 0);
    
    hMac.init(new KeyParameter(K));
    
    hMac.update(V, 0, V.length);
    
    hMac.doFinal(V, 0);
  }
  
  public BigInteger nextK()
  {
    byte[] t = new byte[(n.bitLength() + 7) / 8];
    
    for (;;)
    {
      int tOff = 0;
      
      while (tOff < t.length)
      {
        hMac.update(V, 0, V.length);
        
        hMac.doFinal(V, 0);
        
        int len = Math.min(t.length - tOff, V.length);
        System.arraycopy(V, 0, t, tOff, len);
        tOff += len;
      }
      
      BigInteger k = bitsToInt(t);
      
      if ((k.compareTo(ZERO) > 0) && (k.compareTo(n) < 0))
      {
        return k;
      }
      
      hMac.update(V, 0, V.length);
      hMac.update((byte)0);
      
      hMac.doFinal(K, 0);
      
      hMac.init(new KeyParameter(K));
      
      hMac.update(V, 0, V.length);
      
      hMac.doFinal(V, 0);
    }
  }
  
  private BigInteger bitsToInt(byte[] t)
  {
    BigInteger v = new BigInteger(1, t);
    
    if (t.length * 8 > n.bitLength())
    {
      v = v.shiftRight(t.length * 8 - n.bitLength());
    }
    
    return v;
  }
}
