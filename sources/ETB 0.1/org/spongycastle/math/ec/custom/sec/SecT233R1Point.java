package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractF2m;

public class SecT233R1Point extends ECPoint.AbstractF2m
{
  /**
   * @deprecated
   */
  public SecT233R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  
  /**
   * @deprecated
   */
  public SecT233R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  SecT233R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecT233R1Point(null, getAffineXCoord(), getAffineYCoord());
  }
  
  public ECFieldElement getYCoord()
  {
    ECFieldElement X = x;ECFieldElement L = y;
    
    if ((isInfinity()) || (X.isZero()))
    {
      return L;
    }
    

    ECFieldElement Y = L.add(X).multiply(X);
    
    ECFieldElement Z = zs[0];
    if (!Z.isOne())
    {
      Y = Y.divide(Z);
    }
    
    return Y;
  }
  
  protected boolean getCompressionYTilde()
  {
    ECFieldElement X = getRawXCoord();
    if (X.isZero())
    {
      return false;
    }
    
    ECFieldElement Y = getRawYCoord();
    

    return Y.testBitZero() != X.testBitZero();
  }
  
  public ECPoint add(ECPoint b)
  {
    if (isInfinity())
    {
      return b;
    }
    if (b.isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    ECFieldElement X1 = x;
    ECFieldElement X2 = b.getRawXCoord();
    
    if (X1.isZero())
    {
      if (X2.isZero())
      {
        return curve.getInfinity();
      }
      
      return b.add(this);
    }
    
    ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
    ECFieldElement L2 = b.getRawYCoord();ECFieldElement Z2 = b.getZCoord(0);
    
    boolean Z1IsOne = Z1.isOne();
    ECFieldElement U2 = X2;ECFieldElement S2 = L2;
    if (!Z1IsOne)
    {
      U2 = U2.multiply(Z1);
      S2 = S2.multiply(Z1);
    }
    
    boolean Z2IsOne = Z2.isOne();
    ECFieldElement U1 = X1;ECFieldElement S1 = L1;
    if (!Z2IsOne)
    {
      U1 = U1.multiply(Z2);
      S1 = S1.multiply(Z2);
    }
    
    ECFieldElement A = S1.add(S2);
    ECFieldElement B = U1.add(U2);
    
    if (B.isZero())
    {
      if (A.isZero())
      {
        return twice();
      }
      
      return curve.getInfinity(); }
    ECFieldElement Z3;
    ECFieldElement X3;
    ECFieldElement L3;
    ECFieldElement Z3; if (X2.isZero())
    {

      ECPoint p = normalize();
      X1 = p.getXCoord();
      ECFieldElement Y1 = p.getYCoord();
      
      ECFieldElement Y2 = L2;
      ECFieldElement L = Y1.add(Y2).divide(X1);
      
      ECFieldElement X3 = L.square().add(L).add(X1).addOne();
      if (X3.isZero())
      {
        return new SecT233R1Point(curve, X3, curve.getB().sqrt(), withCompression);
      }
      
      ECFieldElement Y3 = L.multiply(X1.add(X3)).add(X3).add(Y1);
      ECFieldElement L3 = Y3.divide(X3).add(X3);
      Z3 = curve.fromBigInteger(ECConstants.ONE);
    }
    else
    {
      B = B.square();
      
      ECFieldElement AU1 = A.multiply(U1);
      ECFieldElement AU2 = A.multiply(U2);
      
      X3 = AU1.multiply(AU2);
      if (X3.isZero())
      {
        return new SecT233R1Point(curve, X3, curve.getB().sqrt(), withCompression);
      }
      
      ECFieldElement ABZ2 = A.multiply(B);
      if (!Z2IsOne)
      {
        ABZ2 = ABZ2.multiply(Z2);
      }
      
      L3 = AU2.add(B).squarePlusProduct(ABZ2, L1.add(Z1));
      
      Z3 = ABZ2;
      if (!Z1IsOne)
      {
        Z3 = Z3.multiply(Z1);
      }
    }
    
    return new SecT233R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
  }
  
  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    ECFieldElement X1 = x;
    if (X1.isZero())
    {

      return curve.getInfinity();
    }
    
    ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
    
    boolean Z1IsOne = Z1.isOne();
    ECFieldElement L1Z1 = Z1IsOne ? L1 : L1.multiply(Z1);
    ECFieldElement Z1Sq = Z1IsOne ? Z1 : Z1.square();
    ECFieldElement T = L1.square().add(L1Z1).add(Z1Sq);
    if (T.isZero())
    {
      return new SecT233R1Point(curve, T, curve.getB().sqrt(), withCompression);
    }
    
    ECFieldElement X3 = T.square();
    ECFieldElement Z3 = Z1IsOne ? T : T.multiply(Z1Sq);
    
    ECFieldElement X1Z1 = Z1IsOne ? X1 : X1.multiply(Z1);
    ECFieldElement L3 = X1Z1.squarePlusProduct(T, L1Z1).add(X3).add(Z3);
    
    return new SecT233R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
  }
  
  public ECPoint twicePlus(ECPoint b)
  {
    if (isInfinity())
    {
      return b;
    }
    if (b.isInfinity())
    {
      return twice();
    }
    
    ECCurve curve = getCurve();
    
    ECFieldElement X1 = x;
    if (X1.isZero())
    {

      return b;
    }
    
    ECFieldElement X2 = b.getRawXCoord();ECFieldElement Z2 = b.getZCoord(0);
    if ((X2.isZero()) || (!Z2.isOne()))
    {
      return twice().add(b);
    }
    
    ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
    ECFieldElement L2 = b.getRawYCoord();
    
    ECFieldElement X1Sq = X1.square();
    ECFieldElement L1Sq = L1.square();
    ECFieldElement Z1Sq = Z1.square();
    ECFieldElement L1Z1 = L1.multiply(Z1);
    
    ECFieldElement T = Z1Sq.add(L1Sq).add(L1Z1);
    ECFieldElement A = L2.multiply(Z1Sq).add(L1Sq).multiplyPlusProduct(T, X1Sq, Z1Sq);
    ECFieldElement X2Z1Sq = X2.multiply(Z1Sq);
    ECFieldElement B = X2Z1Sq.add(T).square();
    
    if (B.isZero())
    {
      if (A.isZero())
      {
        return b.twice();
      }
      
      return curve.getInfinity();
    }
    
    if (A.isZero())
    {
      return new SecT233R1Point(curve, A, curve.getB().sqrt(), withCompression);
    }
    
    ECFieldElement X3 = A.square().multiply(X2Z1Sq);
    ECFieldElement Z3 = A.multiply(B).multiply(Z1Sq);
    ECFieldElement L3 = A.add(B).square().multiplyPlusProduct(T, L2.addOne(), Z3);
    
    return new SecT233R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
  }
  
  public ECPoint negate()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECFieldElement X = x;
    if (X.isZero())
    {
      return this;
    }
    

    ECFieldElement L = y;ECFieldElement Z = zs[0];
    return new SecT233R1Point(curve, X, L.add(Z), new ECFieldElement[] { Z }, withCompression);
  }
}
