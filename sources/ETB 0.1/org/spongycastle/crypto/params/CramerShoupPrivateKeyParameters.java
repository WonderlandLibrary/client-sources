package org.spongycastle.crypto.params;

import java.math.BigInteger;

public class CramerShoupPrivateKeyParameters extends CramerShoupKeyParameters {
  private BigInteger x1;
  private BigInteger x2;
  private BigInteger y1;
  
  public CramerShoupPrivateKeyParameters(CramerShoupParameters params, BigInteger x1, BigInteger x2, BigInteger y1, BigInteger y2, BigInteger z) {
    super(true, params);
    
    this.x1 = x1;
    this.x2 = x2;
    this.y1 = y1;
    this.y2 = y2;
    this.z = z; }
  
  private BigInteger y2;
  
  public BigInteger getX1() { return x1; }
  
  private BigInteger z;
  private CramerShoupPublicKeyParameters pk;
  public BigInteger getX2() { return x2; }
  
  public BigInteger getY1()
  {
    return y1;
  }
  
  public BigInteger getY2() {
    return y2;
  }
  
  public BigInteger getZ() {
    return z;
  }
  
  public void setPk(CramerShoupPublicKeyParameters pk) {
    this.pk = pk;
  }
  
  public CramerShoupPublicKeyParameters getPk() {
    return pk;
  }
  
  public int hashCode() {
    return x1.hashCode() ^ x2.hashCode() ^ y1.hashCode() ^ y2.hashCode() ^ z.hashCode() ^ super.hashCode();
  }
  
  public boolean equals(Object obj) {
    if (!(obj instanceof CramerShoupPrivateKeyParameters)) {
      return false;
    }
    
    CramerShoupPrivateKeyParameters other = (CramerShoupPrivateKeyParameters)obj;
    
    return (other.getX1().equals(x1)) && (other.getX2().equals(x2)) && (other.getY1().equals(y1)) && (other.getY2().equals(y2)) && (other.getZ().equals(z)) && (super.equals(obj));
  }
}
