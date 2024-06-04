package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECConstants;
import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractF2m;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat576;

public class SecT571R1Point extends ECPoint.AbstractF2m
{
  /**
   * @deprecated
   */
  public SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  
  /**
   * @deprecated
   */
  public SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  SecT571R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecT571R1Point(null, getAffineXCoord(), getAffineYCoord());
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
      
      SecT571FieldElement X3 = (SecT571FieldElement)L.square().add(L).add(X1).addOne();
      if (X3.isZero())
      {
        return new SecT571R1Point(curve, X3, SecT571R1Curve.SecT571R1_B_SQRT, withCompression);
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
        return new SecT571R1Point(curve, X3, SecT571R1Curve.SecT571R1_B_SQRT, withCompression);
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
    
    return new SecT571R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
  }
  
  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    SecT571FieldElement X1 = (SecT571FieldElement)x;
    if (X1.isZero())
    {

      return curve.getInfinity();
    }
    
    SecT571FieldElement L1 = (SecT571FieldElement)y;SecT571FieldElement Z1 = (SecT571FieldElement)zs[0];
    
    long[] t1 = Nat576.create64();
    long[] t2 = Nat576.create64();
    
    long[] Z1Precomp = Z1.isOne() ? null : SecT571Field.precompMultiplicand(x);
    long[] Z1Sq;
    long[] L1Z1; long[] Z1Sq; if (Z1Precomp == null)
    {
      long[] L1Z1 = x;
      Z1Sq = x;
    }
    else
    {
      SecT571Field.multiplyPrecomp(x, Z1Precomp, L1Z1 = t1);
      SecT571Field.square(x, Z1Sq = t2);
    }
    
    long[] T = Nat576.create64();
    SecT571Field.square(x, T);
    SecT571Field.addBothTo(L1Z1, Z1Sq, T);
    
    if (Nat576.isZero64(T))
    {
      return new SecT571R1Point(curve, new SecT571FieldElement(T), SecT571R1Curve.SecT571R1_B_SQRT, withCompression);
    }
    
    long[] tt = Nat576.createExt64();
    SecT571Field.multiplyAddToExt(T, L1Z1, tt);
    
    SecT571FieldElement X3 = new SecT571FieldElement(t1);
    SecT571Field.square(T, x);
    
    SecT571FieldElement Z3 = new SecT571FieldElement(T);
    if (Z1Precomp != null)
    {
      SecT571Field.multiply(x, Z1Sq, x);
    }
    long[] X1Z1;
    long[] X1Z1;
    if (Z1Precomp == null)
    {
      X1Z1 = x;
    }
    else
    {
      SecT571Field.multiplyPrecomp(x, Z1Precomp, X1Z1 = t2);
    }
    
    SecT571Field.squareAddToExt(X1Z1, tt);
    SecT571Field.reduce(tt, t2);
    SecT571Field.addBothTo(x, x, t2);
    SecT571FieldElement L3 = new SecT571FieldElement(t2);
    
    return new SecT571R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
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
    
    SecT571FieldElement X1 = (SecT571FieldElement)x;
    if (X1.isZero())
    {

      return b;
    }
    
    SecT571FieldElement X2 = (SecT571FieldElement)b.getRawXCoord();SecT571FieldElement Z2 = (SecT571FieldElement)b.getZCoord(0);
    if ((X2.isZero()) || (!Z2.isOne()))
    {
      return twice().add(b);
    }
    
    SecT571FieldElement L1 = (SecT571FieldElement)y;SecT571FieldElement Z1 = (SecT571FieldElement)zs[0];
    SecT571FieldElement L2 = (SecT571FieldElement)b.getRawYCoord();
    
    long[] t1 = Nat576.create64();
    long[] t2 = Nat576.create64();
    long[] t3 = Nat576.create64();
    long[] t4 = Nat576.create64();
    
    long[] X1Sq = t1;
    SecT571Field.square(x, X1Sq);
    
    long[] L1Sq = t2;
    SecT571Field.square(x, L1Sq);
    
    long[] Z1Sq = t3;
    SecT571Field.square(x, Z1Sq);
    
    long[] L1Z1 = t4;
    SecT571Field.multiply(x, x, L1Z1);
    
    long[] T = L1Z1;
    SecT571Field.addBothTo(Z1Sq, L1Sq, T);
    
    long[] Z1SqPrecomp = SecT571Field.precompMultiplicand(Z1Sq);
    
    long[] A = t3;
    SecT571Field.multiplyPrecomp(x, Z1SqPrecomp, A);
    SecT571Field.add(A, L1Sq, A);
    
    long[] tt = Nat576.createExt64();
    SecT571Field.multiplyAddToExt(A, T, tt);
    SecT571Field.multiplyPrecompAddToExt(X1Sq, Z1SqPrecomp, tt);
    SecT571Field.reduce(tt, A);
    
    long[] X2Z1Sq = t1;
    SecT571Field.multiplyPrecomp(x, Z1SqPrecomp, X2Z1Sq);
    
    long[] B = t2;
    SecT571Field.add(X2Z1Sq, T, B);
    SecT571Field.square(B, B);
    
    if (Nat576.isZero64(B))
    {
      if (Nat576.isZero64(A))
      {
        return b.twice();
      }
      
      return curve.getInfinity();
    }
    
    if (Nat576.isZero64(A))
    {
      return new SecT571R1Point(curve, new SecT571FieldElement(A), SecT571R1Curve.SecT571R1_B_SQRT, withCompression);
    }
    
    SecT571FieldElement X3 = new SecT571FieldElement();
    SecT571Field.square(A, x);
    SecT571Field.multiply(x, X2Z1Sq, x);
    
    SecT571FieldElement Z3 = new SecT571FieldElement(t1);
    SecT571Field.multiply(A, B, x);
    SecT571Field.multiplyPrecomp(x, Z1SqPrecomp, x);
    
    SecT571FieldElement L3 = new SecT571FieldElement(t2);
    SecT571Field.add(A, B, x);
    SecT571Field.square(x, x);
    
    Nat.zero64(18, tt);
    SecT571Field.multiplyAddToExt(x, T, tt);
    SecT571Field.addOne(x, t4);
    SecT571Field.multiplyAddToExt(t4, x, tt);
    SecT571Field.reduce(tt, x);
    
    return new SecT571R1Point(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
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
    return new SecT571R1Point(curve, X, L.add(Z), new ECFieldElement[] { Z }, withCompression);
  }
}
