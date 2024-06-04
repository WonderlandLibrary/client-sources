package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;

































public final class GoppaCode
{
  private GoppaCode() {}
  
  public static class MaMaPe
  {
    private GF2Matrix s;
    private GF2Matrix h;
    private Permutation p;
    
    public MaMaPe(GF2Matrix s, GF2Matrix h, Permutation p)
    {
      this.s = s;
      this.h = h;
      this.p = p;
    }
    



    public GF2Matrix getFirstMatrix()
    {
      return s;
    }
    



    public GF2Matrix getSecondMatrix()
    {
      return h;
    }
    



    public Permutation getPermutation()
    {
      return p;
    }
  }
  







  public static class MatrixSet
  {
    private GF2Matrix g;
    






    private int[] setJ;
    






    public MatrixSet(GF2Matrix g, int[] setJ)
    {
      this.g = g;
      this.setJ = setJ;
    }
    



    public GF2Matrix getG()
    {
      return g;
    }
    




    public int[] getSetJ()
    {
      return setJ;
    }
  }
  









  public static GF2Matrix createCanonicalCheckMatrix(GF2mField field, PolynomialGF2mSmallM gp)
  {
    int m = field.getDegree();
    int n = 1 << m;
    int t = gp.getDegree();
    


    int[][] hArray = new int[t][n];
    

    int[][] yz = new int[t][n];
    for (int j = 0; j < n; j++)
    {

      yz[0][j] = field.inverse(gp.evaluateAt(j));
    }
    
    for (int i = 1; i < t; i++)
    {
      for (int j = 0; j < n; j++)
      {

        yz[i][j] = field.mult(yz[(i - 1)][j], j);
      }
    }
    

    for (int i = 0; i < t; i++)
    {
      for (int j = 0; j < n; j++)
      {
        for (int k = 0; k <= i; k++)
        {
          hArray[i][j] = field.add(hArray[i][j], field.mult(yz[k][j], gp
            .getCoefficient(t + k - i)));
        }
      }
    }
    


    int[][] result = new int[t * m][n + 31 >>> 5];
    
    for (int j = 0; j < n; j++)
    {
      int q = j >>> 5;
      int r = 1 << (j & 0x1F);
      for (int i = 0; i < t; i++)
      {
        int e = hArray[i][j];
        for (int u = 0; u < m; u++)
        {
          int b = e >>> u & 0x1;
          if (b != 0)
          {
            int ind = (i + 1) * m - u - 1;
            result[ind][q] ^= r;
          }
        }
      }
    }
    
    return new GF2Matrix(n, result);
  }
  











  public static MaMaPe computeSystematicForm(GF2Matrix h, SecureRandom sr)
  {
    int n = h.getNumColumns();
    
    GF2Matrix s = null;
    
    boolean found = false;
    Permutation p;
    GF2Matrix hp;
    GF2Matrix sInv;
    do { p = new Permutation(n, sr);
      hp = (GF2Matrix)h.rightMultiply(p);
      sInv = hp.getLeftSubMatrix();
      try
      {
        found = true;
        s = (GF2Matrix)sInv.computeInverse();
      }
      catch (ArithmeticException ae)
      {
        found = false;
      }
      
    } while (!found);
    
    GF2Matrix shp = (GF2Matrix)s.rightMultiply(hp);
    GF2Matrix m = shp.getRightSubMatrix();
    
    return new MaMaPe(sInv, m, p);
  }
  













  public static GF2Vector syndromeDecode(GF2Vector syndVec, GF2mField field, PolynomialGF2mSmallM gp, PolynomialGF2mSmallM[] sqRootMatrix)
  {
    int n = 1 << field.getDegree();
    

    GF2Vector errors = new GF2Vector(n);
    

    if (!syndVec.isZero())
    {


      PolynomialGF2mSmallM syndrome = new PolynomialGF2mSmallM(syndVec.toExtensionFieldVector(field));
      

      PolynomialGF2mSmallM t = syndrome.modInverse(gp);
      

      PolynomialGF2mSmallM tau = t.addMonomial(1);
      tau = tau.modSquareRootMatrix(sqRootMatrix);
      

      PolynomialGF2mSmallM[] ab = tau.modPolynomialToFracton(gp);
      

      PolynomialGF2mSmallM a2 = ab[0].multiply(ab[0]);
      PolynomialGF2mSmallM b2 = ab[1].multiply(ab[1]);
      PolynomialGF2mSmallM xb2 = b2.multWithMonomial(1);
      PolynomialGF2mSmallM a2plusXb2 = a2.add(xb2);
      

      int headCoeff = a2plusXb2.getHeadCoefficient();
      int invHeadCoeff = field.inverse(headCoeff);
      PolynomialGF2mSmallM elp = a2plusXb2.multWithElement(invHeadCoeff);
      

      for (int i = 0; i < n; i++)
      {

        int z = elp.evaluateAt(i);
        
        if (z == 0)
        {

          errors.setBit(i);
        }
      }
    }
    
    return errors;
  }
}
