package org.spongycastle.math.ec.custom.djb;

import org.spongycastle.math.ec.ECCurve;
import org.spongycastle.math.ec.ECFieldElement;
import org.spongycastle.math.ec.ECPoint;
import org.spongycastle.math.ec.ECPoint.AbstractFp;
import org.spongycastle.math.raw.Nat256;





public class Curve25519Point
  extends ECPoint.AbstractFp
{
  /**
   * @deprecated
   */
  public Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, false);
  }
  







  /**
   * @deprecated
   */
  public Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
  {
    super(curve, x, y);
    
    if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
    {
      throw new IllegalArgumentException("Exactly one of the field elements is null");
    }
    
    this.withCompression = withCompression;
  }
  
  Curve25519Point(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
  {
    super(curve, x, y, zs);
    
    this.withCompression = withCompression;
  }
  
  protected ECPoint detach()
  {
    return new Curve25519Point(null, getAffineXCoord(), getAffineYCoord());
  }
  
  public ECFieldElement getZCoord(int index)
  {
    if (index == 1)
    {
      return getJacobianModifiedW();
    }
    
    return super.getZCoord(index);
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
    
    Curve25519FieldElement X1 = (Curve25519FieldElement)x;Curve25519FieldElement Y1 = (Curve25519FieldElement)y;
    Curve25519FieldElement Z1 = (Curve25519FieldElement)this.zs[0];
    Curve25519FieldElement X2 = (Curve25519FieldElement)b.getXCoord();Curve25519FieldElement Y2 = (Curve25519FieldElement)b.getYCoord();
    Curve25519FieldElement Z2 = (Curve25519FieldElement)b.getZCoord(0);
    

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
      Curve25519Field.square(x, S2);
      
      U2 = t2;
      Curve25519Field.multiply(S2, x, U2);
      
      Curve25519Field.multiply(S2, x, S2);
      Curve25519Field.multiply(S2, x, S2);
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
      Curve25519Field.square(x, S1);
      
      U1 = tt1;
      Curve25519Field.multiply(S1, x, U1);
      
      Curve25519Field.multiply(S1, x, S1);
      Curve25519Field.multiply(S1, x, S1);
    }
    
    int[] H = Nat256.create();
    Curve25519Field.subtract(U1, U2, H);
    
    int[] R = t2;
    Curve25519Field.subtract(S1, S2, R);
    

    if (Nat256.isZero(H))
    {
      if (Nat256.isZero(R))
      {

        return twice();
      }
      

      return curve.getInfinity();
    }
    
    int[] HSquared = Nat256.create();
    Curve25519Field.square(H, HSquared);
    
    int[] G = Nat256.create();
    Curve25519Field.multiply(HSquared, H, G);
    
    int[] V = t3;
    Curve25519Field.multiply(HSquared, U1, V);
    
    Curve25519Field.negate(G, G);
    Nat256.mul(S1, G, tt1);
    
    int c = Nat256.addBothTo(V, V, G);
    Curve25519Field.reduce27(c, G);
    
    Curve25519FieldElement X3 = new Curve25519FieldElement(t4);
    Curve25519Field.square(R, x);
    Curve25519Field.subtract(x, G, x);
    
    Curve25519FieldElement Y3 = new Curve25519FieldElement(G);
    Curve25519Field.subtract(V, x, x);
    Curve25519Field.multiplyAddToExt(x, R, tt1);
    Curve25519Field.reduce(tt1, x);
    
    Curve25519FieldElement Z3 = new Curve25519FieldElement(H);
    if (!Z1IsOne)
    {
      Curve25519Field.multiply(x, x, x);
    }
    if (!Z2IsOne)
    {
      Curve25519Field.multiply(x, x, x);
    }
    
    int[] Z3Squared = (Z1IsOne) && (Z2IsOne) ? HSquared : null;
    

    Curve25519FieldElement W3 = calculateJacobianModifiedW(Z3, Z3Squared);
    
    ECFieldElement[] zs = { Z3, W3 };
    
    return new Curve25519Point(curve, X3, Y3, zs, withCompression);
  }
  
  public ECPoint twice()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECCurve curve = getCurve();
    
    ECFieldElement Y1 = y;
    if (Y1.isZero())
    {
      return curve.getInfinity();
    }
    
    return twiceJacobianModified(true);
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
    
    return twiceJacobianModified(false).add(b);
  }
  
  public ECPoint threeTimes()
  {
    if (isInfinity())
    {
      return this;
    }
    
    ECFieldElement Y1 = y;
    if (Y1.isZero())
    {
      return this;
    }
    
    return twiceJacobianModified(false).add(this);
  }
  
  public ECPoint negate()
  {
    if (isInfinity())
    {
      return this;
    }
    
    return new Curve25519Point(getCurve(), x, y.negate(), zs, withCompression);
  }
  
  protected Curve25519FieldElement calculateJacobianModifiedW(Curve25519FieldElement Z, int[] ZSquared)
  {
    Curve25519FieldElement a4 = (Curve25519FieldElement)getCurve().getA();
    if (Z.isOne())
    {
      return a4;
    }
    
    Curve25519FieldElement W = new Curve25519FieldElement();
    if (ZSquared == null)
    {
      ZSquared = x;
      Curve25519Field.square(x, ZSquared);
    }
    Curve25519Field.square(ZSquared, x);
    Curve25519Field.multiply(x, x, x);
    return W;
  }
  
  protected Curve25519FieldElement getJacobianModifiedW()
  {
    Curve25519FieldElement W = (Curve25519FieldElement)zs[1];
    if (W == null)
    {

      Curve25519FieldElement tmp33_30 = calculateJacobianModifiedW((Curve25519FieldElement)zs[0], null);W = tmp33_30;zs[1] = tmp33_30;
    }
    return W;
  }
  
  protected Curve25519Point twiceJacobianModified(boolean calculateW)
  {
    Curve25519FieldElement X1 = (Curve25519FieldElement)x;Curve25519FieldElement Y1 = (Curve25519FieldElement)y;
    Curve25519FieldElement Z1 = (Curve25519FieldElement)zs[0];Curve25519FieldElement W1 = getJacobianModifiedW();
    


    int[] M = Nat256.create();
    Curve25519Field.square(x, M);
    int c = Nat256.addBothTo(M, M, M);
    c += Nat256.addTo(x, M);
    Curve25519Field.reduce27(c, M);
    
    int[] _2Y1 = Nat256.create();
    Curve25519Field.twice(x, _2Y1);
    
    int[] _2Y1Squared = Nat256.create();
    Curve25519Field.multiply(_2Y1, x, _2Y1Squared);
    
    int[] S = Nat256.create();
    Curve25519Field.multiply(_2Y1Squared, x, S);
    Curve25519Field.twice(S, S);
    
    int[] _8T = Nat256.create();
    Curve25519Field.square(_2Y1Squared, _8T);
    Curve25519Field.twice(_8T, _8T);
    
    Curve25519FieldElement X3 = new Curve25519FieldElement(_2Y1Squared);
    Curve25519Field.square(M, x);
    Curve25519Field.subtract(x, S, x);
    Curve25519Field.subtract(x, S, x);
    
    Curve25519FieldElement Y3 = new Curve25519FieldElement(S);
    Curve25519Field.subtract(S, x, x);
    Curve25519Field.multiply(x, M, x);
    Curve25519Field.subtract(x, _8T, x);
    
    Curve25519FieldElement Z3 = new Curve25519FieldElement(_2Y1);
    if (!Nat256.isOne(x))
    {
      Curve25519Field.multiply(x, x, x);
    }
    
    Curve25519FieldElement W3 = null;
    if (calculateW)
    {
      W3 = new Curve25519FieldElement(_8T);
      Curve25519Field.multiply(x, x, x);
      Curve25519Field.twice(x, x);
    }
    
    return new Curve25519Point(getCurve(), X3, Y3, new ECFieldElement[] { Z3, W3 }, withCompression);
  }
}
