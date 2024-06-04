package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;
import java.util.Random;
import java.util.Vector;




























































































public class GF2nONBField
  extends GF2nField
{
  private static final int MAXLONG = 64;
  private int mLength;
  private int mBit;
  private int mType;
  int[][] mMult;
  
  public GF2nONBField(int deg, SecureRandom random)
    throws RuntimeException
  {
    super(random);
    
    if (deg < 3)
    {
      throw new IllegalArgumentException("k must be at least 3");
    }
    
    mDegree = deg;
    mLength = (mDegree / 64);
    mBit = (mDegree & 0x3F);
    if (mBit == 0)
    {
      mBit = 64;
    }
    else
    {
      mLength += 1;
    }
    
    computeType();
    


    if (mType < 3)
    {
      mMult = new int[mDegree][2];
      for (int i = 0; i < mDegree; i++)
      {
        mMult[i][0] = -1;
        mMult[i][1] = -1;
      }
      computeMultMatrix();
    }
    else
    {
      throw new RuntimeException("\nThe type of this field is " + mType);
    }
    
    computeFieldPolynomial();
    fields = new Vector();
    matrices = new Vector();
  }
  




  int getONBLength()
  {
    return mLength;
  }
  
  int getONBBit()
  {
    return mBit;
  }
  


















  protected GF2nElement getRandomRoot(GF2Polynomial polynomial)
  {
    GF2nPolynomial g = new GF2nPolynomial(polynomial, this);
    int gDegree = g.getDegree();
    


    while (gDegree > 1)
    {
      GF2nPolynomial h;
      int hDegree;
      do {
        GF2nElement u = new GF2nONBElement(this, random);
        GF2nPolynomial ut = new GF2nPolynomial(2, GF2nONBElement.ZERO(this));
        
        ut.set(1, u);
        GF2nPolynomial c = new GF2nPolynomial(ut);
        
        for (int i = 1; i <= mDegree - 1; i++)
        {

          c = c.multiplyAndReduce(c, g);
          c = c.add(ut);
        }
        
        h = c.gcd(g);
        

        hDegree = h.getDegree();
        gDegree = g.getDegree();
      }
      while ((hDegree == 0) || (hDegree == gDegree));
      
      if (hDegree << 1 > gDegree)
      {
        g = g.quotient(h);

      }
      else
      {
        g = new GF2nPolynomial(h);
      }
      gDegree = g.getDegree();
    }
    
    return g.at(0);
  }
  









  protected void computeCOBMatrix(GF2nField B1)
  {
    if (mDegree != mDegree)
    {
      throw new IllegalArgumentException("GF2nField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
    }
    




    GF2Polynomial[] COBMatrix = new GF2Polynomial[mDegree];
    for (int i = 0; i < mDegree; i++)
    {
      COBMatrix[i] = new GF2Polynomial(mDegree);
    }
    
    GF2nElement u;
    
    do
    {
      u = B1.getRandomRoot(fieldPolynomial);
    }
    while (u.isZero());
    
    GF2nElement[] gamma = new GF2nPolynomialElement[mDegree];
    
    gamma[0] = ((GF2nElement)u.clone());
    for (i = 1; i < mDegree; i++)
    {
      gamma[i] = gamma[(i - 1)].square();
    }
    
    for (i = 0; i < mDegree; i++)
    {
      for (int j = 0; j < mDegree; j++)
      {
        if (gamma[i].testBit(j))
        {
          COBMatrix[(mDegree - j - 1)].setBit(mDegree - i - 1);
        }
      }
    }
    
    fields.addElement(B1);
    matrices.addElement(COBMatrix);
    fields.addElement(this);
    matrices.addElement(invertMatrix(COBMatrix));
  }
  






  protected void computeFieldPolynomial()
  {
    if (mType == 1)
    {
      fieldPolynomial = new GF2Polynomial(mDegree + 1, "ALL");
    }
    else if (mType == 2)
    {

      GF2Polynomial q = new GF2Polynomial(mDegree + 1, "ONE");
      
      GF2Polynomial p = new GF2Polynomial(mDegree + 1, "X");
      p.addToThis(q);
      


      for (int i = 1; i < mDegree; i++)
      {

        GF2Polynomial r = q;
        
        q = p;
        
        p = q.shiftLeft();
        p.addToThis(r);
      }
      fieldPolynomial = p;
    }
  }
  







  int[][] invMatrix(int[][] a)
  {
    int[][] A = new int[mDegree][mDegree];
    A = a;
    int[][] inv = new int[mDegree][mDegree];
    
    for (int i = 0; i < mDegree; i++)
    {
      inv[i][i] = 1;
    }
    
    for (int i = 0; i < mDegree; i++)
    {
      for (int j = i; j < mDegree; j++)
      {
        A[(mDegree - 1 - i)][j] = A[i][i];
      }
    }
    return (int[][])null;
  }
  
  private void computeType()
    throws RuntimeException
  {
    if ((mDegree & 0x7) == 0)
    {
      throw new RuntimeException("The extension degree is divisible by 8!");
    }
    

    int s = 0;
    int k = 0;
    mType = 1;
    for (int d = 0; d != 1; mType += 1)
    {
      s = mType * mDegree + 1;
      if (IntegerFunctions.isPrime(s))
      {
        k = IntegerFunctions.order(2, s);
        d = IntegerFunctions.gcd(mType * mDegree / k, mDegree);
      }
    }
    mType -= 1;
    if (mType == 1)
    {
      s = (mDegree << 1) + 1;
      if (IntegerFunctions.isPrime(s))
      {
        k = IntegerFunctions.order(2, s);
        int d = IntegerFunctions.gcd((mDegree << 1) / k, mDegree);
        if (d == 1)
        {
          mType += 1;
        }
      }
    }
  }
  

  private void computeMultMatrix()
  {
    if ((mType & 0x7) != 0)
    {
      int p = mType * mDegree + 1;
      



      int[] F = new int[p];
      int u;
      int u;
      if (mType == 1)
      {
        u = 1;
      } else { int u;
        if (mType == 2)
        {
          u = p - 1;
        }
        else
        {
          u = elementOfOrder(mType, p);
        }
      }
      int w = 1;
      
      for (int j = 0; j < mType; j++)
      {
        int n = w;
        
        for (int i = 0; i < mDegree; i++)
        {
          F[n] = i;
          n = (n << 1) % p;
          if (n < 0)
          {
            n += p;
          }
        }
        w = u * w % p;
        if (w < 0)
        {
          w += p;
        }
      }
      


      if (mType == 1)
      {
        for (int k = 1; k < p - 1; k++)
        {
          if (mMult[F[(k + 1)]][0] == -1)
          {
            mMult[F[(k + 1)]][0] = F[(p - k)];
          }
          else
          {
            mMult[F[(k + 1)]][1] = F[(p - k)];
          }
        }
        
        int m_2 = mDegree >> 1;
        for (int k = 1; k <= m_2; k++)
        {

          if (mMult[(k - 1)][0] == -1)
          {
            mMult[(k - 1)][0] = (m_2 + k - 1);
          }
          else
          {
            mMult[(k - 1)][1] = (m_2 + k - 1);
          }
          
          if (mMult[(m_2 + k - 1)][0] == -1)
          {
            mMult[(m_2 + k - 1)][0] = (k - 1);
          }
          else
          {
            mMult[(m_2 + k - 1)][1] = (k - 1);
          }
        }
      }
      else if (mType == 2)
      {
        for (int k = 1; k < p - 1; k++)
        {
          if (mMult[F[(k + 1)]][0] == -1)
          {
            mMult[F[(k + 1)]][0] = F[(p - k)];
          }
          else
          {
            mMult[F[(k + 1)]][1] = F[(p - k)];
          }
        }
      }
      else
      {
        throw new RuntimeException("only type 1 or type 2 implemented");
      }
    }
    else
    {
      throw new RuntimeException("bisher nur fuer Gausssche Normalbasen implementiert");
    }
  }
  

  private int elementOfOrder(int k, int p)
  {
    Random random = new Random();
    int m = 0;
    while (m == 0)
    {
      m = random.nextInt();
      m %= (p - 1);
      if (m < 0)
      {
        m += p - 1;
      }
    }
    
    int l = IntegerFunctions.order(m, p);
    
    while ((l % k != 0) || (l == 0))
    {
      while (m == 0)
      {
        m = random.nextInt();
        m %= (p - 1);
        if (m < 0)
        {
          m += p - 1;
        }
      }
      l = IntegerFunctions.order(m, p);
    }
    int r = m;
    
    l = k / l;
    
    for (int i = 2; i <= l; i++)
    {
      r *= m;
    }
    
    return r;
  }
}
