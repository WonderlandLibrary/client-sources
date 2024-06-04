package org.spongycastle.pqc.math.linearalgebra;









public class GF2mMatrix
  extends Matrix
{
  protected GF2mField field;
  







  protected int[][] matrix;
  







  public GF2mMatrix(GF2mField field, byte[] enc)
  {
    this.field = field;
    

    int d = 8;
    int count = 1;
    while (field.getDegree() > d)
    {
      count++;
      d += 8;
    }
    
    if (enc.length < 5)
    {
      throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
    }
    

    numRows = ((enc[3] & 0xFF) << 24 ^ (enc[2] & 0xFF) << 16 ^ (enc[1] & 0xFF) << 8 ^ enc[0] & 0xFF);
    

    int n = count * numRows;
    
    if ((numRows <= 0) || ((enc.length - 4) % n != 0))
    {
      throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
    }
    

    numColumns = ((enc.length - 4) / n);
    
    matrix = new int[numRows][numColumns];
    count = 4;
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        for (int jj = 0; jj < d; jj += 8)
        {
          matrix[i][j] ^= (enc[(count++)] & 0xFF) << jj;
        }
        if (!this.field.isElementOfThisField(matrix[i][j]))
        {
          throw new IllegalArgumentException(" Error: given array is not encoded matrix over GF(2^m)");
        }
      }
    }
  }
  






  public GF2mMatrix(GF2mMatrix other)
  {
    numRows = numRows;
    numColumns = numColumns;
    field = field;
    matrix = new int[numRows][];
    for (int i = 0; i < numRows; i++)
    {
      matrix[i] = IntUtils.clone(matrix[i]);
    }
  }
  






  protected GF2mMatrix(GF2mField field, int[][] matrix)
  {
    this.field = field;
    this.matrix = matrix;
    numRows = matrix.length;
    numColumns = matrix[0].length;
  }
  



  public byte[] getEncoded()
  {
    int d = 8;
    int count = 1;
    while (field.getDegree() > d)
    {
      count++;
      d += 8;
    }
    
    byte[] bf = new byte[numRows * numColumns * count + 4];
    bf[0] = ((byte)(numRows & 0xFF));
    bf[1] = ((byte)(numRows >>> 8 & 0xFF));
    bf[2] = ((byte)(numRows >>> 16 & 0xFF));
    bf[3] = ((byte)(numRows >>> 24 & 0xFF));
    
    count = 4;
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        for (int jj = 0; jj < d; jj += 8)
        {
          bf[(count++)] = ((byte)(matrix[i][j] >>> jj));
        }
      }
    }
    
    return bf;
  }
  





  public boolean isZero()
  {
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        if (matrix[i][j] != 0)
        {
          return false;
        }
      }
    }
    return true;
  }
  





  public Matrix computeInverse()
  {
    if (numRows != numColumns)
    {
      throw new ArithmeticException("Matrix is not invertible.");
    }
    

    int[][] tmpMatrix = new int[numRows][numRows];
    for (int i = numRows - 1; i >= 0; i--)
    {
      tmpMatrix[i] = IntUtils.clone(matrix[i]);
    }
    

    int[][] invMatrix = new int[numRows][numRows];
    for (int i = numRows - 1; i >= 0; i--)
    {
      invMatrix[i][i] = 1;
    }
    


    for (int i = 0; i < numRows; i++)
    {

      if (tmpMatrix[i][i] == 0)
      {
        boolean foundNonZero = false;
        
        for (int j = i + 1; j < numRows; j++)
        {
          if (tmpMatrix[j][i] != 0)
          {

            foundNonZero = true;
            swapColumns(tmpMatrix, i, j);
            swapColumns(invMatrix, i, j);
            
            j = numRows;
          }
        }
        

        if (!foundNonZero)
        {

          throw new ArithmeticException("Matrix is not invertible.");
        }
      }
      

      int coef = tmpMatrix[i][i];
      int invCoef = field.inverse(coef);
      multRowWithElementThis(tmpMatrix[i], invCoef);
      multRowWithElementThis(invMatrix[i], invCoef);
      

      for (int j = 0; j < numRows; j++)
      {
        if (j != i)
        {
          coef = tmpMatrix[j][i];
          if (coef != 0)
          {
            int[] tmpRow = multRowWithElement(tmpMatrix[i], coef);
            int[] tmpInvRow = multRowWithElement(invMatrix[i], coef);
            addToRow(tmpRow, tmpMatrix[j]);
            addToRow(tmpInvRow, invMatrix[j]);
          }
        }
      }
    }
    
    return new GF2mMatrix(field, invMatrix);
  }
  
  private static void swapColumns(int[][] matrix, int first, int second)
  {
    int[] tmp = matrix[first];
    matrix[first] = matrix[second];
    matrix[second] = tmp;
  }
  
  private void multRowWithElementThis(int[] row, int element)
  {
    for (int i = row.length - 1; i >= 0; i--)
    {
      row[i] = field.mult(row[i], element);
    }
  }
  
  private int[] multRowWithElement(int[] row, int element)
  {
    int[] result = new int[row.length];
    for (int i = row.length - 1; i >= 0; i--)
    {
      result[i] = field.mult(row[i], element);
    }
    return result;
  }
  






  private void addToRow(int[] fromRow, int[] toRow)
  {
    for (int i = toRow.length - 1; i >= 0; i--)
    {
      toRow[i] = field.add(fromRow[i], toRow[i]);
    }
  }
  
  public Matrix rightMultiply(Matrix a)
  {
    throw new RuntimeException("Not implemented.");
  }
  
  public Matrix rightMultiply(Permutation perm)
  {
    throw new RuntimeException("Not implemented.");
  }
  
  public Vector leftMultiply(Vector vector)
  {
    throw new RuntimeException("Not implemented.");
  }
  
  public Vector rightMultiply(Vector vector)
  {
    throw new RuntimeException("Not implemented.");
  }
  








  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2mMatrix)))
    {
      return false;
    }
    
    GF2mMatrix otherMatrix = (GF2mMatrix)other;
    
    if ((!field.equals(field)) || (numRows != numColumns) || (numColumns != numColumns))
    {


      return false;
    }
    
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        if (matrix[i][j] != matrix[i][j])
        {
          return false;
        }
      }
    }
    
    return true;
  }
  
  public int hashCode()
  {
    int hash = (field.hashCode() * 31 + numRows) * 31 + numColumns;
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        hash = hash * 31 + matrix[i][j];
      }
    }
    return hash;
  }
  

  public String toString()
  {
    String str = numRows + " x " + numColumns + " Matrix over " + field.toString() + ": \n";
    
    for (int i = 0; i < numRows; i++)
    {
      for (int j = 0; j < numColumns; j++)
      {
        str = str + field.elementToStr(matrix[i][j]) + " : ";
      }
      str = str + "\n";
    }
    
    return str;
  }
}
