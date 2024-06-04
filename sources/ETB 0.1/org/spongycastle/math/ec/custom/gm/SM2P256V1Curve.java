package org.spongycastle.math.ec.custom.gm;

import java.math.BigInteger;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECCurve.AbstractFp;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.util.encoders.Hex;

public class SM2P256V1Curve extends ECCurve.AbstractFp
{
  public static final BigInteger q = new BigInteger(1, 
    Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFF"));
  
  private static final int SM2P256V1_DEFAULT_COORDS = 2;
  
  protected SM2P256V1Point infinity;
  
  public SM2P256V1Curve()
  {
    super(q);
    
    infinity = new SM2P256V1Point(this, null, null);
    
    a = fromBigInteger(new BigInteger(1, 
      Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFFF00000000FFFFFFFFFFFFFFFC")));
    b = fromBigInteger(new BigInteger(1, 
      Hex.decode("28E9FA9E9D9F5E344D5A9E4BCF6509A7F39789F515AB8F92DDBCBD414D940E93")));
    order = new BigInteger(1, Hex.decode("FFFFFFFEFFFFFFFFFFFFFFFFFFFFFFFF7203DF6B21C6052B53BBF40939D54123"));
    cofactor = BigInteger.valueOf(1L);
    
    coord = 2;
  }
  
  protected ECCurve cloneCurve()
  {
    return new SM2P256V1Curve();
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
    return new SM2P256V1FieldElement(x);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    return new SM2P256V1Point(this, x, y, withCompression);
  }
  
  protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    return new SM2P256V1Point(this, x, y, zs, withCompression);
  }
  
  public ECPoint getInfinity()
  {
    return infinity;
  }
}
