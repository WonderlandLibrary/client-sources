package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractFp;
import org.spongycastle.math.raw.Nat;








public class SecP521R1Point
  extends ECPoint.AbstractFp
{
  /**
   * @deprecated
   */
  public SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  












  /**
   * @deprecated
   */
  public SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  SecP521R1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecP521R1Point(null, getAffineXCoord(), getAffineYCoord());
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
    if (this == b)
    {
      return twice();
    }
    
    ECCurve curve = getCurve();
    
    SecP521R1FieldElement X1 = (SecP521R1FieldElement)x;SecP521R1FieldElement Y1 = (SecP521R1FieldElement)y;
    SecP521R1FieldElement X2 = (SecP521R1FieldElement)b.getXCoord();SecP521R1FieldElement Y2 = (SecP521R1FieldElement)b.getYCoord();
    
    SecP521R1FieldElement Z1 = (SecP521R1FieldElement)this.zs[0];
    SecP521R1FieldElement Z2 = (SecP521R1FieldElement)b.getZCoord(0);
    
    int[] t1 = Nat.create(17);
    int[] t2 = Nat.create(17);
    int[] t3 = Nat.create(17);
    int[] t4 = Nat.create(17);
    
    boolean Z1IsOne = Z1.isOne();
    int[] S2;
    int[] S2; int[] U2; if (Z1IsOne)
    {
      int[] U2 = x;
      S2 = x;
    }
    else
    {
      S2 = t3;
      SecP521R1Field.square(x, S2);
      
      U2 = t2;
      SecP521R1Field.multiply(S2, x, U2);
      
      SecP521R1Field.multiply(S2, x, S2);
      SecP521R1Field.multiply(S2, x, S2);
    }
    
    boolean Z2IsOne = Z2.isOne();
    int[] S1;
    int[] S1; int[] U1; if (Z2IsOne)
    {
      int[] U1 = x;
      S1 = x;
    }
    else
    {
      S1 = t4;
      SecP521R1Field.square(x, S1);
      
      U1 = t1;
      SecP521R1Field.multiply(S1, x, U1);
      
      SecP521R1Field.multiply(S1, x, S1);
      SecP521R1Field.multiply(S1, x, S1);
    }
    
    int[] H = Nat.create(17);
    SecP521R1Field.subtract(U1, U2, H);
    
    int[] R = t2;
    SecP521R1Field.subtract(S1, S2, R);
    

    if (Nat.isZero(17, H))
    {
      if (Nat.isZero(17, R))
      {

        return twice();
      }
      

      return curve.getInfinity();
    }
    
    int[] HSquared = t3;
    SecP521R1Field.square(H, HSquared);
    
    int[] G = Nat.create(17);
    SecP521R1Field.multiply(HSquared, H, G);
    
    int[] V = t3;
    SecP521R1Field.multiply(HSquared, U1, V);
    
    SecP521R1Field.multiply(S1, G, t1);
    
    SecP521R1FieldElement X3 = new SecP521R1FieldElement(t4);
    SecP521R1Field.square(R, x);
    SecP521R1Field.add(x, G, x);
    SecP521R1Field.subtract(x, V, x);
    SecP521R1Field.subtract(x, V, x);
    
    SecP521R1FieldElement Y3 = new SecP521R1FieldElement(G);
    SecP521R1Field.subtract(V, x, x);
    SecP521R1Field.multiply(x, R, t2);
    SecP521R1Field.subtract(t2, t1, x);
    
    SecP521R1FieldElement Z3 = new SecP521R1FieldElement(H);
    if (!Z1IsOne)
    {
      SecP521R1Field.multiply(x, x, x);
    }
    if (!Z2IsOne)
    {
      SecP521R1Field.multiply(x, x, x);
    }
    
    ECFieldElement[] zs = { Z3 };
    
    return new SecP521R1Point(curve, X3, Y3, zs, withCompression);
  }
  
  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    SecP521R1FieldElement Y1 = (SecP521R1FieldElement)y;
    if (Y1.isZero())
    {
      return curve.getInfinity();
    }
    
    SecP521R1FieldElement X1 = (SecP521R1FieldElement)x;SecP521R1FieldElement Z1 = (SecP521R1FieldElement)zs[0];
    
    int[] t1 = Nat.create(17);
    int[] t2 = Nat.create(17);
    
    int[] Y1Squared = Nat.create(17);
    SecP521R1Field.square(x, Y1Squared);
    
    int[] T = Nat.create(17);
    SecP521R1Field.square(Y1Squared, T);
    
    boolean Z1IsOne = Z1.isOne();
    
    int[] Z1Squared = x;
    if (!Z1IsOne)
    {
      Z1Squared = t2;
      SecP521R1Field.square(x, Z1Squared);
    }
    
    SecP521R1Field.subtract(x, Z1Squared, t1);
    
    int[] M = t2;
    SecP521R1Field.add(x, Z1Squared, M);
    SecP521R1Field.multiply(M, t1, M);
    Nat.addBothTo(17, M, M, M);
    SecP521R1Field.reduce23(M);
    
    int[] S = Y1Squared;
    SecP521R1Field.multiply(Y1Squared, x, S);
    Nat.shiftUpBits(17, S, 2, 0);
    SecP521R1Field.reduce23(S);
    
    Nat.shiftUpBits(17, T, 3, 0, t1);
    SecP521R1Field.reduce23(t1);
    
    SecP521R1FieldElement X3 = new SecP521R1FieldElement(T);
    SecP521R1Field.square(M, x);
    SecP521R1Field.subtract(x, S, x);
    SecP521R1Field.subtract(x, S, x);
    
    SecP521R1FieldElement Y3 = new SecP521R1FieldElement(S);
    SecP521R1Field.subtract(S, x, x);
    SecP521R1Field.multiply(x, M, x);
    SecP521R1Field.subtract(x, t1, x);
    
    SecP521R1FieldElement Z3 = new SecP521R1FieldElement(M);
    SecP521R1Field.twice(x, x);
    if (!Z1IsOne)
    {
      SecP521R1Field.multiply(x, x, x);
    }
    
    return new SecP521R1Point(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
  }
  
  public ECPoint twicePlus(ECPoint b)
  {
    if (this == b)
    {
      return threeTimes();
    }
    if (isInfinity())
    {
      return b;
    }
    if (b.isInfinity())
    {
      return twice();
    }
    
    ECFieldElement Y1 = y;
    if (Y1.isZero())
    {
      return b;
    }
    
    return twice().add(b);
  }
  
  public ECPoint threeTimes()
  {
    if ((isInfinity()) || (y.isZero()))
    {
      return this;
    }
    

    return twice().add(this);
  }
  
  protected ECFieldElement two(ECFieldElement x)
  {
    return x.add(x);
  }
  
  protected ECFieldElement three(ECFieldElement x)
  {
    return two(x).add(x);
  }
  
  protected ECFieldElement four(ECFieldElement x)
  {
    return two(two(x));
  }
  
  protected ECFieldElement eight(ECFieldElement x)
  {
    return four(two(x));
  }
  





  protected ECFieldElement doubleProductFromSquares(ECFieldElement a, ECFieldElement b, ECFieldElement aSquared, ECFieldElement bSquared)
  {
    return a.add(b).square().subtract(aSquared).subtract(bSquared);
  }
  
  public ECPoint negate()
  {
    if (isInfinity())
    {
      return this;
    }
    
    return new SecP521R1Point(curve, x, y.negate(), zs, withCompression);
  }
}
