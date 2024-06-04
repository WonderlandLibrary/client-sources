package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractF2m;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;


public class SecT163R2Curve
  extends ECCurve.AbstractF2m
{
  private static final int SecT163R2_DEFAULT_COORDS = 6;
  protected SecT163R2Point infinity;
  
  public SecT163R2Curve()
  {
    super(163, 3, 6, 7);
    
    infinity = new SecT163R2Point(this, null, null);
    
    a = fromBigInteger(BigInteger.valueOf(1L));
    b = fromBigInteger(new BigInteger(1, Hex.decode("020A601907B8C953CA1481EB10512F78744A3205FD")));
    order = new BigInteger(1, Hex.decode("040000000000000000000292FE77E70C12A4234C33"));
    cofactor = BigInteger.valueOf(2L);
    
    coord = 6;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SecT163R2Curve();
  }
  
  public boolean supportsCoordinateSystem(int coord)
  {
    switch (coord)
    {
    case 6: 
      return true;
    }
    return false;
  }
  

  public int getFieldSize()
  {
    return 163;
  }
  
  public ECFieldElement fromBigInteger(BigInteger x)
  {
    return new SecT163FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new SecT163R2Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SecT163R2Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
  
  public boolean isKoblitz()
  {
    return false;
  }
  
  public int getM()
  {
    return 163;
  }
  
  public boolean isTrinomial()
  {
    return false;
  }
  
  public int getK1()
  {
    return 3;
  }
  
  public int getK2()
  {
    return 6;
  }
  
  public int getK3()
  {
    return 7;
  }
}
