package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;
import java.util.Vector;


















public class GF2nPolynomialField
  extends GF2nField
{
  GF2Polynomial[] squaringMatrix;
  private boolean isTrinomial = false;
  

  private boolean isPentanomial = false;
  

  private int tc;
  

  private int[] pc = new int[3];
  







  public GF2nPolynomialField(int deg, SecureRandom random)
  {
    super(random);
    
    if (deg < 3)
    {
      throw new IllegalArgumentException("k must be at least 3");
    }
    mDegree = deg;
    computeFieldPolynomial();
    computeSquaringMatrix();
    fields = new Vector();
    matrices = new Vector();
  }
  










  public GF2nPolynomialField(int deg, SecureRandom random, boolean file)
  {
    super(random);
    
    if (deg < 3)
    {
      throw new IllegalArgumentException("k must be at least 3");
    }
    mDegree = deg;
    if (file)
    {
      computeFieldPolynomial();
    }
    else
    {
      computeFieldPolynomial2();
    }
    computeSquaringMatrix();
    fields = new Vector();
    matrices = new Vector();
  }
  









  public GF2nPolynomialField(int deg, SecureRandom random, GF2Polynomial polynomial)
    throws RuntimeException
  {
    super(random);
    
    if (deg < 3)
    {
      throw new IllegalArgumentException("degree must be at least 3");
    }
    if (polynomial.getLength() != deg + 1)
    {
      throw new RuntimeException();
    }
    if (!polynomial.isIrreducible())
    {
      throw new RuntimeException();
    }
    mDegree = deg;
    
    fieldPolynomial = polynomial;
    computeSquaringMatrix();
    int k = 2;
    for (int j = 1; j < fieldPolynomial.getLength() - 1; j++)
    {
      if (fieldPolynomial.testBit(j))
      {
        k++;
        if (k == 3)
        {
          tc = j;
        }
        if (k <= 5)
        {
          pc[(k - 3)] = j;
        }
      }
    }
    if (k == 3)
    {
      isTrinomial = true;
    }
    if (k == 5)
    {
      isPentanomial = true;
    }
    fields = new Vector();
    matrices = new Vector();
  }
  






  public boolean isTrinomial()
  {
    return isTrinomial;
  }
  






  public boolean isPentanomial()
  {
    return isPentanomial;
  }
  






  public int getTc()
    throws RuntimeException
  {
    if (!isTrinomial)
    {
      throw new RuntimeException();
    }
    return tc;
  }
  






  public int[] getPc()
    throws RuntimeException
  {
    if (!isPentanomial)
    {
      throw new RuntimeException();
    }
    int[] result = new int[3];
    System.arraycopy(pc, 0, result, 0, 3);
    return result;
  }
  







  public GF2Polynomial getSquaringVector(int i)
  {
    return new GF2Polynomial(squaringMatrix[i]);
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
        GF2nElement u = new GF2nPolynomialElement(this, random);
        GF2nPolynomial ut = new GF2nPolynomial(2, GF2nPolynomialElement.ZERO(this));
        
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
      throw new IllegalArgumentException("GF2nPolynomialField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
    }
    

    if ((B1 instanceof GF2nONBField))
    {


      B1.computeCOBMatrix(this);
      return;
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
    
    GF2nElement[] gamma;
    if ((u instanceof GF2nONBElement))
    {
      GF2nElement[] gamma = new GF2nONBElement[mDegree];
      gamma[(mDegree - 1)] = GF2nONBElement.ONE((GF2nONBField)B1);
    }
    else
    {
      gamma = new GF2nPolynomialElement[mDegree];
      gamma[(mDegree - 1)] = 
        GF2nPolynomialElement.ONE((GF2nPolynomialField)B1);
    }
    gamma[(mDegree - 2)] = u;
    for (i = mDegree - 3; i >= 0; i--)
    {
      gamma[i] = ((GF2nElement)gamma[(i + 1)].multiply(u));
    }
    if ((B1 instanceof GF2nONBField))
    {

      for (i = 0; i < mDegree; i++)
      {
        for (int j = 0; j < mDegree; j++)
        {

          if (gamma[i].testBit(mDegree - j - 1))
          {
            COBMatrix[(mDegree - j - 1)].setBit(mDegree - i - 1);
          }
        }
      }
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
  





  private void computeSquaringMatrix()
  {
    GF2Polynomial[] d = new GF2Polynomial[mDegree - 1];
    
    squaringMatrix = new GF2Polynomial[mDegree];
    for (int i = 0; i < squaringMatrix.length; i++)
    {
      squaringMatrix[i] = new GF2Polynomial(mDegree, "ZERO");
    }
    
    for (i = 0; i < mDegree - 1; i++)
    {

      d[i] = new GF2Polynomial(1, "ONE").shiftLeft(mDegree + i).remainder(fieldPolynomial);
    }
    for (i = 1; i <= Math.abs(mDegree >> 1); i++)
    {
      for (int j = 1; j <= mDegree; j++)
      {
        if (d[(mDegree - (i << 1))].testBit(mDegree - j))
        {
          squaringMatrix[(j - 1)].setBit(mDegree - i);
        }
      }
    }
    for (i = Math.abs(mDegree >> 1) + 1; i <= mDegree; i++)
    {
      squaringMatrix[((i << 1) - mDegree - 1)].setBit(mDegree - i);
    }
  }
  




  protected void computeFieldPolynomial()
  {
    if (testTrinomials())
    {
      return;
    }
    if (testPentanomials())
    {
      return;
    }
    testRandom();
  }
  



  protected void computeFieldPolynomial2()
  {
    if (testTrinomials())
    {
      return;
    }
    if (testPentanomials())
    {
      return;
    }
    testRandom();
  }
  









  private boolean testTrinomials()
  {
    boolean done = false;
    int l = 0;
    
    fieldPolynomial = new GF2Polynomial(mDegree + 1);
    fieldPolynomial.setBit(0);
    fieldPolynomial.setBit(mDegree);
    for (int i = 1; (i < mDegree) && (!done); i++)
    {
      fieldPolynomial.setBit(i);
      done = fieldPolynomial.isIrreducible();
      l++;
      if (done)
      {
        isTrinomial = true;
        tc = i;
        return done;
      }
      fieldPolynomial.resetBit(i);
      done = fieldPolynomial.isIrreducible();
    }
    
    return done;
  }
  









  private boolean testPentanomials()
  {
    boolean done = false;
    int l = 0;
    
    fieldPolynomial = new GF2Polynomial(mDegree + 1);
    fieldPolynomial.setBit(0);
    fieldPolynomial.setBit(mDegree);
    for (int i = 1; (i <= mDegree - 3) && (!done); i++)
    {
      fieldPolynomial.setBit(i);
      for (int j = i + 1; (j <= mDegree - 2) && (!done); j++)
      {
        fieldPolynomial.setBit(j);
        for (int k = j + 1; (k <= mDegree - 1) && (!done); k++)
        {
          fieldPolynomial.setBit(k);
          if ((((mDegree & 0x1) != 0 ? 1 : 0) | ((i & 0x1) != 0 ? 1 : 0) | ((j & 0x1) != 0 ? 1 : 0) | ((k & 0x1) != 0 ? 1 : 0)) != 0)
          {

            done = fieldPolynomial.isIrreducible();
            l++;
            if (done)
            {
              isPentanomial = true;
              pc[0] = i;
              pc[1] = j;
              pc[2] = k;
              return done;
            }
          }
          fieldPolynomial.resetBit(k);
        }
        fieldPolynomial.resetBit(j);
      }
      fieldPolynomial.resetBit(i);
    }
    
    return done;
  }
  








  private boolean testRandom()
  {
    boolean done = false;
    
    fieldPolynomial = new GF2Polynomial(mDegree + 1);
    int l = 0;
    while (!done)
    {
      l++;
      fieldPolynomial.randomize();
      fieldPolynomial.setBit(mDegree);
      fieldPolynomial.setBit(0);
      if (fieldPolynomial.isIrreducible())
      {
        done = true;
        return done;
      }
    }
    
    return done;
  }
}
