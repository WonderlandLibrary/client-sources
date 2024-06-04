package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.util.Random;
















public class GF2nPolynomialElement
  extends GF2nElement
{
  private static final int[] bitMask = { 1, 2, 4, 8, 16, 32, 64, 128, 256, 512, 1024, 2048, 4096, 8192, 16384, 32768, 65536, 131072, 262144, 524288, 1048576, 2097152, 4194304, 8388608, 16777216, 33554432, 67108864, 134217728, 268435456, 536870912, 1073741824, Integer.MIN_VALUE, 0 };
  







  private GF2Polynomial polynomial;
  







  public GF2nPolynomialElement(GF2nPolynomialField f, Random rand)
  {
    mField = f;
    mDegree = mField.getDegree();
    polynomial = new GF2Polynomial(mDegree);
    randomize(rand);
  }
  






  public GF2nPolynomialElement(GF2nPolynomialField f, GF2Polynomial bs)
  {
    mField = f;
    mDegree = mField.getDegree();
    polynomial = new GF2Polynomial(bs);
    polynomial.expandN(mDegree);
  }
  








  public GF2nPolynomialElement(GF2nPolynomialField f, byte[] os)
  {
    mField = f;
    mDegree = mField.getDegree();
    polynomial = new GF2Polynomial(mDegree, os);
    polynomial.expandN(mDegree);
  }
  







  public GF2nPolynomialElement(GF2nPolynomialField f, int[] is)
  {
    mField = f;
    mDegree = mField.getDegree();
    polynomial = new GF2Polynomial(mDegree, is);
    polynomial.expandN(mDegree);
  }
  






  public GF2nPolynomialElement(GF2nPolynomialElement other)
  {
    mField = mField;
    mDegree = mDegree;
    polynomial = new GF2Polynomial(polynomial);
  }
  










  public Object clone()
  {
    return new GF2nPolynomialElement(this);
  }
  







  void assignZero()
  {
    polynomial.assignZero();
  }
  






  public static GF2nPolynomialElement ZERO(GF2nPolynomialField f)
  {
    GF2Polynomial polynomial = new GF2Polynomial(f.getDegree());
    return new GF2nPolynomialElement(f, polynomial);
  }
  






  public static GF2nPolynomialElement ONE(GF2nPolynomialField f)
  {
    GF2Polynomial polynomial = new GF2Polynomial(f.getDegree(), new int[] { 1 });
    
    return new GF2nPolynomialElement(f, polynomial);
  }
  



  void assignOne()
  {
    polynomial.assignOne();
  }
  






  private void randomize(Random rand)
  {
    polynomial.expandN(mDegree);
    polynomial.randomize(rand);
  }
  









  public boolean isZero()
  {
    return polynomial.isZero();
  }
  





  public boolean isOne()
  {
    return polynomial.isOne();
  }
  







  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2nPolynomialElement)))
    {
      return false;
    }
    GF2nPolynomialElement otherElem = (GF2nPolynomialElement)other;
    
    if (mField != mField)
    {
      if (!mField.getFieldPolynomial().equals(mField
        .getFieldPolynomial()))
      {
        return false;
      }
    }
    
    return polynomial.equals(polynomial);
  }
  



  public int hashCode()
  {
    return mField.hashCode() + polynomial.hashCode();
  }
  









  private GF2Polynomial getGF2Polynomial()
  {
    return new GF2Polynomial(polynomial);
  }
  






  boolean testBit(int index)
  {
    return polynomial.testBit(index);
  }
  






  public boolean testRightmostBit()
  {
    return polynomial.testBit(0);
  }
  






  public GFElement add(GFElement addend)
    throws RuntimeException
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.addToThis(addend);
    return result;
  }
  





  public void addToThis(GFElement addend)
    throws RuntimeException
  {
    if (!(addend instanceof GF2nPolynomialElement))
    {
      throw new RuntimeException();
    }
    if (!mField.equals(mField))
    {
      throw new RuntimeException();
    }
    polynomial.addToThis(polynomial);
  }
  





  public GF2nElement increase()
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.increaseThis();
    return result;
  }
  



  public void increaseThis()
  {
    polynomial.increaseThis();
  }
  






  public GFElement multiply(GFElement factor)
    throws RuntimeException
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.multiplyThisBy(factor);
    return result;
  }
  





  public void multiplyThisBy(GFElement factor)
    throws RuntimeException
  {
    if (!(factor instanceof GF2nPolynomialElement))
    {
      throw new RuntimeException();
    }
    if (!mField.equals(mField))
    {
      throw new RuntimeException();
    }
    if (equals(factor))
    {
      squareThis();
      return;
    }
    
    polynomial = polynomial.multiply(polynomial);
    reduceThis();
  }
  









  public GFElement invert()
    throws ArithmeticException
  {
    return invertMAIA();
  }
  







  public GF2nPolynomialElement invertEEA()
    throws ArithmeticException
  {
    if (isZero())
    {
      throw new ArithmeticException();
    }
    GF2Polynomial b = new GF2Polynomial(mDegree + 32, "ONE");
    b.reduceN();
    GF2Polynomial c = new GF2Polynomial(mDegree + 32);
    c.reduceN();
    GF2Polynomial u = getGF2Polynomial();
    GF2Polynomial v = mField.getFieldPolynomial();
    

    u.reduceN();
    while (!u.isOne())
    {
      u.reduceN();
      v.reduceN();
      int j = u.getLength() - v.getLength();
      if (j < 0)
      {
        GF2Polynomial h = u;
        u = v;
        v = h;
        h = b;
        b = c;
        c = h;
        j = -j;
        c.reduceN();
      }
      u.shiftLeftAddThis(v, j);
      b.shiftLeftAddThis(c, j);
    }
    b.reduceN();
    return new GF2nPolynomialElement((GF2nPolynomialField)mField, b);
  }
  











  public GF2nPolynomialElement invertSquare()
    throws ArithmeticException
  {
    if (isZero())
    {
      throw new ArithmeticException();
    }
    
    int b = mField.getDegree() - 1;
    
    GF2nPolynomialElement n = new GF2nPolynomialElement(this);
    polynomial.expandN((mDegree << 1) + 32);
    polynomial.reduceN();
    
    int k = 1;
    

    for (int i = IntegerFunctions.floorLog(b) - 1; i >= 0; i--)
    {

      GF2nPolynomialElement u = new GF2nPolynomialElement(n);
      
      for (int j = 1; j <= k; j++)
      {

        u.squareThisPreCalc();
      }
      
      n.multiplyThisBy(u);
      
      k <<= 1;
      
      if ((b & bitMask[i]) != 0)
      {

        n.squareThisPreCalc();
        n.multiplyThisBy(this);
        
        k++;
      }
    }
    

    n.squareThisPreCalc();
    return n;
  }
  








  public GF2nPolynomialElement invertMAIA()
    throws ArithmeticException
  {
    if (isZero())
    {
      throw new ArithmeticException();
    }
    GF2Polynomial b = new GF2Polynomial(mDegree, "ONE");
    GF2Polynomial c = new GF2Polynomial(mDegree);
    GF2Polynomial u = getGF2Polynomial();
    GF2Polynomial v = mField.getFieldPolynomial();
    
    for (;;)
    {
      if (!u.testBit(0))
      {
        u.shiftRightThis();
        if (!b.testBit(0))
        {
          b.shiftRightThis();
        }
        else
        {
          b.addToThis(mField.getFieldPolynomial());
          b.shiftRightThis();
        }
      } else {
        if (u.isOne())
        {
          return new GF2nPolynomialElement((GF2nPolynomialField)mField, b);
        }
        
        u.reduceN();
        v.reduceN();
        if (u.getLength() < v.getLength())
        {
          GF2Polynomial h = u;
          u = v;
          v = h;
          h = b;
          b = c;
          c = h;
        }
        u.addToThis(v);
        b.addToThis(c);
      }
    }
  }
  






  public GF2nElement square()
  {
    return squarePreCalc();
  }
  




  public void squareThis()
  {
    squareThisPreCalc();
  }
  











  public GF2nPolynomialElement squareMatrix()
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.squareThisMatrix();
    result.reduceThis();
    return result;
  }
  










  public void squareThisMatrix()
  {
    GF2Polynomial result = new GF2Polynomial(mDegree);
    for (int i = 0; i < mDegree; i++)
    {

      if (polynomial.vectorMult(mField).squaringMatrix[(mDegree - i - 1)]))
      {

        result.setBit(i);
      }
    }
    
    polynomial = result;
  }
  










  public GF2nPolynomialElement squareBitwise()
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.squareThisBitwise();
    result.reduceThis();
    return result;
  }
  









  public void squareThisBitwise()
  {
    polynomial.squareThisBitwise();
    reduceThis();
  }
  










  public GF2nPolynomialElement squarePreCalc()
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.squareThisPreCalc();
    result.reduceThis();
    return result;
  }
  









  public void squareThisPreCalc()
  {
    polynomial.squareThisPreCalc();
    reduceThis();
  }
  







  public GF2nPolynomialElement power(int k)
  {
    if (k == 1)
    {
      return new GF2nPolynomialElement(this);
    }
    

    GF2nPolynomialElement result = ONE((GF2nPolynomialField)mField);
    if (k == 0)
    {
      return result;
    }
    
    GF2nPolynomialElement x = new GF2nPolynomialElement(this);
    polynomial.expandN((mDegree << 1) + 32);
    polynomial.reduceN();
    
    for (int i = 0; i < mDegree; i++)
    {
      if ((k & 1 << i) != 0)
      {
        result.multiplyThisBy(x);
      }
      x.square();
    }
    
    return result;
  }
  






  public GF2nElement squareRoot()
  {
    GF2nPolynomialElement result = new GF2nPolynomialElement(this);
    result.squareRootThis();
    return result;
  }
  




  public void squareRootThis()
  {
    polynomial.expandN((mDegree << 1) + 32);
    polynomial.reduceN();
    for (int i = 0; i < mField.getDegree() - 1; i++)
    {
      squareThis();
    }
  }
  










  public GF2nElement solveQuadraticEquation()
    throws RuntimeException
  {
    if (isZero())
    {
      return ZERO((GF2nPolynomialField)mField);
    }
    
    if ((mDegree & 0x1) == 1)
    {
      return halfTrace();
    }
    
    GF2nPolynomialElement z;
    
    GF2nPolynomialElement w;
    do
    {
      GF2nPolynomialElement p = new GF2nPolynomialElement((GF2nPolynomialField)mField, new Random());
      

      z = ZERO((GF2nPolynomialField)mField);
      w = (GF2nPolynomialElement)p.clone();
      
      for (int i = 1; i < mDegree; i++)
      {


        z.squareThis();
        w.squareThis();
        z.addToThis(w.multiply(this));
        w.addToThis(p);
      }
      
    } while (w.isZero());
    
    if (!equals(z.square().add(z)))
    {
      throw new RuntimeException();
    }
    

    return z;
  }
  





  public int trace()
  {
    GF2nPolynomialElement t = new GF2nPolynomialElement(this);
    

    for (int i = 1; i < mDegree; i++)
    {
      t.squareThis();
      t.addToThis(this);
    }
    
    if (t.isOne())
    {
      return 1;
    }
    return 0;
  }
  






  private GF2nPolynomialElement halfTrace()
    throws RuntimeException
  {
    if ((mDegree & 0x1) == 0)
    {
      throw new RuntimeException();
    }
    
    GF2nPolynomialElement h = new GF2nPolynomialElement(this);
    
    for (int i = 1; i <= mDegree - 1 >> 1; i++)
    {
      h.squareThis();
      h.squareThis();
      h.addToThis(this);
    }
    
    return h;
  }
  






  private void reduceThis()
  {
    if (polynomial.getLength() > mDegree)
    {
      if (((GF2nPolynomialField)mField).isTrinomial())
      {

        try
        {

          tc = ((GF2nPolynomialField)mField).getTc();
        }
        catch (RuntimeException NATExc) {
          int tc;
          throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a trinomial");
        }
        
        int tc;
        if ((mDegree - tc <= 32) || 
        
          (polynomial.getLength() > mDegree << 1))
        {
          reduceTrinomialBitwise(tc);
          return;
        }
        polynomial.reduceTrinomial(mDegree, tc);
        return;
      }
      if (((GF2nPolynomialField)mField).isPentanomial())
      {


        try
        {

          pc = ((GF2nPolynomialField)mField).getPc();
        }
        catch (RuntimeException NATExc) {
          int[] pc;
          throw new RuntimeException("GF2nPolynomialElement.reduce: the field polynomial is not a pentanomial");
        }
        
        int[] pc;
        if ((mDegree - pc[2] <= 32) || 
        
          (polynomial.getLength() > mDegree << 1))
        {
          reducePentanomialBitwise(pc);
          return;
        }
        polynomial.reducePentanomial(mDegree, pc);
        return;
      }
      

      polynomial = polynomial.remainder(mField.getFieldPolynomial());
      polynomial.expandN(mDegree);
      return;
    }
    
    if (polynomial.getLength() < mDegree)
    {
      polynomial.expandN(mDegree);
    }
  }
  





  private void reduceTrinomialBitwise(int tc)
  {
    int k = mDegree - tc;
    for (int i = polynomial.getLength() - 1; i >= mDegree; i--)
    {
      if (polynomial.testBit(i))
      {

        polynomial.xorBit(i);
        polynomial.xorBit(i - k);
        polynomial.xorBit(i - mDegree);
      }
    }
    
    polynomial.reduceN();
    polynomial.expandN(mDegree);
  }
  






  private void reducePentanomialBitwise(int[] pc)
  {
    int k = mDegree - pc[2];
    int l = mDegree - pc[1];
    int m = mDegree - pc[0];
    for (int i = polynomial.getLength() - 1; i >= mDegree; i--)
    {
      if (polynomial.testBit(i))
      {
        polynomial.xorBit(i);
        polynomial.xorBit(i - k);
        polynomial.xorBit(i - l);
        polynomial.xorBit(i - m);
        polynomial.xorBit(i - mDegree);
      }
    }
    
    polynomial.reduceN();
    polynomial.expandN(mDegree);
  }
  










  public String toString()
  {
    return polynomial.toString(16);
  }
  







  public String toString(int radix)
  {
    return polynomial.toString(radix);
  }
  






  public byte[] toByteArray()
  {
    return polynomial.toByteArray();
  }
  







  public BigInteger toFlexiBigInt()
  {
    return polynomial.toFlexiBigInt();
  }
}
