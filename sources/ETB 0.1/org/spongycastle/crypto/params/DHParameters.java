package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;





public class DHParameters
  implements CipherParameters
{
  private static final int DEFAULT_MINIMUM_LENGTH = 160;
  private BigInteger g;
  private BigInteger p;
  private BigInteger q;
  private BigInteger j;
  private int m;
  private int l;
  private DHValidationParameters validation;
  
  private static int getDefaultMParam(int lParam)
  {
    if (lParam == 0)
    {
      return 160;
    }
    
    return lParam < 160 ? lParam : 160;
  }
  


  public DHParameters(BigInteger p, BigInteger g)
  {
    this(p, g, null, 0);
  }
  



  public DHParameters(BigInteger p, BigInteger g, BigInteger q)
  {
    this(p, g, q, 0);
  }
  




  public DHParameters(BigInteger p, BigInteger g, BigInteger q, int l)
  {
    this(p, g, q, getDefaultMParam(l), l, null, null);
  }
  





  public DHParameters(BigInteger p, BigInteger g, BigInteger q, int m, int l)
  {
    this(p, g, q, m, l, null, null);
  }
  





  public DHParameters(BigInteger p, BigInteger g, BigInteger q, BigInteger j, DHValidationParameters validation)
  {
    this(p, g, q, 160, 0, j, validation);
  }
  







  public DHParameters(BigInteger p, BigInteger g, BigInteger q, int m, int l, BigInteger j, DHValidationParameters validation)
  {
    if (l != 0)
    {
      if (l > p.bitLength())
      {
        throw new IllegalArgumentException("when l value specified, it must satisfy 2^(l-1) <= p");
      }
      if (l < m)
      {
        throw new IllegalArgumentException("when l value specified, it may not be less than m value");
      }
    }
    
    this.g = g;
    this.p = p;
    this.q = q;
    this.m = m;
    this.l = l;
    this.j = j;
    this.validation = validation;
  }
  
  public BigInteger getP()
  {
    return p;
  }
  
  public BigInteger getG()
  {
    return g;
  }
  
  public BigInteger getQ()
  {
    return q;
  }
  





  public BigInteger getJ()
  {
    return j;
  }
  





  public int getM()
  {
    return m;
  }
  





  public int getL()
  {
    return l;
  }
  
  public DHValidationParameters getValidationParameters()
  {
    return validation;
  }
  

  public boolean equals(Object obj)
  {
    if (!(obj instanceof DHParameters))
    {
      return false;
    }
    
    DHParameters pm = (DHParameters)obj;
    
    if (getQ() != null)
    {
      if (!getQ().equals(pm.getQ()))
      {
        return false;
      }
      

    }
    else if (pm.getQ() != null)
    {
      return false;
    }
    

    return (pm.getP().equals(p)) && (pm.getG().equals(g));
  }
  
  public int hashCode()
  {
    return getP().hashCode() ^ getG().hashCode() ^ (getQ() != null ? getQ().hashCode() : 0);
  }
}
