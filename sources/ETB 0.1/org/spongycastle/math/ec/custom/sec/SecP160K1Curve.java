package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractFp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP160K1Curve extends ECCurve.AbstractFp
{
  public static final BigInteger q = SecP160R2Curve.q;
  
  private static final int SECP160K1_DEFAULT_COORDS = 2;
  
  protected SecP160K1Point infinity;
  
  public SecP160K1Curve()
  {
    super(q);
    
    infinity = new SecP160K1Point(this, null, null);
    
    a = fromBigInteger(ECConstants.ZERO);
    b = fromBigInteger(BigInteger.valueOf(7L));
    order = new BigInteger(1, Hex.decode("0100000000000000000001B8FA16DFAB9ACA16B6B3"));
    cofactor = BigInteger.valueOf(1L);
    coord = 2;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SecP160K1Curve();
  }
  
  public boolean supportsCoordinateSystem(int coord)
  {
    switch (coord)
    {
    case 2: 
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
    return new SecP160R2FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new SecP160K1Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SecP160K1Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
}
