package org.spongycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;






































public class PolynomialGF2mSmallM
{
  private GF2mField field;
  private int degree;
  private int[] coefficients;
  public static final char RANDOM_IRREDUCIBLE_POLYNOMIAL = 'I';
  
  public PolynomialGF2mSmallM(GF2mField field)
  {
    this.field = field;
    degree = -1;
    coefficients = new int[1];
  }
  









  public PolynomialGF2mSmallM(GF2mField field, int deg, char typeOfPolynomial, SecureRandom sr)
  {
    this.field = field;
    
    switch (typeOfPolynomial)
    {
    case 'I': 
      coefficients = createRandomIrreduciblePolynomial(deg, sr);
      break;
    default: 
      throw new IllegalArgumentException(" Error: type " + typeOfPolynomial + " is not defined for GF2smallmPolynomial");
    }
    
    
    computeDegree();
  }
  








  private int[] createRandomIrreduciblePolynomial(int deg, SecureRandom sr)
  {
    int[] resCoeff = new int[deg + 1];
    resCoeff[deg] = 1;
    resCoeff[0] = field.getRandomNonZeroElement(sr);
    for (int i = 1; i < deg; i++)
    {
      resCoeff[i] = field.getRandomElement(sr);
    }
    while (!isIrreducible(resCoeff))
    {
      int n = RandUtils.nextInt(sr, deg);
      if (n == 0)
      {
        resCoeff[0] = field.getRandomNonZeroElement(sr);
      }
      else
      {
        resCoeff[n] = field.getRandomElement(sr);
      }
    }
    return resCoeff;
  }
  






  public PolynomialGF2mSmallM(GF2mField field, int degree)
  {
    this.field = field;
    this.degree = degree;
    coefficients = new int[degree + 1];
    coefficients[degree] = 1;
  }
  







  public PolynomialGF2mSmallM(GF2mField field, int[] coeffs)
  {
    this.field = field;
    coefficients = normalForm(coeffs);
    computeDegree();
  }
  






  public PolynomialGF2mSmallM(GF2mField field, byte[] enc)
  {
    this.field = field;
    

    int d = 8;
    int count = 1;
    while (field.getDegree() > d)
    {
      count++;
      d += 8;
    }
    
    if (enc.length % count != 0)
    {
      throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
    }
    

    coefficients = new int[enc.length / count];
    count = 0;
    for (int i = 0; i < coefficients.length; i++)
    {
      for (int j = 0; j < d; j += 8)
      {
        coefficients[i] ^= (enc[(count++)] & 0xFF) << j;
      }
      if (!this.field.isElementOfThisField(coefficients[i]))
      {
        throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
      }
    }
    

    if ((coefficients.length != 1) && (coefficients[(coefficients.length - 1)] == 0))
    {

      throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
    }
    
    computeDegree();
  }
  






  public PolynomialGF2mSmallM(PolynomialGF2mSmallM other)
  {
    field = field;
    degree = degree;
    coefficients = IntUtils.clone(coefficients);
  }
  







  public PolynomialGF2mSmallM(GF2mVector vect)
  {
    this(vect.getField(), vect.getIntArrayForm());
  }
  










  public int getDegree()
  {
    int d = coefficients.length - 1;
    if (coefficients[d] == 0)
    {
      return -1;
    }
    return d;
  }
  



  public int getHeadCoefficient()
  {
    if (degree == -1)
    {
      return 0;
    }
    return coefficients[degree];
  }
  






  private static int headCoefficient(int[] a)
  {
    int degree = computeDegree(a);
    if (degree == -1)
    {
      return 0;
    }
    return a[degree];
  }
  






  public int getCoefficient(int index)
  {
    if ((index < 0) || (index > degree))
    {
      return 0;
    }
    return coefficients[index];
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
    
    byte[] res = new byte[coefficients.length * count];
    count = 0;
    for (int i = 0; i < coefficients.length; i++)
    {
      for (int j = 0; j < d; j += 8)
      {
        res[(count++)] = ((byte)(coefficients[i] >>> j));
      }
    }
    
    return res;
  }
  







  public int evaluateAt(int e)
  {
    int result = coefficients[degree];
    for (int i = degree - 1; i >= 0; i--)
    {
      result = field.mult(result, e) ^ coefficients[i];
    }
    return result;
  }
  






  public PolynomialGF2mSmallM add(PolynomialGF2mSmallM addend)
  {
    int[] resultCoeff = add(coefficients, coefficients);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  





  public void addToThis(PolynomialGF2mSmallM addend)
  {
    coefficients = add(coefficients, coefficients);
    computeDegree();
  }
  


  private int[] add(int[] a, int[] b)
  {
    int[] addend;
    

    int[] result;
    
    int[] addend;
    
    if (a.length < b.length)
    {
      int[] result = new int[b.length];
      System.arraycopy(b, 0, result, 0, b.length);
      addend = a;
    }
    else
    {
      result = new int[a.length];
      System.arraycopy(a, 0, result, 0, a.length);
      addend = b;
    }
    
    for (int i = addend.length - 1; i >= 0; i--)
    {
      result[i] = field.add(result[i], addend[i]);
    }
    
    return result;
  }
  






  public PolynomialGF2mSmallM addMonomial(int degree)
  {
    int[] monomial = new int[degree + 1];
    monomial[degree] = 1;
    int[] resultCoeff = add(coefficients, monomial);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  








  public PolynomialGF2mSmallM multWithElement(int element)
  {
    if (!field.isElementOfThisField(element))
    {
      throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
    }
    
    int[] resultCoeff = multWithElement(coefficients, element);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  







  public void multThisWithElement(int element)
  {
    if (!field.isElementOfThisField(element))
    {
      throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
    }
    
    coefficients = multWithElement(coefficients, element);
    computeDegree();
  }
  








  private int[] multWithElement(int[] a, int element)
  {
    int degree = computeDegree(a);
    if ((degree == -1) || (element == 0))
    {
      return new int[1];
    }
    
    if (element == 1)
    {
      return IntUtils.clone(a);
    }
    
    int[] result = new int[degree + 1];
    for (int i = degree; i >= 0; i--)
    {
      result[i] = field.mult(a[i], element);
    }
    
    return result;
  }
  






  public PolynomialGF2mSmallM multWithMonomial(int k)
  {
    int[] resultCoeff = multWithMonomial(coefficients, k);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  







  private static int[] multWithMonomial(int[] a, int k)
  {
    int d = computeDegree(a);
    if (d == -1)
    {
      return new int[1];
    }
    int[] result = new int[d + k + 1];
    System.arraycopy(a, 0, result, k, d + 1);
    return result;
  }
  







  public PolynomialGF2mSmallM[] div(PolynomialGF2mSmallM f)
  {
    int[][] resultCoeffs = div(coefficients, coefficients);
    return new PolynomialGF2mSmallM[] { new PolynomialGF2mSmallM(field, resultCoeffs[0]), new PolynomialGF2mSmallM(field, resultCoeffs[1]) };
  }
  










  private int[][] div(int[] a, int[] f)
  {
    int df = computeDegree(f);
    int da = computeDegree(a) + 1;
    if (df == -1)
    {
      throw new ArithmeticException("Division by zero.");
    }
    int[][] result = new int[2][];
    result[0] = new int[1];
    result[1] = new int[da];
    int hc = headCoefficient(f);
    hc = field.inverse(hc);
    result[0][0] = 0;
    System.arraycopy(a, 0, result[1], 0, result[1].length);
    while (df <= computeDegree(result[1]))
    {

      int[] coeff = new int[1];
      coeff[0] = field.mult(headCoefficient(result[1]), hc);
      int[] q = multWithElement(f, coeff[0]);
      int n = computeDegree(result[1]) - df;
      q = multWithMonomial(q, n);
      coeff = multWithMonomial(coeff, n);
      result[0] = add(coeff, result[0]);
      result[1] = add(q, result[1]);
    }
    return result;
  }
  






  public PolynomialGF2mSmallM gcd(PolynomialGF2mSmallM f)
  {
    int[] resultCoeff = gcd(coefficients, coefficients);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  








  private int[] gcd(int[] f, int[] g)
  {
    int[] a = f;
    int[] b = g;
    if (computeDegree(a) == -1)
    {
      return b;
    }
    while (computeDegree(b) != -1)
    {
      int[] c = mod(a, b);
      a = new int[b.length];
      System.arraycopy(b, 0, a, 0, a.length);
      b = new int[c.length];
      System.arraycopy(c, 0, b, 0, b.length);
    }
    int coeff = field.inverse(headCoefficient(a));
    return multWithElement(a, coeff);
  }
  







  public PolynomialGF2mSmallM multiply(PolynomialGF2mSmallM factor)
  {
    int[] resultCoeff = multiply(coefficients, coefficients);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  




  private int[] multiply(int[] a, int[] b)
  {
    int[] mult2;
    



    if (computeDegree(a) < computeDegree(b))
    {
      int[] mult1 = b;
      mult2 = a;
    }
    else
    {
      mult1 = a;
      mult2 = b;
    }
    
    int[] mult1 = normalForm(mult1);
    int[] mult2 = normalForm(mult2);
    
    if (mult2.length == 1)
    {
      return multWithElement(mult1, mult2[0]);
    }
    
    int d1 = mult1.length;
    int d2 = mult2.length;
    int[] result = new int[d1 + d2 - 1];
    
    if (d2 != d1)
    {
      int[] res1 = new int[d2];
      int[] res2 = new int[d1 - d2];
      System.arraycopy(mult1, 0, res1, 0, res1.length);
      System.arraycopy(mult1, d2, res2, 0, res2.length);
      res1 = multiply(res1, mult2);
      res2 = multiply(res2, mult2);
      res2 = multWithMonomial(res2, d2);
      result = add(res1, res2);
    }
    else
    {
      d2 = d1 + 1 >>> 1;
      int d = d1 - d2;
      int[] firstPartMult1 = new int[d2];
      int[] firstPartMult2 = new int[d2];
      int[] secondPartMult1 = new int[d];
      int[] secondPartMult2 = new int[d];
      
      System.arraycopy(mult1, 0, firstPartMult1, 0, firstPartMult1.length);
      
      System.arraycopy(mult1, d2, secondPartMult1, 0, secondPartMult1.length);
      

      System.arraycopy(mult2, 0, firstPartMult2, 0, firstPartMult2.length);
      
      System.arraycopy(mult2, d2, secondPartMult2, 0, secondPartMult2.length);
      
      int[] helpPoly1 = add(firstPartMult1, secondPartMult1);
      int[] helpPoly2 = add(firstPartMult2, secondPartMult2);
      int[] res1 = multiply(firstPartMult1, firstPartMult2);
      int[] res2 = multiply(helpPoly1, helpPoly2);
      int[] res3 = multiply(secondPartMult1, secondPartMult2);
      res2 = add(res2, res1);
      res2 = add(res2, res3);
      res3 = multWithMonomial(res3, d2);
      result = add(res2, res3);
      result = multWithMonomial(result, d2);
      result = add(result, res1);
    }
    
    return result;
  }
  











  private boolean isIrreducible(int[] a)
  {
    if (a[0] == 0)
    {
      return false;
    }
    int d = computeDegree(a) >> 1;
    int[] u = { 0, 1 };
    int[] Y = { 0, 1 };
    int fieldDegree = field.getDegree();
    for (int i = 0; i < d; i++)
    {
      for (int j = fieldDegree - 1; j >= 0; j--)
      {
        u = modMultiply(u, u, a);
      }
      u = normalForm(u);
      int[] g = gcd(add(u, Y), a);
      if (computeDegree(g) != 0)
      {
        return false;
      }
    }
    return true;
  }
  






  public PolynomialGF2mSmallM mod(PolynomialGF2mSmallM f)
  {
    int[] resultCoeff = mod(coefficients, coefficients);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  







  private int[] mod(int[] a, int[] f)
  {
    int df = computeDegree(f);
    if (df == -1)
    {
      throw new ArithmeticException("Division by zero");
    }
    int[] result = new int[a.length];
    int hc = headCoefficient(f);
    hc = field.inverse(hc);
    System.arraycopy(a, 0, result, 0, result.length);
    while (df <= computeDegree(result))
    {

      int coeff = field.mult(headCoefficient(result), hc);
      int[] q = multWithMonomial(f, computeDegree(result) - df);
      q = multWithElement(q, coeff);
      result = add(q, result);
    }
    return result;
  }
  









  public PolynomialGF2mSmallM modMultiply(PolynomialGF2mSmallM a, PolynomialGF2mSmallM b)
  {
    int[] resultCoeff = modMultiply(coefficients, coefficients, coefficients);
    
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  








  public PolynomialGF2mSmallM modSquareMatrix(PolynomialGF2mSmallM[] matrix)
  {
    int length = matrix.length;
    
    int[] resultCoeff = new int[length];
    int[] thisSquare = new int[length];
    

    for (int i = 0; i < coefficients.length; i++)
    {
      thisSquare[i] = field.mult(coefficients[i], coefficients[i]);
    }
    

    for (int i = 0; i < length; i++)
    {

      for (int j = 0; j < length; j++)
      {
        if (i < coefficients.length)
        {


          int scalarTerm = field.mult(coefficients[i], thisSquare[j]);
          
          resultCoeff[i] = field.add(resultCoeff[i], scalarTerm);
        }
      }
    }
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  









  private int[] modMultiply(int[] a, int[] b, int[] g)
  {
    return mod(multiply(a, b), g);
  }
  






  public PolynomialGF2mSmallM modSquareRoot(PolynomialGF2mSmallM a)
  {
    int[] resultCoeff = IntUtils.clone(coefficients);
    int[] help = modMultiply(resultCoeff, resultCoeff, coefficients);
    while (!isEqual(help, coefficients))
    {
      resultCoeff = normalForm(help);
      help = modMultiply(resultCoeff, resultCoeff, coefficients);
    }
    
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  











  public PolynomialGF2mSmallM modSquareRootMatrix(PolynomialGF2mSmallM[] matrix)
  {
    int length = matrix.length;
    
    int[] resultCoeff = new int[length];
    

    for (int i = 0; i < length; i++)
    {

      for (int j = 0; j < length; j++)
      {
        if (i < coefficients.length)
        {


          if (j < coefficients.length)
          {
            int scalarTerm = field.mult(coefficients[i], coefficients[j]);
            
            resultCoeff[i] = field.add(resultCoeff[i], scalarTerm);
          }
        }
      }
    }
    
    for (int i = 0; i < length; i++)
    {
      resultCoeff[i] = field.sqRoot(resultCoeff[i]);
    }
    
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  









  public PolynomialGF2mSmallM modDiv(PolynomialGF2mSmallM divisor, PolynomialGF2mSmallM modulus)
  {
    int[] resultCoeff = modDiv(coefficients, coefficients, coefficients);
    
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  









  private int[] modDiv(int[] a, int[] b, int[] g)
  {
    int[] r0 = normalForm(g);
    int[] r1 = mod(b, g);
    int[] s0 = { 0 };
    int[] s1 = mod(a, g);
    

    while (computeDegree(r1) != -1)
    {
      int[][] q = div(r0, r1);
      r0 = normalForm(r1);
      r1 = normalForm(q[1]);
      int[] s2 = add(s0, modMultiply(q[0], s1, g));
      s0 = normalForm(s1);
      s1 = normalForm(s2);
    }
    
    int hc = headCoefficient(r0);
    s0 = multWithElement(s0, field.inverse(hc));
    return s0;
  }
  






  public PolynomialGF2mSmallM modInverse(PolynomialGF2mSmallM a)
  {
    int[] unit = { 1 };
    int[] resultCoeff = modDiv(unit, coefficients, coefficients);
    return new PolynomialGF2mSmallM(field, resultCoeff);
  }
  








  public PolynomialGF2mSmallM[] modPolynomialToFracton(PolynomialGF2mSmallM g)
  {
    int dg = degree >> 1;
    int[] a0 = normalForm(coefficients);
    int[] a1 = mod(coefficients, coefficients);
    int[] b0 = { 0 };
    int[] b1 = { 1 };
    while (computeDegree(a1) > dg)
    {
      int[][] q = div(a0, a1);
      a0 = a1;
      a1 = q[1];
      int[] b2 = add(b0, modMultiply(q[0], b1, coefficients));
      b0 = b1;
      b1 = b2;
    }
    
    return new PolynomialGF2mSmallM[] { new PolynomialGF2mSmallM(field, a1), new PolynomialGF2mSmallM(field, b1) };
  }
  












  public boolean equals(Object other)
  {
    if ((other == null) || (!(other instanceof PolynomialGF2mSmallM)))
    {
      return false;
    }
    
    PolynomialGF2mSmallM p = (PolynomialGF2mSmallM)other;
    
    if ((field.equals(field)) && (degree == degree) && 
      (isEqual(coefficients, coefficients)))
    {
      return true;
    }
    
    return false;
  }
  








  private static boolean isEqual(int[] a, int[] b)
  {
    int da = computeDegree(a);
    int db = computeDegree(b);
    if (da != db)
    {
      return false;
    }
    for (int i = 0; i <= da; i++)
    {
      if (a[i] != b[i])
      {
        return false;
      }
    }
    return true;
  }
  



  public int hashCode()
  {
    int hash = field.hashCode();
    for (int j = 0; j < coefficients.length; j++)
    {
      hash = hash * 31 + coefficients[j];
    }
    return hash;
  }
  





  public String toString()
  {
    String str = " Polynomial over " + field.toString() + ": \n";
    
    for (int i = 0; i < coefficients.length; i++)
    {
      str = str + field.elementToStr(coefficients[i]) + "Y^" + i + "+";
    }
    str = str + ";";
    
    return str;
  }
  




  private void computeDegree()
  {
    degree = (coefficients.length - 1);
    while ((degree >= 0) && (coefficients[degree] == 0)) { degree -= 1;
    }
  }
  










  private static int computeDegree(int[] a)
  {
    for (int degree = a.length - 1; (degree >= 0) && (a[degree] == 0); degree--) {}
    


    return degree;
  }
  






  private static int[] normalForm(int[] a)
  {
    int d = computeDegree(a);
    

    if (d == -1)
    {

      return new int[1];
    }
    

    if (a.length == d + 1)
    {

      return IntUtils.clone(a);
    }
    

    int[] result = new int[d + 1];
    System.arraycopy(a, 0, result, 0, d + 1);
    return result;
  }
}
