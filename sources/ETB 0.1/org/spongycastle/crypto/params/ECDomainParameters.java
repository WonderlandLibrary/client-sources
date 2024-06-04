package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.Arrays;





public class ECDomainParameters
  implements ECConstants
{
  private ECCurve curve;
  private byte[] seed;
  private ECPoint G;
  private BigInteger n;
  private BigInteger h;
  
  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n)
  {
    this(curve, G, n, ONE, null);
  }
  




  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h)
  {
    this(curve, G, n, h, null);
  }
  





  public ECDomainParameters(ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed)
  {
    this.curve = curve;
    this.G = G.normalize();
    this.n = n;
    this.h = h;
    this.seed = seed;
  }
  
  public ECCurve getCurve()
  {
    return curve;
  }
  
  public ECPoint getG()
  {
    return G;
  }
  
  public BigInteger getN()
  {
    return n;
  }
  
  public BigInteger getH()
  {
    return h;
  }
  
  public byte[] getSeed()
  {
    return Arrays.clone(seed);
  }
  

  public boolean equals(Object obj)
  {
    if (this == obj)
    {
      return true;
    }
    
    if ((obj instanceof ECDomainParameters))
    {
      ECDomainParameters other = (ECDomainParameters)obj;
      
      return (curve.equals(curve)) && (G.equals(G)) && (n.equals(n)) && (h.equals(h));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    int hc = curve.hashCode();
    hc *= 37;
    hc ^= G.hashCode();
    hc *= 37;
    hc ^= n.hashCode();
    hc *= 37;
    hc ^= h.hashCode();
    return hc;
  }
}
