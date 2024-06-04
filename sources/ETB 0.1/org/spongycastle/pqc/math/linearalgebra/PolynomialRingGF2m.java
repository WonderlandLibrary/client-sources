package org.spongycastle.pqc.math.linearalgebra;







public class PolynomialRingGF2m
{
  private GF2mField field;
  





  private PolynomialGF2mSmallM p;
  





  protected PolynomialGF2mSmallM[] sqMatrix;
  





  protected PolynomialGF2mSmallM[] sqRootMatrix;
  






  public PolynomialRingGF2m(GF2mField field, PolynomialGF2mSmallM p)
  {
    this.field = field;
    this.p = p;
    computeSquaringMatrix();
    computeSquareRootMatrix();
  }
  



  public PolynomialGF2mSmallM[] getSquaringMatrix()
  {
    return sqMatrix;
  }
  



  public PolynomialGF2mSmallM[] getSquareRootMatrix()
  {
    return sqRootMatrix;
  }
  




  private void computeSquaringMatrix()
  {
    int numColumns = p.getDegree();
    sqMatrix = new PolynomialGF2mSmallM[numColumns];
    for (int i = 0; i < numColumns >> 1; i++)
    {
      int[] monomCoeffs = new int[(i << 1) + 1];
      monomCoeffs[(i << 1)] = 1;
      sqMatrix[i] = new PolynomialGF2mSmallM(field, monomCoeffs);
    }
    for (int i = numColumns >> 1; i < numColumns; i++)
    {
      int[] monomCoeffs = new int[(i << 1) + 1];
      monomCoeffs[(i << 1)] = 1;
      PolynomialGF2mSmallM monomial = new PolynomialGF2mSmallM(field, monomCoeffs);
      
      sqMatrix[i] = monomial.mod(p);
    }
  }
  




  private void computeSquareRootMatrix()
  {
    int numColumns = p.getDegree();
    

    PolynomialGF2mSmallM[] tmpMatrix = new PolynomialGF2mSmallM[numColumns];
    for (int i = numColumns - 1; i >= 0; i--)
    {
      tmpMatrix[i] = new PolynomialGF2mSmallM(sqMatrix[i]);
    }
    

    sqRootMatrix = new PolynomialGF2mSmallM[numColumns];
    for (int i = numColumns - 1; i >= 0; i--)
    {
      sqRootMatrix[i] = new PolynomialGF2mSmallM(field, i);
    }
    


    for (int i = 0; i < numColumns; i++)
    {

      if (tmpMatrix[i].getCoefficient(i) == 0)
      {
        boolean foundNonZero = false;
        
        for (int j = i + 1; j < numColumns; j++)
        {
          if (tmpMatrix[j].getCoefficient(i) != 0)
          {

            foundNonZero = true;
            swapColumns(tmpMatrix, i, j);
            swapColumns(sqRootMatrix, i, j);
            
            j = numColumns;
          }
        }
        

        if (!foundNonZero)
        {

          throw new ArithmeticException("Squaring matrix is not invertible.");
        }
      }
      


      int coef = tmpMatrix[i].getCoefficient(i);
      int invCoef = field.inverse(coef);
      tmpMatrix[i].multThisWithElement(invCoef);
      sqRootMatrix[i].multThisWithElement(invCoef);
      

      for (int j = 0; j < numColumns; j++)
      {
        if (j != i)
        {
          coef = tmpMatrix[j].getCoefficient(i);
          if (coef != 0)
          {

            PolynomialGF2mSmallM tmpSqColumn = tmpMatrix[i].multWithElement(coef);
            
            PolynomialGF2mSmallM tmpInvColumn = sqRootMatrix[i].multWithElement(coef);
            tmpMatrix[j].addToThis(tmpSqColumn);
            sqRootMatrix[j].addToThis(tmpInvColumn);
          }
        }
      }
    }
  }
  

  private static void swapColumns(PolynomialGF2mSmallM[] matrix, int first, int second)
  {
    PolynomialGF2mSmallM tmp = matrix[first];
    matrix[first] = matrix[second];
    matrix[second] = tmp;
  }
}
