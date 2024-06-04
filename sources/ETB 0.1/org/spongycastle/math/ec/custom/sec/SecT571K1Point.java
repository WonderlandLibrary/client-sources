package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractF2m;
import org.spongycastle.math.raw.Nat576;

public class SecT571K1Point extends ECPoint.AbstractF2m
{
  /**
   * @deprecated
   */
  public SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  
  /**
   * @deprecated
   */
  public SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  SecT571K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecT571K1Point(null, getAffineXCoord(), getAffineYCoord());
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
    
    SecT571FieldElement X1 = (SecT571FieldElement)x;
    SecT571FieldElement X2 = (SecT571FieldElement)b.getRawXCoord();
    
    if (X1.isZero())
    {
      if (X2.isZero())
      {
        return curve.getInfinity();
      }
      
      return b.add(this);
    }
    
    SecT571FieldElement L1 = (SecT571FieldElement)y;SecT571FieldElement Z1 = (SecT571FieldElement)zs[0];
    SecT571FieldElement L2 = (SecT571FieldElement)b.getRawYCoord();SecT571FieldElement Z2 = (SecT571FieldElement)b.getZCoord(0);
    
    long[] t1 = Nat576.create64();
    long[] t2 = Nat576.create64();
    long[] t3 = Nat576.create64();
    long[] t4 = Nat576.create64();
    
    long[] Z1Precomp = Z1.isOne() ? null : SecT571Field.precompMultiplicand(x);
    long[] S2;
    long[] U2; long[] S2; if (Z1Precomp == null)
    {
      long[] U2 = x;
      S2 = x;
    }
    else
    {
      SecT571Field.multiplyPrecomp(x, Z1Precomp, U2 = t2);
      SecT571Field.multiplyPrecomp(x, Z1Precomp, S2 = t4);
    }
    
    long[] Z2Precomp = Z2.isOne() ? null : SecT571Field.precompMultiplicand(x);
    long[] S1;
    long[] U1; long[] S1; if (Z2Precomp == null)
    {
      long[] U1 = x;
      S1 = x;
    }
    else
    {
      SecT571Field.multiplyPrecomp(x, Z2Precomp, U1 = t1);
      SecT571Field.multiplyPrecomp(x, Z2Precomp, S1 = t3);
    }
    
    long[] A = t3;
    SecT571Field.add(S1, S2, A);
    
    long[] B = t4;
    SecT571Field.add(U1, U2, B);
    
    if (Nat576.isZero64(B))
    {
      if (Nat576.isZero64(A))
      {
        return twice();
      }
      
      return curve.getInfinity(); }
    SecT571FieldElement Z3;
    SecT571FieldElement X3;
    SecT571FieldElement Z3;
    SecT571FieldElement L3; if (X2.isZero())
    {

      ECPoint p = normalize();
      X1 = (SecT571FieldElement)p.getXCoord();
      ECFieldElement Y1 = p.getYCoord();
      
      ECFieldElement Y2 = L2;
      ECFieldElement L = Y1.add(Y2).divide(X1);
      
      SecT571FieldElement X3 = (SecT571FieldElement)L.square().add(L).add(X1);
      if (X3.isZero())
      {
        return new SecT571K1Point(curve, X3, curve.getB(), withCompression);
      }
      
      ECFieldElement Y3 = L.multiply(X1.add(X3)).add(X3).add(Y1);
      SecT571FieldElement L3 = (SecT571FieldElement)Y3.divide(X3).add(X3);
      Z3 = (SecT571FieldElement)curve.fromBigInteger(ECConstants.ONE);
    }
    else
    {
      SecT571Field.square(B, B);
      
      long[] APrecomp = SecT571Field.precompMultiplicand(A);
      
      long[] AU1 = t1;
      long[] AU2 = t2;
      
      SecT571Field.multiplyPrecomp(U1, APrecomp, AU1);
      SecT571Field.multiplyPrecomp(U2, APrecomp, AU2);
      
      X3 = new SecT571FieldElement(t1);
      SecT571Field.multiply(AU1, AU2, x);
      
      if (X3.isZero())
      {
        return new SecT571K1Point(curve, X3, curve.getB(), withCompression);
      }
      
      Z3 = new SecT571FieldElement(t3);
      SecT571Field.multiplyPrecomp(B, APrecomp, x);
      
      if (Z2Precomp != null)
      {
        SecT571Field.multiplyPrecomp(x, Z2Precomp, x);
      }
      
      long[] tt = Nat576.createExt64();
      
      SecT571Field.add(AU2, B, t4);
      SecT571Field.squareAddToExt(t4, tt);
      
      SecT571Field.add(x, x, t4);
      SecT571Field.multiplyAddToExt(t4, x, tt);
      
      L3 = new SecT571FieldElement(t4);
      SecT571Field.reduce(tt, x);
      
      if (Z1Precomp != null)
      {
        SecT571Field.multiplyPrecomp(x, Z1Precomp, x);
      }
    }
    
    return new SecT571K1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
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
    ECFieldElement Z1Sq = Z1IsOne ? Z1 : Z1.square();
    ECFieldElement T;
    ECFieldElement T; if (Z1IsOne)
    {
      T = L1.square().add(L1);
    }
    else
    {
      T = L1.add(Z1).multiply(L1);
    }
    
    if (T.isZero())
    {
      return new SecT571K1Point(curve, T, curve.getB(), withCompression);
    }
    
    ECFieldElement X3 = T.square();
    ECFieldElement Z3 = Z1IsOne ? T : T.multiply(Z1Sq);
    
    ECFieldElement t1 = L1.add(X1).square();
    ECFieldElement t2 = Z1IsOne ? Z1 : Z1Sq.square();
    ECFieldElement L3 = t1.add(T).add(Z1Sq).multiply(t1).add(t2).add(X3).add(Z3);
    
    return new SecT571K1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
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
    
    ECFieldElement T = L1Sq.add(L1Z1);
    ECFieldElement L2plus1 = L2.addOne();
    ECFieldElement A = L2plus1.multiply(Z1Sq).add(L1Sq).multiplyPlusProduct(T, X1Sq, Z1Sq);
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
      return new SecT571K1Point(curve, A, curve.getB(), withCompression);
    }
    
    ECFieldElement X3 = A.square().multiply(X2Z1Sq);
    ECFieldElement Z3 = A.multiply(B).multiply(Z1Sq);
    ECFieldElement L3 = A.add(B).square().multiplyPlusProduct(T, L2plus1, Z3);
    
    return new SecT571K1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
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
    return new SecT571K1Point(curve, X, L.add(Z), new ECFieldElement[] { Z }, withCompression);
  }
}
