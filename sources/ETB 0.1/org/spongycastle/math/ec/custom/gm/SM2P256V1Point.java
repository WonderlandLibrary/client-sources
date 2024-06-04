package org.spongycastle.math.ec.custom.gm;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractFp;
import org.spongycastle.math.raw.Nat;
import org.spongycastle.math.raw.Nat256;








public class SM2P256V1Point
  extends ECPoint.AbstractFp
{
  /**
   * @deprecated
   */
  public SM2P256V1Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  












  /**
   * @deprecated
   */
  public SM2P256V1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  SM2P256V1Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new SM2P256V1Point(null, getAffineXCoord(), getAffineYCoord());
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
    
    SM2P256V1FieldElement X1 = (SM2P256V1FieldElement)x;SM2P256V1FieldElement Y1 = (SM2P256V1FieldElement)y;
    SM2P256V1FieldElement X2 = (SM2P256V1FieldElement)b.getXCoord();SM2P256V1FieldElement Y2 = (SM2P256V1FieldElement)b.getYCoord();
    
    SM2P256V1FieldElement Z1 = (SM2P256V1FieldElement)this.zs[0];
    SM2P256V1FieldElement Z2 = (SM2P256V1FieldElement)b.getZCoord(0);
    

    int[] tt1 = Nat256.createExt();
    int[] t2 = Nat256.create();
    int[] t3 = Nat256.create();
    int[] t4 = Nat256.create();
    
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
      SM2P256V1Field.square(x, S2);
      
      U2 = t2;
      SM2P256V1Field.multiply(S2, x, U2);
      
      SM2P256V1Field.multiply(S2, x, S2);
      SM2P256V1Field.multiply(S2, x, S2);
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
      SM2P256V1Field.square(x, S1);
      
      U1 = tt1;
      SM2P256V1Field.multiply(S1, x, U1);
      
      SM2P256V1Field.multiply(S1, x, S1);
      SM2P256V1Field.multiply(S1, x, S1);
    }
    
    int[] H = Nat256.create();
    SM2P256V1Field.subtract(U1, U2, H);
    
    int[] R = t2;
    SM2P256V1Field.subtract(S1, S2, R);
    

    if (Nat256.isZero(H))
    {
      if (Nat256.isZero(R))
      {

        return twice();
      }
      

      return curve.getInfinity();
    }
    
    int[] HSquared = t3;
    SM2P256V1Field.square(H, HSquared);
    
    int[] G = Nat256.create();
    SM2P256V1Field.multiply(HSquared, H, G);
    
    int[] V = t3;
    SM2P256V1Field.multiply(HSquared, U1, V);
    
    SM2P256V1Field.negate(G, G);
    Nat256.mul(S1, G, tt1);
    
    int c = Nat256.addBothTo(V, V, G);
    SM2P256V1Field.reduce32(c, G);
    
    SM2P256V1FieldElement X3 = new SM2P256V1FieldElement(t4);
    SM2P256V1Field.square(R, x);
    SM2P256V1Field.subtract(x, G, x);
    
    SM2P256V1FieldElement Y3 = new SM2P256V1FieldElement(G);
    SM2P256V1Field.subtract(V, x, x);
    SM2P256V1Field.multiplyAddToExt(x, R, tt1);
    SM2P256V1Field.reduce(tt1, x);
    
    SM2P256V1FieldElement Z3 = new SM2P256V1FieldElement(H);
    if (!Z1IsOne)
    {
      SM2P256V1Field.multiply(x, x, x);
    }
    if (!Z2IsOne)
    {
      SM2P256V1Field.multiply(x, x, x);
    }
    
    ECFieldElement[] zs = { Z3 };
    
    return new SM2P256V1Point(curve, X3, Y3, zs, withCompression);
  }
  
  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    SM2P256V1FieldElement Y1 = (SM2P256V1FieldElement)y;
    if (Y1.isZero())
    {
      return curve.getInfinity();
    }
    
    SM2P256V1FieldElement X1 = (SM2P256V1FieldElement)x;SM2P256V1FieldElement Z1 = (SM2P256V1FieldElement)zs[0];
    

    int[] t1 = Nat256.create();
    int[] t2 = Nat256.create();
    
    int[] Y1Squared = Nat256.create();
    SM2P256V1Field.square(x, Y1Squared);
    
    int[] T = Nat256.create();
    SM2P256V1Field.square(Y1Squared, T);
    
    boolean Z1IsOne = Z1.isOne();
    
    int[] Z1Squared = x;
    if (!Z1IsOne)
    {
      Z1Squared = t2;
      SM2P256V1Field.square(x, Z1Squared);
    }
    
    SM2P256V1Field.subtract(x, Z1Squared, t1);
    
    int[] M = t2;
    SM2P256V1Field.add(x, Z1Squared, M);
    SM2P256V1Field.multiply(M, t1, M);
    int c = Nat256.addBothTo(M, M, M);
    SM2P256V1Field.reduce32(c, M);
    
    int[] S = Y1Squared;
    SM2P256V1Field.multiply(Y1Squared, x, S);
    c = Nat.shiftUpBits(8, S, 2, 0);
    SM2P256V1Field.reduce32(c, S);
    
    c = Nat.shiftUpBits(8, T, 3, 0, t1);
    SM2P256V1Field.reduce32(c, t1);
    
    SM2P256V1FieldElement X3 = new SM2P256V1FieldElement(T);
    SM2P256V1Field.square(M, x);
    SM2P256V1Field.subtract(x, S, x);
    SM2P256V1Field.subtract(x, S, x);
    
    SM2P256V1FieldElement Y3 = new SM2P256V1FieldElement(S);
    SM2P256V1Field.subtract(S, x, x);
    SM2P256V1Field.multiply(x, M, x);
    SM2P256V1Field.subtract(x, t1, x);
    
    SM2P256V1FieldElement Z3 = new SM2P256V1FieldElement(M);
    SM2P256V1Field.twice(x, x);
    if (!Z1IsOne)
    {
      SM2P256V1Field.multiply(x, x, x);
    }
    
    return new SM2P256V1Point(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
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
    
    return new SM2P256V1Point(curve, x, y.negate(), zs, withCompression);
  }
}
