package org.spongycastle.pqc.crypto.rainbow.util;








public class ComputeInField
{
  private short[][] A;
  






  short[] x;
  







  public ComputeInField() {}
  







  public short[] solveEquation(short[][] B, short[] b)
  {
    if (B.length != b.length)
    {
      return null;
    }
    







    try
    {
      A = new short[B.length][B.length + 1];
      
      x = new short[B.length];
      

      for (int i = 0; i < B.length; i++)
      {
        for (int j = 0; j < B[0].length; j++)
        {
          A[i][j] = B[i][j];
        }
      }
      



      for (int i = 0; i < b.length; i++)
      {
        A[i][b.length] = GF2Field.addElem(b[i], A[i][b.length]);
      }
      

      computeZerosUnder(false);
      substitute();
      
      return x;
    }
    catch (RuntimeException rte) {}
    

    return null;
  }
  















  public short[][] inverse(short[][] coef)
  {
    try
    {
      A = new short[coef.length][2 * coef.length];
      if (coef.length != coef[0].length)
      {
        throw new RuntimeException("The matrix is not invertible. Please choose another one!");
      }
      


      for (int i = 0; i < coef.length; i++)
      {
        for (int j = 0; j < coef.length; j++)
        {

          A[i][j] = coef[i][j];
        }
        
        for (int j = coef.length; j < 2 * coef.length; j++)
        {
          A[i][j] = 0;
        }
        A[i][(i + A.length)] = 1;
      }
      


      computeZerosUnder(true);
      

      for (int i = 0; i < A.length; i++)
      {
        short factor = GF2Field.invElem(A[i][i]);
        for (int j = i; j < 2 * A.length; j++)
        {
          A[i][j] = GF2Field.multElem(A[i][j], factor);
        }
      }
      

      computeZerosAbove();
      

      short[][] inverse = new short[A.length][A.length];
      for (int i = 0; i < A.length; i++)
      {
        for (int j = A.length; j < 2 * A.length; j++)
        {
          inverse[i][(j - A.length)] = A[i][j];
        }
      }
      return inverse;
    }
    catch (RuntimeException rte) {}
    


    return (short[][])null;
  }
  





















  private void computeZerosUnder(boolean usedForInverse)
    throws RuntimeException
  {
    short tmp = 0;
    int length;
    int length;
    if (usedForInverse)
    {
      length = 2 * A.length;

    }
    else
    {
      length = A.length + 1;
    }
    

    for (int k = 0; k < A.length - 1; k++)
    {
      for (int i = k + 1; i < A.length; i++)
      {
        short factor1 = A[i][k];
        short factor2 = GF2Field.invElem(A[k][k]);
        


        if (factor2 == 0)
        {
          throw new IllegalStateException("Matrix not invertible! We have to choose another one!");
        }
        
        for (int j = k; j < length; j++)
        {

          tmp = GF2Field.multElem(A[k][j], factor2);
          
          tmp = GF2Field.multElem(factor1, tmp);
          
          A[i][j] = GF2Field.addElem(A[i][j], tmp);
        }
      }
    }
  }
  











  private void computeZerosAbove()
    throws RuntimeException
  {
    short tmp = 0;
    for (int k = A.length - 1; k > 0; k--)
    {
      for (int i = k - 1; i >= 0; i--)
      {
        short factor1 = A[i][k];
        short factor2 = GF2Field.invElem(A[k][k]);
        if (factor2 == 0)
        {
          throw new RuntimeException("The matrix is not invertible");
        }
        for (int j = k; j < 2 * A.length; j++)
        {

          tmp = GF2Field.multElem(A[k][j], factor2);
          
          tmp = GF2Field.multElem(factor1, tmp);
          
          A[i][j] = GF2Field.addElem(A[i][j], tmp);
        }
      }
    }
  }
  

















  private void substitute()
    throws IllegalStateException
  {
    short temp = GF2Field.invElem(A[(A.length - 1)][(A.length - 1)]);
    if (temp == 0)
    {
      throw new IllegalStateException("The equation system is not solvable");
    }
    

    x[(A.length - 1)] = GF2Field.multElem(A[(A.length - 1)][A.length], temp);
    for (int i = A.length - 2; i >= 0; i--)
    {
      short tmp = A[i][A.length];
      for (int j = A.length - 1; j > i; j--)
      {
        temp = GF2Field.multElem(A[i][j], x[j]);
        tmp = GF2Field.addElem(tmp, temp);
      }
      
      temp = GF2Field.invElem(A[i][i]);
      if (temp == 0)
      {
        throw new IllegalStateException("Not solvable equation system");
      }
      x[i] = GF2Field.multElem(tmp, temp);
    }
  }
  













  public short[][] multiplyMatrix(short[][] M1, short[][] M2)
    throws RuntimeException
  {
    if (M1[0].length != M2.length)
    {
      throw new RuntimeException("Multiplication is not possible!");
    }
    short tmp = 0;
    A = new short[M1.length][M2[0].length];
    for (int i = 0; i < M1.length; i++)
    {
      for (int j = 0; j < M2.length; j++)
      {
        for (int k = 0; k < M2[0].length; k++)
        {
          tmp = GF2Field.multElem(M1[i][j], M2[j][k]);
          A[i][k] = GF2Field.addElem(A[i][k], tmp);
        }
      }
    }
    return A;
  }
  











  public short[] multiplyMatrix(short[][] M1, short[] m)
    throws RuntimeException
  {
    if (M1[0].length != m.length)
    {
      throw new RuntimeException("Multiplication is not possible!");
    }
    short tmp = 0;
    short[] B = new short[M1.length];
    for (int i = 0; i < M1.length; i++)
    {
      for (int j = 0; j < m.length; j++)
      {
        tmp = GF2Field.multElem(M1[i][j], m[j]);
        B[i] = GF2Field.addElem(B[i], tmp);
      }
    }
    return B;
  }
  









  public short[] addVect(short[] vector1, short[] vector2)
  {
    if (vector1.length != vector2.length)
    {
      throw new RuntimeException("Multiplication is not possible!");
    }
    short[] rslt = new short[vector1.length];
    for (int n = 0; n < rslt.length; n++)
    {
      rslt[n] = GF2Field.addElem(vector1[n], vector2[n]);
    }
    return rslt;
  }
  









  public short[][] multVects(short[] vector1, short[] vector2)
  {
    if (vector1.length != vector2.length)
    {
      throw new RuntimeException("Multiplication is not possible!");
    }
    short[][] rslt = new short[vector1.length][vector2.length];
    for (int i = 0; i < vector1.length; i++)
    {
      for (int j = 0; j < vector2.length; j++)
      {
        rslt[i][j] = GF2Field.multElem(vector1[i], vector2[j]);
      }
    }
    return rslt;
  }
  







  public short[] multVect(short scalar, short[] vector)
  {
    short[] rslt = new short[vector.length];
    for (int n = 0; n < rslt.length; n++)
    {
      rslt[n] = GF2Field.multElem(scalar, vector[n]);
    }
    return rslt;
  }
  







  public short[][] multMatrix(short scalar, short[][] matrix)
  {
    short[][] rslt = new short[matrix.length][matrix[0].length];
    for (int i = 0; i < matrix.length; i++)
    {
      for (int j = 0; j < matrix[0].length; j++)
      {
        rslt[i][j] = GF2Field.multElem(scalar, matrix[i][j]);
      }
    }
    return rslt;
  }
  









  public short[][] addSquareMatrix(short[][] matrix1, short[][] matrix2)
  {
    if ((matrix1.length != matrix2.length) || (matrix1[0].length != matrix2[0].length))
    {
      throw new RuntimeException("Addition is not possible!");
    }
    
    short[][] rslt = new short[matrix1.length][matrix1.length];
    for (int i = 0; i < matrix1.length; i++)
    {
      for (int j = 0; j < matrix2.length; j++)
      {
        rslt[i][j] = GF2Field.addElem(matrix1[i][j], matrix2[i][j]);
      }
    }
    return rslt;
  }
}
