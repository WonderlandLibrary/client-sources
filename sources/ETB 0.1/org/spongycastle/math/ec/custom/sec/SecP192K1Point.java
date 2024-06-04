package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractFp;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat192;








public class SecP192K1Point
  extends ECPoint.AbstractFp
{
  /**
   * @deprecated
   */
  public SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  












  /**
   * @deprecated
   */
  public SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  

  SecP192K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecP192K1Point(null, getAffineXCoord(), getAffineYCoord());
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
    
    SecP192K1FieldElement X1 = (SecP192K1FieldElement)x;SecP192K1FieldElement Y1 = (SecP192K1FieldElement)y;
    SecP192K1FieldElement X2 = (SecP192K1FieldElement)b.getXCoord();SecP192K1FieldElement Y2 = (SecP192K1FieldElement)b.getYCoord();
    
    SecP192K1FieldElement Z1 = (SecP192K1FieldElement)this.zs[0];
    SecP192K1FieldElement Z2 = (SecP192K1FieldElement)b.getZCoord(0);
    

    int[] tt1 = Nat192.createExt();
    int[] t2 = Nat192.create();
    int[] t3 = Nat192.create();
    int[] t4 = Nat192.create();
    
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
      SecP192K1Field.square(x, S2);
      
      U2 = t2;
      SecP192K1Field.multiply(S2, x, U2);
      
      SecP192K1Field.multiply(S2, x, S2);
      SecP192K1Field.multiply(S2, x, S2);
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
      SecP192K1Field.square(x, S1);
      
      U1 = tt1;
      SecP192K1Field.multiply(S1, x, U1);
      
      SecP192K1Field.multiply(S1, x, S1);
      SecP192K1Field.multiply(S1, x, S1);
    }
    
    int[] H = Nat192.create();
    SecP192K1Field.subtract(U1, U2, H);
    
    int[] R = t2;
    SecP192K1Field.subtract(S1, S2, R);
    

    if (Nat192.isZero(H))
    {
      if (Nat192.isZero(R))
      {

        return twice();
      }
      

      return curve.getInfinity();
    }
    
    int[] HSquared = t3;
    SecP192K1Field.square(H, HSquared);
    
    int[] G = Nat192.create();
    SecP192K1Field.multiply(HSquared, H, G);
    
    int[] V = t3;
    SecP192K1Field.multiply(HSquared, U1, V);
    
    SecP192K1Field.negate(G, G);
    Nat192.mul(S1, G, tt1);
    
    int c = Nat192.addBothTo(V, V, G);
    SecP192K1Field.reduce32(c, G);
    
    SecP192K1FieldElement X3 = new SecP192K1FieldElement(t4);
    SecP192K1Field.square(R, x);
    SecP192K1Field.subtract(x, G, x);
    
    SecP192K1FieldElement Y3 = new SecP192K1FieldElement(G);
    SecP192K1Field.subtract(V, x, x);
    SecP192K1Field.multiplyAddToExt(x, R, tt1);
    SecP192K1Field.reduce(tt1, x);
    
    SecP192K1FieldElement Z3 = new SecP192K1FieldElement(H);
    if (!Z1IsOne)
    {
      SecP192K1Field.multiply(x, x, x);
    }
    if (!Z2IsOne)
    {
      SecP192K1Field.multiply(x, x, x);
    }
    
    ECFieldElement[] zs = { Z3 };
    
    return new SecP192K1Point(curve, X3, Y3, zs, withCompression);
  }
  

  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    SecP192K1FieldElement Y1 = (SecP192K1FieldElement)y;
    if (Y1.isZero())
    {
      return curve.getInfinity();
    }
    
    SecP192K1FieldElement X1 = (SecP192K1FieldElement)x;SecP192K1FieldElement Z1 = (SecP192K1FieldElement)zs[0];
    


    int[] Y1Squared = Nat192.create();
    SecP192K1Field.square(x, Y1Squared);
    
    int[] T = Nat192.create();
    SecP192K1Field.square(Y1Squared, T);
    
    int[] M = Nat192.create();
    SecP192K1Field.square(x, M);
    int c = Nat192.addBothTo(M, M, M);
    SecP192K1Field.reduce32(c, M);
    
    int[] S = Y1Squared;
    SecP192K1Field.multiply(Y1Squared, x, S);
    c = Nat.shiftUpBits(6, S, 2, 0);
    SecP192K1Field.reduce32(c, S);
    
    int[] t1 = Nat192.create();
    c = Nat.shiftUpBits(6, T, 3, 0, t1);
    SecP192K1Field.reduce32(c, t1);
    
    SecP192K1FieldElement X3 = new SecP192K1FieldElement(T);
    SecP192K1Field.square(M, x);
    SecP192K1Field.subtract(x, S, x);
    SecP192K1Field.subtract(x, S, x);
    
    SecP192K1FieldElement Y3 = new SecP192K1FieldElement(S);
    SecP192K1Field.subtract(S, x, x);
    SecP192K1Field.multiply(x, M, x);
    SecP192K1Field.subtract(x, t1, x);
    
    SecP192K1FieldElement Z3 = new SecP192K1FieldElement(M);
    SecP192K1Field.twice(x, x);
    if (!Z1.isOne())
    {
      SecP192K1Field.multiply(x, x, x);
    }
    
    return new SecP192K1Point(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
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
  
  public ECPoint negate()
  {
    if (isInfinity())
    {
      return this;
    }
    
    return new SecP192K1Point(curve, x, y.negate(), zs, withCompression);
  }
}
