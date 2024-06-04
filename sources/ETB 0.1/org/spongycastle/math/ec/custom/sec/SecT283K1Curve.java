package org.spongycastle.math.ec.custom.sec;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractF2m;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECMultiplier;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.WTauNafMultiplier;
import org.spongycastle.util.encoders.Hex;


public class SecT283K1Curve
  extends ECCurve.AbstractF2m
{
  private static final int SecT283K1_DEFAULT_COORDS = 6;
  protected SecT283K1Point infinity;
  
  public SecT283K1Curve()
  {
    super(283, 5, 7, 12);
    
    infinity = new SecT283K1Point(this, null, null);
    
    a = fromBigInteger(BigInteger.valueOf(0L));
    b = fromBigInteger(BigInteger.valueOf(1L));
    order = new BigInteger(1, Hex.decode("01FFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFE9AE2ED07577265DFF7F94451E061E163C61"));
    cofactor = BigInteger.valueOf(4L);
    
    coord = 6;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SecT283K1Curve();
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
  

  protected ECMultiplier createDefaultMultiplier()
  {
    return new WTauNafMultiplier();
  }
  
  public int getFieldSize()
  {
    return 283;
  }
  
  public ECFieldElement fromBigInteger(BigInteger x)
  {
    return new SecT283FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new SecT283K1Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SecT283K1Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
  
  public boolean isKoblitz()
  {
    return true;
  }
  
  public int getM()
  {
    return 283;
  }
  
  public boolean isTrinomial()
  {
    return false;
  }
  
  public int getK1()
  {
    return 5;
  }
  
  public int getK2()
  {
    return 7;
  }
  
  public int getK3()
  {
    return 12;
  }
}
