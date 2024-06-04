package org.spongycastle.math.ec.custom.djb;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractFp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.raw.Nat256;
import org.spongycastle.util.encoders.Hex;

public class Curve25519 extends ECCurve.AbstractFp
{
  public static final BigInteger q = Nat256.toBigInteger(Curve25519Field.P);
  
  private static final int Curve25519_DEFAULT_COORDS = 4;
  
  protected Curve25519Point infinity;
  
  public Curve25519()
  {
    super(q);
    
    infinity = new Curve25519Point(this, null, null);
    
    a = fromBigInteger(new BigInteger(1, 
      Hex.decode("2AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA984914A144")));
    b = fromBigInteger(new BigInteger(1, 
      Hex.decode("7B425ED097B425ED097B425ED097B425ED097B425ED097B4260B5E9C7710C864")));
    order = new BigInteger(1, Hex.decode("1000000000000000000000000000000014DEF9DEA2F79CD65812631A5CF5D3ED"));
    cofactor = BigInteger.valueOf(8L);
    
    coord = 4;
  }
  
  protected ECCurve cloneCurve()
  {
    return new Curve25519();
  }
  
  public boolean supportsCoordinateSystem(int coord)
  {
    switch (coord)
    {
    case 4: 
      return true;
    }
    return false;
  }
  

  public BigInteger getQ()
  {
    return q;
  }
  
  public int getFieldSize()
  {
    return q.bitLength();
  }
  
  public ECFieldElement fromBigInteger(BigInteger x)
  {
    return new Curve25519FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new Curve25519Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new Curve25519Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
}
