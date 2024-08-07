package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractF2m;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;


public class SecT193R1Curve
  extends ECCurve.AbstractF2m
{
  private static final int SecT193R1_DEFAULT_COORDS = 6;
  protected SecT193R1Point infinity;
  
  public SecT193R1Curve()
  {
    super(193, 15, 0, 0);
    
    infinity = new SecT193R1Point(this, null, null);
    
    a = fromBigInteger(new BigInteger(1, Hex.decode("0017858FEB7A98975169E171F77B4087DE098AC8A911DF7B01")));
    b = fromBigInteger(new BigInteger(1, Hex.decode("00FDFB49BFE6C3A89FACADAA7A1E5BBC7CC1C2E5D831478814")));
    order = new BigInteger(1, Hex.decode("01000000000000000000000000C7F34A778F443ACC920EBA49"));
    cofactor = BigInteger.valueOf(2L);
    
    coord = 6;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SecT193R1Curve();
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
    return 193;
  }
  
  public ECFieldElement fromBigInteger(BigInteger x)
  {
    return new SecT193FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new SecT193R1Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SecT193R1Point(this, x, y, zs, withCompression);
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
    return 193;
  }
  
  public boolean isTrinomial()
  {
    return true;
  }
  
  public int getK1()
  {
    return 15;
  }
  
  public int getK2()
  {
    return 0;
  }
  
  public int getK3()
  {
    return 0;
  }
}
