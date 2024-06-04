package org.spongycastle.math.ec.custom.sec;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractFp;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat160;








public class SecP160K1Point
  extends ECPoint.AbstractFp
{
  /**
   * @deprecated
   */
  public SecP160K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  












  /**
   * @deprecated
   */
  public SecP160K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  

  SecP160K1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SecP160K1Point(null, getAffineXCoord(), getAffineYCoord());
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
    
    SecP160R2FieldElement X1 = (SecP160R2FieldElement)x;SecP160R2FieldElement Y1 = (SecP160R2FieldElement)y;
    SecP160R2FieldElement X2 = (SecP160R2FieldElement)b.getXCoord();SecP160R2FieldElement Y2 = (SecP160R2FieldElement)b.getYCoord();
    
    SecP160R2FieldElement Z1 = (SecP160R2FieldElement)this.zs[0];
    SecP160R2FieldElement Z2 = (SecP160R2FieldElement)b.getZCoord(0);
    

    int[] tt1 = Nat160.createExt();
    int[] t2 = Nat160.create();
    int[] t3 = Nat160.create();
    int[] t4 = Nat160.create();
    
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
      SecP160R2Field.square(x, S2);
      
      U2 = t2;
      SecP160R2Field.multiply(S2, x, U2);
      
      SecP160R2Field.multiply(S2, x, S2);
      SecP160R2Field.multiply(S2, x, S2);
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
      SecP160R2Field.square(x, S1);
      
      U1 = tt1;
      SecP160R2Field.multiply(S1, x, U1);
      
      SecP160R2Field.multiply(S1, x, S1);
      SecP160R2Field.multiply(S1, x, S1);
    }
    
    int[] H = Nat160.create();
    SecP160R2Field.subtract(U1, U2, H);
    
    int[] R = t2;
    SecP160R2Field.subtract(S1, S2, R);
    

    if (Nat160.isZero(H))
    {
      if (Nat160.isZero(R))
      {

        return twice();
      }
      

      return curve.getInfinity();
    }
    
    int[] HSquared = t3;
    SecP160R2Field.square(H, HSquared);
    
    int[] G = Nat160.create();
    SecP160R2Field.multiply(HSquared, H, G);
    
    int[] V = t3;
    SecP160R2Field.multiply(HSquared, U1, V);
    
    SecP160R2Field.negate(G, G);
    Nat160.mul(S1, G, tt1);
    
    int c = Nat160.addBothTo(V, V, G);
    SecP160R2Field.reduce32(c, G);
    
    SecP160R2FieldElement X3 = new SecP160R2FieldElement(t4);
    SecP160R2Field.square(R, x);
    SecP160R2Field.subtract(x, G, x);
    
    SecP160R2FieldElement Y3 = new SecP160R2FieldElement(G);
    SecP160R2Field.subtract(V, x, x);
    SecP160R2Field.multiplyAddToExt(x, R, tt1);
    SecP160R2Field.reduce(tt1, x);
    
    SecP160R2FieldElement Z3 = new SecP160R2FieldElement(H);
    if (!Z1IsOne)
    {
      SecP160R2Field.multiply(x, x, x);
    }
    if (!Z2IsOne)
    {
      SecP160R2Field.multiply(x, x, x);
    }
    
    ECFieldElement[] zs = { Z3 };
    
    return new SecP160K1Point(curve, X3, Y3, zs, withCompression);
  }
  

  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    SecP160R2FieldElement Y1 = (SecP160R2FieldElement)y;
    if (Y1.isZero())
    {
      return curve.getInfinity();
    }
    
    SecP160R2FieldElement X1 = (SecP160R2FieldElement)x;SecP160R2FieldElement Z1 = (SecP160R2FieldElement)zs[0];
    


    int[] Y1Squared = Nat160.create();
    SecP160R2Field.square(x, Y1Squared);
    
    int[] T = Nat160.create();
    SecP160R2Field.square(Y1Squared, T);
    
    int[] M = Nat160.create();
    SecP160R2Field.square(x, M);
    int c = Nat160.addBothTo(M, M, M);
    SecP160R2Field.reduce32(c, M);
    
    int[] S = Y1Squared;
    SecP160R2Field.multiply(Y1Squared, x, S);
    c = Nat.shiftUpBits(5, S, 2, 0);
    SecP160R2Field.reduce32(c, S);
    
    int[] t1 = Nat160.create();
    c = Nat.shiftUpBits(5, T, 3, 0, t1);
    SecP160R2Field.reduce32(c, t1);
    
    SecP160R2FieldElement X3 = new SecP160R2FieldElement(T);
    SecP160R2Field.square(M, x);
    SecP160R2Field.subtract(x, S, x);
    SecP160R2Field.subtract(x, S, x);
    
    SecP160R2FieldElement Y3 = new SecP160R2FieldElement(S);
    SecP160R2Field.subtract(S, x, x);
    SecP160R2Field.multiply(x, M, x);
    SecP160R2Field.subtract(x, t1, x);
    
    SecP160R2FieldElement Z3 = new SecP160R2FieldElement(M);
    SecP160R2Field.twice(x, x);
    if (!Z1.isOne())
    {
      SecP160R2Field.multiply(x, x, x);
    }
    
    return new SecP160K1Point(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
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
    
    return new SecP160K1Point(curve, x, y.negate(), zs, withCompression);
  }
}
