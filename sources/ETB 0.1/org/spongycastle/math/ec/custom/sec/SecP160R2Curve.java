package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractFp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SecP160R2Curve extends ECCurve.AbstractFp
{
  public static final BigInteger q = new BigInteger(1, 
    Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC73"));
  
  private static final int SecP160R2_DEFAULT_COORDS = 2;
  
  protected SecP160R2Point infinity;
  
  public SecP160R2Curve()
  {
    super(q);
    
    infinity = new SecP160R2Point(this, null, null);
    
    a = fromBigInteger(new BigInteger(1, 
      Hex.decode("FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFEFFFFAC70")));
    b = fromBigInteger(new BigInteger(1, 
      Hex.decode("B4E134D3FB59EB8BAB57274904664D5AF50388BA")));
    order = new BigInteger(1, Hex.decode("0100000000000000000000351EE786A818F3A1A16B"));
    cofactor = BigInteger.valueOf(1L);
    
    coord = 2;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SecP160R2Curve();
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
    return new SecP160R2Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SecP160R2Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
}
