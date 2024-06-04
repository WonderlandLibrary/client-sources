package org.spongycastle.pqc.math.linearalgebra;







public class GF2nPolynomial
{
  private GF2nElement[] coeff;
  





  private int size;
  






  public GF2nPolynomial(int deg, GF2nElement elem)
  {
    size = deg;
    coeff = new GF2nElement[size];
    for (int i = 0; i < size; i++)
    {
      coeff[i] = ((GF2nElement)elem.clone());
    }
  }
  





  private GF2nPolynomial(int deg)
  {
    size = deg;
    coeff = new GF2nElement[size];
  }
  






  public GF2nPolynomial(GF2nPolynomial a)
  {
    coeff = new GF2nElement[size];
    size = size;
    for (int i = 0; i < size; i++)
    {
      coeff[i] = ((GF2nElement)coeff[i].clone());
    }
  }
  







  public GF2nPolynomial(GF2Polynomial polynomial, GF2nField B1)
  {
    size = (B1.getDegree() + 1);
    coeff = new GF2nElement[size];
    
    if ((B1 instanceof GF2nONBField))
    {
      for (int i = 0; i < size; i++)
      {
        if (polynomial.testBit(i))
        {
          coeff[i] = GF2nONBElement.ONE((GF2nONBField)B1);
        }
        else
        {
          coeff[i] = GF2nONBElement.ZERO((GF2nONBField)B1);
        }
      }
    }
    if ((B1 instanceof GF2nPolynomialField))
    {
      for (int i = 0; i < size; i++)
      {
        if (polynomial.testBit(i))
        {

          coeff[i] = GF2nPolynomialElement.ONE((GF2nPolynomialField)B1);

        }
        else
        {
          coeff[i] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)B1);
        }
      }
    }
    

    throw new IllegalArgumentException("PolynomialGF2n(Bitstring, GF2nField): B1 must be an instance of GF2nONBField or GF2nPolynomialField!");
    

    int i;
  }
  

  public final void assignZeroToElements()
  {
    for (int i = 0; i < size; i++)
    {
      coeff[i].assignZero();
    }
  }
  






  public final int size()
  {
    return size;
  }
  






  public final int getDegree()
  {
    for (int i = size - 1; i >= 0; i--)
    {
      if (!coeff[i].isZero())
      {
        return i;
      }
    }
    return -1;
  }
  





  public final void enlarge(int k)
  {
    if (k <= size)
    {
      return;
    }
    
    GF2nElement[] res = new GF2nElement[k];
    System.arraycopy(coeff, 0, res, 0, size);
    GF2nField f = coeff[0].getField();
    if ((coeff[0] instanceof GF2nPolynomialElement))
    {
      for (int i = size; i < k; i++)
      {
        res[i] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)f);
      }
    }
    if ((coeff[0] instanceof GF2nONBElement))
    {
      for (int i = size; i < k; i++)
      {
        res[i] = GF2nONBElement.ZERO((GF2nONBField)f);
      }
    }
    size = k;
    coeff = res;
  }
  
  public final void shrink()
  {
    int i = size - 1;
    while ((coeff[i].isZero()) && (i > 0))
    {
      i--;
    }
    i++;
    if (i < size)
    {
      GF2nElement[] res = new GF2nElement[i];
      System.arraycopy(coeff, 0, res, 0, i);
      coeff = res;
      size = i;
    }
  }
  






  public final void set(int index, GF2nElement elem)
  {
    if ((!(elem instanceof GF2nPolynomialElement)) && (!(elem instanceof GF2nONBElement)))
    {

      throw new IllegalArgumentException("PolynomialGF2n.set f must be an instance of either GF2nPolynomialElement or GF2nONBElement!");
    }
    

    coeff[index] = ((GF2nElement)elem.clone());
  }
  






  public final GF2nElement at(int index)
  {
    return coeff[index];
  }
  






  public final boolean isZero()
  {
    for (int i = 0; i < size; i++)
    {
      if (coeff[i] != null)
      {
        if (!coeff[i].isZero())
        {
          return false;
        }
      }
    }
    return true;
  }
  
  public final boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof GF2nPolynomial)))
    {
      return false;
    }
    
    GF2nPolynomial otherPol = (GF2nPolynomial)other;
    
    if (getDegree() != otherPol.getDegree())
    {
      return false;
    }
    
    for (int i = 0; i < size; i++)
    {
      if (!coeff[i].equals(coeff[i]))
      {
        return false;
      }
    }
    return true;
  }
  



  public int hashCode()
  {
    return getDegree() + coeff.hashCode();
  }
  





  public final GF2nPolynomial add(GF2nPolynomial b)
    throws RuntimeException
  {
    GF2nPolynomial result;
    




    if (size() >= b.size())
    {
      GF2nPolynomial result = new GF2nPolynomial(size());
      
      for (int i = 0; i < b.size(); i++)
      {
        coeff[i] = ((GF2nElement)coeff[i].add(coeff[i]));
      }
      for (; i < size(); i++)
      {
        coeff[i] = coeff[i];
      }
    }
    else
    {
      result = new GF2nPolynomial(b.size());
      
      for (int i = 0; i < size(); i++)
      {
        coeff[i] = ((GF2nElement)coeff[i].add(coeff[i]));
      }
      for (; i < b.size(); i++)
      {
        coeff[i] = coeff[i];
      }
    }
    return result;
  }
  









  public final GF2nPolynomial scalarMultiply(GF2nElement s)
    throws RuntimeException
  {
    GF2nPolynomial result = new GF2nPolynomial(size());
    
    for (int i = 0; i < size(); i++)
    {
      coeff[i] = ((GF2nElement)coeff[i].multiply(s));
    }
    

    return result;
  }
  










  public final GF2nPolynomial multiply(GF2nPolynomial b)
    throws RuntimeException
  {
    int aDegree = size();
    int bDegree = b.size();
    if (aDegree != bDegree)
    {
      throw new IllegalArgumentException("PolynomialGF2n.multiply: this and b must have the same size!");
    }
    

    GF2nPolynomial result = new GF2nPolynomial((aDegree << 1) - 1);
    for (int i = 0; i < size(); i++)
    {
      for (int j = 0; j < b.size(); j++)
      {
        if (coeff[(i + j)] == null)
        {

          coeff[(i + j)] = ((GF2nElement)coeff[i].multiply(coeff[j]));

        }
        else
        {
          coeff[(i + j)] = ((GF2nElement)coeff[(i + j)].add(coeff[i].multiply(coeff[j])));
        }
      }
    }
    return result;
  }
  












  public final GF2nPolynomial multiplyAndReduce(GF2nPolynomial b, GF2nPolynomial g)
    throws RuntimeException, ArithmeticException
  {
    return multiply(b).reduce(g);
  }
  










  public final GF2nPolynomial reduce(GF2nPolynomial g)
    throws RuntimeException, ArithmeticException
  {
    return remainder(g);
  }
  






  public final void shiftThisLeft(int amount)
  {
    if (amount > 0)
    {

      int oldSize = size;
      GF2nField f = coeff[0].getField();
      enlarge(size + amount);
      for (int i = oldSize - 1; i >= 0; i--)
      {
        coeff[(i + amount)] = coeff[i];
      }
      if ((coeff[0] instanceof GF2nPolynomialElement))
      {
        for (i = amount - 1; i >= 0; i--)
        {

          coeff[i] = GF2nPolynomialElement.ZERO((GF2nPolynomialField)f);
        }
      }
      if ((coeff[0] instanceof GF2nONBElement))
      {
        for (i = amount - 1; i >= 0; i--)
        {
          coeff[i] = GF2nONBElement.ZERO((GF2nONBField)f);
        }
      }
    }
  }
  
  public final GF2nPolynomial shiftLeft(int amount)
  {
    if (amount <= 0)
    {
      return new GF2nPolynomial(this);
    }
    GF2nPolynomial result = new GF2nPolynomial(size + amount, coeff[0]);
    result.assignZeroToElements();
    for (int i = 0; i < size; i++)
    {
      coeff[(i + amount)] = coeff[i];
    }
    return result;
  }
  









  public final GF2nPolynomial[] divide(GF2nPolynomial b)
    throws RuntimeException, ArithmeticException
  {
    GF2nPolynomial[] result = new GF2nPolynomial[2];
    GF2nPolynomial a = new GF2nPolynomial(this);
    a.shrink();
    

    int bDegree = b.getDegree();
    GF2nElement inv = (GF2nElement)coeff[bDegree].invert();
    if (a.getDegree() < bDegree)
    {
      result[0] = new GF2nPolynomial(this);
      result[0].assignZeroToElements();
      result[0].shrink();
      result[1] = new GF2nPolynomial(this);
      result[1].shrink();
      return result;
    }
    result[0] = new GF2nPolynomial(this);
    result[0].assignZeroToElements();
    int i = a.getDegree() - bDegree;
    while (i >= 0)
    {
      GF2nElement factor = (GF2nElement)coeff[a.getDegree()].multiply(inv);
      GF2nPolynomial shift = b.scalarMultiply(factor);
      shift.shiftThisLeft(i);
      a = a.add(shift);
      a.shrink();
      0coeff[i] = ((GF2nElement)factor.clone());
      i = a.getDegree() - bDegree;
    }
    result[1] = a;
    result[0].shrink();
    return result;
  }
  









  public final GF2nPolynomial remainder(GF2nPolynomial b)
    throws RuntimeException, ArithmeticException
  {
    GF2nPolynomial[] result = new GF2nPolynomial[2];
    result = divide(b);
    return result[1];
  }
  









  public final GF2nPolynomial quotient(GF2nPolynomial b)
    throws RuntimeException, ArithmeticException
  {
    GF2nPolynomial[] result = new GF2nPolynomial[2];
    result = divide(b);
    return result[0];
  }
  











  public final GF2nPolynomial gcd(GF2nPolynomial g)
    throws RuntimeException, ArithmeticException
  {
    GF2nPolynomial a = new GF2nPolynomial(this);
    GF2nPolynomial b = new GF2nPolynomial(g);
    a.shrink();
    b.shrink();
    


    while (!b.isZero())
    {
      GF2nPolynomial c = a.remainder(b);
      a = b;
      b = c;
    }
    GF2nElement alpha = coeff[a.getDegree()];
    GF2nPolynomial result = a.scalarMultiply((GF2nElement)alpha.invert());
    return result;
  }
}
