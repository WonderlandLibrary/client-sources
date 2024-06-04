package org.spongycastle.pqc.math.linearalgebra;

import java.math.BigInteger;
import java.security.SecureRandom;















public class GF2nONBElement
  extends GF2nElement
{
  private static final long[] mBitmask = { 1L, 2L, 4L, 8L, 16L, 32L, 64L, 128L, 256L, 512L, 1024L, 2048L, 4096L, 8192L, 16384L, 32768L, 65536L, 131072L, 262144L, 524288L, 1048576L, 2097152L, 4194304L, 8388608L, 16777216L, 33554432L, 67108864L, 134217728L, 268435456L, 536870912L, 1073741824L, 2147483648L, 4294967296L, 8589934592L, 17179869184L, 34359738368L, 68719476736L, 137438953472L, 274877906944L, 549755813888L, 1099511627776L, 2199023255552L, 4398046511104L, 8796093022208L, 17592186044416L, 35184372088832L, 70368744177664L, 140737488355328L, 281474976710656L, 562949953421312L, 1125899906842624L, 2251799813685248L, 4503599627370496L, 9007199254740992L, 18014398509481984L, 36028797018963968L, 72057594037927936L, 144115188075855872L, 288230376151711744L, 576460752303423488L, 1152921504606846976L, 2305843009213693952L, 4611686018427387904L, Long.MIN_VALUE };
  





















  private static final long[] mMaxmask = { 1L, 3L, 7L, 15L, 31L, 63L, 127L, 255L, 511L, 1023L, 2047L, 4095L, 8191L, 16383L, 32767L, 65535L, 131071L, 262143L, 524287L, 1048575L, 2097151L, 4194303L, 8388607L, 16777215L, 33554431L, 67108863L, 134217727L, 268435455L, 536870911L, 1073741823L, 2147483647L, 4294967295L, 8589934591L, 17179869183L, 34359738367L, 68719476735L, 137438953471L, 274877906943L, 549755813887L, 1099511627775L, 2199023255551L, 4398046511103L, 8796093022207L, 17592186044415L, 35184372088831L, 70368744177663L, 140737488355327L, 281474976710655L, 562949953421311L, 1125899906842623L, 2251799813685247L, 4503599627370495L, 9007199254740991L, 18014398509481983L, 36028797018963967L, 72057594037927935L, 144115188075855871L, 288230376151711743L, 576460752303423487L, 1152921504606846975L, 2305843009213693951L, 4611686018427387903L, Long.MAX_VALUE, -1L };
  

























  private static final int[] mIBY64 = { 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 2, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 3, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5 };
  









  private static final int MAXLONG = 64;
  









  private int mLength;
  









  private int mBit;
  









  private long[] mPol;
  










  public GF2nONBElement(GF2nONBField gf2n, SecureRandom rand)
  {
    mField = gf2n;
    mDegree = mField.getDegree();
    mLength = gf2n.getONBLength();
    mBit = gf2n.getONBBit();
    mPol = new long[mLength];
    if (mLength > 1)
    {
      for (int j = 0; j < mLength - 1; j++)
      {
        mPol[j] = rand.nextLong();
      }
      long last = rand.nextLong();
      mPol[(mLength - 1)] = (last >>> 64 - mBit);
    }
    else
    {
      mPol[0] = rand.nextLong();
      mPol[0] >>>= 64 - mBit;
    }
  }
  






  public GF2nONBElement(GF2nONBField gf2n, byte[] e)
  {
    mField = gf2n;
    mDegree = mField.getDegree();
    mLength = gf2n.getONBLength();
    mBit = gf2n.getONBBit();
    mPol = new long[mLength];
    assign(e);
  }
  







  public GF2nONBElement(GF2nONBField gf2n, BigInteger val)
  {
    mField = gf2n;
    mDegree = mField.getDegree();
    mLength = gf2n.getONBLength();
    mBit = gf2n.getONBBit();
    mPol = new long[mLength];
    assign(val);
  }
  







  private GF2nONBElement(GF2nONBField gf2n, long[] val)
  {
    mField = gf2n;
    mDegree = mField.getDegree();
    mLength = gf2n.getONBLength();
    mBit = gf2n.getONBBit();
    mPol = val;
  }
  










  public GF2nONBElement(GF2nONBElement gf2n)
  {
    mField = mField;
    mDegree = mField.getDegree();
    mLength = ((GF2nONBField)mField).getONBLength();
    mBit = ((GF2nONBField)mField).getONBBit();
    mPol = new long[mLength];
    assign(gf2n.getElement());
  }
  





  public Object clone()
  {
    return new GF2nONBElement(this);
  }
  






  public static GF2nONBElement ZERO(GF2nONBField gf2n)
  {
    long[] polynomial = new long[gf2n.getONBLength()];
    return new GF2nONBElement(gf2n, polynomial);
  }
  






  public static GF2nONBElement ONE(GF2nONBField gf2n)
  {
    int mLength = gf2n.getONBLength();
    long[] polynomial = new long[mLength];
    

    for (int i = 0; i < mLength - 1; i++)
    {
      polynomial[i] = -1L;
    }
    polynomial[(mLength - 1)] = mMaxmask[(gf2n.getONBBit() - 1)];
    
    return new GF2nONBElement(gf2n, polynomial);
  }
  







  void assignZero()
  {
    mPol = new long[mLength];
  }
  




  void assignOne()
  {
    for (int i = 0; i < mLength - 1; i++)
    {
      mPol[i] = -1L;
    }
    mPol[(mLength - 1)] = mMaxmask[(mBit - 1)];
  }
  





  private void assign(BigInteger val)
  {
    assign(val.toByteArray());
  }
  





  private void assign(long[] val)
  {
    System.arraycopy(val, 0, mPol, 0, mLength);
  }
  









  private void assign(byte[] val)
  {
    mPol = new long[mLength];
    for (int j = 0; j < val.length; j++)
    {
      mPol[(j >>> 3)] |= (val[(val.length - 1 - j)] & 0xFF) << ((j & 0x7) << 3);
    }
  }
  










  public boolean isZero()
  {
    boolean result = true;
    
    for (int i = 0; (i < mLength) && (result); i++)
    {
      result = (result) && ((mPol[i] & 0xFFFFFFFFFFFFFFFF) == 0L);
    }
    
    return result;
  }
  






  public boolean isOne()
  {
    boolean result = true;
    
    for (int i = 0; (i < mLength - 1) && (result); i++)
    {
      result = (result) && ((mPol[i] & 0xFFFFFFFFFFFFFFFF) == -1L);
    }
    

    if (result)
    {
      result = (result) && ((mPol[(mLength - 1)] & mMaxmask[(mBit - 1)]) == mMaxmask[(mBit - 1)]);
    }
    

    return result;
  }
  







  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2nONBElement)))
    {
      return false;
    }
    
    GF2nONBElement otherElem = (GF2nONBElement)other;
    
    for (int i = 0; i < mLength; i++)
    {
      if (mPol[i] != mPol[i])
      {
        return false;
      }
    }
    
    return true;
  }
  



  public int hashCode()
  {
    return mPol.hashCode();
  }
  











  public boolean testRightmostBit()
  {
    return (mPol[(mLength - 1)] & mBitmask[(mBit - 1)]) != 0L;
  }
  









  boolean testBit(int index)
  {
    if ((index < 0) || (index > mDegree))
    {
      return false;
    }
    long test = mPol[(index >>> 6)] & mBitmask[(index & 0x3F)];
    return test != 0L;
  }
  




  private long[] getElement()
  {
    long[] result = new long[mPol.length];
    System.arraycopy(mPol, 0, result, 0, mPol.length);
    
    return result;
  }
  






  private long[] getElementReverseOrder()
  {
    long[] result = new long[mPol.length];
    for (int i = 0; i < mDegree; i++)
    {
      if (testBit(mDegree - i - 1))
      {
        result[(i >>> 6)] |= mBitmask[(i & 0x3F)];
      }
    }
    return result;
  }
  




  void reverseOrder()
  {
    mPol = getElementReverseOrder();
  }
  










  public GFElement add(GFElement addend)
    throws RuntimeException
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.addToThis(addend);
    return result;
  }
  





  public void addToThis(GFElement addend)
    throws RuntimeException
  {
    if (!(addend instanceof GF2nONBElement))
    {
      throw new RuntimeException();
    }
    if (!mField.equals(mField))
    {
      throw new RuntimeException();
    }
    
    for (int i = 0; i < mLength; i++)
    {
      mPol[i] ^= mPol[i];
    }
  }
  





  public GF2nElement increase()
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.increaseThis();
    return result;
  }
  



  public void increaseThis()
  {
    addToThis(ONE((GF2nONBField)mField));
  }
  






  public GFElement multiply(GFElement factor)
    throws RuntimeException
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.multiplyThisBy(factor);
    return result;
  }
  






  public void multiplyThisBy(GFElement factor)
    throws RuntimeException
  {
    if (!(factor instanceof GF2nONBElement))
    {
      throw new RuntimeException("The elements have different representation: not yet implemented");
    }
    
    if (!mField.equals(mField))
    {
      throw new RuntimeException();
    }
    
    if (equals(factor))
    {
      squareThis();

    }
    else
    {
      long[] a = mPol;
      long[] b = mPol;
      long[] c = new long[mLength];
      
      int[][] m = mField).mMult;
      

      int degf = mLength - 1;
      int degb = mBit - 1;
      int s = 0;
      
      long TWOTOMAXLONGM1 = mBitmask[63];
      long TWOTODEGB = mBitmask[degb];
      







      for (int k = 0; k < mDegree; k++)
      {

        s = 0;
        
        for (int i = 0; i < mDegree; i++)
        {



          int fielda = mIBY64[i];
          


          int bita = i & 0x3F;
          


          int fieldb = mIBY64[m[i][0]];
          


          int bitb = m[i][0] & 0x3F;
          
          if ((a[fielda] & mBitmask[bita]) != 0L)
          {

            if ((b[fieldb] & mBitmask[bitb]) != 0L)
            {
              s ^= 0x1;
            }
            
            if (m[i][1] != -1)
            {



              fieldb = mIBY64[m[i][1]];
              


              bitb = m[i][1] & 0x3F;
              
              if ((b[fieldb] & mBitmask[bitb]) != 0L)
              {
                s ^= 0x1;
              }
            }
          }
        }
        
        int fielda = mIBY64[k];
        int bita = k & 0x3F;
        
        if (s != 0)
        {
          c[fielda] ^= mBitmask[bita];
        }
        



        if (mLength > 1)
        {



          boolean old = (a[degf] & 1L) == 1L;
          
          for (int i = degf - 1; i >= 0; i--)
          {
            boolean now = (a[i] & 1L) != 0L;
            
            a[i] >>>= 1;
            
            if (old)
            {
              a[i] ^= TWOTOMAXLONGM1;
            }
            
            old = now;
          }
          a[degf] >>>= 1;
          
          if (old)
          {
            a[degf] ^= TWOTODEGB;
          }
          


          old = (b[degf] & 1L) == 1L;
          
          for (int i = degf - 1; i >= 0; i--)
          {
            boolean now = (b[i] & 1L) != 0L;
            
            b[i] >>>= 1;
            
            if (old)
            {
              b[i] ^= TWOTOMAXLONGM1;
            }
            
            old = now;
          }
          
          b[degf] >>>= 1;
          
          if (old)
          {
            b[degf] ^= TWOTODEGB;
          }
        }
        else
        {
          boolean old = (a[0] & 1L) == 1L;
          a[0] >>>= 1;
          
          if (old)
          {
            a[0] ^= TWOTODEGB;
          }
          
          old = (b[0] & 1L) == 1L;
          b[0] >>>= 1;
          
          if (old)
          {
            b[0] ^= TWOTODEGB;
          }
        }
      }
      assign(c);
    }
  }
  





  public GF2nElement square()
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.squareThis();
    return result;
  }
  




  public void squareThis()
  {
    long[] pol = getElement();
    
    int f = mLength - 1;
    int b = mBit - 1;
    


    long TWOTOMAXLONGM1 = mBitmask[63];
    

    boolean old = (pol[f] & mBitmask[b]) != 0L;
    
    for (int i = 0; i < f; i++)
    {

      boolean now = (pol[i] & TWOTOMAXLONGM1) != 0L;
      
      pol[i] <<= 1;
      
      if (old)
      {
        pol[i] ^= 1L;
      }
      
      old = now;
    }
    boolean now = (pol[f] & mBitmask[b]) != 0L;
    
    pol[f] <<= 1;
    
    if (old)
    {
      pol[f] ^= 1L;
    }
    


    if (now)
    {
      pol[f] ^= mBitmask[(b + 1)];
    }
    
    assign(pol);
  }
  






  public GFElement invert()
    throws ArithmeticException
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.invertThis();
    return result;
  }
  






  public void invertThis()
    throws ArithmeticException
  {
    if (isZero())
    {
      throw new ArithmeticException();
    }
    int r = 31;
    

    for (boolean found = false; (!found) && (r >= 0); r--)
    {

      if ((mDegree - 1 & mBitmask[r]) != 0L)
      {
        found = true;
      }
    }
    r++;
    
    GF2nElement m = ZERO((GF2nONBField)mField);
    GF2nElement n = new GF2nONBElement(this);
    
    int k = 1;
    
    for (int i = r - 1; i >= 0; i--)
    {
      m = (GF2nElement)n.clone();
      for (int j = 1; j <= k; j++)
      {
        m.squareThis();
      }
      
      n.multiplyThisBy(m);
      
      k <<= 1;
      if ((mDegree - 1 & mBitmask[i]) != 0L)
      {
        n.squareThis();
        
        n.multiplyThisBy(this);
        
        k++;
      }
    }
    n.squareThis();
  }
  





  public GF2nElement squareRoot()
  {
    GF2nONBElement result = new GF2nONBElement(this);
    result.squareRootThis();
    return result;
  }
  




  public void squareRootThis()
  {
    long[] pol = getElement();
    
    int f = mLength - 1;
    int b = mBit - 1;
    


    long TWOTOMAXLONGM1 = mBitmask[63];
    

    boolean old = (pol[0] & 1L) != 0L;
    
    for (int i = f; i >= 0; i--)
    {
      boolean now = (pol[i] & 1L) != 0L;
      pol[i] >>>= 1;
      
      if (old)
      {
        if (i == f)
        {
          pol[i] ^= mBitmask[b];
        }
        else
        {
          pol[i] ^= TWOTOMAXLONGM1;
        }
      }
      old = now;
    }
    assign(pol);
  }
  









  public int trace()
  {
    int result = 0;
    
    int max = mLength - 1;
    
    for (int i = 0; i < max; i++)
    {

      for (int j = 0; j < 64; j++)
      {

        if ((mPol[i] & mBitmask[j]) != 0L)
        {
          result ^= 0x1;
        }
      }
    }
    
    int b = mBit;
    
    for (int j = 0; j < b; j++)
    {

      if ((mPol[max] & mBitmask[j]) != 0L)
      {
        result ^= 0x1;
      }
    }
    return result;
  }
  







  public GF2nElement solveQuadraticEquation()
    throws RuntimeException
  {
    if (trace() == 1)
    {
      throw new RuntimeException();
    }
    
    long TWOTOMAXLONGM1 = mBitmask[63];
    long ZERO = 0L;
    long ONE = 1L;
    
    long[] p = new long[mLength];
    long z = 0L;
    int j = 1;
    for (int i = 0; i < mLength - 1; i++)
    {

      for (j = 1; j < 64; j++)
      {


        if ((((mBitmask[j] & mPol[i]) == ZERO) || ((z & mBitmask[(j - 1)]) == ZERO)) && (((mPol[i] & mBitmask[j]) != ZERO) || ((z & mBitmask[(j - 1)]) != ZERO)))
        {
          z ^= mBitmask[j];
        }
      }
      p[i] = z;
      
      if ((((TWOTOMAXLONGM1 & z) != ZERO) && ((ONE & mPol[(i + 1)]) == ONE)) || (((TWOTOMAXLONGM1 & z) == ZERO) && ((ONE & mPol[(i + 1)]) == ZERO)))
      {

        z = ZERO;
      }
      else
      {
        z = ONE;
      }
    }
    
    int b = mDegree & 0x3F;
    
    long LASTLONG = mPol[(mLength - 1)];
    
    for (j = 1; j < b; j++)
    {
      if ((((mBitmask[j] & LASTLONG) == ZERO) || ((mBitmask[(j - 1)] & z) == ZERO)) && (((mBitmask[j] & LASTLONG) != ZERO) || ((mBitmask[(j - 1)] & z) != ZERO)))
      {
        z ^= mBitmask[j];
      }
    }
    p[(mLength - 1)] = z;
    return new GF2nONBElement((GF2nONBField)mField, p);
  }
  









  public String toString()
  {
    return toString(16);
  }
  








  public String toString(int radix)
  {
    String s = "";
    
    long[] a = getElement();
    int b = mBit;
    
    if (radix == 2)
    {

      for (int j = b - 1; j >= 0; j--)
      {
        if ((a[(a.length - 1)] & 1L << j) == 0L)
        {
          s = s + "0";
        }
        else
        {
          s = s + "1";
        }
      }
      
      for (int i = a.length - 2; i >= 0; i--)
      {
        for (int j = 63; j >= 0; j--)
        {
          if ((a[i] & mBitmask[j]) == 0L)
          {
            s = s + "0";
          }
          else
          {
            s = s + "1";
          }
        }
      }
    }
    else if (radix == 16)
    {
      char[] HEX_CHARS = { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'a', 'b', 'c', 'd', 'e', 'f' };
      
      for (int i = a.length - 1; i >= 0; i--)
      {
        s = s + HEX_CHARS[((int)(a[i] >>> 60) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 56) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 52) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 48) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 44) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 40) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 36) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 32) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 28) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 24) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 20) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 16) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 12) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 8) & 0xF)];
        s = s + HEX_CHARS[((int)(a[i] >>> 4) & 0xF)];
        s = s + HEX_CHARS[((int)a[i] & 0xF)];
        s = s + " ";
      }
    }
    return s;
  }
  








  public BigInteger toFlexiBigInt()
  {
    return new BigInteger(1, toByteArray());
  }
  








  public byte[] toByteArray()
  {
    int k = (mDegree - 1 >> 3) + 1;
    byte[] result = new byte[k];
    
    for (int i = 0; i < k; i++)
    {
      result[(k - i - 1)] = ((byte)(int)((mPol[(i >>> 3)] & 255L << ((i & 0x7) << 3)) >>> ((i & 0x7) << 3)));
    }
    return result;
  }
}
