package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Hashtable;




public abstract class ECPoint
{
  protected static ECFieldElement[] EMPTY_ZS = new ECFieldElement[0];
  protected ECCurve curve;
  protected ECFieldElement x;
  
  protected static ECFieldElement[] getInitialZCoords(ECCurve curve) {
    int coord = null == curve ? 0 : curve.getCoordinateSystem();
    
    switch (coord)
    {
    case 0: 
    case 5: 
      return EMPTY_ZS;
    }
    
    

    ECFieldElement one = curve.fromBigInteger(ECConstants.ONE);
    
    switch (coord)
    {
    case 1: 
    case 2: 
    case 6: 
      return new ECFieldElement[] { one };
    case 3: 
      return new ECFieldElement[] { one, one, one };
    case 4: 
      return new ECFieldElement[] { one, curve.getA() };
    }
    throw new IllegalArgumentException("unknown coordinate system");
  }
  


  protected ECFieldElement y;
  

  protected ECFieldElement[] zs;
  
  protected boolean withCompression;
  
  protected Hashtable preCompTable = null;
  
  protected ECPoint(ECCurve curve, ECFieldElement x, ECFieldElement y)
  {
    this(curve, x, y, getInitialZCoords(curve));
  }
  
  protected ECPoint(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs)
  {
    this.curve = curve;
    this.x = x;
    this.y = y;
    this.zs = zs;
  }
  
  protected boolean satisfiesCofactor()
  {
    BigInteger h = curve.getCofactor();
    return (h == null) || (h.equals(ECConstants.ONE)) || (!ECAlgorithms.referenceMultiply(this, h).isInfinity());
  }
  
  protected abstract boolean satisfiesCurveEquation();
  
  public final ECPoint getDetachedPoint()
  {
    return normalize().detach();
  }
  
  public ECCurve getCurve()
  {
    return curve;
  }
  

  protected abstract ECPoint detach();
  
  protected int getCurveCoordinateSystem()
  {
    return null == curve ? 0 : curve.getCoordinateSystem();
  }
  





  /**
   * @deprecated
   */
  public ECFieldElement getX()
  {
    return normalize().getXCoord();
  }
  






  /**
   * @deprecated
   */
  public ECFieldElement getY()
  {
    return normalize().getYCoord();
  }
  






  public ECFieldElement getAffineXCoord()
  {
    checkNormalized();
    return getXCoord();
  }
  






  public ECFieldElement getAffineYCoord()
  {
    checkNormalized();
    return getYCoord();
  }
  










  public ECFieldElement getXCoord()
  {
    return x;
  }
  










  public ECFieldElement getYCoord()
  {
    return y;
  }
  
  public ECFieldElement getZCoord(int index)
  {
    return (index < 0) || (index >= zs.length) ? null : zs[index];
  }
  
  public ECFieldElement[] getZCoords()
  {
    int zsLen = zs.length;
    if (zsLen == 0)
    {
      return EMPTY_ZS;
    }
    ECFieldElement[] copy = new ECFieldElement[zsLen];
    System.arraycopy(zs, 0, copy, 0, zsLen);
    return copy;
  }
  
  public final ECFieldElement getRawXCoord()
  {
    return x;
  }
  
  public final ECFieldElement getRawYCoord()
  {
    return y;
  }
  
  protected final ECFieldElement[] getRawZCoords()
  {
    return zs;
  }
  
  protected void checkNormalized()
  {
    if (!isNormalized())
    {
      throw new IllegalStateException("point not in normal form");
    }
  }
  
  public boolean isNormalized()
  {
    int coord = getCurveCoordinateSystem();
    
    return (coord == 0) || (coord == 5) || 
    
      (isInfinity()) || 
      (zs[0].isOne());
  }
  






  public ECPoint normalize()
  {
    if (isInfinity())
    {
      return this;
    }
    
    switch (getCurveCoordinateSystem())
    {

    case 0: 
    case 5: 
      return this;
    }
    
    
    ECFieldElement Z1 = getZCoord(0);
    if (Z1.isOne())
    {
      return this;
    }
    
    return normalize(Z1.invert());
  }
  


  ECPoint normalize(ECFieldElement zInv)
  {
    switch (getCurveCoordinateSystem())
    {

    case 1: 
    case 6: 
      return createScaledPoint(zInv, zInv);
    

    case 2: 
    case 3: 
    case 4: 
      ECFieldElement zInv2 = zInv.square();ECFieldElement zInv3 = zInv2.multiply(zInv);
      return createScaledPoint(zInv2, zInv3);
    }
    
    
    throw new IllegalStateException("not a projective coordinate system");
  }
  


  protected ECPoint createScaledPoint(ECFieldElement sx, ECFieldElement sy)
  {
    return getCurve().createRawPoint(getRawXCoord().multiply(sx), getRawYCoord().multiply(sy), withCompression);
  }
  
  public boolean isInfinity()
  {
    return (x == null) || (y == null) || ((zs.length > 0) && (zs[0].isZero()));
  }
  
  /**
   * @deprecated
   */
  public boolean isCompressed()
  {
    return withCompression;
  }
  
  public boolean isValid()
  {
    if (isInfinity())
    {
      return true;
    }
    


    ECCurve curve = getCurve();
    if (curve != null)
    {
      if (!satisfiesCurveEquation())
      {
        return false;
      }
      
      if (!satisfiesCofactor())
      {
        return false;
      }
    }
    
    return true;
  }
  
  public ECPoint scaleX(ECFieldElement scale)
  {
    return isInfinity() ? this : 
    
      getCurve().createRawPoint(getRawXCoord().multiply(scale), getRawYCoord(), getRawZCoords(), withCompression);
  }
  
  public ECPoint scaleY(ECFieldElement scale)
  {
    return isInfinity() ? this : 
    
      getCurve().createRawPoint(getRawXCoord(), getRawYCoord().multiply(scale), getRawZCoords(), withCompression);
  }
  
  public boolean equals(ECPoint other)
  {
    if (null == other)
    {
      return false;
    }
    
    ECCurve c1 = getCurve();ECCurve c2 = other.getCurve();
    boolean n1 = null == c1;boolean n2 = null == c2;
    boolean i1 = isInfinity();boolean i2 = other.isInfinity();
    
    if ((i1) || (i2))
    {
      return (i1) && (i2) && ((n1) || (n2) || (c1.equals(c2)));
    }
    
    ECPoint p1 = this;ECPoint p2 = other;
    if ((!n1) || (!n2))
    {


      if (n1)
      {
        p2 = p2.normalize();
      }
      else if (n2)
      {
        p1 = p1.normalize();
      } else {
        if (!c1.equals(c2))
        {
          return false;
        }
        



        ECPoint[] points = { this, c1.importPoint(p2) };
        

        c1.normalizeAll(points);
        
        p1 = points[0];
        p2 = points[1];
      }
    }
    return (p1.getXCoord().equals(p2.getXCoord())) && (p1.getYCoord().equals(p2.getYCoord()));
  }
  
  public boolean equals(Object other)
  {
    if (other == this)
    {
      return true;
    }
    
    if (!(other instanceof ECPoint))
    {
      return false;
    }
    
    return equals((ECPoint)other);
  }
  
  public int hashCode()
  {
    ECCurve c = getCurve();
    int hc = null == c ? 0 : c.hashCode() ^ 0xFFFFFFFF;
    
    if (!isInfinity())
    {


      ECPoint p = normalize();
      
      hc ^= p.getXCoord().hashCode() * 17;
      hc ^= p.getYCoord().hashCode() * 257;
    }
    
    return hc;
  }
  
  public String toString()
  {
    if (isInfinity())
    {
      return "INF";
    }
    
    StringBuffer sb = new StringBuffer();
    sb.append('(');
    sb.append(getRawXCoord());
    sb.append(',');
    sb.append(getRawYCoord());
    for (int i = 0; i < zs.length; i++)
    {
      sb.append(',');
      sb.append(zs[i]);
    }
    sb.append(')');
    return sb.toString();
  }
  
  /**
   * @deprecated
   */
  public byte[] getEncoded()
  {
    return getEncoded(withCompression);
  }
  






  public byte[] getEncoded(boolean compressed)
  {
    if (isInfinity())
    {
      return new byte[1];
    }
    
    ECPoint normed = normalize();
    
    byte[] X = normed.getXCoord().getEncoded();
    
    if (compressed)
    {
      byte[] PO = new byte[X.length + 1];
      PO[0] = ((byte)(normed.getCompressionYTilde() ? 3 : 2));
      System.arraycopy(X, 0, PO, 1, X.length);
      return PO;
    }
    
    byte[] Y = normed.getYCoord().getEncoded();
    
    byte[] PO = new byte[X.length + Y.length + 1];
    PO[0] = 4;
    System.arraycopy(X, 0, PO, 1, X.length);
    System.arraycopy(Y, 0, PO, X.length + 1, Y.length);
    return PO;
  }
  
  protected abstract boolean getCompressionYTilde();
  
  public abstract ECPoint add(ECPoint paramECPoint);
  
  public abstract ECPoint negate();
  
  public abstract ECPoint subtract(ECPoint paramECPoint);
  
  public ECPoint timesPow2(int e)
  {
    if (e < 0)
    {
      throw new IllegalArgumentException("'e' cannot be negative");
    }
    
    ECPoint p = this;
    for (;;) { e--; if (e < 0)
        break;
      p = p.twice();
    }
    return p;
  }
  
  public abstract ECPoint twice();
  
  public ECPoint twicePlus(ECPoint b)
  {
    return twice().add(b);
  }
  
  public ECPoint threeTimes()
  {
    return twicePlus(this);
  }
  





  public ECPoint multiply(BigInteger k)
  {
    return getCurve().getMultiplier().multiply(this, k);
  }
  
  public static abstract class AbstractFp extends ECPoint
  {
    protected AbstractFp(ECCurve curve, ECFieldElement x, ECFieldElement y)
    {
      super(x, y);
    }
    
    protected AbstractFp(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs)
    {
      super(x, y, zs);
    }
    
    protected boolean getCompressionYTilde()
    {
      return getAffineYCoord().testBitZero();
    }
    
    protected boolean satisfiesCurveEquation()
    {
      ECFieldElement X = x;ECFieldElement Y = y;ECFieldElement A = curve.getA();ECFieldElement B = curve.getB();
      ECFieldElement lhs = Y.square();
      
      switch (getCurveCoordinateSystem())
      {
      case 0: 
        break;
      
      case 1: 
        ECFieldElement Z = zs[0];
        if (!Z.isOne())
        {
          ECFieldElement Z2 = Z.square();ECFieldElement Z3 = Z.multiply(Z2);
          lhs = lhs.multiply(Z);
          A = A.multiply(Z2);
          B = B.multiply(Z3); }
        break;
      


      case 2: 
      case 3: 
      case 4: 
        ECFieldElement Z = zs[0];
        if (!Z.isOne())
        {
          ECFieldElement Z2 = Z.square();ECFieldElement Z4 = Z2.square();ECFieldElement Z6 = Z2.multiply(Z4);
          A = A.multiply(Z4);
          B = B.multiply(Z6); }
        break;
      

      default: 
        throw new IllegalStateException("unsupported coordinate system");
      }
      
      ECFieldElement rhs = X.square().add(A).multiply(X).add(B);
      return lhs.equals(rhs);
    }
    
    public ECPoint subtract(ECPoint b)
    {
      if (b.isInfinity())
      {
        return this;
      }
      

      return add(b.negate());
    }
  }
  








  public static class Fp
    extends ECPoint.AbstractFp
  {
    /**
     * @deprecated
     */
    public Fp(ECCurve curve, ECFieldElement x, ECFieldElement y)
    {
      this(curve, x, y, false);
    }
    







    /**
     * @deprecated
     */
    public Fp(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
    {
      super(x, y);
      
      if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
      {
        throw new IllegalArgumentException("Exactly one of the field elements is null");
      }
      
      this.withCompression = withCompression;
    }
    
    Fp(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
    {
      super(x, y, zs);
      
      this.withCompression = withCompression;
    }
    
    protected ECPoint detach()
    {
      return new Fp(null, getAffineXCoord(), getAffineYCoord());
    }
    
    public ECFieldElement getZCoord(int index)
    {
      if ((index == 1) && (4 == getCurveCoordinateSystem()))
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
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement X1 = x;ECFieldElement Y1 = y;
      ECFieldElement X2 = x;ECFieldElement Y2 = y;
      
      switch (coord)
      {

      case 0: 
        ECFieldElement dx = X2.subtract(X1);ECFieldElement dy = Y2.subtract(Y1);
        
        if (dx.isZero())
        {
          if (dy.isZero())
          {

            return twice();
          }
          

          return curve.getInfinity();
        }
        
        ECFieldElement gamma = dy.divide(dx);
        ECFieldElement X3 = gamma.square().subtract(X1).subtract(X2);
        ECFieldElement Y3 = gamma.multiply(X1.subtract(X3)).subtract(Y1);
        
        return new Fp(curve, X3, Y3, withCompression);
      


      case 1: 
        ECFieldElement Z1 = this.zs[0];
        ECFieldElement Z2 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        boolean Z2IsOne = Z2.isOne();
        
        ECFieldElement u1 = Z1IsOne ? Y2 : Y2.multiply(Z1);
        ECFieldElement u2 = Z2IsOne ? Y1 : Y1.multiply(Z2);
        ECFieldElement u = u1.subtract(u2);
        ECFieldElement v1 = Z1IsOne ? X2 : X2.multiply(Z1);
        ECFieldElement v2 = Z2IsOne ? X1 : X1.multiply(Z2);
        ECFieldElement v = v1.subtract(v2);
        

        if (v.isZero())
        {
          if (u.isZero())
          {

            return twice();
          }
          

          return curve.getInfinity();
        }
        

        ECFieldElement w = Z2IsOne ? Z1 : Z1IsOne ? Z2 : Z1.multiply(Z2);
        ECFieldElement vSquared = v.square();
        ECFieldElement vCubed = vSquared.multiply(v);
        ECFieldElement vSquaredV2 = vSquared.multiply(v2);
        ECFieldElement A = u.square().multiply(w).subtract(vCubed).subtract(two(vSquaredV2));
        
        ECFieldElement X3 = v.multiply(A);
        ECFieldElement Y3 = vSquaredV2.subtract(A).multiplyMinusProduct(u, u2, vCubed);
        ECFieldElement Z3 = vCubed.multiply(w);
        
        return new Fp(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
      


      case 2: 
      case 4: 
        ECFieldElement Z1 = this.zs[0];
        ECFieldElement Z2 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        
        ECFieldElement Z3Squared = null;
        ECFieldElement X3;
        ECFieldElement Y3; ECFieldElement Z3; if ((!Z1IsOne) && (Z1.equals(Z2)))
        {


          ECFieldElement dx = X1.subtract(X2);ECFieldElement dy = Y1.subtract(Y2);
          if (dx.isZero())
          {
            if (dy.isZero())
            {
              return twice();
            }
            return curve.getInfinity();
          }
          
          ECFieldElement C = dx.square();
          ECFieldElement W1 = X1.multiply(C);ECFieldElement W2 = X2.multiply(C);
          ECFieldElement A1 = W1.subtract(W2).multiply(Y1);
          
          ECFieldElement X3 = dy.square().subtract(W1).subtract(W2);
          ECFieldElement Y3 = W1.subtract(X3).multiply(dy).subtract(A1);
          ECFieldElement Z3 = dx;
          
          Z3 = Z3.multiply(Z1);
        } else {
          ECFieldElement S2;
          ECFieldElement U2;
          ECFieldElement S2;
          if (Z1IsOne)
          {
            ECFieldElement Z1Squared = Z1;ECFieldElement U2 = X2;S2 = Y2;
          }
          else
          {
            ECFieldElement Z1Squared = Z1.square();
            U2 = Z1Squared.multiply(X2);
            ECFieldElement Z1Cubed = Z1Squared.multiply(Z1);
            S2 = Z1Cubed.multiply(Y2);
          }
          
          boolean Z2IsOne = Z2.isOne();
          ECFieldElement S1;
          ECFieldElement U1; ECFieldElement S1; if (Z2IsOne)
          {
            ECFieldElement Z2Squared = Z2;ECFieldElement U1 = X1;S1 = Y1;
          }
          else
          {
            ECFieldElement Z2Squared = Z2.square();
            U1 = Z2Squared.multiply(X1);
            ECFieldElement Z2Cubed = Z2Squared.multiply(Z2);
            S1 = Z2Cubed.multiply(Y1);
          }
          
          ECFieldElement H = U1.subtract(U2);
          ECFieldElement R = S1.subtract(S2);
          

          if (H.isZero())
          {
            if (R.isZero())
            {

              return twice();
            }
            

            return curve.getInfinity();
          }
          
          ECFieldElement HSquared = H.square();
          ECFieldElement G = HSquared.multiply(H);
          ECFieldElement V = HSquared.multiply(U1);
          
          X3 = R.square().add(G).subtract(two(V));
          Y3 = V.subtract(X3).multiplyMinusProduct(R, G, S1);
          
          Z3 = H;
          if (!Z1IsOne)
          {
            Z3 = Z3.multiply(Z1);
          }
          if (!Z2IsOne)
          {
            Z3 = Z3.multiply(Z2);
          }
          





          if (Z3 == H)
          {
            Z3Squared = HSquared;
          }
        }
        ECFieldElement[] zs;
        ECFieldElement[] zs;
        if (coord == 4)
        {

          ECFieldElement W3 = calculateJacobianModifiedW(Z3, Z3Squared);
          
          zs = new ECFieldElement[] { Z3, W3 };
        }
        else
        {
          zs = new ECFieldElement[] { Z3 };
        }
        
        return new Fp(curve, X3, Y3, zs, withCompression);
      }
      
      

      throw new IllegalStateException("unsupported coordinate system");
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
      
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement X1 = x;
      
      switch (coord)
      {

      case 0: 
        ECFieldElement X1Squared = X1.square();
        ECFieldElement gamma = three(X1Squared).add(getCurve().getA()).divide(two(Y1));
        ECFieldElement X3 = gamma.square().subtract(two(X1));
        ECFieldElement Y3 = gamma.multiply(X1.subtract(X3)).subtract(Y1);
        
        return new Fp(curve, X3, Y3, withCompression);
      


      case 1: 
        ECFieldElement Z1 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        

        ECFieldElement w = curve.getA();
        if ((!w.isZero()) && (!Z1IsOne))
        {
          w = w.multiply(Z1.square());
        }
        w = w.add(three(X1.square()));
        
        ECFieldElement s = Z1IsOne ? Y1 : Y1.multiply(Z1);
        ECFieldElement t = Z1IsOne ? Y1.square() : s.multiply(Y1);
        ECFieldElement B = X1.multiply(t);
        ECFieldElement _4B = four(B);
        ECFieldElement h = w.square().subtract(two(_4B));
        
        ECFieldElement _2s = two(s);
        ECFieldElement X3 = h.multiply(_2s);
        ECFieldElement _2t = two(t);
        ECFieldElement Y3 = _4B.subtract(h).multiply(w).subtract(two(_2t.square()));
        ECFieldElement _4sSquared = Z1IsOne ? two(_2t) : _2s.square();
        ECFieldElement Z3 = two(_4sSquared).multiply(s);
        
        return new Fp(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
      


      case 2: 
        ECFieldElement Z1 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        
        ECFieldElement Y1Squared = Y1.square();
        ECFieldElement T = Y1Squared.square();
        
        ECFieldElement a4 = curve.getA();
        ECFieldElement a4Neg = a4.negate();
        ECFieldElement S;
        ECFieldElement M;
        ECFieldElement S; if (a4Neg.toBigInteger().equals(BigInteger.valueOf(3L)))
        {
          ECFieldElement Z1Squared = Z1IsOne ? Z1 : Z1.square();
          ECFieldElement M = three(X1.add(Z1Squared).multiply(X1.subtract(Z1Squared)));
          S = four(Y1Squared.multiply(X1));
        }
        else
        {
          ECFieldElement X1Squared = X1.square();
          M = three(X1Squared);
          if (Z1IsOne)
          {
            M = M.add(a4);
          }
          else if (!a4.isZero())
          {
            ECFieldElement Z1Squared = Z1.square();
            ECFieldElement Z1Pow4 = Z1Squared.square();
            if (a4Neg.bitLength() < a4.bitLength())
            {
              M = M.subtract(Z1Pow4.multiply(a4Neg));
            }
            else
            {
              M = M.add(Z1Pow4.multiply(a4));
            }
          }
          
          S = four(X1.multiply(Y1Squared));
        }
        
        ECFieldElement X3 = M.square().subtract(two(S));
        ECFieldElement Y3 = S.subtract(X3).multiply(M).subtract(eight(T));
        
        ECFieldElement Z3 = two(Y1);
        if (!Z1IsOne)
        {
          Z3 = Z3.multiply(Z1);
        }
        



        return new Fp(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
      


      case 4: 
        return twiceJacobianModified(true);
      }
      
      

      throw new IllegalStateException("unsupported coordinate system");
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
      
      ECCurve curve = getCurve();
      int coord = curve.getCoordinateSystem();
      
      switch (coord)
      {

      case 0: 
        ECFieldElement X1 = x;
        ECFieldElement X2 = x;ECFieldElement Y2 = y;
        
        ECFieldElement dx = X2.subtract(X1);ECFieldElement dy = Y2.subtract(Y1);
        
        if (dx.isZero())
        {
          if (dy.isZero())
          {

            return threeTimes();
          }
          

          return this;
        }
        





        ECFieldElement X = dx.square();ECFieldElement Y = dy.square();
        ECFieldElement d = X.multiply(two(X1).add(X2)).subtract(Y);
        if (d.isZero())
        {
          return curve.getInfinity();
        }
        
        ECFieldElement D = d.multiply(dx);
        ECFieldElement I = D.invert();
        ECFieldElement L1 = d.multiply(I).multiply(dy);
        ECFieldElement L2 = two(Y1).multiply(X).multiply(dx).multiply(I).subtract(L1);
        ECFieldElement X4 = L2.subtract(L1).multiply(L1.add(L2)).add(X2);
        ECFieldElement Y4 = X1.subtract(X4).multiply(L2).subtract(Y1);
        
        return new Fp(curve, X4, Y4, withCompression);
      

      case 4: 
        return twiceJacobianModified(false).add(b);
      }
      
      
      return twice().add(b);
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
      
      ECCurve curve = getCurve();
      int coord = curve.getCoordinateSystem();
      
      switch (coord)
      {

      case 0: 
        ECFieldElement X1 = x;
        
        ECFieldElement _2Y1 = two(Y1);
        ECFieldElement X = _2Y1.square();
        ECFieldElement Z = three(X1.square()).add(getCurve().getA());
        ECFieldElement Y = Z.square();
        
        ECFieldElement d = three(X1).multiply(X).subtract(Y);
        if (d.isZero())
        {
          return getCurve().getInfinity();
        }
        
        ECFieldElement D = d.multiply(_2Y1);
        ECFieldElement I = D.invert();
        ECFieldElement L1 = d.multiply(I).multiply(Z);
        ECFieldElement L2 = X.square().multiply(I).subtract(L1);
        
        ECFieldElement X4 = L2.subtract(L1).multiply(L1.add(L2)).add(X1);
        ECFieldElement Y4 = X1.subtract(X4).multiply(L2).subtract(Y1);
        return new Fp(curve, X4, Y4, withCompression);
      

      case 4: 
        return twiceJacobianModified(false).add(this);
      }
      
      

      return twice().add(this);
    }
    


    public ECPoint timesPow2(int e)
    {
      if (e < 0)
      {
        throw new IllegalArgumentException("'e' cannot be negative");
      }
      if ((e == 0) || (isInfinity()))
      {
        return this;
      }
      if (e == 1)
      {
        return twice();
      }
      
      ECCurve curve = getCurve();
      
      ECFieldElement Y1 = y;
      if (Y1.isZero())
      {
        return curve.getInfinity();
      }
      
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement W1 = curve.getA();
      ECFieldElement X1 = x;
      ECFieldElement Z1 = zs.length < 1 ? curve.fromBigInteger(ECConstants.ONE) : zs[0];
      
      if (!Z1.isOne())
      {
        switch (coord)
        {
        case 0: 
          break;
        case 1: 
          ECFieldElement Z1Sq = Z1.square();
          X1 = X1.multiply(Z1);
          Y1 = Y1.multiply(Z1Sq);
          W1 = calculateJacobianModifiedW(Z1, Z1Sq);
          break;
        case 2: 
          W1 = calculateJacobianModifiedW(Z1, null);
          break;
        case 4: 
          W1 = getJacobianModifiedW();
          break;
        case 3: default: 
          throw new IllegalStateException("unsupported coordinate system");
        }
        
      }
      for (int i = 0; i < e; i++)
      {
        if (Y1.isZero())
        {
          return curve.getInfinity();
        }
        
        ECFieldElement X1Squared = X1.square();
        ECFieldElement M = three(X1Squared);
        ECFieldElement _2Y1 = two(Y1);
        ECFieldElement _2Y1Squared = _2Y1.multiply(Y1);
        ECFieldElement S = two(X1.multiply(_2Y1Squared));
        ECFieldElement _4T = _2Y1Squared.square();
        ECFieldElement _8T = two(_4T);
        
        if (!W1.isZero())
        {
          M = M.add(W1);
          W1 = two(_8T.multiply(W1));
        }
        
        X1 = M.square().subtract(two(S));
        Y1 = M.multiply(S.subtract(X1)).subtract(_8T);
        Z1 = Z1.isOne() ? _2Y1 : _2Y1.multiply(Z1);
      }
      
      switch (coord)
      {
      case 0: 
        ECFieldElement zInv = Z1.invert();ECFieldElement zInv2 = zInv.square();ECFieldElement zInv3 = zInv2.multiply(zInv);
        return new Fp(curve, X1.multiply(zInv2), Y1.multiply(zInv3), withCompression);
      case 1: 
        X1 = X1.multiply(Z1);
        Z1 = Z1.multiply(Z1.square());
        return new Fp(curve, X1, Y1, new ECFieldElement[] { Z1 }, withCompression);
      case 2: 
        return new Fp(curve, X1, Y1, new ECFieldElement[] { Z1 }, withCompression);
      case 4: 
        return new Fp(curve, X1, Y1, new ECFieldElement[] { Z1, W1 }, withCompression);
      }
      throw new IllegalStateException("unsupported coordinate system");
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
      
      ECCurve curve = getCurve();
      int coord = curve.getCoordinateSystem();
      
      if (0 != coord)
      {
        return new Fp(curve, x, y.negate(), zs, withCompression);
      }
      
      return new Fp(curve, x, y.negate(), withCompression);
    }
    
    protected ECFieldElement calculateJacobianModifiedW(ECFieldElement Z, ECFieldElement ZSquared)
    {
      ECFieldElement a4 = getCurve().getA();
      if ((a4.isZero()) || (Z.isOne()))
      {
        return a4;
      }
      
      if (ZSquared == null)
      {
        ZSquared = Z.square();
      }
      
      ECFieldElement W = ZSquared.square();
      ECFieldElement a4Neg = a4.negate();
      if (a4Neg.bitLength() < a4.bitLength())
      {
        W = W.multiply(a4Neg).negate();
      }
      else
      {
        W = W.multiply(a4);
      }
      return W;
    }
    
    protected ECFieldElement getJacobianModifiedW()
    {
      ECFieldElement W = zs[1];
      if (W == null)
      {

        ECFieldElement tmp27_24 = calculateJacobianModifiedW(zs[0], null);W = tmp27_24;zs[1] = tmp27_24;
      }
      return W;
    }
    
    protected Fp twiceJacobianModified(boolean calculateW)
    {
      ECFieldElement X1 = x;ECFieldElement Y1 = y;ECFieldElement Z1 = zs[0];ECFieldElement W1 = getJacobianModifiedW();
      
      ECFieldElement X1Squared = X1.square();
      ECFieldElement M = three(X1Squared).add(W1);
      ECFieldElement _2Y1 = two(Y1);
      ECFieldElement _2Y1Squared = _2Y1.multiply(Y1);
      ECFieldElement S = two(X1.multiply(_2Y1Squared));
      ECFieldElement X3 = M.square().subtract(two(S));
      ECFieldElement _4T = _2Y1Squared.square();
      ECFieldElement _8T = two(_4T);
      ECFieldElement Y3 = M.multiply(S.subtract(X3)).subtract(_8T);
      ECFieldElement W3 = calculateW ? two(_8T.multiply(W1)) : null;
      ECFieldElement Z3 = Z1.isOne() ? _2Y1 : _2Y1.multiply(Z1);
      
      return new Fp(getCurve(), X3, Y3, new ECFieldElement[] { Z3, W3 }, withCompression);
    }
  }
  
  public static abstract class AbstractF2m extends ECPoint
  {
    protected AbstractF2m(ECCurve curve, ECFieldElement x, ECFieldElement y)
    {
      super(x, y);
    }
    
    protected AbstractF2m(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs)
    {
      super(x, y, zs);
    }
    
    protected boolean satisfiesCurveEquation()
    {
      ECCurve curve = getCurve();
      ECFieldElement X = x;ECFieldElement A = curve.getA();ECFieldElement B = curve.getB();
      
      int coord = curve.getCoordinateSystem();
      if (coord == 6)
      {
        ECFieldElement Z = zs[0];
        boolean ZIsOne = Z.isOne();
        
        if (X.isZero())
        {

          ECFieldElement Y = y;
          ECFieldElement lhs = Y.square();ECFieldElement rhs = B;
          if (!ZIsOne)
          {
            rhs = rhs.multiply(Z.square());
          }
          return lhs.equals(rhs);
        }
        
        ECFieldElement L = y;ECFieldElement X2 = X.square();
        ECFieldElement rhs;
        ECFieldElement rhs; if (ZIsOne)
        {
          ECFieldElement lhs = L.square().add(L).add(A);
          rhs = X2.square().add(B);
        }
        else
        {
          ECFieldElement Z2 = Z.square();ECFieldElement Z4 = Z2.square();
          lhs = L.add(Z).multiplyPlusProduct(L, A, Z2);
          
          rhs = X2.squarePlusProduct(B, Z4);
        }
        ECFieldElement lhs = lhs.multiply(X2);
        return lhs.equals(rhs);
      }
      
      ECFieldElement Y = y;
      ECFieldElement lhs = Y.add(X).multiply(Y);
      
      switch (coord)
      {
      case 0: 
        break;
      
      case 1: 
        ECFieldElement Z = zs[0];
        if (!Z.isOne())
        {
          ECFieldElement Z2 = Z.square();ECFieldElement Z3 = Z.multiply(Z2);
          lhs = lhs.multiply(Z);
          A = A.multiply(Z);
          B = B.multiply(Z3); }
        break;
      

      default: 
        throw new IllegalStateException("unsupported coordinate system");
      }
      
      ECFieldElement rhs = X.add(A).multiply(X.square()).add(B);
      return lhs.equals(rhs);
    }
    
    public ECPoint scaleX(ECFieldElement scale)
    {
      if (isInfinity())
      {
        return this;
      }
      
      int coord = getCurveCoordinateSystem();
      
      switch (coord)
      {


      case 5: 
        ECFieldElement X = getRawXCoord();ECFieldElement L = getRawYCoord();
        
        ECFieldElement X2 = X.multiply(scale);
        ECFieldElement L2 = L.add(X).divide(scale).add(X2);
        
        return getCurve().createRawPoint(X, L2, getRawZCoords(), withCompression);
      


      case 6: 
        ECFieldElement X = getRawXCoord();ECFieldElement L = getRawYCoord();ECFieldElement Z = getRawZCoords()[0];
        

        ECFieldElement X2 = X.multiply(scale.square());
        ECFieldElement L2 = L.add(X).add(X2);
        ECFieldElement Z2 = Z.multiply(scale);
        
        return getCurve().createRawPoint(X2, L2, new ECFieldElement[] { Z2 }, withCompression);
      }
      
      
      return super.scaleX(scale);
    }
    


    public ECPoint scaleY(ECFieldElement scale)
    {
      if (isInfinity())
      {
        return this;
      }
      
      int coord = getCurveCoordinateSystem();
      
      switch (coord)
      {

      case 5: 
      case 6: 
        ECFieldElement X = getRawXCoord();ECFieldElement L = getRawYCoord();
        

        ECFieldElement L2 = L.add(X).multiply(scale).add(X);
        
        return getCurve().createRawPoint(X, L2, getRawZCoords(), withCompression);
      }
      
      
      return super.scaleY(scale);
    }
    


    public ECPoint subtract(ECPoint b)
    {
      if (b.isInfinity())
      {
        return this;
      }
      

      return add(b.negate());
    }
    
    public AbstractF2m tau()
    {
      if (isInfinity())
      {
        return this;
      }
      
      ECCurve curve = getCurve();
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement X1 = x;
      
      switch (coord)
      {

      case 0: 
      case 5: 
        ECFieldElement Y1 = y;
        return (AbstractF2m)curve.createRawPoint(X1.square(), Y1.square(), withCompression);
      

      case 1: 
      case 6: 
        ECFieldElement Y1 = y;ECFieldElement Z1 = zs[0];
        return (AbstractF2m)curve.createRawPoint(X1.square(), Y1.square(), new ECFieldElement[] {Z1
          .square() }, withCompression);
      }
      
      
      throw new IllegalStateException("unsupported coordinate system");
    }
    


    public AbstractF2m tauPow(int pow)
    {
      if (isInfinity())
      {
        return this;
      }
      
      ECCurve curve = getCurve();
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement X1 = x;
      
      switch (coord)
      {

      case 0: 
      case 5: 
        ECFieldElement Y1 = y;
        return (AbstractF2m)curve.createRawPoint(X1.squarePow(pow), Y1.squarePow(pow), withCompression);
      

      case 1: 
      case 6: 
        ECFieldElement Y1 = y;ECFieldElement Z1 = zs[0];
        return (AbstractF2m)curve.createRawPoint(X1.squarePow(pow), Y1.squarePow(pow), new ECFieldElement[] {Z1
          .squarePow(pow) }, withCompression);
      }
      
      
      throw new IllegalStateException("unsupported coordinate system");
    }
  }
  








  public static class F2m
    extends ECPoint.AbstractF2m
  {
    /**
     * @deprecated
     */
    public F2m(ECCurve curve, ECFieldElement x, ECFieldElement y)
    {
      this(curve, x, y, false);
    }
    





    /**
     * @deprecated
     */
    public F2m(ECCurve curve, ECFieldElement x, ECFieldElement y, boolean withCompression)
    {
      super(x, y);
      
      if ((x == null ? 1 : 0) != (y == null ? 1 : 0))
      {
        throw new IllegalArgumentException("Exactly one of the field elements is null");
      }
      
      if (x != null)
      {

        ECFieldElement.F2m.checkFieldElements(this.x, this.y);
        

        if (curve != null)
        {
          ECFieldElement.F2m.checkFieldElements(this.x, this.curve.getA());
        }
      }
      
      this.withCompression = withCompression;
    }
    


    F2m(ECCurve curve, ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
    {
      super(x, y, zs);
      
      this.withCompression = withCompression;
    }
    


    protected ECPoint detach()
    {
      return new F2m(null, getAffineXCoord(), getAffineYCoord());
    }
    
    public ECFieldElement getYCoord()
    {
      int coord = getCurveCoordinateSystem();
      
      switch (coord)
      {

      case 5: 
      case 6: 
        ECFieldElement X = x;ECFieldElement L = y;
        
        if ((isInfinity()) || (X.isZero()))
        {
          return L;
        }
        

        ECFieldElement Y = L.add(X).multiply(X);
        if (6 == coord)
        {
          ECFieldElement Z = zs[0];
          if (!Z.isOne())
          {
            Y = Y.divide(Z);
          }
        }
        return Y;
      }
      
      
      return y;
    }
    


    protected boolean getCompressionYTilde()
    {
      ECFieldElement X = getRawXCoord();
      if (X.isZero())
      {
        return false;
      }
      
      ECFieldElement Y = getRawYCoord();
      
      switch (getCurveCoordinateSystem())
      {


      case 5: 
      case 6: 
        return Y.testBitZero() != X.testBitZero();
      }
      
      
      return Y.divide(X).testBitZero();
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
      int coord = curve.getCoordinateSystem();
      
      ECFieldElement X1 = x;
      ECFieldElement X2 = x;
      
      switch (coord)
      {

      case 0: 
        ECFieldElement Y1 = y;
        ECFieldElement Y2 = y;
        
        ECFieldElement dx = X1.add(X2);ECFieldElement dy = Y1.add(Y2);
        if (dx.isZero())
        {
          if (dy.isZero())
          {
            return twice();
          }
          
          return curve.getInfinity();
        }
        
        ECFieldElement L = dy.divide(dx);
        
        ECFieldElement X3 = L.square().add(L).add(dx).add(curve.getA());
        ECFieldElement Y3 = L.multiply(X1.add(X3)).add(X3).add(Y1);
        
        return new F2m(curve, X3, Y3, withCompression);
      

      case 1: 
        ECFieldElement Y1 = y;ECFieldElement Z1 = zs[0];
        ECFieldElement Y2 = y;ECFieldElement Z2 = zs[0];
        
        boolean Z2IsOne = Z2.isOne();
        
        ECFieldElement U1 = Z1.multiply(Y2);
        ECFieldElement U2 = Z2IsOne ? Y1 : Y1.multiply(Z2);
        ECFieldElement U = U1.add(U2);
        ECFieldElement V1 = Z1.multiply(X2);
        ECFieldElement V2 = Z2IsOne ? X1 : X1.multiply(Z2);
        ECFieldElement V = V1.add(V2);
        
        if (V.isZero())
        {
          if (U.isZero())
          {
            return twice();
          }
          
          return curve.getInfinity();
        }
        
        ECFieldElement VSq = V.square();
        ECFieldElement VCu = VSq.multiply(V);
        ECFieldElement W = Z2IsOne ? Z1 : Z1.multiply(Z2);
        ECFieldElement uv = U.add(V);
        ECFieldElement A = uv.multiplyPlusProduct(U, VSq, curve.getA()).multiply(W).add(VCu);
        
        ECFieldElement X3 = V.multiply(A);
        ECFieldElement VSqZ2 = Z2IsOne ? VSq : VSq.multiply(Z2);
        ECFieldElement Y3 = U.multiplyPlusProduct(X1, V, Y1).multiplyPlusProduct(VSqZ2, uv, A);
        ECFieldElement Z3 = VCu.multiply(W);
        
        return new F2m(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
      

      case 6: 
        if (X1.isZero())
        {
          if (X2.isZero())
          {
            return curve.getInfinity();
          }
          
          return b.add(this);
        }
        
        ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
        ECFieldElement L2 = y;ECFieldElement Z2 = zs[0];
        
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
          
          ECFieldElement X3 = L.square().add(L).add(X1).add(curve.getA());
          if (X3.isZero())
          {
            return new F2m(curve, X3, curve.getB().sqrt(), withCompression);
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
            return new F2m(curve, X3, curve.getB().sqrt(), withCompression);
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
        
        return new F2m(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
      }
      
      
      throw new IllegalStateException("unsupported coordinate system");
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
      
      int coord = curve.getCoordinateSystem();
      
      switch (coord)
      {

      case 0: 
        ECFieldElement Y1 = y;
        
        ECFieldElement L1 = Y1.divide(X1).add(X1);
        
        ECFieldElement X3 = L1.square().add(L1).add(curve.getA());
        ECFieldElement Y3 = X1.squarePlusProduct(X3, L1.addOne());
        
        return new F2m(curve, X3, Y3, withCompression);
      

      case 1: 
        ECFieldElement Y1 = y;ECFieldElement Z1 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        ECFieldElement X1Z1 = Z1IsOne ? X1 : X1.multiply(Z1);
        ECFieldElement Y1Z1 = Z1IsOne ? Y1 : Y1.multiply(Z1);
        
        ECFieldElement X1Sq = X1.square();
        ECFieldElement S = X1Sq.add(Y1Z1);
        ECFieldElement V = X1Z1;
        ECFieldElement vSquared = V.square();
        ECFieldElement sv = S.add(V);
        ECFieldElement h = sv.multiplyPlusProduct(S, vSquared, curve.getA());
        
        ECFieldElement X3 = V.multiply(h);
        ECFieldElement Y3 = X1Sq.square().multiplyPlusProduct(V, h, sv);
        ECFieldElement Z3 = V.multiply(vSquared);
        
        return new F2m(curve, X3, Y3, new ECFieldElement[] { Z3 }, withCompression);
      

      case 6: 
        ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
        
        boolean Z1IsOne = Z1.isOne();
        ECFieldElement L1Z1 = Z1IsOne ? L1 : L1.multiply(Z1);
        ECFieldElement Z1Sq = Z1IsOne ? Z1 : Z1.square();
        ECFieldElement a = curve.getA();
        ECFieldElement aZ1Sq = Z1IsOne ? a : a.multiply(Z1Sq);
        ECFieldElement T = L1.square().add(L1Z1).add(aZ1Sq);
        if (T.isZero())
        {
          return new F2m(curve, T, curve.getB().sqrt(), withCompression);
        }
        
        ECFieldElement X3 = T.square();
        ECFieldElement Z3 = Z1IsOne ? T : T.multiply(Z1Sq);
        
        ECFieldElement b = curve.getB();
        ECFieldElement L3;
        if (b.bitLength() < curve.getFieldSize() >> 1)
        {
          ECFieldElement t1 = L1.add(X1).square();
          ECFieldElement t2;
          ECFieldElement t2; if (b.isOne())
          {
            t2 = aZ1Sq.add(Z1Sq).square();

          }
          else
          {
            t2 = aZ1Sq.squarePlusProduct(b, Z1Sq.square());
          }
          ECFieldElement L3 = t1.add(T).add(Z1Sq).multiply(t1).add(t2).add(X3);
          if (a.isZero())
          {
            L3 = L3.add(Z3);
          }
          else if (!a.isOne())
          {
            L3 = L3.add(a.addOne().multiply(Z3));
          }
        }
        else
        {
          ECFieldElement X1Z1 = Z1IsOne ? X1 : X1.multiply(Z1);
          L3 = X1Z1.squarePlusProduct(T, L1Z1).add(X3).add(Z3);
        }
        
        return new F2m(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
      }
      
      
      throw new IllegalStateException("unsupported coordinate system");
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
      
      int coord = curve.getCoordinateSystem();
      
      switch (coord)
      {


      case 6: 
        ECFieldElement X2 = x;ECFieldElement Z2 = zs[0];
        if ((X2.isZero()) || (!Z2.isOne()))
        {
          return twice().add(b);
        }
        
        ECFieldElement L1 = y;ECFieldElement Z1 = zs[0];
        ECFieldElement L2 = y;
        
        ECFieldElement X1Sq = X1.square();
        ECFieldElement L1Sq = L1.square();
        ECFieldElement Z1Sq = Z1.square();
        ECFieldElement L1Z1 = L1.multiply(Z1);
        
        ECFieldElement T = curve.getA().multiply(Z1Sq).add(L1Sq).add(L1Z1);
        ECFieldElement L2plus1 = L2.addOne();
        ECFieldElement A = curve.getA().add(L2plus1).multiply(Z1Sq).add(L1Sq).multiplyPlusProduct(T, X1Sq, Z1Sq);
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
          return new F2m(curve, A, curve.getB().sqrt(), withCompression);
        }
        
        ECFieldElement X3 = A.square().multiply(X2Z1Sq);
        ECFieldElement Z3 = A.multiply(B).multiply(Z1Sq);
        ECFieldElement L3 = A.add(B).square().multiplyPlusProduct(T, L2plus1, Z3);
        
        return new F2m(curve, X3, L3, new ECFieldElement[] { Z3 }, withCompression);
      }
      
      
      return twice().add(b);
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
      
      switch (getCurveCoordinateSystem())
      {

      case 0: 
        ECFieldElement Y = y;
        return new F2m(curve, X, Y.add(X), withCompression);
      

      case 1: 
        ECFieldElement Y = y;ECFieldElement Z = zs[0];
        return new F2m(curve, X, Y.add(X), new ECFieldElement[] { Z }, withCompression);
      

      case 5: 
        ECFieldElement L = y;
        return new F2m(curve, X, L.addOne(), withCompression);
      


      case 6: 
        ECFieldElement L = y;ECFieldElement Z = zs[0];
        return new F2m(curve, X, L.add(Z), new ECFieldElement[] { Z }, withCompression);
      }
      
      
      throw new IllegalStateException("unsupported coordinate system");
    }
  }
}
