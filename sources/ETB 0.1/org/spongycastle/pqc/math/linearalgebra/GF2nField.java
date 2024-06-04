package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;
import java.util.Vector;






























public abstract class GF2nField
{
  protected final SecureRandom random;
  protected int mDegree;
  protected GF2Polynomial fieldPolynomial;
  protected Vector fields;
  protected Vector matrices;
  
  protected GF2nField(SecureRandom random)
  {
    this.random = random;
  }
  





  public final int getDegree()
  {
    return mDegree;
  }
  





  public final GF2Polynomial getFieldPolynomial()
  {
    if (fieldPolynomial == null)
    {
      computeFieldPolynomial();
    }
    return new GF2Polynomial(fieldPolynomial);
  }
  







  public final boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2nField)))
    {
      return false;
    }
    
    GF2nField otherField = (GF2nField)other;
    
    if (mDegree != mDegree)
    {
      return false;
    }
    if (!fieldPolynomial.equals(fieldPolynomial))
    {
      return false;
    }
    if (((this instanceof GF2nPolynomialField)) && (!(otherField instanceof GF2nPolynomialField)))
    {

      return false;
    }
    if (((this instanceof GF2nONBField)) && (!(otherField instanceof GF2nONBField)))
    {

      return false;
    }
    return true;
  }
  



  public int hashCode()
  {
    return mDegree + fieldPolynomial.hashCode();
  }
  







  protected abstract GF2nElement getRandomRoot(GF2Polynomial paramGF2Polynomial);
  







  protected abstract void computeCOBMatrix(GF2nField paramGF2nField);
  






  protected abstract void computeFieldPolynomial();
  






  protected final GF2Polynomial[] invertMatrix(GF2Polynomial[] matrix)
  {
    GF2Polynomial[] a = new GF2Polynomial[matrix.length];
    GF2Polynomial[] inv = new GF2Polynomial[matrix.length];
    


    for (int i = 0; i < mDegree; i++)
    {
      try
      {
        a[i] = new GF2Polynomial(matrix[i]);
        inv[i] = new GF2Polynomial(mDegree);
        inv[i].setBit(mDegree - 1 - i);
      }
      catch (RuntimeException BDNEExc)
      {
        BDNEExc.printStackTrace();
      }
    }
    

    for (i = 0; i < mDegree - 1; i++)
    {

      int j = i;
      while ((j < mDegree) && (!a[j].testBit(mDegree - 1 - i)))
      {
        j++;
      }
      if (j >= mDegree)
      {
        throw new RuntimeException("GF2nField.invertMatrix: Matrix cannot be inverted!");
      }
      
      if (i != j)
      {
        GF2Polynomial dummy = a[i];
        a[i] = a[j];
        a[j] = dummy;
        dummy = inv[i];
        inv[i] = inv[j];
        inv[j] = dummy;
      }
      for (j = i + 1; j < mDegree; j++)
      {

        if (a[j].testBit(mDegree - 1 - i))
        {
          a[j].addToThis(a[i]);
          inv[j].addToThis(inv[i]);
        }
      }
    }
    
    for (i = mDegree - 1; i > 0; i--)
    {
      for (int j = i - 1; j >= 0; j--)
      {

        if (a[j].testBit(mDegree - 1 - i))
        {
          a[j].addToThis(a[i]);
          inv[j].addToThis(inv[i]);
        }
      }
    }
    return inv;
  }
  
















  public final GF2nElement convert(GF2nElement elem, GF2nField basis)
    throws RuntimeException
  {
    if (basis == this)
    {
      return (GF2nElement)elem.clone();
    }
    if (fieldPolynomial.equals(fieldPolynomial))
    {
      return (GF2nElement)elem.clone();
    }
    if (mDegree != mDegree)
    {
      throw new RuntimeException("GF2nField.convert: B1 has a different degree and thus cannot be coverted to!");
    }
    



    int i = fields.indexOf(basis);
    if (i == -1)
    {
      computeCOBMatrix(basis);
      i = fields.indexOf(basis);
    }
    GF2Polynomial[] COBMatrix = (GF2Polynomial[])matrices.elementAt(i);
    
    GF2nElement elemCopy = (GF2nElement)elem.clone();
    if ((elemCopy instanceof GF2nONBElement))
    {

      ((GF2nONBElement)elemCopy).reverseOrder();
    }
    GF2Polynomial bs = new GF2Polynomial(mDegree, elemCopy.toFlexiBigInt());
    bs.expandN(mDegree);
    GF2Polynomial result = new GF2Polynomial(mDegree);
    for (i = 0; i < mDegree; i++)
    {
      if (bs.vectorMult(COBMatrix[i]))
      {
        result.setBit(mDegree - 1 - i);
      }
    }
    if ((basis instanceof GF2nPolynomialField))
    {
      return new GF2nPolynomialElement((GF2nPolynomialField)basis, result);
    }
    
    if ((basis instanceof GF2nONBField))
    {

      GF2nONBElement res = new GF2nONBElement((GF2nONBField)basis, result.toFlexiBigInt());
      
      res.reverseOrder();
      return res;
    }
    

    throw new RuntimeException("GF2nField.convert: B1 must be an instance of GF2nPolynomialField or GF2nONBField!");
  }
}
