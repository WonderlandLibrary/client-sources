package org.spongycastle.math.ec;

import java.math.BigInteger;
import java.util.Hashtable;
import java.util.Random;
import org.spongycastle.math.ec.endo.ECEndomorphism;
import org.spongycastle.math.ec.endo.GLVEndomorphism;
import org.spongycastle.math.field.FiniteField;
import org.spongycastle.math.field.FiniteFields;
import org.spongycastle.util.BigIntegers;

public abstract class ECCurve
{
  public static final int COORD_AFFINE = 0;
  public static final int COORD_HOMOGENEOUS = 1;
  public static final int COORD_JACOBIAN = 2;
  public static final int COORD_JACOBIAN_CHUDNOVSKY = 3;
  public static final int COORD_JACOBIAN_MODIFIED = 4;
  public static final int COORD_LAMBDA_AFFINE = 5;
  public static final int COORD_LAMBDA_PROJECTIVE = 6;
  public static final int COORD_SKEWED = 7;
  protected FiniteField field;
  protected ECFieldElement a;
  protected ECFieldElement b;
  protected BigInteger order;
  protected BigInteger cofactor;
  
  public static int[] getAllCoordinateSystems()
  {
    return new int[] { 0, 1, 2, 3, 4, 5, 6, 7 };
  }
  

  public class Config
  {
    protected int coord;
    protected ECEndomorphism endomorphism;
    protected ECMultiplier multiplier;
    
    Config(int coord, ECEndomorphism endomorphism, ECMultiplier multiplier)
    {
      this.coord = coord;
      this.endomorphism = endomorphism;
      this.multiplier = multiplier;
    }
    
    public Config setCoordinateSystem(int coord)
    {
      this.coord = coord;
      return this;
    }
    
    public Config setEndomorphism(ECEndomorphism endomorphism)
    {
      this.endomorphism = endomorphism;
      return this;
    }
    
    public Config setMultiplier(ECMultiplier multiplier)
    {
      this.multiplier = multiplier;
      return this;
    }
    
    public ECCurve create()
    {
      if (!supportsCoordinateSystem(coord))
      {
        throw new IllegalStateException("unsupported coordinate system");
      }
      
      ECCurve c = cloneCurve();
      if (c == ECCurve.this)
      {
        throw new IllegalStateException("implementation returned current curve");
      }
      

      synchronized (c)
      {
        coord = coord;
        endomorphism = endomorphism;
        multiplier = multiplier;
      }
      
      return c;
    }
  }
  




  protected int coord = 0;
  protected ECEndomorphism endomorphism = null;
  protected ECMultiplier multiplier = null;
  
  protected ECCurve(FiniteField field)
  {
    this.field = field;
  }
  
  public abstract int getFieldSize();
  
  public abstract ECFieldElement fromBigInteger(BigInteger paramBigInteger);
  
  public abstract boolean isValidFieldElement(BigInteger paramBigInteger);
  
  public synchronized Config configure()
  {
    return new Config(coord, endomorphism, multiplier);
  }
  
  public ECPoint validatePoint(BigInteger x, BigInteger y)
  {
    ECPoint p = createPoint(x, y);
    if (!p.isValid())
    {
      throw new IllegalArgumentException("Invalid point coordinates");
    }
    return p;
  }
  

  /**
   * @deprecated
   */
  public ECPoint validatePoint(BigInteger x, BigInteger y, boolean withCompression)
  {
    ECPoint p = createPoint(x, y, withCompression);
    if (!p.isValid())
    {
      throw new IllegalArgumentException("Invalid point coordinates");
    }
    return p;
  }
  
  public ECPoint createPoint(BigInteger x, BigInteger y)
  {
    return createPoint(x, y, false);
  }
  

  /**
   * @deprecated
   */
  public ECPoint createPoint(BigInteger x, BigInteger y, boolean withCompression)
  {
    return createRawPoint(fromBigInteger(x), fromBigInteger(y), withCompression);
  }
  
  protected abstract ECCurve cloneCurve();
  
  protected abstract ECPoint createRawPoint(ECFieldElement paramECFieldElement1, ECFieldElement paramECFieldElement2, boolean paramBoolean);
  
  protected abstract ECPoint createRawPoint(ECFieldElement paramECFieldElement1, ECFieldElement paramECFieldElement2, ECFieldElement[] paramArrayOfECFieldElement, boolean paramBoolean);
  
  protected ECMultiplier createDefaultMultiplier()
  {
    if ((endomorphism instanceof GLVEndomorphism))
    {
      return new GLVMultiplier(this, (GLVEndomorphism)endomorphism);
    }
    
    return new WNafL2RMultiplier();
  }
  
  public boolean supportsCoordinateSystem(int coord)
  {
    return coord == 0;
  }
  
  public PreCompInfo getPreCompInfo(ECPoint point, String name)
  {
    checkPoint(point);
    synchronized (point)
    {
      Hashtable table = preCompTable;
      return table == null ? null : (PreCompInfo)table.get(name);
    }
  }
  












  public void setPreCompInfo(ECPoint point, String name, PreCompInfo preCompInfo)
  {
    checkPoint(point);
    synchronized (point)
    {
      Hashtable table = preCompTable;
      if (null == table)
      {
        preCompTable = (table = new Hashtable(4));
      }
      table.put(name, preCompInfo);
    }
  }
  
  public ECPoint importPoint(ECPoint p)
  {
    if (this == p.getCurve())
    {
      return p;
    }
    if (p.isInfinity())
    {
      return getInfinity();
    }
    

    p = p.normalize();
    
    return validatePoint(p.getXCoord().toBigInteger(), p.getYCoord().toBigInteger(), withCompression);
  }
  










  public void normalizeAll(ECPoint[] points)
  {
    normalizeAll(points, 0, points.length, null);
  }
  


















  public void normalizeAll(ECPoint[] points, int off, int len, ECFieldElement iso)
  {
    checkPoints(points, off, len);
    
    switch (getCoordinateSystem())
    {

    case 0: 
    case 5: 
      if (iso != null)
      {
        throw new IllegalArgumentException("'iso' not valid for affine coordinates");
      }
      return;
    }
    
    



    ECFieldElement[] zs = new ECFieldElement[len];
    int[] indices = new int[len];
    int count = 0;
    for (int i = 0; i < len; i++)
    {
      ECPoint p = points[(off + i)];
      if ((null != p) && ((iso != null) || (!p.isNormalized())))
      {
        zs[count] = p.getZCoord(0);
        indices[(count++)] = (off + i);
      }
    }
    
    if (count == 0)
    {
      return;
    }
    
    ECAlgorithms.montgomeryTrick(zs, 0, count, iso);
    
    for (int j = 0; j < count; j++)
    {
      int index = indices[j];
      points[index] = points[index].normalize(zs[j]);
    }
  }
  
  public abstract ECPoint getInfinity();
  
  public FiniteField getField()
  {
    return field;
  }
  
  public ECFieldElement getA()
  {
    return a;
  }
  
  public ECFieldElement getB()
  {
    return b;
  }
  
  public BigInteger getOrder()
  {
    return order;
  }
  
  public BigInteger getCofactor()
  {
    return cofactor;
  }
  
  public int getCoordinateSystem()
  {
    return coord;
  }
  
  protected abstract ECPoint decompressPoint(int paramInt, BigInteger paramBigInteger);
  
  public ECEndomorphism getEndomorphism()
  {
    return endomorphism;
  }
  



  public synchronized ECMultiplier getMultiplier()
  {
    if (multiplier == null)
    {
      multiplier = createDefaultMultiplier();
    }
    return multiplier;
  }
  






  public ECPoint decodePoint(byte[] encoded)
  {
    ECPoint p = null;
    int expectedLength = (getFieldSize() + 7) / 8;
    
    byte type = encoded[0];
    switch (type)
    {

    case 0: 
      if (encoded.length != 1)
      {
        throw new IllegalArgumentException("Incorrect length for infinity encoding");
      }
      
      p = getInfinity();
      break;
    

    case 2: 
    case 3: 
      if (encoded.length != expectedLength + 1)
      {
        throw new IllegalArgumentException("Incorrect length for compressed encoding");
      }
      
      int yTilde = type & 0x1;
      BigInteger X = BigIntegers.fromUnsignedByteArray(encoded, 1, expectedLength);
      
      p = decompressPoint(yTilde, X);
      if (!p.satisfiesCofactor())
      {
        throw new IllegalArgumentException("Invalid point");
      }
      


      break;
    case 4: 
      if (encoded.length != 2 * expectedLength + 1)
      {
        throw new IllegalArgumentException("Incorrect length for uncompressed encoding");
      }
      
      BigInteger X = BigIntegers.fromUnsignedByteArray(encoded, 1, expectedLength);
      BigInteger Y = BigIntegers.fromUnsignedByteArray(encoded, 1 + expectedLength, expectedLength);
      
      p = validatePoint(X, Y);
      break;
    

    case 6: 
    case 7: 
      if (encoded.length != 2 * expectedLength + 1)
      {
        throw new IllegalArgumentException("Incorrect length for hybrid encoding");
      }
      
      BigInteger X = BigIntegers.fromUnsignedByteArray(encoded, 1, expectedLength);
      BigInteger Y = BigIntegers.fromUnsignedByteArray(encoded, 1 + expectedLength, expectedLength);
      
      if (Y.testBit(0) != (type == 7))
      {
        throw new IllegalArgumentException("Inconsistent Y coordinate in hybrid encoding");
      }
      
      p = validatePoint(X, Y);
      break;
    case 1: case 5: 
    default: 
      throw new IllegalArgumentException("Invalid point encoding 0x" + Integer.toString(type, 16));
    }
    
    if ((type != 0) && (p.isInfinity()))
    {
      throw new IllegalArgumentException("Invalid infinity encoding");
    }
    
    return p;
  }
  
  protected void checkPoint(ECPoint point)
  {
    if ((null == point) || (this != point.getCurve()))
    {
      throw new IllegalArgumentException("'point' must be non-null and on this curve");
    }
  }
  
  protected void checkPoints(ECPoint[] points)
  {
    checkPoints(points, 0, points.length);
  }
  
  protected void checkPoints(ECPoint[] points, int off, int len)
  {
    if (points == null)
    {
      throw new IllegalArgumentException("'points' cannot be null");
    }
    if ((off < 0) || (len < 0) || (off > points.length - len))
    {
      throw new IllegalArgumentException("invalid range specified for 'points'");
    }
    
    for (int i = 0; i < len; i++)
    {
      ECPoint point = points[(off + i)];
      if ((null != point) && (this != point.getCurve()))
      {
        throw new IllegalArgumentException("'points' entries must be null or on this curve");
      }
    }
  }
  
  public boolean equals(ECCurve other)
  {
    if (this != other) if (null == other) break label68; label68: return 
    
      (getField().equals(other.getField())) && 
      (getA().toBigInteger().equals(other.getA().toBigInteger())) && 
      (getB().toBigInteger().equals(other.getB().toBigInteger()));
  }
  
  public boolean equals(Object obj)
  {
    return (this == obj) || (((obj instanceof ECCurve)) && (equals((ECCurve)obj)));
  }
  
  public int hashCode()
  {
    return 
    
      getField().hashCode() ^ org.spongycastle.util.Integers.rotateLeft(getA().toBigInteger().hashCode(), 8) ^ org.spongycastle.util.Integers.rotateLeft(getB().toBigInteger().hashCode(), 16);
  }
  
  public static abstract class AbstractFp extends ECCurve
  {
    protected AbstractFp(BigInteger q)
    {
      super();
    }
    
    public boolean isValidFieldElement(BigInteger x)
    {
      return (x != null) && (x.signum() >= 0) && (x.compareTo(getField().getCharacteristic()) < 0);
    }
    
    protected ECPoint decompressPoint(int yTilde, BigInteger X1)
    {
      ECFieldElement x = fromBigInteger(X1);
      ECFieldElement rhs = x.square().add(a).multiply(x).add(b);
      ECFieldElement y = rhs.sqrt();
      



      if (y == null)
      {
        throw new IllegalArgumentException("Invalid point compression");
      }
      
      if (y.testBitZero() != (yTilde == 1))
      {

        y = y.negate();
      }
      
      return createRawPoint(x, y, true);
    }
  }
  

  public static class Fp
    extends ECCurve.AbstractFp
  {
    private static final int FP_DEFAULT_COORDS = 4;
    
    BigInteger q;
    BigInteger r;
    ECPoint.Fp infinity;
    
    public Fp(BigInteger q, BigInteger a, BigInteger b)
    {
      this(q, a, b, null, null);
    }
    
    public Fp(BigInteger q, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor)
    {
      super();
      
      this.q = q;
      r = ECFieldElement.Fp.calculateResidue(q);
      infinity = new ECPoint.Fp(this, null, null);
      
      this.a = fromBigInteger(a);
      this.b = fromBigInteger(b);
      this.order = order;
      this.cofactor = cofactor;
      coord = 4;
    }
    
    protected Fp(BigInteger q, BigInteger r, ECFieldElement a, ECFieldElement b)
    {
      this(q, r, a, b, null, null);
    }
    
    protected Fp(BigInteger q, BigInteger r, ECFieldElement a, ECFieldElement b, BigInteger order, BigInteger cofactor)
    {
      super();
      
      this.q = q;
      this.r = r;
      infinity = new ECPoint.Fp(this, null, null);
      
      this.a = a;
      this.b = b;
      this.order = order;
      this.cofactor = cofactor;
      coord = 4;
    }
    
    protected ECCurve cloneCurve()
    {
      return new Fp(q, r, a, b, order, cofactor);
    }
    
    public boolean supportsCoordinateSystem(int coord)
    {
      switch (coord)
      {
      case 0: 
      case 1: 
      case 2: 
      case 4: 
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
      return new ECFieldElement.Fp(q, r, x);
    }
    
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
    {
      return new ECPoint.Fp(this, x, y, withCompression);
    }
    
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
    {
      return new ECPoint.Fp(this, x, y, zs, withCompression);
    }
    
    public ECPoint importPoint(ECPoint p)
    {
      if ((this != p.getCurve()) && (getCoordinateSystem() == 2) && (!p.isInfinity()))
      {
        switch (p.getCurve().getCoordinateSystem())
        {
        case 2: 
        case 3: 
        case 4: 
          return new ECPoint.Fp(this, 
            fromBigInteger(x.toBigInteger()), 
            fromBigInteger(y.toBigInteger()), new ECFieldElement[] {
            fromBigInteger(zs[0].toBigInteger()) }, withCompression);
        }
        
      }
      


      return super.importPoint(p);
    }
    
    public ECPoint getInfinity()
    {
      return infinity;
    }
  }
  
  public static abstract class AbstractF2m extends ECCurve
  {
    public static BigInteger inverse(int m, int[] ks, BigInteger x)
    {
      return new LongArray(x).modInverse(m, ks).toBigInteger();
    }
    





    private BigInteger[] si = null;
    
    private static FiniteField buildField(int m, int k1, int k2, int k3)
    {
      if (k1 == 0)
      {
        throw new IllegalArgumentException("k1 must be > 0");
      }
      
      if (k2 == 0)
      {
        if (k3 != 0)
        {
          throw new IllegalArgumentException("k3 must be 0 if k2 == 0");
        }
        
        return FiniteFields.getBinaryExtensionField(new int[] { 0, k1, m });
      }
      
      if (k2 <= k1)
      {
        throw new IllegalArgumentException("k2 must be > k1");
      }
      
      if (k3 <= k2)
      {
        throw new IllegalArgumentException("k3 must be > k2");
      }
      
      return FiniteFields.getBinaryExtensionField(new int[] { 0, k1, k2, k3, m });
    }
    
    protected AbstractF2m(int m, int k1, int k2, int k3)
    {
      super();
    }
    
    public boolean isValidFieldElement(BigInteger x)
    {
      return (x != null) && (x.signum() >= 0) && (x.bitLength() <= getFieldSize());
    }
    
    public ECPoint createPoint(BigInteger x, BigInteger y, boolean withCompression)
    {
      ECFieldElement X = fromBigInteger(x);ECFieldElement Y = fromBigInteger(y);
      
      int coord = getCoordinateSystem();
      
      switch (coord)
      {

      case 5: 
      case 6: 
        if (X.isZero())
        {
          if (!Y.square().equals(getB()))
          {
            throw new IllegalArgumentException();




          }
          




        }
        else
        {



          Y = Y.divide(X).add(X);
        }
        break;
      }
      
      




      return createRawPoint(X, Y, withCompression);
    }
    









    protected ECPoint decompressPoint(int yTilde, BigInteger X1)
    {
      ECFieldElement x = fromBigInteger(X1);ECFieldElement y = null;
      if (x.isZero())
      {
        y = getB().sqrt();
      }
      else
      {
        ECFieldElement beta = x.square().invert().multiply(getB()).add(getA()).add(x);
        ECFieldElement z = solveQuadraticEquation(beta);
        if (z != null)
        {
          if (z.testBitZero() != (yTilde == 1))
          {
            z = z.addOne();
          }
          
          switch (getCoordinateSystem())
          {

          case 5: 
          case 6: 
            y = z.add(x);
            break;
          

          default: 
            y = z.multiply(x);
          }
          
        }
      }
      

      if (y == null)
      {
        throw new IllegalArgumentException("Invalid point compression");
      }
      
      return createRawPoint(x, y, true);
    }
    









    private ECFieldElement solveQuadraticEquation(ECFieldElement beta)
    {
      if (beta.isZero())
      {
        return beta;
      }
      
      ECFieldElement zeroElement = fromBigInteger(ECConstants.ZERO);
      
      int m = getFieldSize();
      Random rand = new Random();
      ECFieldElement z;
      ECFieldElement gamma;
      do { ECFieldElement t = fromBigInteger(new BigInteger(m, rand));
        z = zeroElement;
        ECFieldElement w = beta;
        for (int i = 1; i < m; i++)
        {
          ECFieldElement w2 = w.square();
          z = z.square().add(w2.multiply(t));
          w = w2.add(beta);
        }
        if (!w.isZero())
        {
          return null;
        }
        gamma = z.square().add(z);
      }
      while (gamma.isZero());
      
      return z;
    }
    





    synchronized BigInteger[] getSi()
    {
      if (si == null)
      {
        si = Tnaf.getSi(this);
      }
      return si;
    }
    




    public boolean isKoblitz()
    {
      return (order != null) && (cofactor != null) && (b.isOne()) && ((a.isZero()) || (a.isOne()));
    }
  }
  








  public static class F2m
    extends ECCurve.AbstractF2m
  {
    private static final int F2M_DEFAULT_COORDS = 6;
    







    private int m;
    







    private int k1;
    






    private int k2;
    






    private int k3;
    






    private ECPoint.F2m infinity;
    







    public F2m(int m, int k, BigInteger a, BigInteger b)
    {
      this(m, k, 0, 0, a, b, null, null);
    }
    























    public F2m(int m, int k, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor)
    {
      this(m, k, 0, 0, a, b, order, cofactor);
    }
    


























    public F2m(int m, int k1, int k2, int k3, BigInteger a, BigInteger b)
    {
      this(m, k1, k2, k3, a, b, null, null);
    }
    































    public F2m(int m, int k1, int k2, int k3, BigInteger a, BigInteger b, BigInteger order, BigInteger cofactor)
    {
      super(k1, k2, k3);
      
      this.m = m;
      this.k1 = k1;
      this.k2 = k2;
      this.k3 = k3;
      this.order = order;
      this.cofactor = cofactor;
      
      infinity = new ECPoint.F2m(this, null, null);
      this.a = fromBigInteger(a);
      this.b = fromBigInteger(b);
      coord = 6;
    }
    
    protected F2m(int m, int k1, int k2, int k3, ECFieldElement a, ECFieldElement b, BigInteger order, BigInteger cofactor)
    {
      super(k1, k2, k3);
      
      this.m = m;
      this.k1 = k1;
      this.k2 = k2;
      this.k3 = k3;
      this.order = order;
      this.cofactor = cofactor;
      
      infinity = new ECPoint.F2m(this, null, null);
      this.a = a;
      this.b = b;
      coord = 6;
    }
    
    protected ECCurve cloneCurve()
    {
      return new F2m(m, k1, k2, k3, a, b, order, cofactor);
    }
    
    public boolean supportsCoordinateSystem(int coord)
    {
      switch (coord)
      {
      case 0: 
      case 1: 
      case 6: 
        return true;
      }
      return false;
    }
    

    protected ECMultiplier createDefaultMultiplier()
    {
      if (isKoblitz())
      {
        return new WTauNafMultiplier();
      }
      
      return super.createDefaultMultiplier();
    }
    
    public int getFieldSize()
    {
      return m;
    }
    
    public ECFieldElement fromBigInteger(BigInteger x)
    {
      return new ECFieldElement.F2m(m, k1, k2, k3, x);
    }
    
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, boolean withCompression)
    {
      return new ECPoint.F2m(this, x, y, withCompression);
    }
    
    protected ECPoint createRawPoint(ECFieldElement x, ECFieldElement y, ECFieldElement[] zs, boolean withCompression)
    {
      return new ECPoint.F2m(this, x, y, zs, withCompression);
    }
    
    public ECPoint getInfinity()
    {
      return infinity;
    }
    
    public int getM()
    {
      return m;
    }
    





    public boolean isTrinomial()
    {
      return (k2 == 0) && (k3 == 0);
    }
    
    public int getK1()
    {
      return k1;
    }
    
    public int getK2()
    {
      return k2;
    }
    
    public int getK3()
    {
      return k3;
    }
    
    /**
     * @deprecated
     */
    public BigInteger getN()
    {
      return order;
    }
    
    /**
     * @deprecated
     */
    public BigInteger getH()
    {
      return cofactor;
    }
  }
}
