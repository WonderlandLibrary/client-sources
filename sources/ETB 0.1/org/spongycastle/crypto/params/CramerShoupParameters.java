package org.spongycastle.crypto.params;

import java.math.BigInteger;
import org.spongycastle.crypto.CipherParameters;
import org.spongycastle.crypto.Digest;

public class CramerShoupParameters implements CipherParameters
{
  private BigInteger p;
  private BigInteger g1;
  private BigInteger g2;
  private Digest H;
  
  public CramerShoupParameters(BigInteger p, BigInteger g1, BigInteger g2, Digest H)
  {
    this.p = p;
    this.g1 = g1;
    this.g2 = g2;
    this.H = H;
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof DSAParameters)) {
      return false;
    }
    
    CramerShoupParameters pm = (CramerShoupParameters)obj;
    
    return (pm.getP().equals(p)) && (pm.getG1().equals(g1)) && (pm.getG2().equals(g2));
  }
  
  public int hashCode() {
    return getP().hashCode() ^ getG1().hashCode() ^ getG2().hashCode();
  }
  
  public BigInteger getG1() {
    return g1;
  }
  
  public BigInteger getG2() {
    return g2;
  }
  
  public BigInteger getP() {
    return p;
  }
  
  public Digest getH() {
    H.reset();
    return H;
  }
}
