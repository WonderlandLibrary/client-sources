package org.spongycastle.math.ec;

import java.math.BigInteger;

public abstract class ECFieldElement implements ECConstants { public ECFieldElement() {}
  
  public abstract BigInteger toBigInteger();
  
  public abstract String getFieldName();
  
  public abstract int getFieldSize();
  
  public abstract ECFieldElement add(ECFieldElement paramECFieldElement);
  
  public abstract ECFieldElement addOne();
  
  public abstract ECFieldElement subtract(ECFieldElement paramECFieldElement);
  
  public abstract ECFieldElement multiply(ECFieldElement paramECFieldElement);
  
  public abstract ECFieldElement divide(ECFieldElement paramECFieldElement);
  
  public abstract ECFieldElement negate();
  
  public abstract ECFieldElement square();
  
  public abstract ECFieldElement invert();
  public abstract ECFieldElement sqrt();
  public int bitLength() { return toBigInteger().bitLength(); }
  

  public boolean isOne()
  {
    return bitLength() == 1;
  }
  
  public boolean isZero()
  {
    return 0 == toBigInteger().signum();
  }
  
  public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
  {
    return multiply(b).subtract(x.multiply(y));
  }
  
  public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
  {
    return multiply(b).add(x.multiply(y));
  }
  
  public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y)
  {
    return square().subtract(x.multiply(y));
  }
  
  public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y)
  {
    return square().add(x.multiply(y));
  }
  
  public ECFieldElement squarePow(int pow)
  {
    ECFieldElement r = this;
    for (int i = 0; i < pow; i++)
    {
      r = r.square();
    }
    return r;
  }
  
  public boolean testBitZero()
  {
    return toBigInteger().testBit(0);
  }
  
  public String toString()
  {
    return toBigInteger().toString(16);
  }
  
  public byte[] getEncoded()
  {
    return org.spongycastle.util.BigIntegers.asUnsignedByteArray((getFieldSize() + 7) / 8, toBigInteger());
  }
  
  public static class Fp extends ECFieldElement {
    BigInteger q;
    BigInteger r;
    BigInteger x;
    
    static BigInteger calculateResidue(BigInteger p) {
      int bitLength = p.bitLength();
      if (bitLength >= 96)
      {
        BigInteger firstWord = p.shiftRight(bitLength - 64);
        if (firstWord.longValue() == -1L)
        {
          return ONE.shiftLeft(bitLength).subtract(p);
        }
      }
      return null;
    }
    
    /**
     * @deprecated
     */
    public Fp(BigInteger q, BigInteger x)
    {
      this(q, calculateResidue(q), x);
    }
    
    Fp(BigInteger q, BigInteger r, BigInteger x)
    {
      if ((x == null) || (x.signum() < 0) || (x.compareTo(q) >= 0))
      {
        throw new IllegalArgumentException("x value invalid in Fp field element");
      }
      
      this.q = q;
      this.r = r;
      this.x = x;
    }
    
    public BigInteger toBigInteger()
    {
      return x;
    }
    





    public String getFieldName()
    {
      return "Fp";
    }
    
    public int getFieldSize()
    {
      return q.bitLength();
    }
    
    public BigInteger getQ()
    {
      return q;
    }
    
    public ECFieldElement add(ECFieldElement b)
    {
      return new Fp(q, r, modAdd(x, b.toBigInteger()));
    }
    
    public ECFieldElement addOne()
    {
      BigInteger x2 = x.add(ECConstants.ONE);
      if (x2.compareTo(q) == 0)
      {
        x2 = ECConstants.ZERO;
      }
      return new Fp(q, r, x2);
    }
    
    public ECFieldElement subtract(ECFieldElement b)
    {
      return new Fp(q, r, modSubtract(x, b.toBigInteger()));
    }
    
    public ECFieldElement multiply(ECFieldElement b)
    {
      return new Fp(q, r, modMult(x, b.toBigInteger()));
    }
    
    public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
    {
      BigInteger ax = this.x;BigInteger bx = b.toBigInteger();BigInteger xx = x.toBigInteger();BigInteger yx = y.toBigInteger();
      BigInteger ab = ax.multiply(bx);
      BigInteger xy = xx.multiply(yx);
      return new Fp(q, r, modReduce(ab.subtract(xy)));
    }
    
    public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
    {
      BigInteger ax = this.x;BigInteger bx = b.toBigInteger();BigInteger xx = x.toBigInteger();BigInteger yx = y.toBigInteger();
      BigInteger ab = ax.multiply(bx);
      BigInteger xy = xx.multiply(yx);
      return new Fp(q, r, modReduce(ab.add(xy)));
    }
    
    public ECFieldElement divide(ECFieldElement b)
    {
      return new Fp(q, r, modMult(x, modInverse(b.toBigInteger())));
    }
    
    public ECFieldElement negate()
    {
      return x.signum() == 0 ? this : new Fp(q, r, q.subtract(x));
    }
    
    public ECFieldElement square()
    {
      return new Fp(q, r, modMult(x, x));
    }
    
    public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y)
    {
      BigInteger ax = this.x;BigInteger xx = x.toBigInteger();BigInteger yx = y.toBigInteger();
      BigInteger aa = ax.multiply(ax);
      BigInteger xy = xx.multiply(yx);
      return new Fp(q, r, modReduce(aa.subtract(xy)));
    }
    
    public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y)
    {
      BigInteger ax = this.x;BigInteger xx = x.toBigInteger();BigInteger yx = y.toBigInteger();
      BigInteger aa = ax.multiply(ax);
      BigInteger xy = xx.multiply(yx);
      return new Fp(q, r, modReduce(aa.add(xy)));
    }
    

    public ECFieldElement invert()
    {
      return new Fp(q, r, modInverse(x));
    }
    





    public ECFieldElement sqrt()
    {
      if ((isZero()) || (isOne()))
      {
        return this;
      }
      
      if (!q.testBit(0))
      {
        throw new RuntimeException("not done yet");
      }
      



      if (q.testBit(1))
      {
        BigInteger e = q.shiftRight(2).add(ECConstants.ONE);
        return checkSqrt(new Fp(q, r, x.modPow(e, q)));
      }
      
      if (q.testBit(2))
      {
        BigInteger t1 = x.modPow(q.shiftRight(3), q);
        BigInteger t2 = modMult(t1, x);
        BigInteger t3 = modMult(t2, t1);
        
        if (t3.equals(ECConstants.ONE))
        {
          return checkSqrt(new Fp(q, r, t2));
        }
        

        BigInteger t4 = ECConstants.TWO.modPow(q.shiftRight(2), q);
        
        BigInteger y = modMult(t2, t4);
        
        return checkSqrt(new Fp(q, r, y));
      }
      


      BigInteger legendreExponent = q.shiftRight(1);
      if (!x.modPow(legendreExponent, q).equals(ECConstants.ONE))
      {
        return null;
      }
      
      BigInteger X = x;
      BigInteger fourX = modDouble(modDouble(X));
      
      BigInteger k = legendreExponent.add(ECConstants.ONE);BigInteger qMinusOne = q.subtract(ECConstants.ONE);
      

      java.util.Random rand = new java.util.Random();
      BigInteger U;
      do
      {
        BigInteger P;
        do {
          P = new BigInteger(q.bitLength(), rand);
        }
        while ((P.compareTo(q) >= 0) || 
          (!modReduce(P.multiply(P).subtract(fourX)).modPow(legendreExponent, q).equals(qMinusOne)));
        
        BigInteger[] result = lucasSequence(P, X, k);
        U = result[0];
        BigInteger V = result[1];
        
        if (modMult(V, V).equals(fourX))
        {
          return new Fp(q, r, modHalfAbs(V));
        }
        
      } while ((U.equals(ECConstants.ONE)) || (U.equals(qMinusOne)));
      
      return null;
    }
    
    private ECFieldElement checkSqrt(ECFieldElement z)
    {
      return z.square().equals(this) ? z : null;
    }
    





    private BigInteger[] lucasSequence(BigInteger P, BigInteger Q, BigInteger k)
    {
      int n = k.bitLength();
      int s = k.getLowestSetBit();
      


      BigInteger Uh = ECConstants.ONE;
      BigInteger Vl = ECConstants.TWO;
      BigInteger Vh = P;
      BigInteger Ql = ECConstants.ONE;
      BigInteger Qh = ECConstants.ONE;
      
      for (int j = n - 1; j >= s + 1; j--)
      {
        Ql = modMult(Ql, Qh);
        
        if (k.testBit(j))
        {
          Qh = modMult(Ql, Q);
          Uh = modMult(Uh, Vh);
          Vl = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql)));
          Vh = modReduce(Vh.multiply(Vh).subtract(Qh.shiftLeft(1)));
        }
        else
        {
          Qh = Ql;
          Uh = modReduce(Uh.multiply(Vl).subtract(Ql));
          Vh = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql)));
          Vl = modReduce(Vl.multiply(Vl).subtract(Ql.shiftLeft(1)));
        }
      }
      
      Ql = modMult(Ql, Qh);
      Qh = modMult(Ql, Q);
      Uh = modReduce(Uh.multiply(Vl).subtract(Ql));
      Vl = modReduce(Vh.multiply(Vl).subtract(P.multiply(Ql)));
      Ql = modMult(Ql, Qh);
      
      for (int j = 1; j <= s; j++)
      {
        Uh = modMult(Uh, Vl);
        Vl = modReduce(Vl.multiply(Vl).subtract(Ql.shiftLeft(1)));
        Ql = modMult(Ql, Ql);
      }
      
      return new BigInteger[] { Uh, Vl };
    }
    
    protected BigInteger modAdd(BigInteger x1, BigInteger x2)
    {
      BigInteger x3 = x1.add(x2);
      if (x3.compareTo(q) >= 0)
      {
        x3 = x3.subtract(q);
      }
      return x3;
    }
    
    protected BigInteger modDouble(BigInteger x)
    {
      BigInteger _2x = x.shiftLeft(1);
      if (_2x.compareTo(q) >= 0)
      {
        _2x = _2x.subtract(q);
      }
      return _2x;
    }
    
    protected BigInteger modHalf(BigInteger x)
    {
      if (x.testBit(0))
      {
        x = q.add(x);
      }
      return x.shiftRight(1);
    }
    
    protected BigInteger modHalfAbs(BigInteger x)
    {
      if (x.testBit(0))
      {
        x = q.subtract(x);
      }
      return x.shiftRight(1);
    }
    
    protected BigInteger modInverse(BigInteger x)
    {
      int bits = getFieldSize();
      int len = bits + 31 >> 5;
      int[] p = org.spongycastle.math.raw.Nat.fromBigInteger(bits, q);
      int[] n = org.spongycastle.math.raw.Nat.fromBigInteger(bits, x);
      int[] z = org.spongycastle.math.raw.Nat.create(len);
      org.spongycastle.math.raw.Mod.invert(p, n, z);
      return org.spongycastle.math.raw.Nat.toBigInteger(len, z);
    }
    
    protected BigInteger modMult(BigInteger x1, BigInteger x2)
    {
      return modReduce(x1.multiply(x2));
    }
    
    protected BigInteger modReduce(BigInteger x)
    {
      if (r != null)
      {
        boolean negative = x.signum() < 0;
        if (negative)
        {
          x = x.abs();
        }
        int qLen = q.bitLength();
        boolean rIsOne = r.equals(ECConstants.ONE);
        while (x.bitLength() > qLen + 1)
        {
          BigInteger u = x.shiftRight(qLen);
          BigInteger v = x.subtract(u.shiftLeft(qLen));
          if (!rIsOne)
          {
            u = u.multiply(r);
          }
          x = u.add(v);
        }
        while (x.compareTo(q) >= 0)
        {
          x = x.subtract(q);
        }
        if ((negative) && (x.signum() != 0))
        {
          x = q.subtract(x);
        }
      }
      else
      {
        x = x.mod(q);
      }
      return x;
    }
    
    protected BigInteger modSubtract(BigInteger x1, BigInteger x2)
    {
      BigInteger x3 = x1.subtract(x2);
      if (x3.signum() < 0)
      {
        x3 = x3.add(q);
      }
      return x3;
    }
    
    public boolean equals(Object other)
    {
      if (other == this)
      {
        return true;
      }
      
      if (!(other instanceof Fp))
      {
        return false;
      }
      
      Fp o = (Fp)other;
      return (q.equals(q)) && (x.equals(x));
    }
    
    public int hashCode()
    {
      return q.hashCode() ^ x.hashCode();
    }
  }
  







  public static class F2m
    extends ECFieldElement
  {
    public static final int GNB = 1;
    






    public static final int TPB = 2;
    






    public static final int PPB = 3;
    





    private int representation;
    





    private int m;
    





    private int[] ks;
    





    private LongArray x;
    






    /**
     * @deprecated
     */
    public F2m(int m, int k1, int k2, int k3, BigInteger x)
    {
      if ((x == null) || (x.signum() < 0) || (x.bitLength() > m))
      {
        throw new IllegalArgumentException("x value invalid in F2m field element");
      }
      
      if ((k2 == 0) && (k3 == 0))
      {
        representation = 2;
        ks = new int[] { k1 };
      }
      else
      {
        if (k2 >= k3)
        {
          throw new IllegalArgumentException("k2 must be smaller than k3");
        }
        
        if (k2 <= 0)
        {
          throw new IllegalArgumentException("k2 must be larger than 0");
        }
        
        representation = 3;
        ks = new int[] { k1, k2, k3 };
      }
      
      this.m = m;
      this.x = new LongArray(x);
    }
    








    /**
     * @deprecated
     */
    public F2m(int m, int k, BigInteger x)
    {
      this(m, k, 0, 0, x);
    }
    
    private F2m(int m, int[] ks, LongArray x)
    {
      this.m = m;
      representation = (ks.length == 1 ? 2 : 3);
      this.ks = ks;
      this.x = x;
    }
    
    public int bitLength()
    {
      return x.degree();
    }
    
    public boolean isOne()
    {
      return x.isOne();
    }
    
    public boolean isZero()
    {
      return x.isZero();
    }
    
    public boolean testBitZero()
    {
      return x.testBitZero();
    }
    
    public BigInteger toBigInteger()
    {
      return x.toBigInteger();
    }
    
    public String getFieldName()
    {
      return "F2m";
    }
    
    public int getFieldSize()
    {
      return m;
    }
    













    public static void checkFieldElements(ECFieldElement a, ECFieldElement b)
    {
      if ((!(a instanceof F2m)) || (!(b instanceof F2m)))
      {
        throw new IllegalArgumentException("Field elements are not both instances of ECFieldElement.F2m");
      }
      

      F2m aF2m = (F2m)a;
      F2m bF2m = (F2m)b;
      
      if (representation != representation)
      {

        throw new IllegalArgumentException("One of the F2m field elements has incorrect representation");
      }
      
      if ((m != m) || (!org.spongycastle.util.Arrays.areEqual(ks, ks)))
      {
        throw new IllegalArgumentException("Field elements are not elements of the same field F2m");
      }
    }
    



    public ECFieldElement add(ECFieldElement b)
    {
      LongArray iarrClone = (LongArray)x.clone();
      F2m bF2m = (F2m)b;
      iarrClone.addShiftedByWords(x, 0);
      return new F2m(m, ks, iarrClone);
    }
    
    public ECFieldElement addOne()
    {
      return new F2m(m, ks, x.addOne());
    }
    

    public ECFieldElement subtract(ECFieldElement b)
    {
      return add(b);
    }
    







    public ECFieldElement multiply(ECFieldElement b)
    {
      return new F2m(m, ks, x.modMultiply(x, m, ks));
    }
    
    public ECFieldElement multiplyMinusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
    {
      return multiplyPlusProduct(b, x, y);
    }
    
    public ECFieldElement multiplyPlusProduct(ECFieldElement b, ECFieldElement x, ECFieldElement y)
    {
      LongArray ax = this.x;LongArray bx = x;LongArray xx = x;LongArray yx = x;
      
      LongArray ab = ax.multiply(bx, m, ks);
      LongArray xy = xx.multiply(yx, m, ks);
      
      if ((ab == ax) || (ab == bx))
      {
        ab = (LongArray)ab.clone();
      }
      
      ab.addShiftedByWords(xy, 0);
      ab.reduce(m, ks);
      
      return new F2m(m, ks, ab);
    }
    

    public ECFieldElement divide(ECFieldElement b)
    {
      ECFieldElement bInv = b.invert();
      return multiply(bInv);
    }
    

    public ECFieldElement negate()
    {
      return this;
    }
    
    public ECFieldElement square()
    {
      return new F2m(m, ks, x.modSquare(m, ks));
    }
    
    public ECFieldElement squareMinusProduct(ECFieldElement x, ECFieldElement y)
    {
      return squarePlusProduct(x, y);
    }
    
    public ECFieldElement squarePlusProduct(ECFieldElement x, ECFieldElement y)
    {
      LongArray ax = this.x;LongArray xx = x;LongArray yx = x;
      
      LongArray aa = ax.square(m, ks);
      LongArray xy = xx.multiply(yx, m, ks);
      
      if (aa == ax)
      {
        aa = (LongArray)aa.clone();
      }
      
      aa.addShiftedByWords(xy, 0);
      aa.reduce(m, ks);
      
      return new F2m(m, ks, aa);
    }
    
    public ECFieldElement squarePow(int pow)
    {
      return pow < 1 ? this : new F2m(m, ks, x.modSquareN(pow, m, ks));
    }
    
    public ECFieldElement invert()
    {
      return new F2m(m, ks, x.modInverse(m, ks));
    }
    
    public ECFieldElement sqrt()
    {
      return (x.isZero()) || (x.isOne()) ? this : squarePow(m - 1);
    }
    








    public int getRepresentation()
    {
      return representation;
    }
    




    public int getM()
    {
      return m;
    }
    








    public int getK1()
    {
      return ks[0];
    }
    






    public int getK2()
    {
      return ks.length >= 2 ? ks[1] : 0;
    }
    






    public int getK3()
    {
      return ks.length >= 3 ? ks[2] : 0;
    }
    
    public boolean equals(Object anObject)
    {
      if (anObject == this)
      {
        return true;
      }
      
      if (!(anObject instanceof F2m))
      {
        return false;
      }
      
      F2m b = (F2m)anObject;
      
      if ((m == m) && (representation == representation)) {} return 
      
        (org.spongycastle.util.Arrays.areEqual(ks, ks)) && 
        (x.equals(x));
    }
    
    public int hashCode()
    {
      return x.hashCode() ^ m ^ org.spongycastle.util.Arrays.hashCode(ks);
    }
  }
}
