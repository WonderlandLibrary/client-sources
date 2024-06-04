package org.spongycastle.jce.spec;

import java.math.BigInteger;
import java.security.spec.AlgorithmParameterSpec;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECPoint;








public class ECParameterSpec
  implements AlgorithmParameterSpec
{
  private ECCurve curve;
  private byte[] seed;
  private ECPoint G;
  private BigInteger n;
  private BigInteger h;
  
  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n)
  {
    this.curve = curve;
    this.G = G.normalize();
    this.n = n;
    h = BigInteger.valueOf(1L);
    seed = null;
  }
  




  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n, BigInteger h)
  {
    this.curve = curve;
    this.G = G.normalize();
    this.n = n;
    this.h = h;
    seed = null;
  }
  





  public ECParameterSpec(ECCurve curve, ECPoint G, BigInteger n, BigInteger h, byte[] seed)
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
    return seed;
  }
  
  public boolean equals(Object o)
  {
    if (!(o instanceof ECParameterSpec))
    {
      return false;
    }
    
    ECParameterSpec other = (ECParameterSpec)o;
    
    return (getCurve().equals(other.getCurve())) && (getG().equals(other.getG()));
  }
  
  public int hashCode()
  {
    return getCurve().hashCode() ^ getG().hashCode();
  }
}
