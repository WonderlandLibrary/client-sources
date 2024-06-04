package org.spongycastle.jce.spec;

import java.math.BigInteger;














public class GOST3410PublicKeyParameterSetSpec
{
  private BigInteger p;
  private BigInteger q;
  private BigInteger a;
  
  public GOST3410PublicKeyParameterSetSpec(BigInteger p, BigInteger q, BigInteger a)
  {
    this.p = p;
    this.q = q;
    this.a = a;
  }
  





  public BigInteger getP()
  {
    return p;
  }
  





  public BigInteger getQ()
  {
    return q;
  }
  





  public BigInteger getA()
  {
    return a;
  }
  

  public boolean equals(Object o)
  {
    if ((o instanceof GOST3410PublicKeyParameterSetSpec))
    {
      GOST3410PublicKeyParameterSetSpec other = (GOST3410PublicKeyParameterSetSpec)o;
      
      return (a.equals(a)) && (p.equals(p)) && (q.equals(q));
    }
    
    return false;
  }
  
  public int hashCode()
  {
    return a.hashCode() ^ p.hashCode() ^ q.hashCode();
  }
}
