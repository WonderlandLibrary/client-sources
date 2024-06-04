package org.spongycastle.pqc.math.ntru.euclid;




public class IntEuclidean
{
  public int x;
  

  public int y;
  

  public int gcd;
  


  private IntEuclidean() {}
  


  public static IntEuclidean calculate(int a, int b)
  {
    int x = 0;
    int lastx = 1;
    int y = 1;
    int lasty = 0;
    while (b != 0)
    {
      int quotient = a / b;
      
      int temp = a;
      a = b;
      b = temp % b;
      
      temp = x;
      x = lastx - quotient * x;
      lastx = temp;
      
      temp = y;
      y = lasty - quotient * y;
      lasty = temp;
    }
    
    IntEuclidean result = new IntEuclidean();
    x = lastx;
    y = lasty;
    gcd = a;
    return result;
  }
}
